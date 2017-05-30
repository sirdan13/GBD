package gbd.spark;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.spark.api.java.function.PairFlatMapFunction;

import scala.Tuple2;

public class EstraiHashtag implements PairFlatMapFunction<String, String, Integer> {

	
	public Iterator<Tuple2<String, Integer>> call(String linea) throws Exception {
		String [] attrs = linea.split(";");
		ArrayList<Tuple2<String, Integer>> output = new ArrayList<Tuple2<String, Integer>>();
		if(attrs.length<3){
			output.add(new Tuple2<String, Integer>("ERR",0));
			return output.iterator();	
		}
	
		else{
			String tweet = attrs[0];
			String [] words = tweet.split("#");
			for(String word : words){
				if(word.startsWith("#"))
					output.add(new Tuple2<String, Integer>(word,1));
			}
			return output.iterator();
		}
		
	}

}
