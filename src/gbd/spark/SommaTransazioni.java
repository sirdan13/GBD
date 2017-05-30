package gbd.spark;

import org.apache.spark.api.java.function.Function2;

public class SommaTransazioni implements Function2<Integer, Integer, Integer> {

	
	public Integer call(Integer arg0, Integer arg1) throws Exception {
		return arg0+arg1;
	}

}
