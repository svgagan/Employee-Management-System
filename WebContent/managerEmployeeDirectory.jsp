<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.EMS.entity.DirectoryDetails,com.EMS.entity.Permission" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Sub-Managers Directory Page</title>
</head>
<body bgcolor="#ffcccc"><center>
<%if(request.getAttribute("message")!=null){%>
		<p style="color:blue;"><%=request.getAttribute("message") %></p>
	<%} %>
<h1>Directory Details</h1>
<div class="row">
<div class="col-md-12">
	<div class="col-md-6" name="directoryList">
	<table border="1" width="80%"><caption><h2>Sub-Managers Directory Details</h2></caption>
	<thead><th style="display: none;">Ids</th><th>Created By</th><th>DirectoryName</th><th>Permission</th><th></th></thead>
	<tbody>
		<%if(request.getAttribute("directories")!=null){%>
	<% ArrayList<DirectoryDetails> directories = (ArrayList<DirectoryDetails>) request.getAttribute("directories");%>
	<%for (int i = 0; i < directories.size(); i++) {%>
		<tr>
			<td style="display: none;"><input type="hidden" name="id" value="<%=directories.get(i).getId()%>"/></td>
			<td><%=directories.get(i).getCreatedBy()%></td>
			<td><%=directories.get(i).getDirectoryName()%></td>
			<td><%=directories.get(i).getPermissions()%></td>
			<td><form action="ManagerDirectories" method="post"><input type="hidden" name="dirId" value="<%=directories.get(i).getId()%>"/><input type="submit" value="view Files"/></form></td>
		</tr>
	<%} %>
	<%}%>
	</tbody>
	</table>
	</div>
</div>
</div></center>
</body>
</html>