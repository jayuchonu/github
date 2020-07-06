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

import com.timesheet.model.CostCenterModel;

@Controller
public class CostCenterController {
private static Logger logger = Logger.getLogger(CostCenterController.class);

	@RequestMapping(value = "/costcenter", method = RequestMethod.GET)
	public ModelAndView costList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
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
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/costcenter", HttpMethod.GET,
					requestEntity, String.class);
			String costList = response.getBody();
			JSONObject json = new JSONObject(costList);
			JSONArray jsonArr = json.getJSONArray("costcenterList");
			List<CostCenterModel> list = new ArrayList<CostCenterModel>();
			for (int i = 0; i < jsonArr.length(); i++) {
				CostCenterModel costcentermodel = new CostCenterModel();
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				costcentermodel.setId(jsonObj.getInt("id"));
				costcentermodel.setName(jsonObj.getString("name"));
				costcentermodel.setDescription(jsonObj.getString("description"));
				costcentermodel.setOrderdetails(jsonObj.getString("orderdetails"));
				costcentermodel.setOwnername(jsonObj.getString("ownername"));
				costcentermodel.setDatecreated(jsonObj.getString("datecreated"));
				costcentermodel.setDatemodified(jsonObj.has("datemodified")?jsonObj.getString("datemodified"):"");
				list.add(costcentermodel);
			}
			return new ModelAndView("costcenter", "list", list);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return new ModelAndView();
	}

	
	@RequestMapping(value = "/addCostCenter", method = RequestMethod.GET)
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
		restTemplate.exchange(Utilities.readProperties() + "/addCostCenter", HttpMethod.GET,
					requestEntity, String.class);
			return "addCostCenter";
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return "redirect:/login";
				}
			}
		return "";
		
	}

	@RequestMapping(value = "/addcost.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addCostCenter(@RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("orderdetails") String orderdetails, @RequestParam("ownerId") String ownerId,
			HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String userId = request.getSession().getAttribute("userid") != null ? request.getSession().getAttribute("userid").toString() : "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject activityJson = new JSONObject();
		activityJson.put("name", name);
		activityJson.put("description", description);
		activityJson.put("orderdetails", orderdetails);
		activityJson.put("ownerId", ownerId);
		activityJson.put("updatedby", userId);
		HttpEntity<String> requestEntity = new HttpEntity<>(activityJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/addcost", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();

	}

	@RequestMapping(value = "/getuserlist.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getuserlist(HttpServletRequest request) {
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
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getuserlist", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		}  catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return "";
	}

	@RequestMapping(value = "/updateCost.htm", method = RequestMethod.POST)
	@ResponseBody
	public String updatecostRegister(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("orderdetails") String orderdetails,
			@RequestParam("ownerId") String ownerId, HttpServletRequest request) {
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
		activityJson.put("name", name);
		activityJson.put("description", description);
		activityJson.put("orderdetails", orderdetails);
		activityJson.put("ownerId", ownerId);
		HttpEntity<String> requestEntity = new HttpEntity<>(activityJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/updateCost", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);

		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();

	}

	@RequestMapping(value = "/getCost.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getNameById(@RequestParam("id") String id, HttpServletRequest request) {
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
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getCost", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}

	@RequestMapping(value = "/deletecost.htm", method = RequestMethod.POST)
	@ResponseBody
	public String deletecost(@RequestParam("id") String id, HttpServletRequest request) {
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
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/deletecost",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}

}
