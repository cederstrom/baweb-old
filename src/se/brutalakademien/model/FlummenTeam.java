package se.brutalakademien.model;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import se.brutalakademien.servlets.utils.Utils;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class FlummenTeam
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String teamName;
	
	@Persistent
	private String teamNameLower;
	
	@Persistent
	List<TeamMember> members;
	
	@Persistent
	private String slogan;
	
	@Persistent
	private Date date;
	
	@Persistent
	private String email;
	
	@Persistent
	private int nrOfMembers;
	
	@Persistent
	private int price;
	
	@Persistent
	private boolean hasPayed;
	
	@Persistent
	private String city;
	
	@Persistent
	private String cityLower;
	
	public FlummenTeam()
	{
	}
	
	public Key getKey()
	{
		return key;
	}
	
	public String getTeamName()
	{
		return teamName;
	}
	
	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
		this.teamNameLower = teamName.toLowerCase();
	}
	
	public List<TeamMember> getMembers()
	{
		return members;
	}
	
	public void setMembers(final List<TeamMember> members)
	{
		this.members = members;
		this.nrOfMembers = members.size();
		
		this.price = 0;
		for (TeamMember tm : this.members)
		{
			this.price += Utils.getFlummenPrice(tm.needBed);
		}
	}
	
	public String getSlogan()
	{
		return slogan;
	}
	
	public void setSlogan(String slogan)
	{
		this.slogan = slogan;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public boolean isHasPayed()
	{
		return hasPayed;
	}
	
	public void setHasPayed(boolean hasPayed)
	{
		this.hasPayed = hasPayed;
	}
	
	public int getNrOfMembers()
	{
		return nrOfMembers;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
		this.cityLower = city.toLowerCase();
	}
	
}
