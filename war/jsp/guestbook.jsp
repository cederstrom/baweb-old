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
			<h1>Gästbok</h1>
			
			<div class="post">
				<p>
					Här kan ni slänga in ett fräckt meddelande[tm] om det finns några frågor om BA[tm] eller om Flumride[tm]. 
					<br/>
					Annars går det bra att skriva vad som helst också.
				</p>
				<p>
					Tips! Logga in med ett googlekonto så slipper du captchan.
				</p>
				<form action="/guestbook" method="post" accept-charset="utf-8">
					<label for="author"><h3>Namn</h3></label>
					<input type="text" id="author" name="author" value="<c:out value="${guestbookAuth}" />">
					<br/>
					<label for="message"><h3>Meddelande</h3></label>
					<textarea id="message" name="message" rows="5" cols="60"><c:out value="${guestbookMsg}" /></textarea>
					<br/>
					<c:if test="${empty user}">
						<label for="captchaResponse" style="padding-left:2px;"><img src="/image?type=captcha" /></label>
						<input type="text" class="small" id="captchaResponse" name="captchaResponse" style="margin-left:2px;" />
					</c:if>
					<input type="submit" value="Skicka">
				</form>
			</div>
			
			<div class="post">
			<c:forEach var="post" items="${allPosts}">
				<div>
					<hr/>
					<p><c:out value="${post.message}" /></p>
					<p class="author">Skrivet av <strong><c:out value="${post.author}" /></strong> - <c:out value="${post.date}" /></p>
					
					<c:if test="${not empty post.responseMessage}">
						<br />
						<p>
							BA[tm] svarade: <c:out value="${post.responseMessage}" />
						</p>
					</c:if>
				</div>
			</c:forEach>
			</div>
		
		</div>
		
		<!-- Rensa gamla opostade värden -->
		<c:set var="guestbookAuth" value="" scope="session" />
		<c:set var="guestbookMsg" value="" scope="session" />
		
		<!-- Footer: always include this in the pages -->
		<jsp:include page="/includes/pageFooter.jsp" />
	</body>
</html>