/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

/**
 *
 * @author limgeokshanmb
 */
import DAO.*;
import static DAO.AlertsSettingsDAO.retrieveUserAlertsSettings;
import static DAO.UserDAO.getUsers;
import Entity.AlertsSettings;
import Entity.Hotspot;
import Entity.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Alert {

    private static Properties emailProperties;
    private static Session mailSession;
    private static MimeMessage emailMessage;
    

    public static void sendEmail() throws AddressException, MessagingException {
        String emailHost = "smtp.gmail.com";
        String fromUser = "imda.nas2019";//just the id alone without @gmail.com
        String fromUserEmailPassword = "=HVXm2A2";
        ArrayList<String> bootstrapInfo= DashboardDAO.getLastBootstrapInformation();
        
        //set mail server properties
        String emailPort = "587";//gmail's smtp port

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        //create email messages
        
        //1. get all user alerts settings with their emails and alerts settings in hashmap
        HashMap<String, AlertsSettings> userAlertsSettings = retrieveUserAlertsSettings();
        //2. iterate each email and get their alerts settings for their customised alert in email content
        Set<String> set = userAlertsSettings.keySet();
        Iterator<String> iter = set.iterator();
        while (iter.hasNext()) {
            String email = iter.next();
            AlertsSettings alertsSettings = userAlertsSettings.get(email);
            String username = alertsSettings.getUsername();
            String categoryDate = alertsSettings.getCategoryDate();
            String startDate = alertsSettings.getStartDate();
            String endDate = alertsSettings.getEndDate();
            String lessThanConnectedClients = alertsSettings.getLessThanConnectedClients();
            String moreThanConnectedClients = alertsSettings.getMoreThanConnectedClients();
            String lessThanUtilization = alertsSettings.getLessThanUtilization();
            String moreThanUtilization = alertsSettings.getMoreThanUtilization();
            String outlierUtilizationRates = alertsSettings.getOutlierUtilizationRates();
            String outlierConnectedClients = alertsSettings.getOutlierConnectedClients();
            
            String resultHtml = "<div class='page-title' id='results'><div class='row'><div class='col-md-12 col-sm-12 col-xs-12'><div class='x_panel'><div class='x_title'><h3>Customised Alerts By Analysis Types:</h3></div><div class='x_content'>";
            String closingdiv = "</div></div></div></div></div>";
            
            HotspotDAO hotspotDAO = new HotspotDAO();
            
            ArrayList<Hotspot> getLessThanUtilizationList = hotspotDAO.getLessThanUtilization(lessThanUtilization, startDate, endDate);
            String getLessThanUtilizationListHTML = "<h5>Less Than Your Specified % Utilization</h5> <table border='1' style='border-collapse:collapse' id='datatable-buttons'  class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>Utilization Rate</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>";
            for (Hotspot hotspot : getLessThanUtilizationList) {
                getLessThanUtilizationListHTML += "<tr>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getMacAddress() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getClientsOrUtilization() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getApId() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getDeploymentType() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getLocationType() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getLocationName() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getStreetAddress() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getBlock() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getLevel() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getDescription() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getPostalCode() + "</td>";
                getLessThanUtilizationListHTML += "<td>" + hotspot.getOperator() + "</td>";
                getLessThanUtilizationListHTML += "</tr>";
            }
            getLessThanUtilizationListHTML += "</table>";
            
            ArrayList<Hotspot> getMoreThanUtilizationList = hotspotDAO.getMoreThanUtilization(moreThanUtilization, startDate, endDate);
            String getMoreThanUtilizationListHTML = "<h5>More Than Your Specified % Utilization</h5> <table border='1' style='border-collapse:collapse' id='datatable-buttons'  class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>Utilization Rate</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>";
            for (Hotspot hotspot : getMoreThanUtilizationList) {
                getMoreThanUtilizationListHTML += "<tr>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getMacAddress() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getClientsOrUtilization() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getApId() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getDeploymentType() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getLocationType() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getLocationName() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getStreetAddress() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getBlock() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getLevel() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getDescription() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getPostalCode() + "</td>";
                getMoreThanUtilizationListHTML += "<td>" + hotspot.getOperator() + "</td>";
                getMoreThanUtilizationListHTML += "</tr>";
            }
            getMoreThanUtilizationListHTML += "</table>";
            
            ArrayList<Hotspot> getLessThanConnectedClientsList = hotspotDAO.getLessThanConnectedClients(lessThanConnectedClients, startDate, endDate);
            String getLessThanConnectedClientsListHTML = "<h5>Less Than Your Specified % Connected Clients</h5> <table border='1' style='border-collapse:collapse' id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>ConnectedClients</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>";
            for (Hotspot hotspot : getLessThanConnectedClientsList) {
                getLessThanConnectedClientsListHTML += "<tr>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getMacAddress() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getClientsOrUtilization() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getApId() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getDeploymentType() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getLocationType() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getLocationName() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getStreetAddress() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getBlock() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getLevel() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getDescription() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getPostalCode() + "</td>";
                getLessThanConnectedClientsListHTML += "<td>" + hotspot.getOperator() + "</td>";
                getLessThanConnectedClientsListHTML += "</tr>";
            
            }
            getLessThanConnectedClientsListHTML += "</table>";
            
            ArrayList<Hotspot> getMoreThanConnectedClientsList = hotspotDAO.getMoreThanConnectedClients(moreThanConnectedClients, startDate, endDate);
            String getMoreThanConnectedClientsListHTML = "<h5>More Than Your Specified % Connected Clients</h5> <table border='1' style='border-collapse:collapse' id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>ConnectedClients</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>";
            for (Hotspot hotspot : getMoreThanConnectedClientsList) {
                getMoreThanConnectedClientsListHTML += "<tr>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getMacAddress() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getClientsOrUtilization() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getApId() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getDeploymentType() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getLocationType() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getLocationName() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getStreetAddress() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getBlock() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getLevel() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getDescription() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getPostalCode() + "</td>";
                getMoreThanConnectedClientsListHTML += "<td>" + hotspot.getOperator() + "</td>";
                getMoreThanConnectedClientsListHTML += "</tr>";
            }
            getMoreThanConnectedClientsListHTML += "</table>";
            
            //getAutoConnectedClientsList
            String getAutoConnectedClientsListHTML = "";
            if(outlierConnectedClients.equals("Yes")) {
                ArrayList<String> getAutoConnectedClientsList = hotspotDAO.getAutoConnectedClients();
                Double average = Double.parseDouble(getAutoConnectedClientsList.get(0));
                Double standardDeviation = Double.parseDouble(getAutoConnectedClientsList.get(1));
                Double aboveThreeSd = average + (standardDeviation * 3);
                Double belowThreeSd = average - (standardDeviation * 3);
                ArrayList<Hotspot> getMoreThanConnectedClientsList2 = hotspotDAO.getMoreThanConnectedClients(aboveThreeSd.toString(), startDate, endDate);
                ArrayList<Hotspot> getLessThanConnectedClientsList2 = hotspotDAO.getLessThanConnectedClients(belowThreeSd.toString(), startDate, endDate);
                getAutoConnectedClientsListHTML += "<h5>AP Hotspots With Outlying Number Of Connected Clients</h5>"
                                                         + "<h7>Mean Number of Connected Clients is: </h7>" + average + "<br>" 
                                                         + "<h7>Standard Deviation is: </h7>" + standardDeviation + "<br><br>"
                                                         + "<h7>Outliers Above 3 Standard Deviations from Mean:</h7><br>"
                                                         + "<table border='1' style='border-collapse:collapse' id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>ConnectedClients</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>";
                for (Hotspot hotspot : getMoreThanConnectedClientsList2) {
                    getAutoConnectedClientsListHTML += "<tr>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getMacAddress() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getClientsOrUtilization() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getApId() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getDeploymentType() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getLocationType() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getLocationName() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getStreetAddress() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getBlock() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getLevel() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getDescription() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getPostalCode() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getOperator() + "</td>";
                    getAutoConnectedClientsListHTML += "</tr>";
                }
                getAutoConnectedClientsListHTML += "</table><br>";

                getAutoConnectedClientsListHTML += "<h7>Outliers Below 3 Standard Deviations from Mean:<h7><br>";
                getAutoConnectedClientsListHTML += "<table border='1' style='border-collapse:collapse' id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>ConnectedClients</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>";
                for (Hotspot hotspot : getLessThanConnectedClientsList2) {
                    getAutoConnectedClientsListHTML += "<tr>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getMacAddress() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getClientsOrUtilization() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getApId() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getDeploymentType() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getLocationType() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getLocationName() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getStreetAddress() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getBlock() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getLevel() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getDescription() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getPostalCode() + "</td>";
                    getAutoConnectedClientsListHTML += "<td>" + hotspot.getOperator() + "</td>";
                    getAutoConnectedClientsListHTML += "</tr>";
                }
                getAutoConnectedClientsListHTML += "</table>";
            }
            
            //getAutoUtilization
            /**String getAutoUtilizationListHTML = "";
            if(outlierUtilizationRates.equals("Yes")) {
                ArrayList<String> getAutoUtilizationList = hotspotDAO.getAutoUtilization();
                Double average2 = Double.parseDouble(getAutoUtilizationList.get(0));
                Double standardDeviation2 = Double.parseDouble(getAutoUtilizationList.get(1));
                Double aboveThreeSd2 = average2 + (standardDeviation2 * 3);
                Double belowThreeSd2 = average2 - (standardDeviation2 * 3);
                ArrayList<Hotspot> getMoreThanUtilizationList2 = hotspotDAO.getMoreThanUtilization(aboveThreeSd2.toString(), startDate, endDate);
                ArrayList<Hotspot> getLessThanUtilizationList2 = hotspotDAO.getLessThanUtilization(belowThreeSd2.toString(), startDate, endDate);
                getAutoUtilizationListHTML += "<h5>AP Hotspots With Outlying Utilization Rate</h5>"
                                                    + "<h7>Mean Number of Connected Clients is: </h7>" + average + "<br>"
                                                    + "<h7>Standard Deviation is: </h7>" + standardDeviation + "<br><br>"
                                                    + "<h7>Outliers Above 3 Standard Deviations from Mean:</h7><br>"
                                                    + "<table border='1' style='border-collapse:collapse' id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>Utilization</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>";
                for (Hotspot hotspot : getMoreThanUtilizationList2) {
                    getAutoUtilizationListHTML += "<tr>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getMacAddress() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getClientsOrUtilization() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getApId() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getDeploymentType() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getLocationType() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getLocationName() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getStreetAddress() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getBlock() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getLevel() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getDescription() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getPostalCode() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getOperator() + "</td>";
                    getAutoUtilizationListHTML += "</tr>";
                }
                getAutoUtilizationListHTML += "</table><br>";

                getAutoUtilizationListHTML += "<h7>Outliers Below 3 Standard Deviations from Mean:</h7><br>";
                getAutoUtilizationListHTML += "<table border='1' style='border-collapse:collapse' id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>Utilization</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>";
                for (Hotspot hotspot : getLessThanUtilizationList2) {
                    getAutoUtilizationListHTML += "<tr>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getMacAddress() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getClientsOrUtilization() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getApId() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getDeploymentType() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getLocationType() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getLocationName() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getStreetAddress() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getBlock() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getLevel() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getDescription() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getPostalCode() + "</td>";
                    getAutoUtilizationListHTML += "<td>" + hotspot.getOperator() + "</td>";
                    getAutoUtilizationListHTML += "</tr>";
                }
                getAutoUtilizationListHTML += "</table>";
            } **/
            
            String timeStamp = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format(new Date());
            String emailSubject = "NAS - Data upload completed successfully on: " + timeStamp;
            String emailBody = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width\" />" +

                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
                "<title>NAS - Upload Completed </title>" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"https://dl.dropbox.com/s/sffonkin9rzjyrj/email.css\" />" +
                "</head>" +
                "<body bgcolor=\"#FFFFFF\">" +
                "<!-- HEADER -->" +
                "<table class=\"head-wrap\" bgcolor=\"#C2DFFF\">" + 
                "<tr>" +
                "<td></td>" +
                "<td class=\"header container\" >" +
                "<div class=\"content\">" +
                "<table bgcolor=\"#C2DFFF\">" +
                "<tr>" +
                "<td><img src=\"https://i.ibb.co/5FPXJJJ/imda-nas-original-bg.png\" width=\"400\" height=\"200\" /></td>" +
                "</tr>" +
                "</table>" +
                "</div>" +
                "</td>" +
                "<td></td>" +
                "</tr>" +
                "</table>" +
                "<!-- /HEADER -->" +
                "<!-- BODY -->" +
               "<h3>Bootstrap Completed</h3>" +
                "<p class=\"lead\">Last Bootstrap Run Status: Successful</p>" +                
                "<table border='1' style='border-collapse:collapse'>" +
                  "<tr>" +
                    "<th><b>Total No. Logs Processed:</b></th>" +
                    "<th><b>No. of Logs Cleaned:</b></th>" + 
                    "<th><b>No. of Error Logs:</b></th>" +
                    "<th><b>Total Sessions:</b></th>" +
                    "<th><b>Total Singtel Sessions:</b></th>" +
                    "<th><b>Total Starhub Sessions:</b></th>" +
                    "<th><b>Total M1 Sessions:</b></th>" +
                  "</tr>" +
                  "<tr>" +
                    "<td>" + bootstrapInfo.get(1)+"</td>" +
                    "<td>" + bootstrapInfo.get(2) + "</td>" + 
                    "<td>" + bootstrapInfo.get(3) + "</td>" +
                    "<td>" + bootstrapInfo.get(7) + "</td>" +
                    "<td>" + bootstrapInfo.get(4) + "</td>" +
                    "<td>" + bootstrapInfo.get(5) + "</td>" +
                    "<td>" + bootstrapInfo.get(6) + "</td>" +
                  "</tr>" +
                "</table>" +
                "<br>" +
                resultHtml +
                getLessThanUtilizationListHTML +
                getMoreThanUtilizationListHTML +
                getLessThanConnectedClientsListHTML +
                getMoreThanConnectedClientsListHTML +
                getAutoConnectedClientsListHTML +
                //getAutoUtilizationListHTML +
                closingdiv +
                "<br>" +
                "<!-- /BODY -->" +
                "<!-- FOOTER -->" +
                "<table class=\"footer-wrap\">" +
                "<tr>" +
                "<td></td>" +
                "<td class=\"container\">" +
                "<!-- content -->" +
                "<div class=\"content\">" +
                "<table>" +
                "<tr>" +
                "<td align=\"center\">" +
                "<p>" +
                "<a href=\"http://locakhost:8080/IMDA_Network_Analytics_System/\">Login to IMDA NAS</a>" +
                "</p>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</div><!-- /content -->" +
                "</td>" +
                "<td></td>" +
                "</tr>" +
                "</table><!-- /FOOTER -->" +
                "</body>" +
                "</html>";

            mailSession = Session.getDefaultInstance(emailProperties, null);
            emailMessage = new MimeMessage(mailSession);

            //for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));


            //}

            emailMessage.setSubject(emailSubject);
            emailMessage.setContent(emailBody, "text/html");//for a html email
            //emailMessage.setText(emailBody);// for a text email

            Transport transport = mailSession.getTransport("smtp");

            transport.connect(emailHost, fromUser, fromUserEmailPassword);
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
            System.out.println("Email sent successfully.");
        }
        
        
        
            
    }

}
