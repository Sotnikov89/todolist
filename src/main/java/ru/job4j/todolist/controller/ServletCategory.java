package ru.job4j.todolist.controller;

import com.google.gson.Gson;
import ru.job4j.todolist.service.HibernateServiceCategory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletCategory", urlPatterns = "/category")
public class ServletCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String string = new Gson().toJson(HibernateServiceCategory.instOf().findAll());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        resp.getWriter().write(string);
    }
}
