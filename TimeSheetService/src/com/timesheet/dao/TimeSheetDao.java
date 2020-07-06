package com.timesheet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.timesheet.db.DBConnection;

public class TimeSheetDao implements Timesheet {
	private static DBConnection conn = DBConnection.getInstance();
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private final SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
	private static Logger logger = Logger.getLogger(TimeSheetDao.class);

	@Override
	public JSONObject insertTimesheet(JSONObject json) {
		Connection con = null;
	    PreparedStatement preparedstmt = null;
	    JSONObject jsonObject = new JSONObject();
	    int timesheetid =isexistTimesheet(json);
		if(timesheetid>0) {
			jsonObject.put("msg","timesheet already exists");
		}
		else {
		String sql = "Insert into employeetimesheet(activityid,comments,projectid,updatedby,updatedon,totalhours,submitteddate,statusid)values(?,?,?,?,?,?,?,?)";
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, json.getString("activityid"));
			preparedstmt.setString(2, json.has("comments") ? json.getString("comments"):"");
			preparedstmt.setString(3, json.getString("projectid"));
			preparedstmt.setString(4, json.getString("updatedby"));
			preparedstmt.setString(5, LocalDateTime.now().toString());
			preparedstmt.setString(6, json.getString("totalhours"));
			//Date d1 = sdf1.parse(json.getString("submitteddate"));
			preparedstmt.setString(7, json.getString("submitteddate"));
			preparedstmt.setString(8, json.getString("statusid"));
			preparedstmt.executeUpdate();
			jsonObject.put("msg", "timesheet added successfully");
			
		} catch (SQLException e) {
			logger.error(e);
			jsonObject.put("msg", "timesheet creation failed");
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 
		}
		}
			return jsonObject;
		}
	

	
	@Override
	public JSONObject updatetime(JSONObject json) {
		Connection con = null;
		PreparedStatement preparedstmt = null;
		JSONObject jsonObject = new JSONObject();
		String sql = "update employeetimesheet SET activityid=?, comments=?,projectid=?,totalhours=? where id=?";
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, json.getString("activityid"));
			preparedstmt.setString(2, json.getString("comments"));
			preparedstmt.setString(3, json.getString("projectid"));
			preparedstmt.setString(4, json.getString("totalhours"));
			preparedstmt.setString(5, json.getString("id"));
			preparedstmt.executeUpdate();
			jsonObject.put("msg", "timesheet updated successfully");
		} catch (SQLException e) {
			logger.error(e);
			jsonObject.put("msg", "timesheet updated failed");
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 
		}
		return jsonObject;
	}

	@Override
	public JSONObject deletetimesheet(String id) {
	    Connection con = null;
        PreparedStatement preparedstmt = null;
        JSONObject json = new JSONObject();
		String sql = "DELETE FROM employeetimesheet where id=?";
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, id);
			preparedstmt.executeUpdate();
			json.put("msg", "timesheet deleted successfully");
		} catch (SQLException e) {
			logger.error(e);
			json.put("msg", "timesheet deletion failed");
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
		return json;
	}

	
//	public List<TimeSheetDto> sheetsList(String userId, String role,String startdate,String enddate) {
	@Override
	public JSONObject sheetsList(JSONObject json) {
		JSONObject timesheetJson = new JSONObject();
		JSONArray timesheetArr = new JSONArray();
//		List<TimeSheetDto> list = new ArrayList<TimeSheetDto>();
	    Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs = null;
		String sql = "SELECT et.id,code,activityid,comments,et.updatedby,et.updatedon, "
				+ "totalhours,submitteddate,projectname from employeetimesheet et "
				+ " INNER JOIN activity ON et.activityid=activity.id"
				+" INNER JOIN project ON project.id=et.projectid"; 
			
		try {
			con = conn.getConnection();
			if (json.getString("role").equalsIgnoreCase("supervisor")) {
				sql += " where et.updatedby=?  and et.statusid=? and et.submitteddate between (?) and (?)";
			}
			 
			else if (!json.getString("role").equalsIgnoreCase("supervisor")) {
					sql += " where et.updatedby=?  and et.statusid=? ";
			}
			preparedstmt = con.prepareStatement(sql);
			if (json.getString("role").equalsIgnoreCase("supervisor")) {
				preparedstmt.setString(1, json.getString("userId"));
				preparedstmt.setString(2,json.getString("statusid"));
				Date date1 = sdf1.parse(json.getString("startdate"));
				Date date2 = sdf1.parse(json.getString("enddate"));
				preparedstmt.setString(3,sdf.format(date1));
				preparedstmt.setString(4,sdf.format(date2) );
				//preparedstmt.setString(4,json.getString("statusid"));
			}
			else if (!json.getString("role").equalsIgnoreCase("supervisor")) {
					preparedstmt.setString(1, json.getString("userId"));
					preparedstmt.setString(2,json.getString("statusid"));
			}
			rs = preparedstmt.executeQuery();
			while (rs.next()) {
				JSONObject sheets = new JSONObject();
				sheets.put("id",rs.getString("id"));
				sheets.put("activityid",rs.getString("activityid"));
				sheets.put("activity",rs.getString("code"));
				sheets.put("comments",rs.getString("comments"));
				sheets.put("projectname",rs.getString("projectname"));
				sheets.put("updatedby",rs.getInt("updatedby"));
				sheets.put("totalhours",rs.getString("totalhours"));
				Date date = sdf.parse(rs.getString("submitteddate"));
				sheets.put("submitteddate",sdf1.format(date));
				timesheetArr.put(sheets);
			}
			timesheetJson.put("sheetlist",timesheetArr);
		} catch (SQLException | ParseException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			
		}
		return timesheetJson;
	}
	
@Override
public JSONObject TimesheetStatus() {
	JSONObject jsonObject = new JSONObject();
	JSONArray jsonArr = new JSONArray();
	Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
	String sql = "SELECT id ,status from timesheetstatus";
	try {
		 con = conn.getConnection();
		 stmt = con.createStatement();
		 rs = stmt.executeQuery(sql);
		while (rs.next()) {
			JSONObject json = new JSONObject();
			json.put("id", rs.getInt("id"));
			json.put("status", rs.getString("status"));
			jsonArr.put(json);
		}
		jsonObject.put("statusList", jsonArr);
	} catch (SQLException e) {
		logger.error(e);
	}
	finally {
		 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
		 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
		 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
	}

	return jsonObject;

}
	@Override
	public JSONObject activityList() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		Connection con = null;
	    Statement stmt = null;
	    ResultSet rs = null;
		String sql = "SELECT id ,code from activity ORDER BY code";
		try {
			 con = conn.getConnection();
			 stmt = con.createStatement();
			 rs = stmt.executeQuery(sql);
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getInt("id"));
				json.put("code", rs.getString("code"));
				jsonArr.put(json);
			}
			jsonObject.put("timesheetList", jsonArr);
		} catch (SQLException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}

		return jsonObject;
	}

	@Override
	public JSONObject fetchtimes(String id) {
		JSONObject json = new JSONObject();
        Connection con= null;
        PreparedStatement preparedstmt=null;
        ResultSet rs = null;
        String sql = "SELECT totalhours,activityid,comments, submitteddate,projectid from employeetimesheet WHERE id=?";
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, id);
			rs = preparedstmt.executeQuery();
			if (rs.next()) {
				json.put("totalhours", rs.getString("totalhours"));
				json.put("activityid", rs.getString("activityid"));
				json.put("comments", rs.getString("comments"));
				Date date = sdf.parse(rs.getString("submitteddate"));
				json.put("projectid", rs.getString("projectid"));
				json.put("submitteddate", sdf1.format(date));
			}

		} catch (SQLException | ParseException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}

		return json;
	}

	@Override
	public JSONObject getProjectList() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		Connection con=null;
		Statement stmt=null;
		ResultSet rs = null;
		String sql = "SELECT id ,projectname FROM project ORDER BY projectname";
		try {
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
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonObject;
	}
	
	@Override
	public JSONObject sheetList(JSONObject json) {
		List<String> dates = new ArrayList<String>();
		JSONObject sheetListJson = new JSONObject();
		JSONObject timesheetJson = new JSONObject();
		JSONArray timesheetArr = new JSONArray();
		JSONArray timesheetListArr = new JSONArray();
		JSONArray timesheetidArr= new JSONArray();
	    Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs = null;
		String sql = "SELECT et.id,code,activityid,comments,et.updatedby,et.updatedon, "
				+ "totalhours,submitteddate,projectname,status from employeetimesheet et "
				+ " INNER JOIN activity ON et.activityid=activity.id"
				+" INNER JOIN project ON project.id=et.projectid"
				+" INNER JOIN timesheetstatus ts ON ts.id = et.statusid"
				+" WHERE et.updatedby=? and et.submitteddate between (?) and (?)";
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			String currentdate = sdf.format(c.getTime());
			Date startdate = sdf1.parse(json.getString("startdate"));
			dates = getWeekDates(sdf.format(startdate));

			for (String str : dates) {
			timesheetJson = new JSONObject();
		    String[] dateList = str.split("_");
			String status = null;
			preparedstmt.setString(1, json.getString("userId"));
			preparedstmt.setString(2,dateList[0]);
			preparedstmt.setString(3,dateList[1]);
			rs = preparedstmt.executeQuery();
			timesheetArr = new JSONArray();
			timesheetidArr= new JSONArray();
			
			while (rs.next()) {
				JSONObject sheets = new JSONObject();
				sheets.put("id",rs.getString("id"));
				sheets.put("activityid",rs.getString("activityid"));
				sheets.put("activity",rs.getString("code"));
				sheets.put("comments",rs.getString("comments"));
				sheets.put("projectname",rs.getString("projectname"));
				sheets.put("updatedby",rs.getInt("updatedby"));
				sheets.put("totalhours",rs.getString("totalhours"));
				Date date = sdf.parse(rs.getString("submitteddate"));
				sheets.put("submitteddate",sdf1.format(date));
				status =  rs.getString("status");
				timesheetArr.put(sheets);
				timesheetidArr.put(rs.getString("id"));
			}
			timesheetJson.put(str,timesheetArr);
			timesheetJson.put("status", status);
			timesheetJson.put("userids",timesheetidArr);
			if(currentdate.equals(dateList[0]))
			{
				timesheetJson.put("currentWeek", "1");
			}
			timesheetListArr.put(timesheetJson);
		  }
		  sheetListJson.put("sheetList", timesheetListArr);
		} catch (SQLException | ParseException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			
		}
		return sheetListJson;
	}	
	
	@Override
	public List<String> getWeekDates(String startdate) {
	try {
		Calendar c = Calendar.getInstance();
		Date dt = sdf.parse(startdate);
//		System.out.println(dt);
		c.setTime(dt);
		List<String> dates = new ArrayList<String>();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, 1);
		while (c.get(Calendar.MONTH) == month) {
			while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				c.add(Calendar.DAY_OF_MONTH, -1);
			}
			String date = sdf.format(c.getTime()) + "_";
			c.add(Calendar.DAY_OF_MONTH, 6);
			date += sdf.format(c.getTime());
			dates.add(date);
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
			return dates;
		}
		
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public JSONObject setNextWeekSheet(String userId) {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs = null;
		JSONObject timeJson = new JSONObject();
		JSONObject jsonMsg = new JSONObject();
		try {
			timeJson.put("totalhours", "0");
			String sql ="SELECT id FROM activity LIMIT 1";
			con = conn.getConnection();
			stmt = con.prepareStatement(sql);
			rs=stmt.executeQuery();
			if(rs != null && rs.next()){

				timeJson.put("activityid", rs.getString(1));
			}
			stmt.close();
			String projsql ="SELECT id FROM project LIMIT 1";
			stmt = con.prepareStatement(projsql);
			rs=stmt.executeQuery();
			if(rs != null && rs.next()){

				timeJson.put("projectid", rs.getString(1));
			}
			String statussql ="SELECT id FROM timesheetstatus WHERE status='open'";
			stmt = con.prepareStatement(statussql);
			rs=stmt.executeQuery();
			if(rs != null && rs.next()){

				timeJson.put("statusid", rs.getString(1));
			}
			timeJson.put("updatedby", userId);
			Calendar c = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			timeJson.put("submitteddate", df.format(c.getTime()));
			jsonMsg = insertTimesheet(timeJson);

			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			timeJson.put("submitteddate", df.format(c.getTime()));
			jsonMsg =insertTimesheet(timeJson);
			
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			timeJson.put("submitteddate", df.format(c.getTime()));
			jsonMsg= insertTimesheet(timeJson);
			
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			timeJson.put("submitteddate", df.format(c.getTime()));
			jsonMsg =insertTimesheet(timeJson);
			
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			timeJson.put("submitteddate", df.format(c.getTime()));
			jsonMsg = insertTimesheet(timeJson);
			
		} catch (JSONException | SQLException e) {
			logger.error(e);
		}
		return jsonMsg;
	}
	
	
	@Override
	public int isexistTimesheet(JSONObject json) {
		String sql = "SELECT id FROM employeetimesheet WHERE updatedby =? AND submitteddate=?";
		int counter =0;
		Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs=null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1,json.getString("updatedby"));
			preparedstmt.setString(2,json.getString("submitteddate"));
			rs=preparedstmt.executeQuery();
			if(rs.next()) {
				counter=1;
			}
			con.close();
			return counter;
		} catch (SQLException e) {
			logger.error(e);}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return 0;
		}

	@Override
	public JSONObject getId(String[] ids,String submitType) {
		JSONObject timesheetidJson= new JSONObject();
		Connection con =null;
		PreparedStatement preparedstmt = null;
		ResultSet rs =null;
		String sql = "UPDATE employeetimesheet SET statusid=? where id=?";
		try {
			 con= conn.getConnection();
		//	 JSONObject json = new JSONObject();
			 String sql1 ="Select id from timesheetstatus where status=?" ;
				preparedstmt=con.prepareStatement(sql1);
				//if(rs.next()) {
				if (submitType.equalsIgnoreCase("Submit")){
				preparedstmt.setString(1, "Waiting for Approval");
				}
			else if(submitType.equalsIgnoreCase("Approved")) {
				preparedstmt.setString(1, "Approved");
			}
			else if(submitType.equalsIgnoreCase("Declined"))  {
				preparedstmt.setString(1, "Declined");
			}
				rs=preparedstmt.executeQuery();
				if(rs.next()) {
				String id  = rs.getString("id");
					for (String str : ids) {
					  timesheetidJson = new JSONObject();
					  preparedstmt=con.prepareStatement(sql);
					  preparedstmt.setString(1, id);
					  preparedstmt.setString(2,str);
					  preparedstmt.executeUpdate();
			}}
				
			} catch (SQLException e) {
				logger.error(e);
			}
			finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close();} catch (SQLException e){logger.error(e);}}
				}
			return  timesheetidJson;
			
		}
	@Override
	public JSONObject usersdrop(String userid) {
		JSONObject json = new JSONObject();
		JSONArray jsonarr= new JSONArray();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "SELECT CONCAT(firstname,' ',lastname) AS username,userid FROM users WHERE reportingperson=?";
			con=conn.getConnection();
			 preparedstmt= con.prepareStatement(sql);
			preparedstmt.setString(1, userid);
			rs=preparedstmt.executeQuery();
			while(rs.next()) {
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("username",rs.getString("username"));
			jsonobj.put("userid", rs.getString("userid"));
			//jsonobj.put("reportingperson", rs.getString("reportingperson"));
			jsonarr.put(jsonobj);
			}
			json.put("userlist",jsonarr);	
		} catch (SQLException e) {
			logger.error(e);
	
		} 
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return json;
	}
	
		
}

