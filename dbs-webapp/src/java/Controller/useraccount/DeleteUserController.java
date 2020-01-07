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
import DAO.*;
import java.util.*;
import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "DeleteUser", urlPatterns = {"/DeleteUserController"})

public class DeleteUserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        UserDAO userDao = new UserDAO();

        Boolean deleteUser = userDao.deleteUser(username);

        if (deleteUser) {
            request.setAttribute("deleteUser", "User Successfully Deleted!");
            RequestDispatcher rd = request.getRequestDispatcher("AccountManagement.jsp");
            ArrayList<User> userList = userDao.getUsers();
            session.setAttribute("UserList", userList);
            rd.forward(request, response);
        } else {
            request.setAttribute("deleteUser", "User Deletion Unsucessful!");
            RequestDispatcher rd = request.getRequestDispatcher("AccountManagement.jsp");
            rd.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
