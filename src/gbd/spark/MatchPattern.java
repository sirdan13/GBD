package gbd.spark;

import org.apache.spark.api.java.function.Function;

public class MatchPattern implements Function<String, Boolean> {

	String pattern = "capra";
	public Boolean call(String linea) throws Exception {
		if(linea.contains(pattern))
			return true;
		return false;
	}

}
