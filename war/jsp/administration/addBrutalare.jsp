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
			<h1>Lägg upp ett nytt BA-drägg[tm]</h1>
			
			<form action="/addBrutalare" method="post" accept-charset="utf-8">
				<div class="post">
					<fieldset>
						<label for="name"><h3>Namn</h3></label>
						<input type="text" id="name" name="name" class="large">
					</fieldset>
					
					<fieldset>
						<label for="email"><h3>E-post</h3></label>
						<input type="text" id="email" name="email" class="large">
					</fieldset>
					
					<fieldset>
						<label for="generation"><h3>Generation</h3></label>
						<input type="text" id="generation" name="generation" class="large">
					</fieldset>
					
					<fieldset>
						<label for="favoriteSport"><h3>Favvosport</h3></label>
						<input type="text" id="favoriteSport" name="favoriteSport" class="large">
					</fieldset>
					
					<fieldset>
						<label for="bestDekk"><h3>Bästa Dekk</h3></label>
						<input type="text" id="bestDekk" name="bestDekk" class="large">
					</fieldset>
					
					<fieldset>
						<label for="giveAdmin"><h3>Adminrättigheter</h3></label>
						<input type="checkbox" id="giveAdmin" name="giveAdmin">
					</fieldset>
					
					
				</div>
				
				
				<br/><br/>

				<div class="post">
					<input type="submit" value="Lägg till">
				</div>
				
			</form>
			
		</div>
		
		<!-- Footer: always include this in the pages -->
		<jsp:include page="/includes/pageFooter.jsp" />
	</body>
</html>