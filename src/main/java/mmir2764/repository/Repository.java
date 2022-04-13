package mmir2764.repository;

import java.util.List;

public interface Repository <T> {
    void add(T entity);
    List<T> getAll();
}
