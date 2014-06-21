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
		

</div>
<!-- end menu -->
<!-- start page -->
<div id="page">
	
	<!-- start content -->
	<div id="content">
	<div class="postuser">
	<%
	HttpSession ses= request.getSession();
	Integer userId=(Integer)ses.getAttribute("User");
	DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
	QueryExecutor e=new QueryExecutor(dataSource);
	DBqueryGenerator gen= new DBqueryGenerator();
	User u =new User(e,gen,userId);
	out.print(u.getFirstName()+" "+u.getLastName());
	%>
	<div class="bubble"></div>
		</div>
		<div class="post">
								<div class="bubble">
					<div class="rectangle"><h2>Your Items</h2></div>
					<div class="triangle-l"></div>
					<div class="triangle-r"></div>
						<div class="info">
						<%
						QueryExecutor itemExecutor=new QueryExecutor(dataSource);
						DBqueryGenerator itemGen= new DBqueryGenerator();
						ItemContainer container= new ItemContainer( itemGen,itemExecutor);
						ArrayList<ItemsHaveObject> itemsHave=container.getUserItemsHave(userId);
						for(int i=0; i<itemsHave.size();i++){
							ItemsHaveObject cur=itemsHave.get(i);
							out.print("<p>");
							out.print("<a href=\"ItemsHave.jsp?id="+cur.getItemId()+"\">");
							out.print(cur.getItemName());
							out.print("</a>");
							out.print("</p>");
						}
						%>
							
							<p>
							<a href="AddItemsNeed.jsp">
							<input type="submit" name="addItemsHave" value="add item">
							</a>
							</p>
						</div>
					</div>
		</div>
		
							
		
	</div>
	<!-- end content -->
	<!-- start ads -->
	<div id="ads">
	<%
		ArrayList<ItemsHaveObject> latestItems=container.getLatestItems();
		for(int i=0; i<latestItems.size();i++){
			ItemsHaveObject cur=latestItems.get(i);
			if(cur.getItemOwner()!=userId){
				out.print("<p>");
				out.print("<a href=\"ItemsHave.jsp?id="+cur.getItemId()+"\">");
				out.print(cur.getItemName());
				out.print("</a>");
				out.print("</p>");
			}
			}
	%>
	</div>
	<!-- end ads -->
	<!-- start sidebar -->
	<div id="sidebar">
	
			<div class="bubble">
					<div class="rectangle"><h2>Items You Want</h2></div>
					<div class="triangle-l"></div>
					<div class="triangle-r"></div>
						<div class="info">
						<%
							ArrayList<ItemsNeedObject> itemsNeed=container.getUserItemsNeed(userId);
						for(int i=0; i<itemsNeed.size();i++){
							ItemsNeedObject cur=itemsNeed.get(i);
							out.print("<p>");
							out.print("<a href=\"ItemsNeed.jsp?id="+cur.getItemId()+"\">");
							out.print(cur.getItemName());
							out.print("</a>");
							out.print("</p>");
						}
						%>
							<p>
							<a href="AddItemsNeed.jsp">
							<input type="submit" name="addItemsHave" value="add item">
							</a>
							</p>
						</div>
					</div>
	</div>
	<div id="sidebar">
	
			<div class="bubble">
					<div class="rectangle"><h2>Items That are being searched for</h2></div>
					
						<div class="info-center">
							<%
							ArrayList<RealItemsObject> itemsReal=container.getUserItemsReal(userId);
						for(int i=0; i<itemsReal.size();i++){
							RealItemsObject cur=itemsReal.get(i);
							ItemsHaveObject exists=new ItemsHaveObject(gen, e,cur.getItemId());
							out.print("<p>");
							out.print("<a href=\"ItemsHave.jsp?id="+exists.getItemId()+"\">");
							out.print(exists.getItemName());
							out.print("</a>");
							out.print("</p>");
						}
						%>
							
						</div>
					</div>
	</div>
	<!-- end sidebar -->
</div>
<!-- end page -->
<!-- start footer -->
<div id="footer">
	<p class="legal">
			
</div>
</div>
<!-- end footer -->
</body>
</html>
