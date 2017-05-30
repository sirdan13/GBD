package gbd.spark;

import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class EstraiSoggettiToPair implements PairFunction<String, Integer, Soggetto> {

	public Tuple2<Integer, Soggetto> call(String line) throws Exception {
		String [] fields = line.split(";");
		String nome, cognome;
		nome = fields[2].replace("\"", "");
		cognome = fields[1].replace("\"", "");
	//	return new Tuple2<Integer, Soggetto>(Integer.parseInt(fields[0]), new Soggetto(Integer.parseInt(fields[0]), fields[1], fields[2]));
		return new Tuple2<Integer, Soggetto>(Integer.parseInt(fields[0]), new Soggetto(Integer.parseInt(fields[0]), nome, cognome));
	}

}
