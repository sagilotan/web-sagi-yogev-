

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/**
 * Servlet implementation class AddQues
 */

public class AddQues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddQues() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String topics = req.getParameter("topics");
		String message =req.getParameter("message");
		HttpSession session = req.getSession(false);
		String usName =(String) session.getAttribute("usName");
		String usNN =(String) session.getAttribute("usNN");
		 java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
		
		 String drop="DROP TABLE Ques";
		String create ="CREATE TABLE Ques("
				+ "Id int primary key generated always as identity,"
				+ "Topics varchar(50),"
				+ "TheQues varchar(300) NOT NULL, "
				+ "Time TIMESTAMP,"
				+ "UserName varchar(10) NOT NULL,"
				+ "NickName varchar(20),"
				+ "Answers int,"
				+ "QuesRate int,"
				+ "FOREIGN KEY (UserName) REFERENCES Users(UserName)"
				+ ")";
		
		String insert= "INSERT INTO Ques(Topics,TheQues,Time,UserName,NickName,Answers,QuesRate)"
				+ " VALUES('" + topics + "','" + message + "','" + sqlDate + "','" + usName + "','" + usNN + "',0,0)";
		
		try{
			
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/DatasourceA");
			Connection conn = ds.getConnection();
			Statement stmt=conn.createStatement();
			
			 int rs=stmt.executeUpdate(insert);
			 
			 res.sendRedirect("AddQuestion.html");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
