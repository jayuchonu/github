<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<div class="container">
<h5 class="mt-4">Projects</h5>

<div class="row">
	<div class="col-sm-12 mt-4">
		<a href="addProject"><button type="button" class="btn float-right btn-primary" >Add Project</button></a>
</div>
</div>
<div class="row">
<div class="col-sm-12 mt-3">

<table id="example" class="table table-striped table-bordered" style="width:100%">
        <thead>
            <tr>
                <th>ProjectName</th>
				<th>CreatedBy</th>
                <th>StartDate</th>
                <th>EndDate</th>
                <th>Users</th>
				<th>Action</th>
            </tr>
        </thead>
        <tbody>
	
      <c:forEach var="projects" items="${projectsList}">
            <tr>
                <td>${projects.projectname}</td>
				 <td>${projects.createdby}</td>
                <td>${projects.startdate}</td>
                <td>${projects.enddate}</td>
                <td>${projects.userList}</td>
				<td><a href="addProject?id=<c:out value='${projects.id}'/>"><i class="fa fa-edit"></i></a></td>
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
	fetchlogo();
	   toastr.options = {
				  "closeButton": false,
				  "positionClass": "toast-top-center",
				  "preventDuplicates": false,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "5000",
				  "extendedTimeOut": "1000"
				};
	$('#example').DataTable();
});

function deleteproj(id){
	toastr.info('<div>Are you sure to delete the project!<button type="button" id="okBtn" class="btn btn-default" onclick=deleteproject('+id+');>Yes</button><button type="button" id="surpriseBtn" class="btn" style="margin: 0 8px 0 8px">No</button></div>')

}
function deleteproject(id){
	  $.ajax({
	    url: 'deleteProject',
	    type: "POST",
	    async: false,
	    data: {
	       id:id,
	    },
	  success: function(result) {
			var obj= JSON.parse(result);
			console.log(obj);
			if(obj.msg == "Project Deleted"){
				toastr.success("Project Deleted successfully");
			}
			else if(obj.msg == "Project Deleted failed"){
			toastr.error("Project Deletion failed");
			}
			else{
				  toastr.info(obj.msg);
			}
			 location.reload();
	  }
	   
	});
}

</script>
</html>