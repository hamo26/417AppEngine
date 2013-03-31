<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title></title>
<link rel="stylesheet" type="text/css" href="/style.css"/>
</head>

<body>
	<div id="header">
		<h1>H & G Auctions</h1>
	</div>
	<div id="login">
		<form method="post" action="login">
			<h2>Log in</h2>
			<div class="row">
				<label for="userNameInput" id="userName">User Name</label> 
				<input id="userNameInput" name="userName" type="text"/>
			</div>
			<div class="row">
				<label for="passwordInput" id="password">Password</label> 
				<input id="password" name="password" type="password"/>
			</div>
			<div class="row">
				<input type="submit" value="Login"/>
			</div>
		</form>
		<form method="post" action="register">
			<h2>or register</h2>
			<div class="row">
				<label for="userNameInput" id="userName">User Name</label> 
				<input id="userNameInput" name="userName" type="text"/>
			</div>
			<div class="row">
				<label for="passwordInput" id="password">Password</label> 
				<input id="passwordInput" name="password" type="password"/>
			</div>
			<div class="row">
				<label for="passwordInput" id="password">Retype Password</label> 
				<input id="retypedPasswordInput" name="retypedPassword" type="password"/>
			</div>
			<div class="row">
				<label for="firstNameInput" id="firstName">First Name</label> 
				<input id="firstNameInput" name="firstName" type="text"/>
			</div>
			<div class="row">
				<label for="lastNameInput" id="lastName">Last Name</label> 
				<input id="lastNameInput" name="lastName" type="text"/>
			</div>
			<div class="row">
				<label for="emailInput" id="emailInput">Email</label> 
				<input id="emailInput" name="email" type="text"/>
			</div>
			<div class="row">
				<input type="submit" value="Register"/>
			</div>
		</form>
</div>
</html>