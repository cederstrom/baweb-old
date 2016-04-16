package se.brutalakademien.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Brutalare implements Serializable
{
	private static final long serialVersionUID = 7953408087799933706L;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private String email;
	
	@Persistent
	private int generation;
	
	@Persistent
	private String favoriteSport;
	
	@Persistent
	private String bestDekk;
	
	@Persistent
	private boolean admin = false;
	
	@Persistent
	private BlobKey blobKey;
	
	public Brutalare(String name, String email, int generation,
			String favoriteSport, String bestDekk, boolean admin,
			BlobKey blobKey)
	{
		this.name = name;
		this.email = email;
		this.generation = generation;
		this.favoriteSport = favoriteSport;
		this.bestDekk = bestDekk;
		this.admin = admin;
		this.blobKey = blobKey;
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
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public int getGeneration()
	{
		return generation;
	}
	
	public void setGeneration(int generation)
	{
		this.generation = generation;
	}
	
	public String getFavoriteSport()
	{
		return favoriteSport;
	}
	
	public void setFavoriteSport(String favoriteSport)
	{
		this.favoriteSport = favoriteSport;
	}
	
	public String getBestDekk()
	{
		return bestDekk;
	}
	
	public void setBestDekk(String bestDekk)
	{
		this.bestDekk = bestDekk;
	}
	
	public boolean isAdmin()
	{
		return admin;
	}
	
	public void setAdmin(boolean admin)
	{
		this.admin = admin;
	}
	
	public BlobKey getBlobKey()
	{
		return blobKey;
	}
	
	public void setBlobKey(BlobKey blobKey)
	{
		this.blobKey = blobKey;
	}
	
}
