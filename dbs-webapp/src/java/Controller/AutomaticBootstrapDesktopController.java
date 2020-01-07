/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ErrorEntryDAO;
import DAO.LogEntryDAO;
import Entity.LogEntry;
import Utility.Alert;
import Utility.Validation;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.regex.*;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author jeremylimys93
 */
@WebServlet(name = "AutomaticBootstrapDesktopController", urlPatterns = {"/AutomaticBootstrapDesktopController"})
@MultipartConfig
public class AutomaticBootstrapDesktopController extends HttpServlet {

    // all the required variables here
    String acctStatusType = "N.A"; // Acct-Status-Type = Stop
    String acctSessionId = "N.A"; // Acct-Session-Id = "574DB2AA-39531000"
    String userName = "N.A"; // User-Name = "91e794c92f-d766-4324-8863-3495770f512b_1525037903195492@wlan.mnc003.mcc525.3gppnetwork.org"
    String nasIdentifier = "N.A"; // NAS-Identifier = "2C-5D-93-39-53-10"
    String calledStationId = "N.A"; // Called-Station-Id = "2C-5D-93-39-53-10:Wireless@SGx"; final output is "2C-5D-93-39-53-10"
    String callingStationId = "N.A"; // Calling-Station-Id = "BC-92-6B-34-C6-B2"
    String ruckusSSID = "N.A"; // Ruckus-SSID = "Wireless@SGx"
    String acctDelayTime = "N.A"; // Acct-Delay-Time = 0
    String timestamp = "N.A"; // Timestamp = 1464710400
    String connectInfo = "N.A"; // Connect-Info = "CONNECT 802.11a/n"               NOTE: only keep "802.11" onward, If no info, just leave it blank; final value should be "802.11a/n"
    String classs = "N.A"; // Class = "wsg_2048"
    String tunnelPrivateGroupID = "N.A";
    String acctSessionTime = "N.A"; // Acct-Session-Time = 597
    String acctInputPackets = "N.A"; // Acct-Input-Packets = 16
    String acctOutputPackets = "N.A"; // Acct-Output-Packets = 22
    String acctInputOctets = "N.A"; // Acct-Input-Octets = 2077
    String acctOutputOctets = "N.A"; // Acct-Output-Octets = 5632
    String acctTerminateCause = "N.A"; // Acct-Terminate-Cause = Idle-Timeout
    String acctMultiSessionId = "N.A"; // Acct-Multi-Session-Id = "6caab3ecb03cbc926b34c6b2574db03e8bcf"
    String acctLinkCount = "N.A"; // Acct-Link-Count = 7
    String acctAuthentic = "N.A"; // Acct-Authentic = RADIUS
    String airespaceWLANId = "N.A"; // Airespace-WLAN-Id = 10
    String arubaEssidName = "N.A"; // Aruba-Essid-Name = "Wireless@SGx"
    String arubaLocationId = "N.A"; // Aruba-Location-Id = "WCFT-L1-AP02"
    String arubaUserRole = "N.A"; // Aruba-User-Role = "authenticated"
    String arubaUserVlan = "N.A"; // Aruba-User-Vlan = 520
    String ruckusStaRSSI = "N.A"; // Ruckus-Sta-RSSI = 20
    String ssid = "N.A"; // if calledStationId is in the format of "22-59-A0-CC-AE-BE:Wireless@SGx", "Wireless@SGx" shall be put into "SSID" column              NOTE: some AP don't have this "variable"
    // removed String operatorName = "N.A"; // Operator-Name = "StarHub"
    // newly added variables 16/10/2018
    String authMethod = "N.A";
    String accountOperator = "N.A";
    String uploadTime = "";
    String tempFolder;
    String starhubFileName;
    String m1FileName;
    String singtelFileName;
    LinkedHashMap<String, String> starhubMap;
    private static String allFileNames = "";

//    public static String getAllFileNames() {
//        return allFileNames;
//    }
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        starhubMap = new LinkedHashMap<>();
//
//        uploadTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        //AuditDAO.logAction(((User) request.getSession().getAttribute("user")).getUsername(), AuditVariable.ActionTypeBootstrap, AuditVariable.ActionAutoBootstrapSuccess, "Automated Bootstrap has been triggered");
//        tempFolder = getServletContext().getRealPath("/") + "temp";
//        System.out.println("tempFolder: " + tempFolder);
//
//        // If the tempFolder does not exists, create that folder/directory
//        File file = new File(tempFolder);
//
//        // if temp directory exist delete it entirely and make a new directory
//        if (file.exists()) {
//            file.delete();
//            file.mkdir();
//        } else { //else make a new directory
//            file.mkdir();
//        }
//
//        // Creates telco .csv files
//        starhubFileName = tempFolder + File.separator + "starhub.csv";
//        m1FileName = tempFolder + File.separator + "m1.csv";
//        singtelFileName = tempFolder + File.separator + "singtel.csv";
//
//        File starhubFile = new File(starhubFileName);
//        File m1File = new File(m1FileName);
//        File singtelFile = new File(singtelFileName);
//
//        starhubFile.createNewFile();
//        m1File.createNewFile();
//        singtelFile.createNewFile();
//
//        String directory = (String) request.getParameter("directory");
//        File folder = new File(directory);
//        File[] fileList = folder.listFiles();
//
//        for (File subfile : fileList) {
//            // if directory call the same method again
//            if (subfile.isDirectory()) {
//                listAllFiles(subfile);
//            } else {
//                try {
//                    readContent(subfile);
//                } catch (IOException e) {
//                }
//
//            }
//        }
//
//        try {
//            LogEntryDAO.insertCSVToDB(starhubFileName);
//            LogEntryDAO.insertCSVToDB(m1FileName);
//            LogEntryDAO.insertCSVToDB(singtelFileName);
//            ErrorEntryDAO errorEntryDao = new ErrorEntryDAO();
//            errorEntryDao.populateErrorEntries(uploadTime);
//            if (file.exists()) {
//                file.delete();
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            System.out.print("SQL Exception");
//        }
//
//        try {
//            Alert.sendEmail();
//        } catch (MessagingException ex) {
//        }
//
//        System.out.println("Bootstrap completed");
//        request.setAttribute("bootstrapResult", "Bootstrap has completed successfully!");
//        RequestDispatcher rd = request.getRequestDispatcher("DashboardReport.jsp");
//
//        rd.forward(request, response);
//    }
//
//    private void bootstrapM1(List<String[]> m1List) {
//        String operatorRadius = "M1";
//        for (int i = 0; i < m1List.size(); i++) {
//            // Get the row as String array
//            String[] tempArray = m1List.get(i);
//            // Get each of the fields
//            userName = Validation.cleanM1UserName(tempArray[0], authMethod, ssid, accountOperator);
//            timestamp = Validation.cleanM1Timestamp(tempArray[1]);
//            acctStatusType = Validation.cleanM1AcctStatusType(tempArray[2]); // acctRequestType = acctStatusType
//            acctDelayTime = Validation.cleanM1AcctDelayTime(tempArray[3]);
//            acctInputOctets = Validation.cleanM1AcctInputOctets(tempArray[4]);
//            acctOutputOctets = Validation.cleanM1AcctOutputOctets(tempArray[5]);
//            acctInputPackets = Validation.cleanM1AcctInputPackets(tempArray[6]);
//            acctOutputPackets = Validation.cleanM1AcctOutputPackets(tempArray[7]);
//            acctSessionId = tempArray[8];
//            acctSessionTime = Validation.cleanM1AcctSessionTime(tempArray[9]);
//            acctTerminateCause = Validation.cleanM1AcctTerminateCause(tempArray[10]);
//            nasIdentifier = Validation.cleanM1NasIdentifier(tempArray[11]);
//            // String nasIPAddress = tempArray[12];
//            // String nasPort = tempArray[13];
//            // String framedIPAddress = tempArray[14];
//            calledStationId = Validation.cleanM1CalledStationId(tempArray[15]);
//            callingStationId = Validation.cleanM1CallingStationId(tempArray[16]);
//            // String acctInputGigaWords = tempArray[17];
//            // String acctOutputGigaWords = tempArray[18];
//            LogEntry logEntry = new LogEntry(acctStatusType, acctSessionId, userName, nasIdentifier,
//                    calledStationId, callingStationId, ruckusSSID, acctDelayTime, timestamp,
//                    connectInfo, classs, tunnelPrivateGroupID, acctSessionTime,
//                    acctInputPackets, acctOutputPackets, acctInputOctets, acctOutputOctets,
//                    acctTerminateCause, acctMultiSessionId, acctLinkCount, acctAuthentic,
//                    airespaceWLANId, arubaEssidName, arubaLocationId, arubaUserRole,
//                    arubaUserVlan, ruckusStaRSSI, ssid, operatorRadius, authMethod, accountOperator, uploadTime);
//            try {
//                LogEntryDAO.writeEntryToCSV(logEntry, m1FileName);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            resetAllFields();
//        }
//    }
//
//    private void bootstrapSingtel(List<String[]> singtelList) {
//        String operatorRadius = "Singtel";
//        // Include code to skip first line (header)
//        for (int i = 1; i < singtelList.size(); i++) {
//            // Get the row as String array
//            String[] tempArray = singtelList.get(i);
//            //timestamp = cleanSingtelTimestamp(tempArray[0] + " " + tempArray[1]); // check if need convert or anything
//            timestamp = tempArray[0] + " " + tempArray[1];
//            nasIdentifier = Validation.cleanSingtelNasIdentifier(tempArray[2]);
//            calledStationId = Validation.cleanCallingStationId(tempArray[2]);
//            userName = Validation.cleanSingtelUserName(tempArray[3]);
//            callingStationId = Validation.cleanCallingStationId(tempArray[4]);
//            authMethod = Validation.cleanSingtelAuthMethod(tempArray[5]);
//            acctStatusType = Validation.cleanSingtelAcctStatusType(tempArray[6]); // Record-Type = Acct-Status-Type
//            acctSessionId = tempArray[7];
//            acctMultiSessionId = tempArray[8];
//            acctInputOctets = Validation.cleanSingtelAcctInputOctets(tempArray[9]);
//            acctOutputOctets = Validation.cleanSingtelAcctOutputOctets(tempArray[10]);
//            acctSessionTime = tempArray[11];
//
//            LogEntry logEntry = new LogEntry(acctStatusType, acctSessionId, userName, nasIdentifier,
//                    calledStationId, callingStationId, ruckusSSID, acctDelayTime, timestamp,
//                    connectInfo, classs, tunnelPrivateGroupID, acctSessionTime,
//                    acctInputPackets, acctOutputPackets, acctInputOctets, acctOutputOctets,
//                    acctTerminateCause, acctMultiSessionId, acctLinkCount, acctAuthentic,
//                    airespaceWLANId, arubaEssidName, arubaLocationId, arubaUserRole,
//                    arubaUserVlan, ruckusStaRSSI, ssid, operatorRadius, authMethod, accountOperator, uploadTime);
//            try {
//                LogEntryDAO.writeEntryToCSV(logEntry, singtelFileName);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            resetAllFields();
//        }
//    }
//
//    protected void processLineStarhub(String currentLine) {
//        if (currentLine != null && !currentLine.isEmpty()) {
//            if (!currentLine.contains("=")) {
//                currentLine = currentLine.substring(4);
//                currentLine = currentLine.substring(0, currentLine.indexOf("."));
//                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
//                try {
//                    Date date = formatter.parse(currentLine);
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(date);
//                    cal.add(Calendar.HOUR_OF_DAY, 8);
//                    date = cal.getTime();
//                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                    // timestamp = dateFormat.format(date);
//                    starhubMap.put("Timestamp", dateFormat.format(date));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                String result[] = currentLine.trim().split("\\s*=\\s*");
//                String variable = "N.A";
//                String value = "N.A";
//                if (result.length == 2) {
//                    variable = result[0];
//                    value = result[1];
//                } else {
//                    variable = result[0];
//                    value = "ERROR MISSING VALUE";
//                }
//                starhubMap.put(variable, value);
//            }
//        } else {
//            String username = starhubMap.get("User-Name");
//            if (username != null) { // denotes the end of a session entry
//                String operatorRadius = "Starhub";
//
//                LogEntry logEntry = new LogEntry(
//                        (starhubMap.get("Acct-Status-Type") == null ? "N.A" : Validation.cleanAcctStatusType(starhubMap.get("Acct-Status-Type"))),
//                        (starhubMap.get("Acct-Session-Id") == null ? "N.A" : starhubMap.get("Acct-Session-Id")),
//                        (starhubMap.get("User-Name") == null ? "N.A" : Validation.cleanUserName("starhub",starhubMap.get("User-Name"), authMethod, ssid, accountOperator)),
//                        (starhubMap.get("NAS-Identifier") == null ? "N.A" : Validation.cleanStarhubNasIdentifier(starhubMap.get("NAS-Identifier"))),
//                        (starhubMap.get("Called-Station-Id") == null ? "N.A" : Validation.cleanCalledStationId(starhubMap.get("Called-Station-Id"))),
//                        (starhubMap.get("Calling-Station-Id") == null ? "N.A" : Validation.cleanStarhubCallingStationId(starhubMap.get("Calling-Station-Id"))),
//                        (starhubMap.get("Ruckus-SSID") == null ? "N.A" : starhubMap.get("Ruckus-SSID")),
//                        (starhubMap.get("Acct-Delay-Time") == null ? "N.A" : Validation.cleanAcctDelayTime(starhubMap.get("Acct-Delay-Time"))),
//                        (starhubMap.get("Timestamp") == null ? "N.A" : starhubMap.get("Timestamp")),
//                        (starhubMap.get("Connect-Info") == null ? "N.A" : Validation.cleanStarhubConnectInfo(starhubMap.get("Connect-Info"))),
//                        (starhubMap.get("Class") == null ? "N.A" : starhubMap.get("Class")),
//                        (starhubMap.get("Tunnel-Private-Group-ID") == null ? "N.A" : starhubMap.get("Tunnel-Private-Group-ID")),
//                        (starhubMap.get("Acct-Session-Time") == null ? "N.A" : Validation.cleanAcctSessionTime(starhubMap.get("Acct-Session-Time"))),
//                        (starhubMap.get("Acct-Input-Packets") == null ? "N.A" : Validation.cleanAcctInputPackets(starhubMap.get("Acct-Input-Packets"))),
//                        (starhubMap.get("Acct-Output-Packets") == null ? "N.A" : Validation.cleanAcctOutputPackets(starhubMap.get("Acct-Output-Packets"))),
//                        (starhubMap.get("Acct-Input-Octets") == null ? "N.A" : Validation.cleanAcctInputOctets(starhubMap.get("Acct-Input-Octets"))),
//                        (starhubMap.get("Acct-Output-Octets") == null ? "N.A" : Validation.cleanAcctOutputOctets(starhubMap.get("Acct-Output-Octets"))),
//                        (starhubMap.get("Acct-Terminate-Cause") == null ? "N.A" : Validation.cleanStarhubAcctTerminateCause(starhubMap.get("Acct-Terminate-Cause"))),
//                        (starhubMap.get("Acct-Multi-Session-Id") == null ? "N.A" : starhubMap.get("Acct-Multi-Session-Id")),
//                        (starhubMap.get("Acct-Link-Count") == null ? "N.A" : starhubMap.get("Acct-Link-Count")),
//                        (starhubMap.get("Acct-Authentic") == null ? "N.A" : starhubMap.get("Acct-Authentic")),
//                        (starhubMap.get("Airespace-WLAN-Id") == null ? "N.A" : starhubMap.get("Airespace-WLAN-Id")),
//                        (starhubMap.get("Aruba-Essid-Name") == null ? "N.A" : starhubMap.get("Aruba-Essid-Name")),
//                        (starhubMap.get("Aruba-Location-Id") == null ? "N.A" : starhubMap.get("Aruba-Location-Id")),
//                        (starhubMap.get("Aruba-User-Role") == null ? "N.A" : starhubMap.get("Aruba-User-Role")),
//                        (starhubMap.get("Aruba-User-Vlan") == null ? "N.A" : starhubMap.get("Aruba-User-Vlan")),
//                        (starhubMap.get("Ruckus-Sta-RSSI") == null ? "N.A" : starhubMap.get("Ruckus-Sta-RSSIn")),
//                        ssid, operatorRadius, authMethod, accountOperator, uploadTime);
//                try {
//                    LogEntryDAO.writeEntryToCSV(logEntry, starhubFileName);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                starhubMap.clear();
//            }
//        }
//        return;
//    }
//
//    /* This method resets all fields */
//    private void resetAllFields() {
//        acctStatusType = "N.A";
//        acctSessionId = "N.A";
//        userName = "N.A";
//        nasIdentifier = "N.A";
//        calledStationId = "N.A";
//        callingStationId = "N.A";
//        ruckusSSID = "N.A";
//        acctDelayTime = "N.A";
//        timestamp = "N.A";
//        connectInfo = "N.A";
//        classs = "N.A";
//        tunnelPrivateGroupID = "N.A";
//        acctSessionTime = "N.A";
//        acctInputPackets = "N.A";
//        acctOutputPackets = "N.A";
//        acctInputOctets = "N.A";
//        acctOutputOctets = "N.A";
//        acctTerminateCause = "N.A";
//        acctMultiSessionId = "N.A";
//        acctLinkCount = "N.A";
//        acctAuthentic = "N.A";
//        airespaceWLANId = "N.A";
//        arubaEssidName = "N.A";
//        arubaLocationId = "N.A";
//        arubaUserRole = "N.A";
//        arubaUserVlan = "N.A";
//        ruckusStaRSSI = "N.A";
//        ssid = "N.A";
//        authMethod = "N.A";
//        accountOperator = "N.A";
//    }
//    // Uses listFiles method  
//    public void listAllFiles(File folder) {
//        System.out.println("In listAllfiles(File) method");
//        File[] fileNames = folder.listFiles();
//        for (File file : fileNames) {
//            // if directory call the same method again
//            if (file.isDirectory()) {
//                listAllFiles(file);
//            } else {
//                try {
//                    readContent(file);
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }
//
//    public void readContent(File file) throws IOException {
//        System.out.println("read file " + file.getCanonicalPath());
//        String filePath = file.getCanonicalPath();
//        if ((filePath.contains("M1") || filePath.contains("Singtel")) && !filePath.contains("StarHub")) {
//            try (CSVReader csvReader = new CSVReader(new FileReader(filePath));) {
//                List<String[]> csvLines = csvReader.readAll();
//                if (filePath.contains("M1")) {
//                    bootstrapM1(csvLines);
//                } else if (filePath.contains("Singtel")) {
//                    bootstrapSingtel(csvLines);
//                } else {
//                    System.out.print("Does not belong to any telcos: " + filePath);
//                }
//            }
//
//        } else if (filePath.contains("StarHub")) {
//            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//                String currentLine = br.readLine();
//                while (currentLine != null) {
//                    processLineStarhub(currentLine);
//                    currentLine = br.readLine();
//                    String username = starhubMap.get("User-Name");
//                    if (currentLine == null && (username != null)) {
//                        String operatorRadius = "Starhub";
//
//                        LogEntry logEntry = new LogEntry(
//                                (starhubMap.get("Acct-Status-Type") == null ? "N.A" : Validation.cleanAcctStatusType(starhubMap.get("Acct-Status-Type"))),
//                                (starhubMap.get("Acct-Session-Id") == null ? "N.A" : starhubMap.get("Acct-Session-Id")),
//                                (starhubMap.get("User-Name") == null ? "N.A" : Validation.cleanUserName("starhub",starhubMap.get("User-Name"), authMethod, ssid, accountOperator)),
//                                (starhubMap.get("NAS-Identifier") == null ? "N.A" : Validation.cleanStarhubNasIdentifier(starhubMap.get("NAS-Identifier"))),
//                                (starhubMap.get("Called-Station-Id") == null ? "N.A" : Validation.cleanCalledStationId(starhubMap.get("Called-Station-Id"))),
//                                (starhubMap.get("Calling-Station-Id") == null ? "N.A" : Validation.cleanStarhubCallingStationId(starhubMap.get("Calling-Station-Id"))),
//                                (starhubMap.get("Ruckus-SSID") == null ? "N.A" : starhubMap.get("Ruckus-SSID")),
//                                (starhubMap.get("Acct-Delay-Time") == null ? "N.A" : Validation.cleanAcctDelayTime(starhubMap.get("Acct-Delay-Time"))),
//                                (starhubMap.get("Timestamp") == null ? "N.A" : starhubMap.get("Timestamp")),
//                                (starhubMap.get("Connect-Info") == null ? "N.A" : Validation.cleanStarhubConnectInfo(starhubMap.get("Connect-Info"))),
//                                (starhubMap.get("Class") == null ? "N.A" : Validation.cleanStarhubClasss(starhubMap.get("Class"))),
//                                (starhubMap.get("Tunnel-Private-Group-ID") == null ? "N.A" : starhubMap.get("Tunnel-Private-Group-ID")),
//                                (starhubMap.get("Acct-Session-Time") == null ? "N.A" : Validation.cleanAcctSessionTime(starhubMap.get("Acct-Session-Time"))),
//                                (starhubMap.get("Acct-Input-Packets") == null ? "N.A" : Validation.cleanAcctInputPackets(starhubMap.get("Acct-Input-Packets"))),
//                                (starhubMap.get("Acct-Output-Packets") == null ? "N.A" : Validation.cleanAcctOutputPackets(starhubMap.get("Acct-Output-Packets"))),
//                                (starhubMap.get("Acct-Input-Octets") == null ? "N.A" : Validation.cleanAcctInputOctets(starhubMap.get("Acct-Input-Octets"))),
//                                (starhubMap.get("Acct-Output-Octets") == null ? "N.A" : Validation.cleanAcctOutputOctets(starhubMap.get("Acct-Output-Octets"))),
//                                (starhubMap.get("Acct-Terminate-Cause") == null ? "N.A" : Validation.cleanStarhubAcctTerminateCause(starhubMap.get("Acct-Terminate-Cause"))),
//                                (starhubMap.get("Acct-Multi-Session-Id") == null ? "N.A" : starhubMap.get("Acct-Multi-Session-Id")),
//                                (starhubMap.get("Acct-Link-Count") == null ? "N.A" : starhubMap.get("Acct-Link-Count")),
//                                (starhubMap.get("Acct-Authentic") == null ? "N.A" : starhubMap.get("Acct-Authentic")),
//                                (starhubMap.get("Airespace-WLAN-Id") == null ? "N.A" : starhubMap.get("Airespace-WLAN-Id")),
//                                (starhubMap.get("Aruba-Essid-Name") == null ? "N.A" : starhubMap.get("Aruba-Essid-Name")),
//                                (starhubMap.get("Aruba-Location-Id") == null ? "N.A" : starhubMap.get("Aruba-Location-Id")),
//                                (starhubMap.get("Aruba-User-Role") == null ? "N.A" : starhubMap.get("Aruba-User-Role")),
//                                (starhubMap.get("Aruba-User-Vlan") == null ? "N.A" : starhubMap.get("Aruba-User-Vlan")),
//                                (starhubMap.get("Ruckus-Sta-RSSI") == null ? "N.A" : starhubMap.get("Ruckus-Sta-RSSIn")),
//                                ssid, operatorRadius, authMethod, accountOperator, uploadTime);
//                        try {
//                            LogEntryDAO.writeEntryToCSV(logEntry, starhubFileName);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//                        starhubMap.clear();
//                    }
//                }
//
//            } catch (IOException e) {
//            }
//
//        }
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
