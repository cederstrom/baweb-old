package se.brutalakademien.datastore.jdoql;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.model.GuestbookPost;


public class GetGuestbookPosts
{
	
	@SuppressWarnings("unchecked")
	public List<GuestbookPost>getAllPosts()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<GuestbookPost> allPosts=null;

		Query query = pm.newQuery(GuestbookPost.class);
		query.setOrdering("date desc");
		allPosts =(List<GuestbookPost>) query.execute();
		
		return allPosts;
	}
	
	public List<GuestbookPost>changeposts(GuestbookPost post)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<GuestbookPost> allPosts=null;
		
		Query query = pm.newQuery(GuestbookPost.class);
		
		
		return null;
	}
}