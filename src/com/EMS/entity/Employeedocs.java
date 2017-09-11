package com.EMS.entity;

import java.sql.Blob;
import java.util.Date;

public class Employeedocs {
	private int id;
	private String docName;
	private Blob docFile;
	private String docType;
	private String comment;
	private int registrationId;
	private Boolean status;
	private int directoryId;
	private Date date;
	private String directoryName;
	private String dirOwnedBy;
	private String createdBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public Blob getDocFile() {
		return docFile;
	}
	public void setDocFile(Blob docFile) {
		this.docFile = docFile;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public int getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(int directoryId) {
		this.directoryId = directoryId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDirectoryName() {
		return directoryName;
	}
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	public String getDirOwnedBy() {
		return dirOwnedBy;
	}
	public void setDirOwnedBy(String dirOwnedBy) {
		this.dirOwnedBy = dirOwnedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	

}
