<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/views/header.jsp" />

<div class="container mt-4">
<h5 class="mt-4">Add Activity</h5>

<form id="activitySubmit" onsubmit="return false">
	<div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="usr">Code:</label> <input type="text"
							class="form-control" id="code" name="code" maxlength="20">
							<span id="code1" class="text-danger font-weight-bold"></span>
							
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Project</label> 
						<select class="form-control" id="project" name ="project">	</select>
					<span id="project1" class="text-danger font-weight-bold"></span>
							
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
								<span id="startdate1" class="text-danger font-weight-bold"></span>
								
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
								<span id="enddate1" class="text-danger font-weight-bold"></span>
								
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
		<button type="submit" id="addButton"  class="btn btn-success" >Add</button>
		<button type="submit" id="updateButton" class="btn btn-success" onclick="activityUpdate()">Update</button>
		</div>
	</div>
</form>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-datepicker.min.js">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.validate.min.js"></script>

<script>
	$(document).ready(function(){
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
		getProList();
		$('#addButton').show();
  		$('#updateButton').hide();
		 var id=<%=request.getParameter("id") %>
		 if(id !=null){
				$('#addButton').hide();
				$('#updateButton').show();
				getActivityById(id);
			}
	   toastr.options = {
				  "closeButton": false,
				  "positionClass": "toast-top-center",
				  "preventDuplicates": false,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "5000",
				  "extendedTimeOut": "1000"
				};
	});
	
function addActivity(){
	var code=$('#code').val();
	var project=$('#project').val();
	var startdate=$('#startdate').val();
	var enddate=$('#enddate').val();
		$.ajax({
			url: 'actinsert.htm',
			type: "POST",
			async: false,
			data: {
				code:code,
				projectid:project,
				startdate:startdate,
				enddate:enddate
				},
			success: function(result) {
				var obj=JSON.parse(result);
				if(obj.msg == "Code created successfully"){
					toastr.success("Activity Created Successfully");
					}
				if(obj.msg == "Activity already exists"){
					toastr.warning("Activity already exists");
					}
				if(obj.msg == "Code created failed"){
					toastr.error("Activity Creation Failed");
					}
				$('#code').val("");
				$('#project').val("");
				$('#startdate').val("");
				$('#enddate').val("");
				}
			});
	}

function getProList() {
	$.ajax({
		url:'getProject.htm',
		type:"POST",
		async:false,
		data: {
			
		},
		success: function(result) {
			var obj=JSON.parse(result);
			console.log(obj);
			var projectDrop ='<option value="">Select Activity </option>';
			for(var i=0;i<obj.getProjectList.length;i++){
			projectDrop +='<option value='+obj.getProjectList[i].id+'>'+obj.getProjectList[i].projectname+'</option>';
			}
			$('#project').append(projectDrop);
		}
	});
}

function activityUpdate() {
	 var id=<%= request.getParameter("id")%>
	 var code=$('#code').val();
	 var project=$('#project').val();
	 var startdate=$('#startdate').val();
	 var enddate=$('#enddate').val();
	 $.ajax({
		 url:'actupdate.htm',
		 type:"POST",
		 async:false,
		 data: {
			 	id:id,
			 	code:code,
				projectid:project,
				startdate:startdate,
				enddate:enddate
		 },
	 	success: function(result) {
	 		var obj=JSON.parse(result);
	 		if(obj.msg == "Code Updated"){
	 			toastr.success("Activity Updated Successfully");
	 		}
	 		if(obj.msg == "update failed"){
	 			toastr.error("Activity Updation Failed");
	 		}
			$('#code').val("");
			$('#project').val("");
			$('#startdate').val("");
			$('#enddate').val("");
	 	}
	 });
	 
}

function getActivityById(id){
	$.ajax({
		url: 'getActivity.htm',
		type:"POST",
		async:false,
		data: {
			id:id
		},
			success:function(result) {
				var obj=JSON.parse(result);
				console.log(obj);
				$('#id').val(obj.id);
				$('#code').val(obj.code);
				$('#project').val(obj.projectid);
				$('#startdate').val(obj.startdate);
				$('#enddate').val(obj.enddate);
				}
	});
}


$('#activitySubmit').validate({
	    rules: {
	    	code: "required",
	    	project: "required",
	    	startdate:"required",
	    	enddate:"required"
	    },
	    messages: {
	    	code : "Please enter code",
	    	project: "Please select project",
	    	startdate: "Please select startdate",
	    	enddate:"Please select enddate"
	    },
	    submitHandler: function(form) {
	    	addActivity();
	    }
	  });

</script>
</html>
