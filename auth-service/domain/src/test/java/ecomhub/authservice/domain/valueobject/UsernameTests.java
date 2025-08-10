package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.username.MissingUsernameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @Test
    void constructor_WithValidUsername_ShouldInitializeCorrectly() {
        // Arrange
        String expectedValue = "JohnDoe";

        // Act
        Username username = new Username(expectedValue);

        // Assert
        assertEquals(expectedValue, username.getValue());
    }

    @Test
    void constructor_WithNullUsername_ShouldThrowMissingUsernameException() {
        // Arrange
        String nullUsername = null;

        // Act & Assert
        assertThrows(MissingUsernameException.class, () -> new Username(nullUsername));
    }

    @Test
    void constructor_WithBlankUsername_ShouldThrowMissingUsernameException() {
        // Arrange
        String blankUsername = "   ";

        // Act & Assert
        assertThrows(MissingUsernameException.class, () -> new Username(blankUsername));
    }

    @Test
    void equals_WithSameValueDifferentCase_ShouldReturnTrue() {
        // Arrange
        Username username1 = new Username("JohnDoe");
        Username username2 = new Username("johndoe");

        // Act
        boolean isEqual = username1.equals(username2);

        // Assert
        assertTrue(isEqual);
    }

    @Test
    void equals_WithDifferentValue_ShouldReturnFalse() {
        // Arrange
        Username username1 = new Username("JohnDoe");
        Username username2 = new Username("JaneDoe");

        // Act
        boolean isEqual = username1.equals(username2);

        // Assert
        assertFalse(isEqual);
    }

    @Test
    void hashCode_WithSameValueDifferentCase_ShouldBeEqual() {
        // Arrange
        Username username1 = new Username("JohnDoe");
        Username username2 = new Username("johndoe");

        // Act
        int hash1 = username1.hashCode();
        int hash2 = username2.hashCode();

        // Assert
        assertEquals(hash1, hash2);
    }
}
