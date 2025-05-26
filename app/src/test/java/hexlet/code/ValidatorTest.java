package hexlet.code;

import hexlet.code.schemas.StringSchema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ValidatorTest {
    private StringSchema schema;
    private final Validator valid = new Validator();

    @BeforeEach
    public void beforeEach() {
        schema = valid.string();
    }

    @Test
    public void testIsValid() {
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
    }

    @Test
    public void testIsValidRequired() {
        assertFalse(schema.required().isValid(""));
        assertFalse(schema.required().isValid(null));
        assertTrue(schema.isValid("what does the fox say"));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    public void testIsValidMinLength() {
        assertTrue(schema.minLength(10).minLength(4).isValid("Hexlet"));
        assertFalse(schema.minLength(10).isValid("Hexlet"));
    }

    @Test
    public void testIsValidContains() {
        assertTrue(schema.contains("wh").isValid("what does the fox say"));
        assertFalse(schema.contains("whatthe").isValid("what does the fox say"));
    }
}
