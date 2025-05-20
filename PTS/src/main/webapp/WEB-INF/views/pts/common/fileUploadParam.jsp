<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form:form id ="myForm"  method="post"   enctype="multipart/form-data" action="UploadingMeterReadingFileS"
											modelAttribute="FileUploadModel">
<div class="mb-5">
    <div class="card">
        <div class="container mt-4 mb-4" style="width: 70%;">
                <div class="row">
	                <div class="col" >
	                    <div class="selectUnit">
	                        <label>Bill cycle</label>
	                        <div class="input-group">
	                            <input  type="text" id="billCycle" name="billCycle" class="form-control"/>
	                        </div>
	                    </div>
	                </div>
				</div>
				 <div class="row">
                     <div class="col">
                            <div class="selectUnit">
                                <label>Distribution division</label>
                                <div class="input-group">
                                    
									<form:select id="division" class="form-control" path="division" >
										<form:option value="DD1" label="Distribution Division 1" />
										<form:option value="DD2" label="Distribution Division 2" />
										<form:option value="DD3" label="Distribution Division 3" />
										<form:option value="DD4" label="Distribution Division 4" />
										<form:option value="LECO" label="LECO" />
									</form:select>
                                    
                                </div>
                            </div>
                        </div>
					 </div>
					 <div class="row">
                        <div class="col" >
                        	<div class="selectUnit">
                            	<label>Province</label>
	                            <div class="input-group">
	                                <form:select id="province" class="form-control" path="province" onkeypress="">
										<form:option value="WPS1L" label="LECO REGION WPS 1" />
										<form:option value="WPS2L" label="LECO REGION WPS 2" />
										<form:option value="WPNL" label="LECO REGION WPN" />
										<form:option value="SP1L" label="LECO REGION SP1" />
                            </form:select>
	                            </div>
                       		</div>
                        </div>
					</div>
			<div class="col">
				<div class="selectUnit">
					<label>Browse File</label>
					<input  type="file" name="files" multiple class="form-control"/>
				</div>
			</div>


			<div class="row">
				<div class="col">
					<div class="ml-5 pt-3">
						<input type="submit" class="btn btn-primary" id="click_btn" value="Upload">
					</div>
				</div>
			</div>
        </div>
    </div>

</form:form>
<style>
    .selectUnit label, .selectUnit select {
        font-size: small;
        margin: 0;
    }
</style>