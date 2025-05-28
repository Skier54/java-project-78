package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean required;

    public BaseSchema() {
        this.required = false;
    }

    public abstract boolean isValid(T value);

    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }

    public boolean isValidCommon(T value) {
        return this.required && value == null;
    }
}
