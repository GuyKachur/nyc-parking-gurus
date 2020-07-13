<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Collision</title>
</head>
<body>
	<h1>Create Collision</h1>
	<form action="collisioncreate" method="post">
		<p>
			<label for="latitude">Latitude</label>
			<input id="latitude" name="latitude" value="">
		</p>
		<p>
			<label for="longitude">Longitude</label>
			<input id="longitude" name="longitude" value="">
		</p>
		<p>
			<label for="date">Date(yyyy-mm-dd)</label>
			<input id="date" name="date" value="">
		</p>
		<p>
			<label for="borough">Borough</label>
			<input id="borough" name="borough" value="">
		</p>
		<p>
			<label for="zipcode">Zipcode</label>
			<input id="zipcode" name="zipcode" value="">
		</p>
		<p>
			<label for="peopleinjured">People Injured</label>
			<input id="peopleinjured" name="peopleinjured" value="">
		</p>
		<p>
			<label for="peoplekilled">People Killed</label>
			<input id="peoplekilled" name="peoplekilled" value="">
		</p>
		<p>
			<label for="pedestriansinjured">Pedestrians Injured</label>
			<input id="pedestriansinjured" name="pedestriansinjured" value="">
		</p>
		<p>
			<label for="pedestrianskilled">Pedestrians Killed</label>
			<input id="pedestrianskilled" name="pedestrianskilled" value="">
		</p>
		<p>
			<label for="cyclistsinjured">Cyclists Injured</label>
			<input id="cyclistsinjured" name="cyclistsinjured" value="">
		</p>
		<p>
			<label for="cyclistskilled">Cyclists Killed</label>
			<input id="cyclistskilled" name="cyclistskilled" value="">
		</p>
		<p>
			<label for="motoristsinjured">Motorists Injured</label>
			<input id="motoristsinjured" name="motoristsinjured" value="">
		</p>
		<p>
			<label for="motoristskilled">Motorists Killed</label>
			<input id="motoristskilled" name="motoristskilled" value="">
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