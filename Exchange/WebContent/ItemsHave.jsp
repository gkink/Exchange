<%@page import="guestSession.ItemsHaveObject"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="dbClasses.QueryExecutor"%>
<%@page import="dbClasses.DBqueryGenerator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="default.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript">
	function saveChanges(){
		var newDescription = document.getElementById("itemDesc").value;
		var newName = document.getElementById("itemName").value;
		
		window.location.replace("SaveChanges?itemName=" + newName + "&itemDesc=" + newDescription);
	}
	
	function returnToGuestMode(){
		window.location.replace("guest.jsp");		
	}
	
	function addToReals(){
		window.location.replace("AddToRealItems");				
	}
	
	function deleteItem(){
		window.location.replace("DeleteItem");				
	}
</script>
<title>Item</title>
</head>
<body>
<%
	boolean guest, usersItem = false;
	String guestSession = request.getParameter("guest");
	String editingAbility = "disabled";
	int itemId = Integer.parseInt(request.getParameter("itemId"));
	guest = guestSession != null;

	DBqueryGenerator generator =  new DBqueryGenerator();
	DataSource dataSource = (DataSource) request.getServletContext().getAttribute("DataSource");
	QueryExecutor executor =  new QueryExecutor(dataSource);
	ItemsHaveObject item =  new ItemsHaveObject(generator, executor, itemId);

	if(!guest){
		Integer userId = (Integer)request.getSession().getAttribute("User");
		usersItem = userId == item.getItemOwner();
	}
	if(usersItem)
		editingAbility = "";
	
	request.getSession().removeAttribute("itemId");
	request.getSession().setAttribute("itemId", itemId);
%>
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

<div id="page">
	<div id="thecontent">
		<br>
		<h1>Item Name</h1>
		<textarea rows="4" cols="50" id="itemName" <%= editingAbility  %>><%=item.getItemName()%></textarea>
		<br>
		<h1>Item Description</h1>
		<textarea rows="4" cols="50" id="itemDesc" <%= editingAbility  %>><%=item.getItemDescription()%></textarea>
	
	</div>
	<div id="buttons">
		<br>
		<%
		if(!guest){
			if(usersItem){
				out.println("<button id=\"delete_button\" onclick=\"deleteItem()\" ><p>Delete an item</p></button>");
				out.println("<br>");
				out.println("<button id=\"change_button\" onclick=\"saveChanges()\"><p>Save Changes</p></button>");
			}else{
				out.println("<button id=\"add_button\" onclick=\"addToReals()\" ><p>Add To Realitems</p></button>");				
			}
		}else
			out.println("<button id=\"return_button\" onclick=\"returnToGuestMode()\" ><p>Return To Guest Mode</p></button>");
		%>
	</div>
	
</div>
<!-- end page -->


<!-- start footer -->
<div id="footer">
	<p class="legal">
</div>


</body>
</html>