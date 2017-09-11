package com.EMS.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.Employeedocs;
import com.EMS.entity.Leave;
import com.EMS.entity.LeaveDetails;
import com.EMS.entity.Permission;
import com.EMS.entity.Registration;
import com.EMS.utility.FunctionResponse;

public class ManagerDao {

	public static List<DirectoryDetails> getAllDirectories(int eId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails where managerId = ? and status = ?");
		statemet1.setInt(1, eId);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery() ;
		List<DirectoryDetails> directories = new ArrayList<DirectoryDetails>();
		try{
			while(rs.next()){
				DirectoryDetails dir = new DirectoryDetails();
				dir.setId(rs.getInt(1));
				dir.setDirectoryName(rs.getString(2));
				dir.setManagerId(rs.getInt(3));
				dir.setPermissionId(rs.getInt(4));
				dir.setAccessibleIds(rs.getString(5));
				dir.setStatus(rs.getBoolean(6));
				dir.setATE(rs.getString(7));
				directories.add(dir);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return directories;
	}

	public static List<Permission> getAllPermission() throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from permission where status = ?");
		statemet1.setBoolean(1, true);
		ResultSet rs = statemet1.executeQuery() ;
		List<Permission> permission = new ArrayList<Permission>();
		try{
			while(rs.next()){
				Permission perm = new Permission();
				perm.setId(rs.getInt(1));
				perm.setPermission(rs.getString(2));
				perm.setStatus(rs.getBoolean(3));
				permission.add(perm);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return permission;
	}

	public static String getName(int id) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from registration where id = ?");
		statemet1.setInt(1, id);
		ResultSet rs = statemet1.executeQuery() ;
		String name = null;
		try{
			while(rs.next()){
				name = rs.getString(2);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return name;
	}

	public static String getPermission(int permissionId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from permission where id = ? and status = ?");
		statemet1.setInt(1, permissionId);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery() ;
		String permission = null;
		try{
			while(rs.next()){
				permission = rs.getString(2);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return permission;
	}

	public static FunctionResponse saveDirectory(int eId, String directory,int perm, String access) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails");
		ResultSet rs = statemet1.executeQuery() ;
		try{
			int size=1; 
			while(rs.next()){
				size++;
			}
			PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("INSERT INTO directorydetails (id, directoryName, managerId, permissionId, accessibleIds, status, ateIds) values (?,?,?,?,?,?,?)");
			stmt2.setInt(1,size);
			stmt2.setString(2, directory);//Default Directory Name
			stmt2.setInt(3, eId);//ManagerID
			stmt2.setInt(4, perm);//Default Permission
			stmt2.setString(5, access);//Accessible User Details
			stmt2.setBoolean(6, true);
			stmt2.setString(7, "NA");//ATE User Details
			stmt2.executeUpdate();
			stmt2.close();
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		fresResponse.setResponse("Directory Created Successfully");
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static DirectoryDetails getDirectory(int eId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails where managerId = ? and status = ? and permissionId = ?");
		statemet1.setInt(1, eId);
		statemet1.setBoolean(2, true);
		statemet1.setInt(3, 4);//default permission
		ResultSet rs = statemet1.executeQuery() ;
		DirectoryDetails dir = new DirectoryDetails();
		try{
			while(rs.next()){
				dir.setId(rs.getInt(1));
				dir.setDirectoryName(rs.getString(2));
				dir.setManagerId(rs.getInt(3));
				dir.setPermissionId(rs.getInt(4));
				dir.setAccessibleIds(rs.getString(5));
				dir.setStatus(rs.getBoolean(6));
				dir.setATE(rs.getString(7));
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return dir;
	}

	public static List<DirectoryDetails> getAllPublicDirectories() throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails where permissionId = ? and status = ?");
		statemet1.setInt(1, 1);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery() ;
		List<DirectoryDetails> directories = new ArrayList<DirectoryDetails>();
		try{
			while(rs.next()){
				DirectoryDetails dir = new DirectoryDetails();
				dir.setId(rs.getInt(1));
				dir.setDirectoryName(rs.getString(2));
				dir.setManagerId(rs.getInt(3));
				dir.setPermissionId(rs.getInt(4));
				dir.setAccessibleIds(rs.getString(5));
				dir.setStatus(rs.getBoolean(6));
				dir.setATE(rs.getString(7));
				directories.add(dir);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return directories;
	}

	public static List<Registration> getAllActiveEmployees(int eId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from registration where id != ? and managerId = ? and activitystatus = ?");
		statemet1.setInt(1, eId);
		statemet1.setInt(2, eId);
		statemet1.setBoolean(3, true);
		ResultSet rs = statemet1.executeQuery() ;
		List<Registration> activeEmployees = new ArrayList<Registration>();
		try{
			while(rs.next()){
				Registration register = new Registration();
				register.setId(rs.getInt(1));
				register.setFirstname(rs.getString(2));
				register.setLastname(rs.getString(3));
				register.setEmail(rs.getString(4));
				register.setAddress(rs.getString(5));
				register.setPhone(rs.getString(6));
				register.setUsername(rs.getString(7));
				register.setPassword(rs.getString(8));
				register.setRole(rs.getInt(9));
				register.setManagerstatus(rs.getBoolean(10));
				register.setManagerId(rs.getInt(11));
				register.setActivitystatus(rs.getBoolean(12));
				register.setDate(rs.getDate(13));
				activeEmployees.add(register);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return activeEmployees;
	}

	public static FunctionResponse saveBonusDetails(int eid, int paymentid,	double bonus) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt1 = DBAccess.getConnection().prepareStatement("UPDATE payment SET bonus = ? WHERE id = ? and registrationId = ?");
		try{
			stmt1.setDouble(1, bonus);
			stmt1.setInt(2,paymentid);
			stmt1.setInt(3,eid);
			stmt1.executeUpdate();
		}finally{
			stmt1.close();
			DBAccess.con.close();
		}
		fresResponse.setEmployeeId(eid);
		fresResponse.setResponse("Bonus Details Updated successfully");
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static List<LeaveDetails> getLeaveDetails(int eId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from leavedetails where employeeId = ? and status = ?");
		statemet1.setInt(1, eId);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery() ;
		List<LeaveDetails> leaves = new ArrayList<LeaveDetails>();
		try{
			while(rs.next()){
				LeaveDetails leave = new LeaveDetails();
				leave.setId(rs.getInt(1));
				leave.setEmployeeId(rs.getInt(2));
				leave.setManagerId(rs.getInt(3));
				leave.setDetails(rs.getString(4));
				leave.setBresponse(rs.getBoolean(5));
				leave.setResponse(rs.getString(6));
				leave.setDate(rs.getDate(7));
				leave.setStatus(rs.getBoolean(8));
				leaves.add(leave);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return leaves;
	}

	public static FunctionResponse saveLeaveRequest(int eId, int managerId,String details) throws SQLException {
		java.util.Date myDate = new java.util.Date();
		Date sqlDate = new java.sql.Date(myDate.getTime());
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from leavedetails");
		ResultSet rs = statemet1.executeQuery() ;
		try{
			int size=1; 
			while(rs.next()){
				size++;
			}
			PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("INSERT INTO leavedetails (id, employeeId, managerId, details, requestStatus, response, date, status) values (?,?,?,?,?,?,?,?)");
			stmt2.setInt(1,size);
			stmt2.setInt(2, eId);
			stmt2.setInt(3, managerId);//ManagerID
			stmt2.setString(4, details);
			stmt2.setBoolean(5, true);
			stmt2.setString(6, "NA");
			stmt2.setDate(7, sqlDate);
			stmt2.setBoolean(8, true);
			stmt2.executeUpdate();
			stmt2.close();
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		fresResponse.setResponse("Leave Requested Successfully");
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static FunctionResponse updateLeaveRequest(int eId, int lId,String response) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt1 = DBAccess.getConnection().prepareStatement("UPDATE leavedetails SET requestStatus = ?, response = ? WHERE id = ? and employeeId = ?");
		try{
			stmt1.setBoolean(1, false);
			stmt1.setString(2, response);
			stmt1.setInt(3,lId);
			stmt1.setInt(4,eId);
			stmt1.executeUpdate();
		}finally{
			stmt1.close();
			DBAccess.con.close();
		}
		fresResponse.setEmployeeId(eId);
		fresResponse.setResponse("Leave Response Updated successfully");
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static Leave getLeaves(int eId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from leaves where registrationId = ? and status = ?");
		statemet1.setInt(1, eId);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery() ;
		Leave lea = new Leave();
		try{
			while(rs.next()){
				lea.setId(rs.getInt(1));
				lea.setLeaveNum(rs.getInt(2));
				lea.setRegistrationId(rs.getInt(3));
				lea.setStatus(rs.getBoolean(4));
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		
		return lea;
	}

	public static FunctionResponse updateLeaveNumbers(int eId) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from leaves where registrationId = ? and status = ?");
		statemet1.setInt(1, eId);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery();
		try{
			int leavenum = 0;
			while(rs.next()){
				leavenum = rs.getInt(2);
			}
			PreparedStatement stmt1 = DBAccess.getConnection().prepareStatement("UPDATE leaves SET leaveNum = ? WHERE registrationId = ? and status = ?");
			stmt1.setInt(1,leavenum-1);
			stmt1.setInt(2,eId);
			stmt1.setBoolean(3, true);
			stmt1.executeUpdate();
			stmt1.close();
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		fresResponse.setEmployeeId(eId);
		fresResponse.setResponse("Leave Numbers Updated successfully");
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static List<Registration> getManagersList(int managerId) throws SQLException {
		List<Registration> mangersList = new ArrayList<Registration>();
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from registration where id != ? and managerId = ? and activitystatus = ? and managerstatus = ? and role = ?");
		statemet1.setInt(1, managerId);
		statemet1.setInt(2, managerId);
		statemet1.setBoolean(3, true);
		statemet1.setBoolean(4, true);
		statemet1.setInt(5, 2);
		ResultSet rs = statemet1.executeQuery() ;
		try{
			while(rs.next()){
				Registration register = new Registration();
				register.setId(rs.getInt(1));
				register.setFirstname(rs.getString(2));
				register.setLastname(rs.getString(3));
				register.setEmail(rs.getString(4));
				register.setAddress(rs.getString(5));
				register.setPhone(rs.getString(6));
				register.setUsername(rs.getString(7));
				register.setPassword(rs.getString(8));
				register.setRole(rs.getInt(9));
				register.setManagerstatus(rs.getBoolean(10));
				register.setManagerId(rs.getInt(11));
				register.setActivitystatus(rs.getBoolean(12));
				register.setDate(rs.getDate(13));
				mangersList.add(register);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return mangersList;
	}

	public static List<DirectoryDetails> getDirectoriesOf(int managerId,List<Integer> managerIds) throws SQLException {
		List<DirectoryDetails> directories = new ArrayList<DirectoryDetails>();
		for(int z=0; z<managerIds.size();z++){
			PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails where managerId = ? and permissionId != ? and status = ?");
			statemet1.setInt(1, managerIds.get(z));
			statemet1.setInt(2, 1);//No Public
			statemet1.setBoolean(3, true);
			ResultSet rs = statemet1.executeQuery();
			try{
				while(rs.next()){
					DirectoryDetails dir = new DirectoryDetails();
					dir.setId(rs.getInt(1));
					dir.setDirectoryName(rs.getString(2));
					dir.setManagerId(rs.getInt(3));
					dir.setPermissionId(rs.getInt(4));
					dir.setAccessibleIds(rs.getString(5));
					dir.setStatus(rs.getBoolean(6));
					dir.setATE(rs.getString(7));
					directories.add(dir);
				}
			}finally{
				statemet1.close();
				DBAccess.con.close();
			}
		}
		PreparedStatement statemet2 = DBAccess.getConnection().prepareStatement("select * from directorydetails where managerId != ? and permissionId = ? and status = ?");
		statemet2.setInt(1, managerId);
		statemet2.setInt(2, 1);//Only Public
		statemet2.setBoolean(3, true);
		ResultSet rs = statemet2.executeQuery();
		try{
			while(rs.next()){
				DirectoryDetails dir = new DirectoryDetails();
				dir.setId(rs.getInt(1));
				dir.setDirectoryName(rs.getString(2));
				dir.setManagerId(rs.getInt(3));
				dir.setPermissionId(rs.getInt(4));
				dir.setAccessibleIds(rs.getString(5));
				dir.setStatus(rs.getBoolean(6));
				dir.setATE(rs.getString(7));
				directories.add(dir);
			}
		}finally{
			statemet2.close();
			DBAccess.con.close();
		}
		return directories;
	}

	public static List<Employeedocs> getAllDocumentsOfDirectory(int parseInt) throws SQLException {
		// TODO Auto-generated method stub
		List<Employeedocs> docs = new ArrayList<Employeedocs>();
		PreparedStatement statemet2 = DBAccess.getConnection().prepareStatement("select * from employeedocs where directoryId = ? and status = ?");
		statemet2.setInt(1, parseInt);
		statemet2.setBoolean(2, true);
		ResultSet rs = statemet2.executeQuery();
		try{
			while(rs.next()){
				Employeedocs doc = new Employeedocs();
				doc.setId(rs.getInt(1));
				doc.setDocName(rs.getString(2));
				doc.setDocFile(rs.getBlob(3));
				doc.setDocType(rs.getString(4));
				doc.setComment(rs.getString(5));
				doc.setRegistrationId(rs.getInt(6));
				doc.setDirectoryId(rs.getInt(7));
				doc.setDate(rs.getDate(8));
				doc.setStatus(rs.getBoolean(9));
				docs.add(doc);
			}
		}finally{
			statemet2.close();
			DBAccess.con.close();
		}
		return docs;
	}

	public static FunctionResponse updatePermission(int eId, int dirId,	int permissionId, String accessing) throws SQLException {
		FunctionResponse freResponse = new FunctionResponse();
		PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("UPDATE directorydetails SET permissionId = ?, accessibleIds = ? WHERE id = ? and managerId = ? and status = ?");
		try{
			stmt2.setInt(1, permissionId);
			stmt2.setString(2,accessing);
			stmt2.setInt(3, dirId);
			stmt2.setInt(4, eId);
			stmt2.setBoolean(5, true);
			stmt2.executeUpdate();
		}finally{
			stmt2.close();
			DBAccess.con.close();
		}
		freResponse.setStatus(true);
		freResponse.setEmployeeId(eId);
		freResponse.setResponse("Permission Updated Successfullys");
		return freResponse;
	}

}
