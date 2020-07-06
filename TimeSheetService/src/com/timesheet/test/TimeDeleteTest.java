package com.timesheet.test;

import org.junit.jupiter.api.Test;

import com.timesheet.dao.TimeSheetDao;

class TimeDeleteTest {

	@Test
	void test() {
		TimeSheetDao sheetDao=new TimeSheetDao();
		sheetDao.deletetimesheet("26");
	}

}
