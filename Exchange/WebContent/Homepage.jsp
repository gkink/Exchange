<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="ModelClasses.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="user">
	<%
	User u= (User)request.getAttribute("User");
	out.print("<p class=\"userName\">"+u.getFirstName() +" "+u.getLastName()+"</p>");
	
	%> 
	</div>

</body>
</html>