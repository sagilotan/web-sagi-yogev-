

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
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
 * Servlet implementation class exmaple
 */

public class example extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public example() {
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
		String insert ="CREATE TABLE Users("
				+ "UserName varchar(10) PRIMARY KEY,"
				+ "PassWord varchar(8) NOT NULL,"
				+ "NickName varchar(20) NOT NULL, "
				+ "Descript varchar(50),"
				+ "ImageURL varchar(255)"
				+ ")";
				String b= "INSERT INTO Users VALUES('yogev','yogev','shalmon','','')";
		String select="SELECT * FROM Users WHERE UserName='" + username + "' and PassWord='" + password + "'";
		try{
			
			
		Context context = new InitialContext();
		BasicDataSource ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/DatasourceA");
		Connection conn = ds.getConnection();
		Statement stmt=conn.createStatement();
		
		/** String  drop="DROP TABLE VotedAns";
		 
		 stmt.executeUpdate(drop);
		
		drop="DROP TABLE VotedQues";
		 
		 stmt.executeUpdate(drop);
		
		
		drop="DROP TABLE Answers";
		 
		 stmt.executeUpdate(drop);
		
		drop="DROP TABLE Ques";
			
			stmt.executeUpdate(drop);
		
		
		
		
		
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
		
		
		stmt.executeUpdate(create);
		
		System.out.println("!!!!!!!!!!!");
		
		
		 
		create ="CREATE TABLE Answers("
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
		
		 
			stmt.executeUpdate(create);
			
			System.out.println("!!!!!!!!!!!");
		
		 create="CREATE TABLE VotedQues(Id int,UserName varchar(10) NOT NULL,FOREIGN KEY (UserName) REFERENCES Users(UserName),"
					+ "FOREIGN KEY (Id) REFERENCES Ques(Id))";
			
			 
				stmt.executeUpdate(create);
				
				System.out.println("!!!!!!!!!!!");
		 
		 
		 create="CREATE TABLE VotedAns(AnsId int,UserName varchar(10) NOT NULL,FOREIGN KEY (UserName) REFERENCES Users(UserName),"
				+ "FOREIGN KEY (AnsId) REFERENCES Answers(AnsId))";    
		
		
			stmt.executeUpdate(create);
			
			System.out.println("!!!!!!!!!!!"); */
		 
		
		
		
		
		 ResultSet rs=stmt.executeQuery(select);
		if(rs.next()){
			HttpSession session=req.getSession(true);
			 session.setAttribute("status", "user");
		     session.setAttribute("usName", username);
		     session.setAttribute("usNN", rs.getString("NickName"));
		     
			res.sendRedirect("HomePage.html");
		}
		
		else{
			res.sendRedirect("WelcomePage.html");
		}
		
		rs.close();
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
