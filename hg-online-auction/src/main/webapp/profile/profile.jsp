<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.onlineauction.auction.domain.entity.Auction"  %>
<%@ page import="com.onlineauction.bid.domain.entity.Bid"  %>
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
	<% Collection<Bid> winningBids =  (Collection<Bid>)request.getAttribute("winningBids"); %>
	<% Collection<Bid> losingBids =  (Collection<Bid>)request.getAttribute("losingBids"); %>
	<% if(!winningBids.isEmpty() || !losingBids.isEmpty()){ %>
		<div id="auctionlist">
			<p>You've bid on the following auctions:</p>
			<% if(!winningBids.isEmpty()){%>
				<% for(Bid bid : winningBids){ %>
						<div id="row">
							<a href="/displayAuction" auctionId=<%=bid.getAuctionId() %>><%=bid.getItem().getName() %></a>
						</div>
				<% } %>
			<% } %>
			<% if(!losingBids.isEmpty()){%>
				<% for(Bid bid : winningBids){ %>
						<div id="row">
							<a href="/displayAuction" auctionId=<%=bid.getAuctionId() %>><%=bid.getItem().getName() %></a>
						</div>
				<% } %>
			<% } %>
		</div>
	<% } %>
</body>
</html>