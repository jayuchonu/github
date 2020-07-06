<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jsp:include page="/WEB-INF/views/header.jsp" />
<div class="container">
<h5 class="mt-4">Reports</h5>

	<div class="row mt-4">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-4">
					<div class="form-group">
						<label for="date">Submitted Date</label>
						<div class="input-group date">
							<input type="text" class="form-control" id="dtpFrom"
								name="StartDate"> <span
								class="input-group-addon open-datetimepicker1"> <span
								class="glyphicon glyphicon-calendar "></span>
							</span>
						</div>
					</div>
				</div>
			<c:choose>	
	<c:when test="${fn:containsIgnoreCase(sessionScope.role, 'Supervisor')}">
				<div class="col-sm-4">
					<div class="form-group">
						<label for="role">Users</label> <select class="form-control"
							id="username">
						</select>
					</div>
				</div>
				 </c:when>
				 <c:when test="${fn:containsIgnoreCase(sessionScope.role, 'Admin')}">
				<div class="col-sm-4">
					<div class="form-group">
						<label for="role">Users</label> <select class="form-control"
							id="username">
						</select>
					</div>
				</div>
				 </c:when>
				 </c:choose>
			<div class="col-sm-4">
					<div class="form-group">
						<label for="role">Timesheet Status</label> <select class="form-control"
							id="timesheetstatus">
						</select>
					</div>
				</div>
				
			</div>
		</div>
	</div>
<div class="col-sm-12">

<div class="row">
<div class="col-sm-4 mt-4">
        <button  class="btn btn-success" onclick="exportTableToExcel('example', 'timesheets')">Export Table Data To Excel File</button>
		</div>
		<div class="col-sm-4 mt-4">
					<div class="form-group">
						<button type="button" class="btn float-right btn-success"
							onclick="times()">Search</button>
					</div>
				</div>
		</div>
   </div>
	<div class="row">
		<div class="col-sm-12 mt-3">
			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead>
					<tr>
						<th>Projectname</th>
						<th>Comments</th>
						<th>Totalhours</th>
						<th>Submitteddate</th>
						<th>Action</th>

					</tr>
				</thead>
				<tbody id="timesheetbody">
				</tbody>

			</table>
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-4 mt-4">
						<button type="button" id="approvebtn"
							class="btn btn-success"
							onclick="sendmail('Approved')">Approve</button>

						<button type="button" id="rejectbtn"
							class="btn btn-danger"
							onclick="sendmail('Rejected')">Decline</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/js/daterangepicker.min.js"></script>

<script>
$(document).ready(function() {
   userList();
   statusList();
 $('#example').hide();
 $('#approvebtn').hide();
 $('#rejectbtn').hide();

} );
 $('#dtpFrom').daterangepicker({
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                'This Week': [moment().startOf('isoWeek'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            "startDate": moment().startOf('isoWeek'),
            "endDate": moment()
        }, function (start, end, label) {
            
        });
 
function deletetimesheet(id){
	$.ajax({
	    url: 'deletetimesheets.htm',
	    type: "POST",
	    async: false,
	    data: { 
	      id:id
	    },
	    success: function(result) {
		    var obj= JSON.parse(result);
			console.log(obj.msg);
			alert(obj.msg);
	     }
	});
}
function userList(){
	$.ajax({
	  url: "userList.htm",
	  type:"get",
	  async: true,
	  data:{
	  },
	 success: function (response) {
		 var obj = JSON.parse(response);
		 console.log(obj);
		 var userdropdownList='<option>Select Users</option>';
		 for(i=0;i<obj.userList.length;i++){
			 userdropdownList+='<option value='+obj.userList[i].id+'>'+obj.userList[i].name+'</option>';
		 }
		 $('#username').append(userdropdownList);
		 }
		 });
		 }
		 
		function statusList(){
			$.ajax({
				  url: "timestatus",
				  type:"POST",
				  async: true,
				  data:{
				  },
				 success: function (response) {
					 var obj = JSON.parse(response);
					 //console.log(obj.statusList);
						//console.log(obj.statusList[0].id);
					 console.log(obj);
					 var statusdropdownList='<option>Select status</option>';
					 for( i=0;i<obj.statusList.length;i++){
						 statusdropdownList+='<option value='+obj.statusList[i].id+'>'+obj.statusList[i].status+'</option>';
					 }
					 $('#timesheetstatus').append(statusdropdownList);
					 }
					 });
		}
	function sendmail(submitType){
			 var userid=$('#username').val();
			 $.ajax({
				 url:"sendmail",
				 type:"POST",
				 async:false,
				 data:{
					 userid:userid,
					 submitType:submitType
				 },
				 success:function(response){
			      var obj= JSON.parse(response);
			       alert(obj.msg);
				 }
			 });
		 }

			
	function times(){
		var userid=0;
		if($('#username').val() != undefined){
		 userid=$('#username').val();
		}
		else{
			userid= '<%= session.getAttribute("userid") %>';
		}
		var date=$('#dtpFrom').val();
		var daterange = date.split('-');
		var startdate = daterange[0];
		var enddate = daterange[1];
		var statusid=$('#timesheetstatus').val();
		$.ajax({
			url:"addtimes",
			type:"POST",
			async:true,
			data:{
			  userId:userid,
			  startdate:startdate,
			  enddate:enddate,
			  statusid:statusid
			  },
			success: function(response){
				var obj =JSON.parse(response);
				console.log(obj);
				$('#timesheetbody').html('');
				var table='';
				for(var i=0;i<obj.sheetlist.length;i++){
					table+='<tr><td>'+obj.sheetlist[i].projectname+'</td><td>'+obj.sheetlist[i].comments+'</td>'
					+'<td>'+obj.sheetlist[i].totalhours+'</td><td>'+obj.sheetlist[i].submitteddate+'</td><td><a href="addTimeSheet?id='+obj.sheetlist[i].id+'"><i class="fa fa-edit"></i></a>|'
					+'<a href="" onclick="deletetimesheet('+obj.sheetlist[i].id+');"><i class="fa fa-trash"></i></a></td></tr>';
				}
			$('#timesheetbody').append(table);
			/*$('#approvebtn').show();
			$('#rejectbtn').show();*/
			$('#example').show();
			console.log(obj.sheetlist);
			}
		})
	}

	
	function exportTableToExcel(example, filename = ''){
    var downloadLink;
    var dataType = 'application/vnd.ms-excel';
    var tableSelect = document.getElementById(example);
    var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');
    
    // Specify file name
    filename = filename?filename+'.xls':'excel_data.xls';
    
    // Create download link element
    downloadLink = document.createElement("a");
    
    document.body.appendChild(downloadLink);
    
    if(navigator.msSaveOrOpenBlob){
        var blob = new Blob(['\ufeff', tableHTML], {
            type: dataType
        });
        navigator.msSaveOrOpenBlob( blob, filename);
    }else{
        // Create a link to the file
        downloadLink.href = 'data:' + dataType + ', ' + tableHTML;
    
        // Setting the file name
        downloadLink.download = filename;
        
        //triggering the function
        downloadLink.click();
    }
}
</script>
</script>
</html>
