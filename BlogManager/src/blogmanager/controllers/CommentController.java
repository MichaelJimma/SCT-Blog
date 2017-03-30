/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: The CommentController receives a request from welcome.jsp which includes
* the id of the blog object where the comment will be added to. The user for the comment object 
* is retrieved from the current session. It is also responsible 
* to validate the value posted from comment.jsp, create a Comment object and pass it to 
* CommentRepository to be inserted in the data base.
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
import blogmanager.model.Comment;
import blogmanager.model.CommentRepository;
import blogmanager.model.User;
import blogmanager.services.IBlogService;
import blogmanager.services.ICommentService;

@WebServlet("/CommentController")
public class CommentController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private ICommentService commentService;
	private IBlogService blogService;
	RequestDispatcher dispatcher;
	Blog blog = null;
	
	@Resource(name="jdbc/comp3095")
	private DataSource ds;
	
    public CommentController() 
    {
        super();
    }

    @Override
   	public void init() throws ServletException 
   	{
   		super.init();
   		try
   		{
   			//Injecting the dependencies during initial
   			commentService = new CommentRepository(ds);
   			blogService = new BlogRepository(ds);
   		}
   		catch(Exception ex)
   		{
   			throw new ServletException(ex);
   		}
   	} 
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//get the id of the post from the query string
		String id = request.getParameter("id");
		if(!isEmpty(id))
		{
			try 
			{
				//get the blog object from the service provider
				this.blog = blogService.find(Integer.parseInt(id));
				//send the blog object to comment.jsp to view the post detail  
				request.setAttribute("blog",blog); 
				RequestDispatcher dispacher = request.getRequestDispatcher("/comment.jsp");
				dispacher.forward(request, response);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			response.sendRedirect("/BlogManager/comment.jsp");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//get the comment from the request parameter
		String comment = request.getParameter("comment");
		//get the user from the current session
		User currentUser = (User) request.getSession().getAttribute("user");
		
		//validate string
		if(isEmpty(comment))
		{
			request.setAttribute("blog",blog); 
			request.setAttribute("invalidComment",true);
			dispatcher = request.getRequestDispatcher("/comment.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			//create a new comment object by passing the current user, the blog and the comment content
			Comment comm = new Comment(currentUser,blog,comment);
			try 
			{
				//send the new comment to the comment service to store the data in the database
				commentService.add(comm);
				//redirect the user to the welcome page
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
