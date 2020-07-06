<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Time sheet</title>
<link rel="shortcut icon" type="image/png" href="/favicon.png"/>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/dataTables.bootstrap4.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datetimepicker.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/daterangepicker.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datepicker3.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/toastr.css" />

<script src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>

<style>
a { color: inherit; }
.error {
  color: #ff0000;
}

</style>
</head>
<body>
<div class="col-sm-12 mt-4">
<!--img src="${pageContext.request.contextPath}/resources/assets/images/logo.png" width="10%"></img-->
<!--span id="logo"></span>
<!--img src="${pageContext.request.contextPath}/resources/assets/images/user.png" style="float:right;width:3%;"></img-->
<!--span class="float-right">${sessionScope.username}</span><a href="login"><i class="fa fa-sign-out fa-lg" style="float:right;"></i></a!-->

<input type="hidden" value="${sessionScope.role}" id="role" >
</div> 
			
<nav class="navbar navbar-expand-md navbar-light">
    <a href="#" class="navbar-brand"><span id="logo"></span></a>
    <div id="navbarCollapse" class="collapse navbar-collapse">
        <ul class="nav navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">${sessionScope.username}</a>
                <div class="dropdown-menu dropdown-menu-right">
                    <a href="passwordchange" class="dropdown-item">Reset Password</a>
                    <div class="dropdown-divider"></div>
                    <a href="login"class="dropdown-item">Logout</a>
                </div>
            </li>
        </ul>
    </div>
</nav>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
<ul class="navbar-nav">
<c:choose>

<c:when test="${fn:containsIgnoreCase(sessionScope.role, 'Admin')}">
<li class="nav-item">
		<a class="nav-link" href="employees">Employees</a>
	</li>
	<li class="nav-item">
		<a class="nav-link" href="dashboard">Timesheets</a>
	</li>
	 <li class="nav-item">
     <a class="nav-link" href="costcenter">Costcenter</a>
    </li>
	<li class="nav-item">
      <a class="nav-link" href="projects">Projects</a>
    </li>
	<li class="nav-item">
      <a class="nav-link" href="activities">Activities</a>
    </li>
	<li class="nav-item">
      <a class="nav-link" href="addtimes">Reports</a>
    </li>
    </c:when>
	<c:when test="${fn:containsIgnoreCase(sessionScope.role, 'Supervisor')}">
		<li class="nav-item active">
			<a class="nav-link" href="dashboard">Timesheets</a>
		</li>
		<li class="nav-item">
      <a class="nav-link" href="projects">Projects</a>
    </li>
	<li class="nav-item">
      <a class="nav-link" href="activities">Activities</a>
    </li>
	<li class="nav-item">
      <a class="nav-link" href="addtimes">Reports</a>
    </li>
    </c:when>
    <c:otherwise>
        <li class="nav-item">
			<a class="nav-link" href="dashboard">Timesheets</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="addtimes">Reports</a>
		</li>
    </c:otherwise>
</c:choose>


  </ul>
</nav>

	<script>
$(document).ready(function() {
	fetchlogo();
});
function fetchlogo(){
	$.ajax({
	  url: "fetchlogo",
	  type:"GET",
	  async: false,
	  data:{
	  },
	 success: function (response) {
		 var obj = JSON.parse(response);
		 console.log(obj);
		 var image ='<img src="data:image/png;base64, '+obj.logo+'"  width="35%"/>';
		 $("#logo").html(image);
		 }
	});
}
$('.navbar-nav .nav-link').click(function(){
    $('.navbar-nav .nav-link').removeClass('active');
    $(this).addClass('active');
})
</script>