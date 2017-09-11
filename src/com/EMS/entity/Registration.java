package com.EMS.entity;

import java.sql.Date;

public class Registration {
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private String password;
	private String address;
	private String phone;
	private Boolean activitystatus;
	private Boolean managerstatus;
	private int role;
	private int managerId;
	private Date date;
	private String roles;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getActivitystatus() {
		return activitystatus;
	}
	public void setActivitystatus(Boolean activitystatus) {
		this.activitystatus = activitystatus;
	}
	public Boolean getManagerstatus() {
		return managerstatus;
	}
	public void setManagerstatus(Boolean managerstatus) {
		this.managerstatus = managerstatus;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	

}
