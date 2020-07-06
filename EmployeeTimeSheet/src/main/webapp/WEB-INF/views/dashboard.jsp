<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jsp:include page="/WEB-INF/views/header.jsp" />
</head>
<body>

  <div class="container my-4">
<h5 class="mt-4">Timesheets</h5>

   <div class="col-sm-12">
		<div class="row">
			<div class="col-sm-4">
					<div class="form-group">
						<label for="date">Submitted Date</label>
						<div class="input-group date">
							<input type="text" class="form-control" id="dtpFrom"
								name="StartDate"> <span
								class="input-group-addon open-datetimepicker1"></span> <span
								class="glyphicon glyphicon-calendar "></span>
						</div>
					</div>
				</div>
				<div class="col-sm-4" id="userdrop">
					<div class="form-group">
						<label for="role">Users</label> <select class="form-control"
							id="username">
						</select>
					</div>
				</div>
				<div class="col-sm-4 mt-4">
					<div class="form-group">
						<button type="button" class="btn float-right btn-success" onclick="getSheetList()">Search</button>
				</div>
			</div>
		</div>
	</div>

    <!--Accordion wrapper-->
<div class="accordion md-accordion accordion-blocks" id="accordionEx78" role="tablist"
  aria-multiselectable="true">

  <!-- Accordion card -->
 
</div>
<!--/.Accordion wrapper-->
 <!-- Modal -->
   <div class="modal" id="myModal">
    <div class="modal-dialog modal-lg">
	<form  id ="timesheetSubmit" onsubmit="return false">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Timesheet</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
         

		  <div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-6">
					<div class="form-group">
						<input type="hidden" id="timesheetId">
						<label for="firstname">Total Hours:</label> <input type="text"
							class="form-control" id="totalhours" name="hours" maxlength="1">
					</div>
				</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label for="firstname">Comments:</label> <textArea type="text"
						class="form-control" id="comments"  name="comments" maxlength="200"></textArea>
						<span id="comments1" class="text-danger font-weight-bold"></span>
				</div>
			</div>
		</div>

	</div>
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-6">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Activity</label> <select
							class="form-control"  name="activity"  id="activityid">
							
							
						</select>
					</div>
				</div>
				<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-6">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Project</label> <select
							class="form-control" name="project"  id="project">
							
							
						</select>
					</div>
				</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label for="startdate">Submitted Date</label>
					
						<input class="form-control" id="submitteddate" name="date"
							placeholder="MM/DD/YYYY" type="text"  disabled>
					</div>
				</div>
			</div>
		</div>
	</div>
        </div>
        </div>
        <!-- Modal footer -->
        <div class="modal-footer">
         <button type="submit" class="btn btn-success">Save</button>
        </div>
        
      </div>
    </div>
  </form>
  </div>

  </div>	
</div>  
</body>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/toastr.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/daterangepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.validate.min.js"></script>


<script>
$(document).ready(
	function() {
	var start = moment().subtract(29, 'days');
    var end = moment();

    function cb(start, end) {
        $('#dtpFrom span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
    }

    $('#dtpFrom').daterangepicker({
        startDate: start,
        endDate: end,
        ranges: {
           'Today': [moment(), moment()],
           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
           'This Month': [moment().startOf('month'), moment().endOf('month')],
           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    }, cb);

    cb(start, end);
	toastr.options = {
	  "closeButton": true,
	  "positionClass": "toast-top-center",
	  "preventDuplicates": false,
	  "showDuration": "300",
	  "hideDuration": "1000",
	  "timeOut": "5000",
	  "extendedTimeOut": "1000"
	};
	getProList();		
	activitylist();
	getSheetList();
	getusers();
	var role=$('#role').val();
	if(role.toLowerCase()=="Employee".toLowerCase()){
		$('#userdrop').hide();
	}
});
function getSheetList(){
	var role=$('#role').val();
	var date = $('#dtpFrom').val();
	var daterange = date.split("-");
	var userid=0;
	var sessionUserid = '<%= session.getAttribute("userid") %>';
	if($('#username').val() != undefined){
	 userid=$('#username').val();
	}
	else{
		userid= '<%= session.getAttribute("userid") %>';
	}
	$.ajax({
		url:'sheetList',
		async:false,
		type:'POST',
		data:{
			startdate:daterange[1],
			userId:userid
		},
		success:function(response){
			var obj= JSON.parse(response);
			console.log(obj);
			console.log(obj.sheetList.length);
			$('#accordionEx78').html("");
		for(var i=0;i<obj.sheetList.length;i++){
			var table= '';
			console.log(obj.sheetList[i].status);
		for(var key in obj.sheetList[i]){
			if(key !="status" && key != "userids" && key != "currentWeek"){
		var date = key;
		table+= '<div class="card"><div class="card-header" role="tab" id="headingUnfiled"><a data-toggle="collapse" data-parent="#accordionEx78" href="#collapse'+i+'" aria-expanded="true" aria-controls="collapse'+i+'">'
          +'<span>'+date+'</span></a><span style="float:right;">'+(obj.sheetList[i].status != undefined ? obj.sheetList[i].status:"")+'</div>'
		  +'<div id="collapse'+i+'" class="collapse" role="tabpanel" aria-labelledby="headingUnfiled" data-parent="#accordionEx78">'
          +'<div class="card-body"><input type="hidden" id="userids"><input type="hidden" id="submitType">'
			if(obj.sheetList[i][date].length==0 && obj.sheetList[i].currentWeek =="1" && sessionUserid == userid){
			 table+= '<div class="col-sm-12"><button id="addtimesheet" type="submit" class="btn btn-success" onclick="addNewTimesheet();">Add NewTimesheet</button></div>'
			}
	
		 table+='<div class="table-ui p-2 mb-3 mx-3 mb-4"></div><div class="table-responsive mx-3">'
          +'<table class="table table-hover mb-0"><thead><tr>'
          +'<th>Date</th><th>Activity</th><th>Comments</th><th>Projects</th><th>Totalhours</th><th>Submitteddate</th>'
          +'<th></th></tr></thead><tbody>'
		for(var j=0;j<obj.sheetList[i][date].length;j++){
		   console.log(obj.sheetList[i][date][j].timefrom);
		   table+='<tr><td>'+obj.sheetList[i][date][j].submitteddate+'</td>'
		      +'<td>'+(obj.sheetList[i][date][j].totalhours !="0"?obj.sheetList[i][date][j].activityid:"")+'</td>'
			  +'<td>'+(obj.sheetList[i][date][j].totalhours !="0"?obj.sheetList[i][date][j].comments:"")+'</td><td>'+(obj.sheetList[i][date][j].totalhours !="0"?obj.sheetList[i][date][j].projectname:"")+'</td>'
			  +'<td>'+(obj.sheetList[i][date][j].totalhours !="0"?obj.sheetList[i][date][j].totalhours:"")+'</td><td>'+(obj.sheetList[i][date][j].totalhours !="0"?obj.sheetList[i][date][j].submitteddate:"")+'</td>'
			  +'<td><a data-toggle="modal" data-target="#myModal" onclick="fetchtimes('+obj.sheetList[i][date][j].id+')" href="addTimeSheet?id='+obj.sheetList[i][date][j].id+'"><i class="fa fa-plus"></i></a></td></tr>'
		}
		table+='</tbody></table></div>'
		if(obj.sheetList[i][date].length>0 && role.toLowerCase()=="Employee".toLowerCase() && obj.sheetList[i].status.toLowerCase() == "open".toLowerCase()){
			table+='<div class="col-sm-12"><div class="row"><div class="col-sm-12"><button type="submit" class="btn btn-success" onclick=send("Submit","'+obj.sheetList[i].userids+'");>Submit</button>'
		    +'</div></div></div>'
		}
		if(obj.sheetList[i][date].length>0 && role.toLowerCase()=="Supervisor".toLowerCase() && obj.sheetList[i].status.toLowerCase() == "Waiting for Approval".toLowerCase()){
			table+='<div class="col-sm-12"><div class="row"><div class="col-sm-3"><button type="submit" class="btn btn-success" onclick=send("Approved","'+obj.sheetList[i].userids+'");>Approve</button>'
		    +'</div><div class="col-sm-9"><button type="submit" class="btn btn-danger" onclick=send("Decline","'+obj.sheetList[i].userids+'");>Decline</button></div></div></div>'
		}
		if(obj.sheetList[i][date].length>0 && role.toLowerCase()=="Admin".toLowerCase() && obj.sheetList[i].status.toLowerCase() == "Waiting for Approval".toLowerCase()){
			table+='<div class="col-sm-12"><div class="row"><div class="col-sm-3"><button type="submit" class="btn btn-success" onclick=send("Approved","'+obj.sheetList[i].userids+'");>Approve</button>'
		    +'</div><div class="col-sm-9"><button type="submit" class="btn btn-danger" onclick=send("Decline","'+obj.sheetList[i].userids+'");>Decline</button></div></div></div>'
		}
		table+='</div></div></div>';
		}
		}
		$('#accordionEx78').append(table);
		}
} 	
});
}

function send(submitType,ids){
	$('#userids').val(ids);
	$('#submitType').val(submitType);
	toastr.info('<div>Are you sure want to submit timesheet<button type="button" id="okBtn" class="btn btn-default" onclick=sendmail();>Yes</button><button type="button" id="surpriseBtn" class="btn btn-default" style="margin: 0 8px 0 8px">No</button></div>')
}

function sendmail(){
var ids =	$('#userids').val();
var submitType = $('#submitType').val();
var userid="<%= session.getAttribute("userid") %>";
	 $.ajax({
		 url:"sendmail",
		 type:"POST",
		 async:false,
		 data:{
			 userid:userid,
			 submitType:submitType,
			 ids:ids.toString()
		 },
		 success:function(response){
	      var obj= JSON.parse(response);
	      toastr.success(obj.msg);
		 }
	 });
	}


		 
 function activitylist(){
		$.ajax({
			url: 'activitylist',
		    type: "POST",
		    async: false,
		    data: { 
		    },
		    success: function(result) {
				var obj= JSON.parse(result);
				console.log(obj.timesheetList);
				console.log(obj.timesheetList[0].id);
				var activitydropdown='<option>Select Activity</option>';
				for(var i=0;i<obj.timesheetList.length;i++){
					activitydropdown +='<option value='+obj.timesheetList[i].id+'>'+obj.timesheetList[i].code+'</option>'
				}
				
				$('#activityid').append(activitydropdown);
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
				var projectDrop ='<option value="0">Select Project</option>';
				for(var i=0;i<obj.getProjectList.length;i++){
					projectDrop +='<option value='+obj.getProjectList[i].id+'>'+obj.getProjectList[i].projectname+'</option>';
				}
				$('#project').append(projectDrop);
			}
 		});
 	}
	

	
function addNewTimesheet() {
		$.ajax({
			url:'setNextWeek',
			type:"POST",
			async:false,
			data: {
			},
		success: function(result) {
			var obj = JSON.parse(result);
			console.log(obj);
			 if(obj.msg == "timesheet added successfully"){
		        	toastr.success("Timesheet added successfully");
		        	location.reload();
		        }
		        else if(obj.msg == "timesheet creation failed"){
		        	toastr.error("Timesheet creation failed");
		        }else if(obj.msg == "timesheet already exists"){
		        	toastr.warning("Timesheet already exists");
		        }
			getSheetList();	
		}
	});
}

function updatetime(){
 			var id=$('#timesheetId').val();
			var totalhours=$('#totalhours').val();
			var activityid = $('#activityid').val();
			var comments = $('#comments').val();
			var projectid=$('#project').val();
			$.ajax({
				url: 'sheetupdate',
			    type: "POST",
			    async: false,
			    data: {
				    id:id,
			    	activityid:activityid,
			    	comments:comments,
			    	projectid:projectid,
				    totalhours:totalhours
			    },
			    success:function(result){
			    	var obj = JSON.parse(result);
			        console.log(obj);
			        if(obj.msg == "timesheet updated successfully"){
			        	toastr.success("Timesheet updated successfully");
						$('#myModal').modal('hide');
			        }
			        else if(obj.msg == "timesheet updated failed"){
			        	toastr.error("Timesheet updation failed");
			        }
				    $('#activityid').val("");
				    $('#comments').val("");
				    $('#project').val("");
				    
			    }
			    });
		}
		
function fetchtimes(id){
	
	$.ajax({
		url: 'updatelist',
	    type: "POST",
	    async: false,
	    data: { 
	    	id:id
	    },
	    success:function(result){
	    	var obj = JSON.parse(result);
	        console.log(obj);
	        $('#totalhours').val(obj.totalhours);
	        $('#activityid').val(obj.activityid);
	        $('#comments').val(obj.comments);
	        $('#project').val(obj.projectid);
	        $('#submitteddate').val(obj.submitteddate);
	        $('#timesheetId').val(id);
	        
			//$("#timefrom").prop("disabled", true);
			//$("#timeto").prop("disabled", true);
			//$("#totalhours").prop("disabled", true);
			$("#submitteddate").prop("disabled", true);
			
	    }
	    });
	
}

function getusers(){
	var userid= '<%= session.getAttribute("userid") %>';
	$.ajax({
		url:'getuserList.htm',
		type:"POST",
		async:false,
		data:{
			userid:userid
			},
		success: function(result){
			var obj=JSON.parse(result);
			var userlistDrop='<option value="0">Select users</option>';
			for(var i=0;i<obj.userlist.length;i++){
				userlistDrop +='<option value='+obj.userlist[i].userid+'>'+obj.userlist[i].username+'</option>';
			}
			$('#username').append(userlistDrop);
			
			}
		});
}
$('#timesheetSubmit').validate({
		    rules: {
		    	totalhours: "required",
		    	comments: "required",
				activity:"required",
		    	project:"required"
		    },
		    messages: {
		    	totalhours : "Please enter total hours",
		    	comments: "Please enter comments",
		    	activity: "Please select activity",
		    	project:"Please select project"
		    },
		    submitHandler: function(form) {
		    	updatetime();
		    }
		  });
</script>
