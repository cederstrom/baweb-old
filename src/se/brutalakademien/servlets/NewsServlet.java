package se.brutalakademien.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.datastore.jdoql.GetActiveNewsQuery;
import se.brutalakademien.model.Brutalare;
import se.brutalakademien.model.News;
import se.brutalakademien.servlets.utils.Utils;

import com.google.appengine.api.datastore.Blob;

public class NewsServlet extends HttpServlet
{
	private static final long serialVersionUID = 8962339402281494585L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		
		Brutalare user = (Brutalare) request.getSession().getAttribute("user");
		
		if (user != null)
		{
			String title = request.getParameter("title");
			String text = request.getParameter("text");
			Blob image = null; // request.getParameter("image");
			Calendar now = Calendar.getInstance(Utils.TIME_ZONE);
			News news = new News(title, text, image, user.getName(), now.getTime());
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try
			{
				pm.makePersistent(news);
			}
			finally
			{
				pm.close();
			}
		}
		
		response.sendRedirect("/start");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		// Get the 10 latest news from DS
		GetActiveNewsQuery newsManager = new GetActiveNewsQuery();
		List<News> allNews = newsManager.get();
		request.setAttribute("allNews", allNews);
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/jsp/start.jsp");
		rd.forward(request, response);
	}
}
