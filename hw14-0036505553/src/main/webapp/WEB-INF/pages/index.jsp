<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  session="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
</head>
<meta charset="UTF-8">

<h1>Choose your poll wisely</h1>

<ol>
<c:forEach var="u" items="${polls}">
<li><a href="glasanje?pollID=${u.id}">${u.title}</a></li>
</c:forEach>
</ol>

</body>
</html>