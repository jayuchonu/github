package com.timesheet.dao;

import org.json.JSONObject;

import com.timesheet.dto.UserDto;


public interface UserDao {

	public int isexistuser(JSONObject json);
	
	public JSONObject insertUser(JSONObject json);
	
	public int isexistrole(UserDto role);
	
	public String role(UserDto role);
	
	public JSONObject getRoleList();
	
	public JSONObject userList();
	
	public JSONObject updateUser(JSONObject json);
	
	public JSONObject deleteuser(String id);
	
	public JSONObject userById(String userId);
	
	public int login(JSONObject json);
	
	public JSONObject CheckRole(JSONObject jsonObj);
	
	public Boolean authorizeToken(String username, String token);
	
	public void validateToken(String username);
	
	public JSONObject getReportingPerson(int reportingpersonid);
	
	public JSONObject fetchCompanyInfo();
	
	public JSONObject addCompanyInfo(JSONObject json);
	
	public JSONObject passwordchange(JSONObject json);

	
}