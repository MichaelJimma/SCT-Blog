/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: The WelcomeController creates list of Blog and Comment objects and
* forward them to welcome.jsp so that the list items can be traversed and displayed in the view.  
***********************************************************************************
*****************/
package blogmanager.controllers;

import java.io.IOException;
import java.util.List;
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
import blogmanager.services.IBlogService;
import blogmanager.services.ICommentService;

@WebServlet("/WelcomeController")
public class WelcomeController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private IBlogService blogService;
	private ICommentService commentService;
	@Resource(name="jdbc/comp3095")
	private DataSource ds;
	
    public WelcomeController() 
    {
        super();
    }

    @Override
	public void init() throws ServletException 
	{
		super.init();
		
		try
		{
			//Inject the dependencies
			blogService = new BlogRepository(ds);
			commentService = new CommentRepository(ds);
		}
		catch(Exception ex)
		{
			throw new ServletException(ex);
		}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		try 
		{
			//get all the list of blogs and comments
			List<Blog> blogs = blogService.list();
			List<Comment> comments = commentService.list();
			
			//send the list objects to welcome page
			request.setAttribute("comments", comments);
			request.setAttribute("blogs",blogs);
			RequestDispatcher dispacher = request.getRequestDispatcher("/welcome.jsp");
			dispacher.forward(request, response);
		} 
		catch (Exception e) 
		{
			response.getWriter().append(e.getMessage());
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
