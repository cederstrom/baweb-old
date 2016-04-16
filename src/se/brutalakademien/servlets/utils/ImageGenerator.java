package se.brutalakademien.servlets.utils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.brutalakademien.datastore.jdoql.GetCaptchaImage;
import se.brutalakademien.model.CaptchaImageBlobKey;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class ImageGenerator extends HttpServlet
{
	private static final long serialVersionUID = -6441400071610694390L;
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		String type = request.getParameter("type");
		
		if (type != null)
		{
			if (type.equalsIgnoreCase("captcha"))
			{
				CaptchaImageBlobKey captcha = retrieveImage();
				
				request.getSession().setAttribute("captchaValue",
						captcha.getValue());
				blobstoreService.serve(captcha.getBlobKey(), response);
			}
		}
	}
	
	private CaptchaImageBlobKey retrieveImage()
	{
		GetCaptchaImage query = new GetCaptchaImage();
		List<CaptchaImageBlobKey> listCaptcha = query.getCaptcha();
		
		Random generator = new Random();
		int r = generator.nextInt(listCaptcha.size());
		
		return listCaptcha.get(r);
	}
	
}