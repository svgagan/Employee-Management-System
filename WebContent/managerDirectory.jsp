<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.EMS.entity.DirectoryDetails,com.EMS.entity.Permission" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Manager Directory Page</title>
</head>
<body bgcolor="#ffcccc"><center>
<%if(request.getAttribute("message")!=null){%>
		<p style="color:yellow;"><%=request.getAttribute("message") %></p>
	<%} %>
<h1>Directory Details</h1>
<div class="row">
<div class="col-md-12">
	<div class="col-md-6" style="float: left;" name="directoryList">
	<table border="1" width="80%"><caption><h2>Directory Available</h2></caption>
	<thead><th style="display: none;">Ids</th><th>Created By</th><th>DirectoryName</th><th>Permission</th><th>Accessible To</th><th>ATE Details</th></thead>
	<tbody>
		<%if(request.getAttribute("directories")!=null){%>
	<% ArrayList<DirectoryDetails> directories = (ArrayList<DirectoryDetails>) request.getAttribute("directories");%>
	<%for (int i = 0; i < directories.size(); i++) {%>
		<tr>
			<td style="display: none;"><input type="hidden" name="id" value="<%=directories.get(i).getId()%>"/></td>
			<td><%=directories.get(i).getCreatedBy()%></td>
			<td><%=directories.get(i).getDirectoryName()%></td>
			<td><%=directories.get(i).getPermissions()%></td>
			<td><%=directories.get(i).getAccessibleBy()%></td>
			<%if(directories.get(i).getPermissionId() == 3){%><td><%=directories.get(i).getAteBy()%></td><%} %>
			<%if(directories.get(i).getPermissionId() != 3){%><td></td><%} %>
		</tr>
	<%} %>
	<%}%>
	</tbody>
	</table>
	</div>
	<div class="col-md-6" style="float: right;" name="newDirectory">
	<form method="post" action="Directories">
		<table><caption><h2>To Create New Directory</h2></caption>
		<tbody>
		<tr><td>DirectoryName</td><td><input type="text" name="directory"></td></tr>
		<tr><td>Permission</td><td><select name="permission"><option value="-1">Select Permission</option>
		<%if(request.getAttribute("permission")!=null){%>
			<% ArrayList<Permission> permission = (ArrayList<Permission>) request.getAttribute("permission");%>
			<%for (int z = 0; z < permission.size(); z++) {%>
			<option value="<%=permission.get(z).getId()%>"><%=permission.get(z).getPermission()%></option>
			<%} %>
			<%}%>
		</select></td></tr>
		<tr><td colspan="2" align="center"><input type="submit" value="Create"></td></tr>
		</tbody>
		</table>
	</form>
	</div>
	</div>
	<div>
		<form method="post" action="updatePermission">
			<table><caption><h2>To Update Permission of Directory</h2></caption>
			<tbody>
				<tr><td>Directory</td><td><select name="pubDir"><option value="-1">Select Directory</option>
				<%if(request.getAttribute("directories")!=null){%>
					<% ArrayList<DirectoryDetails> directory = (ArrayList<DirectoryDetails>) request.getAttribute("directories");%>
					<%for (int z = 0; z < directory.size(); z++) {%>
						<%if(directory.get(z).getPermissionId()== 1){%>
							<option value="<%=directory.get(z).getId()%>"><%=directory.get(z).getDirectoryName()%></option>
						<%}%>
					<%} %>
				<%}%>
				</select></td></tr>
				<tr><td>Permission</td><td><select name="newPermission"><option value="-1">Change Permission</option>
				<option value="2">Private</option><option value="3">Protected</option><option value="4">Default</option>
				</select></td></tr>
				<tr><td colspan="2" align="center"><input type="submit" value="Change"></td></tr>
			</tbody>
			</table>
		</form>
	</div>
</div></center>
</body>
</html>