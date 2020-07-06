package com.timesheet.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timesheet.dao.UserDaoImpl;

class validateToken {

	@Test
	void test() {
		UserDaoImpl dao = new UserDaoImpl();
		JSONObject json = new JSONObject();
		json.put("username", "supervisor");
		json.put("password", "123456");
		System.out.println(dao.login(json));
		dao.validateToken("supervisor");
		System.out.println(dao.CheckRole(json));
	}

}
