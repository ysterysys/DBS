/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.LogEntry;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author yu xiang
 */
public class UpdateEntryDAO {
       
    public static void updateEntryToErrorDB(ArrayList<LogEntry> list) throws SQLException, IOException {

        Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = null;

        int[] result = null;
        String sql = "update errorsessionentry set acctstatustype=?, acctsessionid=?, username=?, nasidentifier=?, calledstationid=?,"
                + "callingstationid=?, ruckusssid=?, acctdelaytime=?, timestamp=?, connectinfo=?, classs=?, tunnelprivategroupid=?,"
                + "acctsessiontime=?, acctinputpackets=?, acctoutputpackets=?, acctinputoctets=?, acctoutputoctets=?, acctterminatecause=?,"
                + "acctmultisessionid=?, acctlinkcount=?, acctauthentic=?, airespacewlanid=?, arubaessidname=?, arubalocationid=?,"
                + "arubauserrole=?, arubauservlan=?, ruckusstarssi=?, ssid=?, operatorradius=?, uploadtime=?, authmethod=?, accountoperator=?"
                + "where uuid =?";
        
        try {
            stmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for(LogEntry entry: list) {
                stmt.setString(1, entry.getAcctStatusType());
                stmt.setString(2, entry.getAcctSessionId());
                stmt.setString(3, entry.getUserName());
                stmt.setString(4, entry.getNasIdentifier());
                stmt.setString(5, entry.getCalledStationId());
                stmt.setString(6, entry.getCallingStationId());
                stmt.setString(7, entry.getRuckusSSID());
                stmt.setString(8, entry.getAcctDelayTime());
                stmt.setString(9, entry.getTimestamp());
                stmt.setString(10, entry.getConnectInfo());
                stmt.setString(11, entry.getClasss());
                stmt.setString(12, entry.getTunnelPrivateGroupID());
                stmt.setString(13, entry.getAcctSessionTime());
                stmt.setString(14, entry.getAcctInputPackets());
                stmt.setString(15, entry.getAcctOutputPackets());
                stmt.setString(16, entry.getAcctInputOctets());
                stmt.setString(17, entry.getAcctOutputOctets());
                stmt.setString(18, entry.getAcctTerminateCause());
                stmt.setString(19, entry.getAcctMultiSessionId());
                stmt.setString(20, entry.getAcctLinkCount());
                stmt.setString(21, entry.getAcctAuthentic());
                stmt.setString(22, entry.getAirespaceWLANId());
                stmt.setString(23, entry.getArubaEssidName());
                stmt.setString(24, entry.getArubaLocationId());
                stmt.setString(25, entry.getArubaUserRole());
                stmt.setString(26, entry.getArubaUserVlan());
                stmt.setString(27, entry.getRuckusStaRSSI());
                stmt.setString(28, entry.getSsid());
                stmt.setString(29, entry.getOperatorRadius());
                stmt.setString(30, entry.getUploadTime());
                stmt.setString(31, entry.getAuthMethod());
                stmt.setString(32, entry.getAccountOperator());
                
                stmt.setString(33, entry.getUUID());
                stmt.addBatch();
            }
            result = stmt.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            ex.printStackTrace();
        } finally {
            if(conn != null) {
                stmt.close();
                conn.close();
            }
        }
    }
    
    public static void addEntryToCleanDB(ArrayList<LogEntry> list) throws SQLException, IOException {

        Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = null;

        int[] result = null;
        String sql = "insert cleansessionentry set acctstatustype=?, acctsessionid=?, username=?, nasidentifier=?, calledstationid=?,"
                + "callingstationid=?, ruckusssid=?, acctdelaytime=?, timestamp=?, connectinfo=?, classs=?, tunnelprivategroupid=?,"
                + "acctsessiontime=?, acctinputpackets=?, acctoutputpackets=?, acctinputoctets=?, acctoutputoctets=?, acctterminatecause=?,"
                + "acctmultisessionid=?, acctlinkcount=?, acctauthentic=?, airespacewlanid=?, arubaessidname=?, arubalocationid=?,"
                + "arubauserrole=?, arubauservlan=?, ruckusstarssi=?, ssid=?, operatorradius=?, uploadtime=?, authmethod=?, accountoperator=?,"
                + "uuid =?";
        
        try {
            stmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for(LogEntry entry: list) {
                stmt.setString(1, entry.getAcctStatusType());
                stmt.setString(2, entry.getAcctSessionId());
                stmt.setString(3, entry.getUserName());
                stmt.setString(4, entry.getNasIdentifier());
                stmt.setString(5, entry.getCalledStationId());
                stmt.setString(6, entry.getCallingStationId());
                stmt.setString(7, entry.getRuckusSSID());
                stmt.setString(8, entry.getAcctDelayTime());
                stmt.setString(9, entry.getTimestamp());
                stmt.setString(10, entry.getConnectInfo());
                stmt.setString(11, entry.getClasss());
                stmt.setString(12, entry.getTunnelPrivateGroupID());
                stmt.setString(13, entry.getAcctSessionTime());
                stmt.setString(14, entry.getAcctInputPackets());
                stmt.setString(15, entry.getAcctOutputPackets());
                stmt.setString(16, entry.getAcctInputOctets());
                stmt.setString(17, entry.getAcctOutputOctets());
                stmt.setString(18, entry.getAcctTerminateCause());
                stmt.setString(19, entry.getAcctMultiSessionId());
                stmt.setString(20, entry.getAcctLinkCount());
                stmt.setString(21, entry.getAcctAuthentic());
                stmt.setString(22, entry.getAirespaceWLANId());
                stmt.setString(23, entry.getArubaEssidName());
                stmt.setString(24, entry.getArubaLocationId());
                stmt.setString(25, entry.getArubaUserRole());
                stmt.setString(26, entry.getArubaUserVlan());
                stmt.setString(27, entry.getRuckusStaRSSI());
                stmt.setString(28, entry.getSsid());
                stmt.setString(29, entry.getOperatorRadius());
                stmt.setString(30, entry.getUploadTime());
                stmt.setString(31, entry.getAuthMethod());
                stmt.setString(32, entry.getAccountOperator());
                
                stmt.setString(33, entry.getUUID());
                stmt.addBatch();
            }
            result = stmt.executeBatch();
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            ex.printStackTrace();
        } finally {
            if(conn != null) {
                stmt.close();
                conn.close();
            }
        }
    }
    
    public static void deleteEntryInErrorDB(ArrayList<LogEntry> list) throws SQLException, IOException{
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = null;
        int[] result = null;
        
        String sql = "delete errorsessionentry where uuid =?";
        
        try {
            stmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for(LogEntry entry: list) {
                
                stmt.setString(1, entry.getUUID());
                stmt.addBatch();
            }
            stmt.executeBatch();
            conn.commit();
        } catch(SQLException ex) {
            conn.rollback();
            ex.printStackTrace();
        } finally {
            if(conn != null) {
                stmt.close();
                conn.close();
            }
        }
    }
}
