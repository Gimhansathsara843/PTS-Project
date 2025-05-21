<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="out" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <c:choose>
        <c:when test="${not empty msg}">
            <div class="error-message">${msg}</div>
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
        </c:when>
        <c:otherwise>
            <table class="resultTable">
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
                    <th>IMP/EXP</th>
                    <th>COINCIDENT PEAK</th>
                </tr>
                </thead>
                <tbody id="resultTableBody">
                <c:forEach items="${processSummary}" var="reading">
                    <c:choose>
                        <c:when test="${not empty reading.meterReadingRecordModelList}">
                            <c:forEach items="${reading.meterReadingRecordModelList}" var="record" varStatus="status">
                                <tr>
                                    <c:if test="${status.index == 0}">
                                        <td rowspan="6">${reading.serialNo}</td>
                                        <td rowspan="6">${reading.pss}</td>
                                        <td rowspan="6">${reading.status}</td>
                                        <td rowspan="6">${reading.fileName}</td>
                                    </c:if>
                                    <td>${record.measure}</td>
                                    <td class="numeric">${record.currentReading}</td>
                                    <td class="numeric">${record.previousReading}</td>
                                    <td class="numeric">${record.energy}</td>
                                    <c:choose>
                                        <c:when test="${status.index < 3}">
                                            <c:if test="${status.index == 0}">
                                                <td class="numeric" rowspan="3">${reading.exportEnergy}</td>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${status.index == 3}">
                                                <td class="numeric" rowspan="3">${reading.importEnergy}</td>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${status.index < 3}">
                                            <c:if test="${status.index == 0}">
                                                <td class="numeric" rowspan="3">${reading.coincidentPeak}</td>
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
                                <td>${reading.serialNo}</td>
                                <td>${reading.pss}</td>
                                <td>${reading.status}</td>
                                <td>${reading.fileName}</td>
                                <td colspan="6">...</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                </tbody>
            </table>

            <p>Total Energy:</p>
            <table class="resultTable">
                <tbody>
                <c:forEach items="${total}" var="total">
                    <tr>
                        <td>${total.dayOffPeak}</td>
                        <td class="numeric">${total.totalEnergy}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <style>
                .resultTable{
                    width: 100%;
                    table-layout: fixed;
                    font-size: 0.9rem;
                    margin-bottom: 50px;
                }
                td{
                    padding: 3px;
                }
                .numeric{
                    text-align: right;
                }
            </style>
        </c:otherwise>
    </c:choose>
</div>