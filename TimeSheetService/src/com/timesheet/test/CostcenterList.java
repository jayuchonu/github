package com.timesheet.test;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.timesheet.dao.CostcenterDao;
class CostcenterList {
	@Test
	void test() {
			  try {
					 CostcenterDao costdao = new CostcenterDao();
					 JSONObject json=costdao.NameById("4");
					System.out.println(json.getString("Name"));
					 System.out.println(json.getString("Description"));
					 System.out.println(json.getString("Order_details"));
					 System.out.println(json.getInt("OwnerId"));
	        } catch (JSONException e) {
					e.printStackTrace();
				}
			}}