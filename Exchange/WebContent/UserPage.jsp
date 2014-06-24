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
	
	<!-- start content -->
	<div id="content">
	
	
	<div class="postuser">
	<%
	
	String notInt=request.getParameter("User");
	int userId=Integer.parseInt(notInt);
	DataSource dataSource = (DataSource) request.getServletContext().getAttribute("DataSource");
	QueryExecutor e = new QueryExecutor(dataSource);
	DBqueryGenerator gen = new DBqueryGenerator();
	User u = new User(e, gen, userId);
	out.print(u.getFirstName() + " " + u.getLastName());
%>

	
	
		</div>
		<div class="post">
								<div class="bubble">
					<div class="rectangle"><h2>User's Items</h2></div>
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
							
						</div>
					</div>
		</div>
		
							
		
	</div>
	<!-- end content -->
	<!-- start ads -->
	<div id="ads">
	<div class="bubble" >
	<div class="rectangle"><h2>Newly Added Items</h2></div>
	<div class="info" id="ad">
	<script type="text/javascript">
			   var xml = new XMLHttpRequest();
			   var run=latestItemsShower();
               var myVar=setInterval(function(){latestItemsShower()},1000000);
               function latestItemsShower(){
                       xml.open("post", "ItemUpdaterServlet" , true);
                       xml.onreadystatechange = readyStateHandler;
                       xml.send();
               }
               
               function readyStateHandler(){
                       if(xml.readyState === 4){
                               document.getElementById("ad").innerHTML = "";
                               generateSearchResults("ad");                                        
                                
                       }
               }
               function generateSearchResults(type){
                   var resp = xml.responseXML;
                   var idList = resp.getElementsByTagName("itemID");
                   var nameList = resp.getElementsByTagName("itemName");
                   for(var i = 0 ; i < idList.length ; i++){
                           var id = idList[i].childNodes[0].nodeValue;
                           var name = nameList[i].childNodes[0].nodeValue;                                
                           document.getElementById(type).innerHTML += generateElem(id, name);
                   }
           }
               function generateElem(id, name){
                   var href = "http://localhost:8080/Exchange/item.jsp?id=" + id;
                   return  "<p><a href= \" " + href + " \" >" + name + "</a></p>"; 
           }
               
       </script>        
	</div>
	</div>
	</div>
	<!-- end ads -->
	<!-- start sidebar -->
	<div id="sidebar">
			<div class="bubble">
					<div class="rectangle"><h2>Items User Wants</h2></div>
					<div class="triangle-l"></div>
					<div class="triangle-r"></div>
						<div class="info">
						<%
						 	ArrayList<ItemsNeedObject> itemsNeed=container.getUserItemsNeed(userId);
								for(int j=0;j<itemsNeed.size();j++){
								out.print("<p >"+itemsNeed.get(j).getItemName()+"</p>");
							}
							
						%> 
									
							
						</div>
					</div>
					<div class="bubble">
					<div class="rectangle"><h2>Items That are being searched for</h2></div>
					<div class="triangle-l"></div>
					<div class="triangle-r"></div>
						<div class="info">
						<%
						ArrayList<RealItemsObject> realItems=container.getUserItemsReal(u.getId());
						for(int j=0;j<realItems.size();j++){
							ItemsHaveObject cur=new ItemsHaveObject(gen,e,realItems.get(j).getItemId());
							out.print("<p>"+"<a href=\"items.jsp?ID="+cur.getItemId()+"\">"+cur.getItemName()+"</a>"+"</p>");
						}
							
						%> 
									
							
						</div>
					</div>
					
			
	</div>
	
	
					
		
	<!-- end sidebar -->

<!-- end page -->
<!-- start footer -->
<div id="footer">
	<p class="legal">
			
</div>
</div>


<!-- end footer -->
</body>
</html>
