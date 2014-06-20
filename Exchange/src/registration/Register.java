package registration;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import ModelClasses.User;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public DataSource datasource;

    /**
     * Default constructor. 
     */
    public Register() {
    	super();
    }
    
    public void init() throws ServletException {
        try {
            Context initContext  = new InitialContext();
            Context envContext = (Context)initContext.lookup("java:/comp/env");
            datasource = (DataSource)envContext.lookup("jdbc/testdb");
            
        } catch (NamingException e) {
            e.printStackTrace();
        }
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
		String firstName, lastName, email, password;
		firstName = request.getParameter("firstName");
		lastName = request.getParameter("lastName");
		email = request.getParameter("email");
		password = request.getParameter("password");
				
		QueryExecutor ex = new QueryExecutor(datasource);
		DBqueryGenerator gn = new DBqueryGenerator();
		
		User curr = new User(ex, gn, firstName, lastName, email, password);

		if(curr.emailInUse()){
			RequestDispatcher dp = request.getRequestDispatcher("EmailInUse.html");
			dp.forward(request, response);
			
		}

		curr.addToUsers();
		RequestDispatcher dp = request.getRequestDispatcher("SuccessfullRegister.html");
		dp.forward(request, response);
	}
}
