<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="nav bg-light bg-opacity-75">
    <div class="d-flex justify-content-around align-items-center w-100 my-1">

        <div class="nav-item text-center" style="width: 150px;">
            <a class="nav-link d-flex flex-column align-items-center ${param.activeSelection eq 'Dashboard' ? 'activeSelection' : ''}" href="licenseeBillingHome">
                <div class="icon-container">
                    <img src="./icons/dashboard.svg" alt="Upload Icon" style="width: 40px; height: 40px; margin-bottom: 3px;">
                </div>
                <span style="font-size: 0.8rem;">Dashboard</span>
            </a>
        </div>

        <div class="nav-item text-center">
            <a class="nav-link d-flex flex-column align-items-center ${param.activeSelection eq 'Upload Meter Points' ? 'activeSelection' : ''}" href="uploadFile">
                <div class="icon-container">
                    <img src="./icons/uploadfile.svg" alt="Upload Icon" style="width: 40px; height: 40px; margin-bottom: 3px;">
                </div>
                <span style="font-size: 0.8rem;">Upload Meter Points</span>
            </a>
        </div>

        <div class="nav-item text-center">
            <a class="nav-link d-flex flex-column align-items-center ${param.activeSelection eq 'View Upload Meter Points' ? 'activeSelection' : ''}" href="viewUploadFile">
                <div class="icon-container">
                    <img src="./icons/view.svg" alt="View Icon" style="width: 40px; height: 40px;margin-bottom: 3px;">
                </div>
                <span style="font-size: 0.8rem;">View Upload Meter Points</span>
            </a>
        </div>

        <div class="nav-item text-center">
            <a class="nav-link d-flex flex-column align-items-center ${param.activeSelection eq 'Process Meter Reading' ? 'activeSelection' : ''}" href="processMeterReading">
                <div class="icon-container">
                    <img src="./icons/process.svg" alt="Process Icon" style="width: 40px; height: 40px;margin-bottom: 3px;">
                </div>
                <span style="font-size: 0.8rem;">Process Meter Reading</span>
            </a>
        </div>

        <div class="nav-item text-center">
            <a class="nav-link d-flex flex-column align-items-center ${param.activeSelection eq 'View Meter Reading' ? 'activeSelection' : ''}" href="viewMeterReading">
                <div class="icon-container">
                    <img src="./icons/view.svg" alt="Search Icon" style="width: 40px; height: 40px;margin-bottom: 3px;">
                </div>
                <span style="font-size: 0.8rem;">View Meter Readings</span>
            </a>
        </div>
    </div>

    <style>
        .nav-item:hover {
            background-color: rgba(0, 0, 0, 0.1); /* Adjust the color and opacity as needed */
            transition: background-color 0.3s ease; /* Smooth transition effect */
            cursor: pointer;
        }
        .activeSelection{
            background-color: rgba(0, 0, 0, 0.1);
        }
    </style>
</div>
<div div class="d-flex justify-content-center w-100 py-1" style="background-color: #e9e8e8;">
    ${param.activeSelection}
</div>