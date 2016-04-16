package se.brutalakademien.datastore.jdoql;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.model.Brutalare;

@SuppressWarnings("unchecked")
public class GetBrutalareQuery
{
	public GetBrutalareQuery()
	{
	}
	
	public List<Brutalare> getAll()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(Brutalare.class);
		q.setOrdering("generation");
		
		List<Brutalare> baList = (List<Brutalare>) q.execute();
		
		return baList;
	}
	
	public Brutalare getByEmail(String userEmail)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(Brutalare.class);
		q.setFilter("email == param_email");
		q.declareParameters("String param_email");
		
		List<Brutalare> baUserList = (List<Brutalare>) q.execute(userEmail);
		
		if (baUserList.size() > 0)
			return baUserList.get(0);
		else
			return null;
	}
}
