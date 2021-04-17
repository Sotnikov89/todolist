package ru.job4j.todolist.controller;

import ru.job4j.todolist.model.User;
import ru.job4j.todolist.service.HibernateServiceUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletReg", urlPatterns = "/reg")
public class ServletReg extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = User.builder()
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        HibernateServiceUser.instOf().save(user);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
