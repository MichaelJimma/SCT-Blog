<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Registration Success</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
 <link rel='stylesheet' href='${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css'>
  <script src='${pageContext.request.contextPath}/bootstrap/jquery-3.1.1.min.js'></script>
  <script src='${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js'></script>
  <style type="text/css">
  .container{
  	width:700px;
  	}
  	.panel{
  		margin-top:10px;
  	}
  </style>
</head>

<body>
<div class="container">
  <div class="panel panel-default">
    <div class="panel-heading"><h2>Congratulation, ${user.firstName} ${user.lastName}</h2></div>
    <div class="panel-body">
    	<h4>Your registration is now complete.</h4>
    </div>
    <div class="panel-footer">
    	<a href="${pageContext.request.contextPath}/Login">Login</a>
    </div>
  </div>
</div>
	
</body>
</html>