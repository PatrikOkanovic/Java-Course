<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Colors</title>
</head>
<body bgcolor="${pickedBgCol}">
<h2>Pick your color</h2>
<a href = "setcolor?color=white">WHITE</a>
<a href = "setcolor?color=red">RED</a>
<a href = "setcolor?color=blue">BLUE</a>
<a href = "setcolor?color=green">GREEN</a>

</body>
</html>