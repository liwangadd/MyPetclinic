<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<%@ page isELIgnored="false" %>
<html lang="en">
<jsp:include page="../fragments/staticFiles.jsp"/>
<body>
<script>
    $(function () {
        $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
    });
</script>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    <c:choose>
        <c:when test="${pet['new']}">
            <c:set var="method" value="post"/>
        </c:when>
        <c:otherwise>
            <c:set var="method" value="post"/>
        </c:otherwise>
    </c:choose>

    <h2>
        <c:if test="${pet['new']}">New </c:if>
        Pet
    </h2>

    <form:form modelAttribute="pet" method="${method}">
        <div class="control-group">
            <label class="control-label">Owner</label>
            <c:out value="${pet.owner.first_name} ${pet.owner.last_name}"/>
        </div>
        <petclinic:inputField label="Name" name="name"/>
        <petclinic:inputField label="Birth Date" name="birthDate"/>
        <div class="control-group">
            <petclinic:selectField name="type" label="Type" names="${types}" size="5"/>
        </div>
        <div class="form-actions">
            <c:choose>
                <c:when test="${pet['new']}">
                    <button type="submit">Add Pet</button>
                </c:when>
                <c:otherwise>
                    <button type="submit">Update Pet</button>
                </c:otherwise>
            </c:choose>
        </div>
    </form:form>
    <c:if test="${!pet['new']}">
    </c:if>
    <jsp:include page="../fragments/footer.jsp"/>
</div>
</body>
</html>
