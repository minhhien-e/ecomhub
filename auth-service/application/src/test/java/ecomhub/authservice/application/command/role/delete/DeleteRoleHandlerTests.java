package ecomhub.authservice.application.command.role.delete;

import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.abstracts.UpdateFailureException;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRoleHandlerTests {

    @InjectMocks
    private DeleteRoleHandler handler;

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
    void handle_WhenRoleExistsAndRequesterHasHigherRole_ShouldDeleteSuccessfully() {
        // Arrange
        Role higherRole = mock(Role.class);
        DeleteRoleCommand command = new DeleteRoleCommand(roleId);
        command.setRequesterId(requesterId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(targetRole));
        when(roleRepository.findByAccountIdAndHigherLevelThan(requesterId, 50)).thenReturn(List.of(higherRole));
        doNothing().when(targetRole).setActive(false, higherRole);
        when(targetRole.isActive()).thenReturn(false);
        when(roleRepository.updateActive(roleId, false)).thenReturn(1);

        // Act & Assert
        assertDoesNotThrow(() -> handler.handle(command));

        // Verify
        verify(roleRepository).findById(roleId);
        verify(roleRepository).findByAccountIdAndHigherLevelThan(requesterId, 50);
        verify(targetRole).setActive(false, higherRole);
        verify(targetRole).isActive();
        verify(roleRepository).updateActive(roleId, false);
    }

    @Test
    void handle_WhenNoRoleHigherThanTargetRole_ShouldThrowForbiddenException() {
        // Arrange
        DeleteRoleCommand command = new DeleteRoleCommand(roleId);
        command.setRequesterId(requesterId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(targetRole));
        when(roleRepository.findByAccountIdAndHigherLevelThan(requesterId, 50)).thenReturn(List.of());

        // Act & Assert
        assertThrows(ForbiddenException.class, () -> handler.handle(command));

        // Verify
        verify(roleRepository).findById(roleId);
        verify(roleRepository).findByAccountIdAndHigherLevelThan(requesterId, 50);
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void handle_WhenRoleNotFound_ShouldThrowRoleNotFoundException() {
        // Arrange
        DeleteRoleCommand command = new DeleteRoleCommand(roleId);
        command.setRequesterId(requesterId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoleNotFoundException.class, () -> handler.handle(command));

        // Verify
        verify(roleRepository).findById(roleId);
        verify(roleRepository, never()).updateActive(any(), anyBoolean());
    }

    @Test
    void handle_WhenUpdateFails_ShouldThrowUpdateFailureException() {
        // Arrange
        Role higherRole = mock(Role.class);
        DeleteRoleCommand command = new DeleteRoleCommand(roleId);
        command.setRequesterId(requesterId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(targetRole));
        when(roleRepository.findByAccountIdAndHigherLevelThan(requesterId, 50)).thenReturn(List.of(higherRole));
        doNothing().when(targetRole).setActive(false, higherRole);
        when(targetRole.isActive()).thenReturn(false);
        when(roleRepository.updateActive(roleId, false)).thenReturn(0);

        // Act & Assert
        assertThrows(UpdateFailureException.class, () -> handler.handle(command));

        // Verify
        verify(roleRepository).findById(roleId);
        verify(roleRepository).findByAccountIdAndHigherLevelThan(requesterId, 50);
        verify(targetRole).setActive(false, higherRole);
        verify(targetRole).isActive();
        verify(roleRepository).updateActive(roleId, false);
    }
}
