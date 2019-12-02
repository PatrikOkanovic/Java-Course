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
		<a href = "logout">Log out</a>
	</c:if>
	<c:if test="${empty sessionScope['current.user.id']}">
	<h3>Anonymous</h3>
	</c:if>
		

<p style=color:red;>${errorMessage}</p>


	<form action="register" method="post">
	
	
	<div>
		<span class="formLabel">First Name</span><input type="text" name="firstName" value="${user.firstName}" size="20">
		
	</div>
	
	<div>
		<span class="formLabel">Last Name</span><input type="text" name="lastName" value="${user.lastName}" size="20">
		
	</div>
	
	<div>
		<span class="formLabel">EMail</span><input type="text" name="email" value="${user.email}" size="20">
		
	</div>
	
	<div>
		<span class="formLabel">Nick</span><input type="text" name="nick" value="${user.nick}" size="20">
		
	</div>
	
	<div>
		<span class="formLabel">Password</span><input type="password" name="password"  size="20">
		
	</div>
	
	<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Register"> <input type="submit"
				name="method" value="Cancel">
	</div>
	
	</form>

</body>
</html>