<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Mini FB</title>
    <link rel="stylesheet" href="assets/css/style.css"/>
</head>
<body>
<div class="header">
    <img src="assets/images/fb-logo.png" class="logo" alt="logo"/>
    <h1>Mini FB</h1>
</div>

<div class="container">
    <form action="login" method="post" class="card">
        <h2>Login</h2>
        <input type="text" name="username" placeholder="Username" required/>
        <input type="password" name="password" placeholder="Password" required/>
        <button type="submit">Login</button>
        <p>New user? <a href="signup.jsp">Sign up</a></p>
    </form>
</div>
</body>
</html>
