package com.timesheet.model;

public class ProjectModel {
		private String id;
		private String createdby;
		private String projectname ;
		private String startdate;
		private String enddate;
		private String userList;

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCreatedby() {
			return createdby;
		}
		public void setCreatedby(String createdby) {
			this.createdby = createdby;
		}
		public String getProjectname() {
			return projectname;
		}
		public void setProjectname(String projectname) {
			this.projectname = projectname;
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
		public String getUserList() {
			return userList;
		}
		public void setUserList(String userList) {
			this.userList = userList;
		}

}