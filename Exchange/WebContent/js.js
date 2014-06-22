$(document).ready(function() {
	
	$("p#signup").click(function(){
		$("form#first").css("display", "block");
		$("form#second").css("display", "none");
		$("div#signup").css("background-color", "rgb(255, 255, 255)");
		$("div#signin").css("background-color", "rgb(230, 231, 232)");
	});

	$("p#signin").click(function (){
		$("form#first").css("display", "none");
		$("form#second").css("display", "block");
		$("div#signup").css("background-color", "rgb(230, 231, 232)");
		$("div#signin").css("background-color", "rgb(255, 255, 255)");
	});
});

