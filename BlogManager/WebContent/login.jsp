
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel='stylesheet' href='${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css'>
  <script src='${pageContext.request.contextPath}/bootstrap/jquery-3.1.1.min.js'></script>
  <script src='${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js'></script>
  
  <style type="text/css">
  .container{
  	width:700px;
  	}
  	.form-group{
  		width:640px;
  	}
  	.panel{
  		margin-top:10px;
  	}
  	.col-sm-2{
  		border:1px solid #CCCCCC;
  		height:30px;
  	}
  	span{
  		color:red;
  	}
  </style>
</head>

<%
	String username = "";
	String password = "";
	
	Cookie[] cookies = request.getCookies();
	
	if(cookies != null)
	{
		for(Cookie tempCookie : cookies)
		{
			if("usernameCookie".equals(tempCookie.getName()))
			{
				username = tempCookie.getValue();
				break;
			}
		}
		
		for(Cookie tempCookie : cookies)
		{
			if("passwordCookie".equals(tempCookie.getName()))
			{
				password = tempCookie.getValue();
				break;
			}
		}
	}
	
%>

<body>
<div class="container">
  <div class="panel panel-default">
    <div class="panel-heading"><h2>Login</h2></div>
    <div class="panel-body">
    	<c:if test="${userNotFound}">
	    	<span>Invalid username or password. Please try again.</span>
	    </c:if>
	    <br>
    	<form method="post" action="${pageContext.request.contextPath}/Login">
		  <div class="form-group">
		    <label for="username">User Name:</label>
		    <input type="text" class="form-control" id="username" name="username" value="<%= username %>"  required>
		  </div>
		  <div class="form-group">
		    <label for="password">Password:</label>
		    <input type="password" class="form-control" id="password" name="password" value="<%= password %>" required>
		  </div>
		  <div class="checkbox">
		    <label><input type="checkbox" name="remember_me" id="remember_me"> Remember me</label>
		  </div>
		<br>
		  <a href="${pageContext.request.contextPath}/RegisterServlet">Register</a> -Or- <button type="submit" class="btn btn-default">Login</button>
		</form>
    </div>
    <div class="panel-footer">
	    
    </div>
  </div>
</div>
</body>
</html>