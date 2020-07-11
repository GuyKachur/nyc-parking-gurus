<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Update a Point of Interest </title>
</head>
<body>
<h1>Update Point of Interest</h1>
<form action="update?object=point_of_interest" method="post">
    <p>
        <label for="id">ID</label>
        <input id="id" name="id" value="">
    </p>
    <p>
        <label for="lat">Latitude</label>
        <input id="lat" name="lat" value="">
    </p>
    <p>
        <label for="lng">Longitude</label>
        <input id="lng" name="lng" value="">
    </p>
    <p>
        <label for="name">Name</label>
        <input id="name" name="name" value="">
    </p>
    <p>
        <label for="borough">Borough</label>
        <input id="borough" name="borough" value="">
    </p>
    <p>
        <label for="side_of_street">Side of Street (1 or 2) </label>
        <input id="side_of_street" name="side_of_street" value="">
    </p>
    <p>
        <label for="type">Type</label>
        <input id="type" name="type" type="">
    </p>
    <p>
        <input type="submit">
    </p>
</form>
<br/><br/>
<p>
    <span id="successMessage"><b>${messages.success}</b></span>
</p>
</body>
</html>