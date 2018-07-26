<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty Dashboard</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
</head>
<body>
	<h1>Faculty Dashboard</h1>
	<br>
	<a href="${contextPath}/faculty/announcements.htm">Go to
		Announcements</a>
	<br>
	<a href="${contextPath}/faculty/course-material.htm">Go to Course
		Materials</a>
		<br>
	<a href="${contextPath}/faculty/assignments.htm">Go to Assignment</a>
	<br>
	<a href="${contextPath}/user/logout.htm">Logout</a>
	<br>
</body>
</html>