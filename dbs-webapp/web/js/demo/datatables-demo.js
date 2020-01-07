// Call the dataTables jQuery plugin
var data_transaction = [
    [ "71905f4d-9d83-4dcf-a101-3365a97d5e65", "DEBIT", "$20.01", "2018-01-01", "LEISURE", "276522575 FORTUNE CAT KARAOKE" ],
    [ "71905f4d-9d83-4dcf-a101-3365a97d5e65", "DEBIT", "$20.01", "2018-01-01", "LEISURE", "276522575 FORTUNE CAT KARAOKE" ],
    [ "71905f4d-9d83-4dcf-a101-3365a97d5e65", "DEBIT", "$20.01", "2018-01-01", "LEISURE", "276522575 FORTUNE CAT KARAOKE" ]
];

var data_account = [
    [ "POSB SAVINGS ACCOUNT", "1234567", "$209999.01"],
    [ "POSB SAVINGS ACCOUNT", "1234567", "$209999.01" ],
    [ "POSB SAVINGS ACCOUNT", "1234567", "$209999.01" ]
];

//Table for account history
$(document).ready(function() {
  $('#dataTable_1').DataTable(
          
            );});
//table for transaction history
$(document).ready(function() {
  $('#dataTable_2').DataTable();
    } );
    
$(document).ready(function() {
  $('#dataTable_3').DataTable({
           
    } );});



