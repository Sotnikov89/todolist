package ru.job4j.todolist.service;

import ru.job4j.todolist.dao.DaoItem;
import ru.job4j.todolist.model.Item;

import java.util.List;

public class HibernateServiceItem implements ServiceItem {

    private final DaoItem daoItem;

    public HibernateServiceItem() {
        this.daoItem = new DaoItem();
    }

    public static HibernateServiceItem instOf() {
        return new HibernateServiceItem();
    }

    @Override
    public Item findById(int id) {
        return daoItem.findById(id);
    }

    @Override
    public List<Item> findAll() {
        return daoItem.findAll();
    }

    @Override
    public Item save(Item item) {
        return daoItem.save(item);
    }

    @Override
    public void update(Item item) { daoItem.update(item); }

    @Override
    public void delete(int id) {
        daoItem.delete(id);
    }
}
