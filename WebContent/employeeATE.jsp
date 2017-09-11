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
<body bgcolor="#ccffcc"><center>
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
	<div class="col-md-6" style="float: left;" name="documentList">
	<table border="1" width="80%"><caption><h2>Access Requested For Following Directory</h2></caption>
	<thead><th style="display: none;">Ids</th><th>DirectoryName</th><th>Supervised-By</th><th>Status</th></thead>
	<tbody>
		<%if(request.getAttribute("ATE")!=null){%>
	<% ArrayList<ATEDetails> docs = (ArrayList<ATEDetails>) request.getAttribute("ATE");%>
	<%for (int i = 0; i < docs.size(); i++) {%>
		<tr>
			<td style="display: none;"><input type="hidden" name="id" value="<%=docs.get(i).getId()%>"/></td>
			<td><%=docs.get(i).getDirName()%></td>
			<td><%=docs.get(i).getCreatedBy()%></td>
			<td><%=docs.get(i).getAteStatus()%></td>
		</tr>
	<%} %>
	<%}%>
	</tbody>
	</table>
	</div>
	<div class="col-md-6" style="float: right;" name="newDocs">
	<form method="post" action="ATE">
		<table><caption><h2>Request Access To Any Protected Directory Listed</h2></caption>
		<tbody>
		<tr><td>Directory*</td><td><select name="directory"><option value="-1">Select Protected Directory</option>
		<%if(request.getAttribute("directory")!=null){%>
			<% ArrayList<DirectoryDetails> directory = (ArrayList<DirectoryDetails>) request.getAttribute("directory");%>
			<%for (int z = 0; z < directory.size(); z++) {%>
			<option value="<%=directory.get(z).getId()%>"><%=directory.get(z).getDirectoryName()%></option>
			<%} %>
			<%}%>
		</select></td></tr>
		<tr><td colspan="2" align="center"><input type="submit" value="Request"></td></tr>
		</tbody>
		</table>
	</form>
	</div>
</div>
</div></center>
</body>
</html>