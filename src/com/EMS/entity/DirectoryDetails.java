package com.EMS.entity;

public class DirectoryDetails {

	private int id;
	private String directoryName;
	private int managerId;
	private int permissionId;
	private Boolean status;
	private String accessibleIds;
	private String permissions;
	private String accessibleBy;
	private String createdBy;
	private String ATE;
	private String ateBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDirectoryName() {
		return directoryName;
	}
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getAccessibleIds() {
		return accessibleIds;
	}
	public void setAccessibleIds(String accessibleIds) {
		this.accessibleIds = accessibleIds;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	public String getAccessibleBy() {
		return accessibleBy;
	}
	public void setAccessibleBy(String accessibleBy) {
		this.accessibleBy = accessibleBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getATE() {
		return ATE;
	}
	public void setATE(String aTE) {
		ATE = aTE;
	}
	public String getAteBy() {
		return ateBy;
	}
	public void setAteBy(String ateBy) {
		this.ateBy = ateBy;
	}
}
