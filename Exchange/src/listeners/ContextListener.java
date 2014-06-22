package listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import dfsSearch.DFSStarter;


/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {
	
	private static final int QUEUE_SIZE = 20;

    /**
     * Default constructor. 
     */
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	BlockingQueue<Integer> usersQueue = new ArrayBlockingQueue<Integer>(QUEUE_SIZE);
		BlockingQueue<List<ArrayList<Integer>>> resultsQueue = new ArrayBlockingQueue<List<ArrayList<Integer>>>(QUEUE_SIZE);
		arg0.getServletContext().setAttribute("usersQueue", usersQueue);
		arg0.getServletContext().setAttribute("resultsQueue", resultsQueue);
		try {
			Context initContext  = new InitialContext();
            Context envContext = (Context)initContext.lookup("java:/comp/env");
            DataSource dataSource = (DataSource)envContext.lookup("jdbc/testdb");
            arg0.getServletContext().setAttribute("DataSource", dataSource);
            
            DFSStarter starter = new DFSStarter(usersQueue, resultsQueue, dataSource);
            System.out.println("saitma mushaoba daiwyo");
            starter.start();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		//amowmebs programam gaagrZela tu ara mushaoba, tu ar dabeWda qvevit mocemuli stringi eseigi shecdomaa!!!
		System.out.println("saitma mushaoba gaagrZela");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
