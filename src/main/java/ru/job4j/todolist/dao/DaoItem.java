package ru.job4j.todolist.dao;

import ru.job4j.todolist.model.Item;

import java.util.List;

public class DaoItem implements Dao<Item> {

    private final HibernateConnect hibernateConnect;

    public DaoItem() {
        hibernateConnect = HibernateConnect.instOf();
    }

    @Override
    public Item findById(int id) {
        return HibernateConnect.instOf().getOrSave(session -> session.get(Item.class, id));
    }

    @Override
    public List<Item> findAll() {
        return HibernateConnect.instOf().getOrSave(session -> session.createQuery("FROM Item").list());
    }

    @Override
    public Item save(Item item) {
        int id = (Integer) HibernateConnect.instOf().getOrSave(session -> session.save(item));
        item.setId(id);
        return item;
    }

    @Override
    public void update(Item item) {
        HibernateConnect.instOf().updateOrDelete(session -> session.update(item));
    }

    @Override
    public void delete(int id) {
        HibernateConnect.instOf().updateOrDelete(session -> session.delete(Item.builder().id(id).build()));
    }
}
