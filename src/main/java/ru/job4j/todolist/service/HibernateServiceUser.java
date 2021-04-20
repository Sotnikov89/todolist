package ru.job4j.todolist.service;

import ru.job4j.todolist.dao.DaoUser;
import ru.job4j.todolist.model.User;

import java.util.List;

public class HibernateServiceUser implements ServiceUser {

    private final DaoUser daoUser;

    public HibernateServiceUser() { this.daoUser = new DaoUser(); }

    public static HibernateServiceUser instOf() {
        return new HibernateServiceUser();
    }

    @Override
    public User findById(int id) {
        return daoUser.findById(id);
    }

    @Override
    public List<User> findAll() {
        return daoUser.findAll();
    }

    @Override
    public User save(User user) {
        return daoUser.save(user);
    }

    @Override
    public void update(User user) {
        daoUser.update(user);
    }

    @Override
    public void delete(int id) {
        daoUser.delete(id);
    }

    public User findUserByEmail(String email) {
        return daoUser.findUserByEmail(email);
    }
}
