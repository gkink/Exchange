<%@page import="guestSession.TransactionInterface"%>
<%@page import="guestSession.Transaction"%>
<%@page import="guestSession.TransactionContainer2"%>
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
HttpSession ses= request.getSession();
Integer userId=(Integer)ses.getAttribute("User");
TransactionContainer2 container=new TransactionContainer2(e,g,userId);
for(int i=0;i<container.size();i++){
	TransactionInterface cur=container.getTransaction(i);

}


%>
</div>
<!-- end footer -->
</body>
</html>
