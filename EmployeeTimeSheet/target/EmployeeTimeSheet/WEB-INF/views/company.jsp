<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<link rel="shortcut icon" type="image/png" href="/favicon.png"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/dataTables.bootstrap4.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datetimepicker.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/daterangepicker.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datepicker3.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-select.css" />
<script src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script> 
<style>
.error {
  color: #ff0000;
}
</style>
</head>
<body>

  <div class="container my-4">
  <form onsubmit="return true"  id="companySubmit" action="company" method="post" enctype="multipart/form-data">
  <div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="projectname">Logo:</label> 
						<input type="file"  class="form-control" name="file" id="file">
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="projectname">Application Name:</label> 
						<input type="text" class="form-control" name="applicationname" id="applicationname">
					</div>
				</div>
			</div>
		</div>
		</div>
	<span id="message" class="text-danger font-weight-bold">${msg}</span>
	<div class="row">
		<div class="col-sm-12 mt-4">
			<button type="submit" class="btn btn-primary">Add</button>
</div>
</div>
   </form>
  </div>
  
  </body>
  <jsp:include page="/WEB-INF/views/footer.jsp" />
  <script>
  $('#companySubmit').validate({
	    rules: {
	    	file: "required",
	    	applicationname: "required",
	    	startdate:"required",
	    	enddate:"required"
	    },
	    messages: {
	    	file : "Please upload logo image",
	    	applicationname: "please enter the application name"
	    },
	    submitHandler: function(form) {
	    	return true;
	    }
	  });
	  </script>
  </html>