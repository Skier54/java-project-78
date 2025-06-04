package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    @Override
    public StringSchema required() {
        super.required = true;
        addChecks("required", value -> !value.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        addChecks("minLength", value -> value.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        addChecks("contains", value -> !substring.isEmpty() && value.contains(substring));
        return this;
    }
}
