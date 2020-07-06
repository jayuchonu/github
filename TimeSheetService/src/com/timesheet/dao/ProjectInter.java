package com.timesheet.dao;

import org.json.JSONArray;
import org.json.JSONObject;


public interface ProjectInter {
	public JSONObject Insertproject(JSONObject project);
	
	public JSONObject updateproject(JSONObject project);
	
	public JSONObject proList(String id);
	
	public JSONObject projectList();
	
	public JSONObject userList();
	
	public JSONObject deleteproject(String id);
	
	public String userproject(String projectid);
	
	public int nameExists(String projectname);
	
	public JSONArray getUserProjectMapping(String projectId);
}
