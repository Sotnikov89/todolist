package ru.job4j.todolist.controller;

import ru.job4j.todolist.model.User;
import ru.job4j.todolist.service.HibernateServiceUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletAuth", urlPatterns = "/auth")
public class ServletAuth extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("user");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String  password = req.getParameter("password");
        User user = HibernateServiceUser.instOf().findUserByEmail(email);
        if (user.getPassword().equals(password)) {
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("userName", user.getName());
            resp.sendRedirect(req.getContextPath() + "/index");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
