package ecomhub.authservice.domain.entity;

import ecomhub.authservice.common.exception.concrete.role.MissingIdInRoleException;
import ecomhub.authservice.common.exception.concrete.role.MissingPermissionException;
import ecomhub.authservice.common.exception.concrete.role.PermissionAlreadyAssignedException;
import ecomhub.authservice.common.exception.concrete.role.PermissionNotAssignedException;
import ecomhub.authservice.domain.valueobject.Level;
import ecomhub.authservice.domain.valueobject.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    //region Has Permission Tests
    @Test
    void hasPermission_WhenPermissionExists_ShouldReturnTrue() {
        // Arrange
        Permission permission = new Permission("Test permission", "perm.test", null);
        role.grantPermission(permission);

        // Act
        boolean hasPermission = role.hasPermission("perm.test");

        // Assert
        assertTrue(hasPermission);
    }

    @Test
    void hasPermission_WhenPermissionNotExists_ShouldReturnFalse() {
        // Act
        boolean hasPermission = role.hasPermission("perm.nonexistent");

        // Assert
        assertFalse(hasPermission);
    }
    //endregion

    //region Active State Tests
    @Test
    void deactivate_WhenRoleIsActive_ShouldSetActiveToFalse() {
        // Arrange
        assertTrue(role.isActive());

        // Act
        role.deactivate();

        // Assert
        assertFalse(role.isActive());
    }
    //endregion

    //region Update Tests
    @Test
    void updateName_WhenValidName_ShouldUpdateName() {
        // Arrange
        String newName = "UpdatedRoleName";

        // Act
        role.updateName(newName);

        // Assert
        assertEquals(newName, role.getName().getValue());
    }

    @Test
    void updateLevel_WhenValidLevel_ShouldUpdateLevel() {
        // Arrange
        int newLevel = 10;

        // Act
        role.updateLevel(newLevel);

        // Assert
        assertEquals(newLevel, role.getLevel().value());
    }

    @Test
    void updateDescription_WhenValidDescription_ShouldUpdateDescription() {
        // Arrange
        String newDescription = "Updated description";

        // Act
        role.updateDescription(newDescription);

        // Assert
        assertEquals(newDescription, role.getDescription().get());
    }
    //endregion

    //region Getter Tests
    @Test
    void getId_WhenRoleExists_ShouldReturnId() {
        // Act
        UUID id = role.getId();

        // Assert
        assertNotNull(id);
    }

    @Test
    void getName_WhenRoleExists_ShouldReturnName() {
        // Act
        Name name = role.getName();

        // Assert
        assertNotNull(name);
        assertEquals("TestRole", name.getValue());
    }

    @Test
    void getDescription_WhenRoleExists_ShouldReturnDescription() {
        // Act
        String description = role.getDescription().orElse(null);

        // Assert
        assertEquals("Test description", description);
    }

    @Test
    void getPermissions_WhenRoleHasNoPermissions_ShouldReturnEmptySet() {
        // Act
        Set<Permission> permissions = role.getPermissions();

        // Assert
        assertTrue(permissions.isEmpty());
    }

    @Test
    void getPermissions_WhenRoleHasPermissions_ShouldReturnCopyOfPermissions() {
        // Arrange
        Permission permission = new Permission("Test permission", "perm.test", null);
        role.grantPermission(permission);

        // Act
        Set<Permission> permissions = role.getPermissions();

        // Assert
        assertEquals(1, permissions.size());
        assertTrue(permissions.contains(permission));
    }

    @Test
    void isActive_WhenRoleIsActive_ShouldReturnTrue() {
        // Act
        boolean isActive = role.isActive();

        // Assert
        assertTrue(isActive);
    }

    @Test
    void getLevel_WhenRoleExists_ShouldReturnLevel() {
        // Act
        Level level = role.getLevel();

        // Assert
        assertNotNull(level);
        assertEquals(5, level.value());
    }
    //endregion
}
