/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: The RegisterationController validates the data coming 
* from the registration form and redirects the response to welcome.jsp.  
***********************************************************************************
*****************/
package blogmanager.controllers;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import blogmanager.services.*;
import blogmanager.model.*;

@WebServlet(name = "RegisterServlet", urlPatterns = { "/RegisterServlet" })
public class RegisterationController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private IUserService service;
	private User user = null;
	RequestDispatcher dispacher;
	
	@Resource(name="jdbc/comp3095")
	private DataSource ds;

    public RegisterationController() 
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
		//redirect the user to the register page
		response.sendRedirect("/BlogManager/register.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Collect the parameters from the register form
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String confirmEmail = request.getParameter("confirm_email");
		String phone = request.getParameter("telephone");
		String major = request.getParameter("major");
		String year = request.getParameter("year");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		
		//create the user with all the parameters received
		user = new User(firstName, lastName, email, phone, year, major,userName, password);
		
		//Add the user object into request
		request.setAttribute("user", user);
		
		//Validate the form inputs
		if (isValidForm(user, confirmEmail,confirmPassword))
		{
			try 
			{
				//Check if the user is already registered by checking the email(the only unique value a user could have)
				if(service.findUserByEmail(email) != null)
				{
					request.setAttribute("user_exist", true);
					dispacher = request.getRequestDispatcher("/register.jsp");
					dispacher.forward(request, response);
				}
				else
				{
					//send the new user to the user service to store the data in the database
					service.add(user);
					dispacher = request.getRequestDispatcher("/success.jsp");
					dispacher.forward(request, response);
				}
				
			} 
			catch (Exception ex) 
			{
				throw new ServletException(ex);
			}
		}
		else
		{
			/*If any invalid data found, attach a boolean value to each attribute
			 *and send it back to register.jsp so that whichever value evaluates to true
			 *will be appear in the bottom of its input box */ 
			request.setAttribute("firstname", isEmpty(firstName) || !isAlpha(firstName)); 
			request.setAttribute("lastname",isEmpty(lastName) || !isAlpha(lastName));
			request.setAttribute("email", isEmpty(email) || isValidEmail(email));
			request.setAttribute("confirm_email", isEmpty(confirmEmail) || !confirmEmail.equals(email));
			request.setAttribute("confirm_email_value", confirmEmail);
			request.setAttribute("phone", isEmpty(phone) || !isValidPhoneNumber(phone));
			request.setAttribute("year", isEmpty(year));
			request.setAttribute("major", isEmpty(major));
			request.setAttribute("username", isEmpty(userName));
			request.setAttribute("password", isEmpty(password));
			request.setAttribute("confirm_password", isEmpty(confirmPassword) || !confirmPassword.equals(password));
			request.setAttribute("confirm_password_value", confirmPassword);
			request.setAttribute("error", true);
			RequestDispatcher dispacher = request.getRequestDispatcher("/register.jsp");
			dispacher.forward(request, response);
		}

	}

		//Form inputs validator
		private boolean isValidForm(User user, String confirmEmail,String confirmPassword) 
		{
			if (validateNames(user.getFirstName()) == true && validateNames(user.getLastName()) == true
					&& !isEmpty(user.getEmail()) == true && isMatch(user.getEmail(),confirmEmail) == true 
					&& isValidPhoneNumber(user.getPhone()) == true && !isEmpty(user.getMajor()) == true
					&& !isEmpty(user.getYear()) == true && isValidPhoneNumber(user.getPhone()) == true
					&& !isEmpty(user.getPassword()) == true && isMatch(user.getPassword(),confirmPassword) == true) 
				return true;
			else
				return false;
		}
		
		//Checks if name values contain only alpha
		private boolean validateNames(String name) 
		{
			if (name.isEmpty() || !name.matches("[a-zA-Z]+")) 
				return false;
			else
				return true;
		}

		// Checks if the string value is null or empty
		private boolean isEmpty(String value) 
		{
			return (value == null || value.isEmpty());
		}

		// Checks if the email value contains valid email pattern
		public static boolean isValidEmail(String email) 
		{
			return email.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
		}

		//Checks if name values contain only alpha
		public boolean isAlpha(String name) 
		{
			//Check is name value contains only alphabet
			return name.matches("[a-zA-Z]+");
		}

		//Checks if the phone value contains 10 digits
		private static boolean isValidPhoneNumber(String phoneNo) 
		{
			// validate phone number containing only 10 digits 
			return phoneNo.matches("\\d{10}");
		}
		
		//Checks if the two strings are equal
		private static boolean isMatch(String val1, String val2) 
		{
			return val1.equals(val2);
		}
}
