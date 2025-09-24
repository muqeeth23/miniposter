<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Login</h2>
<c:choose>
  <c:when test="${param.signup == '1'}"><p style="color:green">Signup successful. Please login.</p></c:when>
  <c:when test="${param.error == '1'}"><p style="color:red">Invalid username or password.</p></c:when>
  <c:when test="${param.logout == '1'}"><p style="color:green">Logged out.</p></c:when>
</c:choose>
<form action="login" method="post">
  Username:<br/><input type="text" name="username" required/><br/>
  Password:<br/><input type="password" name="password" required/><br/><br/>
  <button type="submit">Login</button>
</form>
<p>New user? <a href="signup.jsp">Sign up</a></p>
</body>
</html>
