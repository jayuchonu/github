package com.timesheet.model;

public class TimeSheetModel {
	public String timeto;
	private String timefrom;
	private String activity;
	private String activityid;
	private String comments;
	private String tasktype;
	
	private String totalhours;
	private String submitteddate;
	
	private String projectname;
	

	private String id;
	
	private int updatedby;
	
	public String getTimeto() {
		return timeto;
	}

	public void setTimeto(String timeto) {
		this.timeto = timeto;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getTimefrom() {
		return timefrom;
	}

	public void setTimefrom(String timefrom) {
		this.timefrom = timefrom;
	}

	

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	

	public String getTotalhours() {
		return totalhours;
	}

	public void setTotalhours(String totalhours) {
		this.totalhours = totalhours;
	}

	public String getSubmitteddate() {
		return submitteddate;
	}

	public void setSubmitteddate(String submitteddate) {
		this.submitteddate = submitteddate;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
		
	}
	public String getProjectname() {
		return projectname;
	}

	public int getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(int updatedby) {
		this.updatedby = updatedby;
	}

	
}
