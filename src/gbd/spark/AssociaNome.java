package gbd.spark;

import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class AssociaNome implements PairFunction<String, String, String> {

	private String filename;
	
	public AssociaNome(String filename){
		this.filename=filename;
	}
	public Tuple2<String, String> call(String parola) throws Exception {
		
		return new Tuple2<String, String>(parola, filename);
	}

}
