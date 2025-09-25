<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up - Mini FB</title>
    <link rel="stylesheet" href="assets/css/style.css"/>
</head>
<body>
<div class="header">
    <img src="assets/images/fb-logo.png" class="logo" alt="logo"/>
    <h1>Mini FB - Sign Up</h1>
</div>
<div class="container">
    <form action="signup" method="post" class="card">
        <h2>Create account</h2>
        <input type="text" name="username" placeholder="Username" required/>
        <input type="email" name="email" placeholder="Email (optional)"/>
        <input type="password" name="password" placeholder="Password (min 6 chars)" required/>
        <button type="submit">Sign Up</button>
        <p>Already have account? <a href="login.jsp">Login</a></p>
    </form>
</div>
</body>
</html>
