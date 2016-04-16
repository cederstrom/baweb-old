package se.brutalakademien.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class CaptchaImageBlobKey
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String value;
	
	@Persistent
	private BlobKey blobKey;
	
	public CaptchaImageBlobKey(String value, BlobKey blobKey)
	{
		this.value = value;
		this.blobKey = blobKey;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	public BlobKey getBlobKey()
	{
		return this.blobKey;
	}
	
}