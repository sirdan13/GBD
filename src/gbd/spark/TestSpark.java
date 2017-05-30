package gbd.spark;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;


public class TestSpark {

	public static void main(String[] args) {
		
	// Esempio d'uso di Spark. Un'applicazione Spark si basa sull'utilizzo delle RDD(Resilient
	// Distributed Data structures). Qualunque applicazione che istanzi ed usa queste strutture
	// dati è implicitamente distribuita. Prima di creare oggetti di tipo RDD occorre definire la
	// configurazione per l'applicazione. Definiamo la configurazione d'uso di Spark.
		
   
	SparkConf conf = new SparkConf();
	conf.setAppName("Esempio d'uso di Spark");
	
	// Definisce la modalità con cui l'applicazione Spark viene eseguita. Nel caso in cui sia
	// eseguita in modalità stand-alone, è necessario specificare la stringa local seguita da una
	// coppia di parentesi quadre al cui interno indichiamo quanti core del sistema corrente 
	// adoperare (* per adoperarli tutti)
	
	conf.setMaster("local[*]");
	
	// Introduciamo l'oggetto JavaSparkContext necessario per accedere ai servizi di Spark. 
	// Una volta creato questo oggetto abbiamo attivato Spark.
	
	JavaSparkContext jsc = new JavaSparkContext(conf);
	
	// Struttura dati distribuita usata per ospitare le righe del file di input da processare
	
	JavaRDD<String> pLines;
	
	// Esistono due modi per creare una RDD. La prima possibilità è convertire una struttura dati
	// tradizionale (di tipo Collection) in una RDD utilizzando il metodo parallelize di JavaSparkContext
	
	List<String> lines=new ArrayList<String>();
	lines.add("Sopra la panca");
	lines.add("La capra campa");
	lines.add("Sotto la panca");
	lines.add("la capra crepa");
	
	// l'uso di parallelize converte una lista locale in una struttura dati distribuita JavaRDD
	
	pLines=jsc.parallelize(lines);
	
	System.out.println("Il numero di righe presenti nella RDD è: " +pLines.count());
	
	// Il metodo stop interrompe il framework Spark
	
	jsc.stop();
	
	
	

	}

}
