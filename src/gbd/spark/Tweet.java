package gbd.spark;

public class Tweet {
	
	public String message;
	public int rt;
	public String userID;
	public Tweet(String message, int rt, String userID) {
		super();
		this.message = message;
		this.rt = rt;
		this.userID = userID;
	}
	
	

}
