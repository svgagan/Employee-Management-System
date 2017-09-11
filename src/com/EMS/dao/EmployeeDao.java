package com.EMS.dao;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.EMS.dao.DBAccess;
import com.EMS.entity.ATEDetails;
import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.Employeedocs;
import com.EMS.entity.Registration;
import com.EMS.utility.FunctionResponse;

public class EmployeeDao {
	
	public static FunctionResponse checkUsername(String userid) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt = DBAccess.getConnection().prepareStatement("select * from registration where username = ?");
		stmt.setString(1, userid);
		ResultSet rs = stmt.executeQuery();
		try{
			while(rs.next()){
				fresResponse.setStatus(true);
				fresResponse.setResponse("Username Exists");
				return fresResponse;
			}
			fresResponse.setStatus(false);
			fresResponse.setResponse("Username Not Exists");
		} finally {
			stmt.close();
			DBAccess.con.close();
		}
		return fresResponse;
	}
	
	public static FunctionResponse validateLogin(String password, String username) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt = DBAccess.getConnection().prepareStatement("select * from registration where username = ? and password = ?");
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		try{
			while(rs.next()){
				fresResponse.setStatus(true);
				fresResponse.setEmployeeId(rs.getInt(1));//EmployeeId
				fresResponse.setResponse(rs.getString(2));//Firstname
				fresResponse.setRole(rs.getInt(9));//Role
				fresResponse.setManagerId(rs.getInt(11));//ManagerId
				fresResponse.setActivityStatus(rs.getBoolean(12));
				return fresResponse;
			}
			fresResponse.setStatus(false);
			fresResponse.setEmployeeId(0);
			fresResponse.setActivityStatus(false);
			fresResponse.setResponse("Invalid Login Credentials");
		}finally{
			stmt.close();
			DBAccess.con.close();
		}
		return fresResponse;
	}
	
	public static FunctionResponse registration(String firstname,String lastname,String email,String address,String phone,String username,String password) throws SQLException{
		java.util.Date myDate = new java.util.Date();
		Date sqlDate = new java.sql.Date(myDate.getTime());
		FunctionResponse fresResponse = new FunctionResponse();
		/*PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from registration");
		ResultSet rs = statemet1.executeQuery() ;
		int size=1;*/
		PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("INSERT INTO registration (firstname, lastname, email, address , phone ,  username, password, role, managerstatus, managerId , activitystatus , date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
		try{
			/*while(rs.next()){
				size++;
			}*/
			//PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("INSERT INTO registration (firstname, lastname, email, address , phone ,  username, password, role, managerstatus, managerId , activitystatus , date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			//stmt2.setInt(1,size);
			stmt2.setString(1, firstname);
			stmt2.setString(2, lastname);
			stmt2.setString(3, email);
			stmt2.setString(4, address);
			stmt2.setString(5, phone);
			stmt2.setString(6, username);
			stmt2.setString(7, password);
			stmt2.setInt(8, 3);//Employee roleId
			stmt2.setBoolean(9, false);//Manager status
			stmt2.setInt(10, (Integer) 0);
			stmt2.setBoolean(11, false);//Active-Inactive status
			stmt2.setDate(12, sqlDate);
			stmt2.executeUpdate();
			ResultSet rss = stmt2.getGeneratedKeys();
			if(rss.next())
				fresResponse.setEmployeeId(rss.getInt(1));
		}finally{
			//statemet1.close();
			stmt2.close();
			DBAccess.con.close();
		}
		fresResponse.setStatus(true);
		return fresResponse;
	}
	
	public static FunctionResponse saveLeaves(int employeeId) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("INSERT INTO leaves (leaveNum, registrationId, status) values (?,?,?)");
		try{
			stmt2.setInt(1,(Integer)4);//Default leaves
			stmt2.setInt(2,employeeId);
			stmt2.setBoolean(3,true);
			stmt2.executeUpdate();
		}finally{
			stmt2.close();
			DBAccess.con.close();
		}
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static FunctionResponse savePayments(int employeeId) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fresResponse = new FunctionResponse();
		/*PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from payment");
		ResultSet rs = statemet1.executeQuery() ;
		int size=1; 
		while(rs.next()){
			size++;
		}
		statemet1.close();*/
		PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("INSERT INTO payment (registrationId, accountNum, salary, bonus, total, status) values (?,?,?,?,?,?)");
		try{
			//stmt2.setInt(1,size);
			stmt2.setInt(1, employeeId);
			stmt2.setString(2, "NA");//account number
			stmt2.setDouble(3, (double)100000);//Salary 100k dollars
			stmt2.setDouble(4, (double)0);//bonus
			stmt2.setDouble(5, (double)0);//total
			stmt2.setBoolean(6, true);
			stmt2.executeUpdate();
			//stmt2.close();
			//DBAccess.con.close();
		}finally{
			stmt2.close();
			DBAccess.con.close();
		}
		//fresResponse.setEmployeeId(employeeId);
		fresResponse.setStatus(true);
		return fresResponse;
	}

	public static Boolean getAllEmployeeLeavesDetails() throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from leaves");
		try{
			ResultSet rs = statemet1.executeQuery() ;
			while(rs.next()){
				PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("UPDATE leaves SET leaveNum = ? WHERE registrationId = ?");
				stmt2.setInt(1,(rs.getInt(2)+4));//leaves updation
				stmt2.setInt(2,rs.getInt(3));
				stmt2.executeUpdate();
				stmt2.close();
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return true;
	}

	public static Registration getUserDetails(int employeeId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from registration where id = ?");// and status = ?
		statemet1.setInt(1, employeeId);
		//statemet1.setBoolean(2, true);
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
				register.setUsername(rs.getString(7));
				register.setPassword(rs.getString(8));
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

	public static FunctionResponse updateUserDetails(String email,String address, String phone, int id) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt1 = DBAccess.getConnection().prepareStatement("UPDATE registration SET email = ?, address = ?, phone = ? WHERE id = ?");
		try{
			stmt1.setString(1,email);
			stmt1.setString(2,address);
			stmt1.setString(3,phone);
			stmt1.setInt(4,id);
			stmt1.executeUpdate();
		}finally{
			stmt1.close();
			DBAccess.con.close();
		}
		fresResponse.setEmployeeId(id);
		fresResponse.setResponse("Profile Details Updated Successfully");	
		return fresResponse;
	}

	public static Boolean updatePayments() throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from payment");
		ResultSet rs = statemet1.executeQuery() ;
		try{
			while(rs.next()){
				PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("UPDATE payment SET bonus = ?, total = ? WHERE registrationId = ? and status = ?");
				stmt2.setDouble(1, (double)0);
				stmt2.setDouble(2, (double)0);
				stmt2.setInt(3,rs.getInt(2));
				stmt2.setBoolean(4, true);
				stmt2.executeUpdate();
				stmt2.close();
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return true;
	}

	public static FunctionResponse saveDocuments(int eId, int directoryId, String docName, String docComment, InputStream inputstream,
			String contentType) throws SQLException {
		java.util.Date myDate = new java.util.Date();
		Date sqlDate = new java.sql.Date(myDate.getTime());
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from employeedocs");
		ResultSet rs = statemet1.executeQuery() ;
		try{
			int size=1; 
			while(rs.next()){
				size++;
			}
			PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("INSERT INTO employeedocs (id, docName, docFile, docType, comment, registrationId, directoryId, date, status) values (?,?,?,?,?,?,?,?,?)");
			stmt2.setInt(1,size);
			stmt2.setString(2, docName);
			stmt2.setBlob(3, inputstream);
			stmt2.setString(4, contentType);
			stmt2.setString(5, docComment);
			stmt2.setInt(6, eId);
			stmt2.setInt(7, directoryId);
			stmt2.setDate(8, sqlDate);
			stmt2.setBoolean(9, true);
			stmt2.executeUpdate();
			stmt2.close();
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		fresResponse.setStatus(true);
		fresResponse.setResponse("Documents Uploaded Successfully");
		return fresResponse;
	}

	//List of all documents uploaded by particular employee
	public static List<Employeedocs> getAllDocumentsForEmployeeRole(int eId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from employeedocs where registrationId = ? and status = ?");
		statemet1.setInt(1, eId);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery() ;
		List<Employeedocs> docs = new ArrayList<Employeedocs>();
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
			statemet1.close();
			DBAccess.con.close();
		}
		return docs;
	}

	//List of all directories employee can see
	public static List<DirectoryDetails> getAllDirectoriesForEmployeeRole(int eId, List<Integer> managerIds) throws SQLException {
		List<DirectoryDetails> directories = new ArrayList<DirectoryDetails>();
		try{
			for(int z=0; z<managerIds.size();z++){
				PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails dir where dir.managerId = ? and dir.status = ? and (dir.permissionId = ? or dir.permissionId = ?)");
				statemet1.setInt(1, managerIds.get(z));
				statemet1.setBoolean(2, true);
				statemet1.setInt(3, 3);//Protected
				statemet1.setInt(4, 4);//Default
				ResultSet rs = statemet1.executeQuery() ;
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
				statemet1.close();
			}
			PreparedStatement statemet2 = DBAccess.getConnection().prepareStatement("select * from directorydetails where permissionId = ? and status = ?");
			statemet2.setInt(1, 1);
			statemet2.setBoolean(2, true);
			ResultSet rs = statemet2.executeQuery() ;
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
			statemet2.close();
			//ATE related
			List<FunctionResponse> fres = new ArrayList<FunctionResponse>();
			PreparedStatement statemet3 = DBAccess.getConnection().prepareStatement("select * from ate where employeeId = ? and ateStatus = ?");
			statemet3.setInt(1, eId);
			statemet3.setBoolean(2, true);
			ResultSet rst = statemet3.executeQuery() ;
			while(rst.next()){
				FunctionResponse dir = new FunctionResponse();
				dir.setId(rst.getInt(4));
				dir.setManagerId(rst.getInt(3));
				fres.add(dir);
			}
			statemet3.close();
			for(int z=0; z<fres.size();z++){
				PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails dir where dir.id = ? and dir.managerId = ? and dir.permissionId = ?");
				statemet1.setInt(1, fres.get(z).getId());
				statemet1.setInt(2, fres.get(z).getManagerId());
				statemet1.setInt(3, 3);//Protected
				ResultSet rss = statemet1.executeQuery() ;
				while(rss.next()){
					DirectoryDetails dir = new DirectoryDetails();
					dir.setId(rss.getInt(1));
					dir.setDirectoryName(rss.getString(2));
					dir.setManagerId(rss.getInt(3));
					dir.setPermissionId(rss.getInt(4));
					dir.setAccessibleIds(rss.getString(5));
					dir.setStatus(rss.getBoolean(6));
					dir.setATE(rss.getString(7));
					directories.add(dir);
				}
				statemet1.close();
			}
		}finally{
			DBAccess.con.close();
		}
		return directories;
	}

	public static String getDirectory(int directoryId) throws SQLException {
		DirectoryDetails dir = new DirectoryDetails();
		PreparedStatement statemet2 = DBAccess.getConnection().prepareStatement("select * from directorydetails where id = ? and status = ?");
		try{
			statemet2.setInt(1, directoryId);
			statemet2.setBoolean(2, true);
			ResultSet rs = statemet2.executeQuery() ;
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
			statemet2.close();
			DBAccess.con.close();
		}
		return dir.getDirectoryName();
	}

	//List of all directories Manager can see
	public static List<DirectoryDetails> getAllDirectoriesForManagerRole(int eId, List<Integer> managerIds) throws SQLException {
		List<DirectoryDetails> directories = new ArrayList<DirectoryDetails>();
		try{
			for(int z=0; z<managerIds.size();z++){
				PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails dir where dir.managerId = ? and dir.status = ? and (dir.permissionId = ? or dir.permissionId = ?)");
				statemet1.setInt(1, managerIds.get(z));
				statemet1.setBoolean(2, true);
				statemet1.setInt(3, 3);//Protected
				statemet1.setInt(4, 4);//Default
				ResultSet rs = statemet1.executeQuery() ;
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
				statemet1.close();
			}
			PreparedStatement statemet2 = DBAccess.getConnection().prepareStatement("select * from directorydetails where permissionId = ? and status = ?");
			statemet2.setInt(1, 1);
			statemet2.setBoolean(2, true);
			ResultSet rs = statemet2.executeQuery() ;
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
			statemet2.close();
			PreparedStatement statemet3 = DBAccess.getConnection().prepareStatement("select * from directorydetails where managerId = ? and permissionId != ? and status = ?");
			statemet3.setInt(1, eId);
			statemet3.setInt(2, 1);
			statemet3.setBoolean(3, true);
			ResultSet rss = statemet3.executeQuery() ;
			while(rss.next()){
				DirectoryDetails dir = new DirectoryDetails();
				dir.setId(rss.getInt(1));
				dir.setDirectoryName(rss.getString(2));
				dir.setManagerId(rss.getInt(3));
				dir.setPermissionId(rss.getInt(4));
				dir.setAccessibleIds(rss.getString(5));
				dir.setStatus(rss.getBoolean(6));
				dir.setATE(rss.getString(7));
				directories.add(dir);
			}
			statemet3.close();
		}finally{
			DBAccess.con.close();
		}
		return directories;
	}

	//List of all documents of manager and documents in his directories uploaded by other employees
	public static List<Employeedocs> getAllDocumentsForManagerRole(int eId, List<DirectoryDetails> directory) throws SQLException {
		List<Employeedocs> docs = new ArrayList<Employeedocs>();
		try{
			for(int z=0; z<directory.size();z++){
				PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from employeedocs where registrationId != ? and directoryId = ? and status = ?");
				statemet1.setInt(1, eId);
				statemet1.setInt(2, directory.get(z).getId());
				statemet1.setBoolean(3, true);
				ResultSet rs = statemet1.executeQuery() ;
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
				statemet1.close();
			}
			PreparedStatement statemet2 = DBAccess.getConnection().prepareStatement("select * from employeedocs where registrationId = ? and status = ?");
			statemet2.setInt(1, eId);
			statemet2.setBoolean(2, true);
			ResultSet rs = statemet2.executeQuery() ;
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
			statemet2.close();
		}finally{
			DBAccess.con.close();
		}
		return docs;
	}

	public static Employeedocs getDocument(int docId) throws SQLException {
		PreparedStatement statemet2 = DBAccess.getConnection().prepareStatement("select * from employeedocs where id = ? and status = ?");
		statemet2.setInt(1, docId);
		statemet2.setBoolean(2, true);
		ResultSet rs = statemet2.executeQuery();
		Employeedocs doc = new Employeedocs();
		try{
			while(rs.next()){
				doc.setId(rs.getInt(1));
				doc.setDocName(rs.getString(2));
				doc.setDocFile(rs.getBlob(3));
				doc.setDocType(rs.getString(4));
				doc.setComment(rs.getString(5));
				doc.setRegistrationId(rs.getInt(6));
				doc.setDirectoryId(rs.getInt(7));
				doc.setDate(rs.getDate(8));
				doc.setStatus(rs.getBoolean(9));
			}
		}finally{
			statemet2.close();
			DBAccess.con.close();
		}
		return doc;
	}

	public static List<ATEDetails> getATEDetails(int eId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from ate where employeeId = ? and status = ?");
		statemet1.setInt(1, eId);
		statemet1.setBoolean(2, true);
		ResultSet rs = statemet1.executeQuery();
		List<ATEDetails> ATE = new ArrayList<ATEDetails>();
		try{
			while(rs.next()){
				ATEDetails doc = new ATEDetails();
				doc.setId(rs.getInt(1));
				doc.setEmployeeId(rs.getInt(2));
				doc.setManagerId(rs.getInt(3));
				doc.setDirectoryId(rs.getInt(4));
				doc.setAteStatus(rs.getBoolean(5));
				doc.setStatus(rs.getBoolean(6));
				ATE.add(doc);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return ATE;
	}

	public static List<DirectoryDetails> getProtectedDirectoryDetails(int managerId, List<Registration> managers) throws SQLException {
		List<DirectoryDetails> directories = new ArrayList<DirectoryDetails>();
		try{
			for(int k=0;k<managers.size();k++){
				PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails where managerId = ? and permissionId = ? and status = ?");
				statemet1.setInt(1, managers.get(k).getId());
				statemet1.setInt(2, 3);//Protected
				statemet1.setBoolean(3, true);
				ResultSet rs = statemet1.executeQuery() ;
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
				statemet1.close();
			}
		}finally{
			DBAccess.con.close();
		}
		return directories;
	}

	public static FunctionResponse saveATERequest(int empId, int managerId,	int dir) throws SQLException {
		FunctionResponse fresResponse = new FunctionResponse();
		PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("INSERT INTO ate (employeeId, managerId, directoryId, ateStatus, status) values (?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
		try{
			stmt2.setInt(1,empId);
			stmt2.setInt(2, managerId);
			stmt2.setInt(3, dir);
			stmt2.setBoolean(4, false);
			stmt2.setBoolean(5, true);
			stmt2.executeUpdate();
			ResultSet rss = stmt2.getGeneratedKeys();
			if(rss.next())
				fresResponse.setId(rss.getInt(1));
		}finally{
			stmt2.close();
			DBAccess.con.close();
		}
		fresResponse.setStatus(true);
		fresResponse.setResponse("Accessing Permission Requested Successfully");
		return fresResponse;
	}

	public static FunctionResponse getATEDetailsFor(int dir, int empId) throws SQLException {
		FunctionResponse response = new FunctionResponse();
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from ate where directoryId = ? and employeeId = ?");
		statemet1.setInt(1, dir);
		statemet1.setInt(2, empId);
		ResultSet rs = statemet1.executeQuery() ;
		try{
			while(rs.next()){
				response.setId(rs.getInt(1));
				response.setEmployeeId(rs.getInt(2));
				response.setManagerId(rs.getInt(3));
				response.setStatus(true);
				return response;
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		response.setId(0);
		response.setStatus(false);
		return response;
	}
	
	public static List<ATEDetails> getATEList(int mId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from ate where managerId = ? and ateStatus = ? and status = ?");
		statemet1.setInt(1, mId);
		statemet1.setBoolean(2, false);
		statemet1.setBoolean(3, true);
		ResultSet rs = statemet1.executeQuery();
		List<ATEDetails> ATE = new ArrayList<ATEDetails>();
		try{
			while(rs.next()){
				ATEDetails doc = new ATEDetails();
				doc.setId(rs.getInt(1));
				doc.setEmployeeId(rs.getInt(2));
				doc.setManagerId(rs.getInt(3));
				doc.setDirectoryId(rs.getInt(4));
				doc.setAteStatus(rs.getBoolean(5));
				doc.setStatus(rs.getBoolean(6));
				ATE.add(doc);
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return ATE;
	}
	
	public static DirectoryDetails getDirectoryDetails(int dirId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from directorydetails where id = ? and status = ?");
		statemet1.setInt(1, dirId);
		statemet1.setBoolean(2, true);
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

	public static ATEDetails getATE(int ateId) throws SQLException {
		PreparedStatement statemet1 = DBAccess.getConnection().prepareStatement("select * from ate where id = ?");
		statemet1.setInt(1, ateId);
		ResultSet rs = statemet1.executeQuery() ;
		ATEDetails ate = new ATEDetails();
		try{
			while(rs.next()){
				ate.setId(rs.getInt(1));
				ate.setEmployeeId(rs.getInt(2));
				ate.setManagerId(rs.getInt(3));
				ate.setDirectoryId(rs.getInt(4));
				ate.setAteStatus(rs.getBoolean(5));
				ate.setStatus(rs.getBoolean(6));
			}
		}finally{
			statemet1.close();
			DBAccess.con.close();
		}
		return ate;
	}

	public static FunctionResponse updateDirectory(int employeeId,int managerId, int directoryId, String newAte) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse freResponse = new FunctionResponse();
		PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("UPDATE directorydetails SET ateIds = ? WHERE id = ? and status = ?");
		try{
			stmt2.setString(1, newAte);
			stmt2.setInt(2,directoryId);
			stmt2.setBoolean(3, true);
			stmt2.executeUpdate();
		}finally{
			stmt2.close();
			DBAccess.con.close();
		}
		freResponse.setStatus(true);
		freResponse.setEmployeeId(employeeId);
		freResponse.setManagerId(managerId);
		return freResponse;
	}

	public static FunctionResponse updateAteDetails(int ateId, int permission) throws SQLException {
		FunctionResponse freResponse = new FunctionResponse();
		PreparedStatement stmt2 = DBAccess.getConnection().prepareStatement("UPDATE ate SET ateStatus = ? WHERE id = ? and status = ?");
		try{
			if(permission == 0){
				stmt2.setBoolean(1, false);
			}else{
				stmt2.setBoolean(1, true);
			}
			stmt2.setInt(2,ateId);
			stmt2.setBoolean(3, true);
			stmt2.executeUpdate();
		}finally{
			stmt2.close();
			DBAccess.con.close();
		}
		freResponse.setStatus(true);
		freResponse.setId((ateId));
		freResponse.setResponse("Permission Updated");
		return freResponse;
	}
	

}
