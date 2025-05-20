<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="out" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <c:choose>
        <c:when test="${not empty msg}">
            <div class="error-message">${msg}</div>
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
                    <th>REMARK</th>
                </tr>
                </thead>
                <tbody id="resultTableBody">
                <c:forEach items="${processSummary}" var="reading">
                    <c:choose>
                        <c:when test="${not empty reading.meterProcessRecordModelList}">
                            <c:forEach items="${reading.meterProcessRecordModelList}" var="record" varStatus="status">
                                <tr>
                                    <c:if test="${status.index == 0}">
                                        <td rowspan="${reading.meterProcessRecordModelList.size()}">${reading.serialNo}</td>
                                        <td rowspan="${reading.meterProcessRecordModelList.size()}">${reading.pss}</td>
                                        <td rowspan="${reading.meterProcessRecordModelList.size()}">${reading.status}</td>
                                        <td rowspan="${reading.meterProcessRecordModelList.size()}">${reading.fileName}</td>
                                    </c:if>
                                    <td>${record.measureName}</td>
                                    <td class="numeric">${record.currentReading}</td>
                                    <td class="numeric">${record.previousReading}</td>
                                    <td class="numeric">${record.energy}</td>
                                    <td></td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td>${reading.serialNo}</td>
                                <td>...</td>
                                <td>${reading.status}</td>
                                <td>${reading.fileName}</td>
                                <td colspan="5">...</td>
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