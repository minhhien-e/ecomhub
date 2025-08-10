package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.password.MissingPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordTests {

    @Test
    void constructor_WithHashedValue_ShouldInitializeCorrectly() {
        String hashedValue = "hashedPassword123";
        Password password = new Password(hashedValue);
        assertEquals(hashedValue, password.getHashedValue());
    }

    @Test
    void constructor_WithNullHashedValue_ShouldThrowException() {
        assertThrows(MissingPasswordException.class, () -> new Password(null));
    }

    @Test
    void constructor_WithEmptyHashedValue_ShouldThrowException() {
        assertThrows(MissingPasswordException.class, () -> new Password(""));
    }

    @Test
    void constructor_WithBlankHashedValue_ShouldThrowException() {
        assertThrows(MissingPasswordException.class, () -> new Password("   "));
    }

    @Test
    void constructor_WithSpecialCharactersInHashedValue_ShouldInitializeCorrectly() {
        String hashedValueWithSpecialChars = "hash@#$%^&*()_+-=[]{}|;':\",./<>?";
        Password password = new Password(hashedValueWithSpecialChars);
        assertEquals(hashedValueWithSpecialChars, password.getHashedValue());
    }

    @Test
    void constructor_WithVeryLongHashedValue_ShouldInitializeCorrectly() {
        String longHashedValue = "veryLongHashedPasswordValueThatExceedsNormalLength".repeat(5);
        Password password = new Password(longHashedValue);
        assertEquals(longHashedValue, password.getHashedValue());
    }
}
