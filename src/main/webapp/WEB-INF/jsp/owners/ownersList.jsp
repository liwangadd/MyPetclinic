<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<%@ page isELIgnored="false" %>
<html lang="en">
<jsp:include page="../fragments/staticFiles.jsp"/>
<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    <h2>Owners</h2>
    <datatables:table id="owners" data="${selections}" row="owner" theme="bootstrap2" cssclass="table table-striped"
                      pageable="false" info="false" export="pdf">

        <datatables:column title="Name" cssstyle="width: 150px;" display="html">
            <spring:url value="/owners/{ownerId}.html" var="ownerUrl">
                <spring:param name="ownerId" value="${owner.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(ownerUrl)}"><c:out value="${owner.first_name} ${owner.last_name}"/></a>
            <datatables:column title="Name" display="pdf">
                <c:out value="${owner.first_name} ${owner.last_name}"/>
            </datatables:column>
            <datatables:column title="Address" property="address" cssStyle="width: 200px;"/>
            <datatables:column title="City" property="city"/>
            <datatables:column title="Telephone" property="telephone" cssstyle="width: 200px;"/>
            <datatables:column title="Pets" cssStyle="width: 100px;">
                <c:forEach var="pet" items="${owner.pets}">
                    <c:out value="${pet.name}"/>
                </c:forEach>
            </datatables:column>
        </datatables:column>
        <datatables:export type="pdf" cssStyle="width: 25px;" cssClass="btn"/>
    </datatables:table>
</div>
</body>
</html>
