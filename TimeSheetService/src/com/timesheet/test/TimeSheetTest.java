package com.timesheet.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timesheet.dao.TimeSheetDao;

class TimeSheetTest {
	@Test
	void test() {
	TimeSheetDao timesheet =new TimeSheetDao();
//	TimeSheetDto employeetimesheet = new TimeSheetDto();
//	employeetimesheet.setTimefrom("16:00:00");
//	employeetimesheet.setTimeto(" 19:30:26");
//	employeetimesheet.setActivityid("1");
//	employeetimesheet.setComments("descRIPTION");
//	employeetimesheet.setUpdatedby(1);
//	employeetimesheet.getTotalhours();
//	employeetimesheet.setSubmitteddate("2020-04-25");
//	
//	timesheet.Insert(employeetimesheet);
//	
//timesheet.updatetime(employeetimesheet);
	JSONObject json = new JSONObject();
	json.put("startdate", "05/08/2020");
	json.put("userId", "3");
	//System.out.println(timesheet.sheetList(json));
	timesheet.setNextWeekSheet("3");
}
}
