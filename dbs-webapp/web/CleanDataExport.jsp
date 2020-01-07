<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<%@include file="ProtectUser.jsp" %>
<html>
    <head>
        <title>Clean Data Export</title>
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
            <h2>Clean Data Export</h2>
            <form action="CleanDataExportController" method="post">
                <div class="content-box">
                    <br>
                    <div class="row">
                        <div class="col-sm-2">Last Run:</div>
                        <div class="col-sm-10">
                            <%
                                if (request.getAttribute("lastExportTimestamp") != null) {
                                    out.print("<b>");
                                    out.print(request.getAttribute("lastExportTimestamp"));
                                    out.print("</b>");
                                } else {
                                    out.print("<b>Please click on generate button below</b>");
                                }
                            %>
                        </div>
                    </div>
                    <div class="row">
                        <br>
                        <div class="col-sm-2">Select Upload Date:</div>
                        <div class="col-sm-10">
                            <select name="day">
                                <%
                                    for (int i = 1; i <= 9; i++) {
                                        out.println("<option value=0" + i + ">" + i + "</option>");
                                    }
                                    for (int i = 10; i <= 31; i++) {
                                        out.println("<option value=" + i + ">" + i + "</option>");
                                    }
                                %>
                            </select>

                            <select name="month">
                                <option value="01">January</option>
                                <option value="02">February</option>
                                <option value="03">March</option>
                                <option value="04">April</option>
                                <option value="05">May</option>
                                <option value="06">June</option>
                                <option value="07">July</option>
                                <option value="08">August</option>
                                <option value="09">September</option>
                                <option value="10">October</option>
                                <option value="11">November</option>
                                <option value="12">December</option>
                            </select>

                            <select name="year">
                                <option value="2016">2016</option>
                                <option value="2017">2017</option>
                                <option value="2018">2018</option>
                                <option value="2019">2019</option>
                            </select>
                        </div>
                    </div>
                    <button class="form-button" type="submit" value="Submit">Generate Clean Data CSV</button><br>
                    <%
                        if (request.getAttribute("exportSuccess") != null) {
                            out.print("<b>");
                            out.print(request.getAttribute("exportSuccess"));
                            out.print("</b>");
                        }
                    %>
                </div>
            </form>
        </div>
    </body>
</html>