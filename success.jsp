<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="row">
    <div class="col-md-8 offset-md-2">
        <% String flash = (String) session.getAttribute("flash");
             String flashType = (String) session.getAttribute("flashType");
             if (flash != null) { %>
                <div class="alert alert-success" role="alert"><strong><%= flashType != null ? flashType : "Success" %>:</strong> <%= flash %></div>
        <% session.removeAttribute("flash"); session.removeAttribute("flashType"); } %>

        <div class="card">
            <div class="card-body text-center">
                <h4 class="card-title">Success</h4>
                <p><a class="btn btn-primary" href="<%= request.getContextPath() %>/">Home</a></p>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>
