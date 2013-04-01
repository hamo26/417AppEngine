<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.onlineauction.auction.domain.entity.Auction"  %>
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
	<table><tr><td>
		<form method="post" action="searchAuction">
			<h2>HG AUCTION SEARCH AUCTION</h2>
			<div class="row">
				<label for="itemNameInput" id="itemName">Item Name</label> 
				<input id="itemNameInput" name="itemName" type="text"/>
			</div>
			<div class="row">
				<input type="submit" value="Submit"/>
			</div>
		</form>
	
		<% Iterable<Auction> auctionList = (Iterable<Auction>)request.getAttribute("auctionList");%>
		<table>
			<% if(auctionList != null){ %>
				<% for(Auction auction : auctionList){ %>
					<!--  what do we want to display? -->
				<% } %>
			<% } %>
		</table>
	</td></tr></table>
	</div>
</body>

</html>