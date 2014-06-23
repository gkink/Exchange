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

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class guestItemSearch
 */
@WebServlet("/guestItemSearch")
public class guestItemSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private DataSource dataSource;
    private Connection connection;
    private Statement statement;
     
    public void init() throws ServletException {
        try {
            Context initContext  = new InitialContext();
            Context envContext = (Context)initContext.lookup("java:/comp/env");
            dataSource = (DataSource)envContext.lookup("jdbc/testdb");
            
            
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }	
	
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
		String itemKeyword = request.getParameter("item");
		System.out.println("Server recieved: " + itemKeyword);

		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		QueryExecutor  executor = new QueryExecutor(dataSource);
		DBqueryGenerator generator = new DBqueryGenerator();
		ItemContainer container = new ItemContainer(generator, executor);
		List<ItemsHaveObject> items = container.getSearchResultItems(itemKeyword);

		out.println("<main>");
		if(items.size() == 0)
			out.println("<message>Nothing found for word " + itemKeyword + "</message>");
		else
			out.println("<message>ok</message>");
		
		
		for(ItemsHaveObject item: items){
			out.println("<itemID>" + item.getItemId() + "</itemID>");
			out.println("<itemName>" + item.getItemName() + "</itemName>");			
		}
		out.println("</main>");
		
		
		System.out.println("Server finished job");
	}
}
