package gbd.spark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class GestioneTransazioni {
	

	public static void main(String[] args) throws SQLException {
		
		/*
		 * Configurazione spark
		 */
		
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
//		Connection c = DriverManager.getConnection("jdbc:neo4j://localhost:7474/","neo4j","transazioni");
//		Statement st = c.createStatement();
		SparkConf conf = new SparkConf();
		conf.setAppName("Esempio d'uso di Spark");
		conf.setMaster("local[*]");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		
		/*
		 * Esercizio 1:
		 * Lettura dei dataset
		 * Filtro le intestazioni in modo da non generare errori
		 */
		
		JavaRDD<String> pLines = jsc.textFile("data/transazioni.csv").filter(x->!x.contains("ID"));
		JavaRDD<String> pLines2 = jsc.textFile("data/Anagrafe.txt").filter(x->!x.contains("\"ID\""));
		
		
		/*
		 * Estraggo transazioni e soggetti importandoli in due distinte JavaPairRDD
		 */
		
		JavaPairRDD<Integer, Transazione> pTransazioni = pLines.mapToPair(new EstraiTransazioniToPair());
		JavaPairRDD<Integer, Soggetto> pSoggetti = pLines2.mapToPair(new EstraiSoggettiToPair());
		
		
		/*
		 * Estraggo i tipi di transazione e ne calcolo le frequenze
		 */
		
		JavaPairRDD<String, Integer> pTipo = pTransazioni.mapToPair((x)->new Tuple2<String, Integer>(x._2.tipo, 1));
		JavaPairRDD<String, Integer> pFrequencies = pTipo.reduceByKey(new SommaFrequenze());

		/*
		 * Mostro a schermo i risultati
		 */
		
		List<Tuple2<String, Integer>> freq = pFrequencies.collect();
		for(Tuple2<String, Integer> w : freq)
			System.out.println(w._1()+" "+w._2());
		System.out.println();
		
		/*
		 * Salvo i risultati sul disco
		 */
		pFrequencies.saveAsTextFile("spark_output/frequenza_tipo_transazione");
		
		
		/*
		 * Esercizio 2:
		 * Creo una nuova JavaPairRDD che comprenda l'id dei soggetti e l'importo totale che hanno disposto nelle loro operazioni
		 * La riduco secondo le chiavi (gli id dei soggetti stessi)
		 * Infine eseguo un join con la JavaPairRDD che contiene le informazioni anagrafiche dei soggetti
		 */
		JavaPairRDD<Integer, Integer> pIDImporti = pTransazioni.mapToPair((x)->new Tuple2<Integer, Integer>(x._2.soggetto, x._2.importo));
		JavaPairRDD<Integer, Integer> reduced = pIDImporti.reduceByKey(new SommaFrequenze());
		JavaPairRDD<Integer, Tuple2<Integer, Soggetto>> pFrequencies2 = reduced.join(pSoggetti);
		
		/*
		 * Mostro a schermo i risultati
		 */
		
		List<Tuple2<Integer, Tuple2<Integer, Soggetto>>> freq2 = pFrequencies2.collect();
		for(Tuple2<Integer, Tuple2<Integer, Soggetto>> u : freq2)
			if((u._2()._2.cognome+" "+u._2._2.nome).length()>=16)
				System.out.println(u._1()+"\t"+u._2()._2.cognome+" "+u._2._2.nome+"\t"+u._2._1);
			else
				System.out.println(u._1()+"\t"+u._2()._2.cognome+" "+u._2._2.nome+"\t\t"+u._2._1);
		System.out.println();
		
		/*
		 * Salvo i risultati sul disco
		 */
		
		pFrequencies2.saveAsTextFile("spark_output/somma_importi_soggetti");
		
	
	
	}

}
