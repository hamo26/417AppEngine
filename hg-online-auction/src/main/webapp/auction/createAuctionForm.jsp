<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title></title>
<link rel="stylesheet" type="text/css" href="/style.css"/>
<script type="text/javascript" src="/js/datetimepicker_css.js"></script>
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
	<div id="lform">
		<h2>HG AUCTION CREATE AUCTION</h2>
		<form method="post" action="/createAuction">
			<div id="row">
				<label for="itemNameInput" id="itemName">Item Name</label> 
				<input id="itemNameInput" name="itemName" type="text"/>
			</div>
			<div id="row">
				<label for="itemDescriptionInput" id="itemDescription">Item Description</label> 
				<textarea id="itemDesciptionInput" name="itemDescription" rows="3"></textarea>
			</div>
			<div id="row">
				<label for="itemBasePriceInput" id="itemBasePrice">Item Base Price $</label>
				<input id="itemBasePriceInput" name="itemBasePrice" type="text"/>
			</div>
			<div id="row">
				<label for="dateTimeInput" id="endTime">End date/time</label>
				<input id="dateTimeInput" name="endTime" type="text"/>
				<img src="/images/cal.gif" onclick="javascript:NewCssCal('dateTimeInput','ddMMyyyy','dropdown',true,'12')" style="cursor:pointer"/>
			</div>
			<div id="row">
				<input type="submit" value="Submit"/>
			</div>
		</form>
	</div>
</body>
</html>