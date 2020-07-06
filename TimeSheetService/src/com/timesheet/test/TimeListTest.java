package com.timesheet.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timesheet.dao.TimeSheetDao;

class TimeListTest {
	@Test
	void test() {
	 TimeSheetDao sheetDao = new TimeSheetDao();
//	 SendMail mail = new SendMail();
//	 JSONObject json = new JSONObject();
//	 json.put("userId", "3");
//	 json.put("role", "supervisor");
//	 json.put("startdate", "01/05/2021");
//	 json.put("enddate", "01/05/2021");
//	 JSONObject jsonobj=sheetDao.sheetsList(json);
//	 System.out.println("jsonobj"+jsonobj);
//	 //JSONObject jsonobj=sheetDao.fetchtimes("1");
//	 JSONArray jsonarr = jsonobj.getJSONArray("sheetlist");
//	 for(int i=0;i<jsonarr.length();i++) {
//		 JSONObject js =jsonarr.getJSONObject(i);
//		 System.out.println(js.getString("activityid"));
//		 System.out.println(js.getString("comments"));
//		}
	 JSONObject json = new JSONObject();
	 json.put("startdate", "05/05/2020");
	 json.put("userId", "3");
	 System.out.println(sheetDao.sheetList(json));
	// sheetDao.setNextWeekSheet();
//	 json.put("email", "anandhasel@gmail.com");
//	 json.put("reportingpersonEmail", "anandhaselv@gmail.com");
//	 json.put("submitType", "submit");
//	 json.put("firstname", "Anandhaselvi");
//	 json.put("lastname", "Natarajan");
//
//	 System.out.println(mail.sendmail(json));
	}
}



