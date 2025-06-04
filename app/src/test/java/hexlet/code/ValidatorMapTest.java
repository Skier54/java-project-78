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


class ValidatorMapTest {
    private StringSchema stringSchema;
    private NumberSchema numberSchema;
    private MapSchema mapSchema;
    private final Validator valid = new Validator();

    @BeforeEach
    void beforeEach() {
        stringSchema = valid.string();
        numberSchema = valid.number();
        mapSchema = valid.map();

        assertNotNull(stringSchema);
        assertNotNull(numberSchema);
        assertNotNull(mapSchema);
    }

    @Test
    void testIsValid() {
        assertTrue(mapSchema.isValid(null));
    }

    @Test
    void testIsValidRequired() {
        assertFalse(mapSchema.required().isValid(null));
        assertTrue(mapSchema.isValid(new HashMap<>()));
    }

    @Test
    void testIsValidSizeof() {
        Map<Object, Object> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");

        assertTrue(mapSchema.sizeof(2).isValid(data));
        assertFalse(mapSchema.sizeof(3).isValid(data));
    }

    @Test
    void testIsValidSizeofIntStr() {
        Map<Object, Object> dataIntStr = new HashMap<>();
        dataIntStr.put(1, "value1");
        dataIntStr.put(2, "value2");

        assertTrue(mapSchema.sizeof(2).isValid(dataIntStr));
        assertFalse(mapSchema.sizeof(3).isValid(dataIntStr));
    }

    @Test
    void testMapShapeValidationStr() {
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
    void testMapShapeValidationInt() {
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
