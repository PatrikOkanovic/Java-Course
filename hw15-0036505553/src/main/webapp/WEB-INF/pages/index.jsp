<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  session="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
</head>
<meta charset="UTF-8">
<body>
<h1>Hello Bloggers!</h1>

<div align="left">
<c:choose>
	<c:when test="${empty sessionScope['current.user.id']}">
			<form action="login" method="post">
			<div>
				<span class="formLabel">Nickname</span><input type="text" name="nick" value='<c:out value="${previousNick}"/>' size="5">
			</div>
			<div>
				<span class="formLabel">Password</span><input type="password" name="password"  size="20">
		
			</div>
			<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Login"> 
			</div>
			</form>
			<p style=color:red;>${message}</p>
	</c:when>
	<c:otherwise>
		<h3>Name: ${sessionScope["current.user.fn"]}</h3>
		<h3>Last name: ${sessionScope["current.user.ln"]}</h3>
		<h3>Nick: ${sessionScope["current.user.nick"]}</h3>
		<a href = "logout">Log out</a>
	</c:otherwise>
</c:choose>
</div>
	
	<br>
	<a href = "register">Register</a>
	<br>
	<h2>List of registered bloggers:</h2>
	<c:if test="${blogUsers!=null}">
	<ul>
		<c:forEach var="author" items="${blogUsers}">
			<li><c:out value="${author.firstName }"/>
			<c:out value="${author.lastName }"/>
			<a href="author/${author.nick }">${author.nick}</a></li>
		</c:forEach>
	</ul>
	</c:if>

</body>
</html>