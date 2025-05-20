<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>CEB Power Trading System</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- <link rel="manifest" href="site.webmanifest"> -->
    <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    <!-- Place favicon.ico in the root directory -->

    <!-- CSS here -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/magnific-popup.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/themify-icons.css">
    <link rel="stylesheet" href="css/nice-select.css">
    <link rel="stylesheet" href="css/flaticon.css">
    <link rel="stylesheet" href="css/gijgo.css">
    <link rel="stylesheet" href="css/animate.css">
    <link rel="stylesheet" href="css/slicknav.css">
    <link rel="stylesheet" href="css/style.css">
    <!-- <link rel="stylesheet" href="css/responsive.css"> -->

    <link href='//fonts.googleapis.com/css?family=Open+Sans:400,600,700,300|Titillium+Web:200,300,400'
          rel='stylesheet' type='text/css'>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <script type="text/javascript">

        $(document).ready(function () {
            // Show the modal after 5 seconds
            setTimeout(function () {
                $('#loginModal').modal('show');
            }, 2500);
        });

        $(document).ready(function () {

            $('#closeLoginModal').on('click', function() {
                window.location.href = '/PTS/WelcomePTS';
            });
        });
    </script>
    <style>
        /* This will not be necessary if you're using Bootstrap modal as it handles the background */
        .modal-backdrop.show {
            filter: blur(10px);
            -webkit-filter: blur(10px);
        }
        /* Custom CSS for modal positioning */

        @media (max-width: 576px) {
            .copy_right {
                font-size: 0.7rem; /* even smaller font size for very small devices */
            }
        }



    </style>
</head>

<body>

<c:if test="${not empty errorMsg}">
    <div class="alert alert-danger text-center" style="margin: 20px;">
        <strong>${errorMsg}</strong>
    </div>
</c:if>

<!-- header-start -->
<header>
    <div class="header-area ">
        <div id="sticky-header" class="main-header-area">
            <div class="container-fluid p-0">
                <div class="row align-items-center no-gutters">
                    <div class="col-xl-5 col-lg-6">
                        <div class="main-menu  d-none d-lg-block">

                        </div>
                    </div>
                    <div class="col-xl-2 col-lg-2">
                        <!-- <div class="logo-img">
                            <a href="WelcomeCBRS">
                                <img src="img/logo.png" alt="">
                            </a>
                        </div> -->
                    </div>
                    <div class="col-12">
                        <!-- <div class="mobile_menu d-block d-lg-none"></div> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- header-end -->

<!-- bradcam_area_start -->
<div class="slider_area">
    <div>
        <div class="single_slider d-flex align-items-center justify-content-center welcomelogin_img">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="slider_text text-center">
                            <h3>Welcome to Energy Marketing System</h3>
                            <p>Ceylon Electricity Board</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Login Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="margin:auto; width:80%; margin-top: 100px;" role="document">
        <div class="modal-content" style="width: 90%; height:65%; margin:auto;">
            <div class="modal-header" style="display: block;">
                <div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="closeLoginModal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div><br/>
                <div class="col text-center">
                    <img src="${pageContext.request.contextPath}/resources/img/logo.png" alt="Logo" class="modal-logo">
                </div>
                <div>
                    <h2 style="text-align:center" class="modal-title" id="loginModalLabel">Energy Marketing System
                    </h2>
                </div>
                <c:if test="${model.errorMsg != null}">
                    <div class="alert alert-success" id="success-alert">
                        <strong>${model.errorMsg} </strong>
                    </div>
                </c:if>
                <script>
                </script>
            </div>
            <div class="modal-body">
                <form id="loginForm" action="home" method="post" modelAttribute="cbrsModel">
                    <div class="form-group">
                        <label for="epfNumber">User Name</label>
                        <input type="text" class="form-control" id="epfNumber" path="username" name="username" aria-describedby="epfNumberHelp">
                        <div id="epfError" class="text-danger" style="display:none;">User name is required.</div>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" path="password" aria-describedby="passwordHelp">
                        <div id="passwordError" class="text-danger" style="display:none;">Password is required.</div>
                    </div>


                    <button type="submit" class="btn btn-primary" style="width: 100%; margin: 0px 0;">Login</button>
                </form>
                <div class="modal-footer" style="border-top: none; padding: 0;">
                    <p class="copy_right" style="margin: 0px 0; font-size: 0.8rem; text-align: center; width: 100%;">

                        <!-- <script>document.write(new Date().getFullYear());</script> -->
                        <a href="https://www.ceb.lk" target="_blank">Ceylon Electricity Board</a> | Version 1.0
                    </p>
                </div>

            </div>
        </div>
    </div>
</div>


<!-- JS here -->
<script src="js/vendor/modernizr-3.5.0.min.js"></script>
<script src="js/vendor/jquery-1.12.4.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/isotope.pkgd.min.js"></script>
<script src="js/ajax-form.js"></script>
<script src="js/waypoints.min.js"></script>
<script src="js/jquery.counterup.min.js"></script>
<script src="js/imagesloaded.pkgd.min.js"></script>
<script src="js/scrollIt.js"></script>
<script src="js/jquery.scrollUp.min.js"></script>
<script src="js/wow.min.js"></script>
<script src="js/nice-select.min.js"></script>
<script src="js/jquery.slicknav.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/plugins.js"></script>
<script src="js/gijgo.min.js"></script>

<!--contact js-->
<script src="js/contact.js"></script>
<script src="js/jquery.ajaxchimp.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/mail-script.js"></script>
<script src="js/main.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

</body>

</html>