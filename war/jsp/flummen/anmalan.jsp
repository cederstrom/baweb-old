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
			<h1>Anmälan</h1>
			
			<c:choose>
				<c:when test="${(IS_ANMALAN_OPEN == true && NR_OF_MEMBERS_LEFT > 0 && NR_OF_SITTING < 151) or isUserAdmin == true}">
					<script type="text/javascript">
						var nrOfBedsLeft = parseInt('${NR_OF_BEDS_LEFT}');
						var bedOptions = '';
						var n = 0;
						
						function addMember() {
							if (n < 9)
							{
								if ($('#namn'+n).val().length > 0 && $('#personnummer'+n).val().length > 0)
								{
									$('.form_error').each(function() {
								        $(this).removeClass('form_error')
								    });
								    
									if (check_pNumber($('#personnummer'+n)))
									{
										n++;
										var newMemberHTML = '<fieldset>\n';
										newMemberHTML += '<input type="text" id="namn'+n+'" name="namn'+n+'" class="medium">\n';
										newMemberHTML += '<input type="text" id="personnummer'+n+'" name="personnummer'+n+'" class="small">\n';
										newMemberHTML += '<input type="text" id="allergier'+n+'" name="allergier'+n+'" class="medium">\n';
										
										if (nrOfBedsLeft <= 0) {
											bedOptions = 'disabled="disabled"';
										}
										newMemberHTML += '<input type="checkbox" '+bedOptions+' id="sovsal'+n+'" name="sovsal'+n+'" class="small">\n';
										newMemberHTML += '<input type="checkbox" checked="checked" id="sfsMember'+n+'" name="sfsMember'+n+'" class="medium" onclick="$.fancybox(\'På grund av många anmälningar så kan vi inte ta emot fler som inte är SFS-medlemmar!\'); return false;" >\n';
										newMemberHTML += '<input type="checkbox" id="sittning'+n+'" name="sittning'+n+'" class="small">\n';
										newMemberHTML += '</fieldset>\n';
										
										$('#members').append(newMemberHTML);
										$('#spots-left').html((9 - n) + ' platser kvar');
									}
								}
								else
								{
									$.fancybox('Glupska lilla gris! Använd de fält du har innan du ber om mer.')
								}
							}
							else
							{
								$.fancybox('Max antal medlemmar är uppnått. Vi har väldigt svårt att tänka oss att du har så många som TIIO vänner. Har du det så får du skapa ett nytt lag.');
							}
						}
						
						$(function() {
							$('#spots-left').html((9 - n) + ' platser kvar');
							
							$('#slogan').live('keydown cut copy paste blur', function(e) {
								if($(this).val().length > 100)
								{
									e.preventDefault();
									$(this).val( $(this).val().substring(0, 100) );
								}
								else
								{
									var val = $(this).val().length;
									if(val == 0)
										val += 'px';
									else
										val += '%';
										
									$('#sloganBar').css('width', val);
								}
							});
						});
					</script>
					
					<div class="post">
						<p>
							Biljetterna har släppts! Priset för årets flumride är som tidigare år: 349 riksdaler!
						</p>
						<p>
							Om man vill att BA[tm] tillhandahåller sängplats så löser vi det för 100 pengar extra. Dvs totalt 449 riksdaler samt 100kr/person i deposition!
						</p>
						<p>
							Nytt för i år är att ni bara får ha <strong>10 personer</strong> per lag. Är ni fler än 10 får ni dela upp er i flera lag!
						</p>
						<p>
							<strong><i>På grund av många anmälningar så kan vi inte ta emot fler som inte är SFS-medlemmar!</i></strong>
						</p>
					</div>
					
					<form action="/submitTeam" method="post" accept-charset="utf-8" onsubmit="return validateSubmitTeam(this);">
					
						<div class="post">
							<fieldset>
								<label for="lagnamn"><h3>Lagnamn *</h3></label>
								<input type="text" id="lagnamn" name="lagnamn" class="large" />
							</fieldset>
						</div>
						
						<div class="post" id="members">
							<h3><label for="namn0">Lagmedlemmar</label></h3>
							<label for="namn0" class="medium addMemberLabel">Namn *</label>
							<label for="personnummer0" class="small addMemberLabel">Personnummer *</label>
							<label for="allergier0" class="medium addMemberLabel">Allergier</label>
							<label class="small addMemberLabel">Behöver sovsal</label>
							<label class="medium addMemberLabel">Medlem i <a class="white" href="http://www.sfs.se/medlemskarer" target="_blank">SFS-ansluten kår</a> Eller student vid BTH</label>
							<label class="small addMemberLabel">Sittning</label>
							
							<c:forEach var="n" begin="0" end="0">
								<fieldset>
									<input type="text" id="namn<c:out value="${n}"/>" name="namn<c:out value="${n}"/>" class="medium mem_name" />
									<input type="text" id="personnummer<c:out value="${n}"/>" name="personnummer<c:out value="${n}"/>" class="small mem_pnum" />
									<input type="text" id="allergier<c:out value="${n}"/>" name="allergier<c:out value="${n}"/>" class="medium" />
									<c:choose>
										<c:when test="${NR_OF_BEDS_LEFT > 0}">
											<input type="checkbox" id="sovsal<c:out value="${n}"/>" name="sovsal<c:out value="${n}"/>" class="small" />
										</c:when>
										<c:otherwise>
											<input type="checkbox" disabled="disabled" id="sovsal<c:out value="${n}"/>" name="sovsal<c:out value="${n}"/>" class="small" />
										</c:otherwise>
									</c:choose>
									<input type="checkbox" checked="checked" id="sfsMember<c:out value="${n}"/>" name="sfsMember<c:out value="${n}"/>" class="medium" onclick="$.fancybox('På grund av många anmälningar så kan vi inte ta emot fler som inte är SFS-medlemmar!'); return false;" />
									<input type="checkbox" id="sittning<c:out value="${n}"/>" name="sittning<c:out value="${n}"/>" class="small" />
								</fieldset>
							</c:forEach>
						</div>
						<input type="button" onclick="addMember()" style="margin-left: 10px;" value="Lägg till fler medlemar" />
						<span id="spots-left"></span>
						
						<div class="post">
							<fieldset>
								<label for="slogan"><h3>Slogan</h3></label>
								<p class="author" style="padding-left: 2px;">Max 100 tecken</p>
								<textarea id="slogan" name="slogan" rows="2" cols="71" style="margin-bottom: -1px;"></textarea>
								<div class="outer-progress-bar">
									<div id="sloganBar" class="inner-progress-bar"></div>
								</div>
							</fieldset>
						</div>
						
						<div class="post">
							<h3>Andra uppgifter</h3>
							<fieldset>
								<label for="email">E-post vi kan nå lagledaren på *</label>
								<input type="text" id="email" name="email" class="large email" />
							</fieldset>
							<br/>
							<fieldset>
								<label for="stad">Studentstad ni representerar *</label>
								<input type="text" id="stad" name="stad" class="large" />
							</fieldset>
						</div>
						
						<div class="post">
							<p>Anmälan till Flumride[tm] är bindande. Du kommer få en bekräftelse på din bokning med betalningsinformation till den e-postadress du har angivit.</p>
							<fieldset>
								<label for="confirm"><input type="checkbox" id="confirm" name="confirm" />Jag fattar, BA[tm] har rätt att kräva mig på pengar när jag gjort en anmälan. Genom att klicka här går jag med på det.</label>
							</fieldset>
						</div>
						<br/>
						<br/>
		
						<div class="post">
							<input type="submit" value="Anmäl lag!" />
						</div>
						
					</form>
					
				</c:when>
				<c:when test="${IS_ANMALAN_OPEN == false && IS_TIME_FOR_COUNTDOWN == true}">
					<div class="post bolder">
						<p>Biljetterna släpps om <span class="orange large-font" id="days"></span> dagar, <span class="orange large-font" id="hours"></span> timmar, <span class="orange large-font" id="minutes"></span> minuter och <span class="orange large-font" id="seconds"></span> sekunder...</p>
					</div>
					
					<div class="post">
						<p>
							Medan du väntar kan du passa på att ta reda på dina blivande lagmedlemmars personnummer och allergier samt om de är med i en <a class="white" href="http://www.sfs.se/medlemskarer" target="_blank">SFS-ansluten kår</a> eller ej.
						</p>
						<p>
							Nytt för i år är att ni bara får ha <strong>10 personer</strong> per lag. Är ni fler än 10 får ni dela upp er i flera lag!
						</p>
					</div>
					
					<script type="text/javascript">
						var offsetToStart = parseInt('${TIME_TO_ANMALAN}');
						var openAt = new Date();
						openAt.setTime(openAt.getTime() + offsetToStart);
						
						function countDown() {
							var now = new Date();
							var timeLeft = Math.floor((openAt.getTime() - now.getTime()) / 1000);
							
							$('#seconds').text(timeLeft % 60);
							$('#minutes').text(Math.floor(timeLeft / 60) % 60);
							$('#hours').text(Math.floor(timeLeft / 3600) % 24);
							$('#days').text(Math.floor(timeLeft / 86400));
							
							if(timeLeft > 0)
								setTimeout(function() { countDown(); }, 1000);
							else
								location.reload(true);
						}
						 
						// Start count down
						countDown();
					</script>
					
				</c:when>
				<c:otherwise>
					<div class="post">
						<p>
							Anmälan är stängd. Biljetterna är slutsålda! Lycka till nästa år!
						</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		
		<!-- Footer: always include this in the pages -->
		<jsp:include page="/includes/pageFooter.jsp" />
	</body>
</html>