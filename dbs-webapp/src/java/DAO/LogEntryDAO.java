/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.LogEntry;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeremylimys93
 */
public class LogEntryDAO {

    public static void writeEntryToCSV(String[] row, String fileName) {
        //pass to csv writer
        try (FileWriter fw = new FileWriter(fileName, true);
                CSVWriter writer = new CSVWriter(fw);) {
            writer.writeNext(row);
        } catch (IOException ex) {
            Logger.getLogger(LogEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writeEntryToCSV(LogEntry logEntry, String fileName) {
        //pass to csv writer
        try (FileWriter fw = new FileWriter(fileName, true);
                CSVWriter writer = new CSVWriter(fw);) {
            String[] row = new String[]{logEntry.getUUID(), logEntry.getAcctStatusType(), logEntry.getAcctSessionId(),
                logEntry.getUserName(), logEntry.getNasIdentifier(), logEntry.getCalledStationId(),
                logEntry.getCallingStationId(), logEntry.getRuckusSSID(), logEntry.getAcctDelayTime(),
                logEntry.getTimestamp(), logEntry.getConnectInfo(), logEntry.getClasss(),
                logEntry.getTunnelPrivateGroupID(), logEntry.getAcctSessionTime(), logEntry.getAcctInputPackets(),
                logEntry.getAcctOutputPackets(), logEntry.getAcctInputOctets(), logEntry.getAcctOutputOctets(),
                logEntry.getAcctTerminateCause(), logEntry.getAcctMultiSessionId(), logEntry.getAcctLinkCount(),
                logEntry.getAcctAuthentic(), logEntry.getAirespaceWLANId(), logEntry.getArubaEssidName(),
                logEntry.getArubaLocationId(), logEntry.getArubaUserRole(), logEntry.getArubaUserVlan(),
                logEntry.getRuckusStaRSSI(), logEntry.getSsid(), logEntry.getOperatorRadius(), logEntry.getUploadTime(),
                logEntry.getAuthMethod(), logEntry.getAccountOperator()};
            writer.writeNext(row);
        } catch (IOException ex) {
            Logger.getLogger(LogEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertCSVToDB(String fileName) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            fileName = fileName.replace("\\", "\\\\");

            /*String sql = "load data local infile '" + fileName + "' into table rawsessionentry "
                    + " fields terminated by ','"
                    + " enclosed by '\"'"
                    + " lines terminated by '\\n'";*/

            String sql = "load data local infile '" + fileName + "' into table cleansessionentry "
                    + " fields terminated by ','"
                    + " enclosed by '\"'"
                    + " lines terminated by '\\n'";

            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(LogEntryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

    }
}
