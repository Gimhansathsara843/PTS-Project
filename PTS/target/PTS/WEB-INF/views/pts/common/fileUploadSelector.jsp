<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form:form id ="myForm"  method="post"   enctype="multipart/form-data" action="/PTS/UploadingMeterReadingFileS"
											modelAttribute="model">
	<div class="mb-5">
		<div class="card">
			<div class="container mt-4 mb-4" style="width: 85%;">
				<div class="row">
					<div class="col" >
						<div class="selectUnit">
							<label>Bill cycle</label>
							<div class="input-group">
								<form:input path="metercycle" type="text" id="billCycle" name="billCycle" class="form-control"/>
							</div>
						</div>
					</div>

					<div class="col">
						<div class="selectUnit">
							<label>Distribution division</label>
							<div class="input-group">
								<c:if test="${not empty model.divisionList}">
									<form:select path="division" name="division" id="divisionDropdown" class="form-control" >
										<c:forEach var="division" items="${model.divisionList}">
											<option value="${division.licenseCode}" label="${division.licenseName}" />
										</c:forEach>
									</form:select>
								</c:if>
							</div>
						</div>
					</div>


					<div class="col" >
						<div class="selectUnit">
							<label>Province</label>
							<div class="input-group">
								<form:select path="province" name="province" id="provinceDropdown" class="form-control">
								</form:select>
							</div>
						</div>
					</div>

					<div class="col">
						<div class="selectUnit">
							<label>Browse File</label>
							<input style="font-size: 0.7rem;" type="file" name="files" multiple class="form-control"/>
						</div>
					</div>

					<div>
						<div class="ml-5 pt-3">
							<input type="submit" class="btn btn-primary" id="click_btn" value="Upload" onclick="submitForm()">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>
<script>
	$(document).ready(function() {
		//-------------------------------------------------------------------
		//            drop down menu
		//-------------------------------------------------------------------
		const divDropdown = $('#divisionDropdown');
		const provDropdown = $('#provinceDropdown');
		const provinceList = ${provinceList};

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

	function submitForm(){
		document.getElementById('myForm').submit();
	}

</script>
<style>
    .selectUnit label, .selectUnit select {
        font-size: small;
        margin: 0;
    }
</style>