package com.EMS.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.OrganizationProfile;
import com.EMS.entity.Payment;
import com.EMS.entity.Registration;
import com.EMS.entity.Roles;
import com.EMS.utility.FunctionResponse;

public class AdminDao {

	public static List<Registration> getAllActiveEmployees() throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from registration where activitystatus = ?");
		statemet1.setBoolean(1, true);
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

	public static Roles getRole(int role) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from roles where id = ?");
		statemet1.setInt(1, role);
		ResultSet rs = statemet1.executeQuery() ;
		Roles roles = new Roles();
		try{
			while(rs.next()){
				roles.setId(rs.getInt(1));
				roles.setRolename(rs.getString(2));
				roles.setStatus(rs.getBoolean(3));
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return roles;
	}

	public static List<Registration> getAllInActiveEmployees() throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from registration where activitystatus = ?");
		statemet1.setBoolean(1, false);
		ResultSet rs = statemet1.executeQuery() ;
		List<Registration> inActiveEmployees = new ArrayList<Registration>();
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
				inActiveEmployees.add(register);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return inActiveEmployees;
	}

	public static List<Registration> getManagers() throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from registration where activitystatus = ? and managerstatus = ? and role = ?");
		statemet1.setBoolean(1, true);
		statemet1.setBoolean(2, true);
		statemet1.setInt(3, 2);
		ResultSet rs = statemet1.executeQuery() ;
		List<Registration> mangersList = new ArrayList<Registration>();
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

	public static FunctionResponse saveInactiveUserDetails(int eid, int mid,int roleId) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt1 = DBAccess.getConnection().prepareStatement("UPDATE registration SET managerId = ?, role = ?, managerstatus = ?, activitystatus = ? WHERE id = ?");
		try{
			stmt1.setInt(1,mid);
			stmt1.setInt(2,roleId);
			if(roleId == 2){
				stmt1.setBoolean(3, true);
			}else{
				stmt1.setBoolean(3, false);
			}
			stmt1.setBoolean(4,true);
			stmt1.setInt(5,eid);
			stmt1.executeUpdate();
		}finally{
			stmt1.close();
			DBAccess.con.close();
		}
		fresResponse.setEmployeeId(eid);
		fresResponse.setResponse("Employee details activated successfully");//(dir.permissionId = ? or dir.permissionId = ?)
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static FunctionResponse saveEmployeeToDirectory(int eid, int mid) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails where managerId = ? and (permissionId = ? or permissionId = ?) and status = ?");
		statemet1.setInt(1, mid);
		statemet1.setInt(2, 3);//Protected permission
		statemet1.setInt(3, 4);//Default permission
		statemet1.setBoolean(4, true);
		ResultSet rs = statemet1.executeQuery() ;
		try{
			while(rs.next()){
				PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("UPDATE directorydetails SET accessibleIds = ? WHERE id = ?");
				stmt2.setString(1,(rs.getString(5)+Integer.toString(eid)+","));//Directory Accessible Ids updation
				stmt2.setInt(2,rs.getInt(1));
				stmt2.executeUpdate();
				stmt2.close();
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static List<Integer> getSuperiorManagers(int eid, int mid) throws SQLException {
		// TODO Auto-generated method stub
		List<Integer> superManager = new ArrayList<Integer>();
		superManager.add(mid);//superManager.add(eid);
		int managerId = mid;
		try{
			while(managerId != 2){
				PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails where managerId = ? and permissionId = ? and status = ?");
				statemet1.setInt(1, managerId);
				statemet1.setInt(2, 4);//default directory
				statemet1.setBoolean(3, true);
				ResultSet rs = statemet1.executeQuery();
				while(rs.next()){
					String accessId = rs.getString(5);
					String[] access = accessId.split(",");
					for(int i=0;i<access.length;i++){
						managerId = Integer.parseInt(access[1]);break;
					}
					superManager.add(managerId);
				}
				statemet1.close();
			}
		}finally{
			DBAccess.con.close();
		}
		return superManager;
	}

	public static FunctionResponse updateDirectoryList(int eid,String accessibleUsers) throws SQLException {
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
			stmt2.setString(2, "DIR-"+eid);//Default Directory Name
			stmt2.setInt(3, eid);//ManagerID
			stmt2.setInt(4, 4);//Default Permission
			stmt2.setString(5, accessibleUsers);//Accessible User Details
			stmt2.setBoolean(6, true);
			stmt2.setString(7, "NA");//ATE User Details
			stmt2.executeUpdate();
			stmt2.close();
		}finally{
			statemet1.close();
			fresResponse.setStatus(true);
		}
		return fresResponse;
	}

	public static Registration getRegistrationDetails(int eid) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from registration where id = ? and activitystatus = ?");
		statemet1.setInt(1, eid);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery() ;
		Registration register = new Registration();
		try{
			while(rs.next()){
				register.setId(rs.getInt(1));
				register.setFirstname(rs.getString(2));
				register.setLastname(rs.getString(3));
				register.setEmail(rs.getString(4));
				register.setAddress(rs.getString(5));
				register.setPhone(rs.getString(6));
				register.setRole(rs.getInt(9));
				register.setManagerstatus(rs.getBoolean(10));
				register.setManagerId(rs.getInt(11));
				register.setActivitystatus(rs.getBoolean(12));
				register.setDate(rs.getDate(13));
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return register;
	}

	public static Payment getPayments(int eid) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from payment where registrationId = ? and status = ?");
		statemet1.setInt(1, eid);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery() ;
		Payment pay = new Payment();
		try{
			while(rs.next()){
				pay.setId(rs.getInt(1));
				pay.setRegistrationId(rs.getInt(2));
				pay.setAccount(rs.getString(3));
				pay.setSalary(rs.getDouble(4));
				pay.setBonus(rs.getDouble(5));
				pay.setTotal(rs.getDouble(6));
				pay.setStatus(rs.getBoolean(7));
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return pay;
	}

	public static FunctionResponse savePaymentDetails(int eid, int paymentid,double salary, double total) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt1 = DBAccess.getConnection().prepareStatement("UPDATE payment SET salary = ?, total = ? WHERE id = ? and registrationId = ?");
		try{
			stmt1.setDouble(1, salary);
			stmt1.setDouble(2, total);
			stmt1.setInt(3,paymentid);
			stmt1.setInt(4,eid);
			stmt1.executeUpdate();
		}finally{
			stmt1.close();
			DBAccess.con.close();
		}
		fresResponse.setEmployeeId(eid);
		fresResponse.setResponse("Payments Details Updated & Paycheck Generated successfully");
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static OrganizationProfile getEmployeeProfileDetails(int eid) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from employeedetails where employeeId = ? and status = ?");
		statemet1.setInt(1, eid);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery() ;
		OrganizationProfile profile = new OrganizationProfile();
		try{
			while(rs.next()){
				profile.setId(rs.getInt(1));
				profile.setEmployeeId(rs.getInt(2));
				profile.setRole(rs.getString(3));
				profile.setDivisionName(rs.getString(4));
				profile.setSupervisor(rs.getString(5));
				profile.setStatus(rs.getBoolean(6));
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return profile;
	}

	public static FunctionResponse saveProfileDetails(int eid, int profileid,String role, String division, String supervisor) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from employeedetails");
		ResultSet rs = statemet1.executeQuery() ;
		try{
			int size=1; 
			while(rs.next()){
				size++;
			}
			PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("INSERT INTO employeedetails (id, employeeId, role, divisionName, supervisor, status) values (?,?,?,?,?,?)");
			stmt2.setInt(1,size);
			stmt2.setInt(2,eid);
			stmt2.setString(3, role);//Role
			stmt2.setString(4, division);//Division
			stmt2.setString(5, supervisor);//Supervisor
			stmt2.setBoolean(6, true);
			stmt2.executeUpdate();
			stmt2.close();
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		fresResponse.setStatus(true);
		fresResponse.setResponse("Organization Profile Details Updated Successfully");
		return fresResponse;
	}

	public static FunctionResponse updateProfileDetails(int eid, int profileid,String role, String division, String supervisor) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt1 = DBAccess.getConnection().prepareStatement("UPDATE employeedetails SET role = ?, divisionName = ? WHERE id = ? and employeeId = ?");
		try{
			stmt1.setString(1, role);
			stmt1.setString(2, division);
			stmt1.setInt(3,profileid);
			stmt1.setInt(4,eid);
			stmt1.executeUpdate();
		}finally{
			stmt1.close();
			DBAccess.con.close();
		}
		fresResponse.setEmployeeId(eid);
		fresResponse.setResponse("Organization Profile Details Updated Successfully");
		fresResponse.setStatus(true);
		return fresResponse;
	}

}
