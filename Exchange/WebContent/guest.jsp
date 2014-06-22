<%@page import="dbClasses.QueryExecutor"%>
<%@page import="dbClasses.DBqueryGenerator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Register your account</title>
	<link rel="stylesheet" href="styles.css">
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js.js"></script>
	<script type="text/javascript">
		var xml = new XMLHttpRequest();
		function searchFunction(){
			xml.open("post", "guestItemSearch?item=" + document.getElementById("iteminput").value, true);
			xml.onreadystatechange = readyStateHandler;
			xml.send();
		}
		
		function readyStateHandler(){
			if(xml.readyState === 4){
				document.getElementById("searchContent").innerHTML = "";
				var resp = xml.responseXML;
				var message = resp.getElementsByTagName("message")[0].childNodes[0].nodeValue;
				if(message === "ok"){
					generateSearchResults();					
				}else{
					document.getElementById("searchContent").innerHTML += message;
				}
			}
		}
		
		function generateSearchResults(){
			var resp = xml.responseXML;
			var idList = resp.getElementsByTagName("itemID");
			var nameList = resp.getElementsByTagName("itemName");
			for(var i = 0 ; i < idList.length ; i++){
				var id = idList[i].childNodes[0].nodeValue;
				var name = nameList[i].childNodes[0].nodeValue;				
				document.getElementById("searchContent").innerHTML += generateElem(id, name);
			}
		}
		
		function generateElem(id, name){
			var href = "http://localhost:8080/Exchange/item.jsp?id=" + id;
			return  "<li><a href= \" " + href + " \" >" + name + "</a></li>"; 
		}
		
		$(document).ready(function() {
			setTimeout(callback, delay)
		});


	</script>
</head>
<body>
	<header></header>
	<div id="main">
		<div id="left">
			<div id="searchbox">
				search a intm: <input id="iteminput" placeholder="Item" tabindex="1" type="text">
				<button type="button" onclick="searchFunction()">click</button>
			</div>
			<div>
				<ul id="searchContent">
				</ul>
			</div>
		</div>
		<div id="right">
			<div id="full_form">
				<div id="form_top">
					<div id="signup"><p id="signup">Sign Up</p></div>
					<div id="signin"><p id="signin">Sign In</p></div>
				</div>
				<div id="form_body">
					<form method="post" action="Register" id="first">
						<input name="firstName" placeholder="FirstName" required="required" tabindex="1" type="text">
						<input name="lastName" placeholder="LastName" required="required" tabindex="2" type="text">
						<input name="email" placeholder="Email" required="required" tabindex="3" type="text">
						<input name="password" placeholder="Password" required="required" tabindex="4" type="password">
						<input class="button" type="submit" value="Sign Up">
					</form>
					<form method="post" action="Register" id="second">
						<input name="email" placeholder="Email" required="required" tabindex="3" type="text">
						<input name="password" placeholder="Password" required="required" tabindex="4" type="password">
						<input class="button" type="submit" required="required" value="Sign In">
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>