

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

import models.Answer;
import models.Question;
import models.User;
import models.Topic;

/**
 * Servlet implementation class NewQues
 */

public class LeaderBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaderBoard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ResultSet rs,gs = null,ms=null;
		String s="select * from Users";
		int Qsum,Qnum,Asum,Anum,k,i;  //Qsum:sum of Q rating,Qnum number of Q.A similar.k indector for 5 times
		double temp;
		try{
			
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/DatasourceA");
			Connection conn = ds.getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt2=conn.createStatement();//for search the question of answer that user answered
			Statement st=conn.createStatement();//for going throw questions,answers
			
			Collection <User> Users=new ArrayList<User>();
			Collection <Question> Questions=new ArrayList<Question>();
			Collection <Answer> Answers=new ArrayList<Answer>();
			Collection <Topic> Topics=new ArrayList<Topic>();
			String[] Array = {"Random","TheDoctor","1-8Doctor","9Doctor","109Doctor","11Doctor","12Doctor","Tardis","DoctorCompanion","RoseTyler",
					"MickeySmith","DonnaNoble","MarthaJones","AmyPond","RoryWilliams","RiverSong","ClaraOswald","DoctorEnemies"
					,"Daleks","CyberMen","TheMaster","WeepingAngels","Zygons","Sontarans","VashtaNerda","Odd","TheSilence","TheTvShow"};
			rs=stmt.executeQuery(s);
			String get="";
			while (rs.next()){
				
				Qnum=0;
				Qsum=0;
				Anum=0;
				Asum=0;
			    
				
				get="select * from Ques where UserName='" + rs.getString("UserName") + "' order by Id desc";  //getting all Q that user answered
				gs=st.executeQuery(get);
					
				k=0;
				Questions=new ArrayList<Question>();
				while(gs.next()){ 
				Qnum++;
				Qsum=Qsum+ gs.getInt("QuesRate");
				if(k<5){
					Questions.add(new Question(gs.getString("Topics"),gs.getString("TheQues"),gs.getString("Time"),gs.getInt("QuesRate")));//adding the 5 last asked questions
					k++;
				}
				
				}
				
				get="select * from Answers where UserName='" + rs.getString("UserName") + "' order by AnsId desc";  //getting all A that user answered
				gs=st.executeQuery(get);
				
				k=0;
				Answers=new ArrayList<Answer>();
				while(gs.next()){ 
					Anum++;
					Asum=Qsum+ gs.getInt("AnsRate");
					if(k<5){
						get="select * from Ques where Id=" + gs.getInt("Id");
						ms=stmt2.executeQuery(get);
						ms.next();
						Answers.add(new Answer(gs.getString("TheAns"),gs.getInt("AnsRate"),new Question(ms.getString("Topics"),ms.getString("TheQues"),ms.getString("Time"),ms.getInt("QuesRate"))));
						k++;
					}
				}
				
				if(Anum==0){
					Anum=1;   //so we dont have dived zero still 0
				}
				
				if(Qnum==0){
					Qnum=1;
				}
				
				temp=(0.2*((double)Qsum/Qnum))+(0.8*((double)Asum/Anum));
				
				
				i=0;
				Topics=new ArrayList<Topic>();
				
				while (i<Array.length){
					get="select SUM(AnsRate) AS TopicRate from Answers where UserName='" + rs.getString("UserName") + "' and"
							+ " Id in (select Id from Ques where Topics LIKE '%" + Array[i] + "%')";  //getting all Q that user answered in specific topic
					ms=stmt2.executeQuery(get);
					ms.next();//always we can do next
					
					if(ms.getInt("TopicRate")>0){//only if he have some rating answers in the topic we add
					Topics.add(new Topic(Array[i],ms.getInt("TopicRate")));	
					}
								i++;	
				}
				
				Users.add(new User(rs.getString("NickName"),rs.getString("ImageURL"),temp,rs.getString("Descript"),Questions,Answers,Topics));
				
				
			}
			
			rs.close();
			gs.close();
			ms.close();
			st.close();
			stmt.close();
			stmt2.close();
			conn.close();
			
			
			Gson gson = new Gson();
			
        	//convert from questions arraylist to json
			Type type=new TypeToken<Collection<User>>() {}.getType();
			
			String QuestionJsonResult=gson.toJson(Users, type);
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
