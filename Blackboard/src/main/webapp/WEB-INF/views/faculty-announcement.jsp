<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty Announcements</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<a href="${contextPath}/faculty/createAnnouncement.htm">Add
		Announcement</a>
	<c:set var="announcements" value="${success}" scope="session" />

	<c:choose>
		<c:when test="${empty announcements}">
			<div>
				<p>No Announcement added yet.</p>
			</div>
		</c:when>
		<c:otherwise>
		<form action ="${contextPath}/faculty/removeAnnouncement.htm", method="post">
			<table border="5">
				<tr>
				<th></th>
					<th>ID</th>
					<th>Announcement</th>
					<th>Posted By</th>
					<th>Posted on</th>
					<th>Course</th>
					<th></th>
				</tr>
				<c:forEach items="${announcements}" var="dataText" varStatus="loop">
					<tr>
					<td><input type="checkbox" name="ids" value="${dataText.getId()}"></input></td>
						<td>${dataText.getId()}</td>
						<td>${dataText.getMessage()}</td>
						<td>${dataText.getPostedBy().toString()}</td>
						<td>${dataText.getCreateDateTime()}</td>
						<td>${dataText.getCourse().toString()}</td>
						<td><a
							href='${contextPath}/faculty/removeAnnouncement.htm?Id=${dataText.getId()}'>Remove
						</a></td>
					</tr>
				</c:forEach>
				<input type='hidden' name='action' value='remove'/>
			</table>
			<input type="submit" value="remove announcement(s)"/>
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
				href="${contextPath}/faculty/announcements.htm?page=${pageVal-5 < 1 ? 1 : pageVal-5}">
				<< </a>&nbsp;</c:if>

		<c:forEach begin="${pageVal}" end="${pageVal + 4}" var="val"
			varStatus="loop">
			<c:set var="startVal" value="${ROWS_TO_DISPLAY * (loop.index - 1)}"></c:set>
			<a
				href="${contextPath}/faculty/announcements.htm?page=${loop.index}&start=${startVal}">${loop.index}</a>&nbsp;&nbsp;
			<c:set var="nextVal" value="${ROWS_TO_DISPLAY * (loop.index)}"></c:set>
			<c:set var="nextPage" value="${loop.index + 1}"></c:set>
		</c:forEach>
		<a
			href="${contextPath}/faculty/announcements.htm?page=${nextPage}&start=${nextVal}">
			>></a>
	</c:if>
	
	
	<br>
	<a href="${contextPath}/faculty/dashboard.htm"> Go Back</a>
	<br>
	
</body>
</html>