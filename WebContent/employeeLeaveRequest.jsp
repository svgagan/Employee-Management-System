<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.EMS.entity.LeaveDetails,com.EMS.entity.Permission,com.EMS.entity.Leave" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Employee/Manager Leave Request Page</title>
</head>
<body bgcolor="#ccffcc"><center>
<%if(request.getAttribute("message")!=null){%>
		<p style="color:blue;"><%=request.getAttribute("message") %></p>
	<%} %>
<h1>Leave Details</h1>
<div class="row">
<div class="col-md-12">
	<div class="col-md-6" style="float: left;" name="leaveList">
	<table border="1"><caption><h2>Leave Details</h2>
			<%if(request.getAttribute("leaveNum")!=null){%>
			<% Leave leavenum = (Leave)request.getAttribute("leaveNum");  %>
			<p style="color: red;">Number Of Leaves Remaining : <b><%=leavenum.getLeaveNum()%></b></p>
			<%}%>
	</caption>
	<thead><th style="display: none;">Ids</th><th>Details</th><th>Status</th><th>Date</th></thead>
	<tbody>
		<%if(request.getAttribute("leave")!=null){%>
	<% ArrayList<LeaveDetails> leave = (ArrayList<LeaveDetails>) request.getAttribute("leave");%>
	<%for (int i = 0; i < leave.size(); i++) {%>
		<tr>
			<td style="display: none;"><input type="hidden" name="id" value="<%=leave.get(i).getId()%>"/></td>
			<td><%=leave.get(i).getDetails()%></td>
			<td><%=leave.get(i).getResponse()%></td>
			<td><%=leave.get(i).getDate()%></td>
		</tr>
	<%} %>
	<%}%>
	</tbody>
	</table>
	</div>
	<div class="col-md-6" style="float: right;" name="newLeave">
	<form method="post" action="Leaves">
		<table><caption><h2>Request Leave</h2></caption>
		<tbody>
		<tr><td>Details</td><td><textarea type="text" name="leave"></textarea></td></tr>
		<tr><td colspan="2" align="center"><input type="submit" value="Request"></td></tr>
		</tbody>
		</table>
	</form>
	</div>
</div>
</div></center>
</body>
</html>