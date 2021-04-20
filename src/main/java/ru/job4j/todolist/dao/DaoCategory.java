package ru.job4j.todolist.dao;

import ru.job4j.todolist.model.Category;

import java.util.List;

public class DaoCategory implements Dao<Category> {

    private final HibernateConnect hibernateConnect;

    public DaoCategory() {
        hibernateConnect = HibernateConnect.instOf();
    }

    @Override
    public Category findById(int id) {
        return HibernateConnect.instOf().getOrSave(session -> session.get(Category.class, id));
    }

    @Override
    public List<Category> findAll() {
        return HibernateConnect.instOf().getOrSave(session -> session.createQuery("FROM Category ").list());
    }

    @Override
    public Category save(Category category) {
        int id = (Integer) HibernateConnect.instOf().getOrSave(session -> session.save(category));
        category.setId(id);
        return category;
    }

    @Override
    public void update(Category category) {
        HibernateConnect.instOf().updateOrDelete(session -> session.update(category));
    }

    @Override
    public void delete(int id) {
        HibernateConnect.instOf().updateOrDelete(session -> session.delete(Category.builder().id(id).build()));
    }
}
