

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
import models.Answer;
/**
 * Servlet implementation class BrosweQues
 */

public class BrowseQues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseQues() {
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
		
		Gson gson = new Gson();
		Type type=new TypeToken<Collection<Answer>>() {}.getType();
		
		ResultSet rs,gs = null,ms,as=null;
		String s="select * from Ques order by Id desc";
		
		try{
			
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/DatasourceA");
			Connection conn = ds.getConnection();
			Statement stmt=conn.createStatement(); //for all the questions
			Statement stmt2=conn.createStatement(); //get all answers
			Statement st=conn.createStatement(); //check if ques already voted
			Statement st2=conn.createStatement();//check if ans already voted
			Collection <Question> Questions=new ArrayList<Question>();
			Collection <Answer> Answers=new ArrayList<Answer>();
			Answer FirstAns=null;
			rs=stmt.executeQuery(s);
			String get="";
			String votedAns="";
			int sum,a=0;//holding the sum of rating answers for question
			double temp=0;
			while (rs.next()){
				sum=0; //intallize to 0 each time
				
				get="select * from VotedQues where Id=" + rs.getInt("Id") + "and UserName='" + usName + "'";
				gs=st.executeQuery(get);
				
				get="select * from Answers where Id=" + rs.getInt("Id") + " order by AnsRate"; //get all the answers
				ms=stmt2.executeQuery(get);
				FirstAns=null;
				if(ms.next()){    //first answer
					sum=sum+ms.getInt("AnsRate");
					votedAns="select * from VotedAns where AnsId=" + ms.getInt("AnsId") + "and UserName='" + usName + "'";
					as=st2.executeQuery(votedAns);
					if(as.next()||usName.equals(ms.getString("UserName"))){	//meaning user ALREADY voted or he is the one who upload answer
				      FirstAns=new Answer(ms.getInt("AnsId"),ms.getString("TheAns"),ms.getString("Time"),ms.getString("NickName"),ms.getInt("AnsRate"),"disabled");
					}
					
					else{
						FirstAns=new Answer(ms.getInt("AnsId"),ms.getString("TheAns"),ms.getString("Time"),ms.getString("NickName"),ms.getInt("AnsRate"),"active");
					}
					
				
				}// end if of first answer
				
				Answers=new ArrayList<Answer>(); //rest of the answers
				while(ms.next()){
					sum=sum+ms.getInt("AnsRate");
					votedAns="select * from VotedAns where AnsId=" + ms.getInt("AnsId") + "and UserName='" + usName + "'";
					as=st2.executeQuery(votedAns);
					if(as.next()||usName.equals(ms.getString("UserName"))){	//meaning user ALREADY voted or he is the one who upload answer	
				Answers.add(new Answer(ms.getInt("AnsId"),ms.getString("TheAns"),ms.getString("Time"),ms.getString("NickName"),ms.getInt("AnsRate"),"disabled"));	
					}
					
					else{
				Answers.add(new Answer(ms.getInt("AnsId"),ms.getString("TheAns"),ms.getString("Time"),ms.getString("NickName"),ms.getInt("AnsRate"),"active"));
					}
				
				}//end of while that get throw answers
				
				a=rs.getInt("Answers");
				if(a==0){
					temp=(rs.getInt("QuesRate")*0.2);
				}
				else{
				temp=(((double)sum/a)*0.8+(rs.getInt("QuesRate")*0.2));
				//System.out.println(((double)sum/a)*0.8 + "," + (rs.getInt("QuesRate")*0.2));
				}
				
				if(gs.next()||usName.equals(rs.getString("UserName"))){ //meaning user ALREADY voted or he is the one who upload question
				Questions.add(new Question(rs.getInt("Id"),rs.getString("Topics"), rs.getString("TheQues"), rs.getString("Time"), rs.getString("NickName"),rs.getInt("Answers"),temp, rs.getInt("QuesRate"),"disabled",FirstAns,Answers));
				}
				
				else{ //meaning user NOT voted
				Questions.add(new Question(rs.getInt("Id"),rs.getString("Topics"), rs.getString("TheQues"), rs.getString("Time"), rs.getString("NickName"),rs.getInt("Answers"),temp,rs.getInt("QuesRate"),"active",FirstAns,Answers));
				}
				
			} //end of while that get questions
			
			rs.close();
			gs.close();
			st.close();
			stmt.close();
			stmt2.close();
			conn.close();
			
			
			
        	//convert from questions arraylist to json
			 type=new TypeToken<Collection<Question>>() {}.getType();
			
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
