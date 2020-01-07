/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static DAO.AlertsSettingsDAO.insertAlertsSettings;
import static DAO.AlertsSettingsDAO.retrieveAlertsSettings;
import static DAO.AlertsSettingsDAO.updateAlertsSettings;
import Entity.AlertsSettings;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jeremylimys93
 */
@WebServlet(name = "alertsSettingsController", urlPatterns = {"/alertsSettingsController"})
public class alertsSettingsController extends HttpServlet {

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
        
         HttpSession session = request.getSession();
         String userName = (String)session.getAttribute("username");  
         // change null to blank to ensure insert db successfully
         String analysisDays = request.getParameter("analysisDays");
         if(analysisDays == null) {
             analysisDays = "";
         }
         String startDate = request.getParameter("startDate");
         if(startDate == null) {
             startDate = "";
         }
         String endDate = request.getParameter("endDate");
          if(endDate == null) {
             endDate = "";
         }
         String lessThanConnectedClientsField = request.getParameter("lessThanConnectedClientsField");
         String moreThanConnectedClientsField = request.getParameter("moreThanConnectedClientsField");
         String lessThanUtilizationField = request.getParameter("lessThanUtilizationField");
         String moreThanUtilizationField = request.getParameter("moreThanUtilizationField");
         String outliersUtiliseRates = request.getParameter("outliersUtiliseRates");
         String outliersConnectedClients = request.getParameter("outliersConnectedClients");
         
         
        // testing for correct input data
         System.out.println("userName:" + userName);
         System.out.println("analysisDays:" + analysisDays);
         System.out.println("startDate:" + startDate);
         System.out.println("endDate:" + endDate);
         System.out.println("lessThanConnectedClientsField:" + lessThanConnectedClientsField);
         System.out.println("moreThanConnectedClientsField:" + moreThanConnectedClientsField);
         System.out.println("lessThanUtilizationField:" + lessThanUtilizationField);
         System.out.println("moreThanUtilizationField:" + moreThanUtilizationField);
         System.out.println("outliersUtiliseRates:" + outliersUtiliseRates);
         System.out.println("outliersConnectedClients:" + outliersConnectedClients);
         
     
         
         //check if userName exist in  alert table in DB, then either create new alert settings for new username or update existing alert settings for existing username
         AlertsSettings alertsSettings = retrieveAlertsSettings(userName);
         if(alertsSettings == null) {
             //1.create AlertsSettings object
            AlertsSettings alertSettings = new AlertsSettings(userName, analysisDays, startDate, endDate, lessThanConnectedClientsField, moreThanConnectedClientsField,
                lessThanUtilizationField, moreThanUtilizationField, outliersUtiliseRates, outliersConnectedClients);
            //2.call insertAlertsSettings from DAO
            insertAlertsSettings(alertSettings);
            //3. display lastest alert setting for the new username
            request.setAttribute("userAlertsSettings", retrieveAlertsSettings(userName));
            RequestDispatcher view = request.getRequestDispatcher("alertsSettings.jsp");
            view.forward(request, response);
            
         } else {
             //1.create AlertsSettings object
                AlertsSettings alertSettings = new AlertsSettings(userName, analysisDays, startDate, endDate, lessThanConnectedClientsField, moreThanConnectedClientsField,
                lessThanUtilizationField, moreThanUtilizationField, outliersUtiliseRates, outliersConnectedClients);
             //2.call updateAlertsSetting from DAO
             updateAlertsSettings(alertSettings);
             //3. display lastest alert setting for the existing username
               request.setAttribute("userAlertsSettings", retrieveAlertsSettings(userName));
               RequestDispatcher view = request.getRequestDispatcher("alertsSettings.jsp");
               view.forward(request, response);
         }
         
       
         //response.sendRedirect("alertsSettings.jsp");
        
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
