package se.brutalakademien.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.datastore.jdoql.HandleFlummenTeamQuery;
import se.brutalakademien.model.FlummenTeam;
import se.brutalakademien.model.TeamMember;
import se.brutalakademien.servlets.utils.Utils;

public class TeamServlet extends FlummenServlet
{
	private static final String SFS_MEMBER = "sfsMember";
	
	private static final long serialVersionUID = 2646374553121374445L;
	
	private static final Logger log = Logger.getLogger(TeamServlet.class
			.getName());
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		String mode = request.getParameter("mode");
		if ("addMember".equalsIgnoreCase(mode))
		{
			String teamId = request.getParameter("teamId");
			boolean error = false;
			if (teamId != null)
			{
				String name = request.getParameter("namn");
				String pNumber = request.getParameter("personnummer");
				String allergies = request.getParameter("allergier");
				boolean needBed = (request.getParameter("sovsal") != null && request
						.getParameter("sovsal").equalsIgnoreCase("on"));
				boolean sfs = (request.getParameter(SFS_MEMBER) != null && request
						.getParameter(SFS_MEMBER).equalsIgnoreCase("on"));
				boolean sittning = (request.getParameter("sittning") != null && request
						.getParameter("sittning").equalsIgnoreCase("on"));
				
				if (name != null && name.length() > 0 && pNumber != null
						&& pNumber.length() > 0)
				{
					TeamMember newMember = new TeamMember(name, pNumber,
							allergies, needBed, sfs, sittning);
					
					HandleFlummenTeamQuery teamHandler = new HandleFlummenTeamQuery();
					teamHandler.addMember(teamId, newMember);
				}
				else
				{
					error = true;
				}
			}
			
			if (teamId == null || error)
			{
				request.getSession()
						.setAttribute(
								"MESSAGE",
								"Kunde inte lägga till medlemmen. Kontrollera så att all indata ser ok ut eller kontakta Phugel för att få det fixat :)");
			}
			
			response.sendRedirect("/flummen?page=anmalda");
			return;
		}
		else if ("updateSfs".equalsIgnoreCase(mode))
		{
			final String teamId = request.getParameter("teamId");
			final String memberPnumber = request.getParameter("memberPnumber");
			final boolean sfs = (request.getParameter(SFS_MEMBER) != null && request
					.getParameter(SFS_MEMBER).equalsIgnoreCase("on"));
			
			final HandleFlummenTeamQuery teamHandler = new HandleFlummenTeamQuery();
			teamHandler.updateSfs(teamId, memberPnumber, sfs);
		}
		else if ("setHasPayed".equals(mode))
		{
			final String teamId = request.getParameter("teamId");
			
			final HandleFlummenTeamQuery teamHandler = new HandleFlummenTeamQuery();
			teamHandler.setHasPayed(teamId, true);
		}
		else
		{
			ServletContext sc = getServletContext();
			RequestDispatcher rd;
			getSubMenu(request);
			
			String validateStr = request.getParameter("confirm");
			String email = request.getParameter("email");
			String city = request.getParameter("stad");
			String teamName = request.getParameter("lagnamn");
			String slogan = request.getParameter("slogan");
			
			if (validateStr == null || !validateStr.equalsIgnoreCase("on")
					|| email == null || email.length() == 0 || city == null
					|| city.length() == 0 || teamName == null
					|| teamName.length() == 0)
			{
				request.setAttribute(
						"MESSAGE",
						"Ett fel uppstod när anmälan skulle sparas. Var noga med att fylla i alla fält.");
				rd = sc.getRequestDispatcher("/jsp/flummen/anmalan.jsp");
			}
			else
			{
				FlummenTeam team = new FlummenTeam();
				team.setTeamName(teamName);
				
				Calendar now = Calendar.getInstance(Utils.TIME_ZONE);
				team.setDate(now.getTime());
				team.setEmail(email);
				team.setHasPayed(false);
				team.setSlogan(slogan != null && slogan.length() > 0 ? slogan
						: "");
				team.setCity(city);
				
				List<TeamMember> members = new ArrayList<TeamMember>();
				for (int i = 0; i < 100; i++)
				{
					final String name = (String) request.getParameter("namn"
							+ i);
					String pNumber = (String) request
							.getParameter("personnummer" + i);
					if ((name == null && pNumber == null)
							|| (name.length() == 0 && pNumber.length() == 0))
					{
						break;
					}
					
					String allergies = (String) request
							.getParameter("allergier" + i);
					String needBedStr = (String) request.getParameter("sovsal"
							+ i);
					
					pNumber = addHyphen(pNumber);
					allergies = (allergies != null) ? allergies : "";
					
					final boolean needBed = (needBedStr != null && needBedStr
							.equalsIgnoreCase("on"));
					final boolean sfs = (request.getParameter(SFS_MEMBER + i) != null && request
							.getParameter(SFS_MEMBER + i)
							.equalsIgnoreCase("on"));
					final boolean sittning = (request.getParameter("sittning"
							+ i) != null && request
							.getParameter("sittning" + i)
							.equalsIgnoreCase("on"));
					
					final TeamMember mem = new TeamMember(name, pNumber,
							allergies, needBed, sfs, sittning);
					
					members.add(mem);
					
				}
				
				team.setMembers(members);
				
				PersistenceManager pm = PMF.get().getPersistenceManager();
				try
				{
					pm.makePersistent(team);
				}
				finally
				{
					pm.close();
				}
				
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				
				try
				{
					Message msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(
							"admin@brutal-akademien.org", "Brutal-akademien"));
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(team.getEmail()));
					msg.addRecipient(
							Message.RecipientType.BCC,
							new InternetAddress(
									"admin@brutal-akademien.org.test-google-a.com"));
					msg.setSubject("Bokningskonfirmation");
					msg.setText("Tack för din anmälan!\n\nPriset ni betalar för Flumride är "
							+ team.getPrice()
							+ " kr .\nVänligen sätt in pangarna på vårt konto hos Nordea på kontonummer 1111,3103450 senast 2014-04-18, märk betalningen med ert lagnamn ("
							+ team.getTeamName()
							+ ").\n\nOm du upptäcker att något blev fel så är det bara att svara på det här mailet.\n\n--\nBrutal Akademien\nNe Sedibus Rotalibus Ludas");
					Transport.send(msg);
				}
				catch (Exception e)
				{
					log.severe(e.getMessage());
				}
				
				request.setAttribute("team", team);
				rd = sc.getRequestDispatcher("/jsp/flummen/confirmation.jsp");
			}
			
			rd.forward(request, response);
		}
	}
	
	private String addHyphen(String pNumber)
	{
		if (pNumber != null && pNumber.length() == 12
				&& pNumber.contains("-") == false)
		{
			String age = pNumber.substring(0, 8);
			String sista = pNumber.substring(8, 12);
			pNumber = age.concat("-").concat(sista);
		}
		
		return pNumber;
	}
	
}
