package ru.job4j.todolist.controller;

import com.google.gson.Gson;
import ru.job4j.todolist.model.Category;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.model.User;
import ru.job4j.todolist.service.HibernateServiceCategory;
import ru.job4j.todolist.service.HibernateServiceItem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ServletItem", urlPatterns = "/items")
public class ServletItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String string = new Gson().toJson(HibernateServiceItem.instOf().findAll());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        resp.getWriter().write(string);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if (id != 0) {
            HibernateServiceItem.instOf().updateStatusById(id, req.getParameter("status").equals("true"));
        } else {
            List<Category> categories = Arrays.stream(req.getParameterValues("categories[]"))
                    .map(Integer::parseInt)
                    .map(value -> HibernateServiceCategory.instOf().findById(value))
                    .collect(Collectors.toList());
            Item item = Item.builder()
                    .description(req.getParameter("description"))
                    .created(LocalDateTime.now())
                    .done(false)
                    .user((User) req.getSession().getAttribute("user"))
                    .categories(categories)
                    .build();
            String string = new Gson().toJson(HibernateServiceItem.instOf().save(item));
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("json");
            resp.getWriter().write(string);
        }
    }
}
