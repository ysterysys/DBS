<%-- 
    Document   : HistoricalAnalysis
    Created on : 1 Jan, 2019, 4:48:04 PM
    Author     : WeiHao
--%>

<%@page import="Entity.Hotspot"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <%@include file="ProtectUser.jsp" %>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="img/favicon.ico" type="image/ico" />

        <title>NAS | Historical Analysis </title>

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
        <!-- datatables -->
        <link href="vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link href="vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
        <link href="vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
        <link href="vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
        <link href="vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">


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
                                <h3>Historical Analysis</h3>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="page-title">
                            <div class="row">
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h4>Analyse Historical data</h4>
                                        </div>
                                        <div class="x_content">
                                            <br>
                                            <form action = "HistoricalAnalysisController" method = "post" class="form-horizontal form-label-left" id="form">
                                                Using Radius Log Data, identify Access Points above or below a certain threshold for utilization rate and connected clients.<br>
                                                <br>

                                                <div class="form-group">
                                                    <label class="control-label col-md-2 col-sm-2 col-xs-8">Select Time Range</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                                        <input type="hidden" name="startDate" id="startDate">
                                                        <input type="hidden" name="endDate" id="endDate">
                                                        <div id="reportrange" class="pull-left" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc">
                                                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                                            <span></span> <b class="caret"></b>
                                                        </div>
                                                    </div>  
                                                </div>
                                                <br>
                                                <!--                                                From Timestamp: <input type ="text" name ="startTimestamp" value ="07/11/2018 12:00:01 AM">
                                                                                                To Timestamp: <input type ="text" name ="endTimestamp" value ="08/11/2018 12:00:01 AM"><br>-->


                                                <div class="form-group">
                                                    <label class="control-label col-md-2 col-sm-2 col-xs-8">Select Analysis Type
                                                        <br>
                                                        <!--                                                        <small class="text-navy">Normal Bootstrap elements</small>-->
                                                    </label>

                                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                                        <div>
                                                            <input type="radio" class="flat" checked="checked" name="filter" value="lessThanConnectedClients">
                                                            Less Than <input type ="text" name ="lessThanConnectedClientsField" value =20> Connected Clients<br>
                                                        </div>

                                                        <br>
                                                        <div>
                                                            <input type="radio" class="flat" name="filter" value="moreThanConnectedClients">
                                                            More Than <input type ="text" name ="moreThanConnectedClientsField" value =80> Connected Clients<br>
                                                        </div>
                                                        <br>
                                                        <div>
                                                            <input type="radio" class="flat"  name="filter" value="lessThanUtilization">                                                     
                                                            Less Than <input type ="text" name ="lessThanUtilizationField" value =20> % Utilization

                                                        </div>
                                                        <br>
                                                        <div>
                                                            <input type="radio" class="flat" name="filter" value="moreThanUtilization">
                                                            More Than <input type ="text" name ="moreThanUtilizationField" value =80> % Utilization<br>
                                                        </div>

                                                        <br>
                                                        <div>
                                                            <input type="radio" class="flat" name="filter" value="autoUtilization">
                                                            Detect AP Hotspots with outlying utilization rates.<br>
                                                        </div>
                                                        <br>
                                                        <div>
                                                            <input type="radio" class="flat" name="filter" value="autoConnectedClients">
                                                            Detect AP Hotspots with outlying number of connected clients.<br>
                                                        </div>
                                                    </div>
                                                </div>






                                                <div class="ln_solid"></div>
                                                <div class="form-group">
                                                    <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-2">

                                                        <button class="btn btn-success" type="submit">View Analysis</button>                                       <br>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="clearfix"></div>

                        <% String resultHtml = "<div class='page-title' id='results'><div class='row'><div class='col-md-12 col-sm-12 col-xs-12'><div class='x_panel'><div class='x_title'><h4>Results</h4></div><div class='x_content'><br>";%>
                        <% String closingdiv = "</div></div></div></div></div>";%>
                        <%
                            if (request.getAttribute("queryStatus") != null) {
                                if (request.getAttribute("queryStatus").equals("SuccessConnectedClients")) {
                                    ArrayList<Hotspot> output = (ArrayList<Hotspot>) session.getAttribute("output");
                                    out.print(resultHtml + " <table id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>");
                                    out.print("ConnectedClients");
                                    out.print("</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>");
                                    for (Hotspot hotspot : output) {
                                        out.print("<tr>");
                                        out.print("<td>" + hotspot.getMacAddress() + "</td>");
                                        out.print("<td>" + hotspot.getClientsOrUtilization() + "</td>");
                                        out.print("<td>" + hotspot.getApId() + "</td>");
                                        out.print("<td>" + hotspot.getDeploymentType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationName() + "</td>");
                                        out.print("<td>" + hotspot.getStreetAddress() + "</td>");
                                        out.print("<td>" + hotspot.getBlock() + "</td>");
                                        out.print("<td>" + hotspot.getLevel() + "</td>");
                                        out.print("<td>" + hotspot.getDescription() + "</td>");
                                        out.print("<td>" + hotspot.getPostalCode() + "</td>");
                                        out.print("<td>" + hotspot.getOperator() + "</td>");
                                        out.print("</tr>");
                                    }
                                    out.print("</table>" + closingdiv);
                                }
                                if (request.getAttribute("queryStatus").equals("SuccessUtilization")) {
                                    ArrayList<Hotspot> output = (ArrayList<Hotspot>) session.getAttribute("output");
                                    out.print(resultHtml + "<table id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>");
                                    out.print("Utilization Rate");
                                    out.print("</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>");
                                    for (Hotspot hotspot : output) {
                                        out.print("<tr>");
                                        out.print("<td>" + hotspot.getMacAddress() + "</td>");
                                        out.print("<td>" + hotspot.getClientsOrUtilization() + "</td>");
                                        out.print("<td>" + hotspot.getApId() + "</td>");
                                        out.print("<td>" + hotspot.getDeploymentType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationName() + "</td>");
                                        out.print("<td>" + hotspot.getStreetAddress() + "</td>");
                                        out.print("<td>" + hotspot.getBlock() + "</td>");
                                        out.print("<td>" + hotspot.getLevel() + "</td>");
                                        out.print("<td>" + hotspot.getDescription() + "</td>");
                                        out.print("<td>" + hotspot.getPostalCode() + "</td>");
                                        out.print("<td>" + hotspot.getOperator() + "</td>");
                                        out.print("</tr>");
                                    }
                                    out.print("</table>" + closingdiv);
                                }
                                if (request.getAttribute("queryStatus").equals("SuccessAutoConnectedClients")) {
                                    String average = (String) session.getAttribute("average");
                                    String standardDeviation = (String) session.getAttribute("standardDeviation");
                                    ArrayList<Hotspot> moreThanConnectedClientsOutput = (ArrayList<Hotspot>) session.getAttribute("moreThanConnectedClientsOutput");
                                    ArrayList<Hotspot> lessThanConnectedClientsOutput = (ArrayList<Hotspot>) session.getAttribute("lessThanConnectedClientsOutput");
                                    out.print(resultHtml + "Mean Number of Connected Clients is: " + average + "<br>");
                                    out.print("Standard Deviation is: " + standardDeviation + "<br>");
                                    out.print("Outliers Above 3 Standard Deviations from Mean:<br>");
                                    out.print(" <table id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>");
                                    out.print("ConnectedClients");
                                    out.print("</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>");
                                    for (Hotspot hotspot : moreThanConnectedClientsOutput) {
                                        out.print("<tr>");
                                        out.print("<td>" + hotspot.getMacAddress() + "</td>");
                                        out.print("<td>" + hotspot.getClientsOrUtilization() + "</td>");
                                        out.print("<td>" + hotspot.getApId() + "</td>");
                                        out.print("<td>" + hotspot.getDeploymentType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationName() + "</td>");
                                        out.print("<td>" + hotspot.getStreetAddress() + "</td>");
                                        out.print("<td>" + hotspot.getBlock() + "</td>");
                                        out.print("<td>" + hotspot.getLevel() + "</td>");
                                        out.print("<td>" + hotspot.getDescription() + "</td>");
                                        out.print("<td>" + hotspot.getPostalCode() + "</td>");
                                        out.print("<td>" + hotspot.getOperator() + "</td>");
                                        out.print("</tr>");
                                    }

                                    out.print("</table><br>");
                                    out.print("Outliers Below 3 Standard Deviations from Mean:<br>");
                                    out.print(" <table id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>");
                                    out.print("ConnectedClients");
                                    out.print("</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>");
                                    for (Hotspot hotspot : lessThanConnectedClientsOutput) {
                                        out.print("<tr>");
                                        out.print("<td>" + hotspot.getMacAddress() + "</td>");
                                        out.print("<td>" + hotspot.getClientsOrUtilization() + "</td>");
                                        out.print("<td>" + hotspot.getApId() + "</td>");
                                        out.print("<td>" + hotspot.getDeploymentType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationName() + "</td>");
                                        out.print("<td>" + hotspot.getStreetAddress() + "</td>");
                                        out.print("<td>" + hotspot.getBlock() + "</td>");
                                        out.print("<td>" + hotspot.getLevel() + "</td>");
                                        out.print("<td>" + hotspot.getDescription() + "</td>");
                                        out.print("<td>" + hotspot.getPostalCode() + "</td>");
                                        out.print("<td>" + hotspot.getOperator() + "</td>");
                                        out.print("</tr>");
                                    }
                                    out.print("</table>" + closingdiv);
                                }
                                if (request.getAttribute("queryStatus").equals("SuccessAutoUtilization")) {
                                    String average = (String) session.getAttribute("average");
                                    String standardDeviation = (String) session.getAttribute("standardDeviation");
                                    ArrayList<Hotspot> moreThanUtilizationOutput = (ArrayList<Hotspot>) session.getAttribute("moreThanUtilizationOutput");
                                    ArrayList<Hotspot> lessThanUtilizationOutput = (ArrayList<Hotspot>) session.getAttribute("lessThanUtilizationOutput");
                                    out.print(resultHtml + "Mean Number of Connected Clients is: " + average + "<br>");
                                    out.print("Standard Deviation is: " + standardDeviation + "<br>");
                                    out.print("Outliers Above 3 Standard Deviations from Mean:<br>");
                                    out.print(" <table id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>");
                                    out.print("Utilization");
                                    out.print("</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>");
                                    for (Hotspot hotspot : moreThanUtilizationOutput) {
                                        out.print("<tr>");
                                        out.print("<td>" + hotspot.getMacAddress() + "</td>");
                                        out.print("<td>" + hotspot.getClientsOrUtilization() + "</td>");
                                        out.print("<td>" + hotspot.getApId() + "</td>");
                                        out.print("<td>" + hotspot.getDeploymentType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationName() + "</td>");
                                        out.print("<td>" + hotspot.getStreetAddress() + "</td>");
                                        out.print("<td>" + hotspot.getBlock() + "</td>");
                                        out.print("<td>" + hotspot.getLevel() + "</td>");
                                        out.print("<td>" + hotspot.getDescription() + "</td>");
                                        out.print("<td>" + hotspot.getPostalCode() + "</td>");
                                        out.print("<td>" + hotspot.getOperator() + "</td>");
                                        out.print("</tr>");
                                    }
                                    out.print("</table><br>");
                                    out.print("Outliers Below 3 Standard Deviations from Mean:<br>");
                                    out.print(" <table id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>AP MacAddress</th><th>");
                                    out.print("Utilization");
                                    out.print("</th><th>AP ID</th><th>Deployment Types</th><th>Location Type</th><th>Location Name</th><th>Street Address</th><th>Block</th><th>Level</th><th>Description</th><th>Postal Code</th><th>Operator</th></tr></thead>");
                                    for (Hotspot hotspot : lessThanUtilizationOutput) {
                                        out.print("<tr>");
                                        out.print("<td>" + hotspot.getMacAddress() + "</td>");
                                        out.print("<td>" + hotspot.getClientsOrUtilization() + "</td>");
                                        out.print("<td>" + hotspot.getApId() + "</td>");
                                        out.print("<td>" + hotspot.getDeploymentType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationType() + "</td>");
                                        out.print("<td>" + hotspot.getLocationName() + "</td>");
                                        out.print("<td>" + hotspot.getStreetAddress() + "</td>");
                                        out.print("<td>" + hotspot.getBlock() + "</td>");
                                        out.print("<td>" + hotspot.getLevel() + "</td>");
                                        out.print("<td>" + hotspot.getDescription() + "</td>");
                                        out.print("<td>" + hotspot.getPostalCode() + "</td>");
                                        out.print("<td>" + hotspot.getOperator() + "</td>");
                                        out.print("</tr></table>");
                                    }
                                    out.print("</table>" + closingdiv);
                                }
                            }
                        %>

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

        <!-- Datatables -->
        <script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
        <script src="vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
        <script src="vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
        <script src="vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
        <script src="vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
        <script src="vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
        <script src="vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
        <script src="vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
        <script src="vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
        <script src="vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
        <script src="vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
        <script src="vendors/jszip/dist/jszip.min.js"></script>
        <script src="vendors/pdfmake/build/pdfmake.min.js"></script>
        <script src="vendors/pdfmake/build/vfs_fonts.js"></script>
        <!-- Custom Theme Scripts -->
        <script src="js/custom-2.js"></script>
        <script>

            /* DATA TABLES */

            function init_DataTables() {
                console.log('run_datatables');
                if (typeof ($.fn.DataTable) === 'undefined') {
                    return;
                }
                console.log('init_DataTables');
                var handleDataTableButtons = function () {
                    if ($("#datatable-buttons").length) {
                        $("#datatable-buttons").DataTable({
                            dom: "Blfrtip",
                            buttons: [
                                {
                                    extend: "copy",
                                    className: "btn-sm"
                                },
                                {
                                    extend: "csv",
                                    className: "btn-sm"
                                },
                                {
                                    extend: "excel",
                                    className: "btn-sm"
                                },
                                {
                                    extend: "pdfHtml5",
                                    className: "btn-sm"
                                },
                                {
                                    extend: "print",
                                    className: "btn-sm"
                                },
                            ],
                            responsive: true,
                        });
                    }
                };
                TableManageButtons = function () {
                    "use strict";
                    return {
                        init: function () {
                            handleDataTableButtons();
                        }
                    };
                }();
                $('#datatable').dataTable();
                $('#datatable-keytable').DataTable({
                    keys: true
                });
                var $datatable = $('#datatable-checkbox');
                $datatable.dataTable({
                    'order': [[1, 'asc']],
                    'columnDefs': [
                        {orderable: false, targets: [0]}
                    ]
                });
                $datatable.on('draw.dt', function () {
                    $('checkbox input').iCheck({
                        checkboxClass: 'icheckbox_flat-green'
                    });
                });
                TableManageButtons.init();
            }
            ;

            $(function () {
                init_DataTables();
                /* on form submit */
            });

        </script>


    </body>
</html>


