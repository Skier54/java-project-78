package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {
    private boolean minLengthBoolean;
    private boolean contains;
    private int minLength;
    private String substring;

    public StringSchema() {
        this.minLengthBoolean = false;
        this.contains = false;
        this.minLength = 0;
        this.substring = "";
    }

    @Override
    public StringSchema required() {
        super.required = true;
        return this;
    }

    public StringSchema minLength(int min) {
        this.minLengthBoolean = true;
        this.minLength = min;
        return this;
    }

    public StringSchema contains(String str) {
        this.contains = true;
        this.substring = str;
        return this;
    }
    @Override
    public boolean isValid(String value) {
        if (this.required && (isValidCommon(value) || value.isEmpty())) {
            return false;
        }
        if (this.minLengthBoolean && value != null && value.length() < minLength) {
            return false;
        }
        if (this.contains && value != null && !substring.isEmpty() && !value.contains(this.substring)) {
            return false;
        }
        return true;
    }
}
