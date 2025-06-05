<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="mb-5">
    <div class="card">
        <div class="container mt-4 mb-4" style="width: 70%;">
            <form action="${pageContext.request.contextPath}/viewUploadDetails" method="get">
                <div class="row">
                    <div class="col">
                        <div class="selectUnit">
                            <label>Bill Cycle</label>
                            <div class="input-group">
                                <input type="text" id="billCycle" name="billCycle" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="col">
                        <div class="selectUnit">
                            <label>Distribution Division</label>
                            <div class="input-group">
                                <c:if test="${not empty licenseList}">
                                    <select id="divisionDropdown" name="division" class="form-control" required>
                                        <c:forEach var="license" items="${licenseList}">
                                            <option value="${license.licenseCode}" label="${license.licenseName}" />
                                        </c:forEach>
                                    </select>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col">
                        <div class="ml-5 pt-3">
                            <button type="submit" class="btn btn-primary">View</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>

    </div>
</div>
<%--<script>--%>
<%--    $(document).ready(function() {--%>
<%--        //---------------------------------------------------------------------%>
<%--        //            drop down menu--%>
<%--        //---------------------------------------------------------------------%>
<%--        const divDropdown = $('#divisionDropdown');--%>
<%--        const provDropdown = $('#provinceDropdown');--%>
<%--        const provinceList = JSON.parse('${provinceList}');--%>
<%--        console.log(provinceList);--%>

<%--        divDropdown.change(function() {--%>
<%--            let selectedLicenseCode = divDropdown.val();--%>
<%--            filterProvince(selectedLicenseCode);--%>
<%--        });--%>

<%--        function filterProvince(lCode) {--%>
<%--            const filteredProvinces = provinceList.filter(function(province) {--%>
<%--                return province.licenseCode === lCode;--%>
<%--            });--%>
<%--            provDropdown.empty().append(--%>
<%--                filteredProvinces.map(function(province) {--%>
<%--                    return $('<option>', {--%>
<%--                        value: province.provinceCode,--%>
<%--                        text: province.provinceName--%>
<%--                    });--%>
<%--                })--%>
<%--            );--%>
<%--        }--%>

<%--        filterProvince('DD1');//initial rendering--%>

<%--    });--%>
<%--</script>--%>
<%--<style>--%>
<%--    .selectUnit label, .selectUnit select {--%>
<%--        font-size: small;--%>
<%--        margin: 0;--%>
<%--    }--%>
<%--</style>--%>

<%--<script>--%>
<%--    function viewform() {--%>
<%--        const billCycle = $('#billCycle').val();--%>
<%--        const division = $('#divisionDropdown').val();--%>
<%--        //const province = $('#provinceDropdown').val();--%>

<%--        console.log(`Bill Cycle: ${billCycle}, Division: ${division}`);--%>

<%--        &lt;%&ndash;if (!billCycle || !division || !province) {&ndash;%&gt;--%>
<%--        &lt;%&ndash;    alert('Please fill in all fields.');&ndash;%&gt;--%>
<%--        &lt;%&ndash;    return;&ndash;%&gt;--%>
<%--        &lt;%&ndash;}&ndash;%&gt;--%>

<%--        &lt;%&ndash;// Redirect to the view page with the selected parameters&ndash;%&gt;--%>
<%--        &lt;%&ndash;window.location.href = `/PTS/view?billCycle=${billCycle}&division=${division}&province=${province}`;&ndash;%&gt;--%>
<%--    }--%>
<%--</script>--%>


<%--<script>--%>



<%--    function viewform() {--%>
<%--        const billCycle = $('#billCycle').val();--%>
<%--        const division = $('#divisionDropdown').val();--%>

<%--        console.log("enter to the seletor");--%>
<%--        console.log("Bill Cycle:" + billCycle + " Division: " + division);--%>

<%--        $.ajax({--%>
<%--            url: '/PTS/viewUploadDetails',--%>
<%--            method: 'GET',--%>
<%--            data: {--%>
<%--                billCycle: billCycle,--%>
<%--                division: division--%>
<%--            },--%>
<%--            success: function(response) {--%>
<%--                console.log('Data fetched successfully:', response);--%>
<%--                console.log('Response:', response);--%>

<%--                // Ensure the table structure exists--%>
<%--                let table = $('#tableContainer table');--%>
<%--                if (table.length === 0) {--%>
<%--                    $('#tableContainer').html(`--%>
<%--                        <table class="table table-bordered">--%>
<%--                            <thead>--%>
<%--                                <tr>--%>
<%--                                    <th>Province</th>--%>
<%--                                    <th>Upload Date</th>--%>
<%--                                    <th>Uploaded By</th>--%>
<%--                                    <th>File Name</th>--%>
<%--                                    <th>Action</th>--%>
<%--                                </tr>--%>
<%--                            </thead>--%>
<%--                            <tbody></tbody>--%>
<%--                        </table>--%>
<%--                    `);--%>
<%--                    table = $('#tableContainer table');--%>
<%--                }--%>

<%--                // Clear the existing table content--%>
<%--                const tableBody = table.find('tbody');--%>
<%--                tableBody.empty();--%>

<%--                // Populate the table with the fetched data--%>
<%--                response.forEach(upload => {--%>
<%--                    const row = `--%>
<%--<tr>--%>
<%--    <td>${model.fileName}</td>--%>
<%--    <td>${model.uploadedBy}</td>--%>
<%--    <td>${model.uploadedDate}</td>--%>
<%--    <td>${model.provinceCode}</td>--%>
<%--    <td>${model.fileType}</td>--%>
<%--                        <td>--%>
<%--                            <a href="/downloadFile?fileName=${model.fileName}" class="btn btn-sm btn-primary">Download</a>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                `;--%>
<%--                    tableBody.append(row);--%>
<%--                });--%>

<%--                console.log(response);--%>

<%--                console.log('Table updated successfully');--%>
<%--            },--%>
<%--            error: function(err) {--%>
<%--                console.error('Error fetching data:', err);--%>
<%--                alert('Failed to fetch data. Please try again.');--%>
<%--            }--%>
<%--        });--%>
<%--    }--%>
<%--</script>--%>