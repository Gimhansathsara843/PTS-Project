<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>Power Trading System</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Include jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <!-- Include jQuery UI -->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <!-- <link rel="manifest" href="site.webmanifest"> -->
    <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png" />
    <!-- Place favicon.ico in the root directory -->

    <!-- ================================================================================== -->
    <!--                                    CSS                                             -->
    <!-- ================================================================================== -->
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/owl.carousel.min.css" />
    <link rel="stylesheet" href="css/magnific-popup.css" />
    <link rel="stylesheet" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/themify-icons.css" />
    <link rel="stylesheet" href="css/nice-select.css" />
    <link rel="stylesheet" href="css/flaticon.css" />
    <link rel="stylesheet" href="css/gijgo.css" />
    <link rel="stylesheet" href="css/animate.css" />
    <link rel="stylesheet" href="css/slicknav.css" />
    <link rel="stylesheet" href="css/style.css" />
    <link href="//fonts.googleapis.com/css?family=Open+Sans:400,600,700,300|Titillium+Web:200,300,400"
          rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.0/fullcalendar.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css">

    <style type="text/css">
        div#map_container {
            width: 100%;
            height: 500px;
            border-radius: 5px;
        }

        .center {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 10vh;
        }
    </style>
    <style>
        #content,
        #rates_holder {
            display: none;
            /* Hide content by default */
        }

        #loading,
        #full_loading {
            display: none;
            /* Show loading animation by default */
            text-align: center;
        }

        .spinner {
            border: 4px solid rgba(0, 0, 0, 0.1);
            border-radius: 50%;
            border-top: 4px solid #3498db;
            width: 40px;
            height: 40px;
            animation: spin 1s linear infinite;
            margin: 20px auto;
        }

        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }

            100% {
                transform: rotate(360deg);
            }
        }

        .caret-icon {
            font-size: 1.0em;
            margin-right: 5px;
            cursor: pointer;

        }

        .avbl_section {
            display: flex;
        }

        .date_holder {
            margin-top: 10px;
            margin-bottom: 10px;
            width: 25%;
        }

        .date_container {
            display: flex;
            width: 400%;
        }

        .full_container {
            overflow-x: scroll;
        }

        .fulldate_container {
            display: flex;
            width: 400%;
        }

        .fulldate_holder {
            margin-top: 10px;
            margin-bottom: 10px;
        }

        /* .date_box {
          height: 50px;
          width: 50px;
          border-radius: 6px;
          border: 2px solid #d1d0d0;
          text-align: center;
          display: flex;
          align-items: center;
          justify-content: center;
          margin-left: auto;
          margin-right: auto;
          font-size: 0.7em;
          font-family: Verdana, Geneva, Tahoma, sans-serif;
        }
*/
        .date_box {
            height: 50px;
            width: 50px;
            border-radius: 50%;
            border: 2px solid #d1d0d0;
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-left: auto;
            margin-right: auto;
            font-size: 0.7em;
            font-family: Verdana, Geneva, Tahoma, sans-serif;
        }

        .available {
            background-color: rgb(3, 213, 3);
        }

        .unavailable {
            background-color: rgb(252, 22, 22);
        }


        .eve_available {
            background-image: linear-gradient(to right, rgba(3, 213, 3, 0) 50%, rgb(3, 213, 3) 50%);
        }

        .eve_unavailable {
            background-image: linear-gradient(to right, rgba(3, 213, 3, 0) 50%, rgb(252, 22, 22) 50%);

        }

        .mor_available {
            background-image: linear-gradient(to right, rgb(3, 213, 3) 50%, rgba(3, 213, 3, 0) 50%);

        }

        .mor_unavailable {
            background-image: linear-gradient(to right, rgb(252, 22, 22) 50%, rgba(3, 213, 3, 0) 50%);

        }

        .rate_tbl {
            width: 50%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th {
            background-color: #4F81BD;
            color: white;
        }

        th,
        td {
            border: 1px solid #dddddd;
            padding: 8px;
            text-align: left;
        }

        td {
            color: #003868;
        }

        /* Alternate row colors */
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .center {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .people_container {
            margin-top: 15px;
            width: 100%;
            display: block;
            align-items: center;
            justify-content: center;
        }

        .people_title {
            margin-top: 20px;
        }

        .visitorCount {
            width: 100%;
            margin-left: auto;
            margin-right: auto;
        }

        .booking_container {
            width: 60%;
        }

        .boxed-btn3 {
            cursor: pointer;
        }

        .btn_unavailable {
            cursor: not-allowed;
        }

        .btn_available {
            cursor: pointer;
        }

        .instructions {
            margin-left: 10%;
            margin-top: 5%;
            margin-bottom: 5%;
        }

        .errorMsg {
            color: red;
            margin-bottom: 3px;
            font-weight: 400;
        }

        .dateDisplay {
            color: #fff;
            text-decoration: none;
            font-family: "Roboto", "Helvetica", "Arial", sans-serif;
        }

        @media screen and (min-width: 200px) and (max-width: 900px) {
            .fulldate_container {
                width: 400%;
            }

            .avbl_section {
                display: block;
            }

            .date_container {
                width: 400%;
            }

            .date_holder {
                padding-left: 35%;
                width: 50%;
            }

            .instructions {
                margin-left: 4%;
                margin-right: 4%;
            }
        }

        @media screen and (max-width: 768px) {
            .rates_holder {
                margin-top: 10px;
                margin-left: auto;
                margin-right: auto
            }
        }

        @media screen and (min-width: 200px) and (max-width: 900px) {
            .fulldate_container {
                width: 100%;
            }

            .avbl_section {
                display: block;
            }

            .date_container {
                width: 100%;
            }

            .date_holder {
                padding-left: 0;
                width: 100%;
            }

            .date_box {
                width: 80px;
                height: 80px;
                font-size: 0.8em;
            }

            .instructions {
                margin-left: 4%;
                margin-right: 4%;
            }
        }


        .date_holder p[id^="room_id_"] {
            margin-left: 2%;
        }

        .fulldate_holder {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        #full_container .fulldate_holder .date_box {
            margin: 3px;
        }

        @media screen and (min-width: 200px) and (max-width: 900px) {

            .date_box {
                width: 80px;
                height: 80px;
                font-size: 0.8em;
            }
        }
    </style>

</head>

<body>
<!-- ---------------------------------------------------------------------------------- -->
<!--                         HEADER                                                     -->
<!-- ---------------------------------------------------------------------------------- -->

<header>
    <div class="header-area">
        <div id="sticky-header" class="main-header-area">
            <div class="container-fluid p-0">
                <div class="row align-items-center no-gutters">
                    <div class="col-xl-5 col-lg-6">
                        <div class="main-menu d-none d-lg-block">
                            <nav>
                                <ul id="navigation">
                                    <li><a href="WelcomePTS">home</a></li>
                                    <li><a href="WelcomePTS">LogOut &nbsp;
                                        <i style="font-size: 20px;" class="fa fa-sign-out fa-lg"></i>
                                    </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    <div class="col-xl-2 col-lg-2">
                        <div class="logo-img">
                            <a href="WelcomeCBRS">
                                <img src="img/logo.png" alt="" />
                            </a>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="mobile_menu d-block d-lg-none"></div>
                    </div>
                    <div class="dateDisplayContainer col-xl-12 col-lg-12 main-menu" id="dateDisplayContainer">
                        <span class="dateDisplay" id="datetimeDisplay"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- header-end -->

<!-- bradcam_area_start -->
<div class="room_details_bradcam_area room_details_breadcam_bg">
    <h3>Process Meter Reading</h3>
</div>
<!-- bradcam_area_end -->



<!-- ---------------------------------------------------------------------------------- -->
<!--                         CONTENT                                                    -->
<!-- ---------------------------------------------------------------------------------- -->

<div class="offers_area padding_top">
    <div class="container">
        <div class="row">
            <!-- left coloumn -->
            <div class="col-xl-6 col-md-6">
                <form:form id ="myForm"  method="post" modelAttribute="dropDownMenu" action="/processTestResult">

                    <div class="card">
                        <div class="card-body">
                            
                            <div class="room-type">
                                <label>Bill cycle</label>
                                <div class="input-group">
                                    <input  type="text" id="billCycle" name="billCycleNo" class="form-control">
                                    </select>
                                </div>
                            </div>

                            <div class="room-type">
                                <label>Distribution division</label>
                                <div class="input-group">
                                    <form:select id="divisionDropdown" name="licenseCode" class="form-control" path="division" >
                                        <form:option value="DD1" label="Distribution Division 1" />
                                        <form:option value="DD2" label="Distribution Division 2" />
                                        <form:option value="DD3" label="Distribution Division 3" />
                                        <form:option value="DD4" label="Distribution Division 4" />
                                        <form:option value="LECO" label="LECO" />
                                    </form:select>
                                </div>
                            </div>


                            <div class="room-type" >
                                <div class="col-xl-12 mb-3 mt-2 center ">
                                    <input type="submit" class="btn btn-primary" id="process_btn" value="Process">
                                </div>
                                <label id="processMsg"></label>
                                <div id="errLog"></div>
                            </div>

                        </div>
                    </div>

                </form:form>

            </div>
        </div>
    </div>

</div>

<!-- ---------------------------------------------------------------------------------- -->
<!--                                         TABLE                                      -->
<!--                                                                                    -->

<div class="container">
    <table id="resultTable">
        <thead>
        <tr>
            <th>SERIAL NO</th>
            <th>PSS</th>
            <th>STATUS</th>
            <th>FILE</th>
            <th>MEASURE</th>
            <th>CURRENT READING</th>
            <th>PREVIOUS READING</th>
            <th>ENERGY</th>
        </tr>
        </thead>
        <tbody id="resultTableBody">
        </tbody>
    </table>
</div>
<style>
    #resultTable{
        width: 100%;
        table-layout: fixed;
        font-size: 0.9rem;
        margin-bottom: 50px;
    }
    .path, .serialNo {
        width: 200px;
        word-wrap: break-word;
        overflow-wrap: break-word;
        white-space: normal;
    }
</style>


<!-- ---------------------------------------------------------------------------------- -->
<!--                         FOOTER                                                     -->
<!-- ---------------------------------------------------------------------------------- -->

<footer class="footer">
    <div class="footer_top">
        <div class="container">
            <div class="row">
                <div class="col-xl-4 col-md-4 col-lg-4"
                     style="display: flex; flex-direction: column; align-items: flex-end;">
                    <div class="footer_widget">
                        <h3 class="footer_title" style="text-align: left;">
                            address
                        </h3>
                        <p class="footer_text"> 50 Sir Chittampalam A Gardiner Mawatha Colombo
                            00200,
                            00700</p>
                        <a href="contact" class="line-button">Get Direction</a>
                    </div>
                </div>
                <div class="col-xl-4 col-md-4 col-lg-4"
                     style="display: flex; flex-direction: column; align-items: flex-end;">
                    <div class="footer_widget" style="text-align: left;">
                        <h3 class="footer_title">
                            Reservation
                        </h3>
                        <p class="footer_text">+94 112 451 098<br>
                            wm@ceb.lk</p>
                    </div>
                </div>
                <div class="col-xl-4 col-md-4 col-lg-4"
                     style="display: flex; flex-direction: column; align-items: flex-end;">
                    <div class="footer_widget" style="text-align: left;">
                        <h3 class="footer_title">
                            Navigation
                        </h3>
                        <ul style="list-style: none; padding: 0; text-align: left;">
                            <li><a href="WelcomeCBRS">Home &nbsp;<i style="font-size: 20px;"
                                                                    class="fa fa-home fa-lg"></i></a></li>

                            <li><a href="admindashboard" id="goBackLink2">Admin Dashboard
                                &nbsp;<i style="font-size: 20px;"
                                         class="fa fa-user fa-lg"></i></a></li>

                            <li><a href="AdminCBRS">LogOut &nbsp;<i style="font-size: 20px;"
                                                                    class="fa fa-sign-out fa-lg"></i></a></li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="copy-right_text">
        <div class="container">
            <div class="footer_border"></div>
            <div class="row">
                <div class="col-xl-8 col-md-7 col-lg-9">
                    <p class="copy_right">
                        Copyright &copy;
                        <script>
                            document.write(new Date().getFullYear());
                        </script>
                        All rights reserved | <a href="https://www.ceb.lk" target="_blank"> Ceylon
                        Electricity Board</a>
                    </p>
                </div>

            </div>
        </div>
    </div>
</footer>




<!-- ================================================================================== -->
<!--                      JAVASCRIPT libraries                                          -->
<!-- ================================================================================== -->

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.0/fullcalendar.min.js"></script>

<script src="js/jquery.slicknav.min.js"></script>

<!--contact js-->
<script src="js/contact.js"></script>
<script src="js/jquery.ajaxchimp.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/mail-script.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<script src="js/main.js"></script>

</body>

</html>