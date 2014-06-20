package mySQL;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
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
 
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	PrintWriter out = resp.getWriter();
<<<<<<< HEAD
    	out.println("<html><head><title>wazaa</title></head><body>");
         
=======
    	out.println("<html><head></head><body>");
    	
>>>>>>> 37c9fc52e4f73e2c2103c40aa52653f1b21af8c3
        ResultSet resultSet = null;
        try {
        	 
            // Get Connection and Statement
            connection = dataSource.getConnection();
            statement = connection.createStatement();
<<<<<<< HEAD
            String query = "SELECT * from users";
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
               out.println("<p>" + resultSet.getString(2) +  "</p>");
=======
            String query = "SELECT * from ItemsHave";
           ;
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
            //	System.out.print("<p>" + resultSet.getString(1) + " " + resultSet.getDate(2) + " " + resultSet.getTime(2) +  "</p>");
               out.println("<p>" + resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(2) +  "</p>");
>>>>>>> 37c9fc52e4f73e2c2103c40aa52653f1b21af8c3
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try { if(null!=resultSet)resultSet.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
        out.println("</body></html>");
        
    }
}
