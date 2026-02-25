<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Login</title>
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
<h2>Login</h2>
<form method="post" action="login" onsubmit="return validateForm();">
    <label>Email: <input id="email" name="email" type="email" required /></label><br/>
    <label>Password: <input name="password" type="password" required /></label><br/>
    <button type="submit">Login</button>
    <p><a href="forgot.jsp">Forgot password?</a></p>
</form>
</body>
</html>
