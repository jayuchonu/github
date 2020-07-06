package com.timesheet.test;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timesheet.dao.UserDaoImpl;

class UserTest {

	@Test
	void test() throws IOException {
	UserDaoImpl user=new UserDaoImpl();
	JSONObject users =new JSONObject();
	users.put("firstname","selvi");
	users.put("lastname","natarajan");
	users.put("username","selvi244");
	users.put("password","selvi123");
	users.put("email","anandhaselvi@gmail.com");
	users.put("reportingperson","1");
	users.put("roleId","1");
	users.put("updatedby","1");
	//System.out.println(user.insertUser(users));
	//System.out.println(user.updateUser(users));
	//user.isexistuser(users);
	System.out.println(user.userList());
	}
} 
