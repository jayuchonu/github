package com.timesheet.test;

import org.junit.jupiter.api.Test;

import com.timesheet.dao.ActivityDao;

class ActivityDeleteTest {

	@Test
	void test() {

		ActivityDao act =new ActivityDao();
		act.deleteActivity("11");
	}

}
