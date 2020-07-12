<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>NYC Parking Gurus</title>
</head>
<body>
	<h1>CRUD on Collision</h1>
	<div id="collisionCreate"><a href="collisioncreate">Create Collision</a></div>
	<div id="findCollisions"><a href="findcollisions">Find Collisions By Borough</a></div>
	<div id="collisionUpdate"><a href="collisionupdate">Update Collision</a></div>
	<div id="collisionDelete"><a href="collisiondelete">Delete Collision</a></div>
	<br>	
	<h1>CRUD on User</h1>
	<div id="userCreate"><a href="usercreate">Create User</a></div>
	<div id="findUsers"><a href="findusers">Find Users by First Name</a></div>
	<div id="userupdate"><a href="userupdate">Update User</a></div>
	<div id="userdelete"><a href="userdelete">Create User</a></div>
</body>
</html>