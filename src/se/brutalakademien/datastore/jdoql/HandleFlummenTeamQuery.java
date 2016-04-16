package se.brutalakademien.datastore.jdoql;

import java.util.List;

import javax.jdo.PersistenceManager;

import se.brutalakademien.datastore.PMF;
import se.brutalakademien.model.FlummenTeam;
import se.brutalakademien.model.TeamMember;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class HandleFlummenTeamQuery
{
	public void addMember(String teamId, TeamMember newMember)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			Key key = KeyFactory.createKey(FlummenTeam.class.getSimpleName(),
					Long.parseLong(teamId));
			FlummenTeam team = pm.getObjectById(FlummenTeam.class, key);
			if (team != null)
			{
				List<TeamMember> members = team.getMembers();
				members.add(newMember);
				team.setMembers(members);
			}
		}
		finally
		{
			pm.close();
		}
	}
	
	public void updateSfs(String teamId, String memberPnumber, boolean sfs)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			Key key = KeyFactory.createKey(FlummenTeam.class.getSimpleName(),
					Long.parseLong(teamId));
			FlummenTeam team = pm.getObjectById(FlummenTeam.class, key);
			if (team != null)
			{
				List<TeamMember> members = team.getMembers();
				for (TeamMember member : members)
				{
					if (member.getpNumber().equalsIgnoreCase(memberPnumber))
					{
						member.setSfs(sfs);
						break;
					}
				}
			}
		}
		finally
		{
			pm.close();
		}
	}
	
	public void setHasPayed(String teamId, boolean hasPayed)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			Key key = KeyFactory.createKey(FlummenTeam.class.getSimpleName(),
					Long.parseLong(teamId));
			FlummenTeam team = pm.getObjectById(FlummenTeam.class, key);
			if (team != null)
			{
				team.setHasPayed(hasPayed);
			}
		}
		finally
		{
			pm.close();
		}
	}
}
