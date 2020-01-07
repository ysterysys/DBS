/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AuditDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author limgeokshanmb
 */
@WebServlet(name = "AuditController", urlPatterns = {"/json/audit-export"})
public class AuditController extends HttpServlet {

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

        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            String startDate = (String) request.getParameter("startDate");
            String endDate = (String) request.getParameter("endDate");
            String user = (String) request.getParameter("user");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonOutput = new JsonObject();
            JsonArray jsonList = new JsonArray();
            jsonOutput.addProperty("status", "success");
            jsonOutput.add("data", jsonList);
//            jsonOutput.add("test",new JsonPrimitive(startDate));
            String csv;
            if (startDate == null || startDate.equals("")) {
                csv = AuditDAO.export();
            } else if (user == null || user.equals("")) {
                csv = AuditDAO.export(startDate, endDate);
            } else {
                csv = AuditDAO.export(startDate, endDate, user);
            }
            if (csv != null) {
                String[] rows = csv.split(System.getProperty("line.separator"));
                for (String row : rows) {
                    JsonArray dataRow = new JsonArray();
                    String[] fields = row.split(",");
                    for (String field : fields) {
                        dataRow.add(field);
                    }
                    jsonList.add(dataRow);
                }
            } else {
                JsonArray dataRow = new JsonArray();
                dataRow.add("Error!");
                jsonList.add(dataRow);
            }
            out.print(gson.toJson(jsonOutput));
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
