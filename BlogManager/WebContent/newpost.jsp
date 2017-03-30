<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>New Post</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel='stylesheet' href='${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css'>
  <script src='${pageContext.request.contextPath}/bootstrap/jquery-3.1.1.min.js'></script>
  <script src='${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js'></script>
</head>
<style>
	.container{
		margin-top:15px;
	}
	.panel-heading{
		height:45px;
	}
</style>
<body>
<div class="container">
  <div class="panel panel-default">
    <div class="panel-heading">
    	<h4>Blog - New Post</h4>
    </div>
    <div class="panel-body">
    	<c:if test="${invalid}">
    		<div class="alert alert-danger">
			  <strong>You should write something before posting.</strong>
			</div>
	    </c:if>
	    <br>
    	<form method='post' action="${pageContext.request.contextPath}/NewPostController">
	    	<div class="form-group">
			  <textarea class="form-control" rows="10" id="post" name="post"></textarea>
			</div>
			<button type="submit" class="btn btn-default">Post</button>
		</form>
    </div>
    <div class="panel-footer">
    </div>
  </div>
</div>
</body>
</html>