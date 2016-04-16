package se.brutalakademien.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class News
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String title;
	
	@Persistent
	private String text;
	
	@Persistent
	private Blob image;
	
	@Persistent
	private String author;
	
	@Persistent
	private Date date;
	
	@Persistent
	private boolean visible = true;
	
	public News(String title, String text, Blob image, String author, Date date)
	{
		this.title = title;
		this.text = text;
		this.image = image;
		this.author = author;
		this.date = date;
	}
	
	public Key getKey()
	{
		return key;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public Blob getImage()
	{
		return image;
	}
	
	public void setImage(Blob image)
	{
		this.image = image;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
}
