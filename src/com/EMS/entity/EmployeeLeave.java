package com.EMS.entity;

import java.sql.Date;

public class EmployeeLeave {

	private int id;
	private int employeeId;
	private String details;
	private int managerId;
	private Boolean requestStatus;
	private Date date;
	private Boolean status;
	private String response;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public Boolean getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(Boolean requestStatus) {
		this.requestStatus = requestStatus;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
}
