package Servlets;

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
import javax.sql.DataSource;

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
		DataSource data= new DataSource() {
			
			@Override
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setLoginTimeout(int seconds) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setLogWriter(PrintWriter out) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Logger getParentLogger() throws SQLFeatureNotSupportedException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getLoginTimeout() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public PrintWriter getLogWriter() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public java.sql.Connection getConnection(String username, String password)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public java.sql.Connection getConnection() throws SQLException {
				Connection con=null;
				try { 
					Class.forName("com.mysql.jdbc.Driver"); 
					con = (Connection) DriverManager.getConnection 
							( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD); 
					Statement stmt = (Statement) con.createStatement(); 
					stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME); 
					}catch (ClassNotFoundException e) { 
						e.printStackTrace(); 
				}
				return con;
			}
		};;
		QueryExecutor e= new QueryExecutor(data);
		String email= request.getParameter("login");
		String pass= request.getParameter("password");
		User u= new User(e, g, email);
		if(u.getId()==0|| !pass.equals(u.getPassword())){
			RequestDispatcher r= request.getRequestDispatcher("InvalidLogin.html");
			r.forward(request, response);
		}else {
			RequestDispatcher r= request.getRequestDispatcher("Homepage.jsp");
			request.setAttribute("User", u);
			r.forward(request, response);
			
		}
		
		
		
		
		}

}
