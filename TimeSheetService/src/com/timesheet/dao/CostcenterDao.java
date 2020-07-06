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
public class CostcenterDao implements Costcenterinterface {
	
	private static Logger logger = Logger.getLogger(CostcenterDao.class);
	private static DBConnection conn = DBConnection.getInstance();
	private final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat dmy = new SimpleDateFormat("MM/dd/yyyy");
	
	@Override
	public JSONObject Insertcost(JSONObject cost) {
		int counter = Nameexists(cost);
		JSONObject json = new JSONObject();
		if(counter>0) {
			json.put("msg","Product already exists");
		}else {
			Connection con=null;
			PreparedStatement preparedstmt = null;
		
		try {
			String sql= "INSERT INTO costcenter(name,description,orderdetails,ownerId,datecreated,updatedby,updatedon)VALUES(?,?,?,?,?,?,?)";
			con =conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1,cost.getString("name"));
			preparedstmt.setString(2,cost.getString("description"));
			preparedstmt.setString(3,cost.getString("orderdetails"));
			preparedstmt.setString(4,cost.getString("ownerId"));
			preparedstmt.setString(5,LocalDateTime.now().toString());
			preparedstmt.setString(6,cost.getString("updatedby"));
			preparedstmt.setString(7,LocalDateTime.now().toString());
		    preparedstmt.executeUpdate();
			json.put("msg","Product created successfully");
		} catch (SQLException e) {
			logger.error(e);
			json.put("msg","Product creation failed");
		}finally { 
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}

	   }
	}
		return json;
	}
	
	@Override
	 public int Nameexists(JSONObject cost) {
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		int counter = 0;
		try {
			String sql = "SELECT Name FROM costcenter WHERE name=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, cost.getString("name"));
			rs = preparedstmt.executeQuery();
			if (rs.next()) {
				counter = 1;
			}
			return counter;
		} catch (SQLException e) {
			logger.error(e);
			return 0;
		}finally { 
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
			 }
	 }
	 
	@Override
	public JSONObject updatecost(JSONObject cost) {
    Connection con =null;
    PreparedStatement preparedstmt = null;
    JSONObject json = new JSONObject();
	try {
			String sql="UPDATE costcenter SET name=?,description=?,orderdetails=?,ownerId=?,datemodified=? WHERE id=?";
			con =conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1,cost.getString("name"));
			preparedstmt.setString(2,cost.getString("description"));
			preparedstmt.setString(3,cost.getString("orderdetails"));
			preparedstmt.setString(4,cost.getString("ownerId"));
			preparedstmt.setString(5,LocalDateTime.now().toString());
			preparedstmt.setString(6,cost.getString("id"));
			preparedstmt.executeUpdate();
			json.put("msg", "costcenter updated successfully");
		} catch (SQLException e) {
			logger.error(e);
			json.put("msg", "costcenter updation failed");
		}finally { 
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
	   }
	return json;

	}
	
	@Override
	public JSONObject costList() {
		JSONObject json = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		Date d1;
		Date d2;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
 		try {

 			String sql = "SELECT costcenter.id, name,description,orderdetails,ownerId,datecreated,datemodified,CONCAT(firstname,' ',lastname) "
 					+ "AS ownername  FROM costcenter INNER JOIN users ON costcenter.ownerId=users.userid";
			 con = conn.getConnection();
			 stmt = con.createStatement();
			 rs = stmt.executeQuery(sql);
			while (rs.next()) {
				JSONObject costcenter = new JSONObject();
				costcenter.put("id",rs.getInt("id"));
				costcenter.put("name",rs.getString("name"));
				costcenter.put("description",rs.getString("description"));
				costcenter.put("orderdetails",rs.getString("orderdetails"));
				costcenter.put("ownername",rs.getString("ownername"));
				d1=ymd.parse(rs.getString("Datecreated"));
				costcenter.put("datecreated",dmy.format(d1));
				if(rs.getString("datemodified") != null) {
				d2=ymd.parse(rs.getString("Datemodified"));
				costcenter.put("datemodified",dmy.format(d2));
				}
				jsonArr.put(costcenter);
			}
			json.put("costcenterList", jsonArr);
		} catch (SQLException | ParseException e) {
			logger.error(e);
			
		}finally { 
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
	   }
 		return json;
	}
	
	@Override
	public JSONObject NameById(String Id){
		JSONObject json=new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs= null;
		try {
			 String sql="SELECT Name,Description,Orderdetails,OwnerId,Datecreated,Datemodified FROM costcenter WHERE Id=?";
			 con=conn.getConnection();
			 preparedstmt=con.prepareStatement(sql);
			 preparedstmt.setString(1,Id);
			 rs=preparedstmt.executeQuery();
			if(rs.next()) {
				json.put("Name",rs.getString("Name"));
				json.put("Description",rs.getString("Description"));
				json.put("Orderdetails",rs.getString("Orderdetails"));
				json.put("OwnerId",rs.getString("OwnerId"));
				}
			}catch (SQLException e) {
				logger.error(e);
				
			}finally { 
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		   }
		return json;
	}
	
	@Override
	public JSONObject userlist(){
			JSONObject json = new JSONObject();
			JSONArray jsonArr=new JSONArray();
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				String sql="SELECT CONCAT(firstname,' ',lastname) AS name,userid FROM users ORDER BY name";
				con = conn.getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
				JSONObject jsonobj = new JSONObject();
					jsonobj.put("name",rs.getString("name"));
					jsonobj.put("userid",rs.getString("userid"));
					jsonArr.put(jsonobj);
				}
				json.put("userlist",jsonArr);
			}catch (SQLException e) {
				logger.error(e);
			}finally { 
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		   }

			return json;
	}

	@Override
	public JSONObject deletecost(String Id) {
		Connection con = null;
		PreparedStatement preparedstmt = null;
		JSONObject json = new JSONObject();
		try {
			String sql = "DELETE from costcenter WHERE Id=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, Id);
			preparedstmt.executeUpdate();
			json.put("msg","costcenter deleted");
		}catch (SQLException e) {
			logger.error(e);
			json.put("msg","costcenter deletion failed");
		}finally { 
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
	   }
		return json;
}
}





