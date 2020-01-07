/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.AlertsSettings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAO.ConnectionManager;
import java.util.HashMap;

/**
 *
 * @author jeremylimys93
 */
public class AlertsSettingsDAO {
    
      public static void insertAlertsSettings(AlertsSettings alertsSettings ) {
          
        Connection conn = null;
        PreparedStatement stmt = null;
        
        //retrieve object values
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
        try {
            //1.open conn 
            conn = ConnectionManager.getConnection();
            //2.prepare stmt
            stmt = conn.prepareStatement("INSERT INTO alert (username, categoryDate, startDate, endDate, lessThanConnectedClients, moreThanConnectedClients , lessThanUtilization, moreThanUtilization, outlierUtilizationRates, outlierConnectedClients) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            stmt.setString(1,username);
            stmt.setString(2,categoryDate);
            stmt.setString(3,startDate);
            stmt.setString(4,endDate);
            stmt.setString(5,lessThanConnectedClients);
            stmt.setString(6,moreThanConnectedClients);
            stmt.setString(7,lessThanUtilization);
            stmt.setString(8,moreThanUtilization);
            stmt.setString(9,outlierUtilizationRates);
            stmt.setString(10,outlierConnectedClients);
            
            //3.execute the preparedstatement insert
            stmt.executeUpdate();
           
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4. close all resources
            ConnectionManager.close(conn, stmt);
        }
        //return nothing
    }
    
        public static void updateAlertsSettings(AlertsSettings alertsSettings) { 
       
        Connection conn = null;
        PreparedStatement stmt = null;
        
        //retrieve object values
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
        
        try {
            //1.open conn 
            conn = ConnectionManager.getConnection();
            //2.prepare stmt
            stmt = conn.prepareStatement("UPDATE alert SET categoryDate = ?, startDate = ?, endDate = ?, lessThanConnectedClients = ?, moreThanConnectedClients = ?, lessThanUtilization = ?, moreThanUtilization = ?, outlierUtilizationRates = ?, outlierConnectedClients = ? WHERE username = ?");
            stmt.setString(1, categoryDate);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            stmt.setString(4, lessThanConnectedClients);
            stmt.setString(5, moreThanConnectedClients);
            stmt.setString(6, lessThanUtilization);
            stmt.setString(7, moreThanUtilization);
            stmt.setString(8, outlierUtilizationRates);
            stmt.setString(9, outlierConnectedClients);
            stmt.setString(10, username);
            //3.execute our sql update statement
            stmt.executeUpdate();
           
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4. close all resources
            ConnectionManager.close(conn, stmt);
        }
        //return nothing
    }
    
      
      //return the alertsSettings  with the given input username that is found in the database, otherwise return null  
      public static AlertsSettings retrieveAlertsSettings(String userName) { 
        AlertsSettings alertsSettings = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            //1.open conn 
            conn = ConnectionManager.getConnection();
            //2.prepare stmt
            stmt = conn.prepareStatement("SELECT username, categoryDate, startDate, endDate, lessThanConnectedClients, moreThanConnectedClients, lessThanUtilization, moreThanUtilization, outlierUtilizationRates, outlierConnectedClients FROM alert WHERE username = ?");
            stmt.setString(1, userName);
            //3.execute query for resultset
            rs = stmt.executeQuery();
            while (rs.next()) {
                alertsSettings = new AlertsSettings(rs.getString("username"), rs.getString("categoryDate"), rs.getString("startDate"), rs.getString("endDate"), rs.getString("lessThanConnectedClients"), rs.getString("moreThanConnectedClients"), rs.getString("lessThanUtilization"), rs.getString("moreThanUtilization"), rs.getString("outlierUtilizationRates"), rs.getString("outlierConnectedClients"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4. close all resources
            ConnectionManager.close(conn, stmt, rs);
        }
        return alertsSettings;
    }
      
      //return hashmap to get all user alerts settings with their email and alerts settings for only username with alerts setting 
      public static HashMap<String, AlertsSettings> retrieveUserAlertsSettings() { 
        HashMap<String, AlertsSettings>  userAlertsSettingsMap = new HashMap<String, AlertsSettings>() ;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            //1.open conn 
            conn = ConnectionManager.getConnection();
            //2.prepare stmt
            stmt = conn.prepareStatement("SELECT email, t2.username AS alertUserName, categoryDate, startDate, endDate, lessThanConnectedClients, moreThanConnectedClients, lessThanUtilization, moreThanUtilization, outlierUtilizationRates, outlierConnectedClients FROM users t1 INNER JOIN alert t2 ON t1.username = t2.username");
            //3.execute query for resultset
            rs = stmt.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                AlertsSettings alertsSettings = new AlertsSettings(rs.getString("alertUserName"), rs.getString("categoryDate"), rs.getString("startDate"), rs.getString("endDate"), rs.getString("lessThanConnectedClients"), rs.getString("moreThanConnectedClients"), rs.getString("lessThanUtilization"), rs.getString("moreThanUtilization"), rs.getString("outlierUtilizationRates"), rs.getString("outlierConnectedClients"));
                if(!userAlertsSettingsMap.containsKey(email)){
                    userAlertsSettingsMap.put(email, alertsSettings);
                } else {
                    //the key already exists in the map.ignore it,should not have duplicate email!
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4. close all resources
            ConnectionManager.close(conn, stmt, rs);
        }
        return userAlertsSettingsMap;
    }
        
   
    


}
