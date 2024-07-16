package org.crudservice.repository;


import java.sql.SQLException;
import java.util.Collection;

public interface CRUDRepository<T> {
    T getById(Integer id);

    Collection<T> getAll() throws SQLException;

    void save(T item) throws SQLException;

    void update(T item);

    void deleteById(Integer id);
}
