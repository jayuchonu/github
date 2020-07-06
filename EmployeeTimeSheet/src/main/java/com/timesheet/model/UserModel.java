package com.timesheet.model;

public class UserModel {
	private String firstname;
	private String lastname;
	private String role;
	private String email;
	private String reportingperson;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	private int userid;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getReportingperson() {
		return reportingperson;
	}
	public void setReportingperson(String reportingperson) {
		this.reportingperson = reportingperson;
	}
}