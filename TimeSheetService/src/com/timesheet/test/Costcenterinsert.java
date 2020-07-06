package com.timesheet.test;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.timesheet.dao.CostcenterDao;
class Costcenterinsert {
	@Test
		void test() {
		CostcenterDao cost=new CostcenterDao();
		JSONObject json=new JSONObject();
		json.put("name","celins");
		json.put("description","descrption");
		json.put("orderdetails","not yet");
		json.put("ownerId","1");
		json.put("datecreated","2020-02-20");
		json.put("datemodified","2020-02-21");
		json.put("updatedby","1");
	    cost.Insertcost(json);
	}
}