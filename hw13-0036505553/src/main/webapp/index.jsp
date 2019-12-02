<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body bgColor="${pickedBgCol}">
<h1>Home</h1>

<a href = "setcolor">Background color chooser</a>
<hr>

<a href="trigonometric?a=0&b=90">Trigonometric</a>

<form action="trigonometric" method="GET">
 Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
</form>

<hr>

<a href="stories/funny.jsp">Funny story</a>

<hr>

<a href="powers?a=1&b=100&n=3">Powers</a>

</body>
</html>