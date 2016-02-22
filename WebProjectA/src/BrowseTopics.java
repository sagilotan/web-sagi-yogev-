

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
import models.Topic;


/**
 * Servlet implementation class NewQues
 */

public class BrowseTopics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseTopics() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ResultSet rs = null,ms = null;
		String s,get="";
		int i=0;
		Collection <Topic> Topics=new ArrayList<Topic>();
		Collection <Question> Questions=new ArrayList<Question>();
		try{
			
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/DatasourceA");
			Connection conn = ds.getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt2=conn.createStatement();
			String[] Array = {"abc","def","ghi","jkl"};
			while (i<Array.length){
				
				s="SELECT SUM(QuesRate) AS TopicRate FROM Ques WHERE Topics LIKE '%" + Array[i] + "%'" ;
                rs=stmt.executeQuery(s);
                if(rs.next()){
                 	get="select  * from Ques where Topics LIKE '%" + Array[i] + "%' order by QuesRate desc";
                ms=stmt2.executeQuery(get);
                Questions=new ArrayList<Question>();
                while(ms.next()){
                	Questions.add(new Question(ms.getInt("Id"),ms.getString("Topics"), ms.getString("TheQues"), ms.getString("Time"), ms.getString("NickName"), ms.getInt("QuesRate"),""));
                }
                
				Topics.add(new Topic(Array[i],rs.getInt("TopicRate"),Questions));
                }
                else{
                	Topics.add(new Topic(Array[i],0));
                }
				i++;
			}
			
			rs.close();
			ms.close();
			stmt.close();
			stmt2.close();
			conn.close();
			
			
			Gson gson = new Gson();
			
        	//convert from questions arraylist to json
			Type type=new TypeToken<Collection<Topic>>() {}.getType();
			
			String QuestionJsonResult=gson.toJson(Topics, type);
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
