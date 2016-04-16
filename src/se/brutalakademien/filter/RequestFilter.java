package se.brutalakademien.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import se.brutalakademien.datastore.jdoql.GetBrutalareQuery;
import se.brutalakademien.model.Brutalare;
import se.brutalakademien.servlets.utils.Utils;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class RequestFilter implements Filter
{
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+02:00");
	private FilterConfig filterConfig;
	private static final Logger log = Logger.getLogger(RequestFilter.class
			.getName());
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		HttpSession session = request.getSession();
		
		session.setAttribute("IS_ANMALAN_OPEN", Utils.isAnmalanOpen());
		session.setAttribute("IS_TIME_FOR_COUNTDOWN",
				Utils.isTimeForCountdown());
		Calendar now = Calendar.getInstance();
		now.setTimeZone(TIME_ZONE);
		session.setAttribute("TIME_TO_ANMALAN",
				Utils.getOpenAnmalanTimeInMillis() - now.getTimeInMillis());
		
		if (session.getAttribute("user") == null && user != null)
		{
			GetBrutalareQuery brutalareQuery = new GetBrutalareQuery();
			Brutalare baUser = brutalareQuery.getByEmail(user.getEmail()
					.toLowerCase());
			
			boolean isAdmin = userService.isUserAdmin();
			if (baUser != null)
			{
				log.info("Brutalare loged in. " + baUser.getName()
						+ " med epost " + baUser.getEmail());
				
				isAdmin = isAdmin || baUser.isAdmin();
			}
			else
			{
				baUser = new Brutalare(user.getNickname(), user.getEmail(), -1,
						null, null, false, null);
			}
			session.setAttribute("user", baUser);
			session.setAttribute("isUserAdmin", isAdmin);
		}
		else if (user == null)
		{
			session.removeAttribute("isUserAdmin");
			session.removeAttribute("user");
		}
		
		String path = ((HttpServletRequest) req).getServletPath();
		if (path.contains(".php") || path.contains(".html")
				|| path.contains(".jsp"))
		{
			((HttpServletResponse) res).sendRedirect("/start");
			return;
		}
		
		filterChain.doFilter(request, res);
	}
	
	public FilterConfig getFilterConfig()
	{
		return filterConfig;
	}
	
	public void init(FilterConfig filterConfig)
	{
		this.filterConfig = filterConfig;
	}
	
	public void destroy()
	{
	}
	
}