<%@page import="ModelClasses.Pair"%>
<%@page import="ModelClasses.Cycle"%>
<%@page import="guestSession.RealItemsObject"%>
<%@page import="UnitTests.realItemsTest"%>
<%@page import="guestSession.ItemsNeedObject"%>
<%@page import="guestSession.ItemsHaveObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="guestSession.ItemContainer"%>
<%@page import="ModelClasses.User"%>
<%@page import="dbClasses.DBqueryGenerator"%>
<%@page import="dbClasses.QueryExecutor"%>
<%@page import="javax.sql.DataSource"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC>

<html >
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Exchan.ge</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="default.css" rel="stylesheet" type="text/css" media="screen" />

<!-- Beginning of compulsory code below -->

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
<!-- end header -->
<!-- star menu -->
<div id="menu">
		<form id="searchform" name="search" method="post" action="LogoutServlet">
		<input type="submit" id="bla" value="Logout" />
		
</form>

</div>
<!-- end menu -->
<!-- start page -->
<div id="page">


<%
DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
QueryExecutor e=new QueryExecutor(dataSource);
DBqueryGenerator g= new DBqueryGenerator();
String id=request.getParameter("id");
String type=request.getParameter("accept");
String itemStr=request.getParameter("itemId");
Integer aOrD=Integer.parseInt(type);
Integer cycleId=Integer.parseInt(id);
Integer itemID=Integer.parseInt(itemStr);
Cycle c=new Cycle(e,g,cycleId);
if(aOrD==0){
	c.accept(itemID);
	out.print("<p> You Have Accepted The Cycle");
	
}else{
	c.delete();
	out.print("<p> You Have Rejected The Cycle");
	
}


%>
</div>
<!-- end footer -->
</body>
</html>
