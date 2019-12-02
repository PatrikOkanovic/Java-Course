<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

</head>
<body>

 <h1>${poll.title}</h1>
 <p>${poll.message}</p>

<ol>
<c:forEach var="u" items="${pollOptions}">
<li><a href="glasanje-glasaj?pollID=${pollID }&id=${u.id}">${u.optionTitle}</a></li>
</c:forEach>
</ol>

</body>
</html>