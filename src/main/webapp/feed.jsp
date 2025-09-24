<%@ page import="java.util.*,com.example.minifb.dao.PostDAO,com.example.minifb.dao.CommentDAO,com.example.minifb.dao.UserDAO,com.example.minifb.model.Post,com.example.minifb.model.Comment,com.example.minifb.model.User" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");
    String username = (String) session.getAttribute("username");
    if (userId == null) { response.sendRedirect("login.jsp"); return; }

    PostDAO postDAO = new PostDAO();
    CommentDAO commentDAO = new CommentDAO();
    UserDAO userDAO = new UserDAO();

    List<Post> posts = Collections.emptyList();
    try { posts = postDAO.findAll(); } catch (Exception e) { e.printStackTrace(); }
%>
<html>
<head><title>Feed</title></head>
<body>
<p>Welcome, <b><%= username %></b>! <a href="createPost.jsp">Create Post</a> | <a href="logout">Logout</a></p>
<hr/>
<% for (Post p : posts) { 
     User postUser = null;
     try { postUser = userDAO.findById(p.getUserId()).orElse(null); } catch (Exception e) { e.printStackTrace(); }
%>
  <div style="border:1px solid #ccc;padding:10px;margin:10px;">
    <div>
      <b><%= (postUser != null ? postUser.getUsername() : ("User#" + p.getUserId())) %></b>
      <small style="color:gray"> - <%= p.getCreatedAt() %></small>
    </div>
    <p><%= (p.getCaption() != null ? p.getCaption() : "") %></p>
    <% if (p.getImageUrl() != null && !p.getImageUrl().trim().isEmpty()) { %>
        <div><img src="<%= p.getImageUrl() %>" style="max-width:600px;max-height:500px;"/></div>
    <% } %>

    <h4>Comments</h4>
    <%
       List<Comment> comments = Collections.emptyList();
       try { comments = commentDAO.findByPostId(p.getId()); } catch (Exception e) { e.printStackTrace(); }
       for (Comment c : comments) {
           User commentUser = null;
           try { commentUser = userDAO.findById(c.getUserId()).orElse(null); } catch (Exception e) { e.printStackTrace(); }
    %>
       <p><b><%= (commentUser != null ? commentUser.getUsername() : ("User#" + c.getUserId())) %>:</b> <%= c.getContent() %></p>
    <% } %>

    <form action="comment" method="post">
        <input type="hidden" name="postId" value="<%= p.getId() %>"/>
        <input type="text" name="content" placeholder="Write a comment..." required/>
        <button type="submit">Comment</button>
    </form>
  </div>
<% } %>
</body>
</html>
