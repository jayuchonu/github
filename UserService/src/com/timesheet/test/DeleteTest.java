package com.timesheet.test;


import org.junit.jupiter.api.Test;

import com.timesheet.dao.UserDaoImpl;

class DeleteTest {

	@Test
	void test() {
	UserDaoImpl userDao=new UserDaoImpl();
		//userDao.deleteuser("8");
	//System.out.println(userDao.fetchCompanyInfo());
	System.out.println(userDao.deleteuser("12"));
	}

}
