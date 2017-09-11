<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.EMS.entity.Payment" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Payments Page</title>
</head>
<body bgcolor="#ccffcc"><center>
<%if(request.getAttribute("message")!=null){%>
		<p style="color:yellow;"><%=request.getAttribute("message") %></p>
	<%} %>
<%if(request.getAttribute("employee")!=null && request.getAttribute("payment")!=null){%>
<% Registration employee = (Registration)request.getAttribute("employee"); Payment pay = (Payment)request.getAttribute("payment"); %>
	<form action="Payments" method="post">
	<table border="1" width="50%" height="100px">
		<caption><h1><%=employee.getFirstname()%> <%=employee.getLastname()%> Payment Details</h1></caption>
		<tbody>
			<tr style="display: none;"><td>Id</td><td style="display: none;"><input type="hidden" name="employeeId" value="<%=employee.getId()%>"/></td></tr>
			<tr style="display: none;"><td>PayId</td><td style="display: none;"><input type="hidden" name="paymentId" value="<%=pay.getId()%>"/></td></tr>
			<tr><td>Full Name</td><td><%=employee.getFirstname()%> <%=employee.getLastname()%></td></tr>
			<tr><td>Role</td><td><%=employee.getRoles()%></td></tr>
			<tr><td>Email</td><td><%=employee.getEmail()%></td></tr>
			<tr><td>Address</td><td><%=employee.getAddress()%></td></tr>
			<tr><td>Phone</td><td><%=employee.getPhone()%></td></tr>
			<tr><td>Supervising Manager</td><td><%=employee.getUsername()%> <%=employee.getPassword()%></td></tr>
			<tr><td>Salary/annum</td><td><%=pay.getSalary()%></td></tr>
			<tr><td>Bonus/month</td><td><%=pay.getBonus()%></td></tr>
			<tr><td>Total/month</td><td><%=pay.getTotal()%></td></tr>
		</tbody>
	</table>
	</form>
<%}%></center>
</body>
</html>