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
	<a href = "<%=request.getContextPath() %>/servleti/main">Main page</a>
	<br>
	<h2> ${blogEntry.title} </h2>
	<p> ${blogEntry.text} </p>
	
	<c:if test="${not empty sessionScope['current.user.nick']}">
		<c:if test="${sessionScope['current.user.nick'].equals(author)}">
			<a href="<%=request.getContextPath() %>/servleti/author/${author }/edit?id=${blogEntry.id}" >Edit</a>
		</c:if>
	</c:if>
		
	<ul>
		<c:forEach var="comments" items="${comments}">
			<li><c:out value="${comments.message }"/> 
			<c:out value="${comments.postedOn }"/>
			<c:out value="${comments.usersEMail }"/>
		</c:forEach>
	</ul>
	<c:choose>
	<c:when test="${not empty sessionScope['current.user.nick']}">
		
	<form action="<%=request.getContextPath() %>/servleti/addComment?nick=${author }&id=${blogEntry.id }" method="post">
				
				<div>
					<span class="formLabel">Comment</span><textarea rows="5" cols="40" name="comment"></textarea>
				</div>
								
				<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					name="method" value="Submit">
				</div>
	</form>

	</c:when>
	<c:otherwise>
	<form action="<%=request.getContextPath() %>/servleti/addComment?nick=${author }&id=${blogEntry.id }" method="post">
				
				<div>
					<span class="formLabel"></span>Comment<textarea name="Comment" rows="5" cols="40" name="comment"></textarea>
				</div>
				
				<div>
					<span class="formLabel">EMail</span><input type="text" name="EMail"  size="100"/>
				</div>
								
				<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					name="method" value="Submit">
				</div>
	</form>
	
	</c:otherwise>
	</c:choose>

</body>
</html>