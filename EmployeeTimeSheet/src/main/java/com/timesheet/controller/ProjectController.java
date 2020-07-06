package com.timesheet.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.timesheet.model.ProjectModel;

@Controller
public class ProjectController {
private static Logger logger = Logger.getLogger(ProjectController.class);
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public ModelAndView ProjectList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null ?request.getSession().getAttribute("token").toString():"";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/projects", HttpMethod.GET,
					requestEntity, String.class);
			String ProjectList = response.getBody();
			JSONObject json = new JSONObject(ProjectList);
			JSONArray jsonArr = json.getJSONArray("projectList");
			List<ProjectModel> pro = new ArrayList<ProjectModel>();

			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				ProjectModel promodel = new ProjectModel();
				promodel.setId(jsonObj.getString("id"));
				promodel.setCreatedby(jsonObj.getString("createdby"));
				promodel.setProjectname(jsonObj.getString("projectname"));
				promodel.setStartdate(jsonObj.getString("startdate"));
				promodel.setEnddate(jsonObj.getString("enddate"));
				promodel.setUserList(jsonObj.getString("userList"));
				pro.add(promodel);
			}
			return new ModelAndView("projects", "projectsList", pro);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401 || ex.getStatusCode().value() == 500) {
				return new ModelAndView("redirect:/login");
			}

	
		return new ModelAndView();
		}
	}

	@RequestMapping(value = "/addProject", method = RequestMethod.GET)
	public String addEmployee(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
		restTemplate.exchange(Utilities.readProperties() + "/addProject", HttpMethod.GET,
					requestEntity, String.class);
			return "addProject";
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return "redirect:/login";
				}
			}
		return "";
		
	}

	

//	@RequestMapping(value = "/addProject", method = RequestMethod.GET)
//	public String addProject(HttpServletRequest request) {
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders header = new HttpHeaders();
//		String token = request.getSession().getAttribute("token") != null
//				? request.getSession().getAttribute("token").toString(): "";
//		String username = request.getSession().getAttribute("username") != null
//						? request.getSession().getAttribute("username").toString(): "";
//		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		header.set("Authorization", token);
//		header.set("username",username);
//		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
//		try {
//			restTemplate.exchange(Utilities.readProperties() + "/addProject", HttpMethod.GET,
//					requestEntity, String.class);
//		}
//		 catch (HttpClientErrorException ex) {
//			if (ex.getStatusCode().value() == 401) {
//					return "redirect:/login";
//			}
//		}
//	 return "addProject";
//	}

	@RequestMapping(value = "/addProject.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addProjectDetails(@RequestParam("projectName") String projectName,
		@RequestParam("startdate") String startdate, @RequestParam("enddate") String enddate,
		@RequestParam("userIds") String userIds,HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		String userId = request.getSession().getAttribute("userid") != null
						? request.getSession().getAttribute("userid").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject projectJson = new JSONObject();
		projectJson.put("projectName", projectName);
		projectJson.put("startdate", startdate);
		projectJson.put("enddate", enddate);
		projectJson.put("createdby", userId);	
		projectJson.put("userId", userIds);
		HttpEntity<String> requestEntity = new HttpEntity<>(projectJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/addProject", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}
		

	@RequestMapping(value = "/fetchProject.htm", method = RequestMethod.POST)
	@ResponseBody
	public String fetchproDetails(@RequestParam("projectId") String projectId,HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject proJson = new JSONObject();
		proJson.put("projectId", projectId);
		HttpEntity<String> requestEntity = new HttpEntity<>(proJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/fetchProject", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		}  catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();

	}

	

	@RequestMapping(value = "/updateProject.htm", method = RequestMethod.POST)
	@ResponseBody
	public String updateProjectDetails(@RequestParam("projectName") String projectName,
			@RequestParam("startdate") String startdate, @RequestParam("enddate") String enddate, @RequestParam("userIds") String userIds,
			@RequestParam("projectId") int projectId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);		
		JSONObject proJson = new JSONObject();
		proJson.put("projectName", projectName);
		proJson.put("startdate", startdate);
		proJson.put("enddate", enddate);
		proJson.put("userId",userIds);
		proJson.put("projectId", Integer.toString(projectId));
		HttpEntity<String> requestEntity = new HttpEntity<>(proJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/updateProject", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);

		}  catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();

	}

	@RequestMapping(value = "/userList.htm", method = RequestMethod.GET)
	@ResponseBody
	public String userList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/userList", HttpMethod.GET,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 logger.error(ex.getResponseBodyAsString());
			}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	@ResponseBody
	public String deleteproject (@RequestParam("id")String id, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null ? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject proJson = new JSONObject();
		proJson.put("id", id);
		HttpEntity<String> requestEntity = new HttpEntity<>(proJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/deleteProject", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 logger.error(ex.getResponseBodyAsString());
			}
		return json.toString();
	}
}
