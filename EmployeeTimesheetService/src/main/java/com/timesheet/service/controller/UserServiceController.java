package com.timesheet.service.controller;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.timesheet.dao.UserDaoImpl;

@RestController
public class UserServiceController {
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody String request) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		int counter = userDao.login(json);
		if (counter == 1) {
			userDao.validateToken(json.getString("username"));
			jsonObject = userDao.CheckRole(json);
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
		else if(counter == 2) {
			jsonObject.put("msg","Incorrect username and password");
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
			}
		else {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<String> getlogin(@RequestHeader HttpHeaders headers) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		jsonObject = userDao.fetchCompanyInfo();
		if (jsonObject.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);

	}
	@RequestMapping(value = "/passwordchange", method = RequestMethod.GET)
	public ResponseEntity<String> addActivity(@RequestHeader HttpHeaders headers) {
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
	public ResponseEntity<String> addEmployee(@RequestHeader HttpHeaders headers) {
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public ResponseEntity<String> addEmployee(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!userDao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = userDao.insertUser(jsonObject);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/company", method = RequestMethod.POST)
	public ResponseEntity<String> company(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = new JSONObject(request);
		json = userDao.addCompanyInfo(jsonObject);
		if (json.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> updateEmployee(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!userDao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = userDao.updateUser(jsonObject);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	public ResponseEntity<String> deleteuser(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		UserDaoImpl userdao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!userdao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String id = json.getString("userid");
			jsonObject = userdao.deleteuser(id);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
	public ResponseEntity<String> getUser(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		UserDaoImpl userdao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!userdao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String id = json.getString("id");
			jsonObject = userdao.userById(id);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public ResponseEntity<String> listUsers(@RequestHeader HttpHeaders headers) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token =headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!userDao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		else {
			jsonObject = userDao.userList();
			if(jsonObject.isEmpty()){
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rolelist", method = RequestMethod.POST)
	public ResponseEntity<String> rolelist(@RequestHeader HttpHeaders headers) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token =headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!userDao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		else {
			jsonObject = userDao.getRoleList();
			if(jsonObject.isEmpty()){
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/fetchlogo", method = RequestMethod.GET)
	public ResponseEntity<String> fetchlogo(@RequestHeader HttpHeaders headers) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
			jsonObject = userDao.fetchCompanyInfo();
			if(jsonObject.isEmpty()){
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	@RequestMapping(value = "/passwordchange", method = RequestMethod.POST)
	public ResponseEntity<String> Change (@RequestHeader HttpHeaders headers,@RequestBody String request) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		jsonObject = userDao.passwordchange(json);
		if(!userDao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
}