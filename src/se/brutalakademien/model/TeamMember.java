package se.brutalakademien.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class TeamMember
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	String name;
	
	@Persistent
	String pNumber;
	
	@Persistent
	String allergies;
	
	@Persistent
	boolean needBed;
	
	@Persistent
	boolean sfs;
	
	@Persistent
	boolean sittning;
	
	public TeamMember(String name, String pNumber, String allergies,
			boolean needBed, boolean sfs, boolean sittning)
	{
		this.name = name;
		this.pNumber = pNumber;
		this.allergies = allergies;
		this.needBed = needBed;
		this.sfs = sfs;
		this.sittning = sittning;
	}
	
	public Key getKey()
	{
		return key;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getpNumber()
	{
		return pNumber;
	}
	
	public void setpNumber(String pNumber)
	{
		this.pNumber = pNumber;
	}
	
	public String getAllergies()
	{
		return allergies;
	}
	
	public void setAllergies(String allergies)
	{
		this.allergies = allergies;
	}
	
	public boolean isNeedBed()
	{
		return needBed;
	}
	
	public void setNeedBed(boolean needBed)
	{
		this.needBed = needBed;
	}
	
	public boolean isSfs()
	{
		return sfs;
	}
	
	public void setSfs(boolean sfs)
	{
		this.sfs = sfs;
	}
	
	public boolean isSittning()
	{
		return sittning;
	}
	
	public void setSittning(boolean sittning)
	{
		this.sittning = sittning;
	}
	
}