<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String flash = (String) session.getAttribute("flash");
    String flashType = (String) session.getAttribute("flashType");
    if (flash != null) {
        session.removeAttribute("flash");
        session.removeAttribute("flashType");
    }
%>
<!DOCTYPE html>
<html>
<head><meta charset="utf-8" /><title>Success</title></head>
<body>
    <% if (flash != null) { %>
        <div style="color: green;"><strong><%= flashType != null ? flashType : "Success" %>:</strong> <%= flash %></div>
    <% } %>
    <p><a href="/">Home</a></p>
</body>
</html>
