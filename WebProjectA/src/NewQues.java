

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

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
import models.Question;

/**
 * Servlet implementation class NewQues
 */

public class NewQues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewQues() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);
		String usName =(String) session.getAttribute("usName");
		
		ResultSet rs,gs=null;
		String s="select * from Ques Where Answers=0 order by Id desc";
		
		try{
			
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/DatasourceA");
			Connection conn = ds.getConnection();
			Statement stmt=conn.createStatement();
			
			Statement st=conn.createStatement();
			Collection <Question> Questions=new ArrayList<Question>();
			
			rs=stmt.executeQuery(s);
			String get="";
			while (rs.next()){
				
				get="select * from VotedQues where Id=" + rs.getInt("Id") + "and UserName='" + usName + "'";  //getting if user answered
				gs=st.executeQuery(get);
					
				if(gs.next()||usName.equals(rs.getString("UserName"))){ //meaning user ALREADY voted or he is the one who upload question
				Questions.add(new Question(rs.getInt("Id"),rs.getString("Topics"), rs.getString("TheQues"), rs.getString("Time"), rs.getString("NickName"),rs.getInt("Answers"), rs.getInt("QuesRate"),"disabled"));
				}
				
				else{ //meaning user NOT voted
				Questions.add(new Question(rs.getInt("Id"),rs.getString("Topics"), rs.getString("TheQues"), rs.getString("Time"), rs.getString("NickName"),rs.getInt("Answers"), rs.getInt("QuesRate"),"active"));
				}
				
			}
			rs.close();
			gs.close();
			st.close();
			stmt.close();
			conn.close();
			
			
			Gson gson = new Gson();
			
        	//convert from questions arraylist to json
			Type type=new TypeToken<Collection<Question>>() {}.getType();
			
			String QuestionJsonResult=gson.toJson(Questions, type);
			System.out.println(QuestionJsonResult);
			PrintWriter out = res.getWriter();
        	out.println(QuestionJsonResult);
        	out.close();
        	
			
		}
		catch(SQLException | NamingException e) {
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
