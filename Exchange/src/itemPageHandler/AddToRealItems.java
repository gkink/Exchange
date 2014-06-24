package itemPageHandler;

import guestSession.ItemsHaveObject;
import guestSession.RealItemsObject;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class AddToRealItems
 */
@WebServlet("/AddToRealItems")
public class AddToRealItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToRealItems() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int itemId = (int) request.getSession().getAttribute("itemId");
		int userId = (int) request.getSession().getAttribute("User");
		BlockingQueue<Integer> queue = (BlockingQueue<Integer>) request.getServletContext().getAttribute("usersQueue");

		DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
		QueryExecutor executor =  new QueryExecutor(dataSource);
		DBqueryGenerator generator =  new DBqueryGenerator();

		RealItemsObject realItemsObject =  new RealItemsObject(generator, executor, userId, itemId);
		realItemsObject.insert();
		try {
			queue.put(userId);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		System.out.println(request.getSession().getAttribute("User"));
		response.sendRedirect("NewHomepage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
