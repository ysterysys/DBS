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

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>DBS - Dashboard</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

  <!-- Page level plugin CSS-->
  <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin.css" rel="stylesheet">

</head>

<body id="page-top">

  <nav class="navbar navbar-expand navbar-dark bg-dark static-top">

    <img src="https://www.dbs.com.sg/iwov-resources/flp/images/dbs_logo.svg" class="navbar-brand mr-1" ></img>

    <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
      <i class="fas fa-bars"></i>
    </button>

    <!-- Navbar Search -->
    <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
        <div class="input-group-append">
          <button class="btn btn-primary" type="button">
            <i class="fas fa-search"></i>
          </button>
        </div>
      </div>
    </form>

    <!-- Navbar -->
    <ul class="navbar-nav ml-auto ml-md-0">
      <li class="nav-item dropdown no-arrow mx-1">
        <a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-envelope fa-fw"></i>
          <span class="badge badge-danger">7</span>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="messagesDropdown">
            <a class="dropdown-item" href="#">Inbox</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">Message 1</a>
            <a class="dropdown-item" href="#">Message 2</a>
            <a class="dropdown-item" href="#">Message 3</a>

        </div>
      </li> 
      <li class="nav-item dropdown no-arrow">
        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-user-circle fa-fw"></i>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
          <a class="dropdown-item" href="#">Account Information</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">Logout</a>
        </div>
      </li>
    </ul>

  </nav>

  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="sidebar navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="index.html">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Dashboard</span>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="account.jsp">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>Account Information</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="transaction.html">
          <i class="fas fa-fw fa-table"></i>
          <span>Transaction History</span></a>
      </li>
    </ul>

    <div id="content-wrapper">

      <div class="container-fluid">

        <!-- Breadcrumbs-->
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="#">Dashboard</a>
          </li>
          <li class="breadcrumb-item active">Overview</li>
        </ol>

        
        <div class="card mb-3">
          <div class="card-header">
            <i class="fas fa-table"></i>
            <%session = request.getSession();
            String id = (String)session.getAttribute("id");
            ArrayList<String> accountInfo = RestMethod.getUserDetail(id);%>
            Welcome, <%=accountInfo.get(2) + " " + accountInfo.get(3) + "!"%><br>
          <%="Your last login: " + accountInfo.get(4)%></div>
          
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                  <tr>
                     <%ArrayList<String> accountDeposit = RestMethod.getDepositAccounts(id);%>
                    <th>Account Type</th>
                    <th>Account Name</th>
                    <th>Account Number</th>
                    <th>Balance</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td><%=accountDeposit.get(1)%></td>
                    <td><%=accountDeposit.get(2)%></td>
                    <td><%=accountDeposit.get(3)%></td>
                    <td><%=accountDeposit.get(4)%></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        
        
        
        
        
        
        
        
        
        
        
        
        <div class="card mb-3">
          <div class="card-header">
            <i class="fas fa-table"></i>
            Account Overview</div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                  <tr>
                    <th>Account Type</th>
                    <th>Account Number</th>
                    <th>Balance</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>POST SAVINGS ACCOUNT</td>
                    <td>1234567</td>
                    <td>$20999.01</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <!-- Area Chart Example-->
        <div class="card mb-3">
          <div class="card-header">
            <i class="fas fa-chart-area"></i>
            Monthly Expenditure/ Account Info Example</div>
          <div class="card-body">
            <canvas id="myAreaChart" width="100%" height="30"></canvas>
          </div>
        </div>

        <!-- DataTables Example -->
        <div class="card mb-3">
          <div class="card-header">
            <i class="fas fa-table"></i>
            Transaction History Example</div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                  <tr>
                    <th>Transaction ID</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Tag</th>
                    <th>Reference Number</th>
                  </tr>
                </thead>
                <tfoot>
                  <tr>
                    <th>Transaction ID</th>
                    <th>Type</th>
                    <th>Amount(SGD)</th>
                    <th>Date</th>
                    <th>Tag</th>
                    <th>Reference Number</th>
                  </tr>
                </tfoot>
                <tbody>
                  <tr>
                    <td>71905f4d-9d83-4dcf-a101-3365a97d5e65</td>
                    <td>DEBIT</td>
                    <td>$20.01</td>
                    <td>2018-01-01</td>
                    <td>LEISURE</td>
                    <td>276522575 FORTUNE CAT KARAOKE</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>

      </div>
      <!-- /.container-fluid -->

      <!-- Sticky Footer -->
      <footer class="sticky-footer">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright © Your Website 2019</span>
          </div>
        </div>
      </footer>

    </div>
    <!-- /.content-wrapper -->

  </div>
  <!-- /#wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Page level plugin JavaScript-->
  <script src="vendor/chart.js/Chart.min.js"></script>
  <script src="vendor/datatables/jquery.dataTables.js"></script>
  <script src="vendor/datatables/dataTables.bootstrap4.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin.min.js"></script>

  <!-- Demo scripts for this page-->
  <script src="js/demo/datatables-demo.js"></script>
  <script src="js/demo/chart-area-demo.js"></script>

</body>

</html>
