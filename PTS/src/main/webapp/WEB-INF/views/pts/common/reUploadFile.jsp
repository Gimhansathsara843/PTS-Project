<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="uploadModalLabel">Upload Meter Reading File</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#displayData').text('')">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="fileInput">Select File</label>
                    <input type="file" class="form-control-file" id="fileInput">
                    <small id="displayData" class="form-text text-muted"></small>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="uploadButton">Upload</button>
            </div>
        </div>
    </div>
</div>
