<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="ProtectAdmin.jsp" %>
<html>
    <head>
        <title>IMDA NAS Admin Panel - Account Management</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Admin Panel - Account Management</h1>
            <div>
                <a href="Login.jsp" class="admin-logout">Sign Out</a>
            </div>
            
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#CreateUser">Create a New User</a></li>
                <li><a data-toggle="tab" href="#ViewUsers">View/Delete Users</a></li>
            </ul>

            <div class="tab-content">
              <div id="CreateUser" class="tab-pane fade in active">
                  <h2>Create a New User</h2>
                    <form action = "CreateUserController" method = "post">
                        <div class="row">
                            <div class="col-sm-2">Username: </div>
                            <div class="col-sm-10"><input class="form-input" type = "text" name = "username" required></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2">Password:  </div>
                            <div class="col-sm-10"><input class="form-input" type = "password" name = "password" required></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2">Email:  </div>
                            <div class="col-sm-10"><input class="form-input" type = "text" name = "email" required></div>
                        </div>
                        <button class="form-button" type="submit">Create</button><br>
                    </form>
                    <%
                        if(request.getAttribute("createUser") != null){
                            out.print("<b>");
                            out.print(request.getAttribute("createUser"));
                            out.print("</b>");
                        }
                    %>
                  <br><br>
              </div>
              <div id="ViewUsers" class="tab-pane fade">
                    <h2>View/Delete Users</h2>
                    <%
                        ArrayList<User> userList = (ArrayList<User>)session.getAttribute("UserList");
                        out.print("<form action='DeleteUserController' method='post'><table width='60%' border='1' class='deleteUsersTable'><tr><th>&nbsp;</th><th>Username</th><th>Account Type</th><th>Email</th></tr>");
                        for (User u : userList){
                            String username = u.getUsername();
                            String accountType = u.getAccountType();
                            String email = u.getEmailAddress();
                            out.print("<tr><td>");
                            if (!username.equals("admin")) {
                                out.print("<input type='radio' name='username' value='" + username + "'>");
                            }
                            out.print("</td><td>"+username+"</td><td>"+accountType+"</td><td>"+email+"</td>");
                        }
                        out.print("</table><input type='submit' class='form-button' value='Delete'></form>");

                        if(request.getAttribute("deleteUser") != null){
                            out.print("<b>");
                            out.print(request.getAttribute("deleteUser"));
                            out.print("</b>");
                        }
                    %>
                    <br><br>
              </div>
            </div>
        </div>
    </body>
</html>
