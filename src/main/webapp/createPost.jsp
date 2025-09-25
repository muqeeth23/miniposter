<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Post - Mini FB</title>
    <link rel="stylesheet" href="assets/css/style.css"/>
</head>
<body>
<div class="header">
    <img src="assets/images/fb-logo.png" class="logo" alt="logo"/>
    <h1>Create Post</h1>
    <a class="nav-link" href="feed">Feed</a>
    <a class="nav-link" href="logout">Logout</a>
</div>
<div class="container">
    <form action="post" method="post" enctype="multipart/form-data" class="card">
        <h2>New Post</h2>
        <textarea name="caption" placeholder="What's on your mind?" rows="4"></textarea>
        <input type="file" name="image" accept="image/*"/>
        <button type="submit">Share</button>
    </form>
</div>
</body>
</html>
