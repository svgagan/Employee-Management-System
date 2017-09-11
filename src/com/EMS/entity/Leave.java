package com.EMS.entity;

import java.util.Date;

public class Leave {
	private int id;
	private int leaveNum;
	private int registrationId;
	private Boolean status;
	
	public int getLeaveNum() {
		return leaveNum;
	}
	public void setLeaveNum(int leaveNum) {
		this.leaveNum = leaveNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
