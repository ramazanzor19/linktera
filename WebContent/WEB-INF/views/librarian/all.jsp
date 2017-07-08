
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Kütüphaneci Listesi</title>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="<c:url value='/resources/js/jquery.min.js'/>" type="text/javascript"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>

<!-- navbar -->
<jsp:include page="../partial/navbar.jsp" />

<div class="container" >
	
	<c:if test="${not empty librarians}">
		<table class="table table-bordered ">
		<thead>
	      <tr>
	        <th>İsim</th>
	        <th>Email</th>
	        <th>Telefon</th>
	        <th>Güncelle</th>
	        <th>Sil</th>
	      </tr>
	    </thead>
	    <tbody>
		<c:forEach items="${librarians}" var="user">
			<tr>
		        <td>${fn:escapeXml(user.get("name"))}</td>
		        <td>${fn:escapeXml(user.get("email"))}</td>
		        <td>${fn:escapeXml(user.get("phone"))}</td>	        
		       <td>
		       		<a role="button" class="btn btn-info" href="/updatelibrarian/${user.get('_id')}">Güncelle</a>

		        </td>
		        <td>
		        	<form action="/deletelibrarian" method="post">
		        		<input name="id" class="hidden" value="${user.get('_id')}" />
		        		<button class="btn btn-danger">Delete</button>
		        	</form>
		        </td>
		     </tr>
		</c:forEach>
		</tbody>
		</table>
	</c:if>
	
</div>

<!-- footer -->
 <div class="navbar navbar-default navbar-fixed-bottom">
   <div class="container">
     <p class="navbar-text pull-left">© 2017 - Ramazan Zor - All Rights Reserved</p>
   </div>
 </div>

</body>
</html>

 
