package dfsSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestDFS
 */
@WebServlet("/TestDFS")
public class TestDFS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestDFS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BlockingQueue<Integer> usersQueue = (BlockingQueue<Integer>)getServletContext().getAttribute("usersQueue");
		for (int i = 1; i <= 6; i++){
			try {
				usersQueue.put(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		BlockingQueue<List<ArrayList<Integer>>> resultsQueue = (BlockingQueue<List<ArrayList<Integer>>>)getServletContext().getAttribute("resultsQueue");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("im userebis raodenoba romlebic ciklebshi arian = " + resultsQueue.size());
		List<ArrayList<Integer>> result = null;
		while(!resultsQueue.isEmpty()){
    		try {
				result = resultsQueue.take();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			for (int c = 0; c < result.size(); c++){
				for (int j = 0; j < result.get(c).size(); j++){
					System.out.print("item #" + result.get(c).get(j));
				}
				System.out.println("");
			}
    	}
    	System.out.println("amobechdvebma daasrula mushaoba");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
