<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*"  %>
    <%@ page import="com.UBC417.A2.Data.Flight" %>
    <%@ page import="com.google.appengine.api.datastore.Entity" %>
    <%@ page import="com.UBC417.A2.Data.Seat" %>
    <%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link href="Site.css" rel="Stylesheet" type="text/css"/>
</head>
<body>
	
	
	<div class="wrapper">
		<div id="Content">
			<div id="Header"><H1>Flights - V4</H1></div>
			<hr/>
			<div id="MainContent">

				<% 
				Iterable<Entity> flight1Seats = (Iterable<Entity>)request.getAttribute("flight1Seats");
				%>
				<% 
				Iterable<Entity> flight2Seats = (Iterable<Entity>)request.getAttribute("flight2Seats");
				%>
				<% 
				Iterable<Entity> flight3Seats = (Iterable<Entity>)request.getAttribute("flight3Seats");
				%>
				<% 
				Iterable<Entity> flight4Seats = (Iterable<Entity>)request.getAttribute("flight4Seats");
				%>
				
				<form action="ReserveSeat" method="post">
				
				<input type="hidden" name="Flight" value="<%=request.getParameter("Flight") %>"/>
				
				<table>
					<tr>
						<td align="right">First Name:</td>
						<td align="left"><input type="text" name="FirstName"/></td>
					</tr>
					<tr>
						<td align="right">Last Name:</td>
						<td align="left"><input type="text" name="LastName"/></td>
					</tr>
					
					<% if(!request.getParameter("Flight1").equals("")){ %>
						<tr>
							<td>Flight 1: <%=request.getParameter("Flight1")%></td>
							<td>
								<select name="Flight1Seat">
									<option value="">Please select a seat.</option>
								<% for( Entity e : flight1Seats ) { %>
									<option><%=e.getKey().getName() %></option>
								<%} %>
								</select>
								<input type="hidden" name="Flight1" value=<%=request.getParameter("Flight1")%> />
							</td>
						</tr>
					<% } %>
					
					<% if(!request.getParameter("Flight2").equals("")){ %>
						<tr>
							<td>Flight 2: <%=request.getParameter("Flight2")%></td>
							<td>
								<select name="Flight2Seat">
									<option value="">Please select a seat.</option>
								<% for( Entity e : flight2Seats ) { %>
									<option><%=e.getKey().getName() %></option>
								<%} %>
								</select>
								<input type="hidden" name="Flight2" value=<%=request.getParameter("Flight2")%> />
							</td>
						</tr>
					<% } %>
					
					<% if(!request.getParameter("Flight3").equals("")){ %>
						<tr>
							<td>Flight 3: <%=request.getParameter("Flight3")%></td>
							<td>
								<select name="Flight3Seat">
									<option value="">Please select a seat.</option>
								<% for( Entity e : flight3Seats ) { %>
									<option><%=e.getKey().getName() %></option>
								<%} %>
								</select>
								<input type="hidden" name="Flight3" value=<%=request.getParameter("Flight3")%> />
							</td>
						</tr>
					<% } %>
					
					<% if(!request.getParameter("Flight4").equals("")){ %>
						<tr>
							<td>Flight 4: <%=request.getParameter("Flight4")%></td>
							<td>
								<select name="Flight4Seat">
									<option value="">Please select a seat.</option>
								<% for( Entity e : flight4Seats ) { %>
									<option><%=e.getKey().getName() %></option>
								<%} %>
								</select>
								<input type="hidden" name="Flight4" value=<%=request.getParameter("Flight4")%> />
							</td>
						</tr>
					<% } %>
				
					<tr>
						<td></td>
						<td><input type="submit" value="Reserve Seats"/></td>
					</tr>
				</table>
				</form>
				
			</div><!-- end MainContent -->
		</div><!-- end Content -->
	</div><!-- end wrapper -->
</body>
</html>