package com.timesheet.test;
import org.junit.jupiter.api.Test;
import com.timesheet.dao.CostcenterDao;
class Costcenterdelete {
	@Test
	void test() {
		CostcenterDao adm=new CostcenterDao();
		adm.deletecost("4");
	}
}