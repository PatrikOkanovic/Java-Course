<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
th, td {
  padding: 5px;
  text-align: left;
}
</style>
<meta charset="UTF-8">
</head>
<body bgcolor="${pickedBgCol}">


<table>
   <tr><td>Value</td><td>Sin</td><td>Cos</td></tr>
   <c:forEach var="u" items="${map}">

    <tr><td>${u.key}</td><td> ${u.value[0]}</td><td> ${u.value[1]}</td></tr>
    </c:forEach>
</table>


</body>
</html>