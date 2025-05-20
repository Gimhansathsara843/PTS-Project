<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <c:choose>
        <c:when test="${not empty meterReadingFileList}">
            <table id="resultTable">
                <thead>
                    <tr>
                        <th rowspan="2">Serial No</th>
                        <th rowspan="2">Area</th>
                        <th rowspan="2">PSS</th>
                        <th rowspan="2">Status</th>
                        <th colspan="5">Meter Process Records</th>
                    </tr>
                    <tr>
                        <th>Measure Name</th>
                        <th>Current Reading</th>
                        <th>Previous Reading</th>
                        <th>Energy</th>
                        <th>Remark</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${meterReadingFileList}" var="meterReadingFile">
                    <c:choose>
                        <c:when test="${not empty meterReadingFile.meterReadingModels}">
                            <c:forEach items="${meterReadingFile.meterReadingModels}" var="processRecord" varStatus="recordStatus">
                                <tr>
                                    <c:if test="${recordStatus.index == 0}">
                                        <td rowspan="${meterReadingFile.meterReadingModels.size()}">${meterReadingFile.serialNo}</td>
                                        <td rowspan="${meterReadingFile.meterReadingModels.size()}">${meterReadingFile.area}</td>
                                        <td rowspan="${meterReadingFile.meterReadingModels.size()}">${meterReadingFile.pss}</td>
                                        <td rowspan="${meterReadingFile.meterReadingModels.size()}">${meterReadingFile.status}</td>
                                    </c:if>
                                    <td>${processRecord.measure}</td>
                                    <td class="numeric">${processRecord.currentReading}</td>
                                    <td class="numeric">${processRecord.previousReading}</td>
                                    <td class="numeric">${processRecord.energy}</td>
                                    <td></td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td>${meterReadingFile.serialNo}</td>
                                <td>${meterReadingFile.area}</td>
                                <td>${meterReadingFile.pss}</td>
                                <td>${meterReadingFile.status}</td>
                                <td class="noRec upload" colspan="5"><a href="#">Re upload</a></td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                </tbody>
            </table>

            <style>
                #resultTable{
                    width: 100%;
                    table-layout: fixed;
                    font-size: 0.9rem;
                    margin-bottom: 50px;
                }
                td{
                    padding: 4px;
                }
                .noRec{
                    color: #0f74a8;
                    font-size: 0.8rem;
                }
                .numeric{
                    text-align: right;
                }
            </style>
        </c:when>
        <c:otherwise>
            <div class="error-message">No data available</div>
            <style>
                .error-message {
                    color: #dc3545;
                    padding: 10px;
                    margin: 20px 0;
                    border: 1px solid #dc3545;
                    border-radius: 4px;
                    background-color: #f8d7da;
                }
            </style>
        </c:otherwise>
    </c:choose>
</div>
