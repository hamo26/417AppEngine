<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.onlineauction.auction.domain.entity.Auction"%>
<%@ page import="com.onlineauction.item.domain.entity.Item"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Collection" %>

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
			<% if(request.getAttribute("invalidated") != null){ %>
				<p>THIS AUCTION WAS STOPPED BY THE USER</p>
			<% } %>
			<a href="/userProfile?userId=<%=request.getAttribute("sellerId")%>"><%=request.getAttribute("sellerId") %></a>
			<h3><%=request.getAttribute("itemName")%></h3>
			<p><%=request.getAttribute("itemDescription")%></p>
			<p>Base Price: $<%=request.getAttribute("itemBasePrice")%></p>
		</div>
		<% if (request.getAttribute("maxBidPrice") != null) { %>
		<div>
			<p>Current Max Bid: $<%=request.getAttribute("maxBidPrice")%></p>
			<p>By <%=request.getAttribute("maxBidUsername")%></p>
		</div>
		<% } %>
		<div>
			<p> Start date: <%=request.getAttribute("startTime")%></p>
			<p> Close date: <%=request.getAttribute("endTime")%></p>
		</div>
		<% if(request.getAttribute("invalidated") == null){ %>
			<% if(((String)request.getAttribute("sellerId")).equals((String)request.getSession().getAttribute("userName"))){ %>
				<div>
					<form method="post" action="/invalidateAuction">
						<input type="hidden" name="auctionId" value="<%=request.getAttribute("auctionId") %>"/>
						<input type="submit" value="Invalidate Auction"/>
					</form>
				</div>
			<% } else{ %>
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
			<% } %>
		<% } %>
	</div>
	<% Collection<Auction> recAuctions = (Collection<Auction>)request.getAttribute("recAuctions"); %>
	<% if(!recAuctions.isEmpty()){ %>
		<div id="auctionlist">
			<p>You also might be interested in:</p>
			<% for(Auction auction : recAuctions){ %>
				<%Item item = auction.getAuctionItem();%>
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
		</div>
	<% } %>
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