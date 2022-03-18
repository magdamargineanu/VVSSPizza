package mmir2764.model;

public interface Validator<T> {
    void validate(T entity) throws Exception;
}
