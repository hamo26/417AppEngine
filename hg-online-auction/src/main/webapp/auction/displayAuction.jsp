<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.onlineauction.auction.domain.entity.Auction"%>
<%@ page import="com.onlineauction.item.domain.entity.Item"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title></title>
<link rel="stylesheet" type="text/css" href="/style.css" />
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
		</ul>
	</div>
	<div id="main">
		<h2>HG AUCTION SHOW AUCTION</h2>
	</div>


	<%
		if (request.getAttribute("validAuction") != null) {
	%>
	<div id="auction">
		<div>
			<a href="/userProfile?userId=<%=request.getAttribute("sellerId")%>"><%=request.getAttribute("sellerId") %></a>
			<h3><%=request.getAttribute("itemName")%></h3>
			<p><%=request.getAttribute("itemDescription")%></p>
			<p>Base Price: $<%=request.getAttribute("itemBasePrice")%></p>
		</div>
		<% if (request.getAttribute("maxBidPrice") != null) { %>
		<div>
			<p>Current Max Bid: $<%=request.getAttribute("maxBidPrice")%></p>
			<p>By<%=request.getAttribute("maxBidUsername")%></p>
		</div>
		<% } %>
		<div>
			<p> Start date: <%=request.getAttribute("startTime")%></p>
			<p> Close date: <%=request.getAttribute("endTime")%></p>
		</div>
		<% if(request.getAttribute("isOver") == null){ %>
			<div>
				<form method="post" action="/placeBid">
					<h3>Place bid?</h3>
					<label for="bidValueInput">$</label> <input id="bidValueInput"
						name="bidValue" type="text" /> <input name="auctionId"
						type="hidden" value="<%=request.getAttribute("auctionId")%>" /> <input
						type="submit" value="Submit" />
				</form>
			</div>
		<% } %>
	</div>
	<%
		} else {
	%>
	<div id="centered">
		<p>Auction not found</p>
		<!-- auction not found message -->
	</div>
	<%
		}
	%>
</body>
</html>