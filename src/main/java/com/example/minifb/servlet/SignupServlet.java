package com.example.minifb.servlet;

import com.example.minifb.dao.UserDAO;
import com.example.minifb.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (username == null || password == null || username.trim().isEmpty() || password.length() < 6) {
            resp.sendRedirect("signup.jsp?error=1");
            return;
        }

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        User u = new User(username.trim(), email == null ? "" : email.trim(), hashed);
        try {
            userDAO.save(u);
            resp.sendRedirect("login.jsp?signup=1");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("signup.jsp?error=2");
        }
    }
}
