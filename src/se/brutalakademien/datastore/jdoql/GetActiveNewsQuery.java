package se.brutalakademien.datastore.jdoql;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.model.News;

public class GetActiveNewsQuery
{
	public GetActiveNewsQuery()
	{
	}
	
	@SuppressWarnings("unchecked")
	public List<News> get()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(News.class);
		q.setFilter("visible == true");
		q.setOrdering("date desc");
		q.setRange(0, 10);
		
		List<News> allNews = (List<News>) q.execute();
		
		return allNews;
	}
	
}
