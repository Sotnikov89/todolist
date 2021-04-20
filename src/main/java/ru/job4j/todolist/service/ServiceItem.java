package ru.job4j.todolist.service;

import ru.job4j.todolist.dao.Dao;
import ru.job4j.todolist.model.Item;

public interface ServiceItem extends Dao<Item> {
    public void updateStatusById(int id, boolean status);
}
