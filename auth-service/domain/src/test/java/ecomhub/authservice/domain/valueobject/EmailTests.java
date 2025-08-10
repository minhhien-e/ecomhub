package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.email.InvalidEmailFormatException;
import ecomhub.authservice.common.exception.concrete.valueobject.email.MissingEmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTests {

    //region Constructor Tests
    @Test
    void constructor_WithValidEmail_ShouldInitializeCorrectly() {
        // Arrange
        String validEmail = "test@example.com";
        String domainName = "tài khoản";

        // Act
        Email email = new Email(validEmail, domainName);

        // Assert
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void constructor_WithValidEmailUpperCase_ShouldInitializeCorrectly() {
        // Arrange
        String validEmail = "TEST@EXAMPLE.COM";
        String domainName = "tài khoản";

        // Act
        Email email = new Email(validEmail, domainName);

        // Assert
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void constructor_WithValidEmailWithDots_ShouldInitializeCorrectly() {
        // Arrange
        String validEmail = "test.user@example.co.uk";
        String domainName = "tài khoản";

        // Act
        Email email = new Email(validEmail, domainName);

        // Assert
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void constructor_WithValidEmailWithUnderscores_ShouldInitializeCorrectly() {
        // Arrange
        String validEmail = "test_user@example.com";
        String domainName = "tài khoản";

        // Act
        Email email = new Email(validEmail, domainName);

        // Assert
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void constructor_WithValidEmailWithHyphens_ShouldInitializeCorrectly() {
        // Arrange
        String validEmail = "test-user@example.com";
        String domainName = "tài khoản";

        // Act
        Email email = new Email(validEmail, domainName);

        // Assert
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void constructor_WithValidEmailWithPlus_ShouldInitializeCorrectly() {
        // Arrange
        String validEmail = "test+tag@example.com";
        String domainName = "tài khoản";

        // Act
        Email email = new Email(validEmail, domainName);

        // Assert
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void constructor_WithValidEmailWithPercent_ShouldInitializeCorrectly() {
        // Arrange
        String validEmail = "test%tag@example.com";
        String domainName = "tài khoản";

        // Act
        Email email = new Email(validEmail, domainName);

        // Assert
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void constructor_WithNullEmail_ShouldThrowException() {
        // Arrange
        String domainName = "tài khoản";

        // Act & Assert
        assertThrows(MissingEmailException.class, () -> new Email(null, domainName));
    }

    @Test
    void constructor_WithEmptyEmail_ShouldThrowException() {
        // Arrange
        String emptyEmail = "";
        String domainName = "tài khoản";

        // Act & Assert
        assertThrows(MissingEmailException.class, () -> new Email(emptyEmail, domainName));
    }

    @Test
    void constructor_WithBlankEmail_ShouldThrowException() {
        // Arrange
        String blankEmail = "   ";
        String domainName = "tài khoản";

        // Act & Assert
        assertThrows(MissingEmailException.class, () -> new Email(blankEmail, domainName));
    }

    @Test
    void constructor_WithInvalidEmailNoAtSymbol_ShouldThrowException() {
        // Arrange
        String invalidEmail = "testexample.com";
        String domainName = "tài khoản";

        // Act & Assert
        assertThrows(InvalidEmailFormatException.class, () -> new Email(invalidEmail, domainName));
    }

    @Test
    void constructor_WithInvalidEmailNoDomain_ShouldThrowException() {
        // Arrange
        String invalidEmail = "test@";
        String domainName = "tài khoản";

        // Act & Assert
        assertThrows(InvalidEmailFormatException.class, () -> new Email(invalidEmail, domainName));
    }

    @Test
    void constructor_WithInvalidEmailNoLocalPart_ShouldThrowException() {
        // Arrange
        String invalidEmail = "@example.com";
        String domainName = "tài khoản";

        // Act & Assert
        assertThrows(InvalidEmailFormatException.class, () -> new Email(invalidEmail, domainName));
    }

    @Test
    void constructor_WithInvalidEmailMultipleAtSymbols_ShouldThrowException() {
        // Arrange
        String invalidEmail = "test@@example.com";
        String domainName = "tài khoản";

        // Act & Assert
        assertThrows(InvalidEmailFormatException.class, () -> new Email(invalidEmail, domainName));
    }

    @Test
    void constructor_WithInvalidEmailInvalidCharacters_ShouldThrowException() {
        // Arrange
        String invalidEmail = "test<tag>@example.com";
        String domainName = "tài khoản";

        // Act & Assert
        assertThrows(InvalidEmailFormatException.class, () -> new Email(invalidEmail, domainName));
    }
    //endregion

    //region Getter Tests
    @Test
    void getValue_WhenEmailExists_ShouldReturnEmail() {
        // Arrange
        String validEmail = "test@example.com";
        Email email = new Email(validEmail, "tài khoản");

        // Act
        String value = email.getValue();

        // Assert
        assertEquals(validEmail, value);
    }
    //endregion

    //region Equals Tests
    @Test
    void equals_WhenSameEmail_ShouldReturnTrue() {
        // Arrange
        Email email1 = new Email("test@example.com", "tài khoản");
        Email email2 = new Email("test@example.com", "tài khoản");

        // Act
        boolean equals = email1.equals(email2);

        // Assert
        assertTrue(equals);
    }

    @Test
    void equals_WhenSameEmailDifferentCase_ShouldReturnTrue() {
        // Arrange
        Email email1 = new Email("test@example.com", "tài khoản");
        Email email2 = new Email("TEST@EXAMPLE.COM", "tài khoản");

        // Act
        boolean equals = email1.equals(email2);

        // Assert
        assertTrue(equals);
    }

    @Test
    void equals_WhenDifferentEmail_ShouldReturnFalse() {
        // Arrange
        Email email1 = new Email("test@example.com", "tài khoản");
        Email email2 = new Email("different@example.com", "tài khoản");

        // Act
        boolean equals = email1.equals(email2);

        // Assert
        assertFalse(equals);
    }

    @Test
    void equals_WhenNull_ShouldReturnFalse() {
        // Arrange
        Email email = new Email("test@example.com", "tài khoản");

        // Act
        boolean equals = email.equals(null);

        // Assert
        assertFalse(equals);
    }

    @Test
    void equals_WhenDifferentClass_ShouldReturnFalse() {
        // Arrange
        Email email = new Email("test@example.com", "tài khoản");

        // Act
        boolean equals = email.equals("Not an Email");

        // Assert
        assertFalse(equals);
    }
    //endregion

    //region HashCode Tests
    @Test
    void hashCode_WhenSameEmail_ShouldReturnSameHashCode() {
        // Arrange
        Email email1 = new Email("test@example.com", "tài khoản");
        Email email2 = new Email("test@example.com", "tài khoản");

        // Act
        int hashCode1 = email1.hashCode();
        int hashCode2 = email2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void hashCode_WhenSameEmailDifferentCase_ShouldReturnSameHashCode() {
        // Arrange
        Email email1 = new Email("test@example.com", "tài khoản");
        Email email2 = new Email("TEST@EXAMPLE.COM", "tài khoản");

        // Act
        int hashCode1 = email1.hashCode();
        int hashCode2 = email2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void hashCode_WhenDifferentEmail_ShouldReturnDifferentHashCode() {
        // Arrange
        Email email1 = new Email("test@example.com", "tài khoản");
        Email email2 = new Email("different@example.com", "tài khoản");

        // Act
        int hashCode1 = email1.hashCode();
        int hashCode2 = email2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }
    //endregion

    //region Edge Cases Tests
    @Test
    void constructor_WithVeryLongEmail_ShouldInitializeCorrectly() {
        // Arrange
        String longEmail = "very.long.email.address.with.many.parts.and.subdomains@very.long.domain.name.with.many.subdomains.com";
        String domainName = "tài khoản";

        // Act
        Email email = new Email(longEmail, domainName);

        // Assert
        assertEquals(longEmail, email.getValue());
    }

    @Test
    void constructor_WithEmailWithNumbers_ShouldInitializeCorrectly() {
        // Arrange
        String emailWithNumbers = "test123@example456.com";
        String domainName = "tài khoản";

        // Act
        Email email = new Email(emailWithNumbers, domainName);

        // Assert
        assertEquals(emailWithNumbers, email.getValue());
    }
    //endregion
} 