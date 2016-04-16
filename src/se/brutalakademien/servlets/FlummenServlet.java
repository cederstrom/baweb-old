package se.brutalakademien.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.brutalakademien.datastore.jdoql.GetTeamsQuery;
import se.brutalakademien.model.FlummenTeam;
import se.brutalakademien.model.SubMenuItem;
import se.brutalakademien.model.TeamMember;
import se.brutalakademien.servlets.utils.Utils;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class FlummenServlet extends HttpServlet
{
	private static final long serialVersionUID = 8547739564973170982L;
	
	private static final int maxNrOfMembers = 145; // 150
	private static final int maxNrOfNotSfs = 25;
	private static final int maxNrOfBeds = 80;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		String page = request.getParameter("page");
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc
				.getRequestDispatcher("/jsp/flummen/flummen.jsp");
		
		if (page != null && page.length() > 0)
		{
			if (page.equalsIgnoreCase("phestplan"))
			{
				rd = sc.getRequestDispatcher("/jsp/flummen/phestplan.jsp");
			}
			else if (page.equalsIgnoreCase("anmalan"))
			{
				int nrOfMembers = 0;
				int nrOfOccupiedBeds = 0;
				int nrOfSitting = 0;
				int nrOfNotSfs = 0;
				List<FlummenTeam> allTeams = getThisYearsTeams(request);
				for (FlummenTeam team : allTeams)
				{
					List<TeamMember> members = team.getMembers();
					for (TeamMember member : members)
					{
						nrOfMembers++;
						if (member.isNeedBed())
						{
							nrOfOccupiedBeds++;
						}
						if (member.isSittning())
						{
							nrOfSitting++;
						}
						if (member.isSfs() == false)
						{
							nrOfNotSfs++;
						}
					}
				}
				
				request.setAttribute("NR_OF_MEMBERS_LEFT", maxNrOfMembers - nrOfMembers);
				request.setAttribute("NR_OF_NOT_SFS_LEFT", maxNrOfNotSfs - nrOfNotSfs);
				int nrOfBedsLeft = maxNrOfBeds - nrOfOccupiedBeds;
				nrOfBedsLeft = nrOfBedsLeft < 0 ? 0 : nrOfBedsLeft;
				request.setAttribute("NR_OF_BEDS_LEFT", nrOfBedsLeft);
				request.setAttribute("NR_OF_SITTING", nrOfSitting);
				
				rd = sc.getRequestDispatcher("/jsp/flummen/anmalan.jsp");
			}
			else if (page.equalsIgnoreCase("anmalda"))
			{
				UserService userService = UserServiceFactory.getUserService();
				User user = userService.getCurrentUser();
				if (user != null
						&& user.getEmail().equalsIgnoreCase(
								"flummen-info@brutal-akademien.org"))
				{
					request.setAttribute("isUserAdmin", true);
				}
				
				int nrOfTeamsTot = 0;
				int nrOfMembersTot = 0;
				int nrOFBedsTot = 0;
				int nrOfSfsTot = 0;
				int nrOfSitting = 0;
				
				// Get all submitted teams from DS
				List<FlummenTeam> allTeams = getThisYearsTeams(request);
				for (FlummenTeam team : allTeams)
				{
					nrOfTeamsTot++;
					nrOfMembersTot += team.getNrOfMembers();
					
					List<TeamMember> members = team.getMembers();
					for (TeamMember member : members)
					{
						if (member.isSfs() == true)
						{
							nrOfSfsTot++;
						}
						if (member.isNeedBed() == true)
						{
							nrOFBedsTot++;
						}
						if (member.isSittning())
						{
							nrOfSitting++;
						}
					}
				}
				
				request.setAttribute("nrOfTeamsTot", nrOfTeamsTot);
				request.setAttribute("nrOfMembersTot", nrOfMembersTot);
				request.setAttribute("nrOfSfsTot", nrOfSfsTot);
				request.setAttribute("nrOFBedsTot", nrOFBedsTot);
				request.setAttribute("nrOfSitting", nrOfSitting);
				request.setAttribute("allTeams", allTeams);
				
				rd = sc.getRequestDispatcher("/jsp/flummen/anmalda.jsp");
			}
			else if (page.equalsIgnoreCase("allergies"))
			{
				UserService userService = UserServiceFactory.getUserService();
				User user = userService.getCurrentUser();
				if (user != null
						&& user.getEmail().equalsIgnoreCase(
								"flummen-info@brutal-akademien.org"))
				{
					request.setAttribute("isUserAdmin", true);
				}
				
				// Get all submitted teams from DS
				List<FlummenTeam> allTeams = getThisYearsTeams(request);
				request.setAttribute("allTeams", allTeams);
				
				rd = sc.getRequestDispatcher("/jsp/flummen/allergies.jsp");
			}
			else if (page.equalsIgnoreCase("capzvm"))
			{
				rd = sc.getRequestDispatcher("/jsp/flummen/capzvm.jsp");
			}
			else if (page.equalsIgnoreCase("kontakter"))
			{
				rd = sc.getRequestDispatcher("/jsp/flummen/kontakter.jsp");
			}
		}
		
		getSubMenu(request);
		rd.forward(request, response);
	}
	
	private List<FlummenTeam> getThisYearsTeams(HttpServletRequest request)
	{
		String orderBy;
		if (request.getParameter("sortBy") == null)
			orderBy = "teamName";
		else
			orderBy = request.getParameter("sortBy");
		
		request.setAttribute("queriedOrder", orderBy);
		
		GetTeamsQuery teamsQuery = new GetTeamsQuery(orderBy);
		
		List<FlummenTeam> allTeams = teamsQuery.get();
		List<FlummenTeam> thisYearsTeams = new ArrayList<FlummenTeam>();
		
		Calendar now = Calendar.getInstance();
		now.setTimeZone(Utils.TIME_ZONE);
		int currentYear = now.get(Calendar.YEAR);
		for (FlummenTeam team : allTeams)
		{
			if (team.getDate() == null)
			{
				thisYearsTeams.add(team);
			}
			else
			{
				Calendar created = now;
				created.setTime(team.getDate());
				
				if (created.get(Calendar.YEAR) == currentYear)
					thisYearsTeams.add(team);
			}
		}
		
		return thisYearsTeams;
	}
	
	protected void getSubMenu(HttpServletRequest request)
	{
		List<SubMenuItem> subMenu = new LinkedList<SubMenuItem>();
		subMenu.add(new SubMenuItem("Anmälan", "/flummen?page=anmalan"));
		subMenu.add(new SubMenuItem("Phestplan", "/flummen?page=phestplan"));
		subMenu.add(new SubMenuItem("Capz VM", "/flummen?page=capzvm"));
		subMenu.add(new SubMenuItem("Kånntakter", "/flummen?page=kontakter"));
		subMenu.add(new SubMenuItem("Anmälda lag", "/flummen?page=anmalda"));
		request.setAttribute("subMenu", subMenu);
	}
	
}
