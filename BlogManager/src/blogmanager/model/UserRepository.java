/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: The UserRepository class is responsible to store all users in a MySQL Database.  
***********************************************************************************
*****************/
package blogmanager.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import blogmanager.services.*;

public class UserRepository implements IUserService
{
	private DataSource dataSource;
	private Connection conn = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public UserRepository(DataSource ds) throws SQLException
	{
		this.dataSource = ds;
	}

	@Override
	public User find(int id) throws Exception 
	{
		User user = null;
		
		try
		{
			//Create connection to database
			if(conn == null)
				conn = dataSource.getConnection();
			
			PreparedStatement st = conn.prepareStatement("Select * from users where id = ?");
			st.setInt(1, id);
			resultSet = st.executeQuery();
			
			//there is something from the result set, then create user object
		    if (resultSet.next() ) 
		    {
		        // set all the useful user information
		        user = new User(resultSet.getInt("id"),resultSet.getString("firstname"),resultSet.getString("lastname"),
						resultSet.getString("email"),resultSet.getString("phone"),
						resultSet.getString("year"),resultSet.getString("major"));
		    }
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
		//Return the user 
		return user;
	}

	@Override
	public User findUserByCredentials(String username, String password) throws Exception 
	{
		User user = null;
		try
		{
			//Create connection to database
			conn = dataSource.getConnection();
			
			PreparedStatement st = conn.prepareStatement("Select * from users where username = ? and password = ?");
			st.setString(1, username);
			st.setString(2, password);
			resultSet = st.executeQuery();
			
			//there is something from the result set, then create user object
		    if (resultSet.next() ) 
		    {
		        // set all the useful user information
		        user = new User(resultSet.getInt("id"),resultSet.getString("firstname"),resultSet.getString("lastname"),
						resultSet.getString("email"),resultSet.getString("phone"),
						resultSet.getString("year"),resultSet.getString("major"));
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
		//Return the user 
		return user;
	}

	@Override
	public List<User> list() throws Exception 
	{
		User user;
		List<User> users = new ArrayList<>();
		try
		{
			String query = "select * from users order by lastname";
			//Create connection to database
			
			conn = dataSource.getConnection();
			
			//Create s SQL statement
			statement = conn.createStatement();
			
			//Execute SQl query
			resultSet = statement.executeQuery(query);
			while(resultSet.next())
			{
				user = new User(resultSet.getInt("id"),
						resultSet.getString("firstname"),
						resultSet.getString("lastname"),
						resultSet.getString("email"),
						resultSet.getString("phone"),
						resultSet.getString("year"),
						resultSet.getString("major"));
				users.add(user);	
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
		
		return users;
	}

	@Override
	public void add(User user) throws Exception
	{
		PreparedStatement prepStatement = null;
		
		try
		{
			//Create connection to database
			conn = dataSource.getConnection();
			
			//Create s SQL statement
			prepStatement = conn.prepareStatement("INSERT INTO Users(firstname,lastname,email,phone,year,major,username,password) VALUES(?,?,?,?,?,?,?,?)");
			prepStatement.setString(1, user.getFirstName());
			prepStatement.setString(2, user.getLastName());
			prepStatement.setString(3, user.getEmail());
			prepStatement.setString(4, user.getPhone());
			prepStatement.setString(5, user.getYear());
			prepStatement.setString(6, user.getMajor());
			prepStatement.setString(7, user.getUsername());
			prepStatement.setString(8, user.getPassword());
			
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
	public void update(User entity) throws Exception 
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

	@Override
	public User findUserByEmail(String email) throws Exception 
	{
		User user = null;
		
		try
		{
			//Create connection to database
			if(conn == null)
				conn = dataSource.getConnection();
			
			PreparedStatement st = conn.prepareStatement("Select * from users where email = ?");
			st.setString(1, email);
			resultSet = st.executeQuery();
			
			//there is something from the result set, then create user object
		    if (resultSet.next() ) 
		    {
		        // set all the useful user information
		        user = new User(resultSet.getInt("id"),resultSet.getString("firstname"),resultSet.getString("lastname"),
						resultSet.getString("email"),resultSet.getString("phone"),
						resultSet.getString("year"),resultSet.getString("major"));
		    }
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		
		//Return the user 
		return user;
	}

	
}
