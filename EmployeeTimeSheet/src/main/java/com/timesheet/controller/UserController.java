package com.timesheet.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import com.timesheet.model.UserModel;

@Controller
public class UserController {

	private static Logger logger = Logger.getLogger(UserController.class);
	
	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public ModelAndView userList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null ?request.getSession().getAttribute("token").toString():"";
		String username = request.getSession().getAttribute("username") != null ?request.getSession().getAttribute("username").toString():"";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/employees", HttpMethod.GET,
					requestEntity, String.class);
			String userList = response.getBody();
			JSONObject json = new JSONObject(userList);
			JSONArray jsonArr = json.getJSONArray("userList");
			List<UserModel> users = new ArrayList<UserModel>();
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				UserModel userModel = new UserModel();
				userModel.setUserid(jsonObj.getInt("userid"));
				userModel.setFirstname(jsonObj.getString("firstname"));
				userModel.setLastname(jsonObj.getString("lastname"));
				userModel.setEmail(jsonObj.getString("email"));
				userModel.setRole(jsonObj.getString("Role"));
				userModel.setReportingperson(jsonObj.has("reportingpersonName")?jsonObj.getString("reportingpersonName"):"");
				users.add(userModel);
			}
			return new ModelAndView("employees", "users", users);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401 || ex.getStatusCode().value() == 500) {
				return new ModelAndView("redirect:/login");
			}
		}
		return new ModelAndView();

	}

	@RequestMapping(value = "/rolelist.htm", method = RequestMethod.POST)
	@ResponseBody
	public String rolelist(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null ?request.getSession().getAttribute("username").toString():"";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/rolelist", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		}  catch (HttpClientErrorException  | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return "";

	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
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
		restTemplate.exchange(Utilities.readProperties() + "/addEmployee", HttpMethod.GET,
					requestEntity, String.class);
			return "addEmployee";
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return "redirect:/login";
				}
			}
		return "";
	}
	
	@RequestMapping(value = "/company", method = RequestMethod.GET)
		public String company(HttpServletRequest request) {
		 return "company";
		}
	
	@RequestMapping(value = "/company", method = RequestMethod.POST)
	public ModelAndView addcompany(MultipartRequest multipartRequest,HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView mv  = new ModelAndView();
		try {
			String applicationName = request.getParameter("applicationname");
			MultipartFile multipartFile = multipartRequest.getFile("file");
		    String encodedString = Base64.getEncoder().encodeToString(multipartFile.getBytes());
		    HttpHeaders header = new HttpHeaders();
		    header.add("token", "");
		    JSONObject json = new JSONObject();
		    json.put("applicationName",applicationName);
		    json.put("image", encodedString);
		    HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(),header);
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/company", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			if(json.getString("msg").equalsIgnoreCase("success")) {
				mv.addObject("msg","logo and company name added successfully");
				mv.setViewName("redirect:/login");
			}
			else if(json.getString("msg").equalsIgnoreCase("failed")) {
				mv.addObject("msg", "logo updation failed");
				mv.setViewName("company");
			}
		} catch (IOException ex) {
			logger.error(ex);
			
		}
		return mv;

	}

	@RequestMapping(value = "/addEmployee.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addEmployee(@RequestParam("username") String username, @RequestParam("role") String role,
			@RequestParam("password") String password,
			@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,
			@RequestParam("email") String email, @RequestParam("reportingperson") String reportingperson,
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
		String currentuser = request.getSession().getAttribute("username") != null ?
				request.getSession().getAttribute("username").toString():"";

		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",currentuser);
		JSONObject userJson = new JSONObject();
		userJson.put("username", username);
		userJson.put("roleId", role);
		userJson.put("password", password);
		userJson.put("firstname",  firstname);
		userJson.put("lastname",  lastname);
		userJson.put("email",  email);
		userJson.put("reportingperson",  reportingperson);
		userJson.put("updatedby", userId);
		HttpEntity<String> requestEntity = new HttpEntity<>(userJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/addEmployee", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}

	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestParam("userId") String userId, @RequestParam("role") String role,
			@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,
			@RequestParam("email") String email, @RequestParam("reportingperson") String reportingperson,
			HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null ?
				request.getSession().getAttribute("username").toString():"";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject userJson = new JSONObject();
		userJson.put("userId", userId);
		userJson.put("roleId", role);
		userJson.put("firstname", firstname);
		userJson.put("lastname", lastname);
		userJson.put("email", email);
		userJson.put("reportingperson", reportingperson);
		HttpEntity<String> requestEntity = new HttpEntity<>(userJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/update", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);

		}  catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}

	@RequestMapping(value = "/deleteuser.htm", method = RequestMethod.POST)
	@ResponseBody
	public String deleteuser(@RequestParam("userid") String userid, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null ?
				request.getSession().getAttribute("username").toString():"";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject userJson = new JSONObject();
		userJson.put("userid", userid);
		HttpEntity<String> requestEntity = new HttpEntity<>(userJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/deleteuser", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}	

	@RequestMapping(value = "/getUser.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getUserbyid(@RequestParam("id") String id, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null ?
				request.getSession().getAttribute("username").toString():"";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject activityJson = new JSONObject();
		activityJson.put("id", id);
		HttpEntity<String> requestEntity = new HttpEntity<>(activityJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getUser", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	logger.error(ex.getResponseBodyAsString());
			}
		return json.toString();

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getlogin(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		try {
			HttpEntity<String> requestEntity = new HttpEntity<>("", header);
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/login",
					HttpMethod.GET, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			if (json.has("applicationName")) {
				mv.addObject("applicationName", json.getString("applicationName"));
				mv.setViewName("login");
			}
			else if(json.has("msg"))
			{
				mv.setViewName("redirect:/company");
			}
		}  catch (HttpClientErrorException | HttpServerErrorException ex) { 
				logger.error(ex);
			}
		return mv;
	}
	
	@RequestMapping(value = "/fetchlogo", method = RequestMethod.GET)
	@ResponseBody
	public String fetchlogo(HttpServletRequest request) {
			RestTemplate restTemplate = new RestTemplate();
			JSONObject json = new JSONObject();
			HttpHeaders header = new HttpHeaders();
			try {
			HttpEntity<String> requestEntity = new HttpEntity<>("", header);
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/fetchlogo", HttpMethod.GET,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			}
			 catch (HttpClientErrorException | HttpServerErrorException ex) {
				 	logger.error(ex.getResponseBodyAsString());
			}
			return json.toString();
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView login(HttpServletRequest request) throws SQLException {
		ModelAndView mv = new ModelAndView();
		JSONObject userJson = new JSONObject();
		userJson.put("username", request.getParameter("username"));
		userJson.put("password", request.getParameter("password"));
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		HttpEntity<String> requestEntity = new HttpEntity<>(userJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/login", HttpMethod.POST,
					requestEntity, String.class);
			String userList = response.getBody();
			JSONObject json = new JSONObject(userList);
			HttpSession session=request.getSession(true);
			session.setAttribute("userid", json.has("userid") ? json.getString("userid"): "");
			session.setAttribute("role",json.has("role") ? json.getString("role") : "");
			session.setAttribute("username",json.has("username") ? json.getString("username") : "");
			session.setAttribute("token", json.has("token") ?json.getString("token") :"");
			session.setMaxInactiveInterval(900);
			if (json.has("msg")) {
				mv.addObject("msg", json.getString("msg"));
				mv.setViewName("login");
			}
			else if (json.getString("role").equalsIgnoreCase("Supervisor")) {
				mv.setViewName("redirect:/dashboard");
			} else if (json.getString("role").equalsIgnoreCase("Employee")) {
				mv.setViewName("redirect:/dashboard");
			}
			else if (json.getString("role").equalsIgnoreCase("Admin")) {
				mv.setViewName("redirect:/employees");
			}
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401) {
				mv.setViewName("login");
			}else if(ex.getStatusCode().value() == 500) {
				mv.setViewName("login");
			}
		}
		return mv;
	}
	
	@RequestMapping(value = "/passwordchange", method = RequestMethod.GET)
	public String passwordchange(HttpServletRequest request) {
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
		restTemplate.exchange(Utilities.readProperties() + "/passwordchange", HttpMethod.GET,
					requestEntity, String.class);
			return "passwordchange";
		}
		 catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return "redirect:/login";
				}
			}
		return "";
	}
	@RequestMapping(value = "/passwordchange", method = RequestMethod.POST)
	public ModelAndView change(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView mv = new ModelAndView();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null ?
				request.getSession().getAttribute("username").toString():"";
		String userid = request.getSession().getAttribute("userid") != null ?
						request.getSession().getAttribute("userid").toString():"";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
	
		JSONObject userJson = new JSONObject();
		userJson.put("oldpassword", request.getParameter("oldpassword"));
		userJson.put("password", request.getParameter("newpassword"));
		userJson.put("userid", userid);
		HttpEntity<String> requestEntity = new HttpEntity<>(userJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/passwordchange", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			mv.addObject("msg", json.getString("msg"));

		}  catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401) {
				mv.setViewName("redirect:/login");
			}
//			if (!newpassword.equals(confirmpassword)) {
//				mv.addObject("msg", "new password and confirm password should be same");
//
//			} else {
//				if (counter > 0) {
//					mv.addObject("msg", "Your password has been updated sucessfully");
//
//				} else {
//					mv.addObject("msg", "failed");
//
//				}
//			}
//			System.out.println("pword" + request.getParameter("password"));
//			
//			}
		}
		return mv;
	}
}
