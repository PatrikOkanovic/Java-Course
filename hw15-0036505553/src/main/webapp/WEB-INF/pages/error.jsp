<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  session="true"%>
 <%@ page import="java.util.Random" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

</head>


<h2>Error has been made!</h2>

<p>${errorMessage}</p>
<br>
<a href="<% out.write(request.getContextPath());%>/index.jsp">Main Page</a>

</body>
</html>