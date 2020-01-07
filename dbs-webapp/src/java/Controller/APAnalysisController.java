/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CleanSessionEntryDAO;
import DAO.DashboardDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author limgeokshanmb
 */
@WebServlet(name = "APAnalysisController", urlPatterns = {"/json/ap-analysis"})
public class APAnalysisController extends HttpServlet {

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
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonOutput = new JsonObject();
            JsonArray jsonList = new JsonArray();

            String startDate = (String) request.getParameter("startDate");
            String endDate = (String) request.getParameter("endDate");
            String loginType = (String) request.getParameter("loginType");
            String ssidType = (String) request.getParameter("ssidType");
            String ap = (String) request.getParameter("ap");
            String locationType = (String) request.getParameter("locationType");
            String categoryType = (String) request.getParameter("categoryType");
            String firstLoad = (String) request.getParameter("firstLoad");
            jsonOutput.addProperty("status", "success");
            jsonOutput.add("data", jsonList);
//            jsonOutput.add("test",new JsonPrimitive(startDate));
            String csv;
            if (firstLoad != null && firstLoad.equals("yes")) {
                csv = "No Result Found,No Result Found, No Result Found, No Result Found,No Result Found,No Result Found,No Result Found,No Result Found,No Result Found,No Result Found" + System.getProperty("line.separator");
            } else {
                csv = CleanSessionEntryDAO.getAPDetails(startDate, endDate, ap, loginType, ssidType, locationType, categoryType);
            }
            System.out.println(csv);
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
                dataRow.add("An error has occured!");
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
