/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: The BlogController redirects the user from welcome page to create new post page.
* It receives a blog content from newpost.jsp, creates new blog object and send it BlogRepository
* to be inserted in the database. The user object is accessed from the current session.
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
import blogmanager.model.Blog;
import blogmanager.model.BlogRepository;
import blogmanager.model.User;
import blogmanager.services.IBlogService;

/**
 * Servlet implementation class NewPostController
 */
@WebServlet("/NewPostController")
public class NewPostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IBlogService blogService;
	RequestDispatcher dispatcher;
	
	@Resource(name="jdbc/comp3095")
	private DataSource ds;
    
	
    public NewPostController() {
        super();
    }
    
    @Override
   	public void init() throws ServletException 
   	{
   		super.init();
   		
   		try
   		{
   			//Injecting the dependency
   			blogService = new BlogRepository(ds);
   		}
   		catch(Exception ex)
   		{
   			throw new ServletException(ex);
   		}
   	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//redirect the user to the newpost page 
		response.sendRedirect("/BlogManager/newpost.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//get the post string value from the request parameter
		String post = request.getParameter("post");
		//get the user from the current session
		User currentUser = (User) request.getSession().getAttribute("user");
		
		//validate string
		if(isEmpty(post))
		{
			//add the invalid data to the request attribute and send it back to newpost page 
			request.setAttribute("invalid",true);
			dispatcher = request.getRequestDispatcher("/newpost.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			//create a new blog object by passing the current user and the post content
			Blog blog = new Blog(currentUser,post);
			try 
			{
				//send the new blog post to the blog service to store the data in the database
				blogService.add(blog);
				dispatcher = request.getRequestDispatcher("WelcomeController");
				dispatcher.forward(request, response);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	private boolean isEmpty(String value) 
	{
		//check if the value is empty or contains no string 
		return (value == null || value.isEmpty() || value.replace(" ", "").length() <= 0);
	}
}
