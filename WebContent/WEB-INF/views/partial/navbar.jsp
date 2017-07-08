<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- navbar -->
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/"><img src="<c:url value='/resources/images/logo.png'/>" style="height: 30px; width: auto;" /></a>
    </div>
    
    <div class="collapse navbar-collapse">
    
    	 <ul class="nav navbar-nav">
	    	 <c:if test="${sessionScope.type eq 'admin'}">
	        	<li><a href="/librarians">Kütüphaneciler</a></li>
				<li><a href="/newlibrarian">Yeni Kütüphaneci</a></li>
	        </c:if>
	        <c:if test="${sessionScope.type eq 'user'}">
	        	<li class="dropdown">
	        		<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						Kitap<span class="caret"></span></a>
	        		<ul class="dropdown-menu">       			
	        			<li><a href="/books">Kitap Listesi</a></li>
	        			<li><a href="/newbook">Yeni Kitap</a></li>
	        		</ul>
	        	</li>
	        	<li class="dropdown">
	        		<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						Rezervasyon<span class="caret"></span></a>
	        		<ul class="dropdown-menu">       			
	        			<li><a href="/rezervations">Rezervasyonlar</a></li>
	        			<li><a href="/newrezervation">Yeni Rezervasyon</a></li>
	        			<li><a href="/backrezervation">Rezerasyon Dönüşü</a></li>
	        		</ul>
	        	</li>
	        </c:if>
    	</ul>
    	<c:if test="${empty sessionScope.type}">
          <ul class="nav navbar-nav navbar-right"> 	
      		<li><a href="/adminlogin"><img src="<c:url value="/resources/images/admin.png"/>" style="height: 30px; width: auto;" title="Admin girişi" /></a></li>
			<li><a href="/userlogin"><img src="<c:url value="/resources/images/login.png"/>" style="height: 30px; width: auto;" title="Kütüphaneci girişi" /></a></li>
		  </ul>
       </c:if>
      <c:if test="${not empty sessionScope.type}">
      	<ul class="nav navbar-nav navbar-right navbar-brand">
	       <li style="margin-right: 6px; margin-bottom:6px;">${name}</li>
	      	<li style="margin-bottom:6px; margin-top:-6px;">
	      	<form action="/logout" method="post"  class="form">
	      	<button type="submit" class="btn btn-default">Çıkış Yap</button>
	      	</form>
	      	</li>
	      	</ul>
       </c:if>	
    </div>
    
    </div>    
</nav>
