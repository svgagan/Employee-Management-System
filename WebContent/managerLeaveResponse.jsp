<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList,java.util.List" %>
<%@ page import="com.EMS.entity.LeaveDetails,com.EMS.entity.Leave" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Manager Employees Leave Page</title>
</head>
<body bgcolor="#ffcccc">
	<%if(request.getAttribute("error")!=null){%>
		<p style="color:red;"><%=request.getAttribute("error") %></p>
	<%} %>
	<%if(request.getAttribute("message")!=null){%>
		<p style="color:blue;"><%=request.getAttribute("message") %></p>
	<%} %>
	<table border="1" width="80%" height="100px">
	<caption><h1>Leave Details List</h1>
			<%if(request.getAttribute("leaveNum")!=null){%>
			<% Leave leavenum = (Leave)request.getAttribute("leaveNum");  %>
			<p style="color: red;">Number Of Leaves Remaining For this Employee is <%=leavenum.getLeaveNum()%></p>
			<%}%>
	</caption>
	<thead>
		<tr>
			<th style="display: none;">Id</th><th>Requested By</th><th>Details</th><th>Date</th><th>Status</th><th>Respond</th><th></th>
		</tr>
	</thead>
	<tbody>
	<%if(request.getAttribute("leave")!=null){%>
	<% ArrayList<LeaveDetails> leave = (ArrayList<LeaveDetails>) request.getAttribute("leave");%>
	<%for (int i = 0; i < leave.size(); i++) {%>
		<form name="leave<%=i%>" action="LeavesResponse" method="post">
		<tr>
			<td style="display: none;"><input type="hidden" name="leaveId" value="<%=leave.get(i).getId()%>"/><input type="hidden" name="employeeId" value="<%=leave.get(i).getEmployeeId()%>"/></td>
			<td><%=leave.get(i).getName()%></td>
			<td><%=leave.get(i).getDetails()%></td>
			<td><%=leave.get(i).getDate()%></td>
			<td><%=leave.get(i).getResponse()%></td>
			<td><select name="response"><option value="-1">Select Response</option><option value="1">Accept</option><option value="2">Decline</option></select></td>
			<td><%if(leave.get(i).getBresponse()== true){%><span style="cursor:pointer" onclick="leave<%=i%>.submit()"><button>Respond</button></span><%}%></td>
		</tr>
		</form>
	<%} %>
	<%}%>
	</tbody>
</table>
</body>
</html>