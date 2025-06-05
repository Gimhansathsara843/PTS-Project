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

    <!-- CSS here -->
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
    <!-- <link rel="stylesheet" href="css/responsive.css"> -->

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
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

    </style>

</head>

<body>
<!-- ---------------------------------------------------------------------------------- -->
<!--                         HEADER                                                     -->
<!-- ---------------------------------------------------------------------------------- -->

<jsp:include page="../common/header.jsp"/>

<!-- ---------------------------------------------------------------------------------- -->
<!--                         CONTENT                                                    -->
<!-- ---------------------------------------------------------------------------------- -->

<jsp:include page="../common/navLisenceeBilling.jsp">
    <jsp:param name="activeSelection" value="View Files"/>
</jsp:include>

<jsp:include page="../common/selector.jsp">
    <jsp:param name="btnName" value="View" />
</jsp:include>

<div id="tableContainer" class="container"></div>
<div id="tableContainer1" class="container"></div>
<div id="tableContainer2" class="container"></div>

<%--<c:if test="${not empty uploadDetails}">--%>
<%--    <table class="table table-bordered">--%>
<%--        <thead>--%>
<%--        <tr>--%>
<%--            <th>File Name</th>--%>
<%--            <th>Uploaded By</th>--%>
<%--            <th>Uploaded Date</th>--%>
<%--            <th>Province Code</th>--%>
<%--            <th>File Type</th>--%>
<%--        </tr>--%>
<%--        </thead>--%>
<%--        <tbody>--%>
<%--        <c:forEach items="${uploadDetails}" var="file" >--%>
<%--            <tr>--%>
<%--                <td>${file.fileName}</td>--%>
<%--                <td>${file.uploadedBy}</td>--%>
<%--                <td>${file.uploadedDate}</td>--%>
<%--                <td>${file.provinceCode}</td>--%>
<%--                <td>${file.fileType}</td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--        </tbody>--%>
<%--    </table>--%>
<%--</c:if>--%>

<%--<c:if test="${empty uploadDetails}">--%>
<%--    <p>No uploads found for the selected bill cycle and division.</p>--%>
<%--</c:if>--%>



<div class="container mt-5">
    <h2>Uploaded Files</h2>
    <c:if test="${not empty uploadDetails}">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Province Code</th>
                <th>Uploaded Date</th>
                <th>Uploaded By</th>
                <th>File Name</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="file" items="${uploadDetails}">
                <tr>
                    <td>${file.provinceCode}</td>
                    <td>${file.uploadedDate}</td>
                    <td>${file.uploadedBy}</td>
                    <td>${file.fileName}</td>
                    <td>
<%--                        <button class="btn btn-primary btn-sm" onclick="downloadFile('${file.fileName}')">--%>
<%--                            Download--%>
<%--                        </button>--%>
    <button class="btn btn-primary btn-sm" onclick="downloadZipFile('${file.fileName}', '${file.billCycle}', '${file.division}', '${file.province}')">
        Download
    </button>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty uploadDetails}">
        <p>No uploads found for the selected criteria.</p>
    </c:if>
</div>

<!-- to give a gap to hide the footer -->
<span id="spanItem" style="min-height: 500px; display: inline-block;"></span>

</div>

<!-- Bootstrap JS and Popper.js (order matters) -->
<!-- <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script> -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<!-- FullCalendar JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.0/fullcalendar.min.js"></script>



<!-- ---------------------------------------------------------------------------------- -->
<!--                         FOOTER                                                     -->
<!-- ---------------------------------------------------------------------------------- -->

<jsp:include page="../common/footer.jsp" />

<script src="js/jquery.slicknav.min.js"></script>

<!--contact js-->
<script src="js/contact.js"></script>
<script src="js/jquery.ajaxchimp.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/mail-script.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<script src="js/main.js"></script>

<script>

    $(document).ready(function() {

        $('#uploadModal').hide();
        //-------------------------------------------------------------------
        //            drop down menu
        //-------------------------------------------------------------------
        const divDropdown = $('#divisionDropdown');
        const provDropdown = $('#provinceDropdown');
        const provinceList = JSON.parse('${provinceList}');

        divDropdown.change(function() {
            let selectedLicenseCode = divDropdown.val();
            filterProvince(selectedLicenseCode);
        });

        function filterProvince(lCode) {
            const filteredProvinces = provinceList.filter(function(province) {
                return province.licenseCode === lCode;
            });
            provDropdown.empty().append(
                filteredProvinces.map(function(province) {
                    return $('<option>', {
                        value: province.provinceCode,
                        text: province.provinceName
                    });
                })
            );
        }

        filterProvince('DD1');//initial rendering
    });
</script>
<%--<script>--%>
<%--    $(document).ready(function () {--%>
<%--        // Trigger the AJAX call when the "View" button is clicked--%>
<%--        $('#click_btn').on('click', function (e) {--%>
<%--            e.preventDefault();--%>

<%--            // Get the selected values--%>
<%--            const billCycle = $('#billCycle').val();--%>
<%--            const division = $('#divisionDropdown').val();--%>

<%--            // Validate inputs--%>
<%--            if (!billCycle || !division) {--%>
<%--                alert('Please select both Bill Cycle and Division.');--%>
<%--                return;--%>
<%--            }--%>

<%--            console.log("Bill Cycle: "+ billCycle+" Division:" + division);--%>

<%--            // Show a loading spinner--%>
<%--            $('#tableContainer').html('<div class="spinner"></div>');--%>

<%--            // Make an AJAX call to fetch the uploaded files--%>
<%--            $.ajax({--%>
<%--                url: '/PTS/viewUploadDetails', // URL of the controller method--%>
<%--                method: 'GET',--%>
<%--                data: {--%>
<%--                    billCycle: billCycle,--%>
<%--                    division: division--%>
<%--                },--%>

<%--                success: function (response) {--%>
<%--                    // Check if the response contains data--%>
<%--                    //console.log('Data fetched successfully:', response);--%>
<%--                    if (!response || response.length === 0) {--%>
<%--                        $('#tableContainer').html('<p>No uploads found for the selected criteria.</p>');--%>
<%--                        console.log("No uploads found for the selected criteria.");--%>
<%--                        return;--%>
<%--                    }--%>

<%--                    console.log("enter to the successful funation");--%>

<%--                    // Build the table dynamically--%>
<%--                    let table = `--%>
<%--                        <table class="table table-bordered">--%>
<%--                            <thead>--%>
<%--                                <tr>--%>
<%--                                    <th>File Name</th>--%>
<%--                                    <th>Uploaded By</th>--%>
<%--                                    <th>Uploaded Date</th>--%>
<%--                                    <th>Province Code</th>--%>
<%--<!--                                    <th>File Type</th>-->--%>
<%--                                    <th>Action</th>--%>
<%--                                </tr>--%>
<%--                            </thead>--%>
<%--                            <tbody>--%>
<%--                    `;--%>
<%--                    $('#tableContainer').html(table);--%>

<%--                    console.log("enter the adding data display ");--%>


<%--                        table += `--%>
<%--    <c:forEach var="uploadDetails" items="${uploadDetails}">--%>
<%--        <tr>--%>
<%--            &lt;%&ndash;<td>${uploadDetails.fileName}</td>&ndash;%&gt;--%>
<%--            &lt;%&ndash;<td>${uploadDetails.uploadedBy}</td>&ndash;%&gt;--%>
<%--            &lt;%&ndash;<td>${uploadDetails.uploadedDate}</td>&ndash;%&gt;--%>
<%--            &lt;%&ndash;<td>${uploadDetails.provinceCode}</td>&ndash;%&gt;--%>

<%--            <td>APNL.zip</td>--%>
<%--            <td>system</td>--%>
<%--            <td>2025-06-02</td>--%>
<%--            <td>WPNL</td>--%>
<%--            &lt;%&ndash;//<td>${file.fileType}</td>&ndash;%&gt;--%>
<%--                                        <a href="/downloadFile?fileName=${uploadDetails.fileName}" class="btn btn-sm btn-primary">Download</a>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--                        `;--%>

<%--                    $('#tableContainer').html(table);--%>
<%--                    console.log();--%>

<%--                    table += '</tbody></table>';--%>

<%--                    // Update the table container with the new table--%>
<%--                    $('#tableContainer').html(table);--%>
<%--                },--%>
<%--                error: function () {--%>
<%--                    alert('Failed to fetch data. Please try again.');--%>
<%--                    $('#tableContainer').html('');--%>
<%--                }--%>
<%--            });--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>

<style>
    .selectUnit label, .selectUnit select, .selectUnit input {
        font-size: small;
        margin: 0;
    }
</style>

<%--<script>--%>
<%--    function downloadFile(fileName) {--%>
<%--        const url = `/PTS/downloadFile?fileName=${fileName}`;--%>
<%--        const anchor = document.createElement('a');--%>
<%--        anchor.href = url;--%>
<%--        anchor.download = fileName;--%>
<%--        document.body.appendChild(anchor);--%>
<%--        anchor.click();--%>
<%--        document.body.removeChild(anchor);--%>
<%--    }--%>
<%--</script>--%>

<script>
    function downloadZipFile(fileName, billCycle, division, province) {
        const url = `/PTS/downloadZipFile?fileName=${fileName}&billCycle=${billCycle}&division=${division}&province=${province}`;
        console.log("Download URL: " + url);
        console.log("File Name: " + fileName +
            ", Bill Cycle: " + billCycle +
            ", Division: " + division +
            ", Province: " + province);
        window.location.href = url;
    }
</script>


</body>

</html>
