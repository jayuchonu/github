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

import com.timesheet.dao.ActivityDao;
import com.timesheet.dao.UserDaoImpl;

/**
 * @author Sathya
 *
 */
@RestController
public class ActivityServiceController {
	@RequestMapping(value = "/activities", method = RequestMethod.GET)
	public ResponseEntity<String> listactivity(@RequestHeader HttpHeaders headers) {
		ActivityDao act = new ActivityDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = act.activityList();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/addActivity", method = RequestMethod.GET)
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
	@RequestMapping(value = "/actinsert", method = RequestMethod.POST)
	public ResponseEntity<String> inActivity(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		ActivityDao act = new ActivityDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = act.actInsert(json);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/getProject", method = RequestMethod.POST)
	public ResponseEntity<String> getProList(@RequestHeader HttpHeaders headers) {
		ActivityDao act = new ActivityDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = act.getProjectList();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/actupdate", method = RequestMethod.POST)
	public ResponseEntity<String> updateActivity(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		ActivityDao act = new ActivityDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = act.actUpdate(json);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	

	@RequestMapping(value = "/getActivity", method = RequestMethod.POST)
	public ResponseEntity<String> getActivity(@RequestHeader HttpHeaders headers,@RequestBody String request){
		ActivityDao act = new ActivityDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject(request);
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String id = json.getString("id");
			jsonObject = act.fetchActivity(id);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "deleteactivity", method = RequestMethod.POST)
	public ResponseEntity<String> deleteactivity(@RequestHeader HttpHeaders headers,@RequestBody String request){
		ActivityDao act = new ActivityDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String id = json.getString("id");
			jsonObject = act.deleteActivity(id);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

}
