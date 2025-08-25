package ecomhub.authservice.application.command.role.delete;

import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRoleHandlerTests {

    @InjectMocks
    private HardDeleteRoleHandler handler;

    @Mock
    private RoleRepositoryPort roleRepository;

    private UUID roleId;
    private UUID requesterId;
    private Role targetRole;

    @BeforeEach
    void setUp() {
        roleId = UUID.randomUUID();
        requesterId = UUID.randomUUID();
        targetRole = Mockito.spy(new Role(roleId, "Test", "Test Role", Set.of(), true, 50));
    }



    @Test
    void handle_WhenRoleNotFound_ShouldThrowRoleNotFoundException() {
        // Arrange
        DeleteRoleCommand command = new DeleteRoleCommand(roleId,requesterId);

        when(roleRepository.getById(roleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoleNotFoundException.class, () -> handler.handle(command));

        // Verify
        verify(roleRepository).getById(roleId);
        verify(roleRepository, never()).updateActive(any(), anyBoolean());
    }

}
