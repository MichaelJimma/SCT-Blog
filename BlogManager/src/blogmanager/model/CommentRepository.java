/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: The CommentRepository class is responsible to store all comments in a MySQL Database.  
***********************************************************************************
*****************/
package blogmanager.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import blogmanager.services.IBlogService;
import blogmanager.services.ICommentService;
import blogmanager.services.IUserService;

public class CommentRepository implements ICommentService
{
	private DataSource dataSource;
	private Connection conn = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private IUserService userService = null;
	private IBlogService blogService = null;
	
	public CommentRepository(DataSource ds) throws SQLException
	{
		this.dataSource = ds;
		userService = new UserRepository(ds);
		blogService = new BlogRepository(ds);
	}
	
	@Override
	public Comment find(int id) throws Exception 
	{
		Comment comment = null;
		try
		{
			//Create connection to database
			if(conn == null)
				conn = dataSource.getConnection();
			
			PreparedStatement st = conn.prepareStatement("Select * from comments where id = ?");
			st.setInt(1, id);
			resultSet = st.executeQuery();
			
			//there is something from the result set, then create user object
		    if (resultSet.next() ) 
		    {
		        // set all the useful user information
		        comment = new Comment(resultSet.getInt("id"),userService.find(resultSet.getInt("user_id")),blogService.find(resultSet.getInt("blog_id")),
		        		resultSet.getString("content"),resultSet.getDate("created_on"));
		    }
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
		//Return the comment 
		return comment;
	}
	@Override
	public List<Comment> list() throws Exception 
	{
		Comment comment;
		List<Comment> comments = new ArrayList<>();
		try
		{
			String query = "select * from comments order by id DESC";
			//Create connection to database
			
			conn = dataSource.getConnection();
			
			//Create s SQL statement
			statement = conn.createStatement();
			
			//Execute SQl query
			resultSet = statement.executeQuery(query);
			while(resultSet.next())
			{
				comment = new Comment(resultSet.getInt("id"),
						userService.find(resultSet.getInt("user_id")),
						blogService.find(resultSet.getInt("blog_id")),
						resultSet.getString("content"),
						resultSet.getDate("created_on"));
				comments.add(comment);	
			}
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		finally
		{
			close();
		}
		
		return comments;
	}
	
	@Override
	public List<Comment> listCommentsByBlog(int blogId) throws Exception
	{
		Comment comment;
		List<Comment> comments = new ArrayList<>();
		try
		{
			PreparedStatement st = conn.prepareStatement("Select * from comments where blog_id = ? order by id desc");
			st.setInt(1, blogId);
			//Create connection to database
			
			conn = dataSource.getConnection();
			
			//Create s SQL statement
			statement = conn.createStatement();
			
			//Execute SQl query
			resultSet = st.executeQuery();
			while(resultSet.next())
			{
				 comment = new Comment(resultSet.getInt("id"),userService.find(resultSet.getInt("user_id")),blogService.find(resultSet.getInt("blog_id")),
			        		resultSet.getString("content"),resultSet.getDate("created_on"));
				comments.add(comment);	
			}
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		finally
		{
			close();
		}
		
		return comments;
	}
	
	@Override
	public void add(Comment comment) throws Exception 
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
			prepStatement = conn.prepareStatement("INSERT INTO comments(user_id,blog_id,content,created_on) VALUES(?,?,?,?)");
			prepStatement.setInt(1, comment.getUser().getId());
			prepStatement.setInt(2, comment.getBlog().getId());
			prepStatement.setString(3, comment.getContent());
			prepStatement.setDate(4, createdOn);
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
	public void update(Comment entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void close() throws Exception 
	{
		if(conn != null)
			conn.close();
		
		if(statement != null)
			statement.close();
		
		if(resultSet != null)
			resultSet.close();
		
	}
}
