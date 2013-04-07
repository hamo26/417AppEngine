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
		<li><a href="/profile">Profile</a></li>
		<li><a href="/auction/auctionSearch.jsp">Auction Search</a></li>
		<li><a href="/auction/createAuctionForm.jsp">Create Auction</a></li>
		<li><a href="/logout">Logout</a></li>
	</ul></div>
	<div id="lform">
		<h2>HG AUCTION SEARCH AUCTION</h2>
		<form method="get" action="/searchAuctions">
			<div>
				<label for="searchTermInput" id="searchTerm">Item Name</label> 
				<input id="searchTermInput" name="searchTerm" type="text"/>
			</div>
			<div>
				<label for="searchTypeInput" id="searchType">Search </label>
				<select id="searchTypeInput" name="searchType">
					<option value="byType">By Type</option>
					<option value="byDescription">By Description</option>
				</select>
			</div>
			<div>
				<input type="submit" value="Submit"/>
			</div>
		</form>
	</div>

	<% Iterable<Auction> auctionList = (Iterable<Auction>)request.getAttribute("auctionList");%>
	
	<% if(auctionList != null){ %>
		<div id="auctionlist">
		<% for(Auction auction : auctionList){ %>
			<div>
				<!--  what do we want to display? -->
			</div>
		<% } %>
		</div>
	<% } %>
</body>

</html>