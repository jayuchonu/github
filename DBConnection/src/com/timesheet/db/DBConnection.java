package com.timesheet.db;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
public class DBConnection {
	private static DBConnection instance;
	private static Logger logger = Logger.getLogger(DBConnection.class);
	public Connection getConnection(){
		Connection con=null;
		FileInputStream fs= null;
		Properties prop = null;
		File file = null;
		try {
			file = new File(System.getProperty("user.dir")+"/webapps/EmployeeTimesheetService/config/application.properties");
			Path path = Paths.get(System.getProperty("user.dir"));
			if(file.exists()) {
			fs = new FileInputStream(file);
			}
			else if(!file.exists()) {
				file = new File(path.getParent()+"/EmployeeTimesheetService/config/application.properties");
				fs = new FileInputStream(file);
			}
			prop = new Properties();
			prop.load(fs);
			Class.forName(prop.getProperty("jdbc.driver"));
			con = DriverManager.getConnection(prop.getProperty("jdbc.url"), prop.getProperty("jdbc.username"),
					prop.getProperty("jdbc.password"));
			ResultSet resultSet = con.getMetaData().getCatalogs();
			while (resultSet.next()) {
			  //String databaseName = resultSet.getString(1);
			 // System.out.print(databaseName);
			}
			} catch (SQLException e) {		
			logger.error(e);
		} catch (ClassNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} 
		finally {
		    if (fs != null){try{fs.close();}catch (Exception e){logger.error(e);}}
		}
		return con;
	}
    
	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		} else
			try {
				if (instance.getConnection().isClosed()) {
					instance = new DBConnection();
				}
			} catch (SQLException e) {
				logger.error(e);
			}
		return instance;
	}
  }



