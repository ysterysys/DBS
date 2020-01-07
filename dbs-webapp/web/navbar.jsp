<div class="col-md-3 left_col">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <a href="DashboardReport.jsp" class="site_title"><i class="fa fa-desktop"></i> <span><img src="img/nas_text.png" height="90%"></span></a>
        </div>

        <div class="clearfix"></div>

        <!-- menu profile quick info -->
        <div class="profile clearfix">
            <div class="profile_info">
                <span>Welcome back,</span>
                <h2><% String username = (String) session.getAttribute("username");%><%=username%> !</h2>
            </div>
        </div>
        <!-- /menu profile quick info -->

        <br />

        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <h3>Main</h3>
                <ul class="nav side-menu">
                    <!--                                    <li><a href="DashboardReport.jsp"><i class="fa fa-home"></i> Home</a></li>-->

                    <li><a><i class="fa fa-dashboard"></i> Dashboard <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="DashboardReport.jsp">Cleaning Report</a></li>
                        </ul>
                    </li>

                    <li><a><i class="fa fa-table"></i> Analysis <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="APAnalysis.jsp">AP Analysis</a></li>
                            <li><a href="HistoricalAnalysis.jsp">Historical Analysis</a></li>

                        </ul>
                    </li>

                    <li><a><i class="fa fa-upload"></i> Upload<span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="Bootstrap.jsp">Upload New Logs</a></li>
                            <li><a href="RevertBootstrap.jsp">Revert Past Uploads</a></li>
                            <!--<li><a href="ManualBootstrap.jsp">Re-upload Error Logs</a></li>-->
                        </ul>
                    </li>
                    <li><a><i class="fa fa-download"></i> Export <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="CleanExport.jsp">Export Clean Logs</a></li>
                            <li><a href="ErrorExport.jsp">Export Erroneous Logs</a></li>
                        </ul>
                    </li>

                    <li><a href="Audit.jsp"><i class="fa fa-edit"></i> Audit Trail</a></li>

                    <li><a href="alertsSettings.jsp"><i class="fa fa-exclamation"></i> Alerts</a></li>
                </ul>
            </div>

        </div>
        <!-- /sidebar menu -->

        <!-- /menu footer buttons -->
    </div>
</div>

<!-- top navigation -->

<div class="top_nav">
    <div class="nav_menu">
        <nav>
            <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <li class="">
                    <a href="Logout.jsp" >
                        Log Out &nbsp;<i class="fa fa-sign-out"></i> </a>
                </li>

                <li class="">
                    <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <%=username%>
                        <i class=" fa fa-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-usermenu pull-right">
                        <li><a href="ChangePassword.jsp"> Change Password</a></li>
                        <li><a href="alertsSettings.jsp"> Alerts Settings</a></li>
                        <li><a href="mailto:gslim.2016@smu.edu.sg">Help</a></li>
                    </ul>
                </li>

            </ul>
        </nav>
    </div>
</div>
<!-- /top navigation -->
