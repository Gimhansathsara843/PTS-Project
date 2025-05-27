<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form id="myForm" method="post" enctype="multipart/form-data"
		   action="/PTS/UploadingMeterReadingFileS" modelAttribute="model">

	<div class="mb-5">
		<div class="card">
			<div class="container mt-4 mb-4" style="width: 85%;">
				<div class="row">
					<!-- Bill Cycle -->
					<div class="col">
						<div class="selectUnit">
							<label>Bill cycle</label>
							<div class="input-group">
								<form:input path="billCycle" type="text" id="billCycle"
											class="form-control" readonly="true"/>
							</div>
						</div>
					</div>

					<!-- Division -->
					<div class="col">
						<div class="selectUnit">
							<label>Distribution division</label>
							<div class="input-group">
								<c:if test="${not empty model.licenseList}">
									<form:select path="division" id="divisionDropdown" class="form-control">
										<c:forEach var="division" items="${model.licenseList}">
											<option value="${division.licenseCode}" label="${division.licenseName}"/>
										</c:forEach>
									</form:select>
								</c:if>
							</div>
						</div>
					</div>

					<!-- Province -->
					<div class="col">
						<div class="selectUnit">
							<label>Province</label>
							<div class="input-group">
								<form:select path="province" id="provinceDropdown" class="form-control">
								</form:select>
							</div>
						</div>
					</div>

					<!-- File Upload -->
					<div class="col">
						<div class="selectUnit">
							<label>Browse File</label>
							<input style="font-size: 0.7rem;" type="file" name="files" multiple class="form-control"/>
						</div>
					</div>

					<!-- Submit Button -->
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

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function () {
		// ---------------------- Bill Cycle AJAX ------------------------
		$.ajax({
			url: '/PTS/getBillCycle',
			method: 'GET',
			dataType: 'text',
			success: function (data) {
				$('#billCycle').val(data); // ✅ Will now be "436" instead of [object XMLDocument] or <Long>436</Long>
			},
			error: function (err) {
				console.error('Error fetching bill cycle:', err);
			}
		});



		// ---------------------- Province Dropdown Logic ------------------------
		const divDropdown = $('#divisionDropdown');
		const provDropdown = $('#provinceDropdown');
		const provinceList = ${provinceList};

		divDropdown.change(function () {
			let selectedLicenseCode = divDropdown.val();
			filterProvince(selectedLicenseCode);
		});

		function filterProvince(lCode) {
			const filteredProvinces = provinceList.filter(function (province) {
				return province.licenseCode === lCode;
			});
			provDropdown.empty().append(
					filteredProvinces.map(function (province) {
						return $('<option>', {
							value: province.provinceCode,
							text: province.provinceName
						});
					})
			);
		}

		filterProvince('DD1'); // initial render
	});

	function submitForm() {
		document.getElementById('myForm').submit();
	}
</script>

<!-- Styles -->
<style>
	.selectUnit label,
	.selectUnit select {
		font-size: small;
		margin: 0;
	}
</style>
