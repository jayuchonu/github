package com.timesheet.dao;

import java.util.List;

import org.json.JSONObject;

public interface Timesheet {

	JSONObject insertTimesheet(JSONObject json);

	JSONObject updatetime(JSONObject json);

	JSONObject deletetimesheet(String id);

	JSONObject sheetsList(JSONObject json);

	JSONObject TimesheetStatus();

	JSONObject activityList();

	JSONObject fetchtimes(String id);

	JSONObject getProjectList();

	JSONObject sheetList(JSONObject json);

	List<String> getWeekDates(String startdate);

	int isexistTimesheet(JSONObject json);

	JSONObject setNextWeekSheet(String userId);

	JSONObject getId(String[] ids, String submitType);

	JSONObject usersdrop(String userid);
	

}
