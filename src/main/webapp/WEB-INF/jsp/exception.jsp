<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page isELIgnored="false" %>
<html lang="en">
<jsp:include page="fragments/staticFiles.jsp"/>
<body>
<div class="container">
    <jsp:include page="fragments/bodyHeader.jsp"/>
    <spring:url value="/resources/images/pets.png"/>
    <img src="${petImage}"/>

    <h2>Something happened</h2>

    <p>${exception.message}</p>
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>
