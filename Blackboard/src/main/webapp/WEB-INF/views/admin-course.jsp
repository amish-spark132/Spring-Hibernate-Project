<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Courses</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="courses" value="${success}" scope="session" />
	<c:choose>
		<c:when test="${empty courses}">
			<div>
				<p>No Courses added yet.</p>
				<a href="${contextPath}/admin/addCourse.htm">Add New Course</a>
			</div>
		</c:when>
		<c:otherwise>
			<a href="${contextPath}/admin/addCourse.htm">Add New Course</a>
			<table border="5">
				<tr>

					<th>Course ID</th>
					<th>Course Name</th>
					<th></th>
				</tr>
				<c:forEach items="${courses}" var="dataText" varStatus="loop">
					<tr>

						<td>${dataText.getCourseID()}</td>
						<td>${dataText.getCourseName()}</td>
						<td><a
							href='${contextPath}/admin/removeCourse.htm?Id=${dataText.getCourseID()}'>Remove
						</a></td>
					</tr>
				</c:forEach>

			</table>

		</c:otherwise>
	</c:choose>
<a href="${contextPath}/admin/dashboard.htm"> Go Back</a>
</body>
</html>