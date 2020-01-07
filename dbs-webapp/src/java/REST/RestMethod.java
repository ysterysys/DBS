/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 *
 * @author limge
 */
public class RestMethod {

    public static String getUserID(String username) {
        String id = "-1";
        try {

            URL urlForGetRequest = new URL("http://techtrek-api-gateway.ap-southeast-1.elasticbeanstalk.com/customers/" + username);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            conection.setRequestProperty("Identity", "T32"); // set userId its a sample here
            conection.setRequestProperty("Token", "62f36335-33b8-4556-824e-04c13bebc795");

            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                Object obj = new JSONParser().parse(response.toString());
                // print result
                JSONObject jo = (JSONObject) obj;

                // getting firstName and lastName 
                id = (String) jo.get("customerId");
                //GetAndPost.POSTRequest(response.toString());
            } else {
            }

        } catch (Exception ex) {
            Logger.getLogger(RestMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public static ArrayList<String> getUserDetail(String id) {
        ArrayList<String> list = new ArrayList<>();

        try {

            URL urlForGetRequest = new URL("http://techtrek-api-gateway.ap-southeast-1.elasticbeanstalk.com/customers/" + id + "/details/");
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            conection.setRequestProperty("Identity", "T32"); // set userId its a sample here
            conection.setRequestProperty("Token", "62f36335-33b8-4556-824e-04c13bebc795");

            int responseCode = conection.getResponseCode();
            if (true) {//responseCode == HttpURLConnection.HTTP_OK
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                Object obj = new JSONParser().parse(response.toString());
                // print result
                JSONObject jo = (JSONObject) obj;

                // getting firstName and lastName 
                //id = (String) jo.get("customerId"); 
                list.add((String) jo.get("customerId"));
                list.add((String) jo.get("gender"));
                list.add((String) jo.get("firstName"));
                list.add((String) jo.get("lastName"));
                list.add((String) jo.get("lastLogIn"));
                list.add((String) jo.get("dateOfBirth"));
                list.add((String) jo.get("riskLevel"));
                //GetAndPost.POSTRequest(response.toString());
            }

        } catch (Exception ex) {
            Logger.getLogger(RestMethod.class.getName()).log(Level.SEVERE, null, ex);
            list.add("Exception");
        } finally {
            return list;
        }

    }

    public static ArrayList<String> getDepositAccounts(String id) {
        ArrayList<String> list = new ArrayList<>();

        try {

            URL urlForGetRequest = new URL("http://techtrek-api-gateway.ap-southeast-1.elasticbeanstalk.com/accounts/deposit/" + id);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            conection.setRequestProperty("Identity", "T32"); // set userId its a sample here
            conection.setRequestProperty("Token", "62f36335-33b8-4556-824e-04c13bebc795");

            int responseCode = conection.getResponseCode();
            if (true) {//responseCode == HttpURLConnection.HTTP_OK
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                Object obj = new JSONParser().parse(response.toString());
                // print result
                JSONArray ja = (JSONArray) obj;
                JSONObject jo;
                String accountId;
                int i = 0;
                Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
                Calendar cal = Calendar.getInstance();
                cal.setTime(today); // don't forget this if date is arbitrary e.g. 01-01-2014;
                
                int month = cal.get(Calendar.MONTH); // 5
                int year = cal.get(Calendar.YEAR); // 2016
                while (ja.get(i) != null) {

                    jo = (JSONObject) ja.get(i);
                    accountId = (String) jo.get("accountId").toString();
                    list.add(accountId);
                    list.add((String) jo.get("displayName"));
                    list.add((String) jo.get("type"));
                    list.add((String) jo.get("accountNumber"));
                    list.add(getBalance(accountId,month,year));

                    i++;
                }

                // getting firstName and lastName 
                //id = (String) jo.get("customerId"); 
//                list.add((String) jo.get("customerId"));
//                list.add((String) jo.get("gender"));
//                list.add((String) jo.get("firstName"));
//                list.add((String) jo.get("lastName"));
//                list.add((String) jo.get("lastLogIn"));
//                list.add((String) jo.get("dateOfBirth"));
//                list.add((String) jo.get("riskLevel"));
                //GetAndPost.POSTRequest(response.toString());
            }

        } catch (Exception ex) {
            Logger.getLogger(RestMethod.class.getName()).log(Level.SEVERE, null, ex);
            list.add("Exception");
        } finally {
            return list;
        }

    }

    public static String getBalance(String accountId, int month, int year) {
        String balance = "";
        try {

            URL urlForGetRequest = new URL("http://techtrek-api-gateway.ap-southeast-1.elasticbeanstalk.com/accounts/deposit/" + accountId
                    + "/balance?month=" + month + "&year=" + year);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            conection.setRequestProperty("Identity", "T32"); // set userId its a sample here
            conection.setRequestProperty("Token", "62f36335-33b8-4556-824e-04c13bebc795");

            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                Object obj = new JSONParser().parse(response.toString());
                // print result
                JSONObject jo = (JSONObject) obj;

                // getting firstName and lastName 
                String availBal = (String) jo.get("availableBalance");
                String currency = (String) jo.get("currency");
                balance = currency + " " + availBal;
                //GetAndPost.POSTRequest(response.toString());
            } else {
            }

        } catch (Exception ex) {
            Logger.getLogger(RestMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        return balance;
    }

}
