<jsp:include page="/WEB-INF/views/header.jsp" />
<div class="container mt-4">
<h5 class="mt-4">Add Costcenter</h5>

<form id ="costcenterSubmit" onsubmit="return false">
	<div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="name">Name:</label> <input type="text"
							class="form-control" id="name" name="name" maxlength="20"> <span
							id="name1" class="text-danger font-weight-bold"></span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label for="orderdetails">Order Details:</label> <input
							type="text" class="form-control" id="orderdetails"
							name="orderdetails" maxlength="20"> <span id="orderdetails1"
							class="text-danger font-weight-bold"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">

				<div class="col-sm-6">
					<div class="form-group">
						<label for="description">Description:</label>
						<textArea class="form-control" id="description" name="description" maxlength="20"></textArea>
						<span id="description1" class="text-danger font-weight-bold"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-12">
					<div class="form-group">
						<label for="role">Owner</label> <select class="form-control"
							id="owner" name="owner">
						</select> <span id="owner1" class="text-danger font-weight-bold"></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<button type="submit" id="addButton" class="btn btn-success">Add</button>
			<button type="button" id="updateButton" class="btn btn-success"
				onclick="updatecost()">Update</button>
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
	$(document).ready(function(){
	  $("button").click(function(){
	    $("p").slideToggle();
	  });
	$('#addButton').show();
 		$('#updateButton').hide();
	 var Id =<%=request.getParameter("id")%>
	 if(Id !=null){
		$('#addButton').hide();
		$('#updateButton').show();
		getNameById(Id);
	}
	getuserlist();
	toastr.options = {
			  "positionClass": "toast-top-center",
			  "preventDuplicates": false,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": "5000",
			  "extendedTimeOut": "1000"
		};
});
	
	function costcenter(){
		var name=$('#name').val();
		var description=$('#description').val();
		var orderdetails=$('#orderdetails').val();
		var ownerId=$('#owner').val();
		$.ajax({
			url:'addcost.htm',
			type:"POST",
			async:false,
			data:{
				name:name,
				description:description,
				orderdetails:orderdetails,
				ownerId:ownerId
			},
			success:function(result){
				var obj= JSON.parse(result);
				if(obj.msg == "Product created successfully"){
					toastr.success("Costcenter Created Successfully");
					}
				if(obj.msg == "Product already exists"){
					toastr.warning("Costcenter Already Exists");
					}
					if(obj.msg == "Product creation failed"){
					toastr.error("Costcenter Creation Failed");
					}

				toastr.success(obj.msg);
		  		$('#name').val("");
		  		$('#description').val("");
		  		$('#orderdetails').val("");
		  		$('#owner').val("0");

				//alert(obj.msg);
				//	location.href='costcenter';
				}
		});
	
	}
	function getuserlist(){
		$.ajax({
			url:'getuserlist.htm',
			type:"POST",
			async:false,
			data:{
				
			},
			success: function(result){
				var obj=JSON.parse(result);
				console.log(obj.userlist);
				var userlistDrop='<option value="0">Select users</option>';
				for(var i=0;i<obj.userlist.length;i++){
					userlistDrop +='<option value='+obj.userlist[i].userid+'>'+obj.userlist[i].name+'</option>';
				}
				$('#owner').append(userlistDrop);
				
				}
			});
	}
	function updatecost(){
		 var id =<%=request.getParameter("id")%>
					var name = $('#name').val();
						var description = $('#description').val();
						var orderdetails = $('#orderdetails').val();
						var ownerId=$('#owner').val();
						$.ajax({
							url : 'updateCost.htm',
							type : "POST",
							async : false,
							data : {
						    id:id,
							name:name,
							description:description,
							orderdetails:orderdetails,
							ownerId:ownerId
							},
							success:function(result){
							var obj = JSON.parse(result);
							if(obj.msg == "costcenter updated successfully"){
								toastr.success("Costcenter Updated Successfully");
								}
							
							if(obj.msg == "costcenter updation failed"){
								toastr.error("Costcenter Updation Failed");
								}
							toastr.success(obj.msg);
					  		$('#name').val("");
					  		$('#description').val("");
					  		$('#orderdetails').val("");
					  		$('#owner').val("0");

			                	//alert(obj.msg);
							}
						});
					}
	  function getNameById(id){
		  $.ajax({
			  url:'getCost.htm',
			  type:"POST",
			  async:false,
			  data:{
				 id:id
			      },
			      success: function(result) {
			  		var obj= JSON.parse(result);
			  		console.log(obj);
					console.log(obj.OwnerId);
			  		$('#name').val(obj.Name);
			  		$('#description').val(obj.Description);
			  		$('#orderdetails').val(obj.Orderdetails);
			  		$('#owner').val(obj.OwnerId);
			  		}
			  });
	}
	$('#costcenterSubmit').validate({
	    rules: {
	    	name: "required",
	    	description: "required",
	    	orderdetails:"required",
	    	owner:"required"
	    },
	    messages: {
	    	name : "Please enter projectname",
	    	description: "Please enter description",
	    	orderdetails: "Please enter orderdetails",
	    	owner:"Please select user"
	    },
	    submitHandler: function(form) {
	    	costcenter();
	    }
	  });  
	</script>
</body>
</html>
