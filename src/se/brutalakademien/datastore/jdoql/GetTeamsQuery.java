package se.brutalakademien.datastore.jdoql;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.model.FlummenTeam;

public class GetTeamsQuery
{
	private String orderBy;
	
	public GetTeamsQuery(String orderBy)
	{
		if (orderBy.equalsIgnoreCase("teamName")
				|| orderBy.equalsIgnoreCase("date")
				|| orderBy.equalsIgnoreCase("city")
				|| orderBy.equalsIgnoreCase("hasPayed"))
			this.orderBy = orderBy;
		else
			this.orderBy = "teamName";
		
		if (orderBy.equalsIgnoreCase("teamName")
				|| orderBy.equalsIgnoreCase("city"))
			this.orderBy += "Lower";
	}
	
	@SuppressWarnings("unchecked")
	public List<FlummenTeam> get()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(FlummenTeam.class);
		if (this.orderBy.equals("hasPayed"))
			q.setFilter("hasPayed==false");
		
		q.setOrdering(this.orderBy);
		
		return (List<FlummenTeam>) q.execute();
	}
}
