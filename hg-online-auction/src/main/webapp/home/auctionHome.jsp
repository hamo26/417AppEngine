<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.onlineauction.user.domain.entity.User"  %>

<html>

<head>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<title>Web Template</title>
<style TYPE="text/css">
<!--
A:link {
	text-decoration: none;
	color: #587050
}

a:visited {
	text-decoration: none;
	color: #587050
}

a:active {
	text-decoration: none;
	color: #587050
}

a:hover {
	color: #FFCC00
}
-->
</style>
</head>

<body topmargin="0" bgcolor="#FFFFFF">

	<div align="center">
		<table border="0" width="80%" cellspacing="0" cellpadding="0"
			bgcolour="#00ffff">
			<tr>
				<td width="100%">
					<p align="right">
				</td>
			</tr>
		</table>
	</div>
	<div align="center">
		<table border="0" width="80%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="23%" valign="top">
					<p align="left">
						<font size="4"><br> <br> <a
							href="/profile/profile.jsp">Profile</a><br> <br> <a
							href="/auction/auctionSearch.jsp">Auction Search</a><br> <br>
							<a href="/auction/createAuctionForm.jsp">Create Auction</a><br>
				</td>
				<td width="77%" valign="top">
					<table border="0" width="100%" cellspacing="0" cellpadding="0"
						bgcolor="#88A078">
						<tr>
							<td width="100%"><font size="1">&nbsp;</font></td>
						</tr>
					</table>
					<table border="0" width="100%" bgcolor="#00ffff" cellspacing="0"
						cellpadding="0">
						<tr>
							<td width="100%">&nbsp;</td>
						</tr>
					</table>
					<div align="justify">
						<blockquote>
							<font color="#000000">Welcome to H & G Auctions <%=((User) request.getAttribute("user")).getUserName()%> &nbsp;</font>
							<p>
								<font color="#000000">To the left you will find a series
									of options to begin your shopping experience.&nbsp;</font>
							</p>
						</blockquote>
					</div>
				</td>
			</tr>
		</table>
	</div>

	<div align="center">
		<center>
			<table border="0" width="80%" cellspacing="0" cellpadding="0"
				bgcolor="#00ffff">
				<tr>
					<td width="170"><font size="1">&nbsp;</font></td>
				</tr>
			</table>
		</center>
	</div>
</body>

</html>