<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="files" value="${success}" scope="session" />
	<c:choose>
		<c:when test="${empty files}">
			<div>
				<p>No Assignments added yet.</p>
			</div>
		</c:when>
		<c:otherwise>
			<table border="5">
				<tr>
					<th>ID</th>
					<th>Title</th>
					<th>Assignment Description</th>
					<th>Posted On</th>
					<th>Deadline</th>
					<th>Course</th>
				</tr>
				<c:forEach items="${success}" var="dataText" varStatus="loop">
					<tr>
						<td>${dataText.getId()}</td>
						<td>${dataText.getTitle()}</td>
						<td>${dataText.getDesc()}</td>
						<td>${dataText.getPostedOn()}</td>
						<td>${dataText.getDeadline()}</td>
						<td>${dataText.getCourse().toString()}</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<a href="${contextPath}/student/dashboard.htm"> Go Back</a>
</body>
</html>