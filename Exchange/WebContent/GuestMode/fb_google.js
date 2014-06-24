/**
 * 
 */
  // This is called with the results from from FB.getLoginStatus().
 function statusChangeCallback(response) {
   console.log('statusChangeCallback');
    console.log(response);
 
    if (response.status === 'connected') {
        FB.api('/me', function(response) {
        	document.getElementById('fb_name').value =  response.first_name;
        	document.getElementById('fb_lastName').value =  response.last_name;
        	document.getElementById('fb_email').value =  response.email;
        	document.getElementById('fb_login').submit();    	
        });
   } else if (response.status === 'not_authorized') {
    	alert("You are not authrized, please log in with our account!");
    } else {
    }
  }

  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

  window.fbAsyncInit = function() {
  FB.init({
    appId      : '579400822176329',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.0' // use version 2.0
  });

  FB.getLoginStatus(function(response) {
//    statusChangeCallback(response);
  });

  };

  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));
  
  
  (function() {
      var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
      po.src = 'https://apis.google.com/js/client:plusone.js';
      var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
    })();
     
     function signinCallback(authResult) {
   	  if (authResult['status']['signed_in']) {
   		 gapi.client.load('plus', 'v1');
   	    var request = gapi.client.plus.people.get({
 				'userId' : 'me'
			});

			request.execute(function(resp) {
				console.log('ID: ' + resp.id);
 				console.log('Display Name: ' + resp.displayName);
 				console.log('Image URL: ' + resp.image.url);
 				console.log('Profile URL: ' + resp.url);
			});
   	    document.getElementById('signinButton').setAttribute('style', 'display: none');
   	  } else {
   	    // Update the app to reflect a signed out user
   	    // Possible error values:
   	    //   "user_signed_out" - User is signed-out
   	    //   "access_denied" - User denied access to your app
   	    //   "immediate_failed" - Could not automatically log in the user
   	    console.log('Sign-in state: ' + authResult['error']);
   	  }
   	}


 
 