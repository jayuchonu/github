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

import com.timesheet.dao.SendMail;
import com.timesheet.dao.TimeSheetDao;
import com.timesheet.dao.UserDaoImpl;

/**
 * 
 * @author Kiruba
 * 
 */
@RestController
public class TimeSheetServiceController {

	@RequestMapping(value = "/timesheets", method = RequestMethod.GET)
	public ResponseEntity<String> listtime(@RequestHeader HttpHeaders headers) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject sheetJson = new JSONObject();
		sheetJson.put("userId", headers.getFirst("userId"));
		sheetJson.put("role", headers.getFirst("role"));
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = timeDao.sheetsList(sheetJson);;
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/addTimeSheet", method = RequestMethod.GET)
	public ResponseEntity<String> addTimeSheet(@RequestHeader HttpHeaders headers) {
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
	
	@RequestMapping(value = "/addtimes", method = RequestMethod.GET)
	public ResponseEntity<String> addtimes(@RequestHeader HttpHeaders headers) {
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
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ResponseEntity<String> dashboard(@RequestHeader HttpHeaders headers) {
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
	
	
	@RequestMapping(value = "/addtime", method = RequestMethod.POST)
	public ResponseEntity<String> sheettime(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject(request);
		JSONObject json = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = timeDao.insertTimesheet(jsonObject);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sheetList", method = RequestMethod.POST)
	public ResponseEntity<String> sheetList(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = timeDao.sheetList(jsonObject);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/addtimes", method = RequestMethod.POST)
	public ResponseEntity<String> getProList(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject(request);
		JSONObject json = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = timeDao.sheetsList(jsonObject);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/activitylist", method = RequestMethod.POST)
	public ResponseEntity<String> ProList(@RequestHeader HttpHeaders headers) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = timeDao.activityList();
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/timestatus", method = RequestMethod.POST)
	public ResponseEntity<String> statusList(@RequestHeader HttpHeaders headers) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = timeDao.TimesheetStatus();
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/sheetupdate", method = RequestMethod.POST)
	public ResponseEntity<String> update(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = timeDao.updatetime(jsonObject);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sendmail", method = RequestMethod.POST)
	public ResponseEntity<String> mail(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		SendMail mail = new SendMail();
		UserDaoImpl userdao = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject jsonObj = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!userdao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JSONObject emailJson = userdao.userById(jsonObj.getString("userid"));
			emailJson.put("submitType", jsonObj.getString("submitType"));
			//emailJson.put("ids", jsonObj.getString("ids"));
			json = mail.sendmail(emailJson);
			TimeSheetDao timedao= new TimeSheetDao();
			timedao.getId(jsonObj.getString("ids").replaceAll(",$","").split(","), jsonObj.getString("submitType"));
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/deletetimesheets", method = RequestMethod.POST)
	public ResponseEntity<String> deleteactivity(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String id = json.getString("id");
			jsonObject = timeDao.deletetimesheet(id);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/updatelists", method = RequestMethod.POST)
	public ResponseEntity<String> updatelists(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String id = json.getString("id");
			jsonObject = timeDao.fetchtimes(id);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/setNextWeek", method = RequestMethod.POST)
	public ResponseEntity<String> setNextWeekSheet(@RequestHeader HttpHeaders headers) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		String userId = headers.getFirst("userId");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			 json = timeDao.setNextWeekSheet(userId);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	

	@RequestMapping(value = "/getuserList", method = RequestMethod.POST)
	public ResponseEntity<String> usersdrop(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		TimeSheetDao timeDao = new TimeSheetDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject jsonobj = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
	
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String userid=jsonobj.getString("userid");
			json = timeDao.usersdrop(userid);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}

}
