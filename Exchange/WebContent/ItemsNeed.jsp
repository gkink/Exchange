<%@page import="guestSession.ItemsNeedObject"%>
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
	function changeItemsNeed(){
		window.location.replace("DeleteItemsNeed?name=" + document.getElementById("itemName").value);
	}
	
	function deleteItemsNeed(){
		window.location.replace("DeleteItemsNeed");
	}
</script>
<title>Item</title>
</head>
<body>
<%
	int itemId = Integer.parseInt(request.getParameter("itemId"));

	DBqueryGenerator generator =  new DBqueryGenerator();
	DataSource dataSource = (DataSource) request.getServletContext().getAttribute("DataSource");
	QueryExecutor executor =  new QueryExecutor(dataSource);
	ItemsNeedObject item =  new ItemsNeedObject(generator, executor, itemId);

	request.getSession().removeAttribute("itemsNeedId");
	request.getSession().setAttribute("itemsNeedId", itemId);
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
		<textarea rows="4" cols="50" id="itemName" ><%=item.getItemName()%></textarea>
		<br>
	</div>
	<div id="buttons">
		<br>
		<button onclick="changeItemsNeed()">Save Changes</button>
		<br>
		<button onclick="deleteItemsNeed()">Delete</button>
	</div>
	
</div>
<!-- end page -->
<div id="footer">
	<p class="legal">
</div>

</body>
</html>