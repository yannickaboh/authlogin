<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Register</title>
    <script>
        function validateForm() {
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
            const pwdRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/;
            if (!emailRegex.test(email)) { alert('Invalid email'); return false; }
            if (!pwdRegex.test(password)) { alert('Password must be at least 8 chars, include upper/lower/digit/special'); return false; }
            return true;
        }
    </script>
</head>
<body>
<h2>Register</h2>
<form method="post" action="register" onsubmit="return validateForm();">
    <label>Email: <input id="email" name="email" type="email" required /></label><br/>
    <label>Password: <input id="password" name="password" type="password" required /></label><br/>
    <button type="submit">Register</button>
    <p><a href="login.jsp">Already have an account? Login</a></p>
</form>
</body>
</html>
