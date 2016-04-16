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

public class Login extends HttpServlet
{
	private static final long serialVersionUID = -1622311585863258925L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if (user != null)
		{
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/start");
			rd.forward(request, response);
		}
		else
		{
			response.sendRedirect(userService.createLoginURL(request
					.getRequestURI()));
		}
	}
}
