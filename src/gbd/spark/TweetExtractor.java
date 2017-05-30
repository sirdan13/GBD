package gbd.spark;

import org.apache.spark.api.java.function.Function;

public class TweetExtractor implements Function<String, Tweet> {

	
	public Tweet call(String line) throws Exception {
		if(line.startsWith("message"))
			return new Tweet("error", 0, "error");
		String [] fields = line.split(";");
		return new Tweet(fields[0], Integer.parseInt(fields[1]), fields[2]);
	}

}
