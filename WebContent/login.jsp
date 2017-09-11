<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>EMS - Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body bgcolor="#ccccff">
	<center><h1>Login Page</h1>
	<form action="login" method="post">
	<%if(request.getAttribute("loginerror")!=null){%>
		<p style="color:red;"><%=request.getAttribute("loginerror") %></p>
	<%} %>
	<table>
		<tr><td>UserName</td><td><input name="username" type="text" maxlength="10" placeholder="UserName"></td></tr>
		<tr><td>Password</td><td><input name="password" type="password" maxlength="10" placeholder="Password"></td></tr>
		<tr><td colspan="2" align="center"><input type="submit" value="Login"></td></tr>
	</table>
	</form>
	<form action="register" name="reg" method="get"><span style="cursor:pointer" onclick="reg.submit()"><a>Need an account, <b>Click here</b></a></span></form>
	</center>
</body>
</html>