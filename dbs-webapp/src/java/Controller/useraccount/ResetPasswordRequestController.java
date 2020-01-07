/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.useraccount;

import DAO.UserDAO;
import Entity.User;
import Utility.CustomDate;
import Utility.EnvironmentVariable;
import Utility.SMTPMail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
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
@WebServlet(name = "ResetPasswordRequestController", urlPatterns = {"/ResetPasswordRequestController"})
public class ResetPasswordRequestController extends HttpServlet {

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
        String email = (String) request.getParameter("email");
        User user = UserDAO.getUserByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString().replace("-", "");
            UserDAO.updateUserToken(user, token, CustomDate.getDate10Minutes());
            String requestURL = EnvironmentVariable.host + "resetpassword?token=" + token + "&email=" + email;
            System.out.println(requestURL);
            String[] toEmails = {email};
            String mailContent = SMTPMail.getMailHeader();
            mailContent += "\n"
                    + "\n"
                    + "<!-- BODY -->\n"
                    + "<table class=\"body-wrap\">\n"
                    + "	<tr>\n"
                    + "		<td></td>\n"
                    + "		<td class=\"container\" bgcolor=\"#FFFFFF\">\n"
                    + "\n"
                    + "			<div class=\"content\">\n"
                    + "			<table>\n"
                    + "				<tr>\n"
                    + "					<td>\n"
                    + "						<h3>Password Reset Request</h3>\n"
                    + "						<p> Please click <a href='" + requestURL + "'><b>" + requestURL + "</b></a> to reset your password.<br> Do note that the link will expire in the next 10minutes." + "</p><br>\n"
                    + "						\n"
                    + "					</td>\n"
                    + "				</tr>\n"
                    + "			</table>\n"
                    + "            <br>\n"
                    + "			</div><!-- /content -->\n"
                    + "									\n"
                    + "		</td>\n"
                    + "		<td></td>\n"
                    + "	</tr>\n"
                    + "</table><!-- /BODY -->\n";
            mailContent += SMTPMail.getMailFooter();
            SMTPMail.sendEmail(toEmails, "Password Reset Request", mailContent);
            request.setAttribute("outcome", "Password reset email has been successfully sent to " +email + ". Please check your email.");
        } else {
            request.setAttribute("outcome", "Error Email Address: "+email +" not found in database records. Please try again with another email!");
            request.setAttribute("error", "Error");
        }
        request.getRequestDispatcher("resetpwd.jsp").forward(request, response);
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
