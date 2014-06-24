package registration;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.sql.DataSource;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QDecoderStream;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import ModelClasses.User;

/**
 * Servlet implementation class fbGoogleLogin
 */
@WebServlet("/fbGoogleLogin")
public class fbGoogleLogin extends HttpServlet {
	private static final String Sample_Password = "sample";
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fbGoogleLogin() {
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
		String firstName, lastName, email, password;
		firstName = request.getParameter("name");
		lastName = request.getParameter("lastname");
		email = request.getParameter("email");
		password = Sample_Password;
		
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(email);
		System.out.println(password);

		
		DataSource dataSource = (DataSource) request.getServletContext().getAttribute("DataSource");
		QueryExecutor executor =  new QueryExecutor(dataSource);
		DBqueryGenerator queryGenerator =  new DBqueryGenerator();
		HttpSession ses =  request.getSession();
		
		User curr =  new User(executor, queryGenerator, firstName, lastName, email, password);
		String redirectUrl = "NewHomepage.jsp";
		if(curr.userRegistered())
			ses.setAttribute("User", curr.getId());
		else if(curr.emailInUse())
			redirectUrl = "guest.jsp?error=email";
		else
			curr.addToUsers();

		response.sendRedirect(redirectUrl);
	}
}
