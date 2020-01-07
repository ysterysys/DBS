<%-- 
    Document   : resetupdatepwd
    Created on : 17 Mar, 2019, 4:55:40 PM
    Author     : limgeokshanmb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="img/favicon.ico" type="image/ico" />

        <title>IMDA NAS | Reset Password - Update </title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <!-- NProgress -->
        <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <!--===============================================================================================-->
    </head>
    <body>

        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login200 p-l-50 p-r-50 p-t-77 p-b-30">
                    <% if (request.getAttribute("email")==null){
                       response.sendRedirect("resetpwd.jsp"); 
                    }%>
                    <form class="login100-form validate-form" action="ResetUpdatePasswordController" method="post">
                        <span class="login100-form-title p-b-55">
                            <a href="Login.jsp"><img src="img/imda-nas-original_bg.png" width="60%"></a>
                        </span>
                        <div class="text-center w-full p-b-20">
                            <h2>Reset Password</h2>
                        </div>
                        <div class="p-b-10">
                            Dear ${email},<br> 
                            Please input your password below:
                        </div>

                        <div class="wrap-input100 validate-input m-b-16" data-validate = "Please ensure both password fields match!">
                            <input class="input100" type="password" id="password1" name="password1" placeholder="Enter your Password">
                            <span class="focus-input100"></span>
                            <span class="symbol-input100">
                                <span class="fa fa-key"></span>
                            </span>
                        </div>

                        <div class="wrap-input100 validate-input m-b-16" data-validate = "Please ensure both password fields match!">
                            <input class="input100" type="password" id="password2" name="password2" placeholder="Enter your Password again">
                            <span class="focus-input100"></span>
                            <span class="symbol-input100">
                                <span class="fa fa-key"></span>
                            </span>
                        </div>
                            
                        <input id="email" type="hidden" name="email" value="${email}"> 
                        <div class="container-login100-form-btn p-t-25">
                            <button class="login100-form-btn" type="submit">
                                Reset Password &nbsp;<span class="fa fa-key"></span>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="vendors/jquery/dist/jquery.min.js"></script>
        <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
        <script>
            (function ($) {
                "use strict";


                /*==================================================================
                 [ Validate ]*/
                var input = $('.validate-input .input100');

                $('.validate-form').on('submit', function () {
                    var check = true;

                    for (var i = 0; i < input.length; i++) {
                        if (validate(input[i]) == false) {
                            showValidate(input[i]);
                            check = false;
                        }
                    }

                    return check;
                });

                $('.validate-form .input100').each(function () {
                    $(this).focus(function () {
                        hideValidate(this);
                    });
                });
                function validate(input) {
                    if ($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
                        if ($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                            return false;
                        }
                    } else if ($(input).attr('type') == 'password') {
                        if($('#password1').val() != $('#password2').val()){
                            return false;
                        }
                    }else {
                        if ($(input).val().trim() == '') {
                            return false;
                        }
                    }
                }

                function showValidate(input) {
                    var thisAlert = $(input).parent();

                    $(thisAlert).addClass('alert-validate');
                }

                function hideValidate(input) {
                    var thisAlert = $(input).parent();

                    $(thisAlert).removeClass('alert-validate');
                }



            })(jQuery);
        </script>


    </body>
</html>