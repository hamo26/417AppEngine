<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.onlineauction.user.domain.entity.User"  %>
	<% User user = (User) request.getSession().getAttribute("user"); %>
	<h2>HG USER PROFILE</h2>
	<div class="row">
		<label id="userName">User Name: <%=user.getUserName() %></label> 
	</div>
	<div class="row">
		<label id="firstName">First Name: <%=user.getFirstName() %></label>
	</div>
	<div class="row">
		<label id="lastName">Last Name: <%=user.getLastName() %></label>
	</div>
	<div class="row">
		<label id="emailInput">Email: <%=user.getEmail() %></label>
	</div>
</html>