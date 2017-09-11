<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList,java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Admin Inactive Employees Page</title>
</head>
<body bgcolor="#99ccff">
	<%if(request.getAttribute("success")!=null){%>
		<p style="color:green;"><%=request.getAttribute("success") %></p>
	<%} %>
	<%if(request.getAttribute("failed")!=null){%>
		<p style="color:red;"><%=request.getAttribute("failed") %></p>
	<%} %>
<table border="1" width="80%">
	<caption><h1>In-Active Employee List</h1></caption>
	<thead>
		<tr>
			<th style="display: none;">Id</th><th>Firstname</th><th>Lastname</th><th>Email</th><th>Address</th><th>Phone</th><th>Select Manager</th><th>Select RoleType</th><th></th>
		</tr>
	</thead>
	<tbody>
	<%if(request.getAttribute("inActiveEmployees")!=null){%>
	<% ArrayList<Registration> inActiveEmployees = (ArrayList<Registration>) request.getAttribute("inActiveEmployees");%>
	<%for (int i = 0; i < inActiveEmployees.size(); i++) {%>
		<form name="save<%=i%>" action="InactiveUsers" method="post">
		<tr>
			<td style="display: none;"><input type="hidden" name="employeeId" value="<%=inActiveEmployees.get(i).getId()%>"/></td>
			<td><%=inActiveEmployees.get(i).getFirstname()%></td>
			<td><%=inActiveEmployees.get(i).getLastname()%></td>
			<td><%=inActiveEmployees.get(i).getEmail()%></td>
			<td><%=inActiveEmployees.get(i).getAddress()%></td>
			<td><%=inActiveEmployees.get(i).getPhone()%></td>
			<td><select name="managerId" ><option value="-1">Select Manager</option>
			<%if(request.getAttribute("mangersList")!=null){%>
			<% ArrayList<Registration> mangersList = (ArrayList<Registration>) request.getAttribute("mangersList");%>
			<%for (int z = 0; z < mangersList.size(); z++) {%>
			<option value="<%=mangersList.get(z).getId()%>"><%=mangersList.get(z).getFirstname()%> <%=mangersList.get(z).getLastname()%></option>
			<%} %>
			<%}%>
			</select></td>
			<td><select name="roleType" ><option value="-1">Select Role</option><option value="2">Manager</option><option value="3">Employee</option></select></td>
			<td><span style="cursor:pointer" onclick="save<%=i%>.submit()"><button>save</button></span></td>
		</tr>
		</form>
	<%} %>
	<%}%>
	</tbody>
</table>
</body>
</html>