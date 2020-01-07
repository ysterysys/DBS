<%-- 
    Document   : Dashboard-Report
    Created on : 8 Oct, 2018, 7:35:41 PM
    Author     : limgeokshanmb
--%>

<%@page import="DAO.BootstrapDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DAO.DashboardDAO"%>
<%@page import="REST.RestMethod"%>
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
                                <% String userId = RestMethod.getUserID("marytan");%>
                                <h3><%=userId%></h3>
                                <% ArrayList<String> lastBootstrapInformation = DashboardDAO.getLastBootstrapInformation();%>
                                <h3>Last Upload Results <small><i>Ran at: <%=lastBootstrapInformation.get(0)%></i></small></h3>
                            </div>

                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="x_panel">
                        <div class="row tile_count">
                            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                                <span class="count_top"><i class="fa fa-sticky-note"></i> Total No. Logs Processed</span>
                                <div class="count"><%=lastBootstrapInformation.get(1)%></div>
                            </div>
                            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                                <span class="count_top"><i class="fa fa-check"></i> No. of Logs Cleaned</span>
                                <div class="count"><%=lastBootstrapInformation.get(2)%></div>
                            </div>
                            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                                <span class="count_top"><i class="fa fa-exclamation-circle"></i> No. of Error Logs</span>
                                <div class="count"><%=lastBootstrapInformation.get(3)%></div>
                            </div>


                        </div>
                        <div class="row tile_count">
                            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                                <span class="count_top"><i class="fa fa-map-marker"></i> Total Sessions</span>
                                <div class="count"><%=lastBootstrapInformation.get(7)%></div>
                            </div>
                            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                                <span class="count_top"><i class="fa fa-map-marker"></i> Total Singtel Sessions</span>
                                <div class="count"><%=lastBootstrapInformation.get(4)%></div>
                            </div>
                            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                                <span class="count_top"><i class="fa fa-map-marker"></i> Total Starhub Sessions</span>
                                <div class="count"><%=lastBootstrapInformation.get(5)%></div> 
                            </div>
                            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                                <span class="count_top"><i class="fa fa-map-marker"></i> Total M1 Sessions</span>
                                <div class="count"><%=lastBootstrapInformation.get(6)%></div>
                            </div>
                        </div>
                        <br>
                        Below are details of the last uploads done<br/>
                        <br />
                        <% ArrayList<ArrayList<String>> BootstrapHistories = BootstrapDAO.getBootstrapHistory();
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
                    <!-- /top tiles -->


                    <div class="clearfix"></div>
                    <div class="">
                        <div class="page-title">
                            <div class="title_left">

                                <h3>Cleaning Report </h3>
                            </div>
                            <div class="clearfix"></div>
                            <div class="title">
                                <div class="col-md-5 col-md-offset-1">
                                    Select another date range:
                                    <div id="reportrange1" class="pull-right" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc">
                                        <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                        <span></span> <b class="caret"></b>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="clearfix"></div>   
                    <div class="row"><br>

                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h3>Total Logs </h3>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <canvas id="line-records"></canvas>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h3>No. Erroneous Logs </h3>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <canvas id="line-error"></canvas>
                                </div>
                            </div>
                        </div>



                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h3>Total No. Sessions </h3>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <canvas id="line-chart"></canvas>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h3>No. Singtel Sessions </h3>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <canvas id="line-singtel"></canvas>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h3>No. Starhub Sessions</h3>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <canvas id="line-starhub"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h3>No. M1 Sessions </h3>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <canvas id="line-m1"></canvas>
                                </div>
                            </div>
                        </div>


                    </div>
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
        


        <%String startDate = (String) request.getParameter("startDate");
            String endDate = (String) request.getParameter("endDate");%>
        <script>

            /* DATERANGEPICKER */

            $(function init_daterangepicker() {

                if (typeof ($.fn.daterangepicker) === 'undefined') {
                    return;
                }
                console.log('init_daterangepicker');


                var cb = function (start, end, label) {
                    console.log(start.toISOString(), end.toISOString(), label);
                    $('#reportrange1 span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                };

                var optionSet1 = {
                    startDate: moment().subtract(6, 'days').startOf('day'),
                    endDate: moment().endOf('day'),
                    minDate: '', //DashboardDAO.getMinMaxUploadTime().get(0)%>',
                    maxDate: '', //DashboardDAO.getMinMaxUploadTime().get(1)%>',
                    showDropdowns: true,
                    showWeekNumbers: true,
                    timePicker: true,
                    timePickerIncrement: 1,
                    timePicker12Hour: true,
                    ranges: {
                        'Today': [moment().startOf('day'), moment().endOf('day')],
                        'Yesterday': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
                        'Last 7 Days': [moment().subtract(6, 'days').startOf('day'), moment().endOf('day')],
                        'Last 30 Days': [moment().subtract(29, 'days').startOf('day'), moment().endOf('day')],
                        'This Month': [moment().startOf('month').startOf('day'), moment().endOf('month').endOf('day')],
                        'Last Month': [moment().subtract(1, 'month').startOf('month').startOf('day'), moment().subtract(1, 'month').endOf('month').endOf('day')]
                    },
                    alwaysShowCalendars: 'true',
                    opens: 'left',
                    buttonClasses: ['btn btn-default'],
                    applyClass: 'btn-small btn-primary',
                    cancelClass: 'btn-small',
                    format: 'MMM D, YYYY',
                    separator: ' to ',
                    locale: {
                        format: 'MMM D, YYYY',
                        applyLabel: 'Submit',
                        cancelLabel: 'Clear',
                        fromLabel: 'From',
                        toLabel: 'To',
                        customRangeLabel: 'Custom',
                        daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
                        monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                        firstDay: 1
                    }
                };

                $('#reportrange1 span').html(moment().subtract(7, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
                $('#reportrange1').daterangepicker(optionSet1, cb);
                $('#reportrange1').on('show.daterangepicker', function () {
                    console.log("show event fired");
                });
                $('#reportrange1').on('hide.daterangepicker', function () {
                    console.log("hide event fired");
                });
                $('#reportrange1').on('apply.daterangepicker', function (ev, picker) {
                    console.log("apply event fired, start/end dates are " + picker.startDate.format('YYYY-MM-DD HH:mm:ss') + " to " + picker.endDate.add(1, 'days').format('YYYY-MM-DD HH:mm:ss'));
                    var str = "./json/dashboard-cleaning?startDate=" + picker.startDate.format('YYYY-MM-DD HH:mm:ss') + "&endDate=" + picker.endDate.add(1, 'days').format('YYYY-MM-DD HH:mm:ss');
                    console.log(str);
                    $.getJSON(str, function (results) {
                        console.log(results);

                        var labelsRecords = results.totalRecords.map(function (i) {
                            return i.uploadTime;
                        });
                        var dataRecords = results.totalRecords.map(function (i) {
                            return i.count;
                        });


                        var labels = results.totalLogs.map(function (e) {
                            return e.uploadTime;
                        });
                        var data = results.totalLogs.map(function (e) {
                            return e.count;
                        });

                        var labelsError = results.totalErrors.map(function (f) {
                            return f.uploadTime;
                        });
                        console.log(labelsError);
                        var dataError = results.totalErrors.map(function (f) {
                            return f.count;
                        });

                        var labelsStarhub = results.totalAPStarhub.map(function (g) {
                            return g.uploadTime;
                        });
                        var dataStarhub = results.totalAPStarhub.map(function (g) {
                            return g.count;
                        });


                        var labelsSingtel = results.totalAPSingtel.map(function (h) {
                            return h.uploadTime;
                        });
                        var dataSingtel = results.totalAPSingtel.map(function (h) {
                            return h.count;
                        });


                        var labelsM1 = results.totalAPM1.map(function (h) {
                            return h.uploadTime;
                        });
                        var dataM1 = results.totalAPM1.map(function (h) {
                            return h.count;
                        });

                        chart6.data.datasets[0].data = dataRecords;
                        chart6.data.labels = labelsRecords;
                        chart6.update();

                        chart1.data.datasets[0].data = data;
                        chart1.data.labels = labels;
                        chart1.update();

                        chart2.data.datasets[0].data = dataError;
                        chart2.data.labels = labelsError;
                        chart2.update();

                        chart3.data.datasets[0].data = dataStarhub;
                        chart3.data.labels = labelsStarhub;
                        chart3.update();

                        chart4.data.datasets[0].data = dataSingtel;
                        chart4.data.labels = labelsSingtel;
                        chart4.update();

                        chart5.data.labels = labelsM1;
                        chart5.data.datasets[0].data = dataM1;
                        chart5.update();

                    });
                });
                $('#reportrange1').on('cancel.daterangepicker', function (ev, picker) {
                    console.log("cancel event fired");
                });
                $('#options1').click(function () {
                    $('#reportrange1').data('daterangepicker').setOptions(optionSet1, cb);
                });
                $('#destroy').click(function () {
                    $('#reportrange1').data('daterangepicker').remove();
                });

            });

            //            $(document).ready(function () {
            var chart1;
            var chart2;
            var chart3;
            var chart4;
            var chart5;
            var chart6;
            var str = "./json/dashboard-cleaning?startDate=<%=startDate%>&endDate=<%=endDate%>";
            console.log(str);
            $.getJSON(str, function (results) {
                console.log(results);

                var labelsRecords = results.totalRecords.map(function (i) {
                    return i.uploadTime;
                });
                var dataRecords = results.totalRecords.map(function (i) {
                    return i.count;
                });

                var labels = results.totalLogs.map(function (e) {
                    return e.uploadTime;
                });
                var data = results.totalLogs.map(function (e) {
                    return e.count;
                });

                var labelsError = results.totalErrors.map(function (f) {
                    return f.uploadTime;
                });
                var dataError = results.totalErrors.map(function (f) {
                    return f.count;
                });

                var labelsStarhub = results.totalAPStarhub.map(function (g) {
                    return g.uploadTime;
                });
                var dataStarhub = results.totalAPStarhub.map(function (g) {
                    return g.count;
                });


                var labelsSingtel = results.totalAPSingtel.map(function (h) {
                    return h.uploadTime;
                });
                var dataSingtel = results.totalAPSingtel.map(function (h) {
                    return h.count;
                });


                var labelsM1 = results.totalAPM1.map(function (j) {
                    return j.uploadTime;
                });
                var dataM1 = results.totalAPM1.map(function (j) {
                    return j.count;
                });

                chart6 = new Chart(document.getElementById("line-records"), {
                    type: 'line',
                    data: {
                        labels: labelsRecords,
                        datasets: [{
                                data: dataRecords,
                                label: 'total no. log processed',
                                borderColor: "#cd3e4e",
                                fill: false
                            }
                        ]
                    }
                });
                chart1 = new Chart(document.getElementById("line-chart"), {
                    type: 'line',
                    data: {
                        labels: labels,
                        datasets: [{
                                data: data,
                                label: 'total no. sessions',
                                borderColor: "#3e95cd",
                                fill: false
                            }
                        ]
                    }
                });
                chart2 = new Chart(document.getElementById("line-error"), {
                    type: 'line',
                    data: {
                        labels: labelsError,
                        datasets: [{
                                data: dataError,
                                label: 'no. erroneous log',
                                borderColor: "#3e4ecd",
                                fill: false
                            }
                        ]
                    }
                });
                chart3 = new Chart(document.getElementById("line-starhub"), {
                    type: 'bar',
                    data: {
                        labels: labelsStarhub,
                        datasets: [{
                                data: dataStarhub,
                                label: 'no. starhub sessions',
                                borderColor: "#3ecdbd",
                                fill: true
                            }
                        ]
                    }
                });


                chart4 = new Chart(document.getElementById("line-singtel"), {
                    type: 'bar',
                    data: {
                        labels: labelsSingtel,
                        datasets: [{
                                data: dataSingtel,
                                label: 'no. singtel sessions',
                                borderColor: "#ff0000",
                                fill: true
                            }
                        ]
                    }
                });


                chart5 = new Chart(document.getElementById("line-m1"), {
                    type: 'bar',
                    data: {
                        labels: labelsM1,
                        datasets: [{
                                data: dataM1,
                                label: 'no. M1 sessions',
                                borderColor: "#ffa500",
                                fill: true
                            }
                        ]
                    }
                });

            });


//            var ctx = canvas.getContext('2d');
//            var config = {
//                type: 'line',
//                data: {
//                    labels: labels,
//                    datasets: [{
//                            label: 'Graph Line',
//                            data: data,
//                            backgroundColor: 'rgba(0, 119, 204, 0.3)'
//                        }]
//                }
//            };
//            var chart = new Chart(ctx, config);
            //            });
        </script>
        <!-- Custom Theme Scripts -->
        <script src="js/custom-2.js"></script>

    </body>
</html>
