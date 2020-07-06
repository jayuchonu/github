package com.timesheet.test;

import org.junit.jupiter.api.Test;

import com.timesheet.dao.ProjectDao;

class ProjectDeleteTest {

	@Test
	void test() {
		ProjectDao proDao = new ProjectDao();
		//proDao.deleteproject("5");
		System.out.println(proDao.getUserProjectMapping("1"));
	}

}
