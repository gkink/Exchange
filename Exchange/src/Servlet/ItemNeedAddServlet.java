package Servlet;

import guestSession.ItemsNeedObject;

import java.io.IOException;

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
 * Servlet implementation class ItemNeedAddServlet
 */
@WebServlet("/ItemNeedAddServlet")
public class ItemNeedAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemNeedAddServlet() {
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
		String name=request.getParameter("ItemName");
		String key=request.getParameter("ItemKey");
		HttpSession ses= request.getSession();
		Integer userId=(Integer)ses.getAttribute("User");
		DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
		QueryExecutor e=new QueryExecutor(dataSource);
		DBqueryGenerator gen= new DBqueryGenerator();
		ItemsNeedObject item= new ItemsNeedObject(gen, e, userId, name, key);
		item.insert();
		response.sendRedirect("NewHomepage.jsp");
		
	}

}
