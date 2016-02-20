package models;
import models.Question;
import java.util.*;

public class Answer {

	private String UsNN,Time,Message,UserVote;
	private int Rating,Id;
	private Question Question; //ONLY in case leader board when we want to connect question to answer not the opposite
	public Answer(){
		Message = "";
		Time = "";
		UsNN = "";
		UserVote="";
	}
	
	public Answer(String message,int rating,Question question){  //for leaderboard
		Message = message;
		Rating=rating;
		Question=question;
	}
	
	public Answer(int id,String message, String time,String usnn,int rating,String uservote) {
		Id=id;
		Message = message;
		Time = time;
		UsNN =usnn;
		Rating=rating;
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
	
	
	public int getRating() {
		return Rating;
	}
	
	public int getId() {
		return Id;
	}
	
	public String getUserVote() {
		return UserVote;
	}
	
}
