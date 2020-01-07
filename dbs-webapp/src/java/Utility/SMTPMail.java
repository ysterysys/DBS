/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

/**
 *
 * @author limgeokshanmb
 */
import DAO.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPMail {

    private static Properties emailProperties;
    private static Session mailSession;
    private static MimeMessage emailMessage;

    public static String getMailHeader() {
        String mailHeader = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "<meta name=\"viewport\" content=\"width=device-width\" />\n"
                + "\n"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                + "<title>NAS - Upload Completed </title>\n"
                + "	\n"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"https://dl.dropbox.com/s/sffonkin9rzjyrj/email.css\" />\n"
                + "\n"
                + "</head>\n"
                + " \n"
                + "<body bgcolor=\"#FFFFFF\">\n"
                + "\n"
                + "<!-- HEADER -->\n"
                + "<table class=\"head-wrap\" bgcolor=\"#C2DFFF\">\n"
                + "	<tr>\n"
                + "		<td></td>\n"
                + "		<td class=\"header container\" >\n"
                + "				\n"
                + "				<div class=\"content\">\n"
                + "				<table bgcolor=\"#C2DFFF\">\n"
                + "					<tr>\n"
                + "						<td><img src=\"https://i.ibb.co/5FPXJJJ/imda-nas-original-bg.png\" width=\"100px\" /></td>\n"
                + "					</tr>\n"
                + "				</table>\n"
                + "				</div>\n"
                + "				\n"
                + "		</td>\n"
                + "		<td></td>\n"
                + "	</tr>\n"
                + "</table>\n"
                + "<!-- /HEADER -->\n";
        return mailHeader;
    }
    
    public static String getMailFooter(){
        String mailFooter =  "<!-- FOOTER -->\n"
                + "<table class=\"footer-wrap\">\n"
                + "	<tr>\n"
                + "		<td></td>\n"
                + "		<td class=\"container\">\n"
                + "			\n"
                + "				<!-- content -->\n"
                + "				<div class=\"content\">\n"
                + "				<table>\n"
                + "				<tr>\n"
                + "					<td align=\"center\">\n"
                + "						<p>\n"
                + "							<a href=\"http://18.222.80.173/IMDA_Network_Analytics_System/\">Login to IMDA NAS</a>\n"
                + "						</p>\n"
                + "					</td>\n"
                + "				</tr>\n"
                + "			</table>\n"
                + "				</div><!-- /content -->\n"
                + "				\n"
                + "		</td>\n"
                + "		<td></td>\n"
                + "	</tr>\n"
                + "</table><!-- /FOOTER -->\n"
                + "\n"
                + "</body>\n"
                + "</html>";
        return mailFooter;
    }

    public static void sendEmail(String[] toEmails, String emailSubject, String emailBody) {
        try {
            String emailHost = "smtp.gmail.com";
            String fromUser = "imda.nas2019";//just the id alone without @gmail.com
            String fromUserEmailPassword = "=HVXm2A2";
            ArrayList<String> bootstrapInfo = DashboardDAO.getLastBootstrapInformation();

            //set mail server properties
            String emailPort = "587";//gmail's smtp port

            emailProperties = System.getProperties();
            emailProperties.put("mail.smtp.port", emailPort);
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");

            //create email messages
            mailSession = Session.getDefaultInstance(emailProperties, null);
            emailMessage = new MimeMessage(mailSession);

            for (int i = 0; i < toEmails.length; i++) {
                emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
            }

            emailMessage.setSubject(emailSubject);
            emailMessage.setContent(emailBody, "text/html");//for a html email
            //emailMessage.setText(emailBody);// for a text email

            Transport transport = mailSession.getTransport("smtp");

            transport.connect(emailHost, fromUser, fromUserEmailPassword);
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
            System.out.println("Email sent successfully.");
        } catch (MessagingException ex) {
            Logger.getLogger(SMTPMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
