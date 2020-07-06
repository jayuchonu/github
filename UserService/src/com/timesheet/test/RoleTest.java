package com.timesheet.test;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.timesheet.dao.UserDaoImpl;

class RoleTest {

	@Test
	void test() throws SQLException {
		UserDaoImpl user=new UserDaoImpl();
		//UserDto users =new UserDto();
		//users.setRole("employee");
		//user.role(users);
//		users.setusername("selvi");
//		users.setpassword("selvi123");
//		System.out.println(user.login(users));
//		System.out.println(user.CheckRole(users));
		System.out.println(user.fetchCompanyInfo());

	}

}
