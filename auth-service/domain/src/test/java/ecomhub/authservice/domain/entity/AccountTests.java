package ecomhub.authservice.domain.entity;

import ecomhub.authservice.common.exception.concrete.account.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTests {

    private Account account;
    private Role testRole;
    private UUID testId;

    @BeforeEach
    void init() {
        testId = UUID.randomUUID();
        testRole = new Role("TestRole", "Test role description", 5);
        Set<Role> roles = Set.of(testRole);

        account = new Account(testId, "test@example.com", "testuser", "0939346634",
                "hashedPassword123", "LOCAL", true, roles);
    }

    //region Constructor Tests
    @Test
    void constructor_WithFullParams_ShouldInitializeCorrectly() {
        // Arrange
        Set<Role> roles = Set.of(testRole);

        // Act
        Account constructedAccount = new Account(testId, "test@example.com", "testuser",
                "0939346634", "hashedPassword123", "LOCAL", true, roles);

        // Assert
        assertEquals(testId, constructedAccount.getId());
        assertEquals("test@example.com", constructedAccount.getEmail().getValue());
        assertEquals("testuser", constructedAccount.getUsername().get().getValue());
        assertEquals("0939346634", constructedAccount.getPhoneNumber().getValue());
        assertEquals("hashedPassword123", constructedAccount.getPasswordHash().get().getHashedValue());
        assertEquals("LOCAL", constructedAccount.getProvider().getValue());
        assertTrue(constructedAccount.isActive());
        assertEquals(1, constructedAccount.getRoles().size());
        assertTrue(constructedAccount.getRoles().contains(testRole));
    }

    @Test
    void constructor_WithMinimalParams_ShouldInitializeWithGeneratedId_EmptyRoles_ActiveTrue() {
        // Act
        Account constructedAccount = new Account("new@example.com", "newuser", "0987654321",
                "rawPassword", "LOCAL");

        // Assert
        assertNotNull(constructedAccount.getId());
        assertEquals("new@example.com", constructedAccount.getEmail().getValue());
        assertEquals("newuser", constructedAccount.getUsername().get().getValue());
        assertEquals("0987654321", constructedAccount.getPhoneNumber().getValue());
        assertEquals("rawPassword", constructedAccount.getPasswordHash().get().getHashedValue());
        assertEquals("LOCAL", constructedAccount.getProvider().getValue());
        assertTrue(constructedAccount.isActive());
        assertTrue(constructedAccount.getRoles().isEmpty());
    }


    @Test
    void constructor_WithNullId_ShouldThrowException() {
        // Arrange
        Set<Role> roles = Set.of(testRole);

        // Act & Assert
        assertThrows(MissingIdInAccountException.class,
                () -> new Account(null, "test@example.com", "testuser", "0939346634",
                        "hashedPassword123", "LOCAL", true, roles));
    }

    @Test
    void constructor_WithEmptyRoles_ShouldThrowException() {
        // Arrange
        Set<Role> emptyRoles = new HashSet<>();

        // Act & Assert
        assertThrows(NoRoleAssignedException.class,
                () -> new Account(testId, "test@example.com", "testuser", "0939346634",
                        "hashedPassword123", "LOCAL", true, emptyRoles));
    }
    //endregion

    //region Role Management Tests
    @Test
    void grantRole_WhenNewRole_ShouldAddRole() {
        // Arrange
        Role newRole = new Role("NewRole", "New role description", 3);

        // Act
        account.grantRole(newRole);

        // Assert
        assertTrue(account.getRoles().contains(newRole));
        assertEquals(2, account.getRoles().size());
    }

    @Test
    void grantRole_WhenRoleAlreadyAssigned_ShouldThrowException() {
        // Act & Assert
        assertThrows(RoleAlreadyAssignedException.class,
                () -> account.grantRole(testRole));
    }

    @Test
    void grantRole_WhenNullRole_ShouldThrowException() {
        // Act & Assert
        assertThrows(MissingRoleException.class,
                () -> account.grantRole(null));
    }

    @Test
    void revokeRole_WhenRoleAssigned_ShouldRemoveRole() {
        // Act
        account.revokeRole(testRole);

        // Assert
        assertFalse(account.getRoles().contains(testRole));
        assertTrue(account.getRoles().isEmpty());
    }

    @Test
    void revokeRole_WhenRoleNotAssigned_ShouldThrowException() {
        // Arrange
        Role unassignedRole = new Role("UnassignedRole", "Unassigned role description", 3);

        // Act & Assert
        assertThrows(RoleNotAssignedException.class,
                () -> account.revokeRole(unassignedRole));
    }

    @Test
    void revokeRole_WhenNullRole_ShouldThrowException() {
        // Act & Assert
        assertThrows(MissingRoleException.class,
                () -> account.revokeRole(null));
    }
    //endregion

    //region Equals and HashCode Tests
    @Test
    void equals_WhenSameId_ShouldReturnTrue() {
        // Arrange
        Account sameAccount = new Account(testId, "different@example.com", "differentuser",
                "0987654321", "differentHash", "GOOGLE", false, Set.of(testRole));

        // Act
        boolean equals = account.equals(sameAccount);

        // Assert
        assertTrue(equals);
    }

    @Test
    void equals_WhenDifferentId_ShouldReturnFalse() {
        // Arrange
        Account differentAccount = new Account(UUID.randomUUID(), "test@example.com", "testuser",
                "0939346634", "hashedPassword123", "LOCAL", true, Set.of(testRole));

        // Act
        boolean equals = account.equals(differentAccount);

        // Assert
        assertFalse(equals);
    }

    @Test
    void equals_WhenNull_ShouldReturnFalse() {
        // Act
        boolean equals = account == null;

        // Assert
        assertFalse(equals);
    }

    @Test
    void equals_WhenDifferentClass_ShouldReturnFalse() {
        // Act
        boolean equals = account.equals("Not an Account");

        // Assert
        assertFalse(equals);
    }

    @Test
    void hashCode_WhenSameId_ShouldReturnSameHashCode() {
        // Arrange
        Account sameAccount = new Account(testId, "different@example.com", "differentuser",
                "0987654321", "differentHash", "GOOGLE", false, Set.of(testRole));

        // Act
        int hashCode1 = account.hashCode();
        int hashCode2 = sameAccount.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
    //endregion
}
