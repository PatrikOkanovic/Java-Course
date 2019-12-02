<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  session="true"%>
 <%@ page import="java.util.Random" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

</head>
<body bgcolor="${pickedBgCol}">

<h2>A colour changing joke</h2>
	
<font color = "<%
			Random rand = new Random();
			int x = rand.nextInt()%3;
			if(x==0) {
				out.write("red");
			} else if(x == 1) {
				out.write("green");
			} else {
				out.write("blue");
			}
			%>">
Q: Why didn’t the sun go to college?
A: Because it already had a million degrees!</font>


</body>
</html>