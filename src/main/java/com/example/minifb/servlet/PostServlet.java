package com.example.minifb.servlet;

import com.example.minifb.dao.PostDAO;
import com.example.minifb.model.Post;
import com.example.minifb.util.S3Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/post")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 15 * 1024 * 1024, maxRequestSize = 30 * 1024 * 1024)
public class PostServlet extends HttpServlet {

    private final PostDAO postDAO = new PostDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/createPost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) { resp.sendRedirect("login.jsp"); return; }

        String caption = req.getParameter("caption");

        Part filePart = null;
        try { filePart = req.getPart("image"); } catch (Exception ignored) {}

        String s3Key = null;
        if (filePart != null && filePart.getSize() > 0) {
            String filename = filePart.getSubmittedFileName();
            try (InputStream in = filePart.getInputStream()) {
                s3Key = S3Util.uploadPrivate(filename, in, filePart.getSize(), filePart.getContentType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Post p = new Post();
        p.setUserId(userId);
        p.setCaption(caption);
        p.setImageKey(s3Key);

        try {
            postDAO.save(p);
        } catch (Exception e) { e.printStackTrace(); }

        resp.sendRedirect("feed");
    }
}
