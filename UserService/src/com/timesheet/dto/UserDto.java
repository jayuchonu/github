package com.timesheet.dto;

public class UserDto {
	
	
	private String firstname;
	private String lastname;
	private String password;
	private String username;
	private String email;
	private int roleId;
	private int userid;
	private String role;
	private  int updatedby;
	private int reportingperson;
	private String reportingpersonName;
	private String reportingpersonEmail;

	public String getReportingpersonEmail() {
		return reportingpersonEmail;
	}

	public void setReportingpersonEmail(String reportingpersonEmail) {
		this.reportingpersonEmail = reportingpersonEmail;
	}

	public String getReportingpersonName() {
		return reportingpersonName;
	}

	public void setReportingpersonName(String reportingpersonName) {
		this.reportingpersonName = reportingpersonName;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getfirstname() {
		return firstname;
	}
	
	public void setfirstname(String firstname) {
		this.firstname=firstname;
	}

	public String getlastname() {
	return lastname;
	
	}
	 public void setlastname(String lastname) {
		 this.lastname=lastname;
	 }

	
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		 this.password=password;
	 }

	public String getusername() {
	return username;
	}

	public void setusername(String username) {
		this.username=username;
	}

	

	public int getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(int updatedby) {
		this.updatedby = updatedby;
	}


	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getReportingperson() {
		return reportingperson;
	}

	public void setReportingperson(int reportingperson) {
		this.reportingperson = reportingperson;
	}

	
		
}
	
	
	
	
	
	
	