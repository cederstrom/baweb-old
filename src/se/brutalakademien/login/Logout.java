package se.brutalakademien.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Logout extends HttpServlet
{
	private static final long serialVersionUID = -763192814041913172L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException
	{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if (user != null)
		{
			HttpServletRequest request = (HttpServletRequest) req;
			request.getSession().removeAttribute("isUserAdmin");
			request.getSession().removeAttribute("user");
			
			resp.sendRedirect(userService.createLogoutURL(req.getRequestURI()));
		}
		else
		{
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/start");
			rd.forward(req, resp);
		}
		
	}
}
