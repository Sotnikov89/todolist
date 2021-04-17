package ru.job4j.todolist.dao;

import ru.job4j.todolist.model.User;

import java.util.List;

public class DaoUser implements Dao<User> {

    private final HibernateConnect hibernateConnect;

    public DaoUser() {
        hibernateConnect = HibernateConnect.instOf();
    }

    @Override
    public User findById(int id) {
        return HibernateConnect.instOf().getOrSave(session -> session.get(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return HibernateConnect.instOf().getOrSave(session -> session.createQuery("FROM User").list());
    }

    @Override
    public User save(User user) {
        int id = (Integer) HibernateConnect.instOf().getOrSave(session -> session.save(user));
        user.setId(id);
        return user;
    }

    @Override
    public void update(User user) {
        HibernateConnect.instOf().updateOrDelete(session -> session.update(user));
    }

    @Override
    public void delete(int id) {
        HibernateConnect.instOf().updateOrDelete(session -> session.delete(User.builder().id(id).build()));
    }

    public User findUserByEmail(String email) {
        return (User) HibernateConnect.instOf().getOrSave(
                session -> session.createQuery("FROM User u WHERE u.email = :email")
                        .setParameter("email", email)
                        .getSingleResult());
    }
}
