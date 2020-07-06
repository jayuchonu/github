package com.timesheet.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timesheet.dao.ActivityDao;

class ActivityListTest {

	@Test
	void test() {
		
		ActivityDao activitydao =new ActivityDao();
		//JSONObject jsonobj =activitydao.fetchActivity("1");
		JSONObject jsonobj = activitydao.activityList();
		System.out.println(jsonobj);
//		JSONArray jsonarr=jsonobj.getJSONArray("fetchActivity");
//		
//		for( int i=0;i<jsonarr.length();i++) {
//			JSONObject json =jsonarr.getJSONObject(i);
//			System.out.println(json.get("code"));
//			System.out.println(json.get("project_id"));
//			System.out.println(json.get("start_date"));
//			System.out.println(json.get("end_date"));
//		}
}
}