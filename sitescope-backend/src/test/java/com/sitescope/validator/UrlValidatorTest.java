package com.sitescope.validator;

import com.sitescope.exception.InvalidURLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlValidatorTest {

    private UrlValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UrlValidator();
    }

    @Test
    void testValidHttpUrl() {
        assertDoesNotThrow(() -> validator.validate("http://example.com"));
    }

    @Test
    void testValidHttpsUrl() {
        assertDoesNotThrow(() -> validator.validate("https://spring.io/projects/spring-boot"));
    }

    @Test
    void testNullUrlThrowsException() {
        assertThrows(InvalidURLException.class, () -> validator.validate(null));
    }

    @Test
    void testBlankUrlThrowsException() {
        assertThrows(InvalidURLException.class, () -> validator.validate("   "));
    }

    @Test
    void testUnsupportedProtocolFtpThrowsException() {
        assertThrows(InvalidURLException.class, () -> validator.validate("ftp://example.com"));
    }

    @Test
    void testLocalhostThrowsException() {
        assertThrows(InvalidURLException.class, () -> validator.validate("http://localhost:8080"));
    }

    @Test
    void testInvalidFormatThrowsException() {
        assertThrows(InvalidURLException.class, () -> validator.validate("not-a-url"));
    }
}
