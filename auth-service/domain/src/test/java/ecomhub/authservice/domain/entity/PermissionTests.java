package ecomhub.authservice.domain.entity;

import ecomhub.authservice.common.exception.concrete.permission.MissingIdInPermissionException;
import ecomhub.authservice.domain.valueobject.Name;
import ecomhub.authservice.domain.valueobject.PermissionKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTests {

    private Permission permission;
    private UUID testId;

    @BeforeEach
    void init() {
        testId = UUID.randomUUID();
        permission = new Permission(testId, "Test Permission", "perm.test", "Test permission description");
    }

    //region Constructor Tests
    @Test
    void constructor_WithFullParams_ShouldInitializeCorrectly() {
        // Arrange
        String name = "Full Permission";
        String key = "perm.full";
        String description = "Full permission description";

        // Act
        Permission constructedPermission = new Permission(testId, name, key, description);

        // Assert
        assertEquals(testId, constructedPermission.getId());
        assertEquals(name, constructedPermission.getName().getValue());
        assertEquals(key, constructedPermission.getKey().getValue());
        assertEquals(description, constructedPermission.getDescription().get());
    }

    @Test
    void constructor_WithMinimalParams_ShouldInitializeCorrectly() {
        // Arrange
        String name = "Minimal Permission";
        String key = "perm.minimal";
        String description = "Minimal permission description";

        // Act
        Permission constructedPermission = new Permission(name, key, description);

        // Assert
        assertNotNull(constructedPermission.getId());
        assertEquals(name, constructedPermission.getName().getValue());
        assertEquals(key, constructedPermission.getKey().getValue());
        assertEquals(description, constructedPermission.getDescription().get());
    }

    @Test
    void constructor_WithNullId_ShouldThrowException() {
        // Act & Assert
        assertThrows(MissingIdInPermissionException.class,
                () -> new Permission(null, "Test", "perm.test", "Test description"));
    }

    @Test
    void constructor_WithNullDescription_ShouldInitializeCorrectly() {
        // Act
        Permission constructedPermission = new Permission(testId, "Test", "perm.test", null);

        // Assert
        assertFalse(constructedPermission.getDescription().isPresent());
    }
    //endregion

    //region Getter Tests
    @Test
    void getId_WhenPermissionExists_ShouldReturnId() {
        // Act
        UUID id = permission.getId();

        // Assert
        assertEquals(testId, id);
    }

    @Test
    void getName_WhenPermissionExists_ShouldReturnName() {
        // Act
        Name name = permission.getName();

        // Assert
        assertNotNull(name);
        assertEquals("Test Permission", name.getValue());
    }

    @Test
    void getKey_WhenPermissionExists_ShouldReturnKey() {
        // Act
        PermissionKey key = permission.getKey();

        // Assert
        assertNotNull(key);
        assertEquals("perm.test", key.getValue());
    }

    @Test
    void getDescription_WhenPermissionHasDescription_ShouldReturnDescription() {
        // Act
        Optional<String> description = permission.getDescription();

        // Assert
        assertTrue(description.isPresent());
        assertEquals("Test permission description", description.get());
    }

    @Test
    void getDescription_WhenPermissionHasNullDescription_ShouldReturnEmptyOptional() {
        // Arrange
        Permission permissionWithoutDescription = new Permission(testId, "Test", "perm.test", null);

        // Act
        Optional<String> description = permissionWithoutDescription.getDescription();

        // Assert
        assertFalse(description.isPresent());
    }
    //endregion

    //region Has Key Tests
    @Test
    void hasKey_WhenKeyMatches_ShouldReturnTrue() {
        // Act
        boolean hasKey = permission.hasKey("perm.test");

        // Assert
        assertTrue(hasKey);
    }

    @Test
    void hasKey_WhenKeyDoesNotMatch_ShouldReturnFalse() {
        // Act
        boolean hasKey = permission.hasKey("perm.different");

        // Assert
        assertFalse(hasKey);
    }

    @Test
    void hasKey_WhenKeyIsNull_ShouldReturnFalse() {
        // Act
        boolean hasKey = permission.hasKey(null);

        // Assert
        assertFalse(hasKey);
    }

    @Test
    void hasKey_WhenKeyIsEmpty_ShouldReturnFalse() {
        // Act
        boolean hasKey = permission.hasKey("");

        // Assert
        assertFalse(hasKey);
    }
    //endregion

    //region Equals and HashCode Tests
    @Test
    void equals_WhenSameId_ShouldReturnTrue() {
        // Arrange
        Permission samePermission = new Permission(testId, "Different Name", "perm.different", "Different description");

        // Act
        boolean equals = permission.equals(samePermission);

        // Assert
        assertTrue(equals);
    }

    @Test
    void equals_WhenDifferentId_ShouldReturnFalse() {
        // Arrange
        Permission differentPermission = new Permission(UUID.randomUUID(), "Test Permission", "perm.test", "Test permission description");

        // Act
        boolean equals = permission.equals(differentPermission);

        // Assert
        assertFalse(equals);
    }

    @Test
    void equals_WhenNull_ShouldReturnFalse() {
        // Act
        boolean equals = permission.equals(null);

        // Assert
        assertFalse(equals);
    }

    @Test
    void equals_WhenDifferentClass_ShouldReturnFalse() {
        // Act
        boolean equals = permission.equals("Not a Permission");

        // Assert
        assertFalse(equals);
    }

    @Test
    void hashCode_WhenSameId_ShouldReturnSameHashCode() {
        // Arrange
        Permission samePermission = new Permission(testId, "Different Name", "perm.different", "Different description");

        // Act
        int hashCode1 = permission.hashCode();
        int hashCode2 = samePermission.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
    //endregion

    //region Edge Cases Tests
    @Test
    void constructor_WithEmptyName_ShouldInitializeCorrectly() {
        // Act
        Permission constructedPermission = new Permission(testId, "", "perm.empty", "Empty name permission");

        // Assert
        assertEquals("", constructedPermission.getName().getValue());
    }

    @Test
    void constructor_WithEmptyKey_ShouldInitializeCorrectly() {
        // Act
        Permission constructedPermission = new Permission(testId, "Empty Key Permission", "", "Empty key permission");

        // Assert
        assertEquals("", constructedPermission.getKey().getValue());
    }

    @Test
    void constructor_WithSpecialCharacters_ShouldInitializeCorrectly() {
        // Arrange
        String nameWithSpecialChars = "Permission with @#$%^&*()";
        String keyWithSpecialChars = "perm.special@#$%^&*()";

        // Act
        Permission constructedPermission = new Permission(testId, nameWithSpecialChars, keyWithSpecialChars, "Special chars permission");

        // Assert
        assertEquals(nameWithSpecialChars, constructedPermission.getName().getValue());
        assertEquals(keyWithSpecialChars, constructedPermission.getKey().getValue());
    }
    //endregion
} 