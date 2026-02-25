<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

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

<div class="form-container">
    <div class="card shadow-sm">
        <div class="card-body">
            <h4 class="card-title mb-3">Reset Password</h4>
            <% String flash = (String) session.getAttribute("flash");
               String flashType = (String) session.getAttribute("flashType");
               if (flash != null) { %>
                <div class="alert <%= "Error".equals(flashType) ? "alert-danger" : "alert-success" %>" role="alert"><strong><%= flashType %>:</strong> <%= flash %></div>
            <% session.removeAttribute("flash"); session.removeAttribute("flashType"); } %>

            <form method="post" action="reset" onsubmit="return validateForm();">
                <div class="mb-3">
                    <label for="token" class="form-label">Token</label>
                    <input id="token" name="token" type="text" class="form-control" required />
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">New Password</label>
                    <input id="password" name="password" type="password" class="form-control" required />
                    <div class="form-text">At least 8 chars, include upper/lower/digit/special</div>
                </div>
                <button type="submit" class="btn btn-primary w-100">Reset Password</button>
            </form>

            <div class="mt-3 text-center">
                <a href="login.jsp">Back to login</a>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>
