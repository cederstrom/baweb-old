<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- Header: always include this in the pages -->
		<jsp:include page="/includes/pageHeader.jsp" />
		
		<!-- Google analytics: always include this last in the <head> -->
		<jsp:include page="/includes/google-analytics.jsp" />
		
		<script type="text/javascript">
			var orderId = new Date().getTime();
			_gaq.push(['_addTrans',
			   orderId,			// order ID - required
	           '',				// affiliation or store name
	           '${team.price}.0',	// total - required
	           '',				// tax
	           '',				// shipping
	           '',				// city
	           '',				// state or province
	           ''				// country
	         ]);

	         _gaq.push(['_addItem',
			   orderId,					// order ID - required
	           'Anmälningsavgift',		// SKU/code - required
	           'Medlem',				// product name
	           '',						// category or variation
	           '349.0',					// unit price - required
	           '${team.nrOfMembers}'	// quantity - required
	         ]);
	         
	         _gaq.push(['_trackTrans']);
		</script>
	</head>
	<body>
		<!-- Page content -->
		<div class="page">
			<h1>Tack för din anmälan!</h1>
			
			<div class="post">
				<p>
					Nu jävlar är du och dina comrades-in-booze anmälda till årets Flumride!
				</p>
			</div>
			
			<div class="post">
				<h2>Skriv ner det här...</h2>
				<p>
					Priset ni betalar för denna eminenta tillställning är inte mer än <span class="orange large-font"><c:out value="${team.price}" /></span> kr.
					Dessa surt förvärvade kontanter vill vi ha insatta på Brutal Akademiens pengartvättskonto hos Nordea på konton 1111,3103450 senast <span class="orange large-font">2014-04-18</span>, märk betalningen med ert lagnamn (<c:out value="${team.teamName}" />).
				</p>
				<p>
					Om du upptäcker att något blev fel eller vill lägga till fler medlemmar så är det bara att kontakta oss på adressen nedan.
				</p>
			</div>
			
			<div class="post">
				<p>
					Du ska ha fått en bokningsbekräftelse via e-post med samma information som på den här sidan men det kan vara bra att <span class="orange large-font">spara denna information</span> utifall du inte fått mailet.
				</p>
			</div>
			
		</div>
		
		<!-- Footer: always include this in the pages -->
		<jsp:include page="/includes/pageFooter.jsp" />
	</body>
</html>