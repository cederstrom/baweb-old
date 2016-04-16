<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- Header: always include this in the pages -->
		<jsp:include page="/includes/pageHeader.jsp" />
		
		<script type="text/javascript">
			$(document).ready(function(){
				$('#post_news_wrapper').hide();
				
				$('#open_news_form_btn').live('click', function(e) {
					e.preventDefault();
					
					if($('#post_news_wrapper').hasClass('hidden'))
					{
						$('#post_news_wrapper').removeClass('hidden');
						$('#post_news_wrapper').show();
						$(this).children('img').attr('src', '/images/buttons/close.png');
					}
					else
					{
						$('#post_news_wrapper').addClass('hidden');
						$('#post_news_wrapper').hide();
						$(this).children('img').attr('src', '/images/buttons/add.png');
					}
				});
			});
		</script>
		
		<!-- Google analytics: always include this last in the <head> -->
		<jsp:include page="/includes/google-analytics.jsp" />
	</head>
	<body>
		
		<!-- Page content -->
		<div class="page">
			<h1>Nyheter <c:if test="${isUserAdmin}"><a href="#" class="hideNewsBtn" id="open_news_form_btn"><img alt="Dölj nyhet" src="/images/buttons/add.png"></a></c:if></h1>
			
			<c:if test="${isUserAdmin}">
				
				<div class="post hidden" id="post_news_wrapper">
					<h2>Skapa nyhet</h2>
					<form action="/postNews" method="post" accept-charset="utf-8">
						<input type="text" name="title">
						<br/>
						<textarea name="text" rows="5" cols="100"></textarea>
						<br/>
						<input type="submit" value="Spara">
					</form>
				</div>
				<div class="post" style="clear: both"></div>
			</c:if>
			
			<c:forEach var="news" items="${allNews}">
				<div class="post">
					<c:if test="${isUserAdmin}"><a href="/hideNews?newsId=<c:out value="${news.key.id}"/>" class="hideNewsBtn"><img alt="Dölj nyhet" src="/images/buttons/close.png"></a></c:if>
					<h2><c:out value="${news.title}" /></h2>
					<p><c:out value="${news.text}" escapeXml="false" /></p>
					<p class="author">Skrivet av: <c:out value="${news.author}" /> - <c:out value="${news.date}" /></p>
				</div>
			</c:forEach>
			
		</div>
		
		<!-- Footer: always include this in the pages -->
		<jsp:include page="/includes/pageFooter.jsp" />
	</body>
</html>