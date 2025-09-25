<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");
    String username = (String) session.getAttribute("username");
    if (userId == null) { response.sendRedirect("login.jsp"); return; }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Feed - Mini FB</title>
    <link rel="stylesheet" href="assets/css/style.css"/>
</head>
<body>
<div class="header">
    <img src="assets/images/fb-logo.png" class="logo" alt="logo"/>
    <div class="search">Mini FB</div>
    <div class="right">
        <span>Welcome, <b><%= username %></b></span>
        <a class="nav-link" href="createPost.jsp">Create Post</a>
        <a class="nav-link" href="logout">Logout</a>
    </div>
</div>

<div class="page">
    <div class="left-column">
        <div class="card">
            <h3>Your Profile</h3>
            <p><b><%= username %></b></p>
        </div>
    </div>

    <div class="feed-column">
        <div class="card new-post">
            <form action="post" method="post" enctype="multipart/form-data">
                <textarea name="caption" placeholder="What's on your mind?" rows="3"></textarea>
                <div class="row">
                    <input type="file" name="image" accept="image/*"/>
                    <button type="submit">Post</button>
                </div>
            </form>
        </div>

        <c:forEach var="p" items="${posts}">
            <div class="card post">
                <div class="post-header">
                    <div class="avatar"></div>
                    <div>
                        <b>
                            <c:out value="${p.userId}"/>
                        </b>
                        <div class="time"><c:out value="${p.createdAt}"/></div>
                    </div>
                </div>

                <div class="post-body">
                    <p><c:out value="${p.caption}"/></p>
                    <c:if test="${not empty p.imageKey}">
                        <img src="${p.imageKey}" alt="Post image" class="post-image"/>
                    </c:if>
                </div>

                <div class="post-comments">
                    <h4>Comments</h4>
                    <c:forEach var="c" items="${p.comments}">
                        <div class="comment">
                            <b> User#<c:out value='${c.userId}'/>:</b> <c:out value="${c.content}"/>
                        </div>
                    </c:forEach>
                </div>

                <div class="add-comment">
                    <form action="comment" method="post">
                        <input type="hidden" name="postId" value="${p.id}"/>
                        <input type="text" name="content" placeholder="Write a comment..." required/>
                        <button type="submit">Comment</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="right-column">
        <div class="card">
            <h3>Suggestions</h3>
            <p>People you may know</p>
        </div>
    </div>
</div>

</body>
</html>
