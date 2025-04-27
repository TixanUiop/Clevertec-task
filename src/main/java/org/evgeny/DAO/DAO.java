package org.evgeny.DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<T, F> {
    T create(T entity);
    T update(T entity);
    void delete(T entity);
    Optional<T> findById(F id);
    List<T> findAll();
}
