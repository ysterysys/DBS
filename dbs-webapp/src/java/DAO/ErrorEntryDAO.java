package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ErrorEntryDAO {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public static ArrayList<String> getListOfAllTimeStampReport() throws SQLException, IOException {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        ArrayList<String> reportTimeList = new ArrayList<>();
        String query = "SELECT DISTINCT uploadTime FROM errorsessionentry a ORDER BY uploadTime DESC";

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                String reportTime = rs.getString(1);
                reportTimeList.add(reportTime);
            }
        } finally {
            ConnectionManager.close(conn, ps, rs);
        }
        return reportTimeList;
    }

    public static void cleantimestamp(String uploadTime, String logFileName) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("INSERT INTO nas.errorsessionentry (uuid, acctStatusType, acctSessionId, userName, nasIdentifier, calledStationId, callingStationId, ruckusSSID, acctDelayTime, timestamp, connectInfo, classs, tunnelPrivateGroupID, acctSessionTime, acctInputPackets, acctOutputPackets, acctInputOctets, acctOutputOctets, acctTerminateCause, acctMultiSessionId, acctLinkCount, acctAuthentic, airespaceWLANId, arubaEssidName, arubaLocationId, arubaUserRole, arubaUserVlan, ruckusStaRSSI, ssid, operatorRadius, uploadTime, authMethod, accountOperator, logFileName)\n"
                    + "SELECT * FROM nas.rawsessionentry\n"
                    + "WHERE uploadTime='" + uploadTime + "' and logFileName ='"+ logFileName +"' and uuid LIKE 'ERROR%' OR acctStatusType LIKE 'ERROR%' OR acctSessionId LIKE 'ERROR%' OR userName LIKE 'ERROR%' OR nasIdentifier LIKE 'ERROR%' OR calledStationId LIKE 'ERROR%' OR callingStationId LIKE 'ERROR%' OR ruckusSSID LIKE 'ERROR%' OR acctDelayTime LIKE 'ERROR%' OR timestamp LIKE 'ERROR%' OR connectInfo LIKE 'ERROR%' OR classs LIKE 'ERROR%' OR tunnelPrivateGroupID LIKE 'ERROR%' OR acctSessionTime LIKE 'ERROR%' OR acctInputPackets LIKE 'ERROR%' OR acctOutputPackets LIKE 'ERROR%' OR acctInputOctets LIKE 'ERROR%' OR acctOutputOctets LIKE 'ERROR%' OR acctTerminateCause LIKE 'ERROR%' OR acctMultiSessionId LIKE 'ERROR%' OR acctLinkCount LIKE 'ERROR%' OR acctAuthentic LIKE 'ERROR%' OR airespaceWLANId LIKE 'ERROR%' OR arubaEssidName LIKE 'ERROR%' OR arubaLocationId LIKE 'ERROR%' OR arubaUserRole LIKE 'ERROR%' OR arubaUserVlan LIKE 'ERROR%' OR ruckusStaRSSI LIKE 'ERROR%' OR ssid LIKE 'ERROR%' OR operatorRadius LIKE 'ERROR%' OR uploadTime LIKE 'ERROR%' OR authMethod LIKE 'ERROR%' OR accountOperator LIKE 'ERROR%';");
            stmt.executeUpdate();

            stmt = conn.prepareStatement("INSERT INTO nas.cleansessionentry (uuid, acctStatusType, acctSessionId, userName, nasIdentifier, calledStationId, callingStationId, ruckusSSID, acctDelayTime, timestamp, connectInfo, classs, tunnelPrivateGroupID, acctSessionTime, acctInputPackets, acctOutputPackets, acctInputOctets, acctOutputOctets, acctTerminateCause, acctMultiSessionId, acctLinkCount, acctAuthentic, airespaceWLANId, arubaEssidName, arubaLocationId, arubaUserRole, arubaUserVlan, ruckusStaRSSI, ssid, operatorRadius, uploadTime, authMethod, accountOperator, logFileName)\n"
                    + "SELECT * FROM nas.rawsessionentry\n"
                    + "WHERE uploadTime='" + uploadTime + "' and logFileName ='"+ logFileName +"';");//and uuid NOT LIKE 'ERROR%' AND acctStatusType NOT LIKE 'ERROR%' AND acctSessionId NOT LIKE 'ERROR%' AND userName NOT LIKE 'ERROR%' AND nasIdentifier NOT LIKE 'ERROR%' AND calledStationId NOT LIKE 'ERROR%' AND callingStationId NOT LIKE 'ERROR%' AND ruckusSSID NOT LIKE 'ERROR%' AND acctDelayTime NOT LIKE 'ERROR%' AND timestamp NOT LIKE 'ERROR%' AND connectInfo NOT LIKE 'ERROR%' AND classs NOT LIKE 'ERROR%' AND tunnelPrivateGroupID NOT LIKE 'ERROR%' AND acctSessionTime NOT LIKE 'ERROR%' AND acctInputPackets NOT LIKE 'ERROR%' AND acctOutputPackets NOT LIKE 'ERROR%' AND acctInputOctets NOT LIKE 'ERROR%' AND acctOutputOctets NOT LIKE 'ERROR%' AND acctTerminateCause NOT LIKE 'ERROR%' AND acctMultiSessionId NOT LIKE 'ERROR%' AND acctLinkCount NOT LIKE 'ERROR%' AND acctAuthentic NOT LIKE 'ERROR%' AND airespaceWLANId NOT LIKE 'ERROR%' AND arubaEssidName NOT LIKE 'ERROR%' AND arubaLocationId NOT LIKE 'ERROR%' AND arubaUserRole NOT LIKE 'ERROR%' AND arubaUserVlan NOT LIKE 'ERROR%' AND ruckusStaRSSI NOT LIKE 'ERROR%' AND ssid NOT LIKE 'ERROR%' AND operatorRadius NOT LIKE 'ERROR%' AND uploadTime NOT LIKE 'ERROR%' AND authMethod NOT LIKE 'ERROR%' AND accountOperator NOT LIKE 'ERROR%';");
            stmt.executeUpdate();

//            stmt = conn.prepareStatement("update nas.errorsessionentry "
//                    + "SET\n"
//                    + "ErroneousVariables = CASE WHEN uuid LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'uuid,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctStatusType LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctStatusType,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctSessionId LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctSessionId,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN userName LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'userName,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN nasIdentifier LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'nasIdentifier,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN calledStationId LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'calledStationId,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN callingStationId LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'callingStationId,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN ruckusSSID LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'ruckusSSID,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctDelayTime LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctDelayTime,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN timestamp LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'timestamp,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN connectInfo LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'connectInfo,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN classs LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'classs,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN tunnelPrivateGroupID LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'tunnelPrivateGroupID,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctSessionTime LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctSessionTime,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctInputPackets LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctInputPackets,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctOutputPackets LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctOutputPackets,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctInputOctets LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctInputOctets,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctOutputOctets LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctOutputOctets,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctTerminateCause LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctTerminateCause,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctMultiSessionId LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctMultiSessionId,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctLinkCount LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctLinkCount,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN acctAuthentic LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'acctAuthentic,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN airespaceWLANId LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'airespaceWLANId,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN arubaEssidName LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'arubaEssidName,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN arubaLocationId LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'arubaLocationId,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN arubaUserRole LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'arubaUserRole,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN arubaUserVlan LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'arubaUserVlan,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN ruckusStaRSSI LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'ruckusStaRSSI,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN ssid LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'ssid,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN operatorRadius LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'operatorRadius,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN uploadTime LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'uploadTime,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN authMethod LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'authMethod,') ELSE ErroneousVariables END,\n"
//                    + "ErroneousVariables = CASE WHEN accountOperator LIKE 'ERROR%' then CONCAT(ErroneousVariables, 'accountOperator,') ELSE ErroneousVariables END");
            stmt.executeUpdate();
        } catch (SQLException e) {
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }
}
