<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList,java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Admin Active Employees Page</title>
</head>
<body bgcolor="#99ccff">
	<%if(request.getAttribute("error")!=null){%>
		<p style="color:red;"><%=request.getAttribute("error") %></p>
	<%} %>
	<%if(request.getAttribute("failed")!=null){%>
		<p style="color:red;"><%=request.getAttribute("failed") %></p>
	<%} %>
<table border="1" width="80%" height="100px">
	<caption><h1>Active Employee List</h1></caption>
	<thead>
		<tr>
			<th style="display: none;">Id</th><th>Firstname</th><th>Lastname</th><th>RoleType</th><th>Email</th><th>Address</th><th>Phone</th><th>Paycheck</th><th>Organization-Profile</th>
		</tr>
	</thead>
	<tbody>
	<%if(request.getAttribute("activeEmployees")!=null){%>
	<% ArrayList<Registration> activeEmployees = (ArrayList<Registration>) request.getAttribute("activeEmployees");%>
	<%for (int i = 0; i < activeEmployees.size(); i++) {%>
		<tr>
			<td style="display: none;"><input type="hidden" name="id" value="<%=activeEmployees.get(i).getId()%>"/></td>
			<td><%=activeEmployees.get(i).getFirstname()%></td>
			<td><%=activeEmployees.get(i).getLastname()%></td>
			<td><%=activeEmployees.get(i).getRoles()%></td>
			<td><%=activeEmployees.get(i).getEmail()%></td>
			<td><%=activeEmployees.get(i).getAddress()%></td>
			<td><%=activeEmployees.get(i).getPhone()%></td>
			<td><form name="pay<%=i%>" action="RunPayroll" method="get" > 
			<input type="hidden" name="employeeId" value="<%=activeEmployees.get(i).getId()%>"/>
			<span style="cursor:pointer" onclick="pay<%=i%>.submit()"><button>RunPayroll</button></span>
			</form></td>
			<td><form name="profile<%=i%>" action="OrganizationProfiles" method="get" > 
			<input type="hidden" name="employeeId" value="<%=activeEmployees.get(i).getId()%>"/>
			<span style="cursor:pointer" onclick="profile<%=i%>.submit()"><button>Update-Profile</button></span>
			</form></td>
		</tr>
	<%} %>
	<%}%>
	</tbody>
</table>
</body>
</html>