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
	<br>
	
	<c:choose>
	<c:when test="${not empty sessionScope['current.user.nick']}">
		<c:if test="${sessionScope['current.user.nick'].equals(author)}">
			<form action="<% out.write(request.getContextPath());%>/servleti/editEntryBlog?id=${blogEntry.id}" method="post">
				
				<div>
					<span class="formLabel">Title</span><input type="text" name="title" value = "${blogEntry.title}"  size="20"/>
				</div>
				<div>
					<span class="formLabel">Text</span><input type="text" name="text" value = "${blogEntry.text}" size="50"/>
				</div>
				
				<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					name="method" value="Submit">
				</div>
			</form>
		</c:if>
		<c:if test="${!sessionScope['current.user.nick'].equals(author)}">
		<a style = color:red;>You do not have permision to do that</a>
		</c:if>
	</c:when>
		<c:otherwise>
		<a style = color:red;>You do not have permision to do that</a>
		<br>
		<br>
		</c:otherwise>
	</c:choose>
		


</body>
</html>