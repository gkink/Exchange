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
    	out.println("<html><head><title>wazaa</title></head><body>");
         
        ResultSet resultSet = null;
        try {
            // Get Connection and Statement
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "SELECT * from users";
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
               out.println("<p>" + resultSet.getString(2) +  "</p>");
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
