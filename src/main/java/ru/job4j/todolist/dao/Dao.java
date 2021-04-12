package ru.job4j.todolist.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    T findById(int id);

    List<T> findAll();

    T save(T t) throws SQLException;

    void update(T t);

    void delete(int id);
}
