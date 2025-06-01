package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private boolean sizeof;
    private int mapSize;
    private boolean shape;
    private Map<K, BaseSchema<V>> schemas;

    public MapSchema() {
        this.sizeof = false;
        this.mapSize = 0;
        this.shape = false;
        this.schemas = new HashMap<>();
    }

    @Override
    public MapSchema required() {
        super.required = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        this.sizeof = true;
        this.mapSize = size;
        return this;
    }

    public MapSchema shape(Map<K, BaseSchema<V>> providedSchemas) {
        this.shape = true;
        this.schemas = new HashMap<>(providedSchemas);
        return this;
    }

    @Override
    public boolean isValid(Map<K, V> value) {
        if (isValidCommon(value)) {
            return false;
        }
        if (this.sizeof && value != null && value.size() != this.mapSize) {
            return false;
        }
        if (this.shape && value != null) {
            for (Map.Entry<K, BaseSchema<V>> entry: schemas.entrySet()) {
                K key = entry.getKey();
                BaseSchema<V> schema = entry.getValue();
                V valueForKey = value.get(key);
                if (!schema.isValid(valueForKey)) {
                    return false;
                }
            }
        }
        return true;
    }
}
