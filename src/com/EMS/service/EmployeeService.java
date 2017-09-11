package com.EMS.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.EMS.dao.AdminDao;
import com.EMS.dao.DBAccess;
import com.EMS.dao.EmployeeDao;
import com.EMS.dao.ManagerDao;
import com.EMS.entity.ATEDetails;
import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.Employeedocs;
import com.EMS.entity.LeaveDetails;
import com.EMS.entity.Registration;
import com.EMS.utility.FunctionResponse;

public class EmployeeService {

	public static FunctionResponse registration(String firstname,String lastname, String email, String address, String phone,
			String uid, String password) throws SQLException {
		Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
		Matcher matcher = pattern.matcher(email);
		FunctionResponse response = new FunctionResponse();
		if(firstname.isEmpty() || firstname == null){
			response.setResponse("Invalid Firstname");
			response.setStatus(false);
			return response;
		}else if (lastname.isEmpty() || lastname == null) {
			response.setResponse("Invalid Lastname");
			response.setStatus(false);
			return response;
		}else if(email.isEmpty() || !matcher.matches() || email == null){
			response.setResponse("Invalid Email");
			response.setStatus(false);
			return response;
		}else if (address.isEmpty() || address == null) {
			response.setResponse("Invalid Address");
			response.setStatus(false);
			return response;
		}else if (phone.isEmpty() || phone == null) {
			response.setResponse("Invalid Phone Details");
			response.setStatus(false);
			return response;
		}else if(password.isEmpty() || password == null){
			response.setResponse("Invalid Password");
			response.setStatus(false);
			return response;
		}else if(uid.isEmpty() || uid == null){
			response.setResponse("Invalid Username");
			response.setStatus(false);
			return response;
		}else{
			
			//Statement st = DBAccess.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			response=EmployeeDao.checkUsername(uid);
			if(response.getStatus()){
				response.setResponse("Username Exists");
				response.setStatus(false);
				return response;
			}else{
				FunctionResponse fresResponse = EmployeeDao.registration(firstname, lastname, email, address, phone, uid, password);
				if(fresResponse.getStatus()){
					FunctionResponse respond = EmployeeDao.saveLeaves(fresResponse.getEmployeeId());//Leaves
					FunctionResponse frespond = EmployeeDao.savePayments(fresResponse.getEmployeeId());//Payments
				}
				fresResponse.setResponse("Registered Successfully");
				return fresResponse;
			}
		}
	}
	
	public static FunctionResponse validateLogin(String password, String username) throws SQLException{
		FunctionResponse fresponse = new FunctionResponse();
		if(password.isEmpty() || password == null){
			fresponse.setResponse("Invalid Password");
			fresponse.setStatus(false);
			return fresponse;
		}else if(username.isEmpty() || username == null){
			fresponse.setResponse("Invalid Username");
			fresponse.setStatus(false);
			return fresponse;
		}else{
			fresponse = EmployeeDao.validateLogin(password,username);
			return fresponse;
		}
	}

	public static Boolean getAllEmployeeLeavesDetails() throws SQLException {
		// TODO Auto-generated method stub
		//List<LeaveDetails> employeeLeaves = new ArrayList<LeaveDetails>();
		Boolean employeeLeaves = EmployeeDao.getAllEmployeeLeavesDetails();
		return employeeLeaves;
	}

	public static Registration getUserDetails(int employeeId) throws SQLException {
		// TODO Auto-generated method stub
		Registration register = EmployeeDao.getUserDetails(employeeId);
		return register;
	}

	public static FunctionResponse updateUserDetails(String email,String address, String phone, int id) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fresponse = EmployeeDao.updateUserDetails(email,address,phone,id);
		return fresponse;
	}

	public static Boolean updatePayments() throws SQLException {
		Boolean payment = EmployeeDao.updatePayments();
		return payment;
	}

	public static FunctionResponse saveDocuments(int eId, int directoryId,String docName, String docComment, InputStream inputstream,String contentType) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fresponse = EmployeeDao.saveDocuments(eId, directoryId, docName, docComment, inputstream, contentType);
		return fresponse;
	}

	public static List<Employeedocs> getAllDocuments(int eId) throws SQLException {
		Registration register = EmployeeDao.getUserDetails(eId);
		List<Employeedocs> docs;
		if(register.getRole() == 3){
			docs = EmployeeDao.getAllDocumentsForEmployeeRole(eId);
		}else{
			List<DirectoryDetails> directory = ManagerDao.getAllDirectories(eId);
			docs = EmployeeDao.getAllDocumentsForManagerRole(eId,directory);
		}
		if(docs.size() > 0){
			for (Employeedocs employeedocs : docs) {
				employeedocs.setDirectoryName(EmployeeDao.getDirectory(employeedocs.getDirectoryId()));
				Registration registers = EmployeeDao.getUserDetails(employeedocs.getRegistrationId());
				employeedocs.setCreatedBy(registers.getFirstname()+" "+registers.getLastname());
			}
			Collections.reverse(docs);
		}
		return docs;
	}

	public static List<DirectoryDetails> getAllDirectoriesFor(int eId) throws SQLException {
		// TODO Auto-generated method stub
		Registration register = EmployeeDao.getUserDetails(eId);
		if(register.getActivitystatus()){
			DirectoryDetails dir = ManagerDao.getDirectory(register.getManagerId());
			String[] access = dir.getAccessibleIds().split(",");
			List<Integer> managerIds = new ArrayList<>();
			for(int i=0;i<access.length;i++){
				if(Integer.parseInt(access[i]) == 2){
					String id = access[i];System.out.println("Ids ->"+id);
					managerIds.add(Integer.parseInt(id));
					break;
				}else{
					String id = access[i];System.out.println("Ids ->"+id);
					managerIds.add(Integer.parseInt(id));
				}
			}
			if(register.getRole() == 3){
				List<DirectoryDetails> directory = EmployeeDao.getAllDirectoriesForEmployeeRole(eId,managerIds);
				return directory;
			}else{
				List<DirectoryDetails> directory = EmployeeDao.getAllDirectoriesForManagerRole(eId,managerIds);
				return directory;
			}
		}else{
			return null;
		}		
	}

	public static Employeedocs getDocument(int docId) throws SQLException {
		// TODO Auto-generated method stub
		return EmployeeDao.getDocument(docId);
	}

	public static List<ATEDetails> getATEDetails(int eId) throws SQLException {
		// TODO Auto-generated method stub
		List<ATEDetails> ate = EmployeeDao.getATEDetails(eId);
		if(ate.size() > 0){
			for (ATEDetails ateDetails : ate) {
				ateDetails.setDirName(EmployeeDao.getDirectory(ateDetails.getDirectoryId()));
				ateDetails.setCreatedBy(EmployeeDao.getUserDetails(ateDetails.getManagerId()).getFirstname());
			}
			Collections.reverse(ate);
		}
		return ate;
	}

	public static List<DirectoryDetails> getProtectedDirectoryDetails(int id, int managerId) throws SQLException {
		// TODO Auto-generated method stub
		List<Registration> allManager = AdminDao.getManagers();//All Managers
		DirectoryDetails dir = ManagerDao.getDirectory(managerId);
		String[] access = dir.getAccessibleIds().split(",");
		List<Integer> managerIds = new ArrayList<>();//Particular Employee Manager Tree
		for(int i=0;i<access.length;i++){
			if(Integer.parseInt(access[i]) == 2){
				String ids = access[i];System.out.println("Ids ->"+id);
				managerIds.add(Integer.parseInt(ids));
				break;
			}else{
				String ids = access[i];System.out.println("Ids ->"+id);
				managerIds.add(Integer.parseInt(ids));
			}
		}
		List<Registration> managers = new ArrayList<Registration>();
		Boolean flag = false;
		for(int a=0;a<allManager.size();a++){
			for(int b=0;b<managerIds.size();b++){
				if(allManager.get(a).getId() == managerIds.get(b)){
					flag = true;
					break;
				}else{
					flag = false;
				}
			}
			if(flag == false){
				managers.add(allManager.get(a));
			}
		}
		List<DirectoryDetails> dirs = EmployeeDao.getProtectedDirectoryDetails(managerId,managers);
		return dirs;
	}

	public static FunctionResponse saveATERequest(int empId, int managerId,int dir) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fresponse = EmployeeDao.saveATERequest(empId,managerId,dir);
		return fresponse;
	}

	public static FunctionResponse getATEDetailsFor(int dir, int empId) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fresponse = EmployeeDao.getATEDetailsFor(dir,empId);
		return fresponse;
	}

	public static List<ATEDetails> getATEList(int mId) throws SQLException {
		List<ATEDetails> ate = EmployeeDao.getATEList(mId);
		if(ate.size() > 0){
			for (ATEDetails ateDetails : ate) {
				ateDetails.setDirName(EmployeeDao.getDirectory(ateDetails.getDirectoryId()));
				ateDetails.setCreatedBy(EmployeeDao.getUserDetails(ateDetails.getEmployeeId()).getFirstname());
			}
			Collections.reverse(ate);
		}
		return ate;
	}

	public static DirectoryDetails getDirectoryDetails(int dir) throws SQLException {
		// TODO Auto-generated method stub
		return EmployeeDao.getDirectoryDetails(dir);
	}

	public static FunctionResponse updateATE(int id, int ateId, int permission) throws SQLException {
		FunctionResponse fresponse = new FunctionResponse();
		if(permission == 1){
			ATEDetails ate = EmployeeDao.getATE(ateId);
			DirectoryDetails dir = EmployeeDao.getDirectoryDetails(ate.getDirectoryId());
			String oldAte = dir.getATE();String newAte = null;
			if(oldAte.contentEquals("NA")){
				newAte = Integer.toString(ate.getEmployeeId())+",";
			}else{
				newAte = oldAte+Integer.toString(ate.getEmployeeId())+",";
			}
			fresponse = EmployeeDao.updateDirectory(ate.getEmployeeId(),ate.getManagerId(),ate.getDirectoryId(),newAte);
			if(fresponse.getStatus()){
				FunctionResponse fres = EmployeeDao.updateAteDetails(ateId,permission);
				fresponse.setResponse(fres.getResponse());
			}
		}else{
			FunctionResponse fres = EmployeeDao.updateAteDetails(ateId,permission);
			fresponse.setResponse(fres.getResponse());
		}
		return fresponse;
	}

}
