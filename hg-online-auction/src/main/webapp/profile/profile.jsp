<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.onlineauction.auction.domain.entity.Auction"  %>
<%@ page import="com.onlineauction.bid.domain.entity.Bid"  %>
<%@ page import="com.onlineauction.item.domain.entity.Item" %>
<%@ page import="java.util.Collection" %>
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
	<div id="main">
		<h2>HG USER PROFILE</h2>
		<p>Hello <%=request.getSession().getAttribute("userName") %></p> 
	</div>
	<% Collection<Auction> userAuctions =  (Collection<Auction>)request.getAttribute("userAuctions"); %>
	<% Collection<Auction> winningBidAuctions =  (Collection<Auction>)request.getAttribute("winningBidAuctions"); %>
	<% Collection<Auction> losingBidAuctions =  (Collection<Auction>)request.getAttribute("losingBidAuctions"); %>
	<% Collection<Auction> wonBidAuctions =  (Collection<Auction>)request.getAttribute("wonBidAuctions"); %>
	<% Collection<Auction> lostBidAuctions =  (Collection<Auction>)request.getAttribute("lostBidAuctions"); %>
	
	<% if(!winningBidAuctions.isEmpty()){ %>
		<div id="auctionlist">
			<p>You're winning the bid on the following auctions:</p>
			<% for(Auction auction : winningBidAuctions){ %>
				<%Item item = auction.getAuctionItem();%>
				<div id="row">
					<a href="/displayAuction?auctionId=<%=auction.getId()%>"><%=item.getName() %></a>
					<p><%=item.getDescription()%></p>
					<p>Closing at <%=auction.getEndTime() %></p>
				</div>
			<% } %>
		</div>
	<% } %>
	
	<% if(!losingBidAuctions.isEmpty()){ %>
		<div id="auctionlist">
			<p>You're losing the bid on the following auctions:</p>
			<% for(Auction auction : losingBidAuctions){ %>
				<%Item item = auction.getAuctionItem();%>
				<div id="row">
					<a href="/displayAuction?auctionId=<%=auction.getId()%>"><%=item.getName() %></a>
					<p><%=item.getDescription()%></p>
					<p>Closing at <%=auction.getEndTime() %></p>
				</div>
			<% } %>
		</div>
	<% } %>
	
	<% if(!wonBidAuctions.isEmpty()){ %>
		<div id="auctionlist">
			<p>You've won the bid on the following auctions:</p>
			<% for(Auction auction : wonBidAuctions){ %>
				<%Item item = auction.getAuctionItem();%>
				<div id="row">
					<a href="/displayAuction?auctionId=<%=auction.getId()%>"><%=item.getName() %></a>
					<p><%=item.getDescription()%></p>
				</div>
			<% } %>
		</div>
	<% } %>
	
	<% if(!lostBidAuctions.isEmpty()){ %>
		<div id="auctionlist">
			<p>You've lost the bid on the following auctions:</p>
			<% for(Auction auction : lostBidAuctions){ %>
				<%Item item = auction.getAuctionItem();%>
				<div id="row">
					<a href="/displayAuction?auctionId=<%=auction.getId()%>"><%=item.getName() %></a>
					<p><%=item.getDescription()%></p>
				</div>
			<% } %>
		</div>
	<% } %>
	
	<% if(!userAuctions.isEmpty()){ %>
		<div id="auctionlist">
			<p>You've created the following auctions:</p>
			<% for(Auction auction : userAuctions){ %>
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
	<div id="Delete Profile">
		<p>Delete Your Profile?</p>
		<form method="post" action="/unsubscribeUser">
			<div id="row">
				<input type="submit" value="Delete Profile"/>
			</div>
		</form>
	</div>
</body>
</html>