package com.example.minifb.servlet;

import com.example.minifb.dao.UserDAO;
import com.example.minifb.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Optional<User> opt = userDAO.findByUsername(username);
            if (opt.isPresent()) {
                User u = opt.get();
                if (BCrypt.checkpw(password, u.getPasswordHash())) {
                    HttpSession s = req.getSession(true);
                    s.setAttribute("userId", u.getId());
                    s.setAttribute("username", u.getUsername());
                    resp.sendRedirect("feed");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("login.jsp?error=1");
    }
}
