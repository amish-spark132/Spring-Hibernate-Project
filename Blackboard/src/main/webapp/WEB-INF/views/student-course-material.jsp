<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student course material</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="files" value="${success}" scope="session" />
	<c:choose>
		<c:when test="${empty files}">
			<div>
				<p>No Files added yet.</p>
			</div>
		</c:when>
		<c:otherwise>
			<table border="5">
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>File Description</th>
					<th>Posted On</th>
					
					<th>Posted By</th>
					<th>Download</th>
					
				</tr>
				<c:forEach items="${files}" var="dataText">
					<tr>
						<td>${dataText.getId()}</td>
						<td>${dataText.getFileName()}</td>
						<td>${dataText.getFileDesc()}</td>
						<td>${dataText.getUploadedOn()}</td>
						
						<td>${dataText.getUploadedBy()}</td>
						<td><a href="${contextPath}/student/course-material-${dataText.getId()}.htm">Download</a></td>
						
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<a href="${contextPath}/student/dashboard.htm">Go Back</a>
</body>
</html>