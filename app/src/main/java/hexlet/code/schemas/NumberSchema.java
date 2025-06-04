package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {

    @Override
    public NumberSchema required() {
        super.required = true;
        addChecks("required", Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addChecks("positive", value -> value > 0);
        return this;
    }

    public NumberSchema range(int minNumber, int maxNumber) {
        addChecks("range", value -> (value >= minNumber && value <= maxNumber));
        return this;
    }
}
