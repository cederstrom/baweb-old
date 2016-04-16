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
		
		
		<c:if test="${isUserAdmin}">
			<script type="text/javascript">
				function updateSfs(teamId, memberPnumber, sfsMember, caller) {
					$.post("/submitTeam", {mode: "updateSfs", teamId: teamId, memberPnumber: memberPnumber, sfsMember: sfsMember}, function(data) {
						$(caller).remove();
					})
					.fail(function() { alert("Error"); });
				}
				
				function setHasPayed(teamId, caller) {
					$.post("/submitTeam", {mode: "setHasPayed", teamId: teamId}, success=function() {
						$(caller).parent('p').empty().text('Betalat: Ja');
						alert("Success");
					}, error=function() { 
						alert("Error"); 
					});
				};
			</script>
		</c:if>
		
		<!-- Google analytics: always include this last in the <head> -->
		<jsp:include page="/includes/google-analytics.jsp" />
	</head>
	<body>
		<!-- Page content -->
		<div class="page">
			<h1>Anmälda lag</h1>
			
			<div class="post bolder">
				<p>
					Totalt har vi <span class="orange large-font"><c:out value="${nrOfMembersTot}" /></span> anmälda kaospiloter fördelade på en bataljon om <span class="orange large-font"><c:out value="${nrOfTeamsTot}" /></span> lag.
					
				</p>
				<c:if test="${isUserAdmin}">
					<p>
						Antal sovplatser: <span class="orange large-font">${nrOFBedsTot}</span>
						<br/>
						Antal icke SFS-medlemmar: <span class="orange large-font">${nrOfMembersTot - nrOfSfsTot}</span>
						<br/>
						Antal sittningsbiljetter: <span class="orange large-font">${nrOfSitting}</span>
					</p>
				</c:if>
			</div>
			
			<div class="post noprint">
				<form action="/flummen" method="get" name="sortForm">
					<input type="hidden" name="page" value="anmalda">
					<fieldset>
						<label for="sortBy">Sortera efter
							<select id="sortBy" name="sortBy" onchange="document.sortForm.submit()">
								<option value="teamName" <c:if test="${queriedOrder eq 'teamName'}">selected</c:if> >lagnamn</option>
								<c:if test="${isUserAdmin}"><option value="date" <c:if test="${queriedOrder eq 'date'}">selected</c:if> >datum</option></c:if>
								<option value="city" <c:if test="${queriedOrder eq 'city'}">selected</c:if> >ort</option>
								<c:if test="${isUserAdmin}"><option value="hasPayed" <c:if test="${queriedOrder eq 'hasPayed'}">selected</c:if> >ej betalat</option></c:if>
							</select>
						</label>
					</fieldset>
				</form>
			</div>
			
			<c:forEach var="team" items="${allTeams}">
				<div class="post">
					<h2><c:out value="${team.teamName}" /><br/><font color="white" size="0.5">"<c:out value="${team.slogan}" />"</font></h2>
					<p>Från <c:out value="${team.city}" /></p>
					<p><c:out value="${team.nrOfMembers}" /> st kaospiloter:</p>
					<c:if test="${isUserAdmin}">
						<form action="/submitTeam" method="POST"  accept-charset="utf-8">
							<input type="hidden" id="mode" name="mode" value="addMember" />
							<input type="hidden" id="teamKey" name="teamId" value="<c:out value="${team.key.id}" />" />
							
							<table>
								<tr>
									<td style="width: 250px; border: none;">Namn</td>
									<td style="width: 120px; border: none;">Personnummer</td>
									<td style="width: 250px; border: none;">Allergier</td>
									<td style="width: 50px; border: none; text-align: center;">Sovasal</td>
									<td style="width: 50px; border: none; text-align: center;">SFS</td>
									<td style="width: 50px; border: none; text-align: center;">Sittning</td>
									<td style="width: 50px; border: none;"></td>
								</tr>
								<c:forEach var="member" items="${team.members}">
									<tr>
										<td><c:out value="${member.name}" /></td>
										<td><c:out value="${member.pNumber}" /></td>
										<td><c:out value="${member.allergies}" /></td>
										<td style="text-align: center;"><c:if test="${member.needBed eq true}">X</c:if></td>
										<td style="text-align: center;">
											<c:choose>
												<c:when test="${member.sfs eq true}">X</c:when>
												<c:when test="${isUserAdmin && user.name == 'Phugel'}">
													<a href="javascript:void(0)" onclick="updateSfs('${team.key.id}', '${member.pNumber}', 'on', this); return false;">SET</a>
												</c:when>
											</c:choose>
										</td>
										<td style="text-align: center;"><c:if test="${member.sittning eq true}">X</c:if></td>
										<td style="border: none;"></td>
									</tr>
								</c:forEach>
								<tr class="noprint">
									<td><input type="text" id="namn" name="namn" style="width: 98%"></td>
									<td><input type="text" id="personnummer" name="personnummer" style="width: 98%"></td>
									<td><input type="text" id="allergier" name="allergier" style="width: 98%"></td>
									<td style="text-align: center;"><input type="checkbox" id="sovsal" name="sovsal"></td>
									<td style="text-align: center;"><input type="checkbox" id="sfsMember" name="sfsMember"></td>
									<td style="text-align: center;"><input type="checkbox" id="sittning" name="sittning"></td>
									<td style="border: none;"><input type="submit" value="Lägg till"></td>
								</tr>
							</table>
						</form>
						
						<p class="noprint">Anmält av: <a href="mailto:<c:out value="${team.email}" />" class="in_text"><c:out value="${team.email}" /></a> - <c:out value="${team.date}" /></p>
						<p>Pris: <c:out value="${team.price}" /> kr</p>
						<p>Betalat:
							<c:if test="${team.hasPayed eq true}">Ja</c:if>
							<c:if test="${team.hasPayed eq false}">Nej <a href="javascript:void(0);" onclick="setHasPayed('${team.key.id}', this); return false;">[SET]</a></c:if>
						</p>

					</c:if>
					<c:if test="${not isUserAdmin}">
						<c:forEach var="member" items="${team.members}">
							${member.name}<br/>
						</c:forEach>
					</c:if>
				</div>
			</c:forEach>
			
		</div>
		
		<!-- Footer: always include this in the pages -->
		<jsp:include page="/includes/pageFooter.jsp" />
	</body>
</html>