package gbd.spark;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

public class EstraiParole implements PairFlatMapFunction<String, String, Integer> {

	private String pattern;
	
	public EstraiParole(String pattern){
		this.pattern=pattern;
	}
	
	
	
	public EstraiParole() {
		
	}



	public Iterator<Tuple2<String, Integer>> call(String linea) throws Exception {
		
		ArrayList<Tuple2<String, Integer>> temp = new ArrayList<Tuple2<String, Integer>>();
		String [] parole = linea.split(" ");
		for(String parola : parole){
			if(parola.contains(pattern))
				temp.add(new Tuple2<String, Integer>(parola,1));
		}
		
		return temp.iterator();
	}

}
