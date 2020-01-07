/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.BootstrapDAO;
import DAO.ErrorEntryDAO;
import DAO.LogEntryDAO;
import Utility.Alert;
import Utility.CustomDate;
import Utility.FileIOUtil;
import Utility.Validation;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author limgeokshanmb
 */
@WebServlet(name = "OptimisedBootstrapController", urlPatterns = {"/OptimisedBootstrapController"})
public class OptimisedBootstrapController extends HttpServlet {

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
    String operatorRadius = "";
    String fileName;
    String tempCleanCsvFileName;
    //String tempErrorCsvFileName;
    String uploadTime;
    String logDateTime;
    String accountOperatorFromFileName = "N.A.";
    int totalRecords = 0;
    int singtelSessions = 0;
    int m1Sessions = 0;
    int starhubSessions = 0;

    int allFilesRecords = 0;
    int allFilesSingtelSessions = 0;
    int allFilesM1Sessions = 0;
    int allFilesStarhubSessions = 0;
    int fileCounter = 0;
    String tempFolderPath;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            initializeVariables();
            uploadTime = CustomDate.getDateTime();
            //creates tempfolder to store temp csv files:
            tempFolderPath = getServletContext().getRealPath("/") + "temp";

            // If the tempFolder does not exists, create that folder/directory
            FileIOUtil.deleteFilesInFolderRecursively(tempFolderPath);

            File file = new File(tempFolderPath);
            // if temp directory exist delete it entirely and make a new directory
            file.mkdir();

            //Loops from given directory folder recursively to read all log files
            String directory = (String) request.getParameter("directory");
            //String directory = "C:\\Users\\limgeokshanmb\\Desktop\\RADIUS samples\\M1";

            File folder = new File(directory);
            File[] fileList = folder.listFiles();
            //loop through individual files
            for (File subfile : fileList) {
                // if it's a directory,  call the same method again
                if (subfile.isDirectory()) {
                    listAllFiles(subfile);
                } else {
                    processRawFile(subfile);
                }
            }

            if (file.exists()) {
                Files.delete(file.toPath());
            }

            boolean fileDeleted = false;
            while (!fileDeleted) {
                try {
                    FileIOUtil.deleteFilesInFolderRecursively(tempFolderPath);
                    fileDeleted = true;
                } catch (java.nio.file.DirectoryNotEmptyException ex) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(OptimisedBootstrapController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
            //Alert.sendEmail();
            System.out.println("Bootstrap completed");

        } catch (IOException ex) {
            Logger.getLogger(OptimisedBootstrapController.class.getName()).log(Level.SEVERE, null, ex);
            //} catch (MessagingException ex) {
            //    Logger.getLogger(OptimisedBootstrapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BootstrapDAO.updatebootstrapTotalRecords(uploadTime, allFilesRecords, allFilesM1Sessions, allFilesSingtelSessions, allFilesStarhubSessions);
        request.setAttribute("bootstrapResult", "Bootstrap has completed successfully!");
        request.getRequestDispatcher("DashboardReport.jsp").forward(request, response);
    }

// Uses listFiles method  -- recursive loops
    private void listAllFiles(File folder) {
        System.out.println("In listAllfiles(File) method");
        File[] fileNames = folder.listFiles();
        for (File file : fileNames) {
            // if directory call the same method again
            if (file.isDirectory()) {
                listAllFiles(file);
            } else {
                processRawFile(file);

            }
        }
    }

    private void processRawFile(File file) {
        try {
            tempCleanCsvFileName = tempFolderPath + File.separator + "tempCleaned" + fileCounter + ".csv";
            //tempErrorCsvFileName = tempFolderPath + File.separator + "tempError" + fileCounter + ".csv";
            System.out.println("read file " + file.getCanonicalPath());
            String filePath = file.getCanonicalPath();
            //check if file has already been processed
            //-- if yes, ignore upload((retrieve from db etc etc), throw error back to user.
            //create a bootstrap history table for user to see past uploads
            fileName = file.getName();
            boolean logFileAlreadyProcessed = BootstrapDAO.checkDuplicate(fileName);
            if (!logFileAlreadyProcessed) {

                //process file as per normal
                logDateTime = "N.A.";
                accountOperatorFromFileName = "N.A.";

                //TO CHECK STARHUB .LOG FILES
                String[] accountOperatorList = {"myrep", "m1", "singtel", "starhub"};
                for (String accountOperatorString : accountOperatorList) {
                    if (fileName.toLowerCase().contains(accountOperatorString)) {
                        accountOperatorFromFileName = accountOperatorString;
                    }
                }

                //CHECK OPERATOR RADIUS OF FOLDER CONTAINING LOG FILES
                operatorRadius = "";
                String parentDirectoryOfFile = Paths.get(filePath).getParent().toString();
                String[] operatorsRadiusList = new String[]{"M1", "Starhub", "Singtel", "MyRep"};
                for (String operator : operatorsRadiusList) {
                    if (parentDirectoryOfFile.toLowerCase().contains(operator.toLowerCase())) {
                        operatorRadius = operator;
                        break;
                    }
                }
                //CHECKS IF FILE ENDS WITH .CSV and .LOG
                if (filePath.endsWith(".csv")) {
                    processCSV(fileName, filePath);
                } else {
                    processLog(fileName, filePath);
                }
                LogEntryDAO.insertCSVToDB(tempCleanCsvFileName);
                //ErrorEntryDAO.cleantimestamp(uploadTime, fileName);
                BootstrapDAO.updateBootstrapIndividualFileHistory(uploadTime, fileName, operatorRadius, logDateTime, totalRecords, m1Sessions, singtelSessions, starhubSessions, "Completed");
            } else {
                BootstrapDAO.updateBootstrapIndividualFileHistory(uploadTime, fileName, operatorRadius, logDateTime, "Failed: Duplicate");
            }
            allFilesRecords += totalRecords;
            allFilesSingtelSessions += singtelSessions;
            allFilesM1Sessions += m1Sessions;
            allFilesStarhubSessions += starhubSessions;
            totalRecords = 0;
            singtelSessions = 0;
            m1Sessions = 0;
            starhubSessions = 0;
            logDateTime = "N.A.";
            fileCounter++;
        } catch (IOException ex) {
            Logger.getLogger(OptimisedBootstrapController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processCSV(String fileName, String filePath) {
        // BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("myFile.csv"));
        if (operatorRadius.toLowerCase().equals("m1")) {
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(filePath), 8192 * 32)));
                    FileWriter fw = new FileWriter(tempCleanCsvFileName, true);
                    CSVWriter writer = new CSVWriter(fw);) {
                int userNameCol = 0;
                int timestampCol = 1;
                int acctStatusTypeCol = 2;
                int acctDelayTimeCol = 3;
                int acctInputOctetCol = 4;
                int acctOutputOctetCol = 5;
                int acctInputPacketsCol = 6;
                int acctOutputPacketsCol = 7;
                int acctSessionIdCol = 8;
                int acctSessionTimeCol = 9;
                int acctTerminateCauseCol = 10;
                int nasIdentifierCol = 11;
                int calledStationIdCol = 15;
                int callingStationIdCol = 16;
                int ruckusSsidCol = -1;
                int connectInfoCol = -1;
                int classCol = -1;
                String removeQuotesRegex = "^[\"']+|[\"']+$";
                String[] line;
                String[] userAuthSsidOperator = new String[4];
                String[] calledstationidSsid = new String[2];
                accountOperator = "N.A.";
                while ((line = csvReader.readNext()) != null) {
                    totalRecords++;
                    if (line[acctStatusTypeCol].replaceAll(removeQuotesRegex, "").toLowerCase().equals("stop")) {

                        userAuthSsidOperator = Validation.cleanUserName(operatorRadius, line[userNameCol].replaceAll(removeQuotesRegex, ""), userAuthSsidOperator);

                        if (userAuthSsidOperator[3].equals("N.A.") && (!accountOperatorFromFileName.equals("N.A."))) {
                            accountOperator = accountOperatorFromFileName;
                        } else {
                            accountOperator = userAuthSsidOperator[3];
                        }

                        if (accountOperator.toLowerCase().equals(operatorRadius.toLowerCase())) {
                            m1Sessions++;
                            if (logDateTime.equals("N.A.")) {
                                logDateTime = Validation.cleanTimestamp(line[timestampCol].replaceAll(removeQuotesRegex, "")).substring(0, 10);
                            }
                            calledstationidSsid = Validation.cleanCalledStationId(line[calledStationIdCol].replaceAll(removeQuotesRegex, ""));
                            writer.writeNext(new String[]{
                                UUID.randomUUID().toString(),
                                Validation.cleanAcctStatusType(line[acctStatusTypeCol].replaceAll(removeQuotesRegex, "")),
                                line[acctSessionIdCol].replaceAll(removeQuotesRegex, ""),
                                userAuthSsidOperator[0],
                                (nasIdentifierCol != -1 ? line[nasIdentifierCol].replaceAll(removeQuotesRegex, "") : "N.A"),
                                calledstationidSsid[0],
                                Validation.cleanCallingStationId(line[callingStationIdCol].replaceAll(removeQuotesRegex, "")),
                                (ruckusSsidCol != -1 ? line[ruckusSsidCol].replaceAll(removeQuotesRegex, "") : "N.A."),
                                (acctDelayTimeCol != -1 ? Validation.cleanAcctDelayTime(line[acctDelayTimeCol].replaceAll(removeQuotesRegex, "")) : "N.A."),
                                Validation.cleanTimestamp(line[timestampCol].replaceAll(removeQuotesRegex, "")),
                                (connectInfoCol != -1 ? line[connectInfoCol].replaceAll(removeQuotesRegex, "") : "N.A."),
                                (classCol != -1 ? line[classCol].replaceAll(removeQuotesRegex, "") : "N.A."),
                                "N.A.", //tunnel private group id
                                Validation.cleanAcctSessionTime(line[acctSessionTimeCol].replaceAll(removeQuotesRegex, "")),
                                Validation.cleanAcctInputPackets(line[acctInputPacketsCol].replaceAll(removeQuotesRegex, "")),
                                Validation.cleanAcctOutputPackets(line[acctOutputPacketsCol].replaceAll(removeQuotesRegex, "")),
                                Validation.cleanAcctInputOctets(line[acctInputOctetCol].replaceAll(removeQuotesRegex, "")),
                                Validation.cleanAcctOutputOctets(line[acctOutputOctetCol].replaceAll(removeQuotesRegex, "")),
                                line[acctTerminateCauseCol],
                                "N.A.",//"Acct-Multi-Session-Id"
                                "N.A.",//Acct-Link-Count
                                "N.A.",//Acct-Authentic
                                "N.A.",//"Airespace-WLAN-Id
                                "N.A.",//"Aruba-Essid-Name
                                "N.A.",//"Aruba-Location-Id"
                                "N.A.",//"Aruba-User-Role"
                                "N.A.",//"Aruba-User-Vlan"
                                "N.A.",//"Ruckus-Sta-RSSI"
                                (userAuthSsidOperator[2].equals("N.A.") ? calledstationidSsid[1] : userAuthSsidOperator[2]),//ssid
                                operatorRadius,
                                uploadTime,
                                userAuthSsidOperator[1],//authmethod
                                accountOperator,//acctoperator
                                fileName});

                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OptimisedBootstrapController.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(OptimisedBootstrapController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("done!!");
        } else if (operatorRadius.toLowerCase().equals("singtel")) {
            SimpleDateFormat singtelFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date;
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(filePath), 8192 * 32)));
                    FileWriter fw = new FileWriter(tempCleanCsvFileName, true);
                    CSVWriter writer = new CSVWriter(fw);) {
                System.out.println("singtelcsv");
                int userNameCol = 3;
                int dateCol = 0;
                int timestampCol = 1;
                //int acctStatusTypeCol = 6; //jasper's csv
                int acctStatusTypeCol = 6; //own test files
                int acctDelayTimeCol = -1;
                int authMethodCol = 5;
                int acctInputOctetCol = 9; //jasper files
                int acctOutputOctetCol = 10; //jasper files
                int acctSessionTimeCol = 11; //jasper files
                int acctInputPacketsCol = -1;
                int acctOutputPacketsCol = -1;
                int acctSessionIdCol = 7;
                int acctTerminateCauseCol = -1;
                int nasIdentifierCol = 2;
                int calledStationIdCol = 2;
                int callingStationIdCol = 4;
                int ruckusSsidCol = -1;
                int connectInfoCol = -1;
                int classCol = -1;
                String removeQuotesRegex = "^[\"']+|[\"']+$";
                String[] line;
                String[] userAuthSsidOperator = new String[4];
                String[] calledstationidSsid = new String[2];
                accountOperator = "N.A.";
                while ((line = csvReader.readNext()) != null) {
                    totalRecords++;
                    //System.out.println(line[acctStatusTypeCol].replaceAll(removeQuotesRegex, "").toLowerCase());
                    if (line[acctStatusTypeCol].replaceAll(removeQuotesRegex, "").toLowerCase().equals("stop")) {
                        userAuthSsidOperator = new String[]{line[userNameCol], line[authMethodCol], "N.A.", "Singtel"}; //imda
                        if (userAuthSsidOperator[3].equals("N.A.") && (!accountOperatorFromFileName.equals("N.A."))) {
                            accountOperator = accountOperatorFromFileName;
                        } else {
                            accountOperator = userAuthSsidOperator[3];
                        }

                        date = singtelFormatter.parse((line[dateCol] + " " + line[timestampCol]).replaceAll(removeQuotesRegex, ""));
                        cal.setTime(date);
                        //cal.add(Calendar.HOUR_OF_DAY, 8);
                        date = cal.getTime();
                        logDateTime = dateFormat.format(date).substring(0, 10);
                        if (userAuthSsidOperator[1].toLowerCase().equals("uam")) {
                            singtelSessions++;
                            calledstationidSsid = Validation.cleanCalledStationId(line[calledStationIdCol].replaceAll(removeQuotesRegex, ""));
                            writer.writeNext(new String[]{
                                UUID.randomUUID().toString(),
                                Validation.cleanAcctStatusType(line[acctStatusTypeCol].replaceAll(removeQuotesRegex, "")),
                                line[acctSessionIdCol].replaceAll(removeQuotesRegex, ""),
                                userAuthSsidOperator[0],
                                "N.A.", //nasidentifierCol
                                calledstationidSsid[0],
                                Validation.cleanCallingStationId(line[callingStationIdCol].replaceAll(removeQuotesRegex, "")),
                                (ruckusSsidCol != -1 ? line[ruckusSsidCol].replaceAll(removeQuotesRegex, "") : "N.A."),
                                (acctDelayTimeCol != -1 ? Validation.cleanAcctDelayTime(line[acctDelayTimeCol].replaceAll(removeQuotesRegex, "")) : "N.A."),
                                dateFormat.format(date),
                                "N.A.", //conectInfoCol
                                "N.A.", //classcol
                                "N.A.", //tunnel private group id
                                Validation.cleanAcctSessionTime(line[acctSessionTimeCol].replaceAll(removeQuotesRegex, "")),
                                "N.A.", //acctInputPacketsCol
                                "N.A.", //accountOutputPacketsCol
                                Validation.cleanAcctInputOctets(line[acctInputOctetCol].replaceAll(removeQuotesRegex, "")),
                                Validation.cleanAcctOutputOctets(line[acctOutputOctetCol].replaceAll(removeQuotesRegex, "")),
                                "N.A.",
                                "N.A.",//"Acct-Multi-Session-Id"
                                "N.A.",//Acct-Link-Count
                                "N.A.",//Acct-Authentic
                                "N.A.",//"Airespace-WLAN-Id
                                "N.A.",//"Aruba-Essid-Name
                                "N.A.",//"Aruba-Location-Id"
                                "N.A.",//"Aruba-User-Role"
                                "N.A.",//"Aruba-User-Vlan"
                                "N.A.",//"Ruckus-Sta-RSSI"
                                (userAuthSsidOperator[2].equals("N.A.") ? calledstationidSsid[1] : userAuthSsidOperator[2]),//ssid
                                operatorRadius,
                                uploadTime,
                                userAuthSsidOperator[1],//authmethod
                                accountOperator,//acctoperator
                                fileName});

                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OptimisedBootstrapController.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(OptimisedBootstrapController.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (ParseException ex) {
                Logger.getLogger(OptimisedBootstrapController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("done!!");

        }
    }

    private void processLog(String fileName, String filePath) {
        SimpleDateFormat singtelFormatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy");
        SimpleDateFormat starhubFormatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss.SSS");
        SimpleDateFormat operatorFormatter =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (operatorRadius.toLowerCase().equals("starhub")){
            operatorFormatter = starhubFormatter;
        }else if (operatorRadius.toLowerCase().equals("singtel")){
            operatorFormatter = singtelFormatter;
        }
        LinkedHashMap<String, String> logMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                FileWriter fw = new FileWriter(tempCleanCsvFileName, true);
                CSVWriter writer = new CSVWriter(fw)) {
            String line;
            String variable = "";
            String value = "";
            String tempValue = "";
            String tempDate = "N.A";
            Date date;
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            boolean skipRecord = false;
            String[] userAuthSsidOperator = new String[4];
            String[] calledstationidSsid = new String[2];

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty() && !line.contains("=")) {
                    //check if it's date
                    try {
                        date = operatorFormatter.parse(line);
                        cal.setTime(date);
                        //cal.add(Calendar.HOUR_OF_DAY, 8);
                        date = cal.getTime();
                        tempDate = dateFormat.format(date);
                        logDateTime = tempDate.substring(0, 10);
                    } catch (ParseException e) {
                    }
                    //if ignore remaining lines for that particular log entry
                } else if (skipRecord && !line.isEmpty()) {
                    continue;
                } else if (skipRecord && line.isEmpty()) {
                    //if end of ignored entry, continue reading
                    skipRecord = false;
                } else if (line.isEmpty() && (!skipRecord) && (logMap.size() > 5)) {
                    //if new line and end of Record and write to db
                    //check if map has smth inside
                    totalRecords++;
                    if (logMap.get("Acct-Status-Type") != null && logMap.get("Acct-Status-Type").toLowerCase().equals("stop")) {
                        userAuthSsidOperator = Validation.cleanUserName(operatorRadius, logMap.get("User-Name"), userAuthSsidOperator);
                        calledstationidSsid = Validation.cleanCalledStationId(logMap.get("Called-Station-Id"));
                        if (userAuthSsidOperator[3].equals("N.A.") && (!accountOperatorFromFileName.equals("N.A."))) {
                            accountOperator = accountOperatorFromFileName;
                        } else {
                            accountOperator = userAuthSsidOperator[3];
                        }
                        //System.out.println(accountOperator + " " + operatorRadius);
                        if (accountOperator.toLowerCase().equals(operatorRadius.toLowerCase())) {
                            if (accountOperator.toLowerCase().equals("m1")) {
                                m1Sessions++;
                            } else if (accountOperator.toLowerCase().equals("singtel")) {
                                singtelSessions++;
                            } else if (accountOperator.toLowerCase().equals("starhub")) {
                                starhubSessions++;
                            }

                            if (logMap.get("Timestamp") == null) {
                                logMap.put("Timestamp", tempDate);
                            }
                            logDateTime = Validation.cleanTimestamp(logMap.get("Timestamp")).substring(0, 10);

                            writer.writeNext(new String[]{
                                UUID.randomUUID().toString(),
                                (logMap.get("Acct-Status-Type")),
                                (logMap.get("Acct-Session-Id") == null ? "N.A" : logMap.get("Acct-Session-Id")),
                                (logMap.get("User-Name") == null ? "N.A" : userAuthSsidOperator[0]),
                                (logMap.get("NAS-Identifier") == null ? "N.A" : logMap.get("NAS-Identifier")),
                                (logMap.get("Called-Station-Id") == null ? "N.A" : calledstationidSsid[0]),
                                (logMap.get("Calling-Station-Id") == null ? "N.A" : Validation.cleanCallingStationId(logMap.get("Calling-Station-Id"))),
                                (logMap.get("Ruckus-SSID") == null ? "N.A" : logMap.get("Ruckus-SSID")),
                                (logMap.get("Acct-Delay-Time") == null ? "N.A" : Validation.cleanAcctDelayTime(logMap.get("Acct-Delay-Time"))),
                                Validation.cleanTimestamp(logMap.get("Timestamp")),
                                (logMap.get("Connect-Info") == null ? "N.A" : logMap.get("Connect-Info")),
                                (logMap.get("Class") == null ? "N.A" : logMap.get("Class")),
                                (logMap.get("Tunnel-Private-Group-ID") == null ? "N.A" : logMap.get("Tunnel-Private-Group-ID")),
                                (logMap.get("Acct-Session-Time") == null ? "N.A" : Validation.cleanAcctSessionTime(logMap.get("Acct-Session-Time"))),
                                (logMap.get("Acct-Input-Packets") == null ? "N.A" : Validation.cleanAcctInputPackets(logMap.get("Acct-Input-Packets"))),
                                (logMap.get("Acct-Output-Packets") == null ? "N.A" : Validation.cleanAcctOutputPackets(logMap.get("Acct-Output-Packets"))),
                                (logMap.get("Acct-Input-Octets") == null ? "N.A" : Validation.cleanAcctInputOctets(logMap.get("Acct-Input-Octets"))),
                                (logMap.get("Acct-Output-Octets") == null ? "N.A" : Validation.cleanAcctOutputOctets(logMap.get("Acct-Output-Octets"))),
                                (logMap.get("Acct-Terminate-Cause") == null ? "N.A" : logMap.get("Acct-Terminate-Cause")),
                                (logMap.get("Acct-Multi-Session-Id") == null ? "N.A" : logMap.get("Acct-Multi-Session-Id")),
                                (logMap.get("Acct-Link-Count") == null ? "N.A" : logMap.get("Acct-Link-Count")),
                                (logMap.get("Acct-Authentic") == null ? "N.A" : logMap.get("Acct-Authentic")),
                                (logMap.get("Airespace-WLAN-Id") == null ? "N.A" : logMap.get("Airespace-WLAN-Id")),
                                (logMap.get("Aruba-Essid-Name") == null ? "N.A" : logMap.get("Aruba-Essid-Name")),
                                (logMap.get("Aruba-Location-Id") == null ? "N.A" : logMap.get("Aruba-Location-Id")),
                                (logMap.get("Aruba-User-Role") == null ? "N.A" : logMap.get("Aruba-User-Role")),
                                (logMap.get("Aruba-User-Vlan") == null ? "N.A" : logMap.get("Aruba-User-Vlan")),
                                (logMap.get("Ruckus-Sta-RSSI") == null ? "N.A" : logMap.get("Ruckus-Sta-RSSIn")),
                                (userAuthSsidOperator[2].equals("N.A.") ? calledstationidSsid[1] : userAuthSsidOperator[2]),//ssid
                                operatorRadius,
                                uploadTime,
                                userAuthSsidOperator[1],//authmethod
                                accountOperator,//acctoperator
                                fileName});
                            logMap.clear();
                        }
                    }
                } else {
                    //process line as usual
                    String result[] = line.trim().split("\\s*=\\s*");
                    if (result.length == 2) {
                        variable = result[0];
                        value = result[1].replaceAll("^[\"']+|[\"']+$", "");
                        tempValue = value.toLowerCase();
                        if (tempValue.contains("start") || tempValue.contains("interim") || tempValue.contains("accounting-on")) {
                            skipRecord = true;
                            totalRecords++;
                            logMap.clear();
                        } else {
                            logMap.put(variable, value);

                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OptimisedBootstrapController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(OptimisedBootstrapController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeVariables() {
        acctStatusType = "N.A"; // Acct-Status-Type = Stop
        acctSessionId = "N.A"; // Acct-Session-Id = "574DB2AA-39531000"
        userName = "N.A"; // User-Name = "91e794c92f-d766-4324-8863-3495770f512b_1525037903195492@wlan.mnc003.mcc525.3gppnetwork.org"
        nasIdentifier = "N.A"; // NAS-Identifier = "2C-5D-93-39-53-10"
        calledStationId = "N.A"; // Called-Station-Id = "2C-5D-93-39-53-10:Wireless@SGx"; final output is "2C-5D-93-39-53-10"
        callingStationId = "N.A"; // Calling-Station-Id = "BC-92-6B-34-C6-B2"
        ruckusSSID = "N.A"; // Ruckus-SSID = "Wireless@SGx"
        acctDelayTime = "N.A"; // Acct-Delay-Time = 0
        timestamp = "N.A"; // Timestamp = 1464710400
        connectInfo = "N.A"; // Connect-Info = "CONNECT 802.11a/n"               NOTE: only keep "802.11" onward, If no info, just leave it blank; final value should be "802.11a/n"
        classs = "N.A"; // Class = "wsg_2048"
        tunnelPrivateGroupID = "N.A";
        acctSessionTime = "N.A"; // Acct-Session-Time = 597
        acctInputPackets = "N.A"; // Acct-Input-Packets = 16
        acctOutputPackets = "N.A"; // Acct-Output-Packets = 22
        acctInputOctets = "N.A"; // Acct-Input-Octets = 2077
        acctOutputOctets = "N.A"; // Acct-Output-Octets = 5632
        acctTerminateCause = "N.A"; // Acct-Terminate-Cause = Idle-Timeout
        acctMultiSessionId = "N.A"; // Acct-Multi-Session-Id = "6caab3ecb03cbc926b34c6b2574db03e8bcf"
        acctLinkCount = "N.A"; // Acct-Link-Count = 7
        acctAuthentic = "N.A"; // Acct-Authentic = RADIUS
        airespaceWLANId = "N.A"; // Airespace-WLAN-Id = 10
        arubaEssidName = "N.A"; // Aruba-Essid-Name = "Wireless@SGx"
        arubaLocationId = "N.A"; // Aruba-Location-Id = "WCFT-L1-AP02"
        arubaUserRole = "N.A"; // Aruba-User-Role = "authenticated"
        arubaUserVlan = "N.A"; // Aruba-User-Vlan = 520
        ruckusStaRSSI = "N.A"; // Ruckus-Sta-RSSI = 20
        ssid = "N.A"; // if calledStationId is in the format of "22-59-A0-CC-AE-BE:Wireless@SGx", "Wireless@SGx" shall be put into "SSID" column              NOTE: some AP don't have this "variable"
        // removed String operatorName = "N.A"; // Operator-Name = "StarHub"
        // newly added variables 16/10/2018
        authMethod = "N.A";
        accountOperator = "N.A";
        operatorRadius = "";
        fileName = "";
        tempCleanCsvFileName = "";
        //tempErrorCsvFileName = "";
        uploadTime = "";
        logDateTime = "";
        accountOperatorFromFileName = "N.A.";
        totalRecords = 0;
        singtelSessions = 0;
        m1Sessions = 0;
        starhubSessions = 0;
        allFilesRecords = 0;
        allFilesSingtelSessions = 0;
        allFilesM1Sessions = 0;
        allFilesStarhubSessions = 0;
        fileCounter = 0;
    }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
