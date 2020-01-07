<header class="main-header">
    <span>
        <a href="MainMenu.jsp" class="logo">IMDA NAS</a>
    </span>

    <div>
        <a href="Login.jsp" class="nav-logout">Sign Out</a>
    </div>
</header>

<section class="main-sidebar">
    <ul class="sidebar-menu">
        <li class="sidebar-header">MAIN NAVIGATION</li>
        <li>
            <a href="DashboardReport.jsp">
                <i class="fa fa-dashboard"></i> <span>Cleaning Report</span>
            </a>
        </li>
        <li>
            <a href="#">
                <i class="fa fa-flag"></i> <span>Data Export</span> <i class="fa fa-angle-left pull-right"></i>
            </a>
            <ul class="sidebar-submenu">
                <li><a href="ErrorDataExport.jsp"><i class="fa fa-circle-o"></i> Error Data Export</a></li>
                <li><a href="CleanDataExport.jsp"><i class="fa fa-circle-o"></i> Clean Data Export</a></li>
            </ul>
        </li>
        <li>
            <a href="#">
                <i class="fa fa-pie-chart"></i> <span>AP Analysis</span>
            </a>
        </li>
        <li>
            <a href="#">
                <i class="fa fa-flag"></i> <span>Historical Analysis</span> <i class="fa fa-angle-left pull-right"></i>
            </a>
            <ul class="sidebar-submenu">
                <li><a href="#"><i class="fa fa-circle-o"></i> AP Flags</a></li>
                <li><a href="#"><i class="fa fa-circle-o"></i> Backhaul Flags</a></li>
            </ul>
        </li>
        <li>
            <a href="#">
                <i class="fa fa-flag"></i> <span>User Settings</span> <i class="fa fa-angle-left pull-right"></i>
            </a>
            <ul class="sidebar-submenu">
                <li><a href="ChangePassword.jsp"><i class="fa fa-circle-o"></i> Change Password</a></li>
                <li><a href="#"><i class="fa fa-circle-o"></i> Adjust Alerts Threshold</a></li>
            </ul>
        </li>
    </ul>
</section>

<script>$.sidebarMenu($('.sidebar-menu'));</script>