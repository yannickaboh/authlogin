<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="py-5 text-center">
    <h1 class="display-5">Welcome to AuthLogin</h1>
    <p class="lead">A simple authentication demo with secure password hashing and reset flow.</p>
    <p>
        <a class="btn btn-primary btn-lg" href="register.jsp" role="button">Get Started</a>
    </p>
</div>

<div class="row">
    <div class="col-md-8 offset-md-2">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Quick Links</h5>
                <ul>
                    <li><a href="register.jsp">Register</a></li>
                    <li><a href="login.jsp">Login</a></li>
                    <li><a href="forgot.jsp">Forgot Password</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>
