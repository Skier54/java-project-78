package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {

    @Override
    public MapSchema<K, V> required() {
        super.required = true;
        addChecks("required", Objects::nonNull);
        return this;
    }

    public MapSchema<K, V> sizeof(int mapSize) {
        addChecks("sizeof", value -> value != null && value.size() == mapSize);
        return this;
    }

    public MapSchema<K, V> shape(Map<K, BaseSchema<V>> schemas) {
        addChecks("shape", value -> {
            if (value == null) {
                return false;
            }
            for (Map.Entry<K, BaseSchema<V>> entry : schemas.entrySet()) {
                K key = entry.getKey();
                BaseSchema<V> schema = entry.getValue();
                V valueForKey = value.get(key);
                if (!schema.isValid(valueForKey)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
