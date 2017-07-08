
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rezervasyon Listesi</title>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="<c:url value='/resources/js/jquery.min.js'/>" type="text/javascript"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>

<!-- navbar -->
<jsp:include page="../partial/navbar.jsp" />

<div class="container" >
	
	<c:if test="${not empty rezervations}">
		<table class="table table-bordered ">
		<thead>
	      <tr>
	        <th>Kitap Id</th>
	        <th>Öğrenci Id</th>
	        <th>Öğrenci Adı</th>
	        <th>Öğrenci Telefon</th>
	        <th>Rezervasyon Tarihi</th>
	        <th>Dönüş Durumu</th>
	      </tr>
	    </thead>
	    <tbody>
		<c:forEach items="${rezervations}" var="rezervation">
			<tr>
				<td>${fn:escapeXml(rezervation.get("bookid"))}</td>
		        <td>${fn:escapeXml(rezervation.get("studentid"))}</td>
		        <td>${fn:escapeXml(rezervation.get("studentname"))}</td>
		        <td>${fn:escapeXml(rezervation.get("phone"))}</td>
		        <td>${fn:escapeXml(formatter.format(rezervation.get("date")))}</td>	
		        <td>${fn:escapeXml(rezervation.get("return"))}</td>	
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

 
