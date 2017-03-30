/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: The LoginController receives the user's login credentials and validate the values.
* After validation, the controller will send the values to UserService object for authentication.
* Once authenticated, the controller will create a session object and put the user object in the session
* to make the logged in user accessible throughout the application session.
***********************************************************************************
*****************/
package blogmanager.controllers;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import blogmanager.model.*;
import blogmanager.services.*;
@WebServlet("/Login")
public class LoginController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private IUserService service;
	private User user = null;
	private RequestDispatcher dispatcher;
	
	@Resource(name="jdbc/comp3095")
	private DataSource ds;

    public LoginController() 
    {
        super();
    }
    
    @Override
	public void init() throws ServletException 
	{
		super.init();
		
		try
		{
			//Injecting the dependency
			service = new UserRepository(ds);
		}
		catch(Exception ex)
		{
			throw new ServletException(ex);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendRedirect("/BlogManager/login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    //get the username and password from the query string  
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		
		try 
		{
			//validate the input 
			if(isEmpty(userName) || isEmpty(password))
				userNotFound(userName,request,response);
			
			//call the user service and get the user object
			user = service.findUserByCredentials(userName,password);
			
			//Check if the returned user object is null
			if(user != null)
			{
				//Check if the user select to remember his/her user name and password 
				if(request.getParameter("remember_me") != null)
				{
					//Create the cookies for username and password 
					Cookie usernameCookie = new Cookie("username",userName);
					Cookie passwordCookie = new Cookie("password",password);
					
					// Set expiry date after 24 Hrs for both the cookies.
					usernameCookie.setMaxAge(60*60*24); 
					passwordCookie.setMaxAge(60*60*24); 
					
					// Add both the cookies in the response header.
					response.addCookie( usernameCookie );
					response.addCookie( passwordCookie );
				}
				
				//Obtain the session object, create a new session if doesn't exist
				HttpSession session = request.getSession(true);
				//add the user in the session to be accessed in the entire application for the current session
				session.setAttribute("user",user);
				
				dispatcher = request.getRequestDispatcher("WelcomeController");
				dispatcher.forward(request, response);
			}
			else
			{
				//Redirects the user to login page with all the necessary error values 
				userNotFound(userName,request,response);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	void userNotFound(String userName,HttpServletRequest request, HttpServletResponse response)
	{
		/*Attach a boolean attribute and set it to true to indicates the user is 
		 * not authenticated for the error message to be displayed in login page
		 */
		request.setAttribute("userNotFound",true);
		request.setAttribute("username",userName);
		dispatcher = request.getRequestDispatcher("/login.jsp");
		try 
		{
			dispatcher.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
	}
	private boolean isEmpty(String value) 
	{
		return (value == null || value.isEmpty());
	}
}
