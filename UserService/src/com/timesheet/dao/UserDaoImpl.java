package com.timesheet.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.timesheet.db.DBConnection;
import com.timesheet.dto.UserDto;

public class UserDaoImpl implements UserDao {
	
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);
	private static DBConnection conn = DBConnection.getInstance();
	
	@Override
	public int isexistuser(JSONObject json) {
		String sql = "SELECT username FROM users WHERE username =?";
		int counter =0;
		Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs=null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1,json.getString("username"));
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
    public JSONObject insertUser(JSONObject json) {
		JSONObject jsonObj = new JSONObject();
		int roleid =isexistuser(json);
		if(roleid>0) {
			jsonObj.put("msg","already exists");
		}
		else {
		String sql = "INSERT INTO users(firstname,lastname,password,updatedon,username,email,reportingperson,roleid,updatedby) VALUES(?,?,?,?,?,?,?,?,?)";
		Connection con = null;
        PreparedStatement preparedstmt = null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, json.getString("firstname"));
			preparedstmt.setString(2, json.getString("lastname"));
			preparedstmt.setString(3,getMD5EncryptedValue(json.getString("password")));
			preparedstmt.setString(4, LocalDateTime.now().toString());
			preparedstmt.setString(5, json.getString("username"));
			preparedstmt.setString(6, json.getString("email"));
		    preparedstmt.setInt(7,json.getInt("reportingperson"));
			preparedstmt.setInt(8,json.getInt("roleId"));
			preparedstmt.setInt(9,json.getInt("updatedby"));
			preparedstmt.executeUpdate();
			jsonObj.put("msg","user Created successfully");
		} catch (SQLException e) {
			logger.error(e);
			jsonObj.put("msg","user Creation failed");
	}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			
		}
		}
		return jsonObj;
	}
    
  
	@Override
	public String role(UserDto role) {
		String message ="";
		int roleid =isexistrole(role);
		if(roleid>0) {
			 message = "role already exited";
		}
		else {
		String sql = "INSERT INTO role(role) VALUES(?)";
		Connection con = null;
        PreparedStatement preparedstmt = null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, role.getRole());
			con.close();
			message = "role Created successfully";
			
		} catch (SQLException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			}
		}
     return message;
}
	
	@Override
	public JSONObject getRoleList() {
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArr=new JSONArray();
		String sql = "SELECT id, role FROM role ORDER BY role";
		Connection con = null;
		PreparedStatement createstatement = null;
		ResultSet rs=null;
        try {
	        con = conn.getConnection();	
	        createstatement = con.prepareStatement(sql);
			rs=createstatement.executeQuery();
			while (rs.next()) {
				JSONObject json=new JSONObject();
				json.put("id",rs.getString("id"));
				json.put("role",rs.getString("role"));
				jsonArr.put(json);
			}
		jsonObject.put("roleList",jsonArr);
		} catch (SQLException e) {
			logger.error(e);
		}
        finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (createstatement != null) { try {createstatement.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonObject;
	}
	
	@Override
	public JSONObject userList(){
		JSONObject json = new JSONObject();
		JSONArray jsonArr =  new JSONArray();
		String sql = "SELECT users.userid, firstname,lastname,password,username,email,roleid,role,reportingperson FROM users  " + 
				"INNER JOIN role ON users.roleid=role.id;";
		Connection con = null;
		PreparedStatement createstatment = null;
		ResultSet rs=null;
        	try {
        		con = conn.getConnection();	
    	        createstatment = con.prepareStatement(sql);
    			rs=createstatment.executeQuery();
			while (rs.next()) {
				JSONObject use = new JSONObject();
				use.put("userid",rs.getInt("userid"));
				use.put("firstname",rs.getString("firstname"));
				use.put("lastname",rs.getString("lastname"));
				use.put("password",rs.getString("password"));
				use.put("Role",rs.getString("role"));
				use.put("username",rs.getString("username"));
				use.put("email",rs.getString("email"));
				use.put("roleId",rs.getInt("roleid"));
				JSONObject jsonobj = getReportingPerson(rs.getInt("reportingperson"));
				if(!jsonobj.isEmpty()) {
				use.put("reportingpersonName",jsonobj.getString("reportingperson"));
				}
				jsonArr.put(use);
			}
			json.put("userList", jsonArr);
		} catch (SQLException e) {
			logger.error(e);
		}	finally {
   			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
   			 if (createstatment != null) { try { createstatment.close();} catch (SQLException e){logger.error(e);}}
   			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
   		}
    		return json;
    	
    	}

    @Override
	public JSONObject updateUser(JSONObject json) {
		String sql="UPDATE Users SET firstname=?,lastname=?,roleid=?,email=?,Reportingperson=? WHERE userId=?";
		JSONObject jsonobj = new JSONObject();
		Connection con = null;
        PreparedStatement preparedstmt = null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, json.getString("firstname"));
			preparedstmt.setString(2, json.getString("lastname"));
			preparedstmt.setInt(3,json.getInt("roleId"));
			preparedstmt.setString(4, json.getString("email"));
		    preparedstmt.setInt(5,json.getInt("reportingperson"));
			preparedstmt.setInt(6,json.getInt("userId"));
			preparedstmt.executeUpdate();
			jsonobj.put("msg", "user updated successfully");
		}catch (SQLException e) {
			logger.error(e);
			jsonobj.put("msg", "user updated failed");
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
		return jsonobj;
	
	}
	
	@Override
	public int isexistrole(UserDto role) {
		String sql = "SELECT role FROM role WHERE role =?";
		int counter =0;
		 Connection con = null;
	        PreparedStatement preparedstmt = null;
	        ResultSet rs=null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, role.getRole());
			rs=preparedstmt.executeQuery();
			if(rs.next()) {
				counter=1;
			}
			con.close();
			return counter;
		} catch (SQLException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
			return 0;
		}
	
	@Override
	public JSONObject deleteuser(String userid) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT userid FROM users WHERE reportingperson=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, userid);
			ResultSet rs1 = preparedstmt.executeQuery();
			if (rs1.next()) {
				json.put("msg", "You should not delete this user\r\n" + "user Mapped to the reportingperson");
			}
			// preparedstmt.close();
			else {
				String usesrSql = "SELECT userid from userprojectMapping WHERE userid=?";
				PreparedStatement preparedstmt1 = con.prepareStatement(usesrSql);
				preparedstmt1.setString(1, userid);
				rs = preparedstmt1.executeQuery();
				// preparedstmt1.close();
				if (rs.next()) {
					json.put("msg", "You should not delete this user\r\n" + "user Mapped to the project");
				} else {
					String sql1 = "DELETE FROM users WHERE userid=?";
					PreparedStatement preparedstmt2 = con.prepareStatement(sql1);
					preparedstmt2.setString(1, userid);
					preparedstmt2.executeUpdate();
					json.put("msg", "User Deleted");
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			json.put("msg", "User Deletion failed");
		} finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return json;
	}
	
	@Override
	public JSONObject userById(String userId) {
		JSONObject jsonobj = new JSONObject();
		String sql = "SELECT firstname,lastname,roleid,email,username,reportingperson FROM users WHERE userid=?";
		Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs=null;
		try {
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, userId);
			rs=preparedstmt.executeQuery();
			if (rs.next()) {
				jsonobj.put("username",rs.getString("username"));
				jsonobj.put("firstname",rs.getString("firstname"));
				jsonobj.put("lastname",rs.getString("lastname"));
				jsonobj.put("roleId",rs.getInt("roleid"));
				jsonobj.put("email",rs.getString("email"));
				jsonobj.put("reportingperson",rs.getInt("reportingperson"));
				JSONObject json = getReportingPerson(rs.getInt("reportingperson"));
				if(!json.isEmpty()) {
				jsonobj.put("reportingpersonEmail",json.getString("reportingpersonemail"));
				}
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonobj;
	}
		
	
	private static String getMD5EncryptedValue(String password) {
        final byte[] defaultBytes = password.getBytes();
        try {
            final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
            md5MsgDigest.reset();
            md5MsgDigest.update(defaultBytes);
            final byte messageDigest[] = md5MsgDigest.digest();
            final StringBuffer hexString = new StringBuffer();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            password = hexString + "";
        } catch (final NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
      
		return password;
	
		
}
      
    @Override
	public int login(JSONObject json) {
	  Connection con = null;
      PreparedStatement preparedstmt = null;
      ResultSet rs=null;
      int counter=0;
		try {
			String sql = "SELECT username,password FROM users WHERE username=? AND password=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, json.getString("username"));
			preparedstmt.setString(2,getMD5EncryptedValue(json.getString("password")));
			rs=preparedstmt.executeQuery();
			if (rs.next()) {
				counter=1;
			}else {
				counter=2;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		
		}
		return counter;
	}
	@Override
	public JSONObject CheckRole(JSONObject jsonObj){
        JSONObject json = new JSONObject();
        Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs= null;
		try {
			String sql = "SELECT users.userid,role.role,users.username,token FROM users "
					+ " INNER JOIN role ON users.roleid=role.id "
					+ " INNER JOIN sessiontoken st ON st.username = users.username WHERE users.username=?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1,jsonObj.getString("username"));
			rs = preparedstmt.executeQuery();
			if (rs.next()) {
				json.put("userid",rs.getString("userid"));
				json.put("role",rs.getString("role")); 
				json.put("username", rs.getString("username"));
				json.put("token",rs.getString("token"));
			}
		}catch (SQLException e) {
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
	public JSONObject getReportingPerson(int reportingpersonid) {
		JSONObject json = new JSONObject();
		Connection con = null;
        PreparedStatement preparedstmt = null;
        ResultSet rs=null;
		try {
			String sql = "SELECT CONCAT(firstname,' ',lastname) AS reportingperson,email,userid FROM users WHERE userid =?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setInt(1, reportingpersonid);
			rs=preparedstmt.executeQuery();
			if (rs.next()) {
			 json.put("reportingperson", rs.getString("reportingperson"));
			 json.put("reportingpersonemail", rs.getString("email"));
			}
			con.close();
		}catch (SQLException e) {
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
	public void validateToken(String username) {
		Connection con = null;
		PreparedStatement preparedstmt = null;
		try {
		String token = UUID.randomUUID().toString();
		String deletesql = "DELETE FROM sessiontoken WHERE username=?";
		con = conn.getConnection();
		preparedstmt = con.prepareStatement(deletesql);
		preparedstmt.setString(1, username);
		preparedstmt.executeUpdate();
		preparedstmt.close();
		String sql = "INSERT INTO sessiontoken(username,token,updatedon) VALUES(?,?,?)";
		preparedstmt = con.prepareStatement(sql);
		preparedstmt.setString(1, username);
		preparedstmt.setString(2, token);
		preparedstmt.setString(3, LocalDateTime.now().toString());
		preparedstmt.executeUpdate();
		}catch (SQLException e) {
			logger.error(e);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
	}
	@Override
	public Boolean authorizeToken(String username,String token) {
		Connection con =null;
		PreparedStatement preparedstmt = null;
		ResultSet rs= null;
		try {
			String sql ="SELECT token FROM sessiontoken WHERE username=?";
			con=conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, username);
			rs= preparedstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("token").equalsIgnoreCase(token)) {
					return true;
				}
				else {
					return false;
				}
			}
		}catch(SQLException e) {
			logger.error(e);
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
		return false;
	}
	@Override
	public JSONObject addCompanyInfo(JSONObject json) {
		Connection con =null;
		PreparedStatement preparedstmt = null;
		JSONObject jsonObj = new JSONObject();
		try {
		  String sql="INSERT INTO companyinfo(logo,applicationName,createdon)VALUES(?,?,?)";
		  con=conn.getConnection(); 
	      preparedstmt=con.prepareStatement(sql);
	      preparedstmt.setString(1, json.getString("image"));
	      preparedstmt.setString(2,json.getString("applicationName"));
	      preparedstmt.setString(3, LocalDateTime.now().toString());
	      int counter=preparedstmt.executeUpdate();
	      if(counter>0) {
	    	  jsonObj.put("msg", "success");
	      }
		}
		catch(SQLException ex) {
			logger.error(ex);
			jsonObj.put("msg", "failed");
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
		return jsonObj;
	}
	@Override
	public JSONObject fetchCompanyInfo() {
		Connection con =null;
		PreparedStatement preparedstmt = null;
		JSONObject jsonObj = new JSONObject();
		try {
		  String sql="SELECT logo,applicationName FROM companyinfo";
		  con=conn.getConnection(); 
	      preparedstmt=con.prepareStatement(sql);
	      ResultSet rs =preparedstmt.executeQuery();
	      if(rs.next())
	      {
	    	  jsonObj.put("logo",rs.getString("logo"));
	    	  jsonObj.put("applicationName", rs.getString("applicationName"));
	      }
	      else {
	    	  jsonObj.put("msg","logo not found");
	      }
		}
		catch(SQLException ex) {
			logger.error(ex);
		}
		finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
		return jsonObj;
	}
	
	@Override
	public JSONObject passwordchange(JSONObject json) {
		  JSONObject jsonObj = new JSONObject();
		  Connection con = null;
	      PreparedStatement preparedstmt = null;
	      ResultSet rs=null;
			try {
				int userid = checkoldPassword(json);
				if(userid>0) {
				String sql = "UPDATE users set Password=? WHERE userid=?";
				con = conn.getConnection();
				preparedstmt = con.prepareStatement(sql);
				preparedstmt.setString(1,getMD5EncryptedValue(json.getString("password")));
				preparedstmt.setString(2, json.getString("userid"));
				int counter =preparedstmt.executeUpdate();
				if (counter>0) {
					jsonObj.put("msg", "Your password has been updated sucessfully");
					}
				}
				else {
					jsonObj.put("msg", "Incorrect old password");
				}
			} catch (SQLException e) {
				logger.error(e);
				jsonObj.put("msg", "password updation failed");
			}
			finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
			}
			return jsonObj;
		}

	private int checkoldPassword(JSONObject json) {
		 Connection con = null;
	      PreparedStatement preparedstmt = null;
	      ResultSet rs=null;
	      int counter =0;
			try {
				String sql ="SELECT userid FROM users WHERE password=? and userid=?";
				con = conn.getConnection();
				preparedstmt = con.prepareStatement(sql);
				preparedstmt.setString(1,getMD5EncryptedValue(json.getString("oldpassword")));
				preparedstmt.setString(2, json.getString("userid"));
			    rs =preparedstmt.executeQuery();
				if (rs.next()) {
					counter=1;
				}
				}
			catch (SQLException e) {
				logger.error(e);
			}
			finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
			}
			return counter;
	}
	
}