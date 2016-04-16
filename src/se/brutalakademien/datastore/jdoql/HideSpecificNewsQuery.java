package se.brutalakademien.datastore.jdoql;

import javax.jdo.PersistenceManager;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.model.News;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class HideSpecificNewsQuery
{
	
	public void hide(String id)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			Key key = KeyFactory.createKey(News.class.getSimpleName(), Long
					.parseLong(id));
			News news = pm.getObjectById(News.class, key);
			if (news != null)
			{
				news.setVisible(false);
			}
		}
		finally
		{
			pm.close();
		}
	}
}
