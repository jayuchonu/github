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

import com.timesheet.model.ActivityModel;

/**
 * @author Sathya
 *
 */
@Controller
public class ActivityController {
  private static Logger logger = Logger.getLogger(ActivityController.class);
	@RequestMapping(value = "/activities", method = RequestMethod.GET)
	public ModelAndView activityList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username", username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/activities",
					HttpMethod.GET, requestEntity, String.class);
			String activityList = response.getBody();
			JSONObject json = new JSONObject(activityList);
			JSONArray jsonArr = json.getJSONArray("activityList");
			List<ActivityModel> act = new ArrayList<ActivityModel>();
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				ActivityModel activitymodel = new ActivityModel();
				activitymodel.setId(jsonObj.getString("id"));
				activitymodel.setCode(jsonObj.getString("code"));
				activitymodel.setProjectname(jsonObj.getString("projectname"));
				activitymodel.setStartdate(jsonObj.getString("startdate"));
				activitymodel.setEnddate(jsonObj.getString("enddate"));
				activitymodel.setOtherdetails(jsonObj.has("otherdetails")?jsonObj.getString("otherdetails"):"");
				act.add(activitymodel);
			}
			return new ModelAndView("activities", "activity", act);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401 || ex.getStatusCode().value() == 500) {
				return new ModelAndView("redirect:/login");
			}
			return new ModelAndView();
		}
	}


	@RequestMapping(value = "/addActivity", method = RequestMethod.GET)
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
		restTemplate.exchange(Utilities.readProperties() + "/addActivity", HttpMethod.GET,
					requestEntity, String.class);
			return "addActivity";
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return "redirect:/login";
				}
			}
		return "";
		
	}

	@RequestMapping(value = "/actinsert.htm", method = RequestMethod.POST)
	@ResponseBody
	public String inActivity(@RequestParam("code") String code, @RequestParam("projectid") String projectid,
			@RequestParam("startdate") String startdate, @RequestParam("enddate") String enddate,
			HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String userId = request.getSession().getAttribute("userid") != null
				? request.getSession().getAttribute("userid").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject activityJson = new JSONObject();
		activityJson.put("code", code);
		activityJson.put("projectid", projectid);
		activityJson.put("startdate", startdate);
		activityJson.put("enddate", enddate);
		activityJson.put("updatedby", userId);
		HttpEntity<String> requestEntity = new HttpEntity<>(activityJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/actinsert",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}

	@RequestMapping(value = "/getProject.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getProList(HttpServletRequest request) {
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
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getProject",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return "";

	}
 	
	@RequestMapping(value = "/actupdate.htm", method = RequestMethod.POST)
	@ResponseBody
	public String updateActivity(@RequestParam("id") int id, @RequestParam("code") String code,
			@RequestParam("projectid") String projectid, @RequestParam("startdate") String startdate,
			@RequestParam("enddate") String enddate, HttpServletRequest request) {
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
		JSONObject activityJson = new JSONObject();
		activityJson.put("id", Integer.toString(id));
		activityJson.put("code", code);
		activityJson.put("projectid", projectid);
		activityJson.put("startdate", startdate);
		activityJson.put("enddate", enddate);
		HttpEntity<String> requestEntity = new HttpEntity<>(activityJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/actupdate",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();

	}

	@RequestMapping(value = "/getActivity.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getActivity(@RequestParam("id") String id, HttpServletRequest request) {
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
		JSONObject activityJson = new JSONObject();
		activityJson.put("id", id);
		HttpEntity<String> requestEntity = new HttpEntity<>(activityJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getActivity",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}

	@RequestMapping(value = "deleteactivity.htm", method = RequestMethod.POST)
	@ResponseBody
	public String deleteactivity(@RequestParam("id") String id, HttpServletRequest request) {
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
		JSONObject activityJson = new JSONObject();
		activityJson.put("id", id);
		HttpEntity<String> requestEntity = new HttpEntity<>(activityJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/deleteactivity",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}
}
