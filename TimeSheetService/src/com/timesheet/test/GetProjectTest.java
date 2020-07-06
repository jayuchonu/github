package com.timesheet.test;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timesheet.dao.ActivityDao;

class GetProjectTest {

	@Test
	void test() {
		
		ActivityDao act=new ActivityDao();
	
		JSONObject jsonobj=act.getProjectList();
		JSONArray jsonArr=jsonobj.getJSONArray("getProjectList");
		
		for(int i=0;i<jsonArr.length();i++) {
			JSONObject json =jsonArr.getJSONObject(i);
			System.out.println(json.getInt("id"));
			System.out.println(json.get("projectname"));
		}
	}
}
