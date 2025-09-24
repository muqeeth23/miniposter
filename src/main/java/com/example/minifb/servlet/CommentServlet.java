package com.example.minifb.servlet;

import com.example.minifb.dao.CommentDAO;
import com.example.minifb.model.Comment;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private final CommentDAO commentDAO = new CommentDAO();

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) { resp.sendRedirect("login.jsp"); return; }

        int postId = Integer.parseInt(req.getParameter("postId"));
        String content = req.getParameter("content");

        Comment c = new Comment(postId, userId, content);
        try {
            commentDAO.save(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("feed.jsp");
    }
}
