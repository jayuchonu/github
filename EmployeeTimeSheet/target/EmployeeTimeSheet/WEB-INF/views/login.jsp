<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Login</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" type="image/png" href="/favicon.png" />
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
</head>
<body>
	<div id="login">
		<h3 class="text-center text-white pt-5">Login</h3>
		<div class="container">
			<div id="login-row"
				class="row justify-content-center align-items-center">
				<div id="login-column" class="col-md-6">
					<div id="login-box" class="col-md-12">
						<form action="login.htm" method="post" onsubmit="return validateform()" >
							<h3 class="text-center text-info">${applicationName}</h3>
							<div class="form-group">
								 <label for="usrname"class="text-info">Username</label>
							    <input type="text" id="username" name="username" maxlength="20" class="form-control">
								<span id="username1" class="text-danger font-weight-bold"></span>
							</div>
							<div class="form-group">
								<label for="password" class="text-info">Password:</label><br>
								<input type="password" id="password" name="password"  maxlength="8"
									class="form-control" >
									<span id="password1" class="text-danger font-weight-bold"></span>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-default">Login</button>
							</div>
							<span id="message" class="text-danger font-weight-bold">${msg}</span>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script>
function validateform(){
	var username=document.getElementById('username').value;
	var password=document.getElementById('password').value;
	  var valid=true;
	  
	   if((username == "")||(username == undefined)){
		  document.getElementById("username1").innerHTML = "**please fill the username";
		  return false;
	  }
	  if((username.length<3)||(username.length>20)){
		  document.getElementById("username1").innerHTML = "**please fill the username between 3 to 20 characters";
		  return false;
	  }
	  if((password == "")||(password == undefined)){
		  document.getElementById("password1").innerHTML = "**please fill the password";
		  return false;
	  }
	  if((password.length<6)||(password.length>20)){
		  document.getElementById("password1").innerHTML = "**please fill the password between  6 to 20 characters";
	  return false;
	  }
	  return true;
}

function login(){
	var result = validateform();
	if(result == true){
	var username=$('#username').val();
	var password=$('#password').val();
$.ajax({
    url: 'login.htm',
    type: "POST",
    async: false,
    data: {
	 username:username,
	 password:password
    },
  success: function(result) {
		var obj= JSON.parse(result);
		console.log(obj);
		
		if(obj.msg == "login fail"){
			alert("login failed");
		}
		else if(obj.msg == "loginsuccessful"){
				window.location.href = ("employees");
			}
			
		}
});
}
}
</script>
</body>
</html>




