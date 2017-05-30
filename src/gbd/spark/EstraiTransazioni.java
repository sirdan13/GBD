package gbd.spark;

import org.apache.spark.api.java.function.Function;

public class EstraiTransazioni implements Function<String, Transazione> {

	
	public Transazione call(String line) throws Exception {

		String [] fields = line.split(";");
		if(fields[3].length()<1)
			return new Transazione(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), 0, fields[4]);
		return new Transazione(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), Integer.parseInt(fields[3]), fields[4]);

	}

}
