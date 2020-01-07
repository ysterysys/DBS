package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportDAO {

    public static String error = "";

    public static String exportErrorData() throws IOException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String csv = "";
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select 'uuid', 'acctStatusType', 'acctSessionId', 'userName', 'nasIdentifier', 'calledStationId', 'callingStationId', 'ruckusSSID', 'acctDelayTime', 'timestamp', 'connectInfo', 'classs', 'tunnelPrivateGroupID', 'acctSessionTime', 'acctInputPackets', 'acctOutputPackets', 'acctInputOctets', 'acctOutputOctets', 'acctTerminateCause', 'acctMultiSessionId', 'acctLinkCount', 'acctAuthentic', 'airespaceWLANId', 'arubaEssidName', 'arubaLocationId', 'arubaUserRole', 'arubaUserVlan', 'ruckusStaRSSI', 'ssid', 'operatorRadius', 'uploadTime', 'authMethod', 'accountOperator', 'logFileName', 'ErroneousVariables', 'ignoreData'"
                    + " union all"
                    + " select uuid, acctStatusType, acctSessionId, userName, nasIdentifier, calledStationId, callingStationId, ruckusSSID, acctDelayTime, timestamp, connectInfo, classs, tunnelPrivateGroupID, acctSessionTime, acctInputPackets, acctOutputPackets, acctInputOctets, acctOutputOctets, acctTerminateCause, acctMultiSessionId, acctLinkCount, acctAuthentic, airespaceWLANId, arubaEssidName, arubaLocationId, arubaUserRole, arubaUserVlan, ruckusStaRSSI, ssid, operatorRadius, uploadTime, authMethod, accountOperator,logFileName, ErroneousVariables, ignoreData"
                    + " from errorsessionentry where ignoreData<>'1'");
            // to add headers, and selection criteria
            //stmt.setString(1, fileLocation);
            rs = stmt.executeQuery();
            while (rs.next()) {
                csv += "\"" + rs.getString(1) + "\"" + ","
                        + "\"" + rs.getString(2) + "\"" + ","
                        + "\"" + rs.getString(3) + "\"" + ","
                        + "\"" + rs.getString(4) + "\"" + ","
                        + "\"" + rs.getString(5) + "\"" + ","
                        + "\"" + rs.getString(6) + "\"" + ","
                        + "\"" + rs.getString(7) + "\"" + ","
                        + "\"" + rs.getString(8) + "\"" + ","
                        + "\"" + rs.getString(9) + "\"" + ","
                        + "\"" + rs.getString(10) + "\"" + ","
                        + "\"" + rs.getString(11) + "\"" + ","
                        + "\"" + rs.getString(12) + "\"" + ","
                        + "\"" + rs.getString(13) + "\"" + ","
                        + "\"" + rs.getString(14) + "\"" + ","
                        + "\"" + rs.getString(15) + "\"" + ","
                        + "\"" + rs.getString(16) + "\"" + ","
                        + "\"" + rs.getString(17) + "\"" + ","
                        + "\"" + rs.getString(18) + "\"" + ","
                        + "\"" + rs.getString(19) + "\"" + ","
                        + "\"" + rs.getString(20) + "\"" + ","
                        + "\"" + rs.getString(21) + "\"" + ","
                        + "\"" + rs.getString(22) + "\"" + ","
                        + "\"" + rs.getString(23) + "\"" + ","
                        + "\"" + rs.getString(24) + "\"" + ","
                        + "\"" + rs.getString(25) + "\"" + ","
                        + "\"" + rs.getString(26) + "\"" + ","
                        + "\"" + rs.getString(27) + "\"" + ","
                        + "\"" + rs.getString(28) + "\"" + ","
                        + "\"" + rs.getString(29) + "\"" + ","
                        + "\"" + rs.getString(30) + "\"" + ","
                        + "\"" + rs.getString(31) + "\"" + ","
                        + "\"" + rs.getString(32) + "\"" + ","
                        + "\"" + rs.getString(33) + "\"" + ","
                        + "\"" + rs.getString(34) + "\"" + ","
                        + "\"" + rs.getString(35) + "\"" + ","
                        + "\"" + rs.getString(36) + "\"" + (System.getProperty("line.separator"));
            }
            return csv;
        } catch (SQLException ex) {
            error = ex.toString();
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }

    public boolean exportCleanData(String fileLocation, String uploadTime) throws SQLException, IOException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("select 'uuid', 'acctStatusType', 'acctSessionId', 'userName', 'nasIdentifier', 'calledStationId', 'callingStationId', 'ruckusSSID', 'acctDelayTime', 'timestamp', 'connectInfo', 'classs', 'tunnelPrivateGroupID', 'acctSessionTime', 'acctInputPackets', 'acctOutputPackets', 'acctInputOctets', 'acctOutputOctets', 'acctTerminateCause', 'acctMultiSessionId', 'acctLinkCount', 'acctAuthentic', 'airespaceWLANId', 'arubaEssidName', 'arubaLocationId', 'arubaUserRole', 'arubaUserVlan', 'ruckusStaRSSI', 'ssid', 'operatorRadius', 'uploadTime', 'authMethod', 'accountOperator'"
                    + " union all"
                    + " select uuid, acctStatusType, acctSessionId, userName, nasIdentifier, calledStationId, callingStationId, ruckusSSID, acctDelayTime, timestamp, connectInfo, classs, tunnelPrivateGroupID, acctSessionTime, acctInputPackets, acctOutputPackets, acctInputOctets, acctOutputOctets, acctTerminateCause, acctMultiSessionId, acctLinkCount, acctAuthentic, airespaceWLANId, arubaEssidName, arubaLocationId, arubaUserRole, arubaUserVlan, ruckusStaRSSI, ssid, operatorRadius, uploadTime, authMethod, accountOperator"
                    + " into outfile ? fields terminated by ',' optionally enclosed by '\"' from rawsessionentry where uploadTime like ?");
            // to add headers, and selection criteria
            // to add headers, and selection criteria
            System.out.println(stmt);
            stmt.setString(1, fileLocation);
            stmt.setString(2, uploadTime + "%");
            rs = stmt.executeQuery();
            return true;
        } catch (SQLException ex) {
            return false;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }
}
