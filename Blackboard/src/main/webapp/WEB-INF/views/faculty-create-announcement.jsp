<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Announcement</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="courseList" value="${courseList}" scope="page" />
	<form action="${contextPath}/faculty/createAnnouncement.htm"
		method="POST">
		<table>
			<tr>
				<th>Enter Message :</th>
				<td><input type="text" name="message"  required="required" /></td>
			</tr>
			<tr>
				<th>Select Course:</th>
				<td><select name="course">
						<c:forEach items="${courseList}" var="course">
							<option value="${course}">${course}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add Announcement" /></td>
			</tr>
		</table>
		<a href="${contextPath}/faculty/dashboard.htm"> Go Back</a>
	</form>
</body>
</html>