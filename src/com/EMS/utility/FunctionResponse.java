package com.EMS.utility;

public class FunctionResponse {
	
	private Boolean status;
	private String response;
	private int id;
	private int employeeId;
	private int managerId;
	private Boolean managerStatus;
	private int role;
	private Boolean activityStatus;
	private int permission;
	
	public Boolean getManagerStatus() {
		return managerStatus;
	}
	public void setManagerStatus(Boolean managerStatus) {
		this.managerStatus = managerStatus;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public Boolean getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Boolean activityStatus) {
		this.activityStatus = activityStatus;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
