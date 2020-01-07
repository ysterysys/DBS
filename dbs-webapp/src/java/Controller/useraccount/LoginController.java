/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.useraccount;

/**
 *
 * @author WeiHao
 */
import Entity.*;
import Utility.BCrypt;
import java.util.*;
import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import REST.RestMethod;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})

public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String inputUsername = request.getParameter("username");
        String inputPassword = request.getParameter("password");

        //UserDAO userDAO = new UserDAO();
        if(inputUsername.equals("marytan") && inputPassword.equals("marytan")){
            session.setAttribute("id",RestMethod.getUserID(inputUsername));
            session.setAttribute("user","mary");
            response.sendRedirect("DashboardReport.jsp");

 
        }else { //failure authenticate
                request.setAttribute("errorMsg", "Invalid Username/Password");
                RequestDispatcher view = request.getRequestDispatcher("Login.jsp");
                view.forward(request, response);
            }
      //  User user = getUser(inputUsername);
        //user exist
       /* if (user != null) {
            String password = user.getPassword();

            boolean matched = BCrypt.checkpw(inputPassword, password);

            //success authenticate
            if (matched) {
                //success authenticate as admin type
                if (user.getAccountType().equals("Admin")) {
                    session.setAttribute("user", user);
                    session.setAttribute("username", inputUsername);
                    ArrayList<User> userList = userDAO.getUsers();
                    session.setAttribute("UserList", userList);
                    AuditDAO.logAction((String) session.getAttribute("username"), "User Account", "User Login", "User has successfully logged into NAS System.");
                     
                } else { //success authenticate as user type
                    session.setAttribute("user", user);
                    session.setAttribute("username", inputUsername);
                    AuditDAO.logAction((String) session.getAttribute("username"), "User Account", "User Login", "User has successfully logged into NAS System.");
                    response.sendRedirect("DashboardReport.jsp");
                }
            } else { //failure authenticate
                request.setAttribute("errorMsg", "Invalid Username/Password");
                RequestDispatcher view = request.getRequestDispatcher("Login.jsp");
                view.forward(request, response);
            }

        } else { // user don't exit
            request.setAttribute("errorMsg", "Invalid Username/Password");
            AuditDAO.logAction("System", "User Account", "User Login", "Failed login attempt by user " + inputUsername + ".");
            RequestDispatcher view = request.getRequestDispatcher("Login.jsp");
            view.forward(request, response);
        }
        */

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
