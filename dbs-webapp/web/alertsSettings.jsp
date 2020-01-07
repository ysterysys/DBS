<%-- 
    Document   : alertsSettings
    Created on : 17 Mar, 2019, 5:55:09 PM
    Author     : jeremylimys93
--%>

<%@page import="Entity.AlertsSettings"%>
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

        <title>NAS | Alerts Settings </title>

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

        <script>
            function radioTextBox() {
                var form = document.forms['form'];
                form.group1[0].onfocus = function () {
                    form.analysisDays.disabled = false;
                    form.startDate.disabled = form.endDate.disabled = true;
                }
                form.group1[1].onfocus = function () {
                    form.analysisDays.disabled = true;
                    form.startDate.disabled = form.endDate.disabled = false;
                }
            }
        </script>
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
                                <h3>Email Alerts</h3>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="page-title">
                            <div class="row">
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h4>Customise Alert Settings</h4>
                                        </div>
                                        <div class="x_content">
                                            <br>
                                            <form action = "alertsSettingsController" method = "post" class="form-horizontal form-label-left" id="form" name="form" onclick="radioTextBox()">
                                                Receive email alerts based on your customised preference<br>
                                                <br>

                                                <div class="form-group">
                                                    <label class="control-label col-md-2 col-sm-2 col-xs-8">Select Time Range</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                                        <input type="radio" name="group1" value="option1" checked="checked"/>
                                                        Last <input type ="text" name ="analysisDays" value =""/> Days &nbsp;&nbsp;&nbsp;
                                                        <input type="radio" name="group1" value="option2" />
                                                        Start Date <input type="date" name="startDate" placeholder="date" value="yyyy-MM-dd" disabled="disabled" />
                                                        End Date <input type="date" name="endDate" placeholder="date" value="yyyy-MM-dd" disabled="disabled" />
                                                        <br>
                                                    </div> 
                                                </div>
                                                <br>

                                                <div class="form-group">
                                                    <label class="control-label col-md-2 col-sm-2 col-xs-8">Filter Analysis
                                                        <br>
                                                    </label>
                                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                                        <div>
                                                            Less Than <input type ="text" name ="lessThanConnectedClientsField" value ="20"> Connected Clients<br>
                                                        </div>

                                                        <br>
                                                        <div>
                                                            More Than <input type ="text" name ="moreThanConnectedClientsField" value ="30"> Connected Clients<br>
                                                        </div>
                                                        <br>
                                                        <div>                                               
                                                            Less Than <input type ="text" name ="lessThanUtilizationField" value ="40"> % Utilization
                                                        </div>
                                                        <br>
                                                        <div>
                                                            More Than <input type ="text" name ="moreThanUtilizationField" value ="50"> % Utilization<br>
                                                        </div>

                                                        <br>
                                                        <div>
                                                            Detect AP Hotspots with outlying utilization rates?<br>
                                                            <input type="radio" class="flat" name="outliersUtiliseRates" value="Yes">Yes</input>
                                                            <input type="radio" class="flat" name="outliersUtiliseRates" value="No">No</input>

                                                        </div>
                                                        <br>
                                                        <div>
                                                            Detect AP Hotspots with outlying number of connected clients?<br>
                                                            <input type="radio" class="flat" name="outliersConnectedClients" value="Yes">Yes</input>
                                                            <input type="radio" class="flat" name="outliersConnectedClients" value="No">No</input>

                                                        </div>
                                                    </div>
                                                </div>




                                                <div class="ln_solid"></div>
                                                <div class="form-group">
                                                    <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-2">

                                                        <button class="btn btn-success" type="submit">Update Settings</button>                                       <br>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="clearfix"></div>
                        <%
                            AlertsSettings alertsSettings = (AlertsSettings) request.getAttribute("userAlertsSettings");
                            if (alertsSettings != null) {
                                String resultHtml = "<div class='page-title' id='results'><div class='row'><div class='col-md-12 col-sm-12 col-xs-12'><div class='x_panel'><div class='x_title'><h4>Your Alerts Settings</h4></div><div class='x_content'><br>";
                                String closingdiv = "</div></div></div></div></div>";
                                out.print(resultHtml + " <table id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>User Name</th><th>Analysis Days</th><th>Start Date</th><th>End Date</th><th>");
                                out.print("Less Than Connected Clients");
                                out.print("</th><th>More Than Connected Clients</th><th>Less Than % Utilization</th><th>More Than % Utilization</th><th>Outliers Utilisation Rates</th><th>Outliers Connected Clients</th></tr></thead>");

                                out.print("<tr>");
                                out.print("<td>" + alertsSettings.getUsername() + "</td>");
                                out.print("<td>" + alertsSettings.getCategoryDate() + "</td>");
                                out.print("<td>" + alertsSettings.getStartDate() + "</td>");
                                out.print("<td>" + alertsSettings.getEndDate() + "</td>");
                                out.print("<td>" + alertsSettings.getLessThanConnectedClients() + "</td>");
                                out.print("<td>" + alertsSettings.getMoreThanConnectedClients() + "</td>");
                                out.print("<td>" + alertsSettings.getLessThanUtilization() + "</td>");
                                out.print("<td>" + alertsSettings.getMoreThanUtilization() + "</td>");
                                out.print("<td>" + alertsSettings.getOutlierUtilizationRates() + "</td>");
                                out.print("<td>" + alertsSettings.getOutlierConnectedClients() + "</td>");
                                out.print("</tr>");

                                out.print("</table>" + closingdiv);
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


