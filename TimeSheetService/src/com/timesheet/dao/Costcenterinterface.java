package com.timesheet.dao;
import org.json.JSONObject;
public interface Costcenterinterface {
	
	public JSONObject Insertcost(JSONObject cost);
	
	public JSONObject updatecost(JSONObject cost);
	
	public JSONObject costList();
	
	public JSONObject NameById(String Id);
	
	public JSONObject userlist();
	
	public JSONObject deletecost(String Id);
	
	public int Nameexists(JSONObject cost);
}