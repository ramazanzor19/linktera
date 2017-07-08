
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Kitap Listesi</title>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="<c:url value='/resources/js/jquery.min.js'/>" type="text/javascript"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>

<!-- navbar -->
<jsp:include page="../partial/navbar.jsp" />

<div class="container" >
	
	<c:if test="${not empty books}">
		<table class="table table-bordered ">
		<thead>
	      <tr>
	        <th>İd</th>
	        <th>İsim</th>
	        <th>Yazar</th>
	        <th>Yayıncı</th>
	        <th>Adet</th>
	        <th>Rezervasyon Sayısı</th>
	        <th>Toplam Rezervasyon Sayısı</th>
	        <th>Sil</th>
	      </tr>
	    </thead>
	    <tbody>
		<c:forEach items="${books}" var="book">
			<tr>
				<td>${fn:escapeXml(book.get("id"))}</td>
		        <td>${fn:escapeXml(book.get("name"))}</td>
		        <td>${fn:escapeXml(book.get("writer"))}</td>
		        <td>${fn:escapeXml(book.get("publisher"))}</td>	
		        <td>${fn:escapeXml(book.get("count"))}</td>	
		        <td>${fn:escapeXml(book.get("rezervation_count"))}</td>	
		        <td>${fn:escapeXml(book.get("total_rezervation_count"))}</td>	        
		        <td>
		        	<form action="/deletebook" method="post">
		        		<input name="id" class="hidden" value="${book.get('_id')}" />
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

 
