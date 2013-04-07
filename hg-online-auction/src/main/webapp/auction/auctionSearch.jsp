<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.onlineauction.auction.domain.entity.Auction"  %>
<%@ page import="com.onlineauction.item.domain.entity.Item" %>
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
		<form method="post" action="/searchAuctions">
			<div>
				<label for="searchTermInput" id="searchTerm">Search for</label> 
				<input id="searchTermInput" name="searchTerm" type="text"/>
			</div>
			<div>
				<label for="searchTypeInput" id="searchType">Search </label>
				<select id="searchTypeInput" name="searchType">
					<option value="byName">By Item Name</option>
					<option value="byDescription">By Description</option>
				</select>
			</div>
			<div>
				<label for="searchExpiredInput" id="searchExpired">Search</label>
				<select id="searchExpiredInput" name="searchExpired">
					<option value="ongoing">ongoing auctions</option>
					<option value="expired">expired auctions</option>
					<option value="both">ongoing and expired auctions</option>
				</select>
			</div>
			<div>
				<input type="submit" value="Submit"/>
			</div>
		</form>
	</div>

	<% Iterable<Auction> auctionList = (Iterable<Auction>)request.getAttribute("auctionResults");%>
	<% if(request.getAttribute("displayAuctions") != null){ %>
		<div id="auctionlist">
		<% if(auctionList == null){ %>
			<p>No matching auctions found</p>
		<% } else{ %>
			<% for(Auction auction : auctionList){ %>
				<% Item item =  auction.getAuctionItem();%>
				<div id="row">
					<a href="/displayAuction?auctionId=<%=auction.getId()%>"><%=item.getName() %></a>
					<p><%=item.getDescription()%></p>
					<% if(!auction.isOver()){ %>
						<p>Done at <%=auction.getEndTime() %></p>
					<% } else { %>
						<p>Already done</p>
					<% } %>
				</div>
			<% } %>
		<% } %>
		</div>
	<% } %>
</body>

</html>