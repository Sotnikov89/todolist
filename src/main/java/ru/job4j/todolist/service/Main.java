package ru.job4j.todolist.service;

import ru.job4j.todolist.model.Category;

public class Main {
    public static void main(String[] args) {
        HibernateServiceItem.instOf().findAll().forEach(item -> item.getCategories().forEach(System.out::println));
    }
}
