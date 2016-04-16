<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- Header: always include this in the pages -->
		<jsp:include page="/includes/pageHeader.jsp" />
		
		<!-- Google analytics: always include this last in the <head> -->
		<jsp:include page="/includes/google-analytics.jsp" />
	</head>
	<body>
		
		<!-- Page content -->
		<div class="page">
			<h1>Flumride[tm]</h1>
			
			<div class="post">
				<p>
					Sexmästeriet i Karlskrona och BA[tm] svettas som feta oäkta finnar i en sauna nu när planeringen inför Flumride[tm] har tagit sin början.
				</p>
				<p>
					Nytt för i år är att Flummen hålls i Sveriges blötaste studentstad, Karlskrona! Som om detta inte var nog underbart så utlovas i år ett antal nya sphårrter, mer brutala än någonsin.
				</p>
				<p>
					Det finns ett event och lite annan skit på <strong><a href="https://www.facebook.com/events/620400688031399/" target="_blank">fejsbook</a></strong>. Kolla in det om du gillar data.
				</p>
				<p>
					<strong>Vi ses i khånntajnern!</strong>
				</p>
			</div>
			
			<c:if test="${IS_ANMALAN_OPEN == true}">
				<center><a href="/flummen?page=anmalan"><img alt="Anmälan" src="/images/buttons/anmalan.png"></a></center>
			</c:if>
			
			<center>
				<iframe width="800" height="450" src="//www.youtube.com/embed/xcMEDFcV8xE" frameborder="0" allowfullscreen></iframe>
			</center>
		</div>
		
		<!-- Footer: always include this in the pages -->
		<jsp:include page="/includes/pageFooter.jsp" />
	</body>
</html>