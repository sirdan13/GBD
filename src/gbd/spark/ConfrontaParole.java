package gbd.spark;

import org.apache.spark.api.java.function.Function2;

public class ConfrontaParole implements Function2<String, String, String> {

	
	public String call(String arg0, String arg1) throws Exception {
		if(arg0.length()>arg1.length())
			return arg0;
		return arg1;
	}

}
