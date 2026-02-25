<%@ page contentType="text/html; charset=UTF-8" %>
<!-- Common header include: Bootstrap CSS and page header/navbar -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Auth Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUa6mY5Y2e1rZ+6o6sFQbQp1K3mK7Z4q2Dk3q5l6g7h8i9j0k1l2m3n4o5p6" crossorigin="anonymous">
    <style>
        body { padding-top: 4.5rem; }
        .form-container { max-width: 480px; margin: 0 auto; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
  <div class="container-fluid">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/">AuthLogin</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
      <ul class="navbar-nav me-auto mb-2 mb-md-0">
        <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/register.jsp">Register</a></li>
        <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/login.jsp">Login</a></li>
        <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/forgot.jsp">Forgot</a></li>
      </ul>
    </div>
  </div>
</nav>

<main class="container mt-4">
