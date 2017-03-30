/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: The LogoutController will remove all session objects and redirect the user
* to the login page.
***********************************************************************************
*****************/
package blogmanager.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class LogoutController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
 
    public LogoutController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		//Removing the session objects and redirect the user to login page
		HttpSession session = request.getSession();
		session.invalidate();  
		response.sendRedirect("/BlogManager/login.jsp");
	}

}
