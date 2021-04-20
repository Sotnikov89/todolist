package ru.job4j.todolist.service;

import ru.job4j.todolist.dao.DaoCategory;
import ru.job4j.todolist.model.Category;

import java.util.List;

public class HibernateServiceCategory implements ServiceCategory {

    private final DaoCategory daoCategory;

    public HibernateServiceCategory() { this.daoCategory = new DaoCategory(); }

    public static HibernateServiceCategory instOf() { return new HibernateServiceCategory(); }

    @Override
    public Category findById(int id) { return daoCategory.findById(id); }

    @Override
    public List<Category> findAll() { return daoCategory.findAll(); }

    @Override
    public Category save(Category category) { return daoCategory.save(category); }

    @Override
    public void update(Category category) { daoCategory.update(category); }

    @Override
    public void delete(int id) { daoCategory.delete(id); }
}
