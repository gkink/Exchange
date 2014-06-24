package Servlet;

import guestSession.ItemContainer;
import guestSession.ItemsNeedObject;

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

import ModelClasses.Cycle;
import ModelClasses.CycleContainer;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class CycleUpdater
 */
@WebServlet("/CycleUpdater")
public class CycleUpdater extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CycleUpdater() {
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
        CycleContainer container = new CycleContainer(executor,generator, userId);
        out.println("<main>");
        
        for(int i=0;i<container.contSize();i++){
        	Cycle cycle=container.getCycle(i);
        	
            out.println("<itemID>" + cycle.getID() + "</itemID>");
            out.println("<itemName>" + "cycle "+(int)(i+1) + "</itemName>");                        
        }
        out.println("</main>");
    
    
        System.out.println("Server finished job for cycles");
	}
	

}
