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
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author limgeokshanmb
 */
public class CleanSessionEntryDAO {

    public static ArrayList<String> getSSIDList() {
        ArrayList<String> ssidList = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select distinct ssid from cleansessionentry order by ssid asc");
            // to add headers, and selection criteria
            //stmt.setString(1, fileLocation);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ssidList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return ssidList;
    }

    public static ArrayList<String> getLocationTypeList() {
        ArrayList<String> locationList = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select distinct locationType from hotspotdatabase order by locationType asc");
            // to add headers, and selection criteria
            //stmt.setString(1, fileLocation);
            rs = stmt.executeQuery();
            while (rs.next()) {
                locationList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return locationList;
    }

    public static ArrayList<String> getCategoryList() {
        ArrayList<String> categoryList = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select distinct category from hotspotdatabase order by category asc");
            // to add headers, and selection criteria
            //stmt.setString(1, fileLocation);
            rs = stmt.executeQuery();
            while (rs.next()) {
                categoryList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return categoryList;
    }

    public static ArrayList<String> getAPList() {
        ArrayList<String> list = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select distinct calledStationId from cleansessionentry order by calledStationId asc");
            // to add headers, and selection criteria
            //stmt.setString(1, fileLocation);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return list;
    }

    public static ArrayList<String> getLoginTypeList() {
        ArrayList<String> loginList = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select distinct authMethod from cleansessionentry order by authMethod asc");
            // to add headers, and selection criteria
            //stmt.setString(1, fileLocation);
            rs = stmt.executeQuery();
            while (rs.next()) {
                loginList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return loginList;
    }

    public static String getAPDetails(String startTime, String endTime, String AP, String loginType, String ssidType, String locationType, String category) {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        String csv = "";
        String query = "SELECT calledStationId, "
                + "COUNT(distinct userName), "
                + "COUNT(distinct callingStationId),"
                + "ROUND (sum(acctOutputOctets) * POWER(10,-9),3), "
                + "ROUND (sum(acctInputOctets)*POWER(10,-9),3), "
                + "count(*),"
                + "ROUND ((SUM(acctSessionTime)/3600),3), "
                + "ROUND (AVG(acctSessionTime/60),3), "
                + " ROUND(AVG((acctInputOctets/1000)/(acctSessionTime/60)),1), "
                + "ROUND(AVG((acctOutputOctets/1000)/(acctSessionTime/60)),1) from cleansessionentry left outer join hotspotdatabase on cleansessionentry.calledStationId = hotspotdatabase.macAddress "
                + "where "
                //+ "timestamp >= ? and 
                +"timestamp<=? ";
        if (AP!=null&& (!AP.equals(""))){
            query= query + "and calledStationId = '" +AP+"' ";
        }
        if (loginType!=null &&(!loginType.equals(""))){
            query= query+ "and authMethod = '"+ loginType +"' ";
        }
        if (ssidType!=null && (!ssidType.equals(""))){
            query= query + "and ssid ='" +ssidType+"' ";
        }
        if (locationType!=null && (!locationType.equals(""))){
            query= query+ "and locationType ='" +locationType +"' ";
        }
        if (category!=null && (!category.equals(""))){
            query = query + "and category ='" +category +"' ";
        }

        //+"and calledStationId LIKE ?  and authMethod LIKE ? and ssid LIKE ? and locationType LIKE ? and category LIKE ?"
        query = query + " GROUP BY calledStationId ORDER BY cleansessionentry.calledStationId ASC";

        System.out.println(query);
        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
           // ps.setString(1, startTime);
            ps.setString(1, endTime);
            System.out.println(startTime);
            System.out.println(endTime);
//            ps.setString(1, "%"+AP);
//            ps.setString(2, "%" + loginType);
//            ps.setString(3, "%" + ssidType);
//            ps.setString(4, "%" + locationType);
//            ps.setString(5, "%" + category);
            rs = ps.executeQuery();
            while (rs.next()) {
                csv += rs.getString(1) + ","
                        + rs.getString(2) + ","
                        + rs.getString(3) + ","
                        + rs.getString(4) + ","
                        + rs.getString(5) + ","
                        + rs.getString(6) + ","
                        + rs.getString(7) + ","
                        + rs.getString(8) + ","
                        + rs.getString(9) + ","
                        + rs.getString(10) + "," + (System.getProperty("line.separator"));
            }
            if (csv.equals("")) {
                csv += "No Result Found,No Result Found,No Result Found,No Result Found,No Result Found,No Result Found,No Result Found,No Result Found,No Result Found,No Result Found";
                csv += System.getProperty("line.separator");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR!!!" + ex.toString());
            return null;
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }
        return csv;
    }

}
