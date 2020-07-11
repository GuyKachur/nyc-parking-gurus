<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Find a User</title>
</head>
<body>
<form action="/find?object=point_of_interest" method="post">
    <h1>Search for a Point of interest whose name contains</h1>
    <p>
        <label for="name">name</label>
        <input id="name" name="name" value="${fn:escapeXml(param.name)}">
    </p>
    <p>
        <input type="submit">
        <br/><br/><br/>
        <span id="successMessage"><b>${messages.success}</b></span>
    </p>
</form>
<br/>
<div id="pointofinterestcreate"><a href="create?object=point_of_interest">Create Point Of Interest</a></div>
<br/>
<h1>Matching Points</h1>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Side of Street</th>
        <th>Domain</th>
        <th>Borough</th>
        <th>Type</th>
        <th>ID</th>
        <th>Delete Point</th>
        <th>Update Point</th>
    </tr>
    <c:forEach items="${PointOfInterest}" var="pointOfInterest">
        <tr>
            <td><c:out value="${PointOfInterest.getName()}"/></td>
            <td><c:out value="${PointOfInterest.getSideOfStreet()}"/></td>
            <td><c:out value="${PointOfInterest.getDomain()}"/></td>
            <td><c:out value="${PointOfInterest.getBorough()}"/></td>
            <td><c:out value="${PointOfInterest.getType()}"/></td>
            <td><c:out value="${PointOfInterest.getKey()}"/></td>
            <td><a href="delete?object=point_of_interest&id=<c:out value="${PointOfInterest.getKey()}"/>">Delete</a>
            </td>
            <td><a href="update?object=point_of_interest&id=<c:out value="${PointOfInterest.getKey()}"/>">Update</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
