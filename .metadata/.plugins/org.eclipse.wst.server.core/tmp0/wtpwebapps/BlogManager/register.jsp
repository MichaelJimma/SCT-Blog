<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang='en'>
<head>
<title>Welcome to SCT Blog</title>
<meta charset='utf-8'>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<link rel='stylesheet' href='${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css'>
<script src='${pageContext.request.contextPath}/bootstrap/jquery-3.1.1.min.js'></script>
<script src='${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js'></script>
<style type='text/css'>
.form-group {
	width: 640px;
}

.panel {
	margin-top: 10px;
}

.col-sm-2 {
	border: 1px solid #CCCCCC;
	height: 30px;
}

.container {
	width: 700px;
}

.validate{
	color:red;
}
h2{
	text-align:center;
}
</style>
</head>
<body>
	<div class='container'>
		<div class='panel panel-default'>
			<div class='panel-heading'>
			 <span class="pull-right">Have an account? &nbsp;<a href="${pageContext.request.contextPath}/Login">Login</a></span>
				<h2>SCT Blog</h2>
				<h4>SCT blog is a place where students discuss about computer technologies.</h4>
			</div>
			<div class='panel-body'>
				<form method='post' action="${pageContext.request.contextPath}/RegisterServlet">
					<div class="tab-content">
					  <div id="personal" class="tab-pane fade in active">
					  <c:if test="${error}">
						<div class="alert alert-danger">Some of the values you entered are invalid.</div>
					  </c:if>
					  <c:if test="${user_exist}">
						<div class="alert alert-danger">The email address you have entered is already registered.</div>
					  </c:if>
					    <h5>Enter your personal information in this section to register.</h5>
					    <div class='form-group'>
							<label for='fname'>First Name:</label> 
							<span class="validate">*</span>
							<input type='text' class='form-control' id='fname' name='first_name' value="<c:out value="${user.getFirstName()}" escapeXml="true"></c:out>" >
							<c:if test="${firstname}">
								<span class="validate">Invalid First Name</span>
							</c:if>
						</div>
						<div class='form-group'>
							<label for='lname'>Last Name:</label> 
							<span class="validate">*</span>
							<input type="text" class="form-control" id="lname" name="last_name" value="<c:out value="${user.getLastName()}" escapeXml="true"></c:out>" >
						    <c:if test="${lastname}">
						    	<span class="validate">Invalid Last Name</span>
						    </c:if>
						</div>
						 <div class="form-group">
						    <label for="email">Email:</label>
						    <span class="validate">*</span>
						    <input type="email" class="form-control" id="email" name="email" value="<c:out value="${user.getEmail()}" escapeXml="true"></c:out>" >
						    <c:if test="${email}">
						    	<span class="validate">Invalid Email</span>
						    </c:if>
						  </div>
						<div class='form-group'>
							<label for='cofirmemail'>Confirm Email:</label>
							<span class="validate">*</span>
							 <input type="email" class="form-control" id="confirmemail" name="confirm_email" value="<c:out value="${confirm_email_value}" escapeXml="true"></c:out>" >
						    <c:if test="${confirm_email}">
						    	<span class="validate">Email Not Match</span>
						    	<br>
						    </c:if>
						</div>
						<div class='form-group'>
							<label for='telephone'>Telephone:</label> 
							<span class="validate">*</span>
							 <input type="tel" class="form-control" id="telephone" name="telephone"  value="<c:out value="${user.getPhone()}" escapeXml="true"></c:out>" >
						    <c:if test="${phone}">
						    	<span class="validate">Invalid Telephone number</span>
						    </c:if>
						</div>
						<div class="form-group">
						  <label for="sel1">Year:</label>
						  <span class="validate">*</span>
						  <select class="form-control" id="sel1" name="year" >
						  	<c:if test="${user.getYear() != null && !user.getYear().isEmpty()}">
						    	<option selected="selected"><c:out value="${user.getYear()}" escapeXml="true"></c:out></option>
						    </c:if>
						  	<option value=''>--Select Year--</option>
							<option value='2010'>2010</option>
							<option value='2011'>2011</option>
							<option value='2012'>2012</option>
							<option value='2013'>2013</option>
							<option value='2014'>2014</option>
							<option value='2015'>2015</option>
							<option value='2016'>2016</option> 
						  </select>
						  <c:if test="${year}">
						    	<span class="validate">Select a Year</span>
						    </c:if>
						</div>
						<div class="form-group">
						  <label for="sel1">Major:</label>
						  <span class="validate">*</span>
						  <select class="form-control" id="sel1" name="major" >
						  <c:if test="${user.getMajor() != null && !user.getMajor().isEmpty()}">
						    	<option selected="selected"><c:out value="${user.getMajor()}" escapeXml="true"></c:out></option>
						    </c:if>
						 	<option value=''>--Select Major--</option>
						    <option>Computer Programmer Analyst</option>
						    <option>Game Programming</option>
						    <option>Computer Networking</option>
						    <option>Computer Security</option>
						  </select>
						  <c:if test="${major}">
						    	<span class="validate">Select a Major</span>
						    </c:if>
						</div>
					  </div>
					  <div id="credentials" class="tab-pane fade">
					    <h5>Create your user name and password in this section and click register to finish your registration.</h5>
					    <div class='form-group'>
							<label for='username'>Username:</label> 
							<span class="validate">*</span>
							<input type='text' class='form-control' id='username' name='username' value="<c:out value="${user.getUsername()}" escapeXml="true"></c:out>" >
							<c:if test="${username}">
						    	<span class="validate">User name is required.</span>
						    </c:if>
						</div>
						<div class='form-group'>
							<label for='password'>Password:</label> 
							<span class="validate">*</span>
							<input type='password' class='form-control' id='password' name='password' value="<c:out value="${user.getPassword()}" escapeXml="true"></c:out>" >
							<c:if test="${password}">
								<span class="validate">Invalid Password</span>
							</c:if>
						</div>
						<div class='form-group'>
							<label for='confirm_password'>Confirm Password:</label> 
							<span class="validate">*</span>
							<input type='password' class='form-control' id='confirm_password' name='confirm_password' value="<c:out value="${confirm_password_value}" escapeXml="true"></c:out>" >
							<c:if test="${confirm_password}">
								<span class="validate">Password Mismatch</span>
							</c:if>
						</div>
					  </div>
					  <div id="register" class="tab-pane fade">
					    <h5>Please read the <a href="#">terms and condition</a> before you register.</h5>
					   <br>
					     <div class="checkbox">
						    <label><input type="checkbox" onchange="document.getElementById('btn_register').disabled = !this.checked;" > I agree with the terms and condition</label>
						  </div>
						  <button id="btn_register" type='submit' class='btn btn-default' disabled>Register</button>
					  </div>
					</div>
					<ul class="nav nav-tabs">
					  <li class="active"><a data-toggle="tab" href="#personal">Personal</a></li>
					  <li><a data-toggle="tab" href="#credentials">Credentials</a></li>
					  <li><a data-toggle="tab" href="#register">Register</a></li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>