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

@WebServlet(name = "CreateUser", urlPatterns = {"/CreateUserController"})

public class CreateUserController extends HttpServlet{
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        UserDAO userDao = new UserDAO();
        
        
            ArrayList<User> userList = userDao.getUsers();
            
            for(User user : userList){
                if(user.getUsername().equals(username)){
                    request.setAttribute("createUser","Duplicate Username!");
                    RequestDispatcher rd = request.getRequestDispatcher("AccountManagement.jsp");
                    rd.forward(request,response);
                }
            }
        
            if(email.indexOf("@") == -1){
                request.setAttribute("createUser","Invalid Email Address!");
                RequestDispatcher rd = request.getRequestDispatcher("AccountManagement.jsp");
                rd.forward(request,response);
            }
            
            Boolean createUser = userDao.createUser(username, password, "User", email);
            
            if(createUser){
                request.setAttribute("createUser","User Successfully Created!");
                RequestDispatcher rd = request.getRequestDispatcher("AccountManagement.jsp");
                userList = userDao.getUsers();
                session.setAttribute("UserList", userList);
                rd.forward(request,response);
            }else{
                request.setAttribute("createUser","User Creation Unsucessful!");
                RequestDispatcher rd = request.getRequestDispatcher("AccountManagement.jsp");
                rd.forward(request,response); 
            }
    }
           
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
