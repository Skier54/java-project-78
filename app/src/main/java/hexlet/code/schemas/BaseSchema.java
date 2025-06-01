package hexlet.code.schemas;

/**
 * Базовый класс для схем валидации.
 * Представляет собой основу для создания различных схем валидации данных.
 *
 * @param <T> тип данных, которые будут валидироваться
 */
public abstract class BaseSchema<T> {
    protected boolean required;

    protected BaseSchema() {
        this.required = false;
    }

    public abstract boolean isValid(T value);

    /**
     * Устанавливает требование обязательности поля.
     * При переопределении метода необходимо учитывать,
     * что поле required должно быть корректно инициализировано.
     *
     * @return текущий экземпляр схемы
     */
    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }

    /**
     * Выполняет базовую проверку значения на обязательность.
     * При переопределении метода необходимо учитывать,
     * что проверка required должна быть выполнена.
     *
     * @param value проверяемое значение
     * @return true если значение проходит базовую проверку
     */
    public boolean isValidCommon(T value) {
        return required && value == null;
    }
}
