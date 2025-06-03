package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Базовый класс для схем валидации.
 * Представляет собой основу для создания различных схем валидации данных.
 *
 * @param <T> тип данных, которые будут валидироваться
 */
public abstract class BaseSchema<T> {
    protected Map<String, Predicate<T>> checks = new LinkedHashMap<>();
    protected boolean required = false;

    protected final void addChecks(String name, Predicate<T> validate) {
        checks.put(name, validate);
    }

    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }

    public final boolean isValid(T value) {
        if (!required && value == null) {
            return true;
        }
        for (Predicate<T> check : checks.values()) {
            if (!check.test(value)) {
                return false;
            }
        }
        return true;
    }
}
