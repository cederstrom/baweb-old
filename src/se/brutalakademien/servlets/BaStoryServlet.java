package se.brutalakademien.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.brutalakademien.model.SubMenuItem;

public class BaStoryServlet extends HttpServlet
{
	private static final long serialVersionUID = 8547739564973170982L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		String page = request.getParameter("page");
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/jsp/whatIsBa.jsp");
		
		if (page != null && page.length() > 0)
		{
			if (page.equalsIgnoreCase("creationOfBa"))
			{
				rd = sc.getRequestDispatcher("/jsp/creationOfBa.jsp");
			}
			else if (page.equalsIgnoreCase("baGoesOn"))
			{
				rd = sc.getRequestDispatcher("/jsp/baGoesOn.jsp");
			}
			
		}
		
		List<SubMenuItem> subMenu = new LinkedList<SubMenuItem>();
		subMenu.add(new SubMenuItem("BA[tm] blir till",
				"/baStory?page=creationOfBa"));
		subMenu.add(new SubMenuItem("BA[tm] går Vidare",
				"/baStory?page=baGoesOn"));
		request.setAttribute("subMenu", subMenu);
		
		rd.forward(request, response);
	}
}
