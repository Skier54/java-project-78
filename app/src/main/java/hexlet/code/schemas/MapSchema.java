package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map> {
    private boolean sizeof;
    private int mapSize;

    public MapSchema() {
        this.sizeof = false;
        this.mapSize = 0;
    }

    public MapSchema sizeof(int size) {
        this.sizeof = true;
        this.mapSize = size;
        return this;
    }

    @Override
    public boolean isValid(Map value) {
        if (isValidCommon(value)) {
            return  false;
        }
        if (this.sizeof && value.size() != this.mapSize) {
            return false;
        }
        return true;
    }
}
