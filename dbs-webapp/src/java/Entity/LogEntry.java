/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author jeremylimys93
 */
public class LogEntry {

    private UUID uuid; // added for identification purposes
    private String acctStatusType; // Acct-Status-Type = Stop
    private String acctSessionId; // Acct-Session-Id = "574DB2AA-39531000"
    private String userName; // User-Name = "91e794c92f-d766-4324-8863-3495770f512b_1525037903195492@wlan.mnc003.mcc525.3gppnetwork.org"
    private String nasIdentifier; // NAS-Identifier = "2C-5D-93-39-53-10"
    private String calledStationId; // Called-Station-Id = "2C-5D-93-39-53-10:Wireless@SGx"
    private String callingStationId; // Calling-Station-Id = "BC-92-6B-34-C6-B2"
    private String ruckusSSID; // Ruckus-SSID = "Wireless@SGx"
    private String acctDelayTime; // Acct-Delay-Time = 0
    private String timestamp; // Timestamp = 1464710400
    private String connectInfo; // Connect-Info = "CONNECT 802.11a/n"               NOTE: only keep "802.11" onward, If no info, just leave it blank
    private String classs; // Class = "wsg_2048"
    private String tunnelPrivateGroupID;
    private String acctSessionTime; // Acct-Session-Time = 597
    private String acctInputPackets; // Acct-Input-Packets = 16
    private String acctOutputPackets; // Acct-Output-Packets = 22
    private String acctInputOctets; // Acct-Input-Octets = 2077
    private String acctOutputOctets; // Acct-Output-Octets = 5632
    private String acctTerminateCause; // Acct-Terminate-Cause = Idle-Timeout
    private String acctMultiSessionId; // Acct-Multi-Session-Id = "6caab3ecb03cbc926b34c6b2574db03e8bcf"
    private String acctLinkCount; // Acct-Link-Count = 7
    private String acctAuthentic; // Acct-Authentic = RADIUS
    private String airespaceWLANId; // Airespace-WLAN-Id = 10                          NOTE: some AP don't have this "variable"
    private String arubaEssidName; // Aruba-Essid-Name = "Wireless@SGx"             NOTE: some AP don't have this "variable"
    private String arubaLocationId; // Aruba-Location-Id = "WCFT-L1-AP02"           NOTE: some AP don't have this "variable"
    private String arubaUserRole; // Aruba-User-Role = "authenticated"              NOTE: some AP don't have this "variable"
    private String arubaUserVlan; // Aruba-User-Vlan = 520                             NOTE: some AP don't have this "variable"
    private String ruckusStaRSSI; // Ruckus-Sta-RSSI = 20                           NOTE: some AP don't have this "variable"
    private String ssid; // if calledStationId is in the format of "22-59-A0-CC-AE-BE:Wireless@SGx", "Wireless@SGx" shall be put into "SSID" column              NOTE: some AP don't have this "variable"
    // REMOVED private String operatorName; // Operator-Name = "StarHub"                           NOTE: some AP don't have this "variable"
    private String operatorRadius;
    private String uploadTime;

    // newly added variables
    private String authMethod;
    private String accountOperator;
    private String logFileName;

    public LogEntry(String acctStatusType, String acctSessionId, String userName, String nasIdentifier,
            String calledStationId, String callingStationId, String ruckusSSID, String acctDelayTime, String timestamp,
            String connectInfo, String classs, String tunnelPrivateGroupID, String acctSessionTime,
            String acctInputPackets, String acctOutputPackets, String acctInputOctets, String acctOutputOctets,
            String acctTerminateCause, String acctMultiSessionId, String acctLinkCount, String acctAuthentic,
            String airespaceWLANId, String arubaEssidName, String arubaLocationId, String arubaUserRole,
            String arubaUserVlan, String ruckusStaRSSI, String ssid, String operatorRadius, String authMethod, String accountOperator,String uploadTime) {

        this.uuid = UUID.randomUUID();
        this.acctStatusType = acctStatusType;
        this.acctSessionId = acctSessionId;
        this.userName = userName;
        this.nasIdentifier = nasIdentifier;
        this.calledStationId = calledStationId;
        this.callingStationId = callingStationId;
        this.ruckusSSID = ruckusSSID;
        this.acctDelayTime = acctDelayTime;
        this.timestamp = timestamp;
        this.connectInfo = connectInfo;
        this.classs = classs;
        this.tunnelPrivateGroupID = tunnelPrivateGroupID;
        this.acctSessionTime = acctSessionTime;
        this.acctInputPackets = acctInputPackets;
        this.acctOutputPackets = acctOutputPackets;
        this.acctInputOctets = acctInputOctets;
        this.acctOutputOctets = acctOutputOctets;
        this.acctTerminateCause = acctTerminateCause;
        this.acctMultiSessionId = acctMultiSessionId;
        this.acctLinkCount = acctLinkCount;
        this.acctAuthentic = acctAuthentic;
        this.airespaceWLANId = airespaceWLANId;
        this.arubaEssidName = arubaEssidName;
        this.arubaLocationId = arubaLocationId;
        this.arubaUserRole = arubaUserRole;
        this.arubaUserVlan = arubaUserVlan;
        this.ruckusStaRSSI = ruckusStaRSSI;
        this.ssid = ssid;
        //this.operatorName = operatorName;
        this.operatorRadius = operatorRadius;
        this.uploadTime = uploadTime;
        this.authMethod = authMethod;
        this.accountOperator = accountOperator;
    }

    
    public LogEntry(String uuid, String acctStatusType, String acctSessionId, String userName, String nasIdentifier,
            String calledStationId, String callingStationId, String ruckusSSID, String acctDelayTime, String timestamp,
            String connectInfo, String classs, String tunnelPrivateGroupID, String acctSessionTime,
            String acctInputPackets, String acctOutputPackets, String acctInputOctets, String acctOutputOctets,
            String acctTerminateCause, String acctMultiSessionId, String acctLinkCount, String acctAuthentic,
            String airespaceWLANId, String arubaEssidName, String arubaLocationId, String arubaUserRole,
            String arubaUserVlan, String ruckusStaRSSI, String ssid, String operatorRadius, String authMethod, String accountOperator, String uploadTime, String logFileName) {

        this.uuid = UUID.fromString(uuid);
        this.acctStatusType = acctStatusType;
        this.acctSessionId = acctSessionId;
        this.userName = userName;
        this.nasIdentifier = nasIdentifier;
        this.calledStationId = calledStationId;
        this.callingStationId = callingStationId;
        this.ruckusSSID = ruckusSSID;
        this.acctDelayTime = acctDelayTime;
        this.timestamp = timestamp;
        this.connectInfo = connectInfo;
        this.classs = classs;
        this.tunnelPrivateGroupID = tunnelPrivateGroupID;
        this.acctSessionTime = acctSessionTime;
        this.acctInputPackets = acctInputPackets;
        this.acctOutputPackets = acctOutputPackets;
        this.acctInputOctets = acctInputOctets;
        this.acctOutputOctets = acctOutputOctets;
        this.acctTerminateCause = acctTerminateCause;
        this.acctMultiSessionId = acctMultiSessionId;
        this.acctLinkCount = acctLinkCount;
        this.acctAuthentic = acctAuthentic;
        this.airespaceWLANId = airespaceWLANId;
        this.arubaEssidName = arubaEssidName;
        this.arubaLocationId = arubaLocationId;
        this.arubaUserRole = arubaUserRole;
        this.arubaUserVlan = arubaUserVlan;
        this.ruckusStaRSSI = ruckusStaRSSI;
        this.ssid = ssid;
        //this.operatorName = operatorName;
        this.operatorRadius = operatorRadius;
        this.uploadTime = uploadTime;
        this.authMethod = authMethod;
        this.accountOperator = accountOperator;
        this.logFileName = logFileName;
    }
    

    public String getUUID() {
        return uuid.toString();
    }

    public String getAcctStatusType() {
        return acctStatusType;
    }

    public String getAcctSessionId() {
        return acctSessionId;
    }

    public String getUserName() {
        return userName;
    }

    public String getNasIdentifier() {
        return nasIdentifier;
    }

    public String getCalledStationId() {
        return calledStationId;
    }

    public String getCallingStationId() {
        return callingStationId;
    }

    public String getRuckusSSID() {
        return ruckusSSID;
    }

    public String getAcctDelayTime() {
        return acctDelayTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getConnectInfo() {
        return connectInfo;
    }

    public String getClasss() {
        return classs;
    }

    public String getTunnelPrivateGroupID() {
        return tunnelPrivateGroupID;
    }

    public String getAcctSessionTime() {
        return acctSessionTime;
    }

    public String getAcctInputPackets() {
        return acctInputPackets;
    }

    public String getAcctOutputPackets() {
        return acctOutputPackets;
    }

    public String getAcctInputOctets() {
        return acctInputOctets;
    }

    public String getAcctOutputOctets() {
        return acctOutputOctets;
    }

    public String getAcctTerminateCause() {
        return acctTerminateCause;
    }

    public String getAcctMultiSessionId() {
        return acctMultiSessionId;
    }

    public String getAcctLinkCount() {
        return acctLinkCount;
    }

    public String getAcctAuthentic() {
        return acctAuthentic;
    }

    public String getAirespaceWLANId() {
        return airespaceWLANId;
    }

    public String getArubaEssidName() {
        return arubaEssidName;
    }

    public String getArubaLocationId() {
        return arubaLocationId;
    }

    public String getArubaUserRole() {
        return arubaUserRole;
    }

    public String getArubaUserVlan() {
        return arubaUserVlan;
    }

    public String getRuckusStaRSSI() {
        return ruckusStaRSSI;
    }

    public String getSsid() {
        return ssid;
    }
    
    public String getLogFileName() {
        return logFileName;
    }

    /*
    public String getOperatorName() {
        return operatorName;
    }
     */

    public String getOperatorRadius() {
        return operatorRadius;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    // newly added getMethods
    public String getAuthMethod() {
        return authMethod;
    }

    public String getAccountOperator() {
        return accountOperator;
    }

    // Setters 
    public void setAcctStatusType(String acctStatusType) {
        this.acctStatusType = acctStatusType;
    }

    public void setAcctSessionId(String acctSessionId) {
        this.acctSessionId = acctSessionId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNasIdentifier(String nasIdentifier) {
        this.nasIdentifier = nasIdentifier;
    }

    public void setCalledStationId(String calledStationId) {
        this.calledStationId = calledStationId;
    }

    public void setCallingStationId(String callingStationId) {
        this.callingStationId = callingStationId;
    }

    public void setRuckusSSID(String ruckusSSID) {
        this.ruckusSSID = ruckusSSID;
    }

    public void setAcctDelayTime(String acctDelayTime) {
        this.acctDelayTime = acctDelayTime;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setConnectInfo(String connectInfo) {
        this.connectInfo = connectInfo;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public void setTunnelPrivateGroupID(String tunnelPrivateGroupID) {
        this.tunnelPrivateGroupID = tunnelPrivateGroupID;
    }

    public void setAcctSessionTime(String acctSessionTime) {
        this.acctSessionTime = acctSessionTime;
    }

    public void setAcctInputPackets(String acctInputPackets) {
        this.acctInputPackets = acctInputPackets;
    }

    public void setAcctOutputPackets(String acctOutputPackets) {
        this.acctOutputPackets = acctOutputPackets;
    }

    public void setAcctInputOctets(String acctInputOctets) {
        this.acctInputOctets = acctInputOctets;
    }

    public void setAcctOutputOctets(String acctOutputOctets) {
        this.acctOutputOctets = acctOutputOctets;
    }

    public void setAcctTerminateCause(String acctTerminateCause) {
        this.acctTerminateCause = acctTerminateCause;
    }

    public void setAcctMultiSessionId(String acctMultiSessionId) {
        this.acctMultiSessionId = acctMultiSessionId;
    }

    public void setAcctLinkCount(String acctLinkCount) {
        this.acctLinkCount = acctLinkCount;
    }

    public void setAcctAuthentic(String acctAuthentic) {
        this.acctAuthentic = acctAuthentic;
    }

    public void setAirespaceWLANId(String airespaceWLANId) {
        this.airespaceWLANId = airespaceWLANId;
    }

    public void setArubaEssidName(String arubaEssidName) {
        this.arubaEssidName = arubaEssidName;
    }

    public void setArubaLocationId(String arubaLocationId) {
        this.arubaLocationId = arubaLocationId;
    }

    public void setArubaUserRole(String arubaUserRole) {
        this.arubaUserRole = arubaUserRole;
    }

    public void setArubaUserVlan(String arubaUserVlan) {
        this.arubaUserVlan = arubaUserVlan;
    }

    public void setRuckusStaRSSI(String ruckusStaRSSI) {
        this.ruckusStaRSSI = ruckusStaRSSI;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    /*
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
     */
    public void setOperatorRadius(String operatorRadius) {
        this.operatorRadius = operatorRadius;
    }

    // newly added setMethods
    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public void setAccountOperator(String accountOperator) {
        this.accountOperator = accountOperator;
    }

}
