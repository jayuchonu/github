<jsp:include page="/WEB-INF/views/header.jsp" />
<div class="container mt-4" style="height:400px;">
<h5 class="mt-4">Add Timesheet</h5>

	<div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="timefrom">Time From:</label>
							<input type="text" id="timefrom" placeholder="MM/DD/YYYY HH:MM"   
								class="form-control form_datetime"> 
								<span id="timefrom1" class="text-danger font-weight-bold"></span>
								</div>
					</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label for="password">Time To:</label> 
						<input type="text" placeholder="MM/DD/YYYY HH:MM" 
								class="form-control form_datetime" id="timeto" name="timeto"  onchange="timeDiff();">
								<span id="timeto1" class="text-danger font-weight-bold"></span>
								
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-6">
					<div class="form-group">
						<label for="firstname">Total Hours:</label> <input type="text"
							class="form-control" id="totalhours" name="hours" >
					</div>
				</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label for="firstname">Comments:</label> <input type="text"
						class="form-control" id="comments"  name="comments" >
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
							class="form-control"   id="activityid">
							
							
						</select>
					</div>
				</div>
				<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-6">
					<div class="form-group">
						<label for="exampleFormControlSelect1">Project</label> <select
							class="form-control"   id="project">
							
							
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
	<div class="col-sm-12">

	<div class="row">
		<div class="col-sm-12">
			<button type="submit" id ='addbutton' class="btn btn-success" onclick="timesheets() ">Add</button>
			<button type="submit" id='updatebutton' class="btn btn-success" onclick="sheetupdate()">update</button>
		</div>
</div>

</div>
</div>
</div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.validate.min.js"></script>

<script>
	$(document).ready(
			function() {
				//$("#timefrom").prop("disabled", false);
				//$("#timeto").prop("disabled", false);
				$("#submitteddate").prop("disabled",true);
				$('#updatebutton').hide();
				var today = new Date();
                var date = (today.getMonth()+1)+'/'+today.getDate()+'/'+today.getFullYear();
				$('#submitteddate').val(date);

				timesheetList();
				getProList();
				var id =<%=request.getParameter("id")%>
				
				if(id !=null){
					fetchtimes(id);
					$('#addbutton').hide();
					$('#updatebutton').show();
				}
				/*var date_input = $('input[name="date"]'); //our date input has the name "date"
				var container = $('.bootstrap-iso form').length > 0 ? $(
						'.bootstrap-iso form').parent() : "body";
				date_input.datepicker({
					format : 'mm/dd/yyyy',
					container : container,
					todayHighlight : true,
					autoclose : true,
				})*/
				
				
			})
			
			
	function timesheets(){
			var result =  validateform();
      if(result == true){
		  	   document.getElementById("timefrom1").innerHTML = "";
	   document.getElementById("timeto1").innerHTML = "";
	   document.getElementById("comments1").innerHTML = "";

		var starttime = $('#timefrom').val();
	    var endtime=$('#timeto').val();
	    var start = starttime.split(" - ");
	    var end = endtime.split(" - ");
        var timefrom=start[1];
	    var timeto=end[1];
	
	
	var totalhours=$('#totalhours').val();
	var comments=$('#comments').val();
	var activityid=$('#activityid').val();
	var submitteddate=$('#submitteddate').val();
	var projectid=$('#project').val();
	
	$.ajax({
	    url: 'addtime',
	    type: "POST",
	    async: false,
	    data: { 
	      timefrom:timefrom,
	      timeto:timeto,
		  totalhours:totalhours,
		  comments:comments,
	      activityid:activityid,
	      submitteddate:submitteddate,
	      projectid:projectid
	   },
		success: function(result) {
			var obj= JSON.parse(result);
			console.log(obj);
			toastr.options = {
					  "positionClass": "toast-top-center",
					  "preventDuplicates": false,
					  "showDuration": "300",
					  "hideDuration": "1000",
					  "timeOut": "5000",
					  "extendedTimeOut": "1000"
			};
			toastr.success(obj.msg);
			//alert(obj.msg);
			$('#timefrom').val("");
		    $('#timeto').val("");
		    $('#totalhours').val("");
		    $('#activityid').val("0");
		    $('#comments').val("");
		    $('#project').val("0");
			//location.href='timesheets';
			}
	});
	}
	
	}
	function timesheetList(){
		
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
	    
		    	
		})
		
	}
	
	function sheetupdate(){
		debugger;
		var id =<%=request.getParameter("id")%>
		var starttime = $('#timefrom').val();
	    var endtime=$('#timeto').val();
	    var start = starttime.split(" - ");
	    var end = endtime.split(" - ");
        var timefrom=start[1];
	    var timeto=end[1];
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
			    timefrom:timefrom,
   			    timeto:timeto,
			    totalhours:totalhours
		    },
		    success:function(result){
		    	var obj = JSON.parse(result);
		        console.log(obj);
				toastr.options = {
						  "positionClass": "toast-top-center",
						  "preventDuplicates": false,
						  "showDuration": "300",
						  "hideDuration": "1000",
						  "timeOut": "5000",
						  "extendedTimeOut": "1000"
				};
				toastr.success(obj.msg);
			    $('#activityid').val("0");
			    $('#comments').val("");
			    $('#project').val("0");
			//alert(obj.msg);
		    }
		    });
	}
	
	 function validateform(){
	  var timefrom=document.getElementById('timefrom').value;
	  var timeto=document.getElementById('timeto').value;
	  var comments=document.getElementById('comments').value;
	  var valid=true;
	  if((timefrom == "")||(timefrom == undefined)){
		  document.getElementById("timefrom1").innerHTML = "**please fill the timefrom**";
		  return false;
	  }
	  if((timeto == "")||(timeto == undefined)){
		  document.getElementById("timeto1").innerHTML = "**please fill the timeto**";
		  return false;
	  }
	  if((comments == "")||(comments == undefined)){
		  document.getElementById("comments1").innerHTML = "**please fill the comments**";
		  return false;
	  }
		  	  return valid;
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
		        $('#timefrom').val(obj.timefrom);
		        $('#timeto').val(obj.timeto);
		        $('#totalhours').val(obj.totalhours);
		        $('#activityid').val(obj.activityid);
		        $('#comments').val(obj.comments);
		        $('#project').val(obj.projectid);
		        $('#submitteddate').val(obj.submitteddate);
		        
				//$("#timefrom").prop("disabled", true);
				//$("#timeto").prop("disabled", true);
				//$("#totalhours").prop("disabled", true);
				$("#submitteddate").prop("disabled", true);
				
		    }
		    })
		
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
