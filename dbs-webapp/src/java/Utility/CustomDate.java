/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author limgeokshanmb
 */
public class CustomDate {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDateTime() {
        return dateFormat.format(new Date());
    }

    public static String getDate10Minutes() {
        long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
        Date now = new Date();
        long curTimeInMs = now.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (10 * ONE_MINUTE_IN_MILLIS));
        return dateFormat.format(afterAddingMins);
    }
}
