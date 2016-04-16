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
			<h1>Administration</h1>
			
			<div class="post">
				<form enctype="multipart/form-data" method="post" action="<c:out value="${uploadUrl}"/>" accept-charset="utf-8">
					<input type="hidden" name="page" value="addCaptcha">
					<label for="value">Value</label>	
					<input type="text" name="captchaValue">
					<label for="image">Image</label>
					<input type="file" name="captchaImage" size="40">
					<br/>
					<input type="submit" value="Spara">
				</form>	
				
				
				<img src="/administration/addCaptcha?img=1" />
			</div>
		</div>
		
		
		
		<!-- Footer: always include this in the pages -->
		<jsp:include page="/includes/pageFooter.jsp" />
	</body>
</html>