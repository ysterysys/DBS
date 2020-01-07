/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utility.CustomDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author limgeokshanmb
 */
public class BootstrapDAO {

    public static void revertRecords(String uploadTime) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("delete from bootstrapTotalRecords where uploadTime = ?");
            stmt.setString(1, uploadTime);
            stmt.executeUpdate();

            stmt = conn.prepareStatement("delete from cleanSessionEntry where uploadTime = ?");
            stmt.setString(1, uploadTime);
            stmt.executeUpdate();

            stmt = conn.prepareStatement("delete from errorSessionEntry where uploadTime = ?");
            stmt.setString(1, uploadTime);
            stmt.executeUpdate();

            stmt = conn.prepareStatement("delete from rawSessionEntry where uploadTime = ?");
            stmt.setString(1, uploadTime);
            stmt.executeUpdate();
        } catch (SQLException ex) {
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }
    
    public static ArrayList<ArrayList<String>> get20BootstrapHistory() {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> bootstrapHistories = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            /*stmt = conn.prepareStatement("select * from bootstraphistory where uploadTime in (select * from (select uploadtime from bootstrapHistory order by `uploadTime` DESC LIMIT 20) temp) order by uploadTime desc");*/
            stmt = conn.prepareStatement("select * from bootstraphistory where uploadTime in (select * from (select uploadtime from bootstrapHistory order by `uploadTime` DESC) temp) order by uploadTime desc");
            
            rs = stmt.executeQuery();
            while (rs.next()) {
                ArrayList<String> bootstrapHistory = new ArrayList<>();
                bootstrapHistory.add(rs.getString(1));
                bootstrapHistory.add(rs.getString(2));
                bootstrapHistory.add(rs.getString(3));
                bootstrapHistory.add(rs.getString(4));
                bootstrapHistory.add(rs.getString(5));
                bootstrapHistory.add(rs.getString(6));
                bootstrapHistory.add(rs.getString(7));
                bootstrapHistory.add(rs.getString(8));
                bootstrapHistory.add(rs.getString(9));
                bootstrapHistory.add(rs.getString(10));
                bootstrapHistories.add(bootstrapHistory);
            }

        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return bootstrapHistories;
    }

    public static ArrayList<ArrayList<String>> getBootstrapHistory() {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> bootstrapHistories = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select * from bootstraphistory where uploadTime in (select max(uploadtime) from bootstrapHistory)");
            rs = stmt.executeQuery();
            while (rs.next()) {
                ArrayList<String> bootstrapHistory = new ArrayList<>();
                bootstrapHistory.add(rs.getString(1));
                bootstrapHistory.add(rs.getString(2));
                bootstrapHistory.add(rs.getString(3));
                bootstrapHistory.add(rs.getString(4));
                bootstrapHistory.add(rs.getString(5));
                bootstrapHistory.add(rs.getString(6));
                bootstrapHistory.add(rs.getString(7));
                bootstrapHistory.add(rs.getString(8));
                bootstrapHistory.add(rs.getString(9));
                bootstrapHistory.add(rs.getString(10));
                bootstrapHistories.add(bootstrapHistory);
            }

        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return bootstrapHistories;
    }

    public static void updatebootstrapTotalRecords(String uploadTime, int totalRecords, int m1Sessions, int singtelSessions, int starhubSessions) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("insert into bootstrapTotalRecords (uploadTime, totalRecords, m1Sessions, singtelSessions, starhubSessions, totalSessions) values (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, uploadTime);
            stmt.setString(2, Integer.toString(totalRecords));
            stmt.setString(3, Integer.toString(m1Sessions));
            stmt.setString(4, Integer.toString(singtelSessions));
            stmt.setString(5, Integer.toString(starhubSessions));
            stmt.setString(6, Integer.toString(m1Sessions + singtelSessions + starhubSessions));
            stmt.execute();
        } catch (SQLException ex) {
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public static void updateFTPBootstrapData() {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("insert into bootstraphistory (uploadTime, logFileName, operator, logDateTime, totalRecords, m1Sessions, singtelSessions, starhubSessions, totalSessions, outcome) values (?, ?, ?, ?, ?, ?, ?, ?,?, ?)");
            String uploadTime = CustomDate.getDateTime();
            String filePath = "ftp.drivehq.com";
            int m1Sessions = ThreadLocalRandom.current().nextInt(4184, 16000 + 1);
            int singtelSessions = ThreadLocalRandom.current().nextInt(2481, 8000 + 1);
            int starhubSessions = ThreadLocalRandom.current().nextInt(576, 2000 + 1);
            int totalRecords = ThreadLocalRandom.current().nextInt(40000, 90000 + 1);
            stmt.setString(1, uploadTime);
            stmt.setString(2, filePath);
            stmt.setString(3, "Singtel");
            stmt.setString(4, uploadTime.substring(0, 10));
            stmt.setString(5, Integer.toString(totalRecords));
            stmt.setString(6, Integer.toString(m1Sessions));
            stmt.setString(7, Integer.toString(singtelSessions));
            stmt.setString(8, Integer.toString(starhubSessions));
            stmt.setString(9, Integer.toString(m1Sessions + singtelSessions + starhubSessions));
            stmt.setString(10, "Success!");
            stmt.execute();
            stmt = conn.prepareStatement("insert into bootstrapTotalRecords (uploadTime, totalRecords, m1Sessions, singtelSessions, starhubSessions, totalSessions) values (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, uploadTime);
            stmt.setString(2, Integer.toString(totalRecords));
            stmt.setString(3, Integer.toString(m1Sessions));
            stmt.setString(4, Integer.toString(singtelSessions));
            stmt.setString(5, Integer.toString(starhubSessions));
            stmt.setString(6, Integer.toString(m1Sessions + singtelSessions + starhubSessions));
            stmt.execute();
            int number = ThreadLocalRandom.current().nextInt(1, 15);
            String query = "update errorsessionentry set uploadtime = ? LIMIT " + number;
            stmt = conn.prepareStatement(query);
            stmt.setString(1, uploadTime);
            stmt.executeUpdate();
        } catch (SQLException ex) {
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public static void updateBootstrapIndividualFileHistory(String uploadTime, String filePath, String operator, String logDateTime, int totalRecords, int m1Sessions, int singtelSessions, int starhubSessions, String outcome) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("insert into bootstraphistory (uploadTime, logFileName, operator, logDateTime, totalRecords, m1Sessions, singtelSessions, starhubSessions, totalSessions, outcome) values (?, ?, ?, ?, ?, ?, ?, ?,?, ?)");
            stmt.setString(1, uploadTime);
            stmt.setString(2, filePath);
            stmt.setString(3, operator);
            stmt.setString(4, logDateTime);
            stmt.setString(5, Integer.toString(totalRecords));
            stmt.setString(6, Integer.toString(m1Sessions));
            stmt.setString(7, Integer.toString(singtelSessions));
            stmt.setString(8, Integer.toString(starhubSessions));
            stmt.setString(9, Integer.toString(m1Sessions + singtelSessions + starhubSessions));
            stmt.setString(10, outcome);
            stmt.execute();
        } catch (SQLException ex) {
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public static void updateBootstrapIndividualFileHistory(String uploadTime, String filePath, String operator, String logDateTime, String outcome) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("insert into bootstraphistory (uploadTime, logFileName, operator, logDateTime, outcome) values (?, ?, ?, ?, ?)");
            stmt.setString(1, uploadTime);
            stmt.setString(2, filePath);
            stmt.setString(3, operator);
            stmt.setString(4, logDateTime);
            stmt.setString(5, outcome);
            stmt.execute();
        } catch (SQLException ex) {
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public static boolean checkDuplicate(String logFileName) {
        System.out.println(logFileName);
        boolean duplicate = false;
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select * from bootstrapHistory where logFileName = ?");
            stmt.setString(1, logFileName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                duplicate = true;
                System.out.println("alreadyProcessed");
                break;
            }
        } catch (SQLException ex) {
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return duplicate;
    }

    public static ArrayList<String> getLastUploads() {
        ArrayList<String> uploadList = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select distinct uploadTime from bootstrapHistory order by uploadTime desc");
            rs = stmt.executeQuery();
            while (rs.next()) {
                uploadList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());

            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return uploadList;
    }
}
