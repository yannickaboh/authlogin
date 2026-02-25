<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Forgot Password</title>
    <script>
        function validateForm() {
            const email = document.getElementById('email').value;
            const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
            if (!emailRegex.test(email)) { alert('Invalid email'); return false; }
            return true;
        }
    </script>
</head>
<body>
<%
    String flash = (String) session.getAttribute("flash");
    String flashType = (String) session.getAttribute("flashType");
    if (flash != null) { session.removeAttribute("flash"); session.removeAttribute("flashType"); }
%>
<h2>Forgot Password</h2>
<% if (flash != null) { %>
    <div style="color: <%= "Error".equals(flashType) ? "red" : "green" %>;"> <strong><%= flashType %>:</strong> <%= flash %></div>
<% } %>

<form method="post" action="forgot" onsubmit="return validateForm();">
    <label>Email: <input id="email" name="email" type="email" required /></label><br/>
    <button type="submit">Send reset link</button>
    <p><a href="login.jsp">Back to login</a></p>
</form>
</body>
</html>
