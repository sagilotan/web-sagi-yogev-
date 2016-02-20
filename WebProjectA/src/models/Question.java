package models;
import models.Answer;
import java.util.*;

public class Question {

private String UsNN,Time,Message,Topics,UserVote;//Question "schema"
private int Rating,Answers,Id;
private Collection <Answer> List=new ArrayList<Answer>();
private Answer Ans;
	public Question(int id,String topics, String message, String time,String usnn,int answers,int rating,String uservote,Answer ans,Collection <Answer> list) {
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
	}
	
	public Question(int id,String topics, String message, String time,String usnn,int answers,int rating,String uservote) {
		Topics = topics;
		Message = message;
		Time = time;
		UsNN =usnn;
		Answers=answers;
		Rating=rating;
		Id=id;
		UserVote=uservote;
		 
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
	
	public int getId() {
		return Id;
	}
	
	public String getUserVote() {
		return UserVote;
	}
	
	
	
	
}

