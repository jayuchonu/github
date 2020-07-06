package com.timesheet.dao;

import org.json.JSONObject;

public interface Activity {
 
	public JSONObject actInsert(JSONObject activitydto);
	
	public JSONObject actUpdate(JSONObject activitydto);
	 
	public JSONObject fetchActivity(String id);
	
	public JSONObject activityList();
	
	public JSONObject getProjectList();
	
	public JSONObject deleteActivity(String id);

	int nameExists(String code);
	
}
