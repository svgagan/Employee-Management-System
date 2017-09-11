<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="com.EMS.entity.Registration" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Employee Dashboard Page</title>
</head>
<body bgcolor="#ccffcc">
	<center>
	<%if(request.getAttribute("updateSuccess")!=null){%>
		<p style="color:green;"><%=request.getAttribute("updateSuccess") %></p>
	<%} %>
	<%if(request.getAttribute("updateError")!=null){%>
		<p style="color:red;"><%=request.getAttribute("updateError") %></p>
	<%} %>
	<form action="updateProfile" method="post">
	<table>
		<% Registration register = (Registration) request.getAttribute("register");%>
		<caption><h2>Profile Details</h2></caption>
		<tbody>
		<tr style="display: none;"><td>EmployeeId</td><td><input value="<%=register.getId()%>" name="id" type="text" placeholder="EmployeeId" disabled="disabled"></td></tr>
		<tr><td>FirstName</td><td><input value="<%=register.getFirstname()%>" name="firstname" type="text" maxlength="25" placeholder="FirstName" disabled="disabled"></td></tr>
		<tr><td>LastName</td><td><input value="<%=register.getLastname()%>" name="lastname" type="text" maxlength="25" placeholder="LastName" disabled="disabled"></td></tr>
		<tr><td>Email*</td><td><input value="<%=register.getEmail()%>" name="email" type="text" maxlength="30" placeholder="Email"></td></tr>
		<tr><td>Address</td><td><input value="<%=register.getAddress()%>" name="address" type="text" maxlength="30" placeholder="Address"></td></tr>
		<tr><td>Phone</td><td><input value="<%=register.getPhone()%>" name="phone" type="text" maxlength="10" placeholder="Phone"></td></tr>
		<tr><td>UserName</td><td><input value="<%=register.getUsername()%>" name="uid" type="text" maxlength="10" placeholder="UserName" disabled="disabled"></td></tr>
		<tr><td colspan="2" align="center"><input type="submit" value="Update"></td></tr>
		</tbody>
	</table>
	</form>
	</center>
</body>
</html>