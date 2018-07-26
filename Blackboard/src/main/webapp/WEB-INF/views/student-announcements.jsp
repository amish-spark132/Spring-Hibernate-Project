<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Announcements</title>
</head>
<body>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="announcements" value="${success}" scope="session" />
	<c:choose>
		<c:when test="${empty announcements}">
			<div>
				<p>No Announcement posted yet.</p>
			</div>
		</c:when>
		<c:otherwise>
			<form action="${contextPath}/student/downloadAnnouncements.htm"
				, method="post">
				<table border="5">
					<tr>
						<th>ID</th>
						<th>Message</th>
						<th>Posted On</th>
						<th>Course</th>
						<th>Posted By</th>
					</tr>
					<c:forEach items="${announcements}" var="dataText" varStatus="loop">
						<tr>
							<td>${dataText.getId()}</td>
							<td>${dataText.getMessage()}</td>
							<td>${dataText.getCreateDateTime()}</td>
							<td>${dataText.getCourse()}</td>
							<td>${dataText.getPostedBy()}</td>

						</tr>
					</c:forEach>
				</table>
				<input type="submit" value="Download Announcements" />
			</form>
		</c:otherwise>
	</c:choose>

	<c:if test="${sessionScope.ROWS_TO_DISPLAY != null}">
		<c:set var="ROWS_TO_DISPLAY" value="${sessionScope.ROWS_TO_DISPLAY}" />
	</c:if>
	<c:if test="${sessionScope.rowCount != null}">
		<c:set var="numberOfRows" value="${sessionScope.rowCount}" />
		<c:choose>
			<c:when test="${param['page'] == null}">
				<c:set var="pageVal" value="1" />
			</c:when>
			<c:when test="${param['page'] != null}">
				<c:set var="pageVal" value="${param['page']}" />
			</c:when>
		</c:choose>
		<c:if test="${pageVal > 1}">
			<a
				href="${contextPath}/student/announcements.htm?page=${pageVal-5 < 1 ? 1 : pageVal-5}">
				<< </a>&nbsp;</c:if>

		<c:forEach begin="${pageVal}" end="${pageVal + 4}" var="val"
			varStatus="loop">
			<c:set var="startVal" value="${ROWS_TO_DISPLAY * (loop.index - 1)}"></c:set>
			<a
				href="${contextPath}/student/announcements.htm?page=${loop.index}&start=${startVal}">${loop.index}</a>&nbsp;&nbsp;
			<c:set var="nextVal" value="${ROWS_TO_DISPLAY * (loop.index)}"></c:set>
			<c:set var="nextPage" value="${loop.index + 1}"></c:set>
		</c:forEach>
		<a
			href="${contextPath}/student/announcements.htm?page=${nextPage}&start=${nextVal}">
			>></a>
	</c:if>

<br>
	<a href="${contextPath}/student/dashboard.htm"> Go Back</a>
</body>
</html>