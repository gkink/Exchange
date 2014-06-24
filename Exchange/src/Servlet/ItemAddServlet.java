package Servlet;

import guestSession.DateTime;
import guestSession.ItemsHaveObject;
import guestSession.ItemsNeedObject;

import java.io.IOException;




import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.sql.Date;
import java.sql.Time;




import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * Servlet implementation class ItemAddServlet
 */
@WebServlet("/ItemAddServlet")
public class ItemAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemAddServlet() {
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
		String desc=request.getParameter("ItemsDesc");
		String key=request.getParameter("ItemKey");
		System.out.print(desc);
		HttpSession ses= request.getSession();
		Integer userId=(Integer)ses.getAttribute("User");
		DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
		QueryExecutor e=new QueryExecutor(dataSource);
		DBqueryGenerator gen= new DBqueryGenerator();
		Date date=new Date(1);
		Time time=new Time(1);
		DateTime dateTime=new DateTime(date, time);
		ItemsHaveObject item=new ItemsHaveObject(gen, e, name, desc, key, userId, dateTime);
		item.insert();
	//	RequestDispatcher r=request.getRequestDispatcher("NewHomepage.jsp");
		response.sendRedirect("NewHomepage.jsp");
	//	r.forward(request, response);
	}

}
