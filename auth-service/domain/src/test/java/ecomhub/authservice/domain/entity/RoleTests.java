package ecomhub.authservice.domain.entity;

import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.concrete.role.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleTests {

    private Role role;

    @BeforeEach
    void init() {
        // Mặc định role level = 5
        role = new Role("TestRole", "Test description", 5);
    }

    //region Constructor Tests
    @Test
    void constructor_WithFullParams_ShouldInitializeCorrectly() {
        // Arrange
        Permission permission = new Permission("Test permission", "perm.test", null);
        Set<Permission> permissions = Set.of(permission);

        // Act
        Role constructedRole = new Role(UUID.randomUUID(), "TestRole", "Test description", permissions, true, 5);

        // Assert
        assertNotNull(constructedRole.getId());
        assertEquals("TestRole", constructedRole.getName().getValue());
        assertEquals("Test description", constructedRole.getDescription().get());
        assertTrue(constructedRole.isActive());
        assertEquals(5, constructedRole.getLevel().value());
        assertEquals(1, constructedRole.getPermissions().size());
        assertTrue(constructedRole.getPermissions().contains(permission));
    }

    @Test
    void constructor_WithMinimalParams_ShouldInitializeCorrectly() {
        // Act
        Role constructedRole = new Role("NewRole", "New role description", 3);

        // Assert
        assertNotNull(constructedRole.getId());
        assertEquals("NewRole", constructedRole.getName().getValue());
        assertEquals("New role description", constructedRole.getDescription().get());
        assertTrue(constructedRole.isActive());
        assertEquals(3, constructedRole.getLevel().value());
        assertTrue(constructedRole.getPermissions().isEmpty());
    }

    @Test
    void constructor_WithNullId_ShouldThrowException() {
        // Arrange
        Permission permission = new Permission("Test permission", "perm.test", null);
        Set<Permission> permissions = Set.of(permission);

        // Act & Assert
        assertThrows(MissingIdInRoleException.class,
                () -> new Role(null, "Test", "Test description", permissions, true, 5));
    }

    @Test
    void constructor_WithEmptyPermissions_ShouldThrowException() {
        // Act & Assert
        assertThrows(NoPermissionAssignedException.class,
                () -> new Role(UUID.randomUUID(), "Test", "Test description", new HashSet<>(), true, 5));
    }
    //endregion

    //region Grant Permission Tests
    @Test
    void grantPermission_WhenNewPermission_ShouldAddPermission() {
        // Arrange
        Permission permission = new Permission("Add permission", "perm.add", null);

        // Act
        role.grantPermission(permission);

        // Assert
        assertTrue(role.getPermissions().contains(permission));
        assertEquals(1, role.getPermissions().size());
    }

    @Test
    void grantPermission_WhenPermissionAlreadyAssigned_ShouldThrowException() {
        // Arrange
        Permission permission = new Permission("Add permission", "perm.add", null);
        role.grantPermission(permission);

        // Act & Assert
        assertThrows(PermissionAlreadyAssignedException.class,
                () -> role.grantPermission(permission));
    }

    @Test
    void grantPermission_WhenNullPermission_ShouldThrowException() {
        // Act & Assert
        assertThrows(MissingPermissionException.class,
                () -> role.grantPermission(null));
    }
    //endregion

    //region Revoke Permission Tests
    @Test
    void revokePermission_WhenPermissionAssigned_ShouldRemovePermission() {
        // Arrange
        Permission permission = new Permission("Remove permission", "perm.remove", null);
        role.grantPermission(permission);

        // Act
        role.revokePermission(permission);

        // Assert
        assertFalse(role.getPermissions().contains(permission));
    }

    @Test
    void revokePermission_WhenPermissionNotAssigned_ShouldThrowException() {
        // Arrange
        Permission permission = new Permission("Remove permission", "perm.remove", null);

        // Act & Assert
        assertThrows(PermissionNotAssignedException.class,
                () -> role.revokePermission(permission));
    }

    @Test
    void revokePermission_WhenNullPermission_ShouldThrowException() {
        // Act & Assert
        assertThrows(MissingPermissionException.class,
                () -> role.revokePermission(null));
    }
    //endregion

    //region Set Active Tests
    @Test
    void setActive_WhenRequesterHasPermissionAndCanModify_ShouldChangeActiveStatus() {
        // Arrange
        Permission deletePermission = new Permission("Delete role", "role.delete", null);
        Role requester = new Role("Requester", "Can modify", 7);
        requester.grantPermission(deletePermission);

        // Act
        role.deactivateBy(false, requester);

        // Assert
        assertFalse(role.isActive());
    }

    @Test
    void setActive_WhenRequesterLacksDeletePermission_ShouldDoNothing() {
        // Arrange
        Permission editPermission = new Permission("Edit role", "role.edit", null);
        Role requester = new Role("Requester", "Cannot delete", 6);
        requester.grantPermission(editPermission);

        // Act & Assert
        role.deactivateBy(false, requester);

        assertTrue(role.isActive());
    }

    @Test
    void setActive_WhenRequesterLevelTooHigh_ShouldDoNothing() {
        // Arrange
        Permission deletePermission = new Permission("Delete role", "role.delete", null);
        Role requester = new Role("Requester", "High level", 4);
        requester.grantPermission(deletePermission);

        // Act & Assert
        role.deactivateBy(false, requester);

        assertTrue(role.isActive());
    }
    //endregion
}
