<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Reset Password</title>
    <script>
        function validateForm() {
            const token = document.getElementById('token').value;
            const password = document.getElementById('password').value;
            const pwdRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/;
            if (!token) { alert('Missing token'); return false; }
            if (!pwdRegex.test(password)) { alert('Password must be at least 8 chars, include upper/lower/digit/special'); return false; }
            return true;
        }
    </script>
</head>
<body>
<h2>Reset Password</h2>
<form method="post" action="reset" onsubmit="return validateForm();">
    <label>Token: <input id="token" name="token" type="text" required /></label><br/>
    <label>New Password: <input id="password" name="password" type="password" required /></label><br/>
    <button type="submit">Reset Password</button>
    <p><a href="login.jsp">Back to login</a></p>
</form>
</body>
</html>
