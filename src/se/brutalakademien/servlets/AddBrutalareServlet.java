package se.brutalakademien.servlets;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.model.Brutalare;

import com.google.appengine.api.blobstore.BlobKey;

public class AddBrutalareServlet extends AdministrationServlet
{
	private static final long serialVersionUID = 1438293425320499585L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String favoriteSport = request.getParameter("favoriteSport");
		String bestDekk = request.getParameter("bestDekk");
		
		String adminStr = request.getParameter("giveAdmin");
		boolean admin = (adminStr != null && adminStr.equalsIgnoreCase("on"));
		
		int generation = 0;
		try
		{
			generation = Integer.parseInt(request.getParameter("generation"));
		}
		catch (Exception e)
		{
		}
		
		if (email != null && email.length() > 5)
		{
			BlobKey k = new BlobKey("naaanda");
			
			Brutalare newBa = new Brutalare(name, email.toLowerCase(),
					generation, favoriteSport, bestDekk, admin, k);
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try
			{
				pm.makePersistent(newBa);
			}
			finally
			{
				pm.close();
			}
		}
		
		response.sendRedirect("/administration");
	}
	
}
