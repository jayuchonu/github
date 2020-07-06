<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/views/header.jsp" />

<div class="container">
<h5 class="mt-4">Employees</h5>

<div class="row">
		<div class="col-sm-12 mt-4">
			<a href="addEmployee"><button type="button" class="btn float-right btn-primary">Add Employee</button></a>
</div>
</div>
<div class="row">
<div class="col-sm-12 mt-3">
<table id="example" class="table table-striped table-bordered" style="width:100%">
        <thead>
            <tr>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>Email</th>
                <th>Role</th>
                <th>ReportingPerson</th>
                <th>Action</th>
                </tr>
		</thead>
		<tbody>
			<c:forEach var="users" items="${users}">
			<tr>
				<td>${users.firstname}</td>
				<td>${users.lastname}</td>
				<td>${users.email}</td>
				<td>${users.role }</td>
				<td>${users.reportingperson}</td>
				<td><a href="addEmployee?id=<c:out value='${users.userid}'/>"><i class="fa fa-edit"></i>
				</a><span onclick="deleteusr('${users.userid}');"><i class="fa fa-trash"></i>
				</span>
				</td>
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
function deleteusr(id){
	toastr.info('<div>Are you sure to delete the project!<button type="button" id="okBtn" class="btn btn-default" onclick=deleteuser('+id+');>Yes</button><button type="button" id="surpriseBtn" class="btn" style="margin: 0 8px 0 8px">No</button></div>')

}
function deleteuser(userid){
		$.ajax({
		    url: 'deleteuser.htm',
		    type: "POST",
		    async: false,
		    data: {
		      userid:userid
		    },
		    success: function(result) {
				var obj= JSON.parse(result);
				if(obj.msg == "User Deleted"){
					toastr.success("User Deleted successfully");
				}
				else if(obj.msg == "User Deletion failed"){
				toastr.error("User Deletion failed");
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