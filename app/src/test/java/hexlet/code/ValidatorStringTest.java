package hexlet.code;

import hexlet.code.schemas.StringSchema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ValidatorStringTest {
    private StringSchema stringSchema;
    private final Validator valid = new Validator();

    @BeforeEach
    void beforeEach() {
        stringSchema = valid.string();
        assertNotNull(stringSchema);
    }

    @Test
    void testIsValid() {
        assertTrue(stringSchema.isValid(""));
        assertTrue(stringSchema.isValid(null));
    }

    @Test
    void testIsValidRequired() {
        assertFalse(stringSchema.required().isValid(""));
        assertFalse(stringSchema.required().isValid(null));
        assertTrue(stringSchema.required().isValid("what does the fox say"));
        assertTrue(stringSchema.required().isValid("hexlet"));
    }

    @Test
    void testIsValidMinLength() {
        assertTrue(stringSchema.minLength(10).minLength(4).isValid("Hexlet"));
        assertFalse(stringSchema.required().minLength(10).isValid("Hexlet"));
    }

    @Test
    void testIsValidContains() {
        assertTrue(stringSchema.contains("wh").isValid("what does the fox say"));
        assertFalse(stringSchema.contains("whatthe").isValid("what does the fox say"));
    }

    @Test
    void testStringSchemaComplexConditions() {
        assertTrue(stringSchema.required().minLength(5).contains("hello").isValid("hello world"));
        assertFalse(stringSchema.required().minLength(15).contains("hello").isValid("hello world"));
        assertFalse(stringSchema.required().minLength(5).contains("world").isValid("hello"));
    }
}
