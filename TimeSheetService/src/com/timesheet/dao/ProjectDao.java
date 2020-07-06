package com.timesheet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.timesheet.db.DBConnection;

public class ProjectDao implements ProjectInter {
	private static Logger logger = Logger.getLogger(ProjectDao.class);

	private static DBConnection conn = DBConnection.getInstance();
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public JSONObject Insertproject(JSONObject json) {
		JSONObject jsonObject = new JSONObject();
		int counter=nameExists(json.getString("projectName"));
		if(counter>0) {
			jsonObject.put("msg", "project already exists");
		} else {
			Connection con = null;
			PreparedStatement preparedstmt =null;
			ResultSet rs = null;
			try {
				String sql = "Insert into project(createdby,projectname,startdate,enddate,createdon)values(?,?,?,?,?)";
				con = conn.getConnection();
			    preparedstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedstmt.setString(1, json.getString("createdby"));
				preparedstmt.setString(2, json.getString("projectName"));
				Date startDate = sdf.parse(json.getString("startdate"));
				preparedstmt.setString(3, sdf1.format(startDate));
				Date endDate = sdf.parse(json.getString("enddate"));
				preparedstmt.setString(4, sdf1.format(endDate));
				preparedstmt.setString(5, LocalDateTime.now().toString());
				preparedstmt.executeUpdate();
				rs = preparedstmt.getGeneratedKeys();
				if(rs != null && rs.next()){
				userProjectMapping(json.getString("userId").replaceAll(",$","").split(","),rs.getString(1),con);
				}
				jsonObject.put("msg", "project created successfully");
			} catch (SQLException | ParseException e) {
				logger.error(e);
				jsonObject.put("msg", "project created failed");
			} finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if(rs!=null) {try {rs.close();} catch(SQLException e) {logger.error(e);}}
		}
	}
		return jsonObject;
	}
	
private void userProjectMapping(String[] userids, String projectid, Connection con) {
		PreparedStatement preparedstmt = null;
		try {
			String sql = "DELETE FROM userProjectMapping where projectid=?";
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, projectid);
			preparedstmt.executeUpdate();
			preparedstmt.close();
			for(String id:userids) {
			 String sql1= "INSERT INTO userProjectMapping(userid,projectid)VALUES(?,?)";
			 preparedstmt = con.prepareStatement(sql1);
			 preparedstmt.setString(1, id);
			preparedstmt.setString(2, projectid);
			preparedstmt.executeUpdate();
		
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		
	}


	@Override
	public int nameExists(String projectname) {
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs=null;
		try {
			String sql = "SELECT projectname FROM project where projectname =?";
			int counter = 0;
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, projectname);
			rs = preparedstmt.executeQuery();
			if (rs.next()) {
				counter = 1;
			}
			return counter;
		} catch (SQLException e) {
			logger.error(e);
			return 0;
		}  finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
	}
	
	@Override
	public JSONObject updateproject(JSONObject jsonobj) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt= null;
		try {
			String sql = "update project SET projectname=?,startdate=?,enddate=? where id=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1,  jsonobj.getString("projectName"));
			Date startDate = sdf.parse( jsonobj.getString("startdate"));
			preparedstmt.setString(2, sdf1.format(startDate));
			Date endDate = sdf.parse( jsonobj.getString("enddate"));
			preparedstmt.setString(3, sdf1.format(endDate));
			preparedstmt.setString(4, jsonobj.getString("projectId"));
			preparedstmt.executeUpdate();
			userProjectMapping(jsonobj.getString("userId").replaceAll(",$","").split(","),jsonobj.getString("projectId"),con);
			json.put("msg","project Updated");
			return  json;
			} catch (SQLException | ParseException e) {
				logger.error(e);
				json.put("msg","update failed");
				return json;
			}finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			}
			}
			
		
	@Override
	public JSONObject projectList() {
		JSONObject jsonobj=new JSONObject();
		JSONArray jsonarr = new JSONArray();
		Connection con = null;
		PreparedStatement preparedstmt =null;
		ResultSet rs=null;
		try {
			String sql = "SELECT project.id,projectname,startdate,enddate,CONCAT(firstname,' ',lastname)as createdby from project  " + 
					"INNER JOIN users ON users.userid = project.createdby";
			
			 con = conn.getConnection();
			 preparedstmt = con.prepareStatement(sql);
			 rs = preparedstmt.executeQuery();
			while (rs.next()) {
			//	ProjectDto proj = new ProjectDto();
				JSONObject json = new JSONObject();
				json.put("id",rs.getString("id"));
				json.put("createdby",rs.getString("createdby"));
				json.put("projectname",rs.getString("projectname"));
			    Date startDate = sdf1.parse(rs.getString("Startdate"));
			   json.put("startdate",sdf.format(startDate));
			    Date endDate = sdf1.parse(rs.getString("enddate"));
				json.put("enddate",sdf.format(endDate));
				json.put("userList", userproject(rs.getString("id")).replaceAll(",$", ""));
				jsonarr.put(json);
			}

			jsonobj.put("projectList", jsonarr);
		} catch (SQLException | ParseException e) {
			logger.error(e);
		}  finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonobj;

	}
	@Override
	public JSONObject proList(String id) {
		JSONObject json = new JSONObject();
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs= null;
		try {
			String sql = "SELECT id, createdby,projectname,startdate,enddate from project  where id=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, id);
			rs = preparedstmt.executeQuery();
			if (rs.next()) {
				json.put("createdBy", rs.getString("createdby"));
				json.put("projectname",rs.getString("projectname"));
				Date startDate = sdf1.parse(rs.getString("Startdate"));
			    json.put("startdate", sdf.format(startDate));
			    Date endDate = sdf1.parse(rs.getString("enddate"));
				json.put("enddate", sdf.format(endDate));
				json.put("projectOwner", getUserProjectMapping(id));
		  }
		}catch (SQLException | ParseException e) {
			logger.error(e);
		}  finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return json;
	}
	@Override
	public JSONObject userList() {
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArr=new JSONArray();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs=null;
	    try {
	    	String sql="Select CONCAT(firstname,' ',lastname) AS Name,userid from users ORDER BY Name";
			 con = conn.getConnection();
			 stmt = con.createStatement();
			 rs = stmt.executeQuery(sql);
			while (rs.next()) {
				JSONObject json=new JSONObject();
				json.put("id",rs.getInt("userid"));
				json.put("name",rs.getString("name"));
				jsonArr.put(json);
			}
			jsonObject.put("userList",jsonArr);
		 }catch (SQLException e) {
			 logger.error(e);
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonObject;
	}
	@Override
	public JSONObject deleteproject(String id) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		try {
			String sql = "DELETE FROM project where id=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, id);
			preparedstmt.executeUpdate();
			json.put("msg", "Project Deleted");
			return json;
		} catch (SQLException e) {
			logger.error(e);
			json.put("msg", "Project Deleted failed");
			return json;
		} finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			}
	    }
	
	@Override
	public JSONArray getUserProjectMapping(String projectId) {
		JSONArray jsonArr = new JSONArray();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		try {
		String sql="SELECT upm.userid FROM userprojectmapping upm "
				+ " WHERE projectid =?";
		con = conn.getConnection();
		preparedstmt = con.prepareStatement(sql);
		preparedstmt.setString(1, projectId);
		rs = preparedstmt.executeQuery();
		while (rs.next()) {
			jsonArr.put(rs.getString("userid"));
		}
		}catch (SQLException e) {
			logger.error(e);
		}finally {
			if(con != null) {try{con.close();}catch(SQLException e) {logger.error(e);}}
			if(preparedstmt != null) {try {preparedstmt.close();}catch(SQLException e){logger.error(e);}
			}
		}
		return jsonArr;
	}
	@Override
	public String userproject(String projectid) {
		String arrusers ="";
		PreparedStatement preparedstmt = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT CONCAT(firstname,' ',lastname)AS name,userprojectmapping.userid FROM users "
					+ " INNER JOIN userprojectmapping ON users.userid=userprojectmapping.userid WHERE projectid=?;";
			con = conn.getConnection();
			preparedstmt= con.prepareStatement(sql);
			preparedstmt.setString(1,projectid);
			 rs = preparedstmt.executeQuery();
			while (rs.next()) {
				arrusers +=rs.getString("name")+",";
				}	
			} catch (SQLException e) {
				logger.error(e);
		
			}
			finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
			}
			return arrusers;
		}
}

