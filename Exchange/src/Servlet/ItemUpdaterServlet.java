package Servlet;

import guestSession.ItemContainer;
import guestSession.ItemsHaveObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class ItemUpdaterServlet
 */
@WebServlet("/ItemUpdaterServlet")
public class ItemUpdaterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemUpdaterServlet() {
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
		 
           

             response.setContentType("text/xml");
             PrintWriter out = response.getWriter();
         	 HttpSession ses= request.getSession();
         	 Integer userId=(Integer)ses.getAttribute("User");
             DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
             QueryExecutor  executor = new QueryExecutor(dataSource);
             DBqueryGenerator generator = new DBqueryGenerator();
             ItemContainer container = new ItemContainer(generator, executor);
             List<ItemsHaveObject> items = container.getLatestItems(userId);

             out.println("<main>");
             
             for(ItemsHaveObject item: items){
                 out.println("<itemID>" + item.getItemId() + "</itemID>");
                 out.println("<itemName>" + item.getItemName() + "</itemName>");                        
         }
         out.println("</main>");
         
         
        
 
	}

}
