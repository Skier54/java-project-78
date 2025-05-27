package hexlet.code.schemas;

public class NumberSchema {
    private boolean required;
    private boolean positive;
    private boolean range;
    private int minNumber;
    private int maxNumber;

    public NumberSchema() {
        this.required = false;
        this.positive = false;
        this.range = false;
        this.minNumber = 0;
        this.maxNumber = 0;
    }

    public NumberSchema required() {
        this.required = true;
        return this;
    }

    public NumberSchema positive() {
        this.positive = true;
        return this;
    }

    public NumberSchema range(int min, int max) {
        this.range = true;
        this.minNumber = min;
        this.maxNumber = max;
        return this;
    }

    public boolean isValid(Integer value) {
        if (this.required && value == null) {
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
