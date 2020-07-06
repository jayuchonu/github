package com.timesheet.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timesheet.dao.ProjectDao;

class ProjectTest {

	
		@Test
		void test() {
		ProjectDao  times =new ProjectDao();
		JSONObject project = new JSONObject();
		project.put("projectName","project5");
		project.put("startdate","02/20/2020");
		project.put("enddate","02/20/2020");
		project.put("createdby", "2");
		project.put("userId", "2");
		System.out.println(times.Insertproject(project));
		//System.out.println(times.updateproject(project));
	}
}