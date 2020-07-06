<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/header.jsp" />
<div class="container">
<h5 class="mt-4">Costcenter</h5>

<div class="row">
		<div class="col-sm-12 mt-4">
			<a href="addCostCenter"><button type="button" class="btn float-right btn-primary" >Add Costcenter</button></a>
</div>
</div>
<div class="row">
<div class="col-sm-12 mt-3">
<table id="example" class="table table-striped table-bordered" style="width:100%">
        <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>OrderDetails</th>
                <th>OwnerId</th>
                <th>DateCreated</th>
                <th>DateModified</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach  var="costcenter" items="${list}">
            <tr>
                <td>${costcenter.name}</td>
                <td>${costcenter.description}</td>
                <td>${costcenter.orderdetails}</td>
                <td>${costcenter.ownername}</td>
                <td>${costcenter.datecreated}</td>
                <td>${costcenter.datemodified}</td>
                <td><a href="addCostCenter?id=<c:out value='${costcenter.id}'/>"><i class="fa fa-edit"></i></a>
                <a href="" onclick="deletecost('${costcenter.id}');"><i class="fa fa-trash"></i></a>
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
} );
function deletecost(id){
	  $.ajax({
		  url:'deletecost.htm',
	      type:"POST",
	      async:false,
	      data:{
	    	  id:id
	      },
	      success: function(result){
	    	  var obj=JSON.parse(result);
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

	      }
	  });
}
</script>
</html>