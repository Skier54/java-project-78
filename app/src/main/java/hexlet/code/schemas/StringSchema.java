package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    private boolean minLength;
    private boolean contains;
    private int minNumber;
    private String substring;

    public StringSchema() {
        this.minLength = false;
        this.contains = false;
        this.minNumber = 0;
        this.substring = "";
    }

    public StringSchema minLength(int min) {
        this.minLength = true;
        this.minNumber = min;
        return this;
    }

    public StringSchema contains(String str) {
        this.contains = true;
        this.substring = str;
        return this;
    }
    @Override
    public boolean isValid(String value) {
        if (isValidCommon(value) || this.required && value.isEmpty()) {
            return false;
        }
        if (this.minLength && value != null && value.length() < this.minNumber) {
            return false;
        }
        if (this.contains && value != null && !substring.isEmpty() && !value.contains(this.substring)) {
            return false;
        }
        return true;
    }
}
