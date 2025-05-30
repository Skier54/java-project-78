package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public class MapSchema extends BaseSchema<Map<Object, Object>> {
    private boolean sizeof;
    private int mapSize;
    private boolean shape;
    private Map<Object, BaseSchema<Object>> schemas;

    public MapSchema() {
        this.sizeof = false;
        this.mapSize = 0;
        this.shape = false;
        this.schemas = new HashMap<>();
    }

    public MapSchema sizeof(int size) {
        this.sizeof = true;
        this.mapSize = size;
        return this;
    }

    public MapSchema shape(Map<Object, BaseSchema<Object>> providedSchemas) {
        this.shape = true;
        this.schemas = providedSchemas;
        return this;
    }

    @Override
    public boolean isValid(Map<Object, Object> value) {
        if (isValidCommon(value)) {
            return false;
        }
        if (this.sizeof && value != null && value.size() != this.mapSize) {
            return false;
        }
        if (this.shape && value != null && schemas != null) {
            for (var entry : schemas.entrySet()) {
                Object key = entry.getKey();
                BaseSchema<Object> schema = entry.getValue();
                Object valueForKey = value.get(key);
                if (!schema.isValid(valueForKey)) {
                    return false;
                }
            }
        }
        return true;
    }
}
