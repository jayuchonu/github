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

public class ActivityDao implements Activity {
	private static Logger logger = Logger.getLogger(ActivityDao.class);

	private static DBConnection conn = DBConnection.getInstance();
	private final SimpleDateFormat simpledf = new SimpleDateFormat("MM/dd/yyyy");
	private final SimpleDateFormat simpledf1 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public JSONObject actInsert(JSONObject json) {
		JSONObject jsonObject = new JSONObject();
		int counter=nameExists(json.getString("code"));
		if(counter>0) {
			jsonObject.put("msg", "Activity already exists");
			} 
		else {
			Connection con = null;
			PreparedStatement preparedstmt = null;
			try {
				String sql = "INSERT INTO activity(code,projectid,startdate,enddate,updatedon,updatedby)VALUES(?,?,?,?,?,?)";
				Date d1, d2;
				con = conn.getConnection();
				preparedstmt = con.prepareStatement(sql);
				preparedstmt.setString(1, json.getString("code"));
				preparedstmt.setString(2, json.getString("projectid"));
				d1 = simpledf.parse(json.getString("startdate"));
				preparedstmt.setString(3, simpledf1.format(d1));
				d2 = simpledf.parse(json.getString("enddate"));
				preparedstmt.setString(4, simpledf1.format(d2));
				preparedstmt.setString(5, LocalDateTime.now().toString());
				preparedstmt.setString(6, json.getString("updatedby"));
				preparedstmt.executeUpdate();
				jsonObject.put("msg", "Code created successfully");
			} catch (SQLException | ParseException e) {
				logger.error(e);
				jsonObject.put("msg", "Code created failed");
			} finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
	}
		return jsonObject;
	}
	
	@Override
	public int nameExists(String code) {
		Connection con = null;
		PreparedStatement preparedstmt= null;
		ResultSet rs = null;
		try {
			String sql = "SELECT code FROM activity WHERE code=?";
			int counter = 0;
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, code);
			rs = preparedstmt.executeQuery();
			if (rs.next()) {
				counter = 1;
			}
			return counter;
		} catch (SQLException e) {
			logger.error(e);
			return 0;
		} 
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
	}
	

	

	@Override
	public JSONObject actUpdate(JSONObject jsonObject) {
		JSONObject json =new JSONObject();
		Connection con =null;
		PreparedStatement preparedstmt = null;
		Date d1, d2;
		try {
			String sql = "UPDATE activity SET code=?,projectid=?,startdate=?,enddate=? where id=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, jsonObject.getString("code"));
			preparedstmt.setString(2, jsonObject.getString("projectid"));
			d1 = simpledf.parse(jsonObject.getString("startdate"));
			preparedstmt.setString(3, simpledf1.format(d1));
			d2 = simpledf.parse(jsonObject.getString("enddate"));
			preparedstmt.setString(4, simpledf1.format(d2));
			preparedstmt.setString(5, jsonObject.getString("id"));
			preparedstmt.executeUpdate();
		 	json.put("msg","Code Updated");
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
	public JSONObject fetchActivity(String id) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT id,code,projectid,startdate,enddate FROM activity where id=?";
			Date d1, d2;
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, id);
			 rs = preparedstmt.executeQuery();
			if (rs.next()) {
				json.put("code", rs.getString("code"));
				json.put("projectid", rs.getString("projectid"));
				d1 = simpledf1.parse(rs.getString("startdate"));
				json.put("startdate", simpledf.format(d1));
				d2 = simpledf1.parse(rs.getString("enddate"));
				json.put("enddate", simpledf.format(d2));
			}
		} catch (SQLException | ParseException e) {
			logger.error(e);
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		  return json;
	}
	
	@Override
	public JSONObject activityList() {
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr = new JSONArray();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT activity.id,activity.code,activity.projectid,activity.startdate,activity.enddate,activity.otherdetails,"
					+ " project.projectname FROM activity  INNER JOIN project ON activity.projectid=project.id";
			con = conn.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getString("id"));
				json.put("code", rs.getString("code"));
				json.put("projectid", rs.getString("projectid"));
				Date d1 = simpledf1.parse(rs.getString("startdate"));
				json.put("startdate", (simpledf.format(d1)));
				Date d2 = simpledf1.parse(rs.getString("enddate"));
				json.put("enddate", (simpledf.format(d2)));
				json.put("otherdetails", rs.getString("otherdetails"));
				json.put("projectname", rs.getString("projectname"));
				jsonarr.put(json);
			}
			jsonobj.put("activityList", jsonarr);
		} catch (SQLException | ParseException e) {
			logger.error(e);
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonobj;
	}

	@Override
	public JSONObject getProjectList() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT id ,projectname FROM project ORDER BY projectname";
			con = conn.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getInt("id"));
				json.put("projectname", rs.getString("projectname"));
				jsonArr.put(json);
			}
			jsonObject.put("getProjectList", jsonArr);
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonObject;
	}

	
	@Override
	public JSONObject deleteActivity(String id) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		
		ResultSet rs = null;
			try{
				String sql="SELECT activityid from employeetimesheet where activityid=?";
				con = conn.getConnection();
				preparedstmt = con.prepareStatement(sql);
        		preparedstmt.setString(1, id);
        		rs=preparedstmt.executeQuery();
				 if(rs.next()){
				 json.put("msg","You should not delete this activity\r\n" +
				 		"Activity Mapped to the project");
                } else {
                		String sql1 = "DELETE FROM activity act WHERE act.id=?";
                		PreparedStatement preparedstmt1 = con.prepareStatement(sql1);
                		preparedstmt1.setString(1, id);
                		preparedstmt1.executeUpdate();
                		json.put("msg", "Activity Deleted");
                		}
			 } catch (SQLException e) {
				 logger.error(e);
				 json.put("msg", "Activity Deletion failed");
		} finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close();} catch (SQLException e){logger.error(e);}}
	
			}
			return json;
		}
	}



	