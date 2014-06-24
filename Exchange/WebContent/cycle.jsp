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
HttpSession ses= request.getSession();
Integer userId=(Integer)ses.getAttribute("User");
DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
QueryExecutor e=new QueryExecutor(dataSource);
DBqueryGenerator g= new DBqueryGenerator();
String id=request.getParameter("id");
Integer cycleId=Integer.parseInt(id);
Cycle c=new Cycle(e,g,cycleId);

ItemsHaveObject myItem=null;
if(c==null) out.print("<p> This Cycle has been Deleted</p>");
else{
	for(int i=0;i<c.cycleSize();i++){
		Pair<User,ItemsHaveObject> cur=c.getUserItemPair(i);
		User curUser=cur.getFirst();
		ItemsHaveObject curItem=cur.getSecond();
		int curItemId=curItem.getItemId();
		int curUserId=curUser.getId();
		
		String itemName=curItem.getItemName();
		String userName=curUser.getFirstName()+" "+curUser.getLastName();
		if(curUserId==userId){
			userName="You";
			myItem=curItem;
		}
		String accept="Waiting for response.";
		if(c.userAccepted(curUserId)) accept="Has accepted the cycle.";
		
		out.print("<p>");
		out.print("Item: ");
		out.print("<a href=\"items.jsp?id="+curItemId+"\">"+itemName+"</a>");
		out.print(" belongs to: ");
		out.print("<a href=\"UserPage.jsp?User="+curUserId+"\">"+userName+"</a>"+" - "+accept);
		out.print("</p>");
	}
		if(c.userAccepted(userId)){
			out.print("You Have Already Accepted This Cycle. Waiting For Others.");
			out.print("<a href=\"CycleAnswer.jsp?id="+c.getID()+"&accept=1"+"&itemId="+myItem.getItemId()+"\">");
			out.print("<input type=\"submit\" name=\"rejectCycle\" value=\"reject\">");
			out.print("</a>");
		}else{
			out.print("<a href=\"CycleAnswer.jsp?id="+c.getID()+"&accept=0"+"&itemId="+myItem.getItemId()+"\">");
			out.print("<input type=\"submit\" name=\"acceptCycle\" value=\"accept\">");
			out.print("</a>");
			out.print("<a href=\"CycleAnswer.jsp?id="+c.getID()+"&accept=1"+"&itemId="+myItem.getItemId()+"\">");
			out.print("<input type=\"submit\" name=\"rejectCycle\" value=\"reject\">");
			out.print("</a>");
			
		}
}
%>
<div id="code">
<script>

function acceptFunction(){
	
	
	
	
	
}

</script>
</div>
</div>
<!-- end footer -->
</body>
</html>
