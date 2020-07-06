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

import com.timesheet.dao.ProjectDao;
import com.timesheet.dao.UserDaoImpl;

/**
 * @author Sathya
 *
 */
@RestController
public class ProjectServiceController {
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public ResponseEntity<String> listprojects(@RequestHeader HttpHeaders headers) {
		ProjectDao pro = new ProjectDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = pro.projectList();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/addProject", method = RequestMethod.GET)
	public ResponseEntity<String> addProject(@RequestHeader HttpHeaders headers) {
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
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	public ResponseEntity<String> addProjectDetails(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		ProjectDao pro = new ProjectDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonobj = new JSONObject(request);
		JSONObject json = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = pro.Insertproject(jsonobj);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public ResponseEntity<String> userList(@RequestHeader HttpHeaders headers) {
		ProjectDao pro = new ProjectDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = pro.userList();
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)

	public ResponseEntity<String> updateProjectDetails(@RequestHeader HttpHeaders headers,
			@RequestBody String request) {
		ProjectDao pro = new ProjectDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonobj = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonobj = pro.updateproject(json);
			if (jsonobj.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonobj.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/fetchProject", method = RequestMethod.POST)

	public ResponseEntity<String> fetchproject(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		ProjectDao pro = new ProjectDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject json = new JSONObject(request);
		JSONObject jsonobj = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String projectId = json.getString("projectId");
			jsonobj = pro.proList(projectId);
			if (jsonobj.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonobj.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	public ResponseEntity<String> deleteproject(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		ProjectDao pro = new ProjectDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonobj = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String id = json.getString("id");
			jsonobj = pro.deleteproject(id);
			if (jsonobj.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonobj.toString(), HttpStatus.OK);
		}
	}

}
