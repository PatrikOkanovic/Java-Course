<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

</head>
<body bgcolor="${pickedBgCol}">

 <h1>Glasanje za omiljeni bend:</h1>
 <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
glasali!</p>

<ol>
<c:forEach var="u" items="${bendovi}">
<li><a href="glasanje-glasaj?id=${u.key}">${u.value[0]}</a></li>
</c:forEach>
</ol>

</body>
</html>