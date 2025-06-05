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
