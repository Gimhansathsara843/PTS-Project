<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="mb-5">
    <div class="card">
        <div class="container mt-4 mb-4" style="width: 70%;">
            <div class="row">
                <div class="col" >
                    <div class="selectUnit">
                        <label>Bill cycle</label>
                        <div class="input-group" style="width: 300px">
                            <input  type="text" id="billCycle" name="billCycle" class="form-control"/>
                            </select>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="ml-5 pt-3">
                        <input type="submit" class="btn btn-primary" id="click_btn" value="Download Bill">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .selectUnit label, .selectUnit select {
        font-size: small;
        margin: 0;
    }
</style>