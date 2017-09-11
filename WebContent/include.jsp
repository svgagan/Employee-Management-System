
<!-- <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body> -->
<%@ page import="com.EMS.entity.Registration" %>
<%@ page import="java.util.ArrayList,java.util.List" %>
  <!-- <script src="WebContent/bootstrap.min.js"></script>
  <link rel="stylesheet" href="WebContent/bootstrap.min.css">
  <script src="WebContent/jQuery-2.1.4.min.js"></script> -->
  <div style="float: left;">
  	<span style="float: left;color: blue;"><b>RoleType :<%if(session.getAttribute("role").equals(1)){%>Admin<%}%>
	<%if(session.getAttribute("role").equals(2)){%>Manager<%}%>
	<%if(session.getAttribute("role").equals(3)){%>Employee<%}%> </b></span><br>
	<span style="float: left;color: blue;"><b>Status :<%if(session.getAttribute("activityStatus").equals("TRUE")){%>Active<%}%>
	<%if(session.getAttribute("activityStatus").equals("FALSE")){%>Inactive<%}%></b></span>
	</div>
	<div style="float: right;">
	<span style="float: right;color: blue;"><b>Employee_Management_System</b></span><br>
	<span style="float: right;color: blue;"><b>Logged In as :<%=session.getAttribute("firstname")%> </b></span><br>
	</div><br><br><br>
	<%if(session.getAttribute("role").equals(1)){%>
		<div style="float: left;">
		<form name="admin" action="Role" method="post"><span style="cursor:pointer" onclick="admin.submit()"><a>Dashboard</a></span></form>
		<form name="admin1" action="ActiveUsers" method="get"><span style="cursor:pointer" onclick="admin1.submit()"><a>Active Users</a></span></form>
		<form name="admin2" action="InactiveUsers" method="get"><span style="cursor:pointer" onclick="admin2.submit()"><a>In-Active Users</a></span></form>
		<form name="admin3" action="Profiles" method="get"><span style="cursor:pointer" onclick="admin3.submit()"><a>My-Profile</a></span></form>
		<form name="admins" action="logout" method="get"><span style="cursor:pointer" onclick="admins.submit()"><a>Logout</a></span></form>
		</div>
	<%} %>
	<%if(session.getAttribute("role").equals(2)){%>
		<div style="float: left;">
		<form name="manager" action="Role" method="post"><span style="cursor:pointer" onclick="manager.submit()"><a>Dashboard</a></span></form>
		<form name="manager2" action="Directories" method="get"><span style="cursor:pointer" onclick="manager2.submit()"><a>My Directories</a></span></form>
		<form name="manager7" action="UploadDocs" method="get"><span style="cursor:pointer" onclick="manager7.submit()"><a>Documents</a></span></form>
		<form name="manager4" action="Leaves" method="get"><span style="cursor:pointer" onclick="manager4.submit()"><a>Leave-Details</a></span></form>
		<form name="manager5" action="Profiles" method="get"><span style="cursor:pointer" onclick="manager5.submit()"><a>My-Profile</a></span></form>
		<form name="manager6" action="Payments" method="get"><span style="cursor:pointer" onclick="manager6.submit()"><a>Payments</a></span></form>
		<form name="manager3" action="SupervisedUsers" method="get"><span style="cursor:pointer" onclick="manager3.submit()"><a>Sub-Employees</a></span></form>
		<form name="manager8" action="ManagerDirectories" method="get"><span style="cursor:pointer" onclick="manager8.submit()"><a>Sub-Managers Directory</a></span></form>
		<form name="manager9" action="updateATE" method="get"><span style="cursor:pointer" onclick="manager9.submit()"><a>ATE Reponse</a></span></form>
		<form name="managers" action="logout" method="get"><span style="cursor:pointer" onclick="managers.submit()"><a>Logout</a></span></form>
		</div>
	<%} %>
	<%if(session.getAttribute("role").equals(3)){%>
		<div style="float: left;">
		<form name="employee" action="Role" method="post"><span style="cursor:pointer" onclick="employee.submit()"><a>Dashboard</a></span></form>
		<form name="employee4" action="UploadDocs" method="get"><span style="cursor:pointer" onclick="employee4.submit()"><a>My-Documents</a></span></form>
		<form name="employee1" action="Leaves" method="get"><span style="cursor:pointer" onclick="employee1.submit()"><a>Leave-Details</a></span></form>
		<form name="employee2" action="Profiles" method="get"><span style="cursor:pointer" onclick="employee2.submit()"><a>My-Profile</a></span></form>
		<form name="employee3" action="Payments" method="get"><span style="cursor:pointer" onclick="employee3.submit()"><a>Payments</a></span></form>
		<form name="employee5" action="ATE" method="get"><span style="cursor:pointer" onclick="employee5.submit()"><a>ATE Request</a></span></form>
		<form name="employees" action="logout" method="get"><span style="cursor:pointer" onclick="employees.submit()"><a>Logout</a></span></form>
		</div>
	<%} %>
	
<!-- </body>
</html> -->