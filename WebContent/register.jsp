<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>EMS - Registration</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body bgcolor="#ccccff">
	<center><h1>Registration Page</h1>
	<form action="register" method="post">
	<%if(request.getAttribute("error")!=null){
		%>
		<p style="color:red;"><%=request.getAttribute("error") %></p>
	<%} %>
	<table>
		<tr><td>FirstName</td><td><input name="firstname" type="text" maxlength="25" placeholder="FirstName"></td></tr>
		<tr><td>LastName</td><td><input name="lastname" type="text" maxlength="25" placeholder="LastName"></td></tr>
		<tr><td>Email</td><td><input name="email" type="text" maxlength="30" placeholder="Email"></td></tr>
		<tr><td>Address</td><td><input name="address" type="text" maxlength="30" placeholder="Address"></td></tr>
		<tr><td>Phone</td><td><input name="phone" type="text" maxlength="10" placeholder="Phone"></td></tr>
		<tr><td>UserName</td><td><input name="uid" type="text" maxlength="10" placeholder="UserName"></td></tr>
		<tr><td>Password</td><td><input name="password" type="password" maxlength="10" placeholder="Password"></td></tr>
		<tr><td colspan="2" align="center"><input type="submit" value="Register"></td></tr>
	</table>
	</form>
	<form action="login" name="log" method="get"><span style="cursor:pointer" onclick="log.submit()"><a>Already have account, <b>Click here</b></a></span></form>
	</center>
</body>
</html>