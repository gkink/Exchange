package Servlet;

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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class RealItemUpdater
 */
@WebServlet("/RealItemUpdater")
public class RealItemUpdater extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RealItemUpdater() {
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
		response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        HttpSession ses= request.getSession();
    	Integer userId=(Integer)ses.getAttribute("User");
        DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
        QueryExecutor  executor = new QueryExecutor(dataSource);
        DBqueryGenerator generator = new DBqueryGenerator();
        ItemContainer container = new ItemContainer(generator, executor);
        List<RealItemsObject> items = container.getUserItemsReal(userId);

        out.println("<main>");
        
        for(RealItemsObject item: items){
        	System.out.println(item.getItemId());
        	ItemsHaveObject exist= new ItemsHaveObject(generator, executor, item.getItemId());
        	System.out.println(exist.getItemName());
        	out.println("<itemID>" + exist.getItemId() + "</itemID>");
            out.println("<itemName>" + exist.getItemName() + "</itemName>");                        
    }
    out.println("</main>");
    
    
    System.out.println("Server finished job2");
	}
	

}
