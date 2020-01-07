/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.useraccount;

import DAO.AuditDAO;
import DAO.UserDAO;
import Utility.AuditVariable;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ResetUpdatePasswordController", urlPatterns = {"/ResetUpdatePasswordController"})
public class ResetUpdatePasswordController extends HttpServlet {

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
        String password = (String) request.getParameter("password1");
        String email = (String) request.getParameter("email");
        Boolean changePasswordUser = UserDAO.changePasswordbyEmail(email, password);
        String username = UserDAO.getUsernameByEmail(email);
        if (changePasswordUser) {
            request.setAttribute("changePassword", "User Password Change Successful! Please login below: ");
            RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
            AuditDAO.logAction(username, AuditVariable.ActionTypeAccount, AuditVariable.ActionChangePassword, "User Password Change Successful! ");
            rd.forward(request, response);
        } else {
            request.setAttribute("changePassword", "User Password Change Unsucessful! Please login below: ");
            AuditDAO.logAction(username, AuditVariable.ActionTypeAccount, AuditVariable.ActionChangePassword, "User Password Change Unsucessful! ");
            RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
            rd.forward(request, response);
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
