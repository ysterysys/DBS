/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.Hotspot;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author WeiHao
 */
public class HotspotDAO {
    
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    
    public static ArrayList<Hotspot> getLessThanConnectedClients(String lessThanConnectedClientsField, String startDate, String endDate){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Hotspot> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, connectedClients from\n" +
                                        "(SELECT * from hotspotDatabase d\n" +
                                        "where (d.macAddress, d.reportMonth, d.reportYear) in\n" +
                                        "(select macAddress, Upper(Left(Monthname(Max((STR_TO_DATE(reportMonth, '%b')))),3)) as reportMonth, Max(reportYear) as reportYear from hotspotdatabase\n" +
                                        "group by macAddress)) as h right join\n" +
                                        "(SELECT calledStationId, count(distinct callingStationId) as connectedClients FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId\n" +
                                        "having count(distinct callingStationId) < ?) as temp\n" +
                                        "on temp.calledStationId = h.macAddress;");
            stmt.setString(1, lessThanConnectedClientsField);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String apId = rs.getString("apId");
                String deploymentType = rs.getString("deploymentType");
                String locationType = rs.getString("locationType");
                String locationName = rs.getString("locationName");
                String streetAddress = rs.getString("streetAddress");
                String block = rs.getString("block");
                String level = rs.getString("level");
                String description = rs.getString("description");
                String postalCode = rs.getString("postalCode");
                String operator = rs.getString("operator");
                String calledStationId = rs.getString("calledStationId");
                String connectedClients = rs.getString("connectedClients");
                Hotspot hotspot = new Hotspot(apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, connectedClients);
                output.add(hotspot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }

    public static ArrayList<Hotspot> getMoreThanConnectedClients(String moreThanConnectedClientsField, String startDate, String endDate){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Hotspot> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, connectedClients from\n" +
                                        "(SELECT * from hotspotDatabase d\n" +
                                        "where (d.macAddress, d.reportMonth, d.reportYear) in\n" +
                                        "(select macAddress, Upper(Left(Monthname(Max((STR_TO_DATE(reportMonth, '%b')))),3)) as reportMonth, Max(reportYear) as reportYear from hotspotdatabase\n" +
                                        "group by macAddress)) as h right join\n" +
                                        "(SELECT calledStationId, count(distinct callingStationId) as connectedClients FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId\n" +
                                        "having count(distinct callingStationId) > ?) as temp\n" +
                                        "on temp.calledStationId = h.macAddress;");
            stmt.setString(1, moreThanConnectedClientsField);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String apId = rs.getString("apId");
                String deploymentType = rs.getString("deploymentType");
                String locationType = rs.getString("locationType");
                String locationName = rs.getString("locationName");
                String streetAddress = rs.getString("streetAddress");
                String block = rs.getString("block");
                String level = rs.getString("level");
                String description = rs.getString("description");
                String postalCode = rs.getString("postalCode");
                String operator = rs.getString("operator");
                String calledStationId = rs.getString("calledStationId");
                String connectedClients = rs.getString("connectedClients");
                Hotspot hotspot = new Hotspot(apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, connectedClients);
                output.add(hotspot);          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
    
    public static ArrayList<Hotspot> getLessThanUtilization(String lessThanUtilizationField, String startDate, String endDate){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Hotspot> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, utilization from\n" +
                                        "(SELECT apId, h.deploymentType, locationType, locationName, streetAddress, block, level, description, h.postalCode, h.operator, calledStationId, mbps/backhaulSize as utilization from\n" +
                                        "(SELECT * from hotspotDatabase d\n" +
                                        "where (d.macAddress, d.reportMonth, d.reportYear) in\n" +
                                        "(select macAddress, Upper(Left(Monthname(Max((STR_TO_DATE(reportMonth, '%b')))),3)) as reportMonth, Max(reportYear) as reportYear from hotspotdatabase\n" +
                                        "group by macAddress)) as h right join\n" +
                                        "(SELECT calledStationId, max(((greatest(acctInputOctets, acctOutputOctets)*8)/1000000)/acctSessionTime) as mbps FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId) as temp\n" +
                                        "on temp.calledStationId = h.macAddress\n" +
                                        "right join backhaulDatabase b\n" +
                                        "on h.backhaulId = b.backhaulId) as main\n" +
                                        "where utilization < ?;");
            stmt.setString(1, lessThanUtilizationField);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String apId = rs.getString("apId");
                String deploymentType = rs.getString("deploymentType");
                String locationType = rs.getString("locationType");
                String locationName = rs.getString("locationName");
                String streetAddress = rs.getString("streetAddress");
                String block = rs.getString("block");
                String level = rs.getString("level");
                String description = rs.getString("description");
                String postalCode = rs.getString("postalCode");
                String operator = rs.getString("operator");
                String calledStationId = rs.getString("calledStationId");
                String utilization = rs.getString("utilization");
                Hotspot hotspot = new Hotspot(apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, utilization);
                output.add(hotspot);          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
        
    public static ArrayList<Hotspot> getMoreThanUtilization(String moreThanUtilizationField, String startDate, String endDate){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Hotspot> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, utilization from\n" +
                                        "(SELECT apId, h.deploymentType, locationType, locationName, streetAddress, block, level, description, h.postalCode, h.operator, calledStationId, mbps/backhaulSize as utilization from\n" +
                                        "(SELECT * from hotspotDatabase d\n" +
                                        "where (d.macAddress, d.reportMonth, d.reportYear) in\n" +
                                        "(select macAddress, Upper(Left(Monthname(Max((STR_TO_DATE(reportMonth, '%b')))),3)) as reportMonth, Max(reportYear) as reportYear from hotspotdatabase\n" +
                                        "group by macAddress)) as h right join\n" +
                                        "(SELECT calledStationId, max(((greatest(acctInputOctets, acctOutputOctets)*8)/1000000)/acctSessionTime) as mbps FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId) as temp\n" +
                                        "on temp.calledStationId = h.macAddress\n" +
                                        "right join backhaulDatabase b\n" +
                                        "on h.backhaulId = b.backhaulId) as main\n" +
                                        "where utilization > ?;");
            stmt.setString(1, moreThanUtilizationField);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String apId = rs.getString("apId");
                String deploymentType = rs.getString("deploymentType");
                String locationType = rs.getString("locationType");
                String locationName = rs.getString("locationName");
                String streetAddress = rs.getString("streetAddress");
                String block = rs.getString("block");
                String level = rs.getString("level");
                String description = rs.getString("description");
                String postalCode = rs.getString("postalCode");
                String operator = rs.getString("operator");
                String calledStationId = rs.getString("calledStationId");
                String utilization = rs.getString("utilization");
                Hotspot hotspot = new Hotspot(apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, utilization);
                output.add(hotspot);          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
    
    public static ArrayList<String> getAutoConnectedClients(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT avg(connectedClients) as average, stddev(connectedClients) as standardDeviation from\n" +
                                        "(SELECT calledStationId, count(distinct callingStationId) as connectedClients FROM nas.cleansessionentry\n" +
                                        "group by calledStationId) as temp;");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String average = rs.getString("average");
                String standardDeviation = rs.getString("standardDeviation");
                output.add(average);
                output.add(standardDeviation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
    
    public static ArrayList<String> getAutoUtilization(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT avg(utilization) as average, stddev(utilization) as standardDeviation from\n" +
                                        "(SELECT calledStationId, mbps/backhaulSize as utilization from\n" +
                                        "(SELECT * from hotspotDatabase d\n" +
                                        "where (d.macAddress, d.reportMonth, d.reportYear) in\n" +
                                        "(select macAddress, Upper(Left(Monthname(Max((STR_TO_DATE(reportMonth, '%b')))),3)) as reportMonth, Max(reportYear) as reportYear from hotspotdatabase\n" +
                                        "group by macAddress)) as h right join\n" +
                                        "(SELECT calledStationId, max(((greatest(acctInputOctets, acctOutputOctets)*8)/1000000)/acctSessionTime) as mbps FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId) as temp\n" +
                                        "on temp.calledStationId = h.macAddress\n" +
                                        "right join backhaulDatabase b\n" +
                                        "on h.backhaulId = b.backhaulId) as main;");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String average = rs.getString("average");
                String standardDeviation = rs.getString("standardDeviation");
                output.add(average);
                output.add(standardDeviation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
    
    //Backup of Code before historical hotspot database change
    /*
        public static ArrayList<Hotspot> getLessThanConnectedClients(String lessThanConnectedClientsField){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Hotspot> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, connectedClients from\n" +
                                        "hotspotDatabase h right join\n" +
                                        "(SELECT calledStationId, count(distinct callingStationId) as connectedClients FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId\n" +
                                        "having count(distinct callingStationId) < ?) as temp\n" +
                                        "on temp.calledStationId = h.macAddress;");
            stmt.setString(1, lessThanConnectedClientsField);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String apId = rs.getString("apId");
                String deploymentType = rs.getString("deploymentType");
                String locationType = rs.getString("locationType");
                String locationName = rs.getString("locationName");
                String streetAddress = rs.getString("streetAddress");
                String block = rs.getString("block");
                String level = rs.getString("level");
                String description = rs.getString("description");
                String postalCode = rs.getString("postalCode");
                String operator = rs.getString("operator");
                String calledStationId = rs.getString("calledStationId");
                String connectedClients = rs.getString("connectedClients");
                Hotspot hotspot = new Hotspot(apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, connectedClients);
                output.add(hotspot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }

    public static ArrayList<Hotspot> getMoreThanConnectedClients(String moreThanConnectedClientsField){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Hotspot> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, connectedClients from\n" +
                                        "hotspotDatabase h right join\n" +
                                        "(SELECT calledStationId, count(distinct callingStationId) as connectedClients FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId\n" +
                                        "having count(distinct callingStationId) > ?) as temp\n" +
                                        "on temp.calledStationId = h.macAddress;");
            stmt.setString(1, moreThanConnectedClientsField);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String apId = rs.getString("apId");
                String deploymentType = rs.getString("deploymentType");
                String locationType = rs.getString("locationType");
                String locationName = rs.getString("locationName");
                String streetAddress = rs.getString("streetAddress");
                String block = rs.getString("block");
                String level = rs.getString("level");
                String description = rs.getString("description");
                String postalCode = rs.getString("postalCode");
                String operator = rs.getString("operator");
                String calledStationId = rs.getString("calledStationId");
                String connectedClients = rs.getString("connectedClients");
                Hotspot hotspot = new Hotspot(apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, connectedClients);
                output.add(hotspot);          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
    
    public static ArrayList<Hotspot> getLessThanUtilization(String lessThanUtilizationField){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Hotspot> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, utilization from\n" +
                                        "(SELECT apId, h.deploymentType, locationType, locationName, streetAddress, block, level, description, h.postalCode, h.operator, calledStationId, mbps/backhaulSize as utilization from\n" +
                                        "hotspotDatabase h right join\n" +
                                        "(SELECT calledStationId, max(((greatest(acctInputOctets, acctOutputOctets)*8)/1000000)/acctSessionTime) as mbps FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId) as temp\n" +
                                        "on temp.calledStationId = h.macAddress\n" +
                                        "right join backhaulDatabase b\n" +
                                        "on h.backhaulId = b.backhaulId) as main\n" +
                                        "where utilization < ?;");
            stmt.setString(1, lessThanUtilizationField);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String apId = rs.getString("apId");
                String deploymentType = rs.getString("deploymentType");
                String locationType = rs.getString("locationType");
                String locationName = rs.getString("locationName");
                String streetAddress = rs.getString("streetAddress");
                String block = rs.getString("block");
                String level = rs.getString("level");
                String description = rs.getString("description");
                String postalCode = rs.getString("postalCode");
                String operator = rs.getString("operator");
                String calledStationId = rs.getString("calledStationId");
                String utilization = rs.getString("utilization");
                Hotspot hotspot = new Hotspot(apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, utilization);
                output.add(hotspot);          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
        
    public static ArrayList<Hotspot> getMoreThanUtilization(String moreThanUtilizationField){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Hotspot> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, utilization from\n" +
                                        "(SELECT apId, h.deploymentType, locationType, locationName, streetAddress, block, level, description, h.postalCode, h.operator, calledStationId, mbps/backhaulSize as utilization from\n" +
                                        "hotspotDatabase h right join\n" +
                                        "(SELECT calledStationId, max(((greatest(acctInputOctets, acctOutputOctets)*8)/1000000)/acctSessionTime) as mbps FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId) as temp\n" +
                                        "on temp.calledStationId = h.macAddress\n" +
                                        "right join backhaulDatabase b\n" +
                                        "on h.backhaulId = b.backhaulId) as main\n" +
                                        "where utilization > ?;");
            stmt.setString(1, moreThanUtilizationField);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String apId = rs.getString("apId");
                String deploymentType = rs.getString("deploymentType");
                String locationType = rs.getString("locationType");
                String locationName = rs.getString("locationName");
                String streetAddress = rs.getString("streetAddress");
                String block = rs.getString("block");
                String level = rs.getString("level");
                String description = rs.getString("description");
                String postalCode = rs.getString("postalCode");
                String operator = rs.getString("operator");
                String calledStationId = rs.getString("calledStationId");
                String utilization = rs.getString("utilization");
                Hotspot hotspot = new Hotspot(apId, deploymentType, locationType, locationName, streetAddress, block, level, description, postalCode, operator, calledStationId, utilization);
                output.add(hotspot);          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
    
    public static ArrayList<String> getAutoConnectedClients(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT avg(connectedClients) as average, stddev(connectedClients) as standardDeviation from\n" +
                                        "(SELECT calledStationId, count(distinct callingStationId) as connectedClients FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId) as temp;");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String average = rs.getString("average");
                String standardDeviation = rs.getString("standardDeviation");
                output.add(average);
                output.add(standardDeviation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
    
    public static ArrayList<String> getAutoUtilization(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> output = new ArrayList<>();
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT avg(utilization) as average, stddev(utilization) as standardDeviation from\n" +
                                        "(SELECT calledStationId, mbps/backhaulSize as utilization from\n" +
                                        "hotspotDatabase h right join\n" +
                                        "(SELECT calledStationId, max(((greatest(acctInputOctets, acctOutputOctets)*8)/1000000)/acctSessionTime) as mbps FROM nas.cleansessionentry\n" +
                                        "where acctStatusType = 'Stop'\n" +
                                        "group by calledStationId) as temp\n" +
                                        "on temp.calledStationId = h.macAddress\n" +
                                        "right join backhaulDatabase b\n" +
                                        "on h.backhaulId = b.backhaulId) as main");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String average = rs.getString("average");
                String standardDeviation = rs.getString("standardDeviation");
                output.add(average);
                output.add(standardDeviation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return output;
    }
    */
    
}
