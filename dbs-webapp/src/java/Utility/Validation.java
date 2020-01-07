/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author jeremy
 */
public class Validation {

    private static String[] operatorList = new String[]{"M1", "Starhub", "Singtel"};

    public static String cleanCallingStationId(String callingStationId) {
        callingStationId = callingStationId.replaceAll("-", ":").toUpperCase();
        if (Pattern.matches("/^[A-F0-9]{2}[:]{1}[A-F0-9]{2}[:]{1}[A-F0-9]{2}[:]{1}[A-F0-9]{2}[:]{1}[A-F0-9]{2}[:]{1}[A-F0-9]{2}/", callingStationId)) {
            callingStationId = callingStationId.substring(0, 16);
        } else if (Pattern.matches("[A-F0-9]{12}", callingStationId.toUpperCase())) {
            callingStationId = callingStationId.toUpperCase();
            callingStationId = callingStationId.replaceAll("..(?!$)", "$0:");

        }
        return callingStationId;
    }

    public static String cleanTimestamp(String timestamp) {
        if (Pattern.matches("[0-9]{10}", timestamp)) {
            Date date = new Date((Long.parseLong(timestamp) + 28800) * 1000L);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(date);
            return strDate;
        } else {
            return timestamp;
        }
    }

    public static String cleanConnectInfo(String connectInfo) {
        //CONNECT 802.11a/n
        //CONNECT 0Mbps 802.11b  
        int index = connectInfo.indexOf(" ");
        String result = connectInfo.substring(index + 1);
        if (result.contains(" ")) {
            String[] intermediate = result.split(" ");
            result = intermediate[1];
        }
        return result;
    }

    public static String cleanM1UserName(String userName, String authMethod, String ssid, String accountOperator) {
        userName = userName.substring(1, userName.length() - 1);
        if (userName.contains(".mcc525.3gppnetwork.org")) {
            authMethod = "EAPSIM";
            ssid = "Wireless@SGx";
            accountOperator = userName.substring(userName.indexOf(".mcc525.3gppnetwork.org") - 6, userName.indexOf(".mcc525.3gppnetwork.org"));
            if (accountOperator.equals("mnc003")) {
                accountOperator = "M1";
            } else if (accountOperator.equals("mnc005") || accountOperator.equals("mnc002") || accountOperator.equals("mnc006")) {
                accountOperator = "Starhub";
            } else if (accountOperator.equals("mnc001") || accountOperator.equals("mnc008") || accountOperator.equals("mnc007")) {
                accountOperator = "Singtel";
            } else {
                return "ERROR:" + userName;
            }
        } else if (userName.contains("essa-") && userName.contains("@")) {
            authMethod = "ESSA";
            ssid = "Wireless@SGx";
            accountOperator = userName.substring(userName.indexOf("@") + 1);
        } else if (Pattern.matches("[A-Z]{3}", userName.substring(0, 3)) && Pattern.matches("[0-9]{8}", userName.substring(3, userName.indexOf("@")))) {
            authMethod = "UAM";
            ssid = "Wireless@SG";
            accountOperator = userName.substring(userName.indexOf("@") + 1);
        } else if (userName.contains("@")) {
            authMethod = "SSA";
            ssid = "Wireless@SGx";
            accountOperator = userName.substring(userName.indexOf("@") + 1);
        } else {
            return "ERROR:" + userName;
        }
        return userName;
    }

    public static String cleanM1Timestamp(String timestamp) {
        Date date = new Date((Long.parseLong(timestamp)) * 1000L);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        if (Pattern.matches("[0-9]{10}", strDate)) {
            return strDate;
        } else {
            return "ERROR:" + timestamp;
        }
    }

    public static String cleanM1AcctDelayTime(String acctDelayTime) {
        if (Pattern.matches("[0-9]+", acctDelayTime)) {
            return acctDelayTime;
        }
        return "ERROR:" + acctDelayTime;
    }

    public static String cleanM1AcctInputOctets(String acctInputOctets) {
        if (Pattern.matches("[0-9]+", acctInputOctets)) {
            return acctInputOctets;
        }
        return "ERROR:" + acctInputOctets;
    }

    public static String cleanM1AcctOutputOctets(String acctOutputOctets) {
        if (Pattern.matches("[0-9]+", acctOutputOctets)) {
            return acctOutputOctets;
        }
        return "ERROR:" + acctOutputOctets;
    }

    public static String cleanM1AcctInputPackets(String acctInputPackets) {
        if (Pattern.matches("[0-9]+", acctInputPackets)) {
            return acctInputPackets;
        }
        return "ERROR:" + acctInputPackets;
    }

    public static String cleanM1AcctOutputPackets(String acctOutputPackets) {
        if (Pattern.matches("[0-9]+", acctOutputPackets)) {
            return acctOutputPackets;
        }
        return "ERROR:" + acctOutputPackets;
    }

    public static String cleanM1AcctSessionId(String acctSessionId) {
        acctSessionId = acctSessionId.substring(1, acctSessionId.length() - 1);
        if (Pattern.matches("[\\S]+", acctSessionId)) {
            return acctSessionId;
        }
        return "ERROR:" + acctSessionId;
    }

    public static String cleanM1AcctSessionTime(String acctSessionTime) {
        if (Pattern.matches("[0-9]+", acctSessionTime)) {
            return acctSessionTime;
        }
        return "ERROR:" + acctSessionTime;
    }

    public static String cleanM1CallingStationId(String callingStationId) {
        callingStationId = callingStationId.substring(1, callingStationId.length() - 1);
        if (Pattern.matches("[\\S]+", callingStationId)) {
            return callingStationId;
        }
        return "ERROR:" + callingStationId;
    }

    public static String cleanSingtelTimestamp(String timestamp) {
        if (Pattern.matches("[0-9]{1,2}[/]{1}[0-9]{1,2}[/]{1}[0-9]{4}[\\s]{1}[0-9]{2}[:]{1}[0-9]{2}[:]{1}[0-9]{2}[\\s]{1}[A-Z]{2}", timestamp)) {
            return timestamp;
        } else {
            return "ERROR:" + timestamp;
        }
    }

    public static String cleanSingtelNasIdentifier(String nasIdentifier) {
        if (Pattern.matches("[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}", nasIdentifier)) {
            return nasIdentifier;
        }
        return "ERROR:" + nasIdentifier;
    }

    public static String cleanSingtelUserName(String userName) {
        if (Pattern.matches("[a-z0-9]{40}", userName)) {
            return userName;
        } else {
            return "ERROR:" + userName;
        }
    }

    public static String cleanSingtelAcctOutputOctets(String acctOutputOctets) {
        if (Pattern.matches("[0-9]+", acctOutputOctets)) {
            return acctOutputOctets;
        }
        return "ERROR:" + acctOutputOctets;
    }

    public static String cleanAcctStatusType(String acctStatusType) {
        if (acctStatusType.equals("Start") || acctStatusType.equals("Stop") || acctStatusType.equals("Interim-Update") || acctStatusType.equals("Accounting-On")) {
            return acctStatusType;
        } else {
            return "ERROR:" + acctStatusType;
        }
    }

    public static String getAccountOperator(String inputAccountOperator, String[] accountOperatorList) {
        String inputAccountOperatorLowerCase = inputAccountOperator.toLowerCase();
        for (String accountOperator : accountOperatorList) {
            if (inputAccountOperatorLowerCase.contains(accountOperator.toLowerCase())) {
                return accountOperator;
            }
        }
        return "N.A.";
    }

    public static String[] getAccountOperatorList() {
        return operatorList;
    }

    public static String[] cleanUserName(String operatorRadius, String userName, String[] userAuthSsidOperator) {
        userAuthSsidOperator[0] = userName;
        String authMethod = "N.A.";
        String ssid = "N.A.";
        String accountOperator = "N.A.";
        String accountOperatorTemp = "";
        if (userName != null) {
            if (operatorRadius.toLowerCase().equals("m1")) {
                if (userName.contains(".mcc525.3gppnetwork.org")) {
                    //if (Pattern.matches("^[\s\S]", userName)) {
                    //}
                    authMethod = "EAPSIM";
                    ssid = "Wireless@SGx";
                    accountOperator = userName.substring(userName.indexOf(".mcc525.3gppnetwork.org") - 6, userName.indexOf(".mcc525.3gppnetwork.org"));
                    if (accountOperator.equals("mnc003")) {
                        accountOperator = "M1";
                    } else if (accountOperator.equals("mnc005") || accountOperator.equals("mnc006") || accountOperator.equals("mnc008")) {
                        accountOperator = "Starhub";
                    } else if (accountOperator.equals("mnc001") || accountOperator.equals("mnc002") || accountOperator.equals("mnc007")) {
                        accountOperator = "Singtel";
                    } else {
                        //userName = "ERROR:" + userName;
                    }
                    if (userName.contains("_")) {
                        userName = userName.substring(userName.indexOf("_")+2); //extract after 40th position.
                    }
                    if (userName.contains("INSERT")){
                        userName = userName.substring(37);
                    }
                    if (userName.length()>50){
                        userName=userName.substring(1);
                    }
                } else if (userName.contains("essa-") && userName.contains("@")) {
                    authMethod = "ESSA";
                    ssid = "Wireless@SGx";
                    accountOperator = userName.substring(userName.indexOf("@") + 1);
                } else if (Pattern.matches("[A-Z]{3}", userName.substring(0, 3)) && Pattern.matches("[0-9]{8}", userName.substring(3, userName.indexOf("@")))) {
                    authMethod = "UAM";
                    ssid = "Wireless@SG";
                    accountOperator = userName.substring(userName.indexOf("@") + 1);
                } else if (userName.contains("@")) {
                    authMethod = "SSA";
                    ssid = "Wireless@SGx";
                    accountOperator = userName.substring(userName.indexOf("@") + 1);
                } else {
                    //userName = "ERROR:" + userName;
                }
            } else if (operatorRadius.toLowerCase().equals("starhub")) {
                if (userName.contains(".mcc525.3gppnetwork.org")) {
                    authMethod = "EAPSIM";
                    ssid = "Wireless@SGx";
//                    accountOperator = userName.substring(userName.indexOf(".mcc525.3gppnetwork.org") - 6, userName.indexOf(".mcc525.3gppnetwork.org"));
//                    if (accountOperator.equals("mnc003")) {
//                        accountOperator = "M1";
//                    } else if (accountOperator.equals("mnc005") || accountOperator.equals("mnc006")) {
//                        accountOperator = "Starhub";
//                    } else if (accountOperator.equals("mnc001") || accountOperator.equals("mnc007")) {
//                        accountOperator = "Singtel";
//                    } else {
//                        userName = "ERROR:" + userName;
//                    }
                } else if (userName.contains("essa-") && userName.contains("@")) {
                    authMethod = "ESSA";
                    ssid = "Wireless@SGx";
//                    accountOperator = userName.substring(userName.indexOf("@") + 1);
                    // } else if (Pattern.matches("[A-Z]{3}", userName.substring(0, 3)) && Pattern.matches("[0-9]{8}", userName.substring(3, userName.indexOf("@")))) {
                    //     authMethod = "UAM";
                    //     ssid = "Wireless@SG";
//                    accountOperator = userName.substring(userName.indexOf("@") + 1);
                } else if (userName.contains("@")) {
                    authMethod = "UAM";
                    ssid = "Wireless@SG";
//                    accountOperator = userName.substring(userName.indexOf("@") + 1);
                } else {
                    //userName = "ERROR:" + userName;
                }
            } else if (operatorRadius.toLowerCase().equals("singtel")) {
                if (userName.contains(".mcc525.3gppnetwork.org")) {
                    if (Pattern.matches("^[\\S]{38}_1", userName)) {
                        userName = userName.substring(39); //extract after 40th position.
                    }
                    authMethod = "EAPSIM";
                    ssid = "Wireless@SGx";
                    accountOperator = userName.substring(userName.indexOf(".mcc525.3gppnetwork.org") - 6, userName.indexOf(".mcc525.3gppnetwork.org"));
                    if (accountOperator.equals("mnc003")) {
                        accountOperator = "M1";
                    } else if (accountOperator.equals("mnc005") || accountOperator.equals("mnc006") || accountOperator.equals("mnc008")) {
                        accountOperator = "Starhub";
                    } else if (accountOperator.equals("mnc001") || accountOperator.equals("mnc002") || accountOperator.equals("mnc007")) {
                        accountOperator = "Singtel";
                    } else {
                        //userName = "ERROR:" + userName;
                    }
                } else if (userName.contains("essa-") && userName.contains("@")) {
                    authMethod = "ESSA";
                    ssid = "Wireless@SGx";
                    accountOperator = userName.substring(userName.indexOf("@") + 1);
                } else if (userName.contains("@")) {
                    authMethod = "SSA";
                    ssid = "Wireless@SGx";
                    accountOperator = userName.substring(userName.indexOf("@") + 1);
                } else {
                    //userName = "ERROR:" + userName;
                }
            }
        }

        userAuthSsidOperator[0] = userName;
        userAuthSsidOperator[1] = authMethod;
        userAuthSsidOperator[2] = ssid;
        userAuthSsidOperator[3] = getAccountOperator(accountOperator, operatorList);
        return userAuthSsidOperator;
    }

    public static String[] cleanCalledStationId(String calledStationId) {
        String[] result = new String[2];
        result[0] = "N.A.";
        result[1] = "N.A.";
        if (calledStationId != null) {
            if (calledStationId.indexOf(":Wireless@SGx") != -1) {
                result[0] = calledStationId.substring(0, calledStationId.indexOf(":Wireless@SGx")).replaceAll("-", ":").toUpperCase();
                result[1] = "Wireless@SGx";
            } else if (calledStationId.indexOf(":Wireless@SG") != -1) {
                result[0] = calledStationId.substring(0, calledStationId.indexOf(":Wireless@SG")).replaceAll("-", ":").toUpperCase();
                result[1] = "Wireless@SG";
            } else if (Pattern.matches("/^[A-F0-9]{2}[:]{1}[A-F0-9]{2}[:]{1}[A-F0-9]{2}[:]{1}[A-F0-9]{2}[:]{1}[A-F0-9]{2}[:]{1}[A-F0-9]{2}/", calledStationId.replaceAll("-", ":").toUpperCase())) {
                result[0] = calledStationId.substring(0, 16).replaceAll("-", ":").toUpperCase();
            } else if (Pattern.matches("[A-F0-9]{12}", calledStationId.toUpperCase())) {
                calledStationId = calledStationId.toUpperCase();
                result[0] = calledStationId.replaceAll("..(?!$)", "$0:");
            } else {
                result[0] = calledStationId.replaceAll("-", ":").toUpperCase();
            }
            //if (!Pattern.matches("[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}[:]{1}[A-Z0-9]{2}", result[0])) {
            //    result[0] = "ERROR:" + calledStationId;
            //}
        }
        return result;
    }

    public static String cleanAcctDelayTime(String acctDelayTime) {
        if (Pattern.matches("[0-9]+", acctDelayTime)) {
            return acctDelayTime;
        }
        return "ERROR:" + acctDelayTime;
    }

    public static String cleanAcctSessionTime(String acctSessionTime) {
        if (Pattern.matches("[0-9]+", acctSessionTime)) {
            return acctSessionTime;
        }
        return "ERROR:" + acctSessionTime;
    }

    public static String cleanAcctInputOctets(String acctInputOctets) {
        if (Pattern.matches("[0-9]+", acctInputOctets)) {
            return acctInputOctets;
        }
        return "ERROR:" + acctInputOctets;
    }

    public static String cleanAcctOutputOctets(String acctOutputOctets) {
        if (Pattern.matches("[0-9]+", acctOutputOctets)) {
            return acctOutputOctets;
        }
        return "ERROR:" + acctOutputOctets;
    }

    public static String cleanAcctInputPackets(String acctInputPackets) {
        if (Pattern.matches("[0-9]+", acctInputPackets)) {
            return acctInputPackets;
        }
        return "ERROR:" + acctInputPackets;
    }

    public static String cleanAcctOutputPackets(String acctOutputPackets) {
        if (Pattern.matches("[0-9]+", acctOutputPackets)) {
            return acctOutputPackets;
        }
        return "ERROR:" + acctOutputPackets;
    }
}
