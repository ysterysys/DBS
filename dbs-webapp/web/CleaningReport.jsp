<%@page import="java.util.ArrayList"%>
<%@page import="DAO.CleaningReportDAO"%>
<%@page import="java.util.TreeMap"%>
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.min.js"></script>
    </head>
    <body>
        <%@include file="Sidebar.jsp" %>

        <div class="content-wrapper">
            <h2>Cleaning Report For Last 5 Days</h2>
            
            <h3>Total Number Of Entries Cleaned</h3>
            <canvas id="myChart"></canvas>
            <h3>Total Number Of Errors Recorded</h3>
            <canvas id="myChart2"></canvas>

            <h3>Total Number Of APs Appeared for Singtel</h3>
            <canvas id="APSingtel"></canvas>
            
            <h3>Total Number Of APs Appeared for Starhub</h3>
            <canvas id="APStarhub"></canvas>
            

            <h3>Total Number Of Aps Appeared for M1</h3>
            <canvas id="APM1"></canvas>


            
            <%
                TreeMap<String, String> last5DateRecords = CleaningReportDAO.getLast5DateTotalRecordsNumber();
                ArrayList<String> date = new ArrayList<>(last5DateRecords.keySet());
                ArrayList<String> totalEntries = new ArrayList<>(last5DateRecords.values());
                TreeMap<String, String> last5DateErrors = CleaningReportDAO.getLast5DateTotalErrorRecords();
                ArrayList<String> dateError = new ArrayList<>(last5DateErrors.keySet());
                ArrayList<String> totalErrors = new ArrayList<>(last5DateErrors.values());
                TreeMap<String, String> last5DateAPSingtel = CleaningReportDAO.getLast5DateTotalErrorRecords();
                ArrayList<String> dateAPSingtel = new ArrayList<>(last5DateAPSingtel.keySet());
                ArrayList<String> totalAPSingtel = new ArrayList<>(last5DateAPSingtel.values());
                TreeMap<String, String> last5DateAPStarhub = CleaningReportDAO.getLast5DateTotalErrorRecords();
                ArrayList<String> dateAPStarhub = new ArrayList<>(last5DateAPStarhub.keySet());
                ArrayList<String> totalAPStarhub = new ArrayList<>(last5DateAPStarhub.values());
                TreeMap<String, String> last5DateAPM1 = CleaningReportDAO.getLast5DateTotalErrorRecords();
                ArrayList<String> dateAPM1 = new ArrayList<>(last5DateAPM1.keySet());
                ArrayList<String> totalAPM1 = new ArrayList<>(last5DateAPM1.values());
            %>
            <script>
                var dateTest = [<% for (int i = 0; i < date.size(); i++) {%>"<%= date.get(i)%>"<%= i + 1 < date.size() ? "," : ""%><% }%>];
                var totalEntriesTest  = [<% for (int i = 0; i < totalEntries.size(); i++) {%>"<%= totalEntries.get(i)%>"<%= i + 1 < totalEntries.size() ? "," : ""%><% }%>];
                
                var dateTestError = [<% for (int i = 0; i < dateError.size(); i++) {%>"<%= dateError.get(i)%>"<%= i + 1 < dateError.size() ? "," : ""%><% }%>];
                var totalErrorsTest = [<% for (int i = 0; i < totalErrors.size(); i++) {%>"<%= totalErrors.get(i)%>"<%= i + 1 < totalErrors.size() ? "," : ""%><% }%>];

                var dateAPSingtel = [<% for (int i = 0; i < dateAPSingtel.size(); i++) {%>"<%= dateAPSingtel.get(i)%>"<%= i + 1 < dateAPSingtel.size() ? "," : ""%><% }%>];
                var totalAPSingtel = [<% for (int i = 0; i < totalAPSingtel.size(); i++) {%>"<%= totalAPSingtel.get(i)%>"<%= i + 1 < totalAPSingtel.size() ? "," : ""%><% }%>];
                
                var dateAPStarhub = [<% for (int i = 0; i < dateAPStarhub.size(); i++) {%>"<%= dateAPStarhub.get(i)%>"<%= i + 1 < dateAPStarhub.size() ? "," : ""%><% }%>];
                var totalAPStarhub = [<% for (int i = 0; i < totalAPStarhub.size(); i++) {%>"<%= totalAPStarhub.get(i)%>"<%= i + 1 < totalAPStarhub.size() ? "," : ""%><% }%>];

                var dateAPM1 = [<% for (int i = 0; i < dateAPM1.size(); i++) {%>"<%= dateAPM1.get(i)%>"<%= i + 1 < dateAPM1.size() ? "," : ""%><% }%>];
                var totalAPM1 = [<% for (int i = 0; i < totalAPM1.size(); i++) {%>"<%= totalAPM1.get(i)%>"<%= i + 1 < totalAPM1.size() ? "," : ""%><% }%>];



                var ctx = document.getElementById('myChart').getContext('2d');
                var ctx2 = document.getElementById('myChart2').getContext('2d');

                var ctxAPSingtel = document.getElementById('APSingtel').getContext('2d');
                var ctxAPStarhub = document.getElementById('APStarhub').getContext('2d');
                var ctxAPM1 = document.getElementById('APM1').getContext('2d');


    
                var myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: dateTest,
                        datasets: [{
                                label: 'Total Number Of Entries Cleaned',
                                data: totalEntriesTest,
                                backgroundColor: "rgba(153,255,51,0.4)"
                            }
                        ]
                    }
                });
                var myChart2 = new Chart(ctx2, {
                    type: 'bar',
                    data: {
                        labels: dateTestError,
                        datasets: [{
                                label: 'Total Number Of Errors',
                                data: totalErrorsTest,
                                backgroundColor: "rgba(255,0,0,0.4)"
                            }
                        ]
                    }
                }); 
                
                
                var APSingtel = new Chart(ctxAPSingtel, {
                    type: 'bar',
                    data: {
                        labels: dateAPSingtel,
                        datasets: [{
                                label: 'Total Number Of APs Appeared for Singtel',
                                data: totalAPSingtel,
                                backgroundColor: "rgba(255,0,0,0.4)"
                            }
                        ]
                    }
                });
                
                
                
                var APStarhub = new Chart(ctxAPStarhub, {
                    type: 'bar',
                    data: {
                        labels: dateAPStarhub,
                        datasets: [{
                                label: 'Total Number Of APs Appeared for Singtel',
                                data: totalAPStarhub,
                                backgroundColor: "rgba(255,0,0,0.4)"
                            }
                        ]
                    }
                });
                
    
                var APM1 = new Chart(ctxAPM1, {
                    type: 'bar',
                    data: {
                        labels: dateAPM1,
                        datasets: [{
                                label:'Total Number Of APs Appeared for Singtel',
                                data: totalAPM1,
                                backgroundColor: "rgba(255,0,0,0.4)"
                            }
                        ]
                    }
                });
            </script>
        </div>
    </body>
</html>