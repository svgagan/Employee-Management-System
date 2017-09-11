<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.EMS.entity.DirectoryDetails,com.EMS.entity.Employeedocs" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS - Sub-Employees Uploaded Page</title>
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
<h1>Documents Details</h1>
<div class="row">
<div class="col-md-12">
	<div class="col-md-6" name="documentList">
	<table border="1" width="80%"><caption><h2>Documents Available</h2></caption>
	<thead><th style="display: none;">Ids</th><th>DirectoryName</th><th>Docs</th><th>Comments</th><th>Uploaded-By</th><th>Date</th><th></th></thead>
	<tbody>
		<%if(request.getAttribute("docs")!=null){%>
	<% ArrayList<Employeedocs> docs = (ArrayList<Employeedocs>) request.getAttribute("docs");%>
	<%for (int i = 0; i < docs.size(); i++) {%>
		<tr>
			<td style="display: none;"><input type="hidden" name="id" value="<%=docs.get(i).getId()%>"/></td>
			<td><%=docs.get(i).getDirectoryName()%></td>
			<td><%=docs.get(i).getDocName()%></td>
			<td><%=docs.get(i).getComment()%></td>
			<td><%=docs.get(i).getCreatedBy()%></td>
			<td><%=docs.get(i).getDate()%></td>
			<td><form action="DownloadDoc" method="post"><input type="hidden" name="docId" value="<%=docs.get(i).getId()%>"/><input type="submit" value="view"/></form></td>
		</tr>
	<%} %>
	<%}%>
	</tbody>
	</table>
	</div>
	<%-- <div class="col-md-6" style="float: right;" name="newDocs">
	<form method="post" action="UploadDocs" enctype="multipart/form-data">
		<table><caption><h2>Upload New Document</h2></caption>
		<thead><tr><th colspan="2" style="color:red;" align="center">* represents Mandatory fields</th></tr></thead>
		<tbody>
		<tr><td>DocumentName*</td><td><input type="text" name="docName" placeholder="Name"></td></tr>
		<tr><td>Document*</td><td><input type="file" name="docFile"></td></tr>
		<tr><td>Comments</td><td><textarea name="docComment" placeholder="Comments"></textarea></td></tr>
		<tr><td>Directory*</td><td><select name="directory"><option value="-1">Select Directory</option>
		<%if(request.getAttribute("directory")!=null){%>
			<% ArrayList<DirectoryDetails> directory = (ArrayList<DirectoryDetails>) request.getAttribute("directory");%>
			<%for (int z = 0; z < directory.size(); z++) {%>
			<option value="<%=directory.get(z).getId()%>"><%=directory.get(z).getDirectoryName()%></option>
			<%} %>
			<%}%>
		</select></td></tr>
		<tr><td colspan="2" align="center"><input type="submit" value="Upload"></td></tr>
		</tbody>
		</table>
	</form>
	</div> --%>
</div>
</div></center>
</body>
</html>