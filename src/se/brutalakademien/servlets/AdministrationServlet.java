package se.brutalakademien.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.model.CaptchaImageBlobKey;
import se.brutalakademien.model.SubMenuItem;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AdministrationServlet extends HttpServlet
{
	private static final long serialVersionUID = 8547739564973170982L;
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserAdmin())
		{
			ServletContext sc = getServletContext();
			RequestDispatcher rd;
			String page = request.getParameter("page");
			
			if (page != null && page.equalsIgnoreCase("addCaptcha"))
			{
				String uploadUrl = blobstoreService
						.createUploadUrl("/administration");
				request.setAttribute("uploadUrl", uploadUrl);
				rd = sc.getRequestDispatcher("/jsp/administration/addCaptcha.jsp");
			}
			else if (page != null && page.equalsIgnoreCase("addBrutalare"))
			{
				rd = sc.getRequestDispatcher("/jsp/administration/addBrutalare.jsp");
			}
			else
			{
				rd = sc.getRequestDispatcher("/jsp/administration/Administration.jsp");
			}
			
			List<SubMenuItem> subMenu = new LinkedList<SubMenuItem>();
			subMenu.add(new SubMenuItem("Add captcha image",
					"/administration?page=addCaptcha"));
			subMenu.add(new SubMenuItem("Lägg till BA-drägg[tm]",
					"/administration?page=addBrutalare"));
			
			request.setAttribute("subMenu", subMenu);
			
			rd.forward(request, response);
		}
		else
		{
			HttpServletRequest req = (HttpServletRequest) request;
			req.getSession().invalidate();
			response.sendRedirect("/start");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		UserService userService = UserServiceFactory.getUserService();
		if (userService.isUserAdmin())
		{
			
			String page = request.getParameter("page");
			String returnPage = "/administration";
			
			if (page != null)
			{
				if (page.equalsIgnoreCase("addCaptcha"))
				{
					returnPage = "/administration";
					String captchaValue = request.getParameter("captchaValue");
					
					Map<String, BlobKey> blobs = blobstoreService
							.getUploadedBlobs(request);
					BlobKey blobKey = blobs.get("captchaImage");
					
					CaptchaImageBlobKey captcha = new CaptchaImageBlobKey(
							captchaValue, blobKey);
					
					PersistenceManager pm = PMF.get().getPersistenceManager();
					try
					{
						pm.makePersistent(captcha);
					}
					finally
					{
						pm.close();
					}
					
				}
			}
			
			response.sendRedirect(returnPage);
		}
		else
		{
			HttpServletRequest req = (HttpServletRequest) request;
			req.getSession().invalidate();
			response.sendRedirect("/start");
		}
	}
	
	protected void getSubMenu(HttpServletRequest request)
	{
		List<SubMenuItem> subMenu = new LinkedList<SubMenuItem>();
		subMenu.add(new SubMenuItem("Add captcha image",
				"/administration?page=addCaptcha"));
		subMenu.add(new SubMenuItem("Lägg till BA-drägg[tm]",
				"/administration?page=addBrutalare"));
		request.setAttribute("subMenu", subMenu);
	}
	
}
