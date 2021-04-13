package ru.job4j.todolist.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todolist.model.Item;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DaoItem implements Dao<Item> {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private void txs(final Consumer<Session> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Item findById(int id) {
        return tx(session -> session.get(Item.class, id));
    }

    @Override
    public List<Item> findAll() {
        return tx(session -> session.createQuery("FROM Item").list());
    }

    @Override
    public Item save(Item item) {
        int id = (Integer) tx(session -> session.save(item));
        item.setId(id);
        return item;
    }

    @Override
    public void update(Item item) {
        txs(session -> session.update(item));
    }

    @Override
    public void delete(int id) {
        txs(session -> session.delete(Item.builder().id(id).build()));
    }
}
