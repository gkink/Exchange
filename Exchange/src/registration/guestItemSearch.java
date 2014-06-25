package registration;

import guestSession.ItemContainer;
import guestSession.ItemsHaveObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.mockito.asm.tree.IntInsnNode;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class guestItemSearch
 */
@WebServlet("/guestItemSearch")
public class guestItemSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
     	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public guestItemSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource dataSource = (DataSource) request.getServletContext().getAttribute("DataSource");
		
		String itemKeyword = request.getParameter("item");
		System.out.println("Server recieved: " + itemKeyword);

		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		QueryExecutor  executor = new QueryExecutor(dataSource);
		DBqueryGenerator generator = new DBqueryGenerator();
		ItemContainer container = new ItemContainer(generator, executor);
		List<ItemsHaveObject> items = container.getSearchResultItems(itemKeyword);

		if(items.size() == 0)
			out.println("<p>Nothing found for word " + itemKeyword + "</p>");
		else{
			for(ItemsHaveObject item: items){
				String href = "http://localhost:8080/Exchange/ItemsHave.jsp?itemId=" + item.getItemId() + "&guest=yes";
				out.println("<li><a href=" + toHref(href) + ">" + item.getItemName() + "</a></li>");
			}
		}
		
		System.out.println("Server finished job");
	}
	
	private String toHref(String initialValue){
		return "\"" + initialValue + "\"";
	}

}
