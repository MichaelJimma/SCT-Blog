/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: A Blog Bean (Entity Class) holds the data for a single instance of the Blog object.  
***********************************************************************************
*****************/
package blogmanager.model;

import java.sql.Date;

public class Blog 
{
	private int id;
	private User user;
	private String content;
	private Date createdOn;
	
	
	public Blog(int id, User user, String content, Date createdOn) 
	{
		super();
		this.id = id;
		this.user = user;
		this.content = content;
		this.createdOn = createdOn;
	}
	public Blog(User user, String content) 
	{
		super();
		this.user = user;
		this.content = content;
	}
	public int getId() 
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public User getUser()
	{
		return user;
	}
	public void setUser(User user) 
	{
		this.user = user;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	public Date getCreatedOn()
	{
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) 
	{
		this.createdOn = createdOn;
	}
}
