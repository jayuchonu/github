package com.timesheet.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {
		    public static final String readProperties(){
		        InputStream inputStream = null;
		        String serviceURL = null;
		        try {
		            Properties properties = new Properties();
		            ClassLoader loader =Thread.currentThread().getContextClassLoader();
		            inputStream = loader.getResourceAsStream("application.properties");
		            properties.load(inputStream);
		            serviceURL = properties.getProperty("serviceUrl");
		        }
		        catch (IOException e) {
					
				}
				return serviceURL; 
		    }
}

