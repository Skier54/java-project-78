package hexlet.code;

import hexlet.code.schemas.NumberSchema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ValidatorNumberTest {
    private NumberSchema numberSchema;
    private final Validator valid = new Validator();

    @BeforeEach
    void beforeEach() {
        numberSchema = valid.number();
        assertNotNull(numberSchema);
    }

    @Test
    void testIsValid() {
        assertTrue(numberSchema.isValid(5));
        assertTrue(numberSchema.isValid(null));
        assertTrue(numberSchema.positive().isValid(null));
    }

    @Test
    void testIsValidRequired() {
        assertFalse(numberSchema.required().isValid(null));
        assertTrue(numberSchema.isValid(5));
        assertTrue(numberSchema.isValid(-5));
    }

    @Test
    void testIsValidPositive() {
        assertTrue(numberSchema.positive().isValid(5));
        assertFalse(numberSchema.positive().isValid(0));
        assertFalse(numberSchema.positive().isValid(-10));
    }

    @Test
    void testIsValidRange() {
        assertTrue(numberSchema.range(5, 10).isValid(5));
        assertTrue(numberSchema.range(5, 10).isValid(10));
        assertFalse(numberSchema.range(5, 10).isValid(3));
        assertFalse(numberSchema.range(5, 10).isValid(12));
    }
}
