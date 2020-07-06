package com.timesheet.test;

import org.junit.jupiter.api.Test;

import com.timesheet.dao.ProjectDao;

class ProjectListTest {

	@Test
	void test() {
		 {
			 ProjectDao proDao = new ProjectDao();
			// List<ProjectDto>proList=proDao.projectList();
			 System.out.println("list"+proDao.projectList());
//			 for (int i=0;i<proList.size();i++) {
//				    System.out.println(proList.get(i).getId());
//					System.out.println(proList.get(i).getCreatedby());
//					System.out.println(proList.get(i).getProjectname());
//					System.out.println(proList.get(i).getStartdate());
//					System.out.println(proList.get(i).getEnddate());
//			 }
			}
	}

}
