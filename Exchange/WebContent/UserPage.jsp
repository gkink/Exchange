<%@page import="ModelClasses.User"%>
<%@page import="dbClasses.DBqueryGenerator"%>
<%@page import="dbClasses.QueryExecutor"%>
<%@page import="javax.sql.DataSource"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="default.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<!-- start header -->
<div id="wrapper">
<div id="header">
	<div id="logo">
		<h1><a href="NewHomepage.jsp">Exchan.ge</a></h1>
		
	</div>
	<div id="rss"><img src="images/sosial media.jpg" width="250px" height="110px"></div>
	<div id="search">
	
		<form id="searchform" name="search" method="get" action="">
			<fieldset>
				<input type="text" name="s" id="s" size="15" value="" placeholder="Search" />
				<input type="submit" id="x" value="Search" />
			</fieldset>
		</form>
	</div>
</div>
</div>
<!-- end header -->
<!-- star menu -->
<div id="menu">
		

</div>
<!-- end menu -->
<!-- start page -->
<div id="page">
<div id="content">
	<div class="postuser">
	<%
		String notInt=request.getParameter("User");
		int userId=Integer.parseInt(notInt);
		DataSource dataSource = (DataSource) request.getServletContext()
				.getAttribute("DataSource");
		QueryExecutor e = new QueryExecutor(dataSource);
		DBqueryGenerator gen = new DBqueryGenerator();
		User u = new User(e, gen, userId);
		out.print(u.getFirstName() + " " + u.getLastName());
	%>

</div>
</div>
</div>

</body>
</html>