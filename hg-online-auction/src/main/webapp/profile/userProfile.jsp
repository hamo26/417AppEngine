<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.onlineauction.auction.domain.entity.Auction"  %>
<%@ page import="com.onlineauction.bid.domain.entity.Bid"  %>
<%@ page import="com.onlineauction.item.domain.entity.Item" %>
<%@ page import="com.onlineauction.rating.domain.entity.Rating" %>
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
		<% long userId = Long.parseLong((String)request.getAttribute("userId")); %>
		<h2>HG USER PROFILE</h2>
		<p><%=userId %>'s Profile</p> 
		<div>
			<h3>User's rating:</h3>
			<% Collection<Rating> ratings = (Collection<Rating>)request.getAttribute("ratings"); %>
			<div id="ratings">
				<% if(ratings != null){ %>
					<% for(Rating rating : ratings){ %>
							<div>
								<p>Review by <%=rating.getUserName() %></p>
								<p><%=rating.getComment() %></p>
								<p><%=Integer.toString(rating.getRating())%>/5</p>
							</div>
					<% } %>
				<% } else{ %>
					<p>No ratings</p>
				<% } %>
			</div>
		<div>
			<form method="post" action="/rateSeller">
				<h3>Rate the seller?</h3>
				<div id="radio">
					<label for="oneStarInput">1</label> 
					<input id="oneStarInput" name="ratingInput" type="radio" value="1"/>
					
					<label for="twoStarInput">2</label> 
					<input id="twoStarInput" name="ratingInput" type="radio" value="2"/>
						
					<label for="threeStarInput">3</label> 
					<input id="threeStarInput" name="ratingInput" type="radio" value="3"/>
					
					<label for="fourStarInput">4</label> 
					<input id="fourStarInput" name="ratingInput" type="radio" value="4"/>
					
					<label for="fiveStarInput">5</label> 
					<input id="fiveStarInput" name="ratingInput" type="radio" value="5"/>
				</div> 
				<div>
					<textarea id="ratingCommentInput" name="ratingComment" rows="3"></textarea>
				</div>
				<input name="userId" value="<%=userId %>" type="hidden"/>
				<input type="submit" value="Submit" />
			</form>
		</div>
	</div>
	
</body>
</html>