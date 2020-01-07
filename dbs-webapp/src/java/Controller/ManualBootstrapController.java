/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import DAO.LogEntryDAO;
import DAO.UpdateEntryDAO;
import Entity.LogEntry;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.FileUtils;

/**
 *
 * @author limgeokshanmb
 */
@WebServlet(name = "ManualBootstrapController", urlPatterns = {"/ManualBootstrapController"})
@MultipartConfig
public class ManualBootstrapController extends HttpServlet {

    
    // all the required variables here
    String uuid;
    String acctStatusType;
    String acctSessionId; 
    String userName;
    String nasIdentifier;
    String calledStationId;
    String callingStationId;
    String ruckusSSID;
    String acctDelayTime;
    String timestamp;
    String connectInfo; 
    String classs;
    String tunnelPrivateGroupID;
    String acctSessionTime;
    String acctInputPackets;
    String acctOutputPackets;
    String acctInputOctets;
    String acctOutputOctets;
    String acctTerminateCause;
    String acctMultiSessionId;
    String acctLinkCount;
    String acctAuthentic;
    String airespaceWLANId;
    String arubaEssidName;
    String arubaLocationId;
    String arubaUserRole;
    String arubaUserVlan;
    String ruckusStaRSSI;
    String ssid;
    // removed String operatorName = "N.A"; // Operator-Name = "StarHub"
    // newly added variables 16/10/2018
    String authMethod;
    String accountOperator;
    
    String operatorRadius;
    String uploadTime;
    List<String> erroneousVariables = new ArrayList<>();
    String ignoreData;
    String logFileName;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    HashMap<Integer, HashMap<String, String>> errorsInCSV = new HashMap<>(); // ***
    ArrayList<LogEntry> erroneousEntries = new ArrayList<>(); // ***
    ArrayList<LogEntry> correctedEntries = new ArrayList<>(); // ***
    boolean containsError = false; // ***
    HashMap<String, String> errorsList = new HashMap<>();
    
    String tempFolder;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               
        tempFolder = getServletContext().getRealPath("/") + "temp";
        System.out.println("tempFolder: " + tempFolder);
        
        // If the tempFolder does not exists, create that folder/directory
        File file = new File(tempFolder);

        // if temp directory exist delete it entirely and make a new directory
        if(file.exists()) {
            file.delete();
            file.mkdir();
        } else { //else make a new directory
            file.mkdir();
        }
        
        //Obtain the part called "bin" from request
        Part part = request.getPart("bin");
        System.out.println("name: " + part.getName());
        saveFile(tempFolder, part.getSubmittedFileName(), part);
        
        //String filePath = tempFolder + File.separator + telcoFileName;

        CSVReader reader = new CSVReader(new FileReader(tempFolder + File.separator + part.getSubmittedFileName()), ',');
        String[] tempArray = reader.readNext(); // reads first line (header)
        int line = 0;
        while((tempArray = reader.readNext()) != null) {
            line++;
            uuid = tempArray[0];
            acctStatusType = tempArray[1];
            acctSessionId = tempArray[2];
            userName = tempArray[3];
            nasIdentifier = tempArray[4];
            calledStationId = tempArray[5];
            callingStationId = tempArray[6];
            ruckusSSID = tempArray[7];
            acctDelayTime = tempArray[8];
            timestamp = tempArray[9];
            connectInfo = tempArray[10];
            classs = tempArray[11];
            tunnelPrivateGroupID = tempArray[12];
            acctSessionTime = tempArray[13];
            acctInputPackets = tempArray[14];
            acctOutputPackets = tempArray[15];
            acctInputOctets = tempArray[16];
            acctOutputOctets = tempArray[17];
            acctTerminateCause = tempArray[18];
            acctMultiSessionId = tempArray[19];
            acctLinkCount = tempArray[20];
            acctAuthentic = tempArray[21];
            airespaceWLANId = tempArray[22];
            arubaEssidName = tempArray[23];
            arubaLocationId = tempArray[24];
            arubaUserRole = tempArray[25];
            arubaUserVlan = tempArray[26];
            ruckusStaRSSI = tempArray[27];
            ssid = tempArray[28];
            operatorRadius = tempArray[29];
            uploadTime = tempArray[30];
            authMethod = tempArray[31];
            accountOperator = tempArray[32];
            logFileName = tempArray[33];
            erroneousVariables = convertErroneousVariablesToArrayList(tempArray[34]);
            ignoreData = tempArray[35]; // ignoreData = 1 means IGNORE DATA
            
            if(ignoreData.equals("0")) { // do not ignore this row
                switch(operatorRadius) {
                    case "M1":
                        for (String erroreousVariable: erroneousVariables) {
                            switch(erroreousVariable) {
                                case "acctStatusType": 
                                    validateM1AcctStatusType(acctStatusType);
                                    break;
                                case "acctSessionId":
                                    validateM1AcctSessionId(acctSessionId);
                                    break;
                                case "userName":
                                    validateM1UserName(userName);
                                    break;
                                case "nasIdentifier":
                                    validateM1NasIdentifier(nasIdentifier);
                                    break;
                                case "calledStationId":
                                    validateM1CalledStationId(calledStationId);
                                    break;
                                case "callingStationId":
                                    validateM1CallingStationId(callingStationId);
                                    break;
                                case "acctDelayTime":
                                    validateM1AcctDelayTime(acctDelayTime);
                                    break;
                                case "timestamp":
                                    validateM1Timestamp(timestamp);
                                    break;
                                case "acctSessionTime":
                                    validateM1AcctSessionTime(acctSessionTime);
                                    break;
                                case "acctInputPackets":
                                    validateM1AcctInputPackets(acctInputPackets);
                                    break;
                                case "acctOutputPackets":
                                    validateM1AcctOutputPackets(acctOutputPackets);
                                    break;
                                case "acctInputOctets":
                                    validateM1AcctInputOctets(acctInputOctets);
                                    break;                               
                                case "acctOutputOctets":
                                    validateM1AcctOutputOctets(acctOutputOctets);
                                    break;
                                case "acctTerminateCause":
                                    validateM1AcctTerminateCause(acctTerminateCause);
                                    break;
                                // 14 validation methods for M1
                            }
                        }
                        break;
                    case "Singtel":
                        for (String erroreousVariable: erroneousVariables) {
                            switch(erroreousVariable) {
                                case "acctInputOctets": 
                                    validateSingtelAcctInputOctets(acctInputOctets);
                                    break;
                                case "acctMultiSessionId":
                                    validateSingtelAcctMultiSessionId(acctMultiSessionId);
                                    break;
                                case "acctOutputOctets":
                                    validateSingtelAcctOutputOctets(acctOutputOctets);
                                    break;
                                case "acctSessionId":
                                    validateSingtelAcctSessionId(acctSessionId);
                                    break;
                                case "acctStatusType":
                                    validateSingtelAcctStatusType(acctStatusType);
                                    break;
                                case "authMethod":
                                    validateSingtelAuthMethod(authMethod);
                                    break;
                                case "nasIdentifier":
                                    validateSingtelNasIdentifier(nasIdentifier);
                                    break;
                                case "timestamp":
                                    validateSingtelTimestamp(timestamp);
                                    break;
                                case "userName":
                                    validateSingtelUserName(userName);
                                    break;
                                // 9 validation methods for Singtel
                            }
                        }
                        break;
                    case "Starhub":
                        for (String erroreousVariable: erroneousVariables) {
                            switch(erroreousVariable) {
                                case "acctAuthentic":
                                    validateStarhubAcctAuthentic(acctAuthentic);
                                    break;
                                case "acctDelayTime":
                                    validateStarhubAcctDelayTime(acctDelayTime);
                                    break;
                                case "acctInputOctets":
                                    validateStarhubAcctInputOctets(acctInputOctets);
                                    break;
                                case "acctInputPackets":
                                    validateStarhubAcctInputPackets(acctInputPackets);
                                case "acctLinkCount":
                                    validateStarhubAcctLinkCount(acctLinkCount);
                                    break;
                                case "acctMultiSessionId":
                                    validateStarhubAcctMultiSessionId(acctMultiSessionId);
                                    break;
                                case "acctOutputOctets":
                                    validateStarhubAcctOutputOctets(acctOutputOctets);
                                    break;
                                case "acctOutputPackets":
                                    validateStarhubAcctOutputPackets(acctOutputPackets);
                                    break;
                                case "acctSessionId":
                                    validateStarhubAcctSessionId(acctSessionId);
                                    break;   
                                case "acctSessionTime":
                                    validateStarhubAcctSessionTime(acctSessionTime);
                                    break;    
                                case "acctStatusType": 
                                    validateStarhubAcctStatusType(acctStatusType);
                                    break;
                                case "acctTerminateCause":
                                    validateStarhubAcctTerminateCause(acctTerminateCause);
                                    break;
                                case "airespaceWLANId":
                                    validateStarhubAirespaceWLANId(airespaceWLANId);
                                    break;
                                case "arubaEssidName":
                                    validateStarhubArubaEssidName(arubaEssidName);
                                    break;
                                case "arubaLocationId":
                                    validateStarhubArubaLocationId(arubaLocationId);
                                    break;
                                case "arubaUserRole":
                                    validateStarhubArubaUserRole(arubaUserRole);
                                    break;
                                case "arubaUserVlan":
                                    validateStarhubArubaUserVlan(arubaUserVlan);
                                    break;
                                 case "calledStationId":
                                    validateStarhubCalledStationId(calledStationId);
                                    break;
                                case "callingStationId":
                                    validateStarhubCallingStationId(callingStationId);
                                    break;   
                                case "classs":
                                    validateStarhubClasss(classs);
                                    break;    
                                case "connectInfo":
                                    validateStarhubConnectInfo(timestamp);
                                    break;    
                                case "nasIdentifier":
                                    validateStarhubNasIdentifier(nasIdentifier);
                                    break;    
                                case "ruckusSSID":
                                    validateStarhubRuckusSSID(ruckusSSID);
                                    break;    
                                case "ruckusStaRSSI":
                                    validateStarhubRuckusStaRSSI(ruckusStaRSSI);
                                    break;    
                                case "tunnelPrivateGroupID":
                                    validateStarhubTunnelPrivateGroupID(tunnelPrivateGroupID);
                                    break;    
                                case "userName":
                                    validateStarhubUserName(userName);
                                    break;
                                // 25 validation methods for Starhub
                            }
                        }
                        break;
                }
                LogEntry logEntry = new LogEntry(uuid, acctStatusType, acctSessionId, userName, nasIdentifier,
                    calledStationId, callingStationId, ruckusSSID, acctDelayTime, timestamp,
                    connectInfo, classs, tunnelPrivateGroupID, acctSessionTime,
                    acctInputPackets, acctOutputPackets, acctInputOctets, acctOutputOctets,
                    acctTerminateCause, acctMultiSessionId, acctLinkCount, acctAuthentic,
                    airespaceWLANId, arubaEssidName, arubaLocationId, arubaUserRole,
                    arubaUserVlan, ruckusStaRSSI, ssid, operatorRadius, authMethod, accountOperator, uploadTime,logFileName);
                
                if(containsError) {
                    erroneousEntries.add(logEntry);
                    errorsInCSV.put(line, errorsList); //***
                    System.out.println("errorsList: " + errorsList);
                } else {
                    correctedEntries.add(logEntry);
                }
            }
            resetAllFields();
        }
        // End of File Reading
        try {
            UpdateEntryDAO.updateEntryToErrorDB(erroneousEntries); 
            UpdateEntryDAO.addEntryToCleanDB(correctedEntries); 
            UpdateEntryDAO.deleteEntryInErrorDB(correctedEntries);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        request.setAttribute("bootstrapResult", "Manual Bootstrap has completed successfully!");
        request.setAttribute("errorsInCSV", errorsInCSV);
        RequestDispatcher rd = request.getRequestDispatcher("ManualBootstrap.jsp");
        rd.forward(request, response);
    }
        
    private void resetAllFields() {
        acctStatusType = "N.A";
        acctSessionId = "N.A";
        userName = "N.A";
        nasIdentifier = "N.A";
        calledStationId = "N.A";
        callingStationId = "N.A";
        ruckusSSID = "N.A";
        acctDelayTime = "N.A";
        timestamp = "N.A";
        connectInfo = "N.A";
        classs = "N.A";
        tunnelPrivateGroupID = "N.A";
        acctSessionTime = "N.A";
        acctInputPackets = "N.A";
        acctOutputPackets = "N.A";
        acctInputOctets = "N.A";
        acctOutputOctets = "N.A";
        acctTerminateCause = "N.A";
        acctMultiSessionId = "N.A";
        acctLinkCount = "N.A";
        acctAuthentic = "N.A";
        airespaceWLANId = "N.A";
        arubaEssidName = "N.A";
        arubaLocationId = "N.A";
        arubaUserRole = "N.A";
        arubaUserVlan = "N.A";
        ruckusStaRSSI = "N.A";
        ssid = "N.A";
        authMethod = "N.A";
        accountOperator = "N.A";
        containsError = false; // reset to contains NO error
        //errorsList.clear(); // clears the errorsList
        errorsList = new HashMap<>();
    }
    
    private List convertErroneousVariablesToArrayList(String str) {
        /* Split the string on a delimiter defined as: 
        zero or more whitespace, a literal comma, zero or more whitespace 
        which will place the words into the list and collapse any whitespace 
        between the words and commas.*/
        if(str.isEmpty() || str == null) { // if there is no erroneousVariables, return an empty ArrayList
            return new ArrayList<>();
        }
        List<String> result = Arrays.asList(str.split("\\s*,\\s*"));
        return result;
    }
    
    private void saveFile(String tempPath, String fileName, Part filePart) throws IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        //final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(tempPath + File.separator + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }
    
    private void validateM1UserName(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("userName", field);
            containsError = true;
        }
    }

    private void validateM1Timestamp(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("timestamp", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]{10}", field))) {
            timestamp = "ERROR" + field;
            errorsList.put("timestamp", field);
            containsError = true;
        }
    }

    private void validateM1AcctStatusType(String field) {
        if (field.contains("ERROR")) {
            errorsList.put("acctStatusType", field);
            containsError = true;
        } else if (!(field.equals("Start") || field.equals("Stop"))) {
            acctStatusType = "ERROR" + field;
            errorsList.put("acctStatusType", field);
            containsError = true;
        }
    }

    private void validateM1AcctDelayTime(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctDelayTime", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field))) {
            acctDelayTime = "ERROR" + field;
            errorsList.put("acctDelayTime", field);
            containsError = true;
        }
    }

    private void validateM1AcctInputOctets(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctInputOctets", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field))) {
            acctInputOctets = "ERROR" + field;
            errorsList.put("acctInputOctets", field);
            containsError = true;
        }
    }

    private void validateM1AcctOutputOctets(String field) {
        if (field.contains("ERROR")) {
            errorsList.put("acctOutputOctets", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field))) {
            acctOutputOctets = "ERROR" + field;
            errorsList.put("acctOutputOctets", field);
            containsError = true;
        }
    }

    private void validateM1AcctInputPackets(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctInputPackets", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field))) {
            acctInputPackets = "ERROR" + field;
            errorsList.put("acctInputPackets", field);
            containsError = true;
        }
    }

    private void validateM1AcctOutputPackets(String field) {
        if (field.contains("ERROR")) {
            errorsList.put("acctOutputPackets", field);
            containsError = true;
        } else if (!Pattern.matches("[0-9]+", field)) {
            acctOutputPackets = "ERROR" + field;
            errorsList.put("acctOutputPackets", field);
            containsError = true;
        }
    }

    private void validateM1AcctSessionId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctSessionId", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            acctOutputPackets = "ERROR" + field;
            errorsList.put("acctSessionId", field);
            containsError = true;
        }
    }

    public void validateM1AcctSessionTime(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctSessionTime", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field))) {
            acctSessionTime = "ERROR" + field;
            errorsList.put("acctSessionTime", field);
            containsError = true;
        }
    }

    private void validateM1AcctTerminateCause(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctTerminateCause", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            acctTerminateCause = "ERROR" + field;
            errorsList.put("acctTerminateCause", field);
            containsError = true;
        }
    }

    private void validateM1NasIdentifier(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("nasIdentifier", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            nasIdentifier = "ERROR" + field;
            errorsList.put("nasIdentifier", field);
            containsError = true;
        }
    }

    private void validateM1CalledStationId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("calledStationId", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            calledStationId = "ERROR" + field;
            errorsList.put("calledStationId", field);
            containsError = true;
        }
    }

    private void validateM1CallingStationId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("callingStationId", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", callingStationId))) {
            callingStationId = "ERROR" + field;
            errorsList.put("callingStationId", field);
            containsError = true;
        }
    }

    private void validateSingtelTimestamp(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("timestamp", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]{1,2}[/]{1}[0-9]{1,2}[/]{1}[0-9]{4}[\\s]{1}[0-9]{2}[:]{1}[0-9]{2}[:]{1}[0-9]{2}[\\s]{1}[A-Z]{2}", field))) {
            timestamp = "ERROR" + field;
            errorsList.put("timestamp", field);
            containsError = true;
        } 
    }

    private void validateSingtelNasIdentifier(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("nasIdentifier", field);
            containsError = true;
        } else if (Pattern.matches("[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}", field)) {
            nasIdentifier = "ERROR" + field;
            errorsList.put("nasIdentifier", field);
            containsError = true;
        }
    }

    private void validateSingtelUserName(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("userName", field);
            containsError = true;
        } else if (!(Pattern.matches("[a-z0-9]{40}", field))) {
            userName = "ERROR" + field;
            errorsList.put("userName", field);
            containsError = true;
        }
    }

    private void validateSingtelAuthMethod(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("authMethod", field);
            containsError = true;
        } else if (!(field.equals("EAPSIM") || field.equals("ESSA") || field.equals("SSA") || field.equals("UAM"))) {
            authMethod = "ERROR" + field;
            errorsList.put("authMethod", field);
            containsError = true;
        } 
    }

    private void validateSingtelAcctStatusType(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctStatusType", field);
            containsError = true;
        } else if (!(field.equals("Start") || field.equals("Stop") || field.equals("Interim"))) {
            acctStatusType = "ERROR" + field;
            errorsList.put("acctStatusType", field);
            containsError = true;
        }
    }

    private void validateSingtelAcctSessionId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctSessionId", field);
            containsError = true;
        } else if (!(Pattern.matches("[A-Z0-9]{8}[-]{1}[A-Z0-9]{8}", acctSessionId))) {
            acctSessionId = "ERROR" + field;
            errorsList.put("acctSessionId", field);
            containsError = true;
        }
    }

    private void validateSingtelAcctMultiSessionId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctMultiSessionId", field);
            containsError = true;
        } else if (!(Pattern.matches("[a-z0-9]{36}", field))) {
            acctMultiSessionId = "ERROR" + field;
            errorsList.put("acctMultiSessionId", field);
            containsError = true;
        }
    }

    private void validateSingtelAcctInputOctets(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctInputOctets", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", acctInputOctets))) {
            acctInputOctets = "ERROR" + field;
            errorsList.put("acctInputOctets", field);
            containsError = true;
        }
    }

    private void validateSingtelAcctOutputOctets(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctOutputOctets", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", acctOutputOctets))) {
            acctOutputOctets = "ERROR" + field;
            errorsList.put("acctOutputOctets", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctStatusType(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctStatusType", field);
            containsError = true;
        } else if (!(field.equals("Start") || field.equals("Stop") || field.equals("Interim-Update"))) {
            acctStatusType = "ERROR" + field;
            errorsList.put("acctStatusType", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctSessionId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctSessionId", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            acctSessionId = "ERROR" + field;
            errorsList.put("acctSessionId", field);
            containsError = true;
        }
    }

    private void validateStarhubUserName(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("userName", field);
            containsError = true;
        }
    }

    private void validateStarhubNasIdentifier(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("nasIdentifier", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            nasIdentifier = "ERROR" + field;
            errorsList.put("nasIdentifier", field);
            containsError = true;
        }
    }

    private void validateStarhubCalledStationId(String field) {
        if (field.indexOf(":Wireless@SGx") != -1) { // Should already remove this part, but put just in case
            calledStationId = field.substring(0, field.indexOf(":Wireless@SGx"));
            field = calledStationId;
        }
        if(field.contains("ERROR")) {
            errorsList.put("calledStationId", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            calledStationId = "ERROR" + field;
            errorsList.put("calledStationId", field);
            containsError = true;
        }
    }

    private void validateStarhubCallingStationId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("callingStationId", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            callingStationId = "ERROR" + field;
            errorsList.put("callingStationId", field);
            containsError = true;
        }
    }

    private void validateStarhubRuckusSSID(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("ruckusSSID", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            ruckusSSID = "ERROR" + field;
            errorsList.put("ruckusSSID", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctDelayTime(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctDelayTime", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field))) {
            acctDelayTime = "ERROR" + field;
            errorsList.put("acctDelayTime", field);
            containsError = true;
        }
    }

    private void validateStarhubConnectInfo(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("connectInfo", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S\\s]+", field))) {
            connectInfo = "ERROR" + field;
            errorsList.put("connectInfo", field);
            containsError = true;
        }
    }

    private void validateStarhubClasss(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("classs", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S\\s]+", field))) {
            classs = "ERROR" + field;
            errorsList.put("classs", field);
            containsError = true;
        }
    }

    private void validateStarhubTunnelPrivateGroupID(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("tunnelPrivateGroupID", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            tunnelPrivateGroupID = "ERROR" + field;
            errorsList.put("tunnelPrivateGroupID", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctSessionTime(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctSessionTime", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field) || field.equals("N.A"))) {
            acctSessionTime = "ERROR" + field;
            errorsList.put("acctSessionTime", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctInputOctets(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctInputOctets", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field) || field.equals("N.A"))) {
            acctInputOctets = "ERROR" + field;
            errorsList.put("acctInputOctets", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctOutputOctets(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctOutputOctets", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field) || field.equals("N.A"))) {
            acctOutputOctets = "ERROR" + field;
            errorsList.put("acctOutputOctets", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctInputPackets(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctInputPackets", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field) || field.equals("N.A"))) {
            acctInputPackets = "ERROR" + field;
            errorsList.put("acctInputPackets", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctOutputPackets(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctOutputPackets", field);
            containsError = true;
        } else if (!(Pattern.matches("[0-9]+", field) || field.equals("N.A"))) {
            acctOutputPackets = "ERROR" + field;
            errorsList.put("acctOutputPackets", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctTerminateCause(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctTerminateCause", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S\\s]+", field))) {
            acctTerminateCause = "ERROR" + field;
            errorsList.put("acctTerminateCause", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctMultiSessionId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctMultiSessionId", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            acctMultiSessionId = "ERROR" + field;
            errorsList.put("acctMultiSessionId", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctLinkCount(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctLinkCount", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            acctLinkCount = "ERROR" + field;
            errorsList.put("acctLinkCount", field);
            containsError = true;
        }
    }

    private void validateStarhubAcctAuthentic(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("acctAuthentic", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", acctAuthentic))) {
            acctAuthentic = "ERROR" + field;
            errorsList.put("acctAuthentic", field);
            containsError = true;
        }
    }

    private void validateStarhubAirespaceWLANId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("airespaceWLANId", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", airespaceWLANId))) {
            airespaceWLANId = "ERROR" + field;
            errorsList.put("airespaceWLANId", field);
            containsError = true;
        }
    }

    private void validateStarhubArubaEssidName(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("arubaEssidName", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            arubaEssidName = "ERROR" + field;
            errorsList.put("arubaEssidName", field);
            containsError = true;
        }
    }

    private void validateStarhubArubaLocationId(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("arubaEssidName", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            arubaLocationId = "ERROR" + field;
            errorsList.put("arubaEssidName", field);
            containsError = true;
        }
    }

    private void validateStarhubArubaUserRole(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("arubaUserRole", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            arubaUserRole = "ERROR" + field;
            errorsList.put("arubaUserRole", field);
            containsError = true;
        }
    }

    private void validateStarhubArubaUserVlan(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("arubaUserVlan", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            arubaUserVlan = "ERROR" + field;
            errorsList.put("arubaUserVlan", field);
            containsError = true;
        }
    }

    private void validateStarhubRuckusStaRSSI(String field) {
        if(field.contains("ERROR")) {
            errorsList.put("ruckusStaRSSI", field);
            containsError = true;
        } else if (!(Pattern.matches("[\\S]+", field))) {
            ruckusStaRSSI = "ERROR" + field;
            errorsList.put("ruckusStaRSSI", field);
            containsError = true;
        }
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
