package Controller.useraccount;

import DAO.*;
import Entity.User;
import Utility.AuditVariable;
import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ChangePasswordController", urlPatterns = {"/ChangePasswordController"})

public class ChangePasswordController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = ((User) session.getAttribute("user")).getUsername();
        String new1 = request.getParameter("newpassword1");
        String new2 = request.getParameter("newpassword2");
        UserDAO userDao = new UserDAO();

        if (!new1.equals(new2)) {
            request.setAttribute("changePassword", "Passwords do not match each other!");
            RequestDispatcher rd = request.getRequestDispatcher("ChangePassword.jsp");
            rd.forward(request, response);
        } else {
            Boolean changePasswordUser = userDao.changePassword(username, new1);
            if (changePasswordUser) {
                request.setAttribute("changePassword", "User Password Change Successful!");
                RequestDispatcher rd = request.getRequestDispatcher("ChangePassword.jsp");
                AuditDAO.logAction(username, AuditVariable.ActionTypeAccount, AuditVariable.ActionChangePassword, "User Password Change Successful! ");
                rd.forward(request, response);
            } else {
                request.setAttribute("changePassword", "User Password Change Unsucessful!");
                AuditDAO.logAction(username, AuditVariable.ActionTypeAccount, AuditVariable.ActionChangePassword, "User Password Change Unsucessful! ");
                RequestDispatcher rd = request.getRequestDispatcher("ChangePassword.jsp");
                rd.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
