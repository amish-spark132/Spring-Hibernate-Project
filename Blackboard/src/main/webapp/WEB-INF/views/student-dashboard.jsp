<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Dashboard</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
</head>
<body>
Student Dashboard
<br>
<a href = "${contextPath}/student/course-material.htm">Go to Course Materials</a><br>
<a href = "${contextPath}/student/announcements.htm">Go to Announcements</a>
<br>
<a href = "${contextPath}/student/assignment.htm">Go to Assignments</a>
<br>
<a href="${contextPath}/user/logout.htm">Logout</a>
</body>
</html>