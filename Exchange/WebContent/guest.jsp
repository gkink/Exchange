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
		var xml;
		var xmlHt;
		function searchFunction(){
			var searchValue = document.getElementById("iteminput").value;
			if(searchValue !== ""){
				xml = new XMLHttpRequest();
				xml.open("post", "guestItemSearch?item=" + document.getElementById("iteminput").value, true);
				xml.onreadystatechange = readyStateHandler;
				xml.send();				
			}else{
				document.getElementById("searchContent").innerHTML = "";				
			}
		}

		function readyStateHandler(){
			if(xml.readyState === 4 && xml.status === 200){
				document.getElementById("searchContent").innerHTML = xml.responseText;
			}
		}

		function readyforTransactios(){
			
		}
	</script>
</head>
<body>
	<header></header>
	<div id="main">
		<div id="left">
			<div id="searchbox">
				search a intm: <input id="iteminput" placeholder="Item" tabindex="1"  onkeyup="searchFunction()" onkeydown="searchFunction()" type="text">
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
			<div>
				<ul id="transactions">
				</ul>				
			</div>
		</div>
	</div>
</body>
</html>