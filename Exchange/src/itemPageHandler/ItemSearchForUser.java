package itemPageHandler;

import guestSession.ItemContainer;
import guestSession.ItemsHaveObject;
import guestSession.ItemsNeedObject;
import guestSession.RealItemsObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class RealItemSearch
 */
@WebServlet("/ItemSearchForUser")
public class ItemSearchForUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemSearchForUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource dataSource = (DataSource) request.getServletContext().getAttribute("DataSource");

		int userid =  (int) request.getSession().getAttribute("User");

		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		QueryExecutor  executor = new QueryExecutor(dataSource);
		DBqueryGenerator generator = new DBqueryGenerator();
		ItemContainer container = new ItemContainer(generator, executor);
		List<ItemsHaveObject> items;
		List<ItemsNeedObject> needs = container.getUserItemsNeed(userid);
		
		if(needs.size() == 0)
			out.println("<p>Nothing to search yet</p>");
		else{
			for(ItemsNeedObject it: needs){
				items = container.getSearchResultItems(it.getItemName());
				for(ItemsHaveObject item: items){
					String href = "http://localhost:8080/Exchange/item.jsp?id=" + item.getItemId();
					out.println("<li><a href=" + toHref(href) + ">" + item.getItemName() + "</a></li>");
				}
			}
		}
				
		System.out.println("Server finished job");
	}
	
	private String toHref(String initialValue){
		return "\"" + initialValue + "\"";
	}


}
