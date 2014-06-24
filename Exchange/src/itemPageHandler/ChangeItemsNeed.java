package itemPageHandler;

import guestSession.ItemsNeedObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class DeleteItemsNeed
 */
@WebServlet("/DeleteItemsNeed")
public class ChangeItemsNeed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeItemsNeed() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String newName = request.getParameter("name");
		int itemId =  (int) request.getSession().getAttribute("itemsNeedId");
		DataSource dataSource =  (DataSource) request.getServletContext().getAttribute("DataSource");
		QueryExecutor executor =  new QueryExecutor(dataSource);
		DBqueryGenerator generator =  new DBqueryGenerator();
		
		ItemsNeedObject item =  new ItemsNeedObject(generator, executor, itemId);
		
		if(newName == null)
			item.delete();
		else
			item.update("name", newName);
		
		response.sendRedirect("NewHomepage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
