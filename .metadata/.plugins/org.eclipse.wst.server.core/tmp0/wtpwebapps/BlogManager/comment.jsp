<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>New Comment</title>
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
    	<h4>Blog - Add a comment</h4>
    </div>
    <br>
    <div class="well">
    	<p>${blog.content}</p>
    	<h4>${blog.user.firstName} ${blog.user.lastName} - ${blog.createdOn}</h4>
    </div>
    <div class="panel-body">
	    <br>
    	<form method='post' action="${pageContext.request.contextPath}/CommentController">
	    	<c:if test="${invalidComment}">
	    		<div class="alert alert-danger">
				  <strong>You can not add empty comment. Please write some comment and try again.</strong>
				</div>
		    </c:if>
	    	<div class="form-group">
			  <textarea class="form-control" rows="10" id="post" name="comment"></textarea>
			</div>
			<button type="submit" class="btn btn-default">Add Comment</button>
		</form>
    </div>
    <div class="panel-footer">
    </div>
  </div>
</div>
</body>
</html>