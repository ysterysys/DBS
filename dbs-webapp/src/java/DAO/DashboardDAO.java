/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
public class DashboardDAO {

    public static ArrayList<String> getLastBootstrapInformation() {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        ArrayList<String> lastBootstrapInformation = new ArrayList<>();

        String uploadTime = "";
        String totalCount = "";
        String totalError = "";
        String totalSingtel = "0";
        String totalStarhub = "0";
        String totalM1 = "0";
        String totalSession = "0";
        String query = "Select * from "
                + "(SELECT uploadTime, totalRecords, singtelSessions, starhubSessions, m1Sessions,totalSessions from `bootstrapTotalRecords` order by uploadTime desc Limit 1) as Result1,"
                + "(select count(*) from `errorsessionentry` where uploadTime in (SELECT max(uploadTime) FROM `bootstrapTotalRecords`)) as Result2";
        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                uploadTime = rs.getString(1);
                totalCount = rs.getString(2);
                totalError = rs.getString(7);
                totalSingtel = rs.getString(3);
                totalStarhub = rs.getString(4);
                totalM1 = rs.getString(5);
                totalSession = rs.getString(6);
                if (uploadTime == null) {
                    uploadTime = "No bootstrap found!";
                }
                if (totalCount == null) {
                    totalCount = "0";
                }
                if (totalError == null) {
                    totalError = "0";
                }
                if (totalSingtel == null) {
                    totalSingtel = "0";
                }
                if (totalStarhub == null) {
                    totalStarhub = "0";
                }
                if (totalM1 == null) {
                    totalM1 = "0";
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = formatter.parse(uploadTime);
                Format newFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                uploadTime = newFormatter.format(date);
                lastBootstrapInformation.add(uploadTime);
                lastBootstrapInformation.add(totalCount);
                String totalClean = String.valueOf(Integer.parseInt(totalCount) - Integer.parseInt(totalError));
                lastBootstrapInformation.add(totalClean);
                lastBootstrapInformation.add(totalError);
                lastBootstrapInformation.add(totalSingtel);
                lastBootstrapInformation.add(totalStarhub);
                lastBootstrapInformation.add(totalM1);
                lastBootstrapInformation.add(totalSession);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DashboardDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DashboardDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }

        if (lastBootstrapInformation.isEmpty()) {
            System.out.println("cant get info from boot strap history table");
            for (int i = 0; i < 10; i++) {
                lastBootstrapInformation.add("0");
            }
        }
        return lastBootstrapInformation;
    }

    public static TreeMap<String, String> getTotalRecordsGraph(String startTime, String endTime) {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        TreeMap<String, String> getTotalLogsGraph = new TreeMap<>();

        String query = "SELECT DISTINCT(logDateTime),sum(totalRecords) FROM `bootstrapHistory` where logDateTime >= '" + startTime + "' and logDateTime<='" + endTime + "' GROUP BY logDateTime ORDER BY logDateTime asc";

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                String uploadTime = rs.getString(1);
                String count = rs.getString(2);
                getTotalLogsGraph.put(uploadTime, count);
            }

        } catch (Exception ex) {
            Logger.getLogger(ErrorEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }
        return getTotalLogsGraph;
    }

    public static TreeMap<String, String> getTotalSessionsGraph(String startTime, String endTime) {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        TreeMap<String, String> getTotalLogsGraph = new TreeMap<>();

        String query = "SELECT DISTINCT(logDateTime),sum(totalSessions) FROM `bootstrapHistory` where logDateTime >= '" + startTime + "' and logDateTime<='" + endTime + "' GROUP BY logDateTime ORDER BY logDateTime asc";

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                String uploadTime = rs.getString(1);
                String count = rs.getString(2);
                getTotalLogsGraph.put(uploadTime, count);
            }

        } catch (Exception ex) {
            Logger.getLogger(ErrorEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }
        return getTotalLogsGraph;
    }

    public static TreeMap<String, String> getTotalErrorsGraph(String startTime, String endTime) {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        TreeMap<String, String> getTotalErrorsGraph = new TreeMap<>();

        String query = "SELECT DISTINCT(substr(uploadTime,1,10)),COUNT(*) FROM `errorsessionentry` where substr(uploadTime, 1, 10) >= '" + startTime + "' and substr(uploadTime, 1, 10)<='" + endTime + "' GROUP BY substr(uploadTime,1,10) ORDER BY uploadTime asc";

        System.out.println(query + startTime + endTime);
        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                String uploadTime = rs.getString(1).substring(0, 10);
                String count = rs.getString(2);
                getTotalErrorsGraph.put(uploadTime, count);
            }

        } catch (Exception ex) {
            Logger.getLogger(ErrorEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }
        return getTotalErrorsGraph;
    }

    public static TreeMap<String, String> getTotalAPGraphSingtel(String startTime, String endTime) {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        TreeMap<String, String> getTotalAPGraphSingtel = new TreeMap<>();

        String query = "SELECT DISTINCT(logDateTime),sum(singtelSessions) FROM `bootstrapHistory` where logDateTime >= '" + startTime + "' and logDateTime<='" + endTime + "' GROUP BY logDateTime ORDER BY logDateTime asc";

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                String uploadTime = rs.getString(1);
                String count = rs.getString(2);
                getTotalAPGraphSingtel.put(uploadTime, count);
            }

        } catch (Exception ex) {
            Logger.getLogger(ErrorEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }
        return getTotalAPGraphSingtel;
    }

    public static TreeMap<String, String> getTotalAPGraphStarhub(String startTime, String endTime) {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        TreeMap<String, String> getTotalAPGraphStarhub = new TreeMap<>();

        String query = "SELECT DISTINCT(logDateTime),sum(starhubSessions) FROM `bootstrapHistory` where logDateTime >= '" + startTime + "' and logDateTime<='" + endTime + "' GROUP BY logDateTime ORDER BY logDateTime asc";

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                String uploadTime = rs.getString(1);
                String count = rs.getString(2);
                getTotalAPGraphStarhub.put(uploadTime, count);
            }

        } catch (Exception ex) {
            Logger.getLogger(ErrorEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }
        return getTotalAPGraphStarhub;
    }

    public static TreeMap<String, String> getTotalAPGraphM1(String startTime, String endTime) {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        TreeMap<String, String> getTotalAPGraphM1 = new TreeMap<>();

        String query = "SELECT DISTINCT(logDateTime),sum(m1Sessions) FROM `bootstrapHistory` where logDateTime >= '" + startTime + "' and logDateTime<='" + endTime + "' GROUP BY logDateTime ORDER BY logDateTime asc";

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                String uploadTime = rs.getString(1);
                String count = rs.getString(2);
                getTotalAPGraphM1.put(uploadTime, count);
            }

        } catch (Exception ex) {
            Logger.getLogger(ErrorEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }
        return getTotalAPGraphM1;
    }

    public static ArrayList<String> getlast7Days() {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        ArrayList<String> minMaxUploadTime = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Format newFormatter = new SimpleDateFormat("MMMM dd, yyyy");

        String endTime = newFormatter.format(new Date());
        String startTime = newFormatter.format(new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000));
        minMaxUploadTime.add(startTime);
        minMaxUploadTime.add(endTime);

        return minMaxUploadTime;
    }

    public static ArrayList<String> getMinMaxUploadTime() {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        ArrayList<String> minMaxUploadTime = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Format newFormatter = new SimpleDateFormat("MM/dd/yyyy");

        String startTime = "";
        String endTime = "";
        String query = "Select * from "
                + "(SELECT min(uploadTime) from `rawsessionentry`) as Result1,"
                + "(SELECT max(uploadTime) from `rawsessionentry`) as Result2";

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                startTime = rs.getString(1);
                Date date = formatter.parse(startTime);
                startTime = newFormatter.format(date);
                endTime = rs.getString(2);
                date = formatter.parse(endTime);
                endTime = newFormatter.format(date);
                minMaxUploadTime.add(startTime);
                minMaxUploadTime.add(endTime);
            }

        } catch (Exception ex) {
            Logger.getLogger(ErrorEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }
        return minMaxUploadTime;
    }

}
