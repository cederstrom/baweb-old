<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="pageFooter">
	<a href="mailto:admin@brutal-akademien.org">admin@brutal-akadamien.org</a> 
</div>

<c:if test="${not empty MESSAGE}">
	<script type="text/javascript">
		$(document).ready(function(){
			$.fancybox('<c:out value="${MESSAGE}" escapeXml="false" />');
		});
	</script>
	<c:set var="MESSAGE" value="" scope="session" />
</c:if>
