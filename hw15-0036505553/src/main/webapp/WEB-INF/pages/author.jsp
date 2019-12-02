<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  session="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
</head>
<meta charset="UTF-8">
<body>

	<c:if test="${not empty sessionScope['current.user.id']}">
	<h3>Name: ${sessionScope["current.user.fn"]}</h3>
		<h3>Last name: ${sessionScope["current.user.ln"]}</h3>
		<h3>Nick: ${sessionScope["current.user.nick"]}</h3>
		<a href="<% out.write(request.getContextPath());%>/servleti/logout">Log out</a>
	</c:if>
	<c:if test="${empty sessionScope['current.user.id']}">
	<h3>Anonymous</h3>
	</c:if>
	<br>
	<a href="<% out.write(request.getContextPath());%>/index.jsp">Main page</a>
	
	<br>
	<h2>List of blog entries:</h2>
	<c:if test="${blogEntries!=null}">
		<ol>
		<c:forEach var="entry" items="${blogEntries}">
			<li><a href="<%=request.getContextPath()%>/servleti/author/${author }/${entry.id }">${entry.title }</a></li>
		</c:forEach>
		</ol>
	</c:if>
	
	<c:if test="${not empty sessionScope['current.user.nick']}">
		<c:if test="${sessionScope['current.user.nick'].equals(author)}">
			<a href="<%=request.getContextPath()%>/servleti/author/${author }/new">Add new blog</a>
		</c:if>
	</c:if>


</body>
</html>