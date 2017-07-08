<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LinkTera Library Management</title>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="<c:url value='/resources/js/jquery.min.js'/>" type="text/javascript"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>

<!-- navbar -->
<jsp:include page="partial/navbar.jsp" />

<div class="container">
	<div class="jumbotron">
		<h4>Linktera kütüphane yönetim sistemine hoşgeldiniz</h4>
		<h5>Admin girişi yapmak için <img src="<c:url value='/resources/images/admin.png'/>" style="height: 30px; width: auto;" /> ikonuna tıklayınız</h5>
		<h5>Kütüphaneci girişi yapmak için <img src="<c:url value='/resources/images/login.png'/>" style="height: 30px; width: auto;"/> ikonuna tıklayınız</h5>
		<h5><img src="<c:url value='/resources/images/logo.png'/>" style="height: 30px; width: auto;"/>  ikonuna tıklayarak anasayfaya dönebilirsiniz</h5>
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