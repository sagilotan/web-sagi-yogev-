

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/**
 * Servlet implementation class RegServlet
 */

public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String username = req.getParameter("username");
		String password =req.getParameter("password");
		String NickName =req.getParameter("NickName");
		String Desc =req.getParameter("Desc");
		String ImgURL =req.getParameter("ImgURL");
		
		String insert= "INSERT INTO Users VALUES('" + username + "','" + password + "','" + 
		NickName + "','" + Desc + "','" + ImgURL + "')";
		
		try{
			
		Context context = new InitialContext();
		BasicDataSource ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/DatasourceA");
		Connection conn = ds.getConnection();
		Statement stmt=conn.createStatement();
		 
		 int rs=stmt.executeUpdate(insert);
		if(rs==1){
			HttpSession session=req.getSession(true);
			 session.setAttribute("status", "user");
		     session.setAttribute("usName", username);
		     session.setAttribute("usNN", NickName);
			res.sendRedirect("HomePage.html");
		}
		
		else{
			res.sendRedirect("WelcomePage.html");
		}
		
		
		stmt.close();
		conn.close();
		
		}
		
		catch (SQLException | NamingException e) {
    		getServletContext().log("Error while closing connection", e);
    		res.sendError(500);//internal server error
    	}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
	}

}
