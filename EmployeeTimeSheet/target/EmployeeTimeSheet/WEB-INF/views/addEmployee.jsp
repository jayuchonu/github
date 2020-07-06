<jsp:include page="/WEB-INF/views/header.jsp" />

<style>
.error {
  color: #ff0000;
}
</style>
<div class="container mt-4">
<h5 class="mt-4">Add Employee</h5>

<form onsubmit="return false" id="employeeRegistration">
	<div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="username">UserName:</label> <input type="text"
							class="form-control" id="username" name="username" maxlength="20">
					<span id="username1" class="text-danger font-weight-bold"></span>
						
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label for="role">Role</label> 
						<select class="form-control" id="roleList" name="role">
							
						</select>
						<span id="role1" class="text-danger font-weight-bold"></span>
						
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12" id="passwordDiv">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="password">Password:</label> <input type="password"
							class="form-control" id="password" name="password" maxlength="8">
						<span id="password1" class="text-danger font-weight-bold"></span>
						
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label for="password">ConfirmPassword:</label> <input
							type="password" class="form-control" id="confirmpassword"
							name="confirmpassword" maxlength="8">
							<span id="confirmpassword1" class="text-danger font-weight-bold"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="firstname">FirstName:</label> <input type="text"
							class="form-control" id="firstname" name="firstname" maxlength="20">
					<span id="firstname1" class="text-danger font-weight-bold"></span>
							
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="lastname">LastName:</label> <input type="text"
							class="form-control" id="lastname" name="lastname" maxlength="20">
						<span id="lastname1" class="text-danger font-weight-bold"></span>
					</div>
				</div>
				</div>
				</div>
				<div class="col-sm-12">
				<div class="row">			
					<div class="col-md-6">
					<div class="form-group">
						<label for="fname">Email</label>
						<input type="text" id="email" name="email" class="form-control" maxlength="50">
						<span id="email1" class="text-danger font-weight-bold"></span>
						</div>
				</div>
					
			<div class="col-md-6">
			<div class="form-group">
				<label for="fname">Reporting Person</label>
				<select class="form-control" id="reportingperson" name="reportingperson">
							
						</select>
				<span id="reportingperson1" class="text-danger font-weight-bold"></span>
				
				</div>
			</div>
				
				</div>
			</div>
			
	<div class="row">
		<div class="col-sm-12">
			<button type="submit" id="addButton" class="btn btn-primary">Add</button>
			<button type="submit" id="updateButton" class="btn btn-primary" onClick="update();">update</button>
		</div>
	</div>
</div>
</form>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.validate.min.js"></script>

<script>

$(document).ready(function() {
   roleList();
   userList();
  $('#addButton').show();
  $('#updateButton').hide();
   var Id = <%= request.getParameter("id") %>
	if(Id != null){
		$('#passwordDiv').hide();
		$('#username').prop('disabled',true);
		$('#addButton').hide();
		$('#updateButton').show();
		 getUserById(Id);
	}
   // getroleList();
   toastr.options = {
				  "closeButton": false,
				  "positionClass": "toast-top-center",
				  "preventDuplicates": false,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "5000",
				  "extendedTimeOut": "1000"
				};
});
function Add(){
	 document.getElementById("username1").innerHTML = "";
	 document.getElementById("password1").innerHTML = "";
	 document.getElementById("firstname1").innerHTML = "";
	 document.getElementById("lastname1").innerHTML = "";
var username=$('#username').val();
var password=$('#password').val();
var confirmpassword=$('#confirmpassword').val();
var role=$('#roleList').val();
var firstname=$('#firstname').val();
var lastname=$('#lastname').val();
var email=$('#email').val();
var reportingperson =$('#reportingperson').val();
$.ajax({
    url: 'addEmployee.htm',
    type: "POST",
    async: false,
    data: {
	 username:username,
	 role:role,
	 password:password,
	 firstname:firstname,
	 lastname:lastname,
	 email:email,
	 reportingperson:reportingperson
    },
  success: function(result) {
		var obj= JSON.parse(result);
		if(obj.msg == "user Created successfully"){
		toastr.success("User Created Successfully");
		}
		if(obj.msg == "already exists"){
		toastr.warning("user already exists");
		}
		if(obj.msg == "user Creation failed"){
		toastr.error("User Creation Failed");
		}
		$('#username').val("");
		$('#firstname').val("");
		$('#lastname').val("");
		$('#email').val("");
		$('#roleList').val("0");
		$('#reportingperson').val("0");
		$('#password').val("");
		$('#confirmpassword').val("");
		//alert(obj.msg);
			///location.href='employees';
  }
});
}

function getUserById(id){
	$.ajax({
	    url: 'getUser.htm',
	    type: "POST",
	    async: false,
	    data: { 
	    id:id
	    
	    },
	    success: function(result) {
			var obj= JSON.parse(result);
			console.log(obj);
			console.log(obj.reportingperson);
			$('#username').val(obj.username);
			$('#firstname').val(obj.firstname);
			$('#lastname').val(obj.lastname);
			$('#email').val(obj.email);
			$('#roleList').val(obj.roleId);
			$('#reportingperson').val(obj.reportingperson);
		
			
	    } 
	});
	
	

}
function update(){
	//var role=$('#role').val();
	var Id =<%= request.getParameter("id") %>
	var role=$('#roleList').val();
	var firstname=$('#firstname').val();
	var lastname=$('#lastname').val();
	var email=$('#email').val();
	var reportingperson =$('#reportingperson').val();
	$.ajax({
	    url: 'update',
	    type: "POST",
	    async: false,
	    data: {
	    	 userId:Id,
		 role:role,
		 firstname:firstname,
		 lastname:lastname,
		 email:email,
		 reportingperson:reportingperson
	    },
	  success: function(result) {
			var obj= JSON.parse(result);
			console.log(obj);
			if(obj.msg == "user updated successfully"){
		toastr.success("User Updated Successfully");
		}
		
		if(obj.msg == "user updated failed"){
		toastr.error("User Updation Failed");
		}
			$('#firstname').val("");
			$('#lastname').val("");
			$('#email').val("");
			$('#roleList').val("");
			$('#reportingperson').val("");
		//alert(obj.msg);
	  }
	});
}


</script>
<script>
function roleList(){
$.ajax({
    url: 'rolelist.htm',
    type: "POST",
    async: false,
    data: {},
  success: function(result) {
		var obj= JSON.parse(result);
		console.log(obj.roleList);
		var roleListDrop ='<option value="">Select Role</option>';
		for(var i=0;i<obj.roleList.length;i++){
			  roleListDrop += '<option value='+obj.roleList[i].id+'>'+obj.roleList[i].role+'</option>';
		}
		
		$('#roleList').append(roleListDrop);
  }
});
}
function userList(){
	$.ajax({
	  url: "userList.htm",
	  type:"GET",
	  async: false,
	  data:{
	  },
	 success: function (response) {
		 var obj = JSON.parse(response);
		 console.log(obj);
		 var userdropdownList='<option value="">Select Reporting person</option>';
		 for(i=0;i<obj.userList.length;i++){
			 userdropdownList+='<option value='+obj.userList[i].id+'>'+obj.userList[i].name+'</option>';
		 }
		 $('#reportingperson').append(userdropdownList);
		 }
		 });
}


$('#employeeRegistration').validate({
	    rules: {
	      firstname: {
		   required:true,
		   minlength:3
		  },
	      lastname: "required",
		  role:"required",
		  reportingperson:"required",
		  username: {
		   required:true,
		   minlength:3
		  },
	      email: {
	        required: true,
	        email: true
	      },
	      password: {
	        required: true,
	        minlength: 6
	      }, 
		   confirmpassword : {
				required:true,
                minlength : 6,
                equalTo : '[name="password"]'
            }
	    },
	    messages: {
		  username :{required:"Please enter username",
		  minlength:"username must be at least 3 characters"},
	      firstname: {required:"Please enter firstname",
		  minlength:"firstname must be at least 3 characters"},
	      lastname: "Please enter lastname",
		  confirmpassword:{required:"Please enter confirmpassword",
		  equalTo:"Password and confirmpassword does not match"
		  },
		  role:"Please select role",
		  reportingperson:"Please select reportingperson",
	      password: {
	        required: "Please enter password",
	        minlength: "password must be at least 6 characters"
	      },
	      email: "Please enter a valid email address"
	    },
		submitHandler: function() {
   			 Add();
 		}
	  });
	  


</script>
</body>
</html>




