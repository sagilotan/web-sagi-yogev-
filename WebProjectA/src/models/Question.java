package models;
import models.Answer;
import java.util.*;

public class Question {

private String UsNN,Time,Message,Topics,UserVote;//Question "schema"
private int Rating,Answers,Id;
private double AvgRating;//AvgRating holding the rating ques in Qustion.html,the reative rate
private Collection <Answer> List=new ArrayList<Answer>();
private Answer Ans;
	public Question(int id,String topics, String message, String time,String usnn,int answers,double avgrating,int rating,String uservote,Answer ans,Collection <Answer> list) {
		Topics = topics;
		Message = message;
		Time = time;
		UsNN =usnn;
		Answers=answers;
		Rating=rating;
		Id=id;
		UserVote=uservote;
		Ans=ans;
		 List=list;
		 AvgRating=avgrating;
	}
	
	public Question(int id,String topics, String message, String time,String usnn,int rating,String uservote) {
		Topics = topics;
		Message = message;
		Time = time;
		UsNN =usnn;
		
		Rating=rating;
		Id=id;
		UserVote=uservote;
		 
	}
	
	public Question(String topics,String message,String time,int rating){
		
		Topics = topics;
		Message = message;
		Time = time;
		Rating=rating;
	}

	public String getUsNN() {
		return UsNN;
	}
 
	public String getTime() {
		return Time;
	}
	
	public String getMessage() {
		return Message;
	}
	
	public String getTopics() {
		return Topics;
	}
	
	public int getRating() {
		return Rating;
	}
	
	public int getAnswers() {
		return Answers;
	}
	
	public double getAvgRating() {
		return AvgRating;
	}
	
	public int getId() {
		return Id;
	}
	
	public String getUserVote() {
		return UserVote;
	}
	
	
	
	
}

