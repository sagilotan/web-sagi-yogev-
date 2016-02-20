

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource; 



/**
 * Servlet implementation class GetName
 */

public class UDVoteAns extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UDVoteAns() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * 
	 * handle also down vote!!!!
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);
		String usName =(String) session.getAttribute("usName");
		
		/**
		Enumeration a = req.getParameterNames();
		 Object obj = a.nextElement();
	      int value=0;
	     String t=(String) obj;
	     String c = req.getParameter(t);
	     if ("1".equals(c)) {
	    	    value=1;
	    	} else if ("-1".equals(c)) {
	    	    value=-1;
	    	}
	    
	    int Id = Integer.parseInt(t); ;*/
		
		String temp=req.getParameter("Id");
		int Id=Integer.parseInt(temp);
		int value=0;
		temp=req.getParameter("value");
		value=Integer.parseInt(temp);
	
		
	    String get="SELECT * FROM Answers WHERE AnsId=" + Id;
	    String insert="INSERT INTO VotedAns(AnsId,UserName) VALUES(" + Id + ",'" + usName + "')";
		String create="CREATE TABLE VotedAns(AnsId int,UserName varchar(10) NOT NULL,FOREIGN KEY (UserName) REFERENCES Users(UserName),"
				+ "FOREIGN KEY (AnsId) REFERENCES Answers(AnsId))";      
		
       try{
			
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/DatasourceA");
			Connection conn = ds.getConnection();
			Statement stmt=conn.createStatement();
			
			 
			 ResultSet r=stmt.executeQuery(get);
			 r.next();
			 int t=r.getInt("AnsRate");
			 t=t+value;
			 System.out.println("!!!!!!!!!");
			 String update="UPDATE Answers SET AnsRate=" + t + " WHERE AnsId=" + Id;
			 int rs=stmt.executeUpdate(update);
			 rs=stmt.executeUpdate(insert);
			 System.out.println("UDVAns");
			
			r.close(); 
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
