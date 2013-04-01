<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.onlineauction.user.domain.entity.User"  %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title></title>
<link rel="stylesheet" type="text/css" href="/style.css"/>
</head>

<body>
	<div id="header">
		<h1>H & G Auctions</h1>
	</div>
	<div id="navigation">
		<h2>Contents</h2>
		<ul>
		<li><a href="/home/auctionHome.jsp">Home</a></li>
		<li><a href="/profile/profile.jsp">Profile</a></li>
		<li><a href="/auction/auctionSearch.jsp">Auction Search</a></li>
		<li><a href="/auction/createAuctionForm.jsp">Create Auction</a></li>
		<li><a href="/logout">Logout</a></li>
	</ul></div>
	<div id="main">
		<% User user = (User) request.getSession().getAttribute("user"); %>
		<h2>HG USER PROFILE</h2>
		<div class="row">
			<label id="userName">User Name: <%=user.getUserName() %></label> 
		</div>
		<div class="row">
			<label id="firstName">First Name: <%=user.getFirstName() %></label>
		</div>
		<div class="row">
			<label id="lastName">Last Name: <%=user.getLastName() %></label>
		</div>
		<div class="row">
			<label id="emailInput">Email: <%=user.getEmail() %></label>
		</div>
	</div>
</body>
</html>