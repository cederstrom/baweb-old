package se.brutalakademien.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.brutalakademien.datastore.jdoql.HideSpecificNewsQuery;

public class HideSpecificNewsServlet extends HttpServlet
{
	private static final long serialVersionUID = 5363274485620823883L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		if (request.getParameter("newsId") != null
				&& request.getParameter("newsId").length() > 0)
		{
			String key = request.getParameter("newsId");
			HideSpecificNewsQuery newsManager = new HideSpecificNewsQuery();
			newsManager.hide(key);
		}
		
		response.sendRedirect("/start");
	}
	
	public static void main(String[] args)
	{
		String key = "7";
		HideSpecificNewsQuery newsManager = new HideSpecificNewsQuery();
		newsManager.hide(key);
	}
}
