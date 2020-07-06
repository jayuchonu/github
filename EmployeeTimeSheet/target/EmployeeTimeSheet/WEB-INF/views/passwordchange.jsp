<jsp:include page="/WEB-INF/views/header.jsp" />

<div class="container mt-4">
<h5 class="mt-4">Passwordchange</h5>

<form action="passwordchange"  method="post"  id ="passwordchange" onsubmit="return true">
	<div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="projectname">OldPassword:</label>     
						<input type="password" id="oldpassword" name="oldpassword" class="form-control"  maxlength="20">
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="newpassword">NewPassword:</label>
							<input type="password" id="newpassword" name="newpassword" class="form-control" maxlength="20">
					</div>
				</div>
				</div>
				</div>
				<div class="col-sm-12">
				<div class ="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="startdate">ConfirmPassword:</label>
							<input type="password" id="confirmpassword" name="confirmpassword" class="form-control" maxlength ="20">
					</div>
				</div>
			</div>
		</div>
	<div class="col-sm-12">
			<button type="submit" id="addButton" class="btn btn-success">Submit</button>
		</div>
	</div>
<span id="message" class="text-danger font-weight-bold">${msg}</span>

</form>
</div>
 <jsp:include page="/WEB-INF/views/footer.jsp" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-select.min.js"></script>

<script>
	
	$('#passwordchange').validate({
		   rules: {
	      oldpassword: {
		   required:true,
		  },
		 
	      newpassword: {
	        required: true,
	        minlength: 6
	      }, 
		   confirmpassword : {
				required:true,
                minlength : 6,
                equalTo : '[name="newpassword"]'
            }
	    },
	    messages: {
		  oldpassword :{required:"Please enter oldpassword"},
		  confirmpassword:{required:"Please enter confirmpassword",
		  minlength: "password must be at least 6 characters",
		  equalTo:"Password and confirmpassword does not match"
		  },
	      newpassword: {
	        required: "Please enter new password",
	        minlength: "password must be at least 6 characters"
	      }
	    },
		submitHandler: function() {
   			return true;
 		}
	  });
	  

</script>
</body>
</html>
