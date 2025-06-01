package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Integer> {
    private boolean positive;
    private boolean range;
    private int minNumber;
    private int maxNumber;

    public NumberSchema() {
        this.positive = false;
        this.range = false;
        this.minNumber = 0;
        this.maxNumber = 0;
    }

    @Override
    public NumberSchema required() {
        super.required = true;
        return this;
    }

    public NumberSchema positive() {
        this.positive = true;
        return this;
    }

    public NumberSchema range(int min, int max) {
        this.range = true;
        if (min > max) {
            throw new IllegalArgumentException("Min должен быть меньше или равен max");
        }
        this.minNumber = min;
        this.maxNumber = max;
        return this;
    }

    @Override
    public boolean isValid(Integer value) {
        if (isValidCommon(value)) {
            return  false;
        }
        if (this.positive && value != null && value <= 0) {
            return false;
        }
        if (this.range && value != null && (value < this.minNumber || value > this.maxNumber)) {
            return false;
        }
        return true;
    }
}
