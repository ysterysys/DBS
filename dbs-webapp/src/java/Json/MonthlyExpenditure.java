/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Json;

import REST.RestMethod;
import java.net.HttpURLConnection;
import java.net.URL;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author limge
 */
@WebServlet(name = "MonthlyExpenditure", urlPatterns = {"/MonthlyExpenditure"})
public class MonthlyExpenditure extends HttpServlet {

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
            jsonOutput.addProperty("status", "success");
//            jsonOutput.add("test",new JsonPrimitive(startDate));
            HttpSession session = request.getSession();
            String id = (String) session.getAttribute("id");
            ArrayList<String> list = RestMethod.getDepositAccounts(id);
            TreeMap<String, String> map = RestMethod.getMonthlyExpenditure(list.get(0));
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
