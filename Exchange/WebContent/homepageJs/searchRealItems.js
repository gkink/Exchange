/**
 * 
 */
var xml;
var xmlHt;

//drois mixedvit gashveba shen daamate

function searchFunction(){
	xml = new XMLHttpRequest();
	xml.open("post", "ItemSearchForUser", true);
	xml.onreadystatechange = readyStateHandler;
	xml.send();				
}
function readyStateHandler(){
	if(xml.readyState === 4 && xml.status === 200){
		//amas shecvli, htm tagis id-s chauwer, romelic savaraudod <ul> iqneba
		document.getElementById("searchContent").innerHTML = xml.responseText;
	}
}

