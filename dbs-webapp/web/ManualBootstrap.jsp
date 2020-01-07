<%-- 
    Document   : ManualBootstrap
    Created on : 22 Jan, 2019, 12:07:39 PM
    Author     : yu xiang
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DAO.DashboardDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <%@include file="ProtectUser.jsp" %>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="img/favicon.ico" type="image/ico" />

        <title>NAS | Manual Bootstrap </title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <!-- NProgress -->
        <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
        <!-- iCheck -->
        <link href="vendors/iCheck/skins/flat/green.css" rel="stylesheet">
        <!-- bootstrap-progressbar -->
        <link href="vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
        <!-- bootstrap-daterangepicker -->
        <link href="vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
        <!-- Custom Theme Style -->
        <link href="css/custom.css" rel="stylesheet">

    </head>

    <body class="nav-md">
        <div class="container body">
            <div class="main_container">
                <%@include file="navbar.jsp" %>
                <!-- page content -->
                <div class="right_col" role="main">
                    <!-- top tiles -->
                    <div class="">
                        <div class="page-title">
                            <div class="title_left">
                                <h3>Manual Bootstrap</h3>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="page-title">
                            <div class="row">
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h4>Manual Bootstrap Erroneous Data Log File</h4>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="x_content">
                                            <br />

                                            <form class="form-horizontal form-label-left" action="ManualBootstrapController" method="post" enctype="multipart/form-data">
                                                <div class="form-group">
                                                    <input type="file" name="bin" required="required"><br>
                                                </div>
                                                <input type="submit" class ="btn btn-success" value ="Upload corrected data" />
                                                <br>
                                            </form>
                                        </div>
                                    </div>


                                    <%
                                        HashMap<Integer, HashMap<String, String>> errorsInCSV = null;
                                        if (request.getAttribute("errorsInCSV") != null) {
                                            errorsInCSV = (HashMap<Integer, HashMap<String, String>>) request.getAttribute("errorsInCSV");
                                            out.println("<div class=\"x_panel\">");
                                            out.println("<div class=\"x_title\">");
                                            out.println("<h4>Errors still present in the Corrected Log Entries:</h4>");
                                            out.println("<div class=\"clearfix\"></div>");
                                            out.println("<div class=\"x_content\">");
                                            out.println("</div>");
                                            out.println("<div class=\"x_content\">");
                                            out.println("<center>Please amend these errors if possible, else set ignoreData to '1'.</center>");
                                            out.println("<br />");
                                            out.println("<center><table border='1' style='width:40%; text-align: center; border-spacing: 2px'>");
                                            out.println("<tr><th><center>Line</center></th><th><center>Variable</center></th><th><center>Field</center></th></tr>");
                                            for (Integer line : errorsInCSV.keySet()) {
                                                HashMap<String, String> set = errorsInCSV.get(line);
                                                out.println("<tr><td>" + set.size() + " " + line + "line</td>");
                                                for (String variable : set.keySet()) {
                                                    String field = set.get(variable);
                                                    out.println("<td>" + variable + "</td><td>" + field + "</td></tr>");
                                                }
                                                out.println("</tr>");
                                            }
                                            out.println("</table></center>");
                                            out.println("</div></div>");
                                        }
                                    %>

                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="clearfix"></div>

                    <!-- /top tiles -->
                    <br />

                </div>
                <!-- /page content -->

                <!-- footer content -->
                <footer>
                    <div class="pull-right">
                        A SMU-X IS480 Project by Team <a href="https://wiki.smu.edu.sg/is480/IS480_Team_wiki%3A_2018T1_Scrabs">Scrabs</a>
                    </div>
                    <div class="clearfix"></div>
                </footer>
                <!-- /footer content -->
            </div>
        </div>
        <!-- jQuery -->
        <script src="vendors/jquery/dist/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
        <!-- FastClick -->
        <script src="vendors/fastclick/lib/fastclick.js"></script>
        <!-- NProgress -->
        <script src="vendors/nprogress/nprogress.js"></script>
        <!-- gauge.js -->
        <script src="vendors/gauge.js/dist/gauge.min.js"></script>
        <!-- bootstrap-progressbar -->
        <script src="vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
        <!-- iCheck -->
        <script src="vendors/iCheck/icheck.min.js"></script>
        <!-- DateJS -->
        <script src="vendors/DateJS/build/date.js"></script>
        <!-- bootstrap-daterangepicker -->
        <script src="vendors/moment/min/moment.min.js"></script>
        <script src="vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
        <!-- Chart.js -->
        <script src="vendors/Chart.js/dist/Chart.min.js"></script>
        <!-- Custom Theme Scripts -->
        <script src="js/custom-2.js"></script>

    </body>

</html>
