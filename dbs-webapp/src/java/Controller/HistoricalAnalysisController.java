/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.HotspotDAO;
import Entity.Hotspot;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author WeiHao
 */
@WebServlet(name = "HistoricalAnalysisController", urlPatterns = {"/HistoricalAnalysisController"})
public class HistoricalAnalysisController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String filter = request.getParameter("filter");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if(filter.equals("lessThanUtilization")){
            String lessThanUtilizationField = request.getParameter("lessThanUtilizationField");
            HotspotDAO hotspotDAO = new HotspotDAO();
            ArrayList<Hotspot> output = hotspotDAO.getLessThanUtilization(lessThanUtilizationField, startDate, endDate);
            request.setAttribute("queryStatus","SuccessUtilization");
            RequestDispatcher rd = request.getRequestDispatcher("HistoricalAnalysis.jsp");
            session.setAttribute("output", output);
            rd.forward(request,response);
        }
        if(filter.equals("moreThanUtilization")){
            String moreThanUtilizationField = request.getParameter("moreThanUtilizationField");
            HotspotDAO hotspotDAO = new HotspotDAO();
            ArrayList<Hotspot> output = hotspotDAO.getMoreThanUtilization(moreThanUtilizationField, startDate, endDate);
            request.setAttribute("queryStatus","SuccessUtilization");
            RequestDispatcher rd = request.getRequestDispatcher("HistoricalAnalysis.jsp");
            session.setAttribute("output", output);
            rd.forward(request,response);
        }
        if(filter.equals("lessThanConnectedClients")){
            String lessThanConnectedClientsField = request.getParameter("lessThanConnectedClientsField");
            HotspotDAO hotspotDAO = new HotspotDAO();
            ArrayList<Hotspot> output = hotspotDAO.getLessThanConnectedClients(lessThanConnectedClientsField, startDate, endDate);
            request.setAttribute("queryStatus","SuccessConnectedClients");
            RequestDispatcher rd = request.getRequestDispatcher("HistoricalAnalysis.jsp");
            session.setAttribute("output", output);
            rd.forward(request,response);
        }
        if(filter.equals("moreThanConnectedClients")){
            String moreThanConnectedClientsField = request.getParameter("moreThanConnectedClientsField");
            HotspotDAO hotspotDAO = new HotspotDAO();
            ArrayList<Hotspot> output = hotspotDAO.getMoreThanConnectedClients(moreThanConnectedClientsField, startDate, endDate);
            request.setAttribute("queryStatus","SuccessConnectedClients");
            RequestDispatcher rd = request.getRequestDispatcher("HistoricalAnalysis.jsp");
            session.setAttribute("output", output);
            rd.forward(request,response);
        }
        if(filter.equals("autoConnectedClients")){
            HotspotDAO hotspotDAO = new HotspotDAO();
            ArrayList<String> output = hotspotDAO.getAutoConnectedClients();
            Double average = Double.parseDouble(output.get(0));
            Double standardDeviation = Double.parseDouble(output.get(1));
            Double aboveThreeSd = average + (standardDeviation * 3);
            Double belowThreeSd = average - (standardDeviation * 3);
            ArrayList<Hotspot> moreThanConnectedClientsOutput = hotspotDAO.getMoreThanConnectedClients(aboveThreeSd.toString(), startDate, endDate);
            ArrayList<Hotspot> lessThanConnectedClientsOutput = hotspotDAO.getLessThanConnectedClients(belowThreeSd.toString(), startDate, endDate);
            request.setAttribute("queryStatus","SuccessAutoConnectedClients");
            RequestDispatcher rd = request.getRequestDispatcher("HistoricalAnalysis.jsp");
            session.setAttribute("average", average.toString());
            session.setAttribute("standardDeviation", standardDeviation.toString());
            session.setAttribute("moreThanConnectedClientsOutput", moreThanConnectedClientsOutput);
            session.setAttribute("lessThanConnectedClientsOutput", lessThanConnectedClientsOutput);
            rd.forward(request,response);
        }
        if(filter.equals("autoUtilization")){
            HotspotDAO hotspotDAO = new HotspotDAO();
            ArrayList<String> output = hotspotDAO.getAutoUtilization();
            Double average = Double.parseDouble(output.get(0));
            Double standardDeviation = Double.parseDouble(output.get(1));
            Double aboveThreeSd = average + (standardDeviation * 3);
            Double belowThreeSd = average - (standardDeviation * 3);
            ArrayList<Hotspot> moreThanUtilizationOutput = hotspotDAO.getMoreThanUtilization(aboveThreeSd.toString(), startDate, endDate);
            ArrayList<Hotspot> lessThanUtilizationOutput = hotspotDAO.getLessThanUtilization(belowThreeSd.toString(), startDate, endDate);
            request.setAttribute("queryStatus","SuccessAutoUtilization");
            RequestDispatcher rd = request.getRequestDispatcher("HistoricalAnalysis.jsp");
            session.setAttribute("average", average.toString());
            session.setAttribute("standardDeviation", standardDeviation.toString());
            session.setAttribute("moreThanUtilizationOutput", moreThanUtilizationOutput);
            session.setAttribute("lessThanUtilizationOutput", lessThanUtilizationOutput);
            rd.forward(request,response);
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
