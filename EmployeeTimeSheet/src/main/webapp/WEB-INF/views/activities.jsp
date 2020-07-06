<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/views/header.jsp" />

<div class="container">
<h5 class="py-4">Activity</h5>

<div class="row">
		<div class="col-sm-12 mt-4">
			<a href="addActivity"><button type="button" class="btn float-right btn-primary" >Add Activity</button></a>
</div>
</div>
<div class="row">
<div class="col-sm-12 mt-3">
<table id="example" class="table table-striped table-bordered" style="width:100%">
        <thead>
            <tr>
                <th>Code</th>
                <th>ProjectName</th>
                <th>Startdate</th>
                <th>Enddate</th>
                <th>Action</th>
            </tr>
        </thead>
       <tbody>
       		 <c:forEach var="activities" items="${activity}">
			<tr>
				<td>${activities.code}</td>
				<td>${activities.projectname}</td>
				<td>${activities.startdate}</td>
				<td>${activities.enddate}</td>
				<td><a href="addActivity?id=<c:out value='${activities.id}'/>"><i class="fa fa-edit"></i></a>
					<span onclick="deleteAct('${activities.id}');"><i class="fa fa-trash"></i></span></td>
				</tr>
			</c:forEach>
			
         </tbody>    
        
    </table>
</div>
</div>
</div>
</body>
 <jsp:include page="/WEB-INF/views/footer.jsp" />
<script src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>

<script>
$(document).ready(function() {
    $('#example').DataTable();
    toastr.options = {
			  "closeButton": false,
			  "positionClass": "toast-top-center",
			  "preventDuplicates": false,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": "5000",
			  "extendedTimeOut": "1000"
			};
} );
function deleteAct(id){
	toastr.info('<div>Are you sure to delete the project!<button type="button" id="okBtn" class="btn btn-default" onclick=deleteActivity('+id+');>Yes</button><button type="button" id="surpriseBtn" class="btn" style="margin: 0 8px 0 8px">No</button></div>')

}
function deleteActivity(id) {
	$.ajax({
		url:'deleteactivity.htm',
		type:"POST",
		async: false,
		data: {
			id:id
		},
		success: function(result) {
			var obj=JSON.parse(result);
			console.log(obj.msg);
			if(obj.msg == "Activity Deleted"){
				toastr.success("Activity Deleted successfully");
				location.reload();
			}

			else if(obj.msg == "Activity Deletion failed"){
			   toastr.error("Activity Deletion failed");
			}
			else{
			  toastr.info(obj.msg);
			}
			
		}
		
	});
	
}

</script>
</html>