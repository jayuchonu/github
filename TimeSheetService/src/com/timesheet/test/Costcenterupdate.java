package com.timesheet.test;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.timesheet.dao.CostcenterDao;
class Costcenterupdate {
	@Test
	void test() {
		CostcenterDao  cost =new CostcenterDao();
		JSONObject json = new JSONObject();
		json.put("name","jenifer");
		json.put("description","cost");
		json.put("orderdetails","not yet");
		json.put("ownerId","1");
		json.put("datecreated","2020-02-20");
		json.put("datemodified","2020-02-21");
		json.put("id","4");
		cost.updatecost(json);
		}
}