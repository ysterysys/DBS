/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

//import Controller.AutomaticBootstrapController;
import Utility.Alert;
import static Utility.Alert.sendEmail;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author jeremylimys93
 */
public class TestSendEmail {
     public static void main(String[] args) {
          try {
            Alert.sendEmail();
        } catch (MessagingException ex) {
            //Logger.getLogger(AutomaticBootstrapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
