package gbd.spark;

import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

/*
 * Definisce una funzione generica che data in input una riga di testo
 * restituisce in output una coppia del tipo (0,x) dove x è la parola più lunga
 * presente nella stringa
 * 
 * T1	 -> generica stringa di input
 * K	 -> intero, costante 0 usata per forzare l'aggregazione delle coppie di input
 * V	 -> stringa, contiene la parola più lunga presente nella stringa di input
 */

public class EstraiParolaPiuLunga implements PairFunction<String, Integer, String> {

	
	public Tuple2<Integer, String> call(String linea) throws Exception {
		String [] parole = linea.split(" ");
		String max = "";
		for(String p : parole){
			if(p.length()>max.length())
				max=p;
		}
		return new Tuple2<Integer, String>(0, max);
	}

}
