<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- Header: always include this in the pages -->
		<jsp:include page="/includes/pageHeader.jsp" />

		<style type="text/css" media="print">
			.pageHeader, .pageFooter, .pageHeader-divider, .noprint {display: none;}
			* {color: black;}
			td {border: 1px solid black;}
			.post {border-bottom: 1px solid black; page-break-inside: avoid;}
		</style>
		
		<!-- Google analytics: always include this last in the <head> -->
		<jsp:include page="/includes/google-analytics.jsp" />
	</head>
	<body>
		<!-- Page content -->
		<div class="page">
			<h1 class="noprint">Allergier</h1>
			
			<c:if test="${isUserAdmin}">
				<table>
					<tr>
						<td style="width: 250px; border: none;">Namn</td>
						<td style="width: 120px; border: none;">Personnummer</td>
						<td style="width: 250px; border: none;">Allergier</td>
						<td style="width: 50px; border: none; text-align: center;">Sittning</td>
					</tr>
					
					<c:forEach var="team" items="${allTeams}">
						<c:forEach var="member" items="${team.members}">
							<c:if test="${member.allergies != null && member.allergies != ''}">
								<tr>
									<td><c:out value="${member.name}" /></td>
									<td><c:out value="${member.pNumber}" /></td>
									<td><c:out value="${member.allergies}" /></td>
									<td style="text-align: center;"><c:if test="${member.sittning eq true}">X</c:if></td>
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
				</table>
			</c:if>
			
		</div>
		
		<!-- Footer: always include this in the pages -->
		<jsp:include page="/includes/pageFooter.jsp" />
	</body>
</html>