<%-- 
    Document   : CleanExport
    Created on : 8 Nov, 2018, 3:35:21 PM
    Author     : limgeokshanmb
--%>

<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="DAO.ExportDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <%@include file="ProtectUser.jsp" %>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="img/favicon.ico" type="image/ico" />

        <title>NAS | Error Log Export </title>

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
                                <h3>Export Erroneous Logs</h3>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="page-title">
                            <div class="row">
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h4>Export to CSV</h4>
                                        </div>
                                        <div class="x_content">
                                            <br>
                                            <%DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                                                    Date date = new Date();
                                                    String todaydt = dateFormat.format(date);%>
                                            <button class="btn btn-primary btn-success" onclick="exportToCsv('ErrorExport<%=todaydt%>.csv', 'text/csv;encoding:utf-8')">Export Error Log</button></a><br>

                                        </div>
                                    </div>
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
        <script>
            function exportToCsv(fileName, mimeType) {
                var str = "./json/error-export?csvexport=yes";
                var csvString = "";
                console.log(str);
                $.getJSON(str, function (results) {
                    console.log(results);
                    csvString = results.csvString;
                    console.log(csvString);
                    var a = document.createElement('a');
                    mimeType = mimeType || 'application/octet-stream';

                    if (navigator.msSaveBlob) { // IE10
                        navigator.msSaveBlob(new Blob([csvString], {
                            type: mimeType
                        }), fileName);
                    } else if (URL && 'download' in a) { //html5 A[download]
                        a.href = URL.createObjectURL(new Blob([csvString], {
                            type: mimeType
                        }));
                        a.setAttribute('download', fileName);
                        document.body.appendChild(a);
                        a.click();
                        document.body.removeChild(a);
                    } else {
                        location.href = 'data:application/octet-stream,' + encodeURIComponent(csvString); // only this mime type is supported
                    }
                });
            }
        </script>
    </body>
</html>
