package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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

        assertNotNull(stringSchema);
        assertNotNull(numberSchema);
        assertNotNull(mapSchema);

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
        assertFalse(stringSchema.required().minLength(10).isValid("Hexlet"));
    }

    @Test
    public void testIsValidContains() {
        assertTrue(stringSchema.contains("wh").isValid("what does the fox say"));
        assertFalse(stringSchema.contains("whatthe").isValid("what does the fox say"));
    }

    @Test
    public void testStringSchemaComplexConditions() {
        assertTrue(stringSchema.required().minLength(5).contains("hello").isValid("hello world"));
        assertFalse(stringSchema.required().minLength(15).contains("hello").isValid("hello world"));
        assertFalse(stringSchema.required().minLength(5).contains("world").isValid("hello"));
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

    @Test
    public void testMapShapeValidationStr() {
        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", stringSchema.required());
        schemas.put("lastName", stringSchema.required().minLength(2));
        mapSchema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(mapSchema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(mapSchema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(mapSchema.isValid(human3));

        Map<String, String> human4 = new HashMap<>();
        human4.put("firstName", "Anna");
        human4.put("lastName", "");
        assertFalse(mapSchema.isValid(human4));
    }

    @Test
    public void testMapShapeValidationInt() {
        Map<String, BaseSchema<Integer>> schemas = new HashMap<>();
        schemas.put("height", numberSchema.required().positive());
        schemas.put("Age", numberSchema.required().positive().range(18, 100));
        mapSchema.shape(schemas);

        Map<String, Integer> human1 = new HashMap<>();
        human1.put("height", 70);
        human1.put("Age", 35);
        assertTrue(mapSchema.isValid(human1));

        Map<String, Integer> human2 = new HashMap<>();
        human2.put("height", -10);
        human2.put("Age", 35);
        assertFalse(mapSchema.isValid(human2));

        Map<String, Integer> human3 = new HashMap<>();
        human3.put("height", 10);
        human3.put("Age", -35);
        assertFalse(mapSchema.isValid(human3));
    }
}
