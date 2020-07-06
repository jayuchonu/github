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

import com.timesheet.dao.CostcenterDao;
import com.timesheet.dao.UserDaoImpl;

@RestController
public class CostCenterController {
	@RequestMapping(value = "/costcenter", method = RequestMethod.GET)
	public ResponseEntity<String> listactivity(@RequestHeader HttpHeaders headers) {
		CostcenterDao dao = new CostcenterDao();
		JSONObject jsonObject = new JSONObject();
		String header = headers.getFirst("Authorization");
		if (header.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = dao.costList();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/addCostCenter", method = RequestMethod.GET)
	public ResponseEntity<String> addCostCenter(@RequestHeader HttpHeaders headers) {
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
	
	@RequestMapping(value = "/addcost", method = RequestMethod.POST)
	public ResponseEntity<String> inActivity(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		CostcenterDao dao = new CostcenterDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = dao.Insertcost(json);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/getuserlist", method = RequestMethod.POST)
	public ResponseEntity<String> getProList(@RequestHeader HttpHeaders headers) {
		CostcenterDao dao = new CostcenterDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = dao.userlist();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/updateCost", method = RequestMethod.POST)
	public ResponseEntity<String> updateActivity(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		CostcenterDao dao = new CostcenterDao();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String header = headers.getFirst("Authorization");
		if (header.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = dao.updatecost(json);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	

	@RequestMapping(value = "/getCost", method = RequestMethod.POST)
	public ResponseEntity<String> getActivity(@RequestHeader HttpHeaders headers,@RequestBody String request){
		CostcenterDao dao = new CostcenterDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject(request);
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String id = json.getString("id");
			jsonObject = dao.NameById(id);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/deletecost", method = RequestMethod.POST)
	public ResponseEntity<String> deleteactivity(@RequestHeader HttpHeaders headers,@RequestBody String request){
		CostcenterDao dao = new CostcenterDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String id = json.getString("id");
			jsonObject = dao.deletecost(id);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

}
