<%-- 
    Document   : Bootstrap
    Created on : 8 Oct, 2018, 7:35:41 PM
    Author     : limgeokshanmb
--%>

<%@page import="DAO.BootstrapDAO"%>
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
        <title>NAS | Dashboard </title>
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
        <!-- datatables -->
        <link href="vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link href="vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
        <link href="vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
        <link href="vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
        <link href="vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">

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
                                <h3>Revert Upload</h3>
                            </div>
                        </div>
                        <div class="page-title">
                            <div class="row">
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h4>Upload History</h4>
                                        </div>
                                        <div class="x_content">
                                            Below are the past uploads done<br/>
                                            <br />
                                            <% ArrayList<ArrayList<String>> BootstrapHistories = BootstrapDAO.get20BootstrapHistory();
                                                if (BootstrapHistories == null || BootstrapHistories.isEmpty()) {
                                                    out.print("No recent uploads detected.");
                                                } else {
                                                    out.print("<table id='datatable-buttons' class='table table-striped table-bordered'><thead><tr><th>Last Upload Time</th><th>Log File Name</th><th>Operator</th><th>Log Date Time</th><th>Total Records</th><th>M1 Sessions</th><th>Singtel Sessions</th><th>Starhub Sessions</th><th>Total Sessions</th><th>Upload Outcome</th></tr></thead>");
                                                    for (ArrayList<String> BootstrapHistory : BootstrapHistories) {
                                                        out.print("<tr>");
                                                        out.print("<td>" + BootstrapHistory.get(0) + "</td>");
                                                        out.print("<td>" + BootstrapHistory.get(1) + "</td>");
                                                        out.print("<td>" + BootstrapHistory.get(2) + "</td>");
                                                        out.print("<td>" + BootstrapHistory.get(3) + "</td>");
                                                        out.print("<td>" + BootstrapHistory.get(4) + "</td>");
                                                        out.print("<td>" + BootstrapHistory.get(5) + "</td>");
                                                        out.print("<td>" + BootstrapHistory.get(6) + "</td>");
                                                        out.print("<td>" + BootstrapHistory.get(7) + "</td>");
                                                        out.print("<td>" + BootstrapHistory.get(8) + "</td>");
                                                        out.print("<td>" + BootstrapHistory.get(9) + "</td>");
                                                        out.print("</tr>");

                                                    }
                                                    out.print("</table>");
                                                }%>

                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="page-title">
                            <div class="row">
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h4>Revert Past Uploads</h4>
                                        </div>
                                        <div class="x_content">
                                            Please select the upload date that you wish to revert<br>
                                            <br />
                                            <form action="RevertUploadController"  method = "post" class="form-horizontal form-label-left" id="form">
                                                <div class="form-group">
                                                    <!--<label class="control-label col-md-2 col-sm-2 col-xs-8" for="directory">Specify directory to read file from <span class="required">*</span>
                                                    </label>-->
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <% ArrayList<String> lastUploads = BootstrapDAO.getLastUploads();
                                                            for (String uploadTime : lastUploads) {%>
                                                        <div>
                                                            <input type="radio" class="flat" name="uploadTime" value="<%=uploadTime%>">
                                                            <%=uploadTime%><br>
                                                        </div>
                                                        <%}%>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="ln_solid"></div>
                                                <div class="form-group">
                                                    <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-2">
                                                        <button class="btn btn-success" type="submit">Revert Upload</button>                                       
                                                        <br>
                                                    </div>
                                                </div>
                                            </form>
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
