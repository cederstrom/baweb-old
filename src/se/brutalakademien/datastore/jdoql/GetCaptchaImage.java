package se.brutalakademien.datastore.jdoql;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.model.CaptchaImageBlobKey;

public class GetCaptchaImage
{
	
	@SuppressWarnings("unchecked")
	public List<CaptchaImageBlobKey> getCaptcha()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(CaptchaImageBlobKey.class);
		List<CaptchaImageBlobKey> allImages = (List<CaptchaImageBlobKey>) q
				.execute();
		
		return allImages;
	}
}