package gbd.spark;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class WordCounter {
	
	/*
	 * Esempio d'uso di Spark
	 * 
	 * Un'applicazione Spark si basa sull'utilizzo delle RDD (Resilient Distributed Data structures).
	 * Qualunque applicazione che istanzi ed uso queste strutture dati è implicitamente distribuita.
	 * 
	 * Prima di creare oggetti di tipo RDD occorre definire la configurazione per l'applicazione
	 */
	
	
	public static void main(String [] args){
		
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);

		/*
		 * Definiamo la configurazione d'uso di Spark
		 */
		SparkConf conf = new SparkConf();
		
		conf.setAppName("Esempio d'uso di Spark");
		/*
		 * Definisce la modalita' con cui l'applicazione Spark viene eseguita.
		 * Nel caso in cui sia eseguita in modalita' stand-alone, e' necessario
		 * specificare la stringa local seguita da una coppia di parentesi quadre
		 * al cui interno indichiamo quanti core del sistema corrente adoperare (* per adoperarli tutti)
		 */
		conf.setMaster("local[*]");
		
		/*
		 * Introduciamo l'oggetto JavaSparkContext necessario per accedere ai servizi di Spark.
		 * Una volta creato questo oggetto abbiamo attivato Spark.
		 */
		
		JavaSparkContext jsc = new JavaSparkContext(conf);
		
		
		/*
		 * Struttura dati distribuita usata per ospitare le righe del file di input da processare
		 */
		
		
		/*
		 * Esistono due modi per creare una RDD.
		 * 
		 * La prima possibilita' e' convertire una struttura dati tradizionale (di tipo Collection) in una RDD
		 * utilizzando il metodo parallelize di JavaSparkContext
		 */
	/*
		List<String> lines = new ArrayList<String>();
		lines.add("Sopraaa la panca");
		lines.add("la capra cammmmpa");
		lines.add("Sotto la panca");
		lines.add("la capra crepa");
	*/	
		
		
		/*
		 * L'uso di parallelize converte una lista locale in una struttura dati distribuita JavaRDD
		 */
		
		/*
		 * Ogni qual volta incontro un metodo richiamato su una JavaRDD, questi non viene eseguito localmente ma in modalita distribuita
		 */
	//	JavaRDD<String> pLines = jsc.parallelize(lines);
		
		JavaRDD<String> pLines = jsc.textFile("nasodargento.txt");
		
		EstraiParole ep = new EstraiParole("a");
		JavaPairRDD<String, Integer> pWords = pLines.flatMapToPair(ep);
		
		/*
		 * Converto pWords in una lista locale 
		 */
		
	//	List<Tuple2<String, Integer>> words = pWords.collect();
	//	for(Tuple2<String, Integer> w : words)
	//		System.out.println(w._1()+" "+w._2());
		
		SommaFrequenze sf = new SommaFrequenze();
		JavaPairRDD<String, Integer> pFrequencies = pWords.reduceByKey(sf);
		
		List<Tuple2<String, Integer>> freq = pFrequencies.collect();
		for(Tuple2<String, Integer> w : freq)
			System.out.println(w._1()+" "+w._2());
		
		pFrequencies.saveAsTextFile("frequencies");
		
		
		jsc.close();
		
	}
	
}