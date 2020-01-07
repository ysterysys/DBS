package Controller;

import DAO.*;
import java.io.*;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CleanDataExportController", urlPatterns = {"/CleanDataExportController"})

public class CleanDataExportController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String day = request.getParameter("day");
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        
        try {
            ExportDAO exportDao = new ExportDAO();
            Date dateNow = new Date();
            Timestamp currentTimestamp = new Timestamp(dateNow.getTime());
            Long affixTime = dateNow.getTime();
            
            String currentPath = request.getRealPath(request.getServletPath());
            String csvFileName = currentPath.substring(0,currentPath.length()-25) + "/csv/error-data-export-" + affixTime + ".csv";
            boolean exportSuccess = exportDao.exportCleanData(csvFileName, year+"-"+month+"-"+day);
            
            if (exportSuccess) {
                request.setAttribute("lastExportTimestamp", currentTimestamp);
                request.setAttribute("exportSuccess", "Clean Data CSV Generated: " + "<a href='csv\\clean-data-export-" + affixTime + ".csv' target='_blank'>" + csvFileName + "</a>");
            } else {
                request.setAttribute("exportSuccess", "Failed To Generate Clean Data CSV From Specified Date");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("CleanExport.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException s) {
            s.getMessage();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CleanDataExportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
