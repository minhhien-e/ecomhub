package ecomhub.authservice.application.command.account.register;

import ecomhub.authservice.application.enums.RoleName;
import ecomhub.authservice.application.mapper.AccountCommandMapper;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.concrete.account.EmailAlreadyExistsException;
import ecomhub.authservice.common.exception.concrete.account.PhoneNumberAlreadyExistsException;
import ecomhub.authservice.common.exception.concrete.account.UsernameAlreadyExistsException;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.service.abstracts.PasswordHashService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class RegisterAccountHandlerTest {

    @Mock
    private AccountRepositoryPort accountRepository;
    @Mock
    private RoleRepositoryPort roleRepository;
    @Mock
    private AccountCommandMapper accountCommandMapper;
    @Mock
    private PasswordHashService passwordHashService;

    @InjectMocks
    private RegisterAccountHandler handler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private RegisterAccountCommand createValidCommand() {
        RegisterAccountCommand cmd = new RegisterAccountCommand();
        cmd.setEmail("user@example.com");
        cmd.setUsername("username");
        cmd.setPhoneNumber("123456789");
        cmd.setRoles(List.of(RoleName.CUSTOMER.name()));
        return cmd;
    }

    @Test
    void shouldThrow_WhenEmailExists() {
        var command = createValidCommand();
        when(accountRepository.existsByIdentifier(command.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> handler.handle(command));
    }

    @Test
    void shouldThrow_WhenPhoneNumberExists() {
        var command = createValidCommand();
        when(accountRepository.existsByIdentifier(command.getEmail())).thenReturn(false);
        when(accountRepository.existsByIdentifier(command.getPhoneNumber())).thenReturn(true);

        assertThrows(PhoneNumberAlreadyExistsException.class, () -> handler.handle(command));
    }

    @Test
    void shouldThrow_WhenUsernameExists() {
        var command = createValidCommand();
        when(accountRepository.existsByIdentifier(command.getEmail())).thenReturn(false);
        when(accountRepository.existsByIdentifier(command.getPhoneNumber())).thenReturn(false);
        when(accountRepository.existsByIdentifier(command.getUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> handler.handle(command));
    }

    @Test
    void shouldThrow_WhenRoleNotFound() {
        var command = createValidCommand();
        when(accountRepository.existsByIdentifier(any())).thenReturn(false);
        when(roleRepository.findByName(RoleName.CUSTOMER.name())).thenReturn(Optional.empty());
        assertThrows(RoleNotFoundException.class, () -> handler.handle(command));
    }

}
