/**
 * 
 */
var xml;
var xmlHt;
function searchFunction(){
var searchValue = document.getElementById("s").value;
if(searchValue !== ""){
	xml = new XMLHttpRequest();
	xml.open("post", "guestItemSearch?item=" + document.getElementById("s").value, true);
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

