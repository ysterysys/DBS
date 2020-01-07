<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="img/favicon.ico" type="image/ico" />

        <title>IMDA NAS | Login </title>

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
                <div class="wrap-login100 p-l-50 p-r-50 p-t-77 p-b-30">
                    <form class="login100-form validate-form" action="login" method="post">
                        <span class="login100-form-title p-b-55">
                            <img src="img/imda-nas-original_bg.png" width="65%">
                        </span>

                        <div class="wrap-input100 validate-input m-b-16" data-validate = "Valid username is required">
                            <input class="input100" type="text" name="username" placeholder="Username">
                            <span class="focus-input100"></span>
                            <span class="symbol-input100">
                                <span class="fa fa-user"></span>
                            </span>
                        </div>

                        <div class="wrap-input100 validate-input m-b-16" data-validate = "Password is required">
                            <input class="input100" type="password" name="password" placeholder="Password">
                            <span class="focus-input100"></span>
                            <span class="symbol-input100">
                                <span class="fa fa-lock"></span>
                            </span>
                        </div>

                        <div class="text-center w-full p-t-20">
                            <span class="txt2">
                                ${errorMsg}
                            </span>
                        </div>

                        <div class="container-login100-form-btn p-t-25">
                            <button class="login100-form-btn" type="submit">
                                Login &nbsp;<span class="fa fa-sign-in"></span>
                            </button>
                        </div>
                        <div class="text-center w-full p-t-25">
                            <span class="txt1">
                                Forgot your password? <br>Please <a href="mailto:gslim.2016@sis.smu.edu.sg?Subject=Password%20Reset">contact administrator</a> or <a href="resetpwd.jsp">reset your password here</a>.
                            </span>
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
                    } else {
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