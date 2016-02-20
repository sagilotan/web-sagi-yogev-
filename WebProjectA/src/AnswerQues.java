

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.Enumeration; 
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


/**
 * Servlet implementation class NewQues
 */

public class AnswerQues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerQues() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Enumeration a = req.getParameterNames();
		 Object obj = a.nextElement();
	      
	     String t=(String) obj;
	    
	      String message = req.getParameter(t);
	    int Id = Integer.parseInt(t); ;
	      
		
		HttpSession session = req.getSession(false);
		String usName =(String) session.getAttribute("usName");
		String usNN =(String) session.getAttribute("usNN");
		 java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
		
		 String drop="DROP TABLE Answers";
		String create ="CREATE TABLE Answers("
				+ "AnsId int primary key generated always as identity,"
				+ "Id int,"
				+ "TheAns varchar(300) NOT NULL, "
				+ "Time TIMESTAMP,"
				+ "UserName varchar(10) NOT NULL,"
				+ "NickName varchar(20),"
				+ "AnsRate int,"
				+ "FOREIGN KEY (UserName) REFERENCES Users(UserName),"
				+ "FOREIGN KEY (Id) REFERENCES Ques(Id)"
				+ ")";
		
		String insert= "INSERT INTO Answers(Id,TheAns,Time,UserName,NickName,AnsRate)"
				+ " VALUES(" + Id + ",'" + message + "','" + sqlDate + "','" + usName + "','" + usNN + "',0)";
		String get="SELECT * FROM Ques WHERE Id=" + Id;
		
		try{
			
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/DatasourceA");
			Connection conn = ds.getConnection();
			Statement stmt=conn.createStatement();
			
			
		     int rs=stmt.executeUpdate(insert); 
			 ResultSet r=stmt.executeQuery(get);
			 r.next();
			 int temp=r.getInt("Answers");
			 temp=temp+1;
			 String update="UPDATE Ques SET Answers=" + temp + " WHERE Id=" + Id;
			 rs=stmt.executeUpdate(update);
			 
			 res.sendRedirect("Questions.html"); 
		
			System.out.println("!!!!");
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
