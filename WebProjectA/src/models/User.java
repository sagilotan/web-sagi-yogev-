package models;

import models.Answer;
import models.Question;
import java.util.*;

public class User {

	private String UsNN,Photo,Desc;
	private double Rating;
	private Collection <Answer> AList=new ArrayList<Answer>();
	private Collection <Question> QList=new ArrayList<Question>();
	public User(String usnn, String photo,double rating,String desc,Collection <Question> qlist,Collection <Answer> alist) {
		Desc=desc;
		UsNN =usnn;
		Rating=rating;
		Photo=photo;
		QList=qlist;
		AList=alist;
	}
	
	public String getUsNN() {
		return UsNN;
	}
	
	public String getPhoto() {
		return Photo;
	}
	
	public String getDesc() {
		return Desc;
	}
	
	public double getRating() {
		return Rating;
	}
}
