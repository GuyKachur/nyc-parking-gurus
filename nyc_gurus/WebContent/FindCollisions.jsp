<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Collision</title>
</head>
<body>
	<form action="findcollisions" method="post">
		<h1>Search for a Collision by Borough</h1>
		<p>
			<label for="borough">Borough</label>
			<input id="borough" name="borough" value="${fn:escapeXml(param.borough)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="collisionCreate"><a href="collisioncreate">Create Collision</a></div>
	<br/>
	<h1>Matching Collisions</h1>
        <table border="1">
            <tr>
                <th>CollisionPK</th>
                <th>Date</th>
                <th>Borough</th>
                <th>ZipCode</th>
                <th>Persons Injured</th>
                <th>Persons Killed</th>
                <th>Pedestrians Injured</th>
                <th>Pedestrians Killed</th>
               	<th>Cyclists Injured</th>
                <th>Cyclists Killed</th>
                <th>Motorsists Injured</th>
                <th>Motorsists Killed</th>
                <th>Latitude</th>
        		<th>Longitude</th>
                <th>Delete Collision</th>
                <th>Update Collision</th>
            </tr>
            <c:forEach items="${collisions}" var="collision" >
                <tr>
                    <td><c:out value="${collision.getKey()}" /></td>
                    <td><fmt:formatDate value="${collision.getDate()}" pattern="MM-dd-yyyy"/></td>
                    <td><c:out value="${collision.getBorough()}" /></td>
                    <td><c:out value="${collision.getZipCode()}" /></td>
                    <td><c:out value="${collision.getPeopleInjured()}" /></td>
                    <td><c:out value="${collision.getPeopleKilled()}" /></td>
                    <td><c:out value="${collision.getPedestriansInjured()}" /></td>
                    <td><c:out value="${collision.getPedestriansKilled()}" /></td>
                    <td><c:out value="${collision.getCyclistsInjured()}" /></td>
                    <td><c:out value="${collision.getCyclistsKilled()}" /></td>
                    <td><c:out value="${collision.getMotoristsInjured()}" /></td>
                    <td><c:out value="${collision.getMotoristsKilled()}" /></td>
                    <td><c:out value="${collision.getLat()}" /></td>
                    <td><c:out value="${collision.getLng()}" /></td>
                    <td><a href="collisiondelete?collisionpk=<c:out value="${collision.getKey()}"/>">Delete</a></td>
                    <td><a href="collisionupdate?collisionpk=<c:out value="${collision.getKey()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>