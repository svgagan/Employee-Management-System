package com.EMS.entity;

public class ATEDetails {
	private int id;
	private int employeeId;
	private int managerId;
	private int directoryId;
	private Boolean ateStatus;
	private Boolean status;
	private String dirName;
	private String createdBy;
	private String fullname;
	
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
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
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public int getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(int directoryId) {
		this.directoryId = directoryId;
	}
	public Boolean getAteStatus() {
		return ateStatus;
	}
	public void setAteStatus(Boolean ateStatus) {
		this.ateStatus = ateStatus;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

}
