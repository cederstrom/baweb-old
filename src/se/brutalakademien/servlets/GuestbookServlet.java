package se.brutalakademien.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.datastore.jdoql.GetGuestbookPosts;
import se.brutalakademien.model.GuestbookPost;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GuestbookServlet extends HttpServlet
{
	private static final long serialVersionUID = -9024943405925769403L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		GetGuestbookPosts query = new GetGuestbookPosts();
		List<GuestbookPost> allPosts = query.getAllPosts();
		
		request.setAttribute("allPosts", allPosts);
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/jsp/guestbook.jsp");
		rd.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		boolean okToPost = false;
		String author = request.getParameter("author");
		String message = request.getParameter("message");
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null)
		{
			okToPost = true;
		}
		else
		{
			String captchaValue = (String) request.getSession().getAttribute(
					"captchaValue");
			String captchaResponse = request.getParameter("captchaResponse");
			
			if (captchaResponse.equalsIgnoreCase(captchaValue))
			{
				okToPost = true;
			}
		}
		
		if (okToPost)
		{
			GuestbookPost guestbookPost = new GuestbookPost(author, message,
					new Date());
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try
			{
				pm.makePersistent(guestbookPost);
			}
			finally
			{
				pm.close();
			}
		}
		else
		{
			request.getSession().setAttribute("guestbookAuth", author);
			request.getSession().setAttribute("guestbookMsg", message);
			request.getSession().setAttribute("MESSAGE",
					"Idiotkontrollen misslyckades!");
		}
		
		response.sendRedirect("/guestbook");
	}
}
