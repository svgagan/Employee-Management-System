<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.EMS.entity.DirectoryDetails,com.EMS.entity.ATEDetails" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Employee ATE Page</title>
</head>
<body bgcolor="#ffcccc"><center>
	<%if(request.getAttribute("message")!=null){%>
		<p style="color:blue;"><%=request.getAttribute("message") %></p>
	<%} %>
	<%if(request.getAttribute("error")!=null){%>
		<p style="color:red;"><%=request.getAttribute("error") %></p>
	<%} %>
	<%if(request.getAttribute("success")!=null){%>
		<p style="color:green;"><%=request.getAttribute("success") %></p>
	<%} %>
<h1>ATE Details</h1>
<div class="row">
<div class="col-md-12">
	<table border="1" width="80%"><caption><h2>Accessing Permission Requested For Following Directory</h2></caption>
	<thead><tr><th style="display: none;">Ids</th><th>DirectoryName</th><th>Requested-By</th><th>Status</th><th></th><th></th></tr></thead>
	<tbody>
		<%if(request.getAttribute("ATE")!=null){%>
	<% ArrayList<ATEDetails> docs = (ArrayList<ATEDetails>) request.getAttribute("ATE");%>
	<%for (int i = 0; i < docs.size(); i++) {%>
		<tr><form name="ate<%=i%>" method="post" action="updateATE">
			<td style="display: none;"><input type="hidden" name="ateId" value="<%=docs.get(i).getId()%>"/></td>
			<td><%=docs.get(i).getDirName()%></td>
			<td><%=docs.get(i).getCreatedBy()%></td>
			<td><%=docs.get(i).getAteStatus()%></td>
			<td><select name="permission"><option value="1">Accept</option><option value="0">Decline</option></select></td>
			<td><span style="cursor:pointer" onclick="ate<%=i%>.submit()"><button>Respond</button></span></td></form>
		</tr>
	<%} %>
	<%}%>
	</tbody>
	</table>
</div>
</div></center>
</body>
</html>