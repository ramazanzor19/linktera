
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Yeni Kitap</title>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="<c:url value='/resources/js/jquery.min.js'/>" type="text/javascript"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>

<!-- navbar -->
<jsp:include page="../partial/navbar.jsp" />

<div class="container" >
	<c:if test="${not empty error}">
		<script>
			setTimeout(function() {
		    $('.alert').fadeOut('fast');
			}, 3000); // <-- time in milliseconds
		</script>
		<div class="row">
			<div class="alert alert-danger">
				<center>${error}</center>
			</div>
		</div>
	</c:if>
	<div class="panel panel-info">
		<div class="panel-heading"><center>Yeni Kitap Ekle</center></div>
		<div class="panel-body">
			<form class="form" method="post" action="/newbook">
				<div class="form-group form-inline">
					<label class="col-sm-1" for="id">İd</label>
					<input id="id" name="id" class="form-control"  required />
				</div>
				<div class="form-group form-inline">
					<label class="col-sm-1" for="name">İsim</label>
					<input id="name" name="name" class="form-control" value="${bookname}"  required />
				</div>
				<div class="form-group form-inline">
					<label class="col-sm-1" for="writer">Yazar</label>
					<input id="writer" name="writer" class="form-control" value="${writer}" />
				</div>
				<div class="form-group form-inline">
					<label class="col-sm-1" for="publisher">Yayıncı</label>
					<input id="publisher" name="publisher" class="form-control" value="${publisher}"  required />
				</div>
				<div class="form-group form-inline">
					<label class="col-sm-1" for="count">Adet</label>
					<input id="count" name="count" class="form-control" />
				</div>
				<div class="form-group form-inline">
					<label class="col-sm-1" for="button"></label>
					<button class="btn btn-success">Ekle</button>
				</div>
			</form>
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

 
