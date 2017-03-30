/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: The BlogRepository class is responsible to store all blogs in a MySQL Database.  
***********************************************************************************
*****************/
package blogmanager.model;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import blogmanager.services.IBlogService;
import blogmanager.services.IUserService;

public class BlogRepository implements IBlogService 
{
	private DataSource dataSource;
	private Connection conn = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private IUserService userService = null;
	
	public BlogRepository(DataSource ds) throws SQLException
	{
		this.dataSource = ds;
		userService = new UserRepository(ds);
	}

	@Override
	public Blog find(int id) throws Exception 
	{
		Blog blog = null;
		try
		{
			//Create connection to database
			if(conn == null)
				conn = dataSource.getConnection();
			
			PreparedStatement st = conn.prepareStatement("Select * from blogs where id = ?");
			st.setInt(1, id);
			resultSet = st.executeQuery();
			
			//there is something from the result set, then create user object
		    if (resultSet.next() ) 
		    {
		        // set all the useful user information
		        blog = new Blog(resultSet.getInt("id"),userService.find(resultSet.getInt("user_id")),
		        		resultSet.getString("content"),resultSet.getDate("created_on"));
		    }
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
		//Return the blog 
		return blog;
	}

	@Override
	public List<Blog> list() throws Exception 
	{
		Blog blog;
		List<Blog> blogs = new ArrayList<>();
		try
		{
			String query = "select * from blogs order by id DESC";
			//Create connection to database
			
			if(conn == null)
				conn = dataSource.getConnection();
			
			//Create s SQL statement
			statement = conn.createStatement();
			
			//Execute SQl query
			resultSet = statement.executeQuery(query);
			while(resultSet.next())
			{
				blog = new Blog(resultSet.getInt("id"),
						userService.find(resultSet.getInt("user_id")),
						resultSet.getString("content"),
						resultSet.getDate("created_on"));
				blogs.add(blog);	
			}
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		return blogs;
	}

	@Override
	public void add(Blog blog) throws Exception
	{
		PreparedStatement prepStatement = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date; 
		date = (Date)dateFormat.parse(dateFormat.format(new Date()));
		@SuppressWarnings("deprecation")
		java.sql.Date createdOn = new java.sql.Date(date.getDate());
		try
		{
			//Create connection to database
			conn = dataSource.getConnection();
			
			//Create s SQL statement
			prepStatement = conn.prepareStatement("INSERT INTO blogs(user_id,content,created_on) VALUES(?,?,?)");
			prepStatement.setInt(1, blog.getUser().getId());
			prepStatement.setString(2, blog.getContent());
			prepStatement.setDate(3, createdOn);
			
			//Execute statement
			prepStatement.execute();
			
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		finally
		{
			close();
		}
	}

	@Override
	public void update(Blog entity) throws Exception 
	{
		// TODO Auto-generated method stub
		
	}

	public void close() throws SQLException 
	{
		if(conn != null)
			conn.close();
		
		if(statement != null)
			statement.close();
		
		if(resultSet != null)
			resultSet.close();
		
	}
}
