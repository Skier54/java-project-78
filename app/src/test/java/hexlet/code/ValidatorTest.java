package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ValidatorTest {
    private StringSchema stringSchema;
    private NumberSchema numberSchema;
    private MapSchema mapSchema;
    private final Validator valid = new Validator();

    @BeforeEach
    public void beforeEach() {
        stringSchema = valid.string();
        numberSchema = valid.number();
        mapSchema = valid.map();
    }

    @Test
    public void testIsValid() {
        assertTrue(stringSchema.isValid(""));
        assertTrue(stringSchema.isValid(null));

        assertTrue(numberSchema.isValid(5));
        assertTrue(numberSchema.isValid(null));
        assertTrue(numberSchema.positive().isValid(null));

        assertTrue(mapSchema.isValid(null));
    }

    @Test
    public void testIsValidRequired() {
        assertFalse(stringSchema.required().isValid(""));
        assertFalse(stringSchema.required().isValid(null));
        assertTrue(stringSchema.required().isValid("what does the fox say"));
        assertTrue(stringSchema.required().isValid("hexlet"));

        assertFalse(numberSchema.required().isValid(null));
        assertTrue(numberSchema.isValid(5));

        assertFalse(mapSchema.required().isValid(null));
        assertTrue(mapSchema.isValid(new HashMap<>()));
    }

    @Test
    public void testIsValidMinLength() {
        assertTrue(stringSchema.minLength(10).minLength(4).isValid("Hexlet"));
        assertFalse(stringSchema.minLength(10).isValid("Hexlet"));
    }

    @Test
    public void testIsValidContains() {
        assertTrue(stringSchema.contains("wh").isValid("what does the fox say"));
        assertFalse(stringSchema.contains("whatthe").isValid("what does the fox say"));
    }

    @Test
    public void testIsValidPositive() {
        assertTrue(numberSchema.positive().isValid(5));
        assertFalse(numberSchema.positive().isValid(0));
        assertFalse(numberSchema.positive().isValid(-10));
    }

    @Test
    public void testIsValidRange() {
        assertTrue(numberSchema.range(5, 10).isValid(5));
        assertTrue(numberSchema.range(5, 10).isValid(10));
        assertFalse(numberSchema.range(5, 10).isValid(3));
        assertFalse(numberSchema.range(5, 10).isValid(12));
    }

    @Test
    public void testIsValidSizeof() {
        Map<Object, Object> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");

        assertTrue(mapSchema.sizeof(2).isValid(data));
        assertFalse(mapSchema.sizeof(3).isValid(data));
    }

    @Test
    public void testIsValidSizeofIntStr() {
        Map<Object, Object> dataIntStr = new HashMap<>();
        dataIntStr.put(1, "value1");
        dataIntStr.put(2, "value2");

        assertTrue(mapSchema.sizeof(2).isValid(dataIntStr));
        assertFalse(mapSchema.sizeof(3).isValid(dataIntStr));
    }
}
