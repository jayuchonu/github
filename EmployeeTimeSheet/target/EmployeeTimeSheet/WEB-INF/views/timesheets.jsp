<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<div class="container">
<div class="row">
		<div class="col-sm-12 mt-4">
			<a href="addTimeSheet"><button type="button" class="btn float-right btn-primary" onclick="timesheets()">Add TimeSheet</button></a>
	</div>
</div>
<div class="row">
<div class="col-sm-12 mt-3">
<table id="example" class="table table-striped table-bordered" style="width:100%">
        <thead>
            <tr>
                <th>Timefrom</th>
                <th>Timeto</th>
                <th>Activity</th>
                <th>Comments</th>
                <th>Projects</th>
                <th>Totalhours</th>
                <th>Submitteddate</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="employeetimesheet" items="${sheets}">
            <tr>
               <td>${employeetimesheet.timefrom}</td>
                <td>${employeetimesheet.timeto}</td>
                <td>${employeetimesheet.activity}</td>
                <td>${employeetimesheet.comments}</td>
                <td>${employeetimesheet.projectname}</td>
                <td>${employeetimesheet.totalhours}</td>
                <td>${employeetimesheet.submitteddate}</td>
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
} );
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
			toastr.options = {
					  "positionClass": "toast-top-center",
					  "preventDuplicates": false,
					  "showDuration": "300",
					  "hideDuration": "1000",
					  "timeOut": "5000",
					  "extendedTimeOut": "1000"
			};
			toastr.success(obj.msg);
	     }
	});
}	
</script>
</html>