package com.timesheet.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.timesheet.model.TimeSheetModel;

/**
 * @author Kiruba
 *
 */
@Controller
public class TimeSheetController {
	private static Logger logger = Logger.getLogger(TimeSheetController.class);
	@RequestMapping(value = "/timesheets", method = RequestMethod.GET)
	public ModelAndView sheetList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("userId", request.getSession().getAttribute("userid") != null?request.getSession().getAttribute("userid").toString():"");
		header.set("role", request.getSession().getAttribute("role") != null ?  request.getSession().getAttribute("role").toString() : "");
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/timesheets", HttpMethod.GET,
					requestEntity, String.class);
			String sheetlist = response.getBody();
			JSONObject json = new JSONObject(sheetlist);
			JSONArray jsonArr = json.getJSONArray("sheetlist");
			List<TimeSheetModel> sheet = new ArrayList<TimeSheetModel>();
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				TimeSheetModel sheetsModel = new TimeSheetModel();
				sheetsModel.setId(jsonObj.getString("id"));
				sheetsModel.setActivityid(jsonObj.getString("activityid"));
				sheetsModel.setActivity(jsonObj.getString("activity"));
				sheetsModel.setProjectname(jsonObj.getString("projectname"));
				sheetsModel.setComments(jsonObj.getString("comments"));
				sheetsModel.setUpdatedby(jsonObj.getInt("updatedby"));
				sheetsModel.setTotalhours(jsonObj.getString("totalhours"));
				sheetsModel.setSubmitteddate(jsonObj.getString("submitteddate"));
				sheet.add(sheetsModel);
			}
			return new ModelAndView("timesheets", "sheets", sheet);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401 || ex.getStatusCode().value() == 500) {
				return new ModelAndView("redirect:/login");
			}

		}
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/addTimeSheet", method = RequestMethod.GET)
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
		restTemplate.exchange(Utilities.readProperties() + "/addTimeSheet", HttpMethod.GET,
					requestEntity, String.class);
			return "addTimeSheet";
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return "redirect:/login";
				}
			}
		return "";
		
	}

	@RequestMapping(value = "/addtimes", method = RequestMethod.GET)
	public String addtimes(HttpServletRequest request, HttpServletResponse response) {
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
		restTemplate.exchange(Utilities.readProperties() + "/addtimes", HttpMethod.GET,
					requestEntity, String.class);
			return "addtimes";
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return "redirect:/login";
				}
			}
		return "";
		
	}



	@RequestMapping(value = "/sheetList", method = RequestMethod.POST)
	@ResponseBody
	public String timesheetList(@RequestParam("startdate")String startdate,@RequestParam("userId")String userId,
			HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		header.set("userId", request.getSession().getAttribute("userid") != null?request.getSession().getAttribute("userid").toString():"");
		JSONObject dateJson = new JSONObject();
		dateJson.put("startdate",startdate);
		dateJson.put("userId", userId);
		HttpEntity<String> requestEntity = new HttpEntity<>(dateJson.toString(), header);
		try {

			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/sheetList", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401) {
				return "redirect:/login";
			}
		}
		return json.toString();
	}

	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String viewdashboard(HttpServletRequest request) {
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
		restTemplate.exchange(Utilities.readProperties() + "/dashboard", HttpMethod.GET,
					requestEntity, String.class);
			return "dashboard";
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return "redirect:/login";
				}
			}
		return "";
	}
	
	@RequestMapping(value = "/addtimes", method = RequestMethod.POST)
	@ResponseBody
	public String addtimes(@RequestParam("userId") String userId, @RequestParam("startdate") String startdate,
			@RequestParam("enddate") String enddate, @RequestParam("statusid") String statusid, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject sheetJson = new JSONObject();
		sheetJson.put("userId", userId);
		sheetJson.put("startdate", startdate);
		sheetJson.put("enddate", enddate);
		sheetJson.put("statusid", statusid);
		sheetJson.put("role", request.getSession().getAttribute("role"));

		HttpEntity<String> requestEntity = new HttpEntity<>(sheetJson.toString(), header);
		try {

			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/addtimes", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}
   
	
	@RequestMapping(value = "/addtime", method = RequestMethod.POST)
	@ResponseBody
	public String addTimeSheet(@RequestParam("totalhours") String totalhours, @RequestParam("comments") String comments,
			@RequestParam("activityid") String activityid, @RequestParam("submitteddate") String submitteddate,
			@RequestParam("projectid") String projectid, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		String userId = request.getSession().getAttribute("userid") != null ? request.getSession().getAttribute("userid").toString() : "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username", username);
		JSONObject timeJson = new JSONObject();
		timeJson.put("totalhours", totalhours);
		timeJson.put("comments", comments);
		timeJson.put("activityid", activityid);
		timeJson.put("submitteddate", submitteddate);
		timeJson.put("projectid", projectid);
		timeJson.put("updatedby", userId);
		HttpEntity<String> requestEntity = new HttpEntity<>(timeJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/addtime", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException  | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}
	

	@RequestMapping(value = "/activitylist", method = RequestMethod.POST)
	@ResponseBody
	public String getProList(HttpServletRequest request) {
		// TimeSheetDao tsDao = new TimeSheetDao();
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
						? request.getSession().getAttribute("username").toString()
						: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/activitylist", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();

	}
	@RequestMapping(value = "/timestatus", method = RequestMethod.POST)
	@ResponseBody
	public String statusList(HttpServletRequest request) {
		// TimeSheetDao tsDao = new TimeSheetDao();
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
						? request.getSession().getAttribute("username").toString()
						: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/timestatus", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();

	}
	

	@RequestMapping(value = "/sheetupdate", method = RequestMethod.POST)
	@ResponseBody
	public String updatetimesheet(@RequestParam("id") int id, @RequestParam("activityid") String activityid,
			@RequestParam("comments") String comments, @RequestParam("projectid") String projectid,
		    		    @RequestParam("totalhours") String totalhours,HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject updateJson = new JSONObject();
		updateJson.put("id", Integer.toString(id));
		updateJson.put("activityid", activityid);
		updateJson.put("comments", comments);
		updateJson.put("projectid", projectid);
		updateJson.put("totalhours", totalhours);
		HttpEntity<String> requestEntity = new HttpEntity<>(updateJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/sheetupdate", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);

		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401) {
				return "redirect:/login";
			}
		}
		return json.toString();
	}

	@RequestMapping(value = "/sendmail",method = RequestMethod.POST)
	@ResponseBody
	public String send(@RequestParam("userid") String userid, @RequestParam("submitType") String submitType, @RequestParam("ids")String ids,HttpServletRequest request) {
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
		header.set("ids",ids.toString());
		JSONObject Json = new JSONObject();
		Json.put("userid", userid);
		Json.put("submitType", submitType);
		Json.put("ids", ids);
		HttpEntity<String> requestEntity = new HttpEntity<>(Json.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/sendmail", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		}  catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return "";
	}

	@RequestMapping(value = "/updatelist", method = RequestMethod.POST)
	@ResponseBody
	public String getsheetsList(@RequestParam("id") String id, HttpServletRequest request) {
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
		JSONObject Json = new JSONObject();
		Json.put("id", id);
		HttpEntity<String> requestEntity = new HttpEntity<>(Json.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/updatelists", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}

	@RequestMapping(value = "/deletetimesheets.htm", method = RequestMethod.POST)
	@ResponseBody
	public String deletetimesheet(@RequestParam("id") String id, HttpServletRequest request) {
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
		JSONObject deleteJson = new JSONObject();
		deleteJson.put("id", id);
		HttpEntity<String> requestEntity = new HttpEntity<>(deleteJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/deletetimesheets",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/setNextWeek", method = RequestMethod.POST)
	@ResponseBody
	public String setNextWeek(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		String userId = request.getSession().getAttribute("userid") != null
						? request.getSession().getAttribute("userid").toString()
						: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username", username);
		header.set("userId",userId);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/setNextWeek", HttpMethod.POST, requestEntity,
					String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}
	@RequestMapping(value = "/getuserList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getusers(@RequestParam("userid") String userid ,HttpServletRequest request) {
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
		JSONObject userjson=new JSONObject();
		userjson.put("userid", userid);
		HttpEntity<String> requestEntity = new HttpEntity<>(userjson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getuserList", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		}  catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return "";
	}

}
