package com.example.minifb.servlet;

import com.example.minifb.dao.PostDAO;
import com.example.minifb.model.Post;
import com.example.minifb.util.S3Util;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@WebServlet("/post")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 20 * 1024 * 1024)
public class PostServlet extends HttpServlet {
    private final PostDAO postDAO = new PostDAO();

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) { resp.sendRedirect("login.jsp"); return; }
        req.getRequestDispatcher("/createPost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) { resp.sendRedirect("login.jsp"); return; }

        String caption = req.getParameter("caption");
        Part filePart = req.getPart("file");
        String imageUrl = null;

        if (filePart != null && filePart.getSize() > 0) {
            String submittedFileName = filePart.getSubmittedFileName();
            try (InputStream in = filePart.getInputStream()) {
                imageUrl = S3Util.upload(submittedFileName, in, filePart.getSize(), filePart.getContentType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Post p = new Post(userId, caption, imageUrl);
        try {
            postDAO.save(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("feed.jsp");
    }
}
