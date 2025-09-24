<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) { response.sendRedirect("login.jsp"); return; }
%>
<html>
<head><title>Create Post</title></head>
<body>
<h2>Create Post</h2>
<form action="post" method="post" enctype="multipart/form-data">
  Caption:<br/>
  <textarea name="caption" rows="4" cols="60"></textarea><br/><br/>
  Image:<br/>
  <input type="file" name="file" accept="image/*"/><br/><br/>
  <button type="submit">Post</button>
</form>
<p><a href="feed.jsp">Back to Feed</a> | <a href="logout">Logout</a></p>
</body>
</html>
