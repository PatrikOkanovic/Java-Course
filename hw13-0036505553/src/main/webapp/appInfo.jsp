<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  session="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

</head>
<body bgcolor="${pickedBgCol}">

<h1>App info</h1>

This application has been running
<% 
	long startedAt = (long) getServletContext().getAttribute("startedAt");
	long currentTime = System.currentTimeMillis();
	long interval = currentTime - startedAt;
	long days =  interval / (1000 * 60 * 60 * 24);
	interval = interval - days * (1000 * 60 * 60 * 24);
	long hours = interval / (1000 * 60 * 60);
	interval = interval - hours * (1000 * 60 * 60);
	long minutes = interval / (1000 * 60);
	interval = interval - minutes * (1000 * 60);
	long seconds = interval / 1000;
	long miliseconds = interval  - seconds * 1000;
	
	out.write(" " + days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds "
			+ miliseconds + " miliseconds");
	
%>


</body>
</html>