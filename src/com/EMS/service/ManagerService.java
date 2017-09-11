package com.EMS.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.EMS.dao.AdminDao;
import com.EMS.dao.EmployeeDao;
import com.EMS.dao.ManagerDao;
import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.Employeedocs;
import com.EMS.entity.Leave;
import com.EMS.entity.LeaveDetails;
import com.EMS.entity.Permission;
import com.EMS.entity.Registration;
import com.EMS.entity.Roles;
import com.EMS.utility.FunctionResponse;

public class ManagerService {

	public static List<DirectoryDetails> getAllDirectories(int eId) throws SQLException {
		// TODO Auto-generated method stub
		List<DirectoryDetails> directory = ManagerDao.getAllDirectories(eId);
		Registration register = EmployeeDao.getUserDetails(eId);
		for (DirectoryDetails directoryDetails : directory) {
			directoryDetails.setPermissions(ManagerDao.getPermission(directoryDetails.getPermissionId()));
			String[] access = directoryDetails.getAccessibleIds().split(",");
			String accessing = "";
			for(int i=0;i<access.length;i++){
				if((access[i].equalsIgnoreCase("ALL"))){
					accessing = "ALL";
				}
				else{
					accessing = accessing + ManagerDao.getName(Integer.parseInt(access[i])) + ",";
				}
			}
			directoryDetails.setAccessibleBy(accessing);
			directoryDetails.setCreatedBy(register.getFirstname()+" "+register.getLastname());
			
			//ATE details
			String ATE = directoryDetails.getATE();
			String[] ate = null;String ateRequests = "";
			if(ATE.equalsIgnoreCase("NA") || ATE.equals(null)){
				ateRequests = "NA";
			}else{
				ate = directoryDetails.getATE().split(",");
				for(int i=0;i<ate.length;i++){
					if((ate[i].equalsIgnoreCase("NA"))){
						ateRequests = "NA";
					}
					else{
						ateRequests = ateRequests + ManagerDao.getName(Integer.parseInt(ate[i])) + ",";
					}
				}
			}
			directoryDetails.setAteBy(ateRequests);
		}
		/*List<DirectoryDetails> publicDirectory = ManagerDao.getAllPublicDirectories();//Public directories of all managers
		for (DirectoryDetails directoryDetails2 : publicDirectory) {
			Registration register2 = EmployeeDao.getUserDetails(directoryDetails2.getManagerId());
			directoryDetails2.setCreatedBy(register2.getFirstname()+" "+register2.getLastname());
			directoryDetails2.setAccessibleBy("ALL");
			directoryDetails2.setPermissions(ManagerDao.getPermission(directoryDetails2.getPermissionId()));
		}
		List<DirectoryDetails> newList = new ArrayList<DirectoryDetails>(directory);
		newList.addAll(publicDirectory);//Merging all directories*/ 		
		return directory;
	}

	public static List<Permission> getAllPermission() throws SQLException {
		List<Permission> permission = ManagerDao.getAllPermission();
 		return permission;
	}

	public static FunctionResponse saveDirectory(int eId, String directory,int perm) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse freResponse = new FunctionResponse();
		if(perm == 1){
			String access = "ALL,";
			freResponse = ManagerDao.saveDirectory(eId,directory,perm,access);//Public directory
		}else if(perm == 2){
			DirectoryDetails dir = ManagerDao.getDirectory(eId);
			String[] access = dir.getAccessibleIds().split(",");
			String accessing = "";//eId+",";
			for(int i=0;i<access.length;i++){
				if(Integer.parseInt(access[i]) == 2){
					accessing = accessing + access[i] + ",";
					break;
				}else{
					accessing = accessing + access[i] + ",";
				}
			}
			freResponse = ManagerDao.saveDirectory(eId,directory,perm,accessing);//Private directory
		}else if(perm == 3){
			DirectoryDetails dir = ManagerDao.getDirectory(eId);
			String[] access = dir.getAccessibleIds().split(",");
			String accessing = "";//eId+",";
			for(int i=0;i<access.length;i++){
					accessing = accessing + access[i] + ",";
			}
			freResponse = ManagerDao.saveDirectory(eId,directory,perm,accessing);//Protected directory
		}else{
			DirectoryDetails dir = ManagerDao.getDirectory(eId);
			String[] access = dir.getAccessibleIds().split(",");
			String accessing = "";//eId+",";
			for(int i=0;i<access.length;i++){
					accessing = accessing + access[i] + ",";
			}
			freResponse = ManagerDao.saveDirectory(eId,directory,perm,accessing);//Default directory
		}
		return freResponse;
	}

	public static List<Registration> getAllActiveEmployees(int eId) throws SQLException {
		// TODO Auto-generated method stub
		List<Registration> activeEmployees = ManagerDao.getAllActiveEmployees(eId);
		for (Registration registration : activeEmployees) {
			Roles role = AdminDao.getRole(registration.getRole());
			registration.setRoles(role.getRolename());
		}
		if(activeEmployees.size() > 0){
			Collections.reverse(activeEmployees);
		}
		return activeEmployees;
	}

	public static FunctionResponse saveBonusDetails(int eid, int paymentid,	double bonus) throws SQLException {
		FunctionResponse fresponse = ManagerDao.saveBonusDetails(eid,paymentid,bonus);
		return fresponse;
	}

	public static List<LeaveDetails> getLeaveDetails(int eId) throws SQLException {
		// TODO Auto-generated method stub
		List<LeaveDetails> leave = ManagerDao.getLeaveDetails(eId);
		if(leave.size() > 0){
			Collections.reverse(leave);
		}
		return leave;
	}

	public static FunctionResponse saveLeaveRequest(int eId, int managerId,String details) throws SQLException {
		FunctionResponse fresponse = ManagerDao.saveLeaveRequest(eId,managerId,details);
		return fresponse;
	}

	public static FunctionResponse updateLeaveRequest(int eId, int lId, int res) throws SQLException {
		FunctionResponse fresponse;
		if(res == 1){
			String response = "Accepted";
			fresponse = ManagerDao.updateLeaveRequest(eId,lId,response);
			if(fresponse.getStatus()){
				FunctionResponse respond = ManagerDao.updateLeaveNumbers(eId);
			}
		}else{
			String response = "Declined";
			fresponse = ManagerDao.updateLeaveRequest(eId,lId,response);
		}
		return fresponse;
	}

	public static List<DirectoryDetails> getSubManagersDirectories(int managerId) throws SQLException {
		// TODO Auto-generated method stub
		List<Integer> managerIds = new ArrayList<>();
		List<Registration> mangersList = ManagerDao.getManagersList(managerId);
		for (Registration registration : mangersList) {
			managerIds.add(registration.getId());
		}
		List<DirectoryDetails> directory = ManagerDao.getDirectoriesOf(managerId,managerIds);
		if(directory.size() > 0){
			for (DirectoryDetails directoryDetails : directory) {
				directoryDetails.setPermissions(ManagerDao.getPermission(directoryDetails.getPermissionId()));
				Registration register = EmployeeDao.getUserDetails(directoryDetails.getManagerId());
				directoryDetails.setCreatedBy(register.getFirstname()+" "+register.getLastname());
			}
		}
		return directory;
	}

	public static List<Employeedocs> getAllDocuments(int parseInt) throws SQLException {
		// TODO Auto-generated method stub
		List<Employeedocs> docs = ManagerDao.getAllDocumentsOfDirectory(parseInt);
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

	public static Leave getLeaveNum(int eId) throws SQLException {
		// TODO Auto-generated method stub
		Leave num = ManagerDao.getLeaves(eId);
		return num;
	}

	public static FunctionResponse updatePermission(int dirId, int permissionId, int eId) throws SQLException {
		FunctionResponse freResponse = new FunctionResponse();
		if(permissionId == 2){
			DirectoryDetails dir = ManagerDao.getDirectory(eId);//Get Default directory
			String[] access = dir.getAccessibleIds().split(",");
			String accessing = "";
			for(int i=0;i<access.length;i++){
				if(Integer.parseInt(access[i]) == 2){
					accessing = accessing + access[i] + ",";
					break;
				}else{
					accessing = accessing + access[i] + ",";
				}
			}
			freResponse = ManagerDao.updatePermission(eId,dirId,permissionId,accessing);//update as Private directory
		}else if(permissionId == 3){
			DirectoryDetails dir = ManagerDao.getDirectory(eId);//Get Default directory
			String accessing = dir.getAccessibleIds();
			freResponse = ManagerDao.updatePermission(eId,dirId,permissionId,accessing);//update as Protected directory
		}else{
			DirectoryDetails dir = ManagerDao.getDirectory(eId);//Get Default directory
			String accessing = dir.getAccessibleIds();
			freResponse = ManagerDao.updatePermission(eId,dirId,permissionId,accessing);//update as Default directory
		}
		return freResponse;
	}

}
