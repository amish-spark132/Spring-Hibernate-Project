<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Add Course</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<form action="${contextPath}/admin/addCourse.htm" method="POST">
		<table>
			<tr>
				<th>Enter Course Name :</th>
				<td><input type="text" name="courseName" required="required"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add Course"/></td>
			</tr>
		</table>
		<a href="${contextPath}/admin/dashboard.htm"> Go Back</a>
	</form>
</body>
</html>