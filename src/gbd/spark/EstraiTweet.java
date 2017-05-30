package gbd.spark;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

public class EstraiTweet implements PairFlatMapFunction<String, String, Integer> {

	public Iterator<Tuple2<String, Integer>> call(String linea) throws Exception {
	//	System.err.println(linea);
		ArrayList<Tuple2<String, Integer>> temp = new ArrayList<Tuple2<String, Integer>>();
		if(linea.length()==0)
			temp.add(new Tuple2<String, Integer>("ERR",1));
		if(linea.startsWith("message;"))
			temp.add(new Tuple2<String, Integer>("ERR",1));
	
		String [] parole = linea.split(";");
		String user = parole[2];
		
		temp.add(new Tuple2<String, Integer>(user,1));
		return temp.iterator();
	}

}
