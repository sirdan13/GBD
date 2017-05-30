package gbd.spark;

import org.apache.spark.api.java.function.Function;

public class MatchUserID implements Function<String, Boolean> {

	String userID;
	
	public MatchUserID(String userID){
		this.userID=userID;
	}
	
	public Boolean call(String arg0) throws Exception {
		if(arg0.equals(userID))
			return true;
		return false;
	}

}
