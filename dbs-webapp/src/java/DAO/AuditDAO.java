/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;
import java.text.*;
import java.util.Date;
import java.util.TreeMap;

/**
 *
 * @author limgeokshanmb
 */
public class AuditDAO {

    public static void logAction(String userName, String actionType, String action, String details) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            // the mysql insert statement
            String query = "insert into auditlog (timestamp, userName, actionType, action, details) values (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            stmt.setString(2, userName);
            stmt.setString(3, actionType);
            stmt.setString(4, action);
            stmt.setString(5, details);
            stmt.execute();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING, "Unable to connect", ex);
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public static ArrayList<String> getUserList() {
        ArrayList<String> userList = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String csv = "";
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select distinct userName from auditlog order by userName asc");
            // to add headers, and selection criteria
            //stmt.setString(1, fileLocation);
            rs = stmt.executeQuery();
            while (rs.next()) {
                userList.add(rs.getString(1));
            }
            return userList;
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public static ArrayList<String> getActionList() {
        ArrayList<String> userList = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String csv = "";
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select distinct actionType from auditlog order by actionType asc");
            // to add headers, and selection criteria
            //stmt.setString(1, fileLocation);
            rs = stmt.executeQuery();
            while (rs.next()) {
                userList.add(rs.getString(1));
            }
            return userList;
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public static String export() throws IOException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String csv = "";

        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select timestamp, userName, actionType, action, details"
                    + " from auditlog");
            //"select 'timestamp', 'userName', 'actionType', 'action', 'details'"
            //+ " union all"
            //+ 
            // to add headers, and selection criteria
            rs = stmt.executeQuery();
            while (rs.next()) {
                csv += rs.getString(1) + ","
                        + rs.getString(2) + ","
                        + rs.getString(3) + ","
                        + rs.getString(4) + ","
                        + rs.getString(5) + "," + (System.getProperty("line.separator"));
            }
            if (csv.equals("")){
                csv+="No Result Found,No Result Found,No Result Found,No Result Found,No Result Found";
            }
            return csv;
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public static String export(String startDate, String endDate) throws IOException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String csv = "";

        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select timestamp, userName, actionType, action, details"
                    + " from auditlog where timestamp >= ? and timestamp<= ?");
            //"select 'timestamp', 'userName', 'actionType', 'action', 'details'"
            //+ " union all"
            //+ 
            // to add headers, and selection criteria
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            rs = stmt.executeQuery();
            while (rs.next()) {
                csv += rs.getString(1) + ","
                        + rs.getString(2) + ","
                        + rs.getString(3) + ","
                        + rs.getString(4) + ","
                        + rs.getString(5) + "," + (System.getProperty("line.separator"));
            }
            if (csv.equals("")){
                csv+="No Result Found,No Result Found,No Result Found,No Result Found,No Result Found";
            }
            return csv;
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public static String export(String startDate, String endDate, String user) throws IOException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String csv = "";

        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select timestamp, userName, actionType, action, details"
                    + " from auditlog where timestamp >= ? and timestamp<= ? and userName = ?");
            //"select 'timestamp', 'userName', 'actionType', 'action', 'details'"
            //+ " union all"
            //+ 
            // to add headers, and selection criteria
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            stmt.setString(3, user);
            rs = stmt.executeQuery();
            while (rs.next()) {
                csv += rs.getString(1) + ","
                        + rs.getString(2) + ","
                        + rs.getString(3) + ","
                        + rs.getString(4) + ","
                        + rs.getString(5) + "," + (System.getProperty("line.separator"));
            }
            if (csv.equals("")){
                csv+="No Result Found,No Result Found,No Result Found,No Result Found,No Result Found";
            }
            return csv;
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

}
