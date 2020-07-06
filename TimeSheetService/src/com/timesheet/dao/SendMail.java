package com.timesheet.dao;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONObject;

public class SendMail {

    public JSONObject sendmail(JSONObject json) {
        // Recipient's email ID needs to be mentioned.
    	JSONObject jsonObj = new JSONObject();
    	String to="";
    	String from ="";
    	 if(json.getString("submitType").equalsIgnoreCase("approved") || json.getString("submitType").equalsIgnoreCase("Declined")) {
       	  from = json.has("reportingpersonEmail") ? json.getString("reportingpersonEmail"):"";
       	  to = json.getString("email");
    	 }
    	 else {
    	  from = json.getString("email");
    	  to = json.has("reportingpersonEmail") ? json.getString("reportingpersonEmail"):"";
    	 }

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("prismentsolwebapp@gmail.com", "prism@123");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            if(json.getString("submitType").equalsIgnoreCase("approved")) {
            	 message.setSubject("Approved Timesheet");

     			// Now set the actual message
                 message.setText("Your timesheet has been approved.");
            }
            else if(json.getString("submitType").equalsIgnoreCase("Declined")) {
           	 	message.setSubject("Declined TimeSheet");

    			// Now set the actual message
                message.setText("Your timesheet has been declined,Please check your updated timesheet and resubmit again.");
           }
            else if(json.getString("submitType").equalsIgnoreCase("submit")) {
            // Set Subject: header field
            message.setSubject("Requesting for the Approval Timesheet");

			// Now set the actual message
           
            message.setContent("<span>" +json.getString("firstname")+" "+json.getString("lastname")+ "  request your approval for the weekly timesheet. Please check the timesheet at </span><a href=http://localhost:8080/EmployeeTimeSheet/dashboard>Click here</a>",
                   "text/html");
        }

           // System.out.println("sending...");
            // Send message
            Transport.send(message);
         //   System.out.println("Sent message successfully....");
    
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("exception...");
        }
       
        return jsonObj.put("msg","Sent message successfully");
    }

}
												