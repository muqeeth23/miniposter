<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Signup</title></head>
<body>
<h2>Signup</h2>
<c:choose>
  <c:when test="${param.error == '1'}"><p style="color:red">Invalid input. Password must be >= 6 chars.</p></c:when>
  <c:when test="${param.error == '2'}"><p style="color:red">Error creating user (maybe username exists).</p></c:when>
</c:choose>
<form action="signup" method="post">
  Username:<br/><input type="text" name="username" required/><br/>
  Email:<br/><input type="email" name="email"/><br/>
  Password:<br/><input type="password" name="password" minlength="6" required/><br/><br/>
  <button type="submit">Sign Up</button>
</form>
<p>Already have account? <a href="login.jsp">Login</a></p>
</body>
</html>
