package com.timesheet.model;

public class ActivityModel {
	 
	
	private String id;
	private String code;
	private String projectid;
	private String startdate;
	private String enddate;
	private String otherdetails;
	private String projectname;
	

	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getOtherdetails() {
		return otherdetails;
	}
	public void setOtherdetails(String otherdetails) {
		this.otherdetails = otherdetails;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
