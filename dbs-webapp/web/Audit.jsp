<%-- 
    Document   : Audit
    Created on : 11 Jan, 2019, 10:29:44 AM
    Author     : limgeokshanmb
--%>

<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="DAO.AuditDAO"%>
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

        <title>NAS | Audit Trail Export </title>

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
        <!-- Datatables -->
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
                                <h3>Audit Trail</h3>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="page-title">
                            <div class="row">
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h4>View Audit Trail</h4>
                                        </div>
                                        <div class="x_content">
                                            <br>
                                            <form class="form-horizontal form-label-left" id="form">
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
                                                <div class="form-group">
                                                    <label class="control-label col-md-2 col-sm-2 col-xs-8">Select User</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                                        <select id="user" class="select2_single form-control" tabindex="-1">
                                                            <option value=""></option>
                                                            <option value="">All Users</option>
                                                            <%  ArrayList<String> userList = AuditDAO.getUserList();
                                                                for (String user : userList) {
                                                                    user = user.substring(0, 1).toUpperCase() + user.substring(1).toLowerCase();%>
                                                            <option value="<%=user%>"><%=user%></option>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                </div>




                                                <div class="ln_solid"></div>
                                                <div class="form-group">
                                                    <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-2">
                                                        <button type="reset" class="btn btn-primary">Reset</button>
                                                        <!--<button class="btn btn-success" onclick="">View Audit Trail</button>-->
                                                        <button class="btn btn-success" type="submit">View Audit Trail</button>                                       
                                                        <br>
                                                    </div>
                                                </div>

                                            </form>

                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="clearfix"></div>
                        <div class="page-title" id="results" style="visibility: hidden">
                            <div class='row'>
                                <div class='col-md-12 col-sm-12 col-xs-12'>
                                    <div class='x_panel'>
                                        <div class='x_title'>
                                            <h4>Results</h4>
                                        </div>
                                        <div class='x_content'>
                                            <br>
                                            <table id='datatable-buttons' class='table table-striped table-bordered'>
                                                <thead>
                                                    <tr>
                                                        <th>Timestamp</th>
                                                        <th>Username</th>
                                                        <th>Action Type</th>
                                                        <th>Action</th>
                                                        <th>Details</th>
                                                    </tr>
                                                </thead>
                                            </table>
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
                            ajax: {url: "./json/audit-export",
                                data: function (d) {
                                    d.startDate = $('#startDate').val();
                                    d.endDate = $('#endDate').val();
                                    d.user = $('#user').val();
                                }
                            },
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
                            "autoWidth": true,
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
                $("#form").submit(function (event) {
                    $('#datatable-buttons').DataTable().ajax.reload();
                    document.getElementById("results").style.visibility = "visible";
                    //var innerHTML="<div class='row'> <div class='col-md-12 col-sm-12 col-xs-12'> <div class='x_panel'> <div class='x_title'> <h4>Results</h4> </div><div class='x_content'> <br><table id='datatable-buttons' class='table table-striped table-bordered'> <thead> <tr> <th>Timestamp</th> <th>Username</th> <th>Action Type</th> <th>Action</th> <th>Details</th> </tr></thead> </table> </div></div></div></div>";
                    //$('#results').innerHTML=innerHTML;
                    event.preventDefault();
                });

            });
        </script>
    </body>
</html>
