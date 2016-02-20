package models;

public class Answer {

	private String UsNN,Time,Message,UserVote;
	private int Rating,Id;
	
	public Answer(){
		Message = "";
		Time = "";
		UsNN = "";
		UserVote="";
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
