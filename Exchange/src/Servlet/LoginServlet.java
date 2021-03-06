package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import sun.org.mozilla.javascript.internal.Context;
import ModelClasses.User;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import dbConnection.MyDBInfo;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		DBqueryGenerator g= new DBqueryGenerator();
		
		DataSource dataSource=(DataSource)request.getServletContext().getAttribute("DataSource");
	
		QueryExecutor e= new QueryExecutor(dataSource);
		String email= request.getParameter("login");
		String pass= request.getParameter("password");
		HttpSession ses= request.getSession();
		
		User u= new User(e, g, email);
				
		if(u.getId()==0|| !pass.equals(u.getPassword())){
			response.sendRedirect("InvalidLogin.html");
		}else {
			
			
			
			ses.setAttribute("User", u.getId());
			response.sendRedirect("NewHomepage.jsp");
			
		}
		
		
		
		
		}

}
