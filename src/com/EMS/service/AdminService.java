package com.EMS.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.EMS.dao.AdminDao;
import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.OrganizationProfile;
import com.EMS.entity.Payment;
import com.EMS.entity.Registration;
import com.EMS.entity.Roles;
import com.EMS.utility.FunctionResponse;

public class AdminService {

	public static List<Registration> getAllActiveEmployees() throws SQLException {
		// TODO Auto-generated method stub
		List<Registration> activeEmployees = AdminDao.getAllActiveEmployees();
		for (Registration registration : activeEmployees) {
			Roles role = AdminDao.getRole(registration.getRole());
			registration.setRoles(role.getRolename());
		}
		if(activeEmployees.size() > 0){
			Collections.reverse(activeEmployees);
		}
		return activeEmployees;
	}

	public static List<Registration> getAllInActiveEmployees() throws SQLException {
		// TODO Auto-generated method stub
		List<Registration> inActiveEmployees = AdminDao.getAllInActiveEmployees();
		for (Registration registration : inActiveEmployees) {
			Roles role = AdminDao.getRole(registration.getRole());
			registration.setRoles(role.getRolename());
		}
		if(inActiveEmployees.size() > 0){
			Collections.reverse(inActiveEmployees);
		}
		return inActiveEmployees;
	}

	public static List<Registration> getManagers() throws SQLException {
		// TODO Auto-generated method stub
		List<Registration> mangersList = AdminDao.getManagers();
		return mangersList;
	}

	public static FunctionResponse saveInactiveUserDetails(int eid, int mid,int roleId) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fresponse = new FunctionResponse();
		if(roleId == 3){
			fresponse = AdminDao.saveInactiveUserDetails(eid,mid,roleId);//Employees
			if(fresponse.getStatus()){
				FunctionResponse res = AdminDao.saveEmployeeToDirectory(eid,mid);//Adding employee to directories table
			}
		}else{
			fresponse = AdminDao.saveInactiveUserDetails(eid,mid,roleId);//Managers
			if(fresponse.getStatus()){
				FunctionResponse res = AdminDao.saveEmployeeToDirectory(eid,mid);//Adding manager to directories table
				List<Integer> superManagerList = AdminDao.getSuperiorManagers(eid,mid);
				String accessibleUsers = Integer.toString(eid)+",";
				for (int i = 0; i < superManagerList.size(); i++) {
					String id = Integer.toString(superManagerList.get(i));
					accessibleUsers = accessibleUsers+id+",";
				}
				FunctionResponse res2 = AdminDao.updateDirectoryList(eid,accessibleUsers);//Creating default directories for new managers
			}
		}
		return fresponse;
	}

	public static Registration getRegistrationDetails(int eid) throws SQLException {
		// TODO Auto-generated method stub
		Registration employee = AdminDao.getRegistrationDetails(eid);
		Registration manager = AdminDao.getRegistrationDetails(employee.getManagerId());
		Roles role = AdminDao.getRole(employee.getRole());
		employee.setRoles(role.getRolename());
		employee.setUsername(manager.getFirstname());
		employee.setPassword(manager.getLastname());
		return employee;
	}

	public static Payment getPaymentDetails(int eid) throws SQLException {
		Payment payment = AdminDao.getPayments(eid);
		return payment;
	}

	public static FunctionResponse savePaymentDetails(int eid, int paymentid,double salary, double total) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fresponse = AdminDao.savePaymentDetails(eid,paymentid,salary,total);
		return fresponse;
	}

	public static OrganizationProfile getEmployeeProfileDetails(int eid) throws SQLException {
		// TODO Auto-generated method stub
		OrganizationProfile profile = AdminDao.getEmployeeProfileDetails(eid);
		return profile;
	}

	public static FunctionResponse saveProfileDetails(int eid, int profileid,String role, String division, String supervisor) throws SQLException {
		// TODO Auto-generated method stub
		FunctionResponse fResponse = new FunctionResponse();
		if(profileid == 0){
			fResponse = AdminDao.saveProfileDetails(eid,profileid,role,division,supervisor);
		}else{
			fResponse = AdminDao.updateProfileDetails(eid,profileid,role,division,supervisor);
		}
		return fResponse;
	}

}
