<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>Intornätt&#8482; - Brutal Akademien</title>

<style type="text/css" media="screen">
	@import url("/css/style.css");
	@import url("/fancybox/jquery.fancybox-1.3.4.css");
</style>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
<script type="text/javascript" src="/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="/js/core.js"></script>
<script type="text/javascript" src="/js/formCheck.js"></script>

<div class="pageHeader-bg">
	
	<div class="pageHeader">
		<div class="menuUser">
			<c:choose>
				<c:when test="${not empty user}">
					<c:out value="${user.name}" />
					|
					<a href="/logout">Logga ut</a>
				</c:when>
				<c:otherwise>
					<a href="/login">Logga in</a>
				</c:otherwise>
			</c:choose>
		</div>
		<div style="clear: both"></div>
		
		<div class="menu">
			<a href="/start" id="start"><img src="/images/buttons/start.png"></a>
			<a href="/baStory" id="about"><img src="/images/buttons/vadebatm.png"></a>
			<a href="/sporrtNews" id="sport"><img src="/images/buttons/sparrtnytt.png"></a>
			<a href="/flummen" id="flumride"><img src="/images/buttons/flummentm.png"></a>
		</div>
		
		
	</div>
	<div class="pageHeader-divider">
		<c:if test="${not empty subMenu}">
			<div id="submenu" class="submenu">
				<c:forEach var="item" items="${subMenu}">
					<a href="<c:out value='${item.href}' />"><c:out value='${item.name}' /></a>
				</c:forEach>
			</div>
		</c:if>
	</div>
	
</div>