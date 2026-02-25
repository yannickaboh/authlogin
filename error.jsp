<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="row">
    <div class="col-md-8 offset-md-2">
        <% String flash = (String) session.getAttribute("flash");
             String flashType = (String) session.getAttribute("flashType");
             if (flash != null) { %>
                <div class="alert alert-danger" role="alert"><strong><%= flashType != null ? flashType : "Error" %>:</strong> <%= flash %></div>
        <% session.removeAttribute("flash"); session.removeAttribute("flashType"); } %>

        <div class="card">
            <div class="card-body text-center">
                <h4 class="card-title">Error</h4>
                <p><a class="btn btn-secondary" href="<%= request.getContextPath() %>/">Home</a></p>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>
