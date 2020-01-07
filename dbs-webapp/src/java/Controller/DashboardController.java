/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
@WebServlet(name = "DashboardController", urlPatterns = {"/json/dashboard-cleaning"})
public class DashboardController extends HttpServlet {

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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonOutput = new JsonObject();
            JsonArray jsonList = new JsonArray();

            String startDate = (String) request.getParameter("startDate");
            String endDate = (String) request.getParameter("endDate");
            if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty() || startDate.length() < 8) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis() - 7L * 24 * 3600 * 1000));
            }
            jsonOutput.addProperty("status", "success");
//            jsonOutput.add("test",new JsonPrimitive(startDate));

            TreeMap<String, String> map = DashboardDAO.getTotalSessionsGraph(startDate, endDate);
            Iterator<String> iter = map.keySet().iterator();
            while (iter.hasNext()) {
                String uploadTime = iter.next();
                String count = map.get(uploadTime);
                JsonObject tmpObject = new JsonObject();
                tmpObject.addProperty("uploadTime", uploadTime);
                tmpObject.addProperty("count", count);
                jsonList.add(tmpObject);
            }
            jsonOutput.add("totalLogs", jsonList);

            jsonList = new JsonArray();
            map = DashboardDAO.getTotalRecordsGraph(startDate, endDate);
            iter = map.keySet().iterator();
            if (map.keySet().isEmpty()) {
                JsonObject tmpObject = new JsonObject();
                tmpObject.addProperty("uploadTime", "0");
                tmpObject.addProperty("count", "0");
                jsonList.add(tmpObject);
            } else {
                while (iter.hasNext()) {
                    String uploadTime = iter.next();
                    String count = map.get(uploadTime);
                    JsonObject tmpObject = new JsonObject();
                    tmpObject.addProperty("uploadTime", uploadTime);
                    tmpObject.addProperty("count", count);
                    jsonList.add(tmpObject);
                }
            }
            jsonOutput.add("totalRecords", jsonList);
            
            jsonList = new JsonArray();
            map = DashboardDAO.getTotalErrorsGraph(startDate, endDate);
            iter = map.keySet().iterator();
            if (map.keySet().isEmpty()) {
                JsonObject tmpObject = new JsonObject();
                tmpObject.addProperty("uploadTime", "0");
                tmpObject.addProperty("count", "0");
                jsonList.add(tmpObject);
            } else {
                while (iter.hasNext()) {
                    String uploadTime = iter.next();
                    String count = map.get(uploadTime);
                    JsonObject tmpObject = new JsonObject();
                    tmpObject.addProperty("uploadTime", uploadTime);
                    tmpObject.addProperty("count", count);
                    jsonList.add(tmpObject);
                }
            }
            jsonOutput.add("totalErrors", jsonList);

            jsonList = new JsonArray();
            map = DashboardDAO.getTotalAPGraphSingtel(startDate, endDate);
            iter = map.keySet().iterator();
            if (map.keySet().isEmpty()) {
                JsonObject tmpObject = new JsonObject();
                tmpObject.addProperty("uploadTime", "0");
                tmpObject.addProperty("count", "0");
                jsonList.add(tmpObject);
            } else {
                while (iter.hasNext()) {
                    String uploadTime = iter.next();
                    String count = map.get(uploadTime);
                    JsonObject tmpObject = new JsonObject();
                    tmpObject.addProperty("uploadTime", uploadTime);
                    tmpObject.addProperty("count", count);
                    jsonList.add(tmpObject);
                }
            }

            jsonOutput.add("totalAPSingtel", jsonList);

            jsonList = new JsonArray();
            map = DashboardDAO.getTotalAPGraphStarhub(startDate, endDate);
            iter = map.keySet().iterator();
            if (map.keySet().isEmpty()) {
                JsonObject tmpObject = new JsonObject();
                tmpObject.addProperty("uploadTime", "0");
                tmpObject.addProperty("count", "0");
                jsonList.add(tmpObject);
            } else {
                while (iter.hasNext()) {
                    String uploadTime = iter.next();
                    String count = map.get(uploadTime);
                    JsonObject tmpObject = new JsonObject();
                    tmpObject.addProperty("uploadTime", uploadTime);
                    tmpObject.addProperty("count", count);
                    jsonList.add(tmpObject);
                }
            }
            jsonOutput.add("totalAPStarhub", jsonList);

            jsonList = new JsonArray();
            map = DashboardDAO.getTotalAPGraphM1(startDate, endDate);
            iter = map.keySet().iterator();
            if (map.keySet().isEmpty()) {
                JsonObject tmpObject = new JsonObject();
                tmpObject.addProperty("uploadTime", "0");
                tmpObject.addProperty("count", "0");
                jsonList.add(tmpObject);
            } else {
                while (iter.hasNext()) {
                    String uploadTime = iter.next();
                    String count = map.get(uploadTime);
                    JsonObject tmpObject = new JsonObject();
                    tmpObject.addProperty("uploadTime", uploadTime);
                    tmpObject.addProperty("count", count);
                    jsonList.add(tmpObject);
                }
            }
            jsonOutput.add("totalAPM1", jsonList);

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
