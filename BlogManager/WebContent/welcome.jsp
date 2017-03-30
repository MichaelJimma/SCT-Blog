<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Welcome</title>
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
	.headername{
		font-size: 17px;
		text-decoration: bold;
	}
</style>
<body>
<div class="container">
  <div class="panel panel-default">
    <div class="panel-heading">
    	<h4> <span class="pull-right">Welcome, &nbsp; <c:out value="${user.getFirstName()}" escapeXml="true"></c:out>&nbsp;<c:out value="${user.getLastName()}" escapeXml="true"></c:out>!&nbsp;<a href="${pageContext.request.contextPath}/Logout">Logout</a></span> </h4>
    </div>
    <div class="panel-body">
    	<div class="row" style="text-align:center">
    	  <h3>Blog</h3>
    	  <hr>
    	</div>
    	<div class="row">
    	<div class="col-sm-4">
    		<a href="${pageContext.request.contextPath}/NewPostController"><b>Write a post</b></a>
    		<br>
    		<br>
    	</div>
    	</div>
    	<c:forEach items="${blogs}" var="blog">
		    <div class="panel panel-default">
			    <div class="panel-heading" style="height: 60px;padding:0">
			    	<p>${blog.content}<br><span class="headername">${blog.user.firstName} ${blog.user.lastName} - </span>${blog.getCreatedOn()}</p>
			    </div>
			    <div class="panel-body">
			   		<div>
			   		${comment.getContent()}
			   			<c:forEach items="${comments}" var="comment">
		   				  <c:if test="${blog.id == comment.blog.id}">
					    	<p><b>${comment.user.getFirstName()} says,</b>  ${comment.getContent()}</p>
					    	<p><small>${comment.getCreatedOn()}</small></p>
					    	<hr>
					      </c:if>
			   			</c:forEach>
			   		</div>
			    </div>
			    <div class="panel-footer">
			    <a href="${pageContext.request.contextPath}/CommentController?id=${blog.id}">Add a comment</a>
			    </div>
			  </div>
		</c:forEach>
    </div>
    <div class="panel-footer">
    </div>
  </div>
</div>
</body>
</html>