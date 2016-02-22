package models;

import java.util.ArrayList;
import java.util.Collection;

public class Topic {

	private String Topic;
	private int Rating;
	private Collection <Question> QList=new ArrayList<Question>();
	
	public Topic(String topic,int rating,Collection <Question> qlist){
		Topic=topic;
		Rating=rating;
		QList=qlist;
		
	}
	
	public Topic(String topic,int rating){
		Topic=topic;
		Rating=rating;
		
		
	}

	public int getRating() {
		return Rating;
	}
	
	public String getTopic(){
		return Topic;
	}
}
