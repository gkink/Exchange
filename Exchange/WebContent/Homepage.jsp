<%@page import="guestSession.RealItemsObject"%>
<%@page import="guestSession.ItemsNeedObject"%>
<%@page import="guestSession.ItemsHaveObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dbClasses.QueryExecutor"%>
<%@page import="dbClasses.DBqueryGenerator"%>
<%@page import="guestSession.ItemContainer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="ModelClasses.User"%>
    <%@page import="guestSession.ItemContainer"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="top"><div id="topLogo"><a href="Homepage.jsp">Exchan.ge</a></div>
	<p id="search"><input type="text" name="search" value="" placeholder="Search"></p>
	
	</div>
	<div class="user">
	<%
	DBqueryGenerator g=(DBqueryGenerator)request.getAttribute("generator");
	QueryExecutor e=(QueryExecutor)request.getAttribute("executor");
	ItemContainer i=new ItemContainer(g,e);
	User u= (User)request.getAttribute("User");
	out.print("<p class=\"userName\">"+u.getFirstName() +" "+u.getLastName()+"</p>");
	%> 
	</div>
	<div class="items">
	<%
 	ArrayList<ItemsHaveObject> itemsHave=i.getUserItemsHave(u.getId());
	out.print("<p class=\"itemsHave\"> Your Items<p>");

	out.print("<ul class=\"itemsHave\">");
	for(int j=0;j<itemsHave.size();j++){
		out.print("<li class=\"itemsHaveElem\">"+itemsHave.get(j).getItemName()+"</li>");
	}
	out.print("<p class=\"addItemsHave\"><input type=\"submit\" name=\"addItemsHave\" value=\"add item\"></p>");

	out.print("</ul>");
	%> 
	<%
 	ArrayList<ItemsNeedObject> itemsNeed=i.getUserItemsNeed(u.getId());
	out.print("<p class=\"itemsNeed\"> Items You Want<p>");
	out.print("<ul class=\"itemsNeed\">");
	for(int j=0;j<itemsNeed.size();j++){
		out.print("<li class=\"itemsNeedElem\">"+itemsNeed.get(j).getItemName()+"</li>");
	}
	out.print("</ul>");
	%> 
	<%
 	ArrayList<RealItemsObject> realItems=i.getUserItemsReal(u.getId());
	out.print("<p class=\"realItems\"> Items That are being searched for<p>");
	out.print("<ul class=\"realItems\">");
	for(int j=0;j<realItems.size();j++){
		ItemsHaveObject cur=new ItemsHaveObject(g,e,realItems.get(j).getItemId());
	//	User owner=new User(g,e,cur.getItemOwner());
		out.print("<li class=\"realItemsElem\">"+cur.getItemName()+"</li>");
	}
	out.print("</ul>");
	%> 
	<%
 	ArrayList<ItemsHaveObject> latestItems=i.getLatestItems();
	out.print("<ul class=\"latestItems\">");
	for(int j=0;j<latestItems.size();j++){
		out.print("<li class=\"latestItemsElem\">"+latestItems.get(j).getItemName()+"</li>");
	}
	//<a href="LoginFile.html" onclick="javascript:void window.open('LoginFile.html','1403193187013','width=200,height=100,toolbar=0,menubar=0,location=0,status=1,scrollbars=1,resizable=1,left=0,top=0');return false;">Pop-up Window</a>

	out.print("</ul>");
	%> 
	
	</div>

</body>
</html>