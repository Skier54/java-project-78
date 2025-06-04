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

    /**
     * Добавляет новую проверку в схему валидации.
     *
     * @param name имя проверки
     * @param validate предикат для проверки
     */
    protected final void addChecks(String name, Predicate<T> validate) {
        checks.put(name, validate);
    }

    /**
     * Проверяет значение на соответствие всем добавленным проверкам.
     *
     * @return true если значение валидно, иначе false
     */
    public abstract BaseSchema<T> required();

    /**
     * Проверяет значение на соответствие всем добавленным проверкам.
     *
     * @param value значение для валидации
     * @return true если значение валидно, иначе false
     */
    public final boolean isValid(T value) {
        if (!required && value == null) {
            return true;
        }

        for (Predicate<T> check : checks.values()) {
            if (value == null || !check.test(value)) {
                return false;
            }
        }
        return true;
    }
}
