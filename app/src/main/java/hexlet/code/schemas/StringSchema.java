package hexlet.code.schemas;

public class StringSchema {
    private boolean required;
    private int minLength;
    private String substring;

    public StringSchema() {
        this.required = false;
        this.minLength = 0;
        this.substring = "";
    }

    public StringSchema required() {
        this.required = true;
        return this;
    }

    public StringSchema minLength(int min) {
        this.minLength = min;
        return this;
    }

    public StringSchema contains(String str) {
        this.substring = str;
        return this;
    }

    public boolean isValid(String value) {
        if (this.required && (value == null || value.isEmpty())) {
            return false;
        }
        if (value.length() < this.minLength) {
            return false;
        }
        if (!substring.isEmpty() && !value.contains(this.substring)) {
            return false;
        }
        return true;
    }
}
