package com.timesheet.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timesheet.dao.ActivityDao;

class ActivityInTest {

	@Test
	void test() {
		ActivityDao activity= new ActivityDao();
		//ActivityDto act=new ActivityDto();
		JSONObject act = new JSONObject();
		act.put("code","tegration");
		act.put("projectid","1");
		act.put("startdate","2020/02/04");
		act.put("enddate","2020/02/06");
		act.put("otherdetails","");
		act.put("id","1");
		System.out.println(activity.actUpdate(act));
		//System.out.println(activity.actInsert(act));
		
		
	}

}
