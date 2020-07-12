<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trips</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>TripPK</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>UserName</th>
                <th>DestinationPK</th>
            </tr>
            <c:forEach items="${trips}" var="trip" >
                <tr>
                    <td><c:out value="${trip.getKey()}" /></td>
                    <td><fmt:formatDate value="${trip.getStart()}" pattern="MM-dd-yyyy"/></td>
                    <td><fmt:formatDate value="${trip.getEnd()}" pattern="MM-dd-yyyy"/></td>
                    <td><c:out value="${trip.getUserID()}" /></td>
                    <td><c:out value="${trip.getDestinationID()}" /></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
