<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Librarian Login</title>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="<c:url value='/resources/js/jquery.min.js'/>" type="text/javascript"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>

<script>
	setTimeout(function() {
    $('.alert').fadeOut('fast');
	}, 3000); // <-- time in milliseconds
</script>

<!-- login form -->
<div class="container" style="margin-top: 40px;">
	<c:if test="${not empty error}">
	<div class="row">
		<div class="alert alert-danger">
			<center>${error}</center>
		</div>
	</div>
	</c:if>
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">                                
					<div class="row-fluid">
						<center><a href="/"><img src="<c:url value='/resources/images/logo.png'/>"/></a></center>           
					</div>
				</div>
				<div class="panel-body">
				<form class="form" action="/userlogin" method="post"> 
				<fieldset> 
					<div class="form-group has-feedback">
						<input name="email" class="form-control" placeholder="Librarian Email" maxlength="32" required  />
					</div>
					<div class="form-group has-feedback">
						<input name="password" type="password" class="form-control" placeholder="Password" maxlength="20" required />                 
					</div>
					<div class="form-group">
					<button type="submit" class="btn btn-lg btn-success btn-block">Giriş Yap</button>
					</div>
				</fieldset>
				</form>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- footer -->
 <div class="navbar navbar-default navbar-fixed-bottom">
   <div class="container">
     <p class="navbar-text pull-left">© 2017 - Ramazan Zor - All Rights Reserved</p>
   </div>
 </div>

</body>
</html>