<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="ProtectUser.jsp" %>
<html>
    <head>
        <title>Change User Password</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/sidebar-menu.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.min.js"></script>
        <script src="js/sidebar-menu.js"></script>
    </head>
    <body>
        <%@include file="Sidebar.jsp" %>

        <div class="content-wrapper">
            <h2>Change User Password</h2>
            <div class="content-box">
                <form action = "ChangePasswordController" method = "post">
                    <div class="row">
                        <br>
                        <div class="col-sm-2">New Password:  </div>
                        <div class="col-sm-10"><input class="form-input" type="password" name="newpassword1" required></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-2">New Password Again:  </div>
                        <div class="col-sm-10"><input class="form-input" type="password" name="newpassword2" required></div>
                    </div>
                    <button class="form-button" type="submit">Change Password</button><br>
                </form>
                <%                if (request.getAttribute("changePassword") != null) {
                        out.print("<b>");
                        out.print(request.getAttribute("changePassword"));
                        out.print("</b>");
                    }
                %>
            </div>
        </div>
    </body>
</html>