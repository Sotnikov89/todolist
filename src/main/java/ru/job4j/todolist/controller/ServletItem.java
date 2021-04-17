package ru.job4j.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.model.User;
import ru.job4j.todolist.service.HibernateServiceItem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "ServletItem", urlPatterns = "/items")
public class ServletItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String string = new ObjectMapper().writeValueAsString(HibernateServiceItem.instOf().findAll());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        resp.getWriter().write(string);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String desc = req.getParameter("description");
        if (id != 0) {
            Item item = Item.builder()
                    .id(id)
                    .description(desc)
                    .created(req.getParameter("day"))
                    .done(req.getParameter("status").equals("true"))
                    .user((User) req.getSession().getAttribute("user"))
                    .build();
            HibernateServiceItem.instOf().update(item);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yy");
            Item item = Item.builder()
                    .description(desc)
                    .created(LocalDateTime.now().format(formatter))
                    .done(false)
                    .user((User) req.getSession().getAttribute("user"))
                    .build();
            String string = new ObjectMapper().writeValueAsString(HibernateServiceItem.instOf().save(item));
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("json");
            resp.getWriter().write(string);
        }
    }
}
