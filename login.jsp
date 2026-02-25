<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<script>
    function validateForm() {
        const email = document.getElementById('email').value;
        const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
        if (!emailRegex.test(email)) { alert('Invalid email'); return false; }
        return true;
    }
</script>

<div class="form-container">
    <div class="card shadow-sm">
        <div class="card-body">
            <h4 class="card-title mb-3">Login</h4>
            <% String flash = (String) session.getAttribute("flash");
               String flashType = (String) session.getAttribute("flashType");
               if (flash != null) { %>
                <div class="alert <%= "Error".equals(flashType) ? "alert-danger" : "alert-success" %>" role="alert"><strong><%= flashType %>:</strong> <%= flash %></div>
            <% session.removeAttribute("flash"); session.removeAttribute("flashType"); } %>

            <form method="post" action="login" onsubmit="return validateForm();">
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input id="email" name="email" type="email" class="form-control" required />
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input id="password" name="password" type="password" class="form-control" required />
                </div>
                <button type="submit" class="btn btn-primary w-100">Login</button>
            </form>

            <div class="mt-3 text-center">
                <a href="forgot.jsp">Forgot password?</a>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>
