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
							<a href="AddItemsHavejsp">
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
                               document.getElementById("ads").innerHTML = "";
                               generateSearchResults("ads");                                        
                                
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
	<!-- end ads -->
	<!-- start sidebar -->
	<div id="sidebar">
			<div class="bubble">
					<div class="rectangle"><h2>Items You Want</h2></div>
					<div class="triangle-l"></div>
					<div class="triangle-r"></div>
						<div class="info" id="itemsNeed">
						<script>
						   var xm = new XMLHttpRequest();
						   var run1=ItemNeedShower();
			               var myVar1=setInterval(function(){ItemNeedSHower()},1000000);
			               function ItemNeedShower(){
			                       xm.open("post", "ItemNeedUpdater" , true);
			                       xm.onreadystatechange = readyStateHandler1;
			                       xm.send();
			               }
			               
			               function readyStateHandler1(){
			                       if(xm.readyState === 4){
			                               document.getElementById("itemsNeed").innerHTML = "";
			                               generateSearchResults1("itemsNeed");                                        
			                                
			                       }
			               }
			               function generateSearchResults1(type){
			                   var resp = xm.responseXML;
			                   var idList = resp.getElementsByTagName("itemID");
			                   var nameList = resp.getElementsByTagName("itemName");
			                   for(var i = 0 ; i < idList.length ; i++){
			                           var id = idList[i].childNodes[0].nodeValue;
			                           var name = nameList[i].childNodes[0].nodeValue;                                
			                           document.getElementById(type).innerHTML += generateElem1(id, name);
			                   }
			           }
			               function generateElem1(id, name){
			                   var href = "http://localhost:8080/Exchange/itemsNeed.jsp?id=" + id;
			                   return  "<p><a href= \" " + href + " \" >" + name + "</a></p>"; 
			           }
			               
			               
			       </script>  
									
							
						</div>
						<p>
							<a href="AddItemsHavejsp">
							<input type="submit" name="addItemsHave" value="add item">
							</a>
							</p>
							
					</div>
					
	</div>
	<div id="sidebar">
	
			<div class="bubble">
					<div class="rectangle"><h2>Items That are being searched for</h2></div>
					
						<div class="info-center" id="realItems">
						<script>
						   var xml1 = new XMLHttpRequest();
						   var run1=ItemShower();
			               var myVar1=setInterval(function(){ItemSHower()},1000000);
			               function ItemShower(){
			                       xml1.open("post", "RealItemUpdater" , true);
			                       xml1.onreadystatechange = readyStateHandler2;
			                       xml1.send();
			               }
			               
			               function readyStateHandler2(){
			                       if(xml1.readyState === 4){
			                               document.getElementById("realItems").innerHTML = "";
			                               generateSearchResults2("realItems");                                        
			                                
			                       }
			               }
			               function generateSearchResults2(type){
			                   var resp = xml1.responseXML;
			                   var idList = resp.getElementsByTagName("itemID");
			                   var nameList = resp.getElementsByTagName("itemName");
			                   for(var i = 0 ; i < idList.length ; i++){
			                           var id = idList[i].childNodes[0].nodeValue;
			                           var name = nameList[i].childNodes[0].nodeValue;                                
			                           document.getElementById(type).innerHTML += generateElem2(id, name);
			                   }
			           }
			               function generateElem2(id, name){
			                   var href = "http://localhost:8080/Exchange/item.jsp?id=" + id;
			                   return  "<p><a href= \" " + href + " \" >" + name + "</a></p>"; 
			           }
			               
			               
			       </script>  
							
							
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
