package com.example.minifb.servlet;

import com.example.minifb.dao.CommentDAO;
import com.example.minifb.dao.PostDAO;
import com.example.minifb.dao.UserDAO;
import com.example.minifb.model.Comment;
import com.example.minifb.model.Post;
import com.example.minifb.model.User;
import com.example.minifb.util.S3Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/feed")
public class FeedServlet extends HttpServlet {
    private final PostDAO postDAO = new PostDAO();
    private final CommentDAO commentDAO = new CommentDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) { resp.sendRedirect("login.jsp"); return; }

        try {
            List<Post> posts = postDAO.findAll();
            for (Post p : posts) {
                // load comments
                List<Comment> comments = commentDAO.findByPostId(p.getId());
                p.setComments(comments);

                // load poster info (optional)
                Optional<User> uu = userDAO.findById(p.getUserId());
                if (uu.isPresent()) {
                    // we'll pass username to JSP via User object in request scope
                    // but simpler: attach a transient User object inside Post? Keep Post minimal; JSP will call userDAO
                }

                // Build CloudFront URL for rendering if image exists
                if (p.getImageKey() != null && !p.getImageKey().trim().isEmpty()) {
                    String cfUrl = S3Util.cloudFrontUrl(p.getImageKey());
                    // store final URL in imageKey field temporarily for rendering
                    p.setImageKey(cfUrl);
                }
            }
            req.setAttribute("posts", posts);
        } catch (Exception e) { e.printStackTrace(); }

        req.getRequestDispatcher("/feed.jsp").forward(req, resp);
    }
}
