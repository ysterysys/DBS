<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="ProtectUser.jsp" %>
<html>
    <head>
        <title>IMDA NAS Main Menu</title>
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
            <h2>User Main Menu</h2>
            <div class="row">
                <div class="col-sm-6">
                    <div class="content-box">
                        <form action="AutomaticBootstrapController" method="post">
                            <h4>To trigger automatic cleaning: </h4>
                            <button class="form-button" type="submit">Run Automatic Bootstrap</button>
                            <br><br>
                        </form>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="content-box">
                        <form action="AutomaticBootstrapController" method="post" enctype="multipart/form-data">
                            <h4>To manually upload server log:</h4>
                            <input type="file" name="Bootstrap" required/><br>
                            <input type="submit" class="btn btn-success btn-lg" value="Run Manual Bootstrap" />
                            <br>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>