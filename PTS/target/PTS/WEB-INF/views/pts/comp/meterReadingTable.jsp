<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <c:choose>
        <c:when test="${not empty meterReadingFileList}">
            <table id="resultTable">
                <thead>
                    <tr>
                        <th>Serial No</th>
                        <th>Area</th>
                        <th>PSS</th>
                        <th>Status</th>
                        <th>Measure</th>
                        <th>Current Reading</th>
                        <th>Previous Reading</th>
                        <th>Energy</th>
                        <th>Exp/Imp</th>
                        <th>Coincident peak</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${meterReadingFileList}" var="meterReadingFile">
                    <c:choose>
                        <c:when test="${not empty meterReadingFile.meterReadingRecordModels}">
                            <c:forEach items="${meterReadingFile.meterReadingRecordModels}" var="processRecord" varStatus="status">
                                <tr>
                                    <c:if test="${status.index == 0}">
                                        <td rowspan="6">${meterReadingFile.serialNo}</td>
                                        <td rowspan="6">${meterReadingFile.area}</td>
                                        <td rowspan="6">${meterReadingFile.pss}</td>
                                        <td rowspan="6">${meterReadingFile.status}</td>
                                    </c:if>
                                    <td>${processRecord.measure}</td>
                                    <td class="numeric">${processRecord.currentReading}</td>
                                    <td class="numeric">${processRecord.previousReading}</td>
                                    <td class="numeric">${processRecord.energy}</td>
                                    <c:choose>
                                        <c:when test="${status.index < 3}">
                                            <c:if test="${status.index == 0}">
                                                <td class="numeric" rowspan="3">${meterReadingFile.exportEnergy}</td>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${status.index == 3}">
                                                <td class="numeric" rowspan="3">${meterReadingFile.importEnergy}</td>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${status.index < 3}">
                                            <c:if test="${status.index == 0}">
                                                <td class="numeric" rowspan="3">${meterReadingFile.coincidentPeak}</td>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${status.index == 3}">
                                                <td class="numeric" rowspan="3">...</td>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td>${meterReadingFile.serialNo}</td>
                                <td>${meterReadingFile.area}</td>
                                <td>${meterReadingFile.pss}</td>
                                <td>${meterReadingFile.status}</td>
                                <td class="noRec upload" colspan="6"><a href="#">Re upload</a></td>
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
            <span style="min-height: 500px; display: inline-block;"></span>
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
