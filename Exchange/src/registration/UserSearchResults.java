package registration;

import guestSession.ItemsHaveObject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import ModelClasses.User;
import ModelClasses.UserSearcContainer;

/**
 * Servlet implementation class UserSearchResults
 */
@WebServlet("/UserSearchResults")
public class UserSearchResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchResults() {
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
		
		String username = request.getParameter("username");
		response.setContentType("text/xml");
		PrintWriter out =  response.getWriter();
		
		QueryExecutor ex =  new QueryExecutor(dataSource);
		DBqueryGenerator gen =  new DBqueryGenerator();
		
		UserSearcContainer cont = new UserSearcContainer(ex, gen, username);
		
		if(cont.size() == 0)
			out.println("<p>No user found found for " + username + "</p>");
		else{
			for(int i = 0 ; i < cont.size() ; i++){
				String href = "http://localhost:8080/Exchange/User?id=" + cont.getUser(i).getId();
				out.println("<li><a href=" + toHref(href) + ">" + cont.getUser(i).getFirstName()
						+ " " + cont.getUser(i).getLastName() + "</a></li>");
			}
		}
	}
	
	private String toHref(String initialValue){
		return "\"" + initialValue + "\"";
	}

}
