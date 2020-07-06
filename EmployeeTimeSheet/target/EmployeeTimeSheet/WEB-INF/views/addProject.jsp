<jsp:include page="/WEB-INF/views/header.jsp" />

<div class="container mt-4">
<h5 class="mt-4">Add Project</h5>

<form id ="projectSubmit" onsubmit="return false">
	<div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="projectname">ProjectName:</label> <input type="text"
							class="form-control" id="projectname" name="projectname" maxlength="20">
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="startdate">Start Date</label>
						<div class='input-group date' id='datetimepicker1'>
							<input class="form-control" id="startdate" name="startdate"
								placeholder="MM/DD/YYYY" type="text" /> <span
								class="input-group-addon"><span
								class="glyphicon glyphicon-calendar"></span> </span>
						</div>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label for="startdate">End Date</label>
						<div class='input-group date' id='datetimepicker1'>
							<input class="form-control" id="enddate" name="enddate"
								placeholder="MM/DD/YYYY" type="text" /> <span
								class="input-group-addon"><span
								class="glyphicon glyphicon-calendar"></span> </span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-12">
					<div class="form-group">
						<label for="role">Users</label>
			    <select id="user" name="user" class="form-control selectpicker" required multiple>
					</select>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<button type="submit" id="addButton" class="btn btn-success">Add</button>
			<button type="button" id="updateButton" class="btn btn-success"
				onClick="updateProject();">Update</button>
		</div>
	</div>
</form>
</div>
 <jsp:include page="/WEB-INF/views/footer.jsp" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-select.min.js"></script>

<script>
	$(document).ready(
			function() {
				var date_input=$('input[name="startdate"]'); //our date input has the name "date"
		var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
		date_input.datepicker({
			format: 'mm/dd/yyyy',
			container: container,
			todayHighlight: true,
			autoclose: true,
		});
				var date_input1=$('input[name="enddate"]'); //our date input has the name "date"
		var container1=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
		date_input1.datepicker({
			format: 'mm/dd/yyyy',
			container: container1,
			todayHighlight: true,
			autoclose: true,
		});
			$('#addButton').show();
			$('#updateButton').hide();
			var projectId =<%=request.getParameter("id")%>
		if (projectId != null) {
					$('#addButton').hide();
					$('#updateButton').show();
					fetchProject(projectId);
				}
				userList();
				toastr.options = {
						  "positionClass": "toast-top-center",
						  "preventDuplicates": false,
						  "showDuration": "300",
						  "hideDuration": "1000",
						  "timeOut": "5000",
						  "extendedTimeOut": "1000"
					};
			});

	function addProject() {
		var projectName = $('#projectname').val();
		var startdate = $('#startdate').val();
		var enddate = $('#enddate').val();
		var userIds = $('#user').val();
		$.ajax({
			url : "addProject.htm",
			type : "POST",
			async : true,
			data : {
				projectName : projectName,
				startdate : startdate,
				enddate : enddate,
				userIds : userIds.toString()
			},
			success : function(response) {
				var obj = JSON.parse(response);
				if(obj.msg == "project created successfully"){
					toastr.success("Project Created Successfully");
				}
				if(obj.msg == "project already exists"){
					toastr.warning("Project Already Exists");
				}
				if(obj.msg == "project created failed"){
				toastr.error("Project Creation Failed");
				}
				$('#projectname').val("");
				$('#startdate').val("");
				$('#enddate').val("");
				$('#user').val("0");
			}
		});
	}
	function fetchProject(projectId) {
		$.ajax({
			url : "fetchProject.htm",
			type : "POST",
			async : true,
			data : {
				projectId : projectId
			},
			success : function(response) {
				var obj = JSON.parse(response);
				console.log(obj);
				$('#projectname').val(obj.projectname);
				$('#startdate').val(obj.startdate);
				$('#enddate').val(obj.enddate);
				$('#user').val(obj.projectOwner);
				$("#user").selectpicker("refresh");			
			 }
		});
	}
	function updateProject() {
		var projectName = $('#projectname').val();
		var startdate = $('#startdate').val();
		var enddate = $('#enddate').val();
		var userIds = $('#user').val();
	    var projectId =<%=request.getParameter("id")%>
	$.ajax({
			url : "updateProject.htm",
			type : "POST",
			async : true,
			data : {
				projectName : projectName,
				startdate : startdate,
				enddate : enddate,
				userIds : userIds.toString(),
				projectId : projectId

			},
			success : function(response) {
				var obj = JSON.parse(response);
				if(obj.msg == "project Updated"){
					toastr.success("Project Updated Successfully");
				}
				if(obj.msg == "update failed"){
					toastr.success("Project Updation Failed");
				}
				$('#projectname').val("");
				$('#startdate').val("");
				$('#enddate').val("");
				$('#user').val("0");
			}
		});
	}
	function userList() {
		$.ajax({
					url : "userList.htm",
					type : "get",
					async : false,
					data : {

					},
					success : function(response) {
						var obj = JSON.parse(response);
						console.log(obj);
						var userdropdownList = '';
						for (var i = 0; i < obj.userList.length; i++) {
							userdropdownList += '<option value="'+obj.userList[i].id+'">'
									+ obj.userList[i].name + '</option>';
						}
						$('#user').append(userdropdownList);
						$("#user").selectpicker("refresh");

					}
				});
	}
	
	
	$('#projectSubmit').validate({
		    rules: {
		    	projectname: "required",
		    	startdate: "required",
		    	enddate:"required",
		    	user:"required"
		    },
		    messages: {
		    	projectname : "Please enter projectname",
		    	startdate: "Please select startdate",
		    	enddate: "Please select enddate",
		    	user:"Please select user"
		    },
		    submitHandler: function(form) {
		    	addProject();
		    }
		  });
</script>
</body>
</html>
