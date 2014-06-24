package itemPageHandler;

import guestSession.ItemsHaveObject;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class SaveChanges
 */
@WebServlet("/SaveChanges")
public class SaveChanges extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveChanges() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int itemId = (int) request.getSession().getAttribute("itemId");
		String newName = request.getParameter("itemName");
		String newDesc = request.getParameter("itemDesc");
		
		DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
		QueryExecutor executor =  new QueryExecutor(dataSource);
		DBqueryGenerator generator =  new DBqueryGenerator();
		
		ItemsHaveObject curr =  new ItemsHaveObject(generator, executor, itemId);
		curr.update("name", newName);
		curr.update("description", newDesc);
		System.out.println(request.getSession().getAttribute("User"));
		response.sendRedirect("NewHomepage.jsp");	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
