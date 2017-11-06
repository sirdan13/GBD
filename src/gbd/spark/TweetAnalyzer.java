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

public class TweetAnalyzer {

	public static void main(String[] args) throws SQLException {
		/*
		 * Esempio d'uso di Spark
		 * 
		 * Un'applicazione Spark si basa sull'utilizzo delle RDD (Resilient Distributed Data structures).
		 * Qualunque applicazione che istanzi ed uso queste strutture dati è implicitamente distribuita.
		 * 
		 * Prima di creare oggetti di tipo RDD occorre definire la configurazione per l'applicazione
		 */
		
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);


		/*
		 * Stabiliamo la connessione con Neo4J
		 */

		Connection c = DriverManager.getConnection("jdbc:neo4j://localhost:7474/","neo4j","tweet");
		Statement st = c.createStatement();		
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
		JavaRDD<String> pLines = jsc.textFile("tweets2.csv");

//		JavaPairRDD<String, Integer> pUsers = pLines.mapToPair(new UserIdExtractor());
		
//		JavaPairRDD<String, Integer> pFrequencies = pUsers.reduceByKey(new SommaFrequenze());

//		pFrequencies.saveAsTextFile("freq1");
		
		
		JavaRDD<Tweet> pTweets = pLines.map(new TweetExtractor());
		
		
		/*
		 * Utilizziamo il lambda calcolo per estrarre da ciascuna occorrenza di Tweet presente in pLines
		 * un suo particolare attributo
		 */
		JavaRDD<String> pUsers = pTweets.map((x)->x.userID);
		
		List<String> users = pUsers.collect();
		for(String u : users){
			if(u.equals("error"))
				continue;
			else{
				String cql = "MERGE (n:User {value:'" + u +"'})";
				st.executeUpdate(cql);
			}
			
			
		}
		
		
		/*
		 * Utilizziamo il lambda calcolo per estrarre da ciascuna occorrenza di Tweet presente in pLines
		 * un suo particolare attributo.
		 * 
		 * Per poter associare ciascun tweet al suo autore, abbiamo bisogno di estrapolare assieme al testo del tweet
		 * anche il codice identificativo di questi
		 */
		
		JavaPairRDD<String, String> pMessages = pTweets.mapToPair((x)->new Tuple2<String,String>(x.message,x.userID));
		
//		JavaRDD<String> pMessages = pTweets.map((x)->x.message);
		
		 List<Tuple2<String, String>> messages = pMessages.collect();
		for(Tuple2<String, String> m : messages){
			if(m._2().equals(0))
				continue;
			else{
				String cql = " CREATE (n:Tweet {value:'" + m._1.replace("'", "\"").replace("\"","\\\"").trim() +"',userId:'" + m._2+"'})";
	//			System.out.println(cql);
				st.executeUpdate(cql);
			}
			
			
		}

		/*
		 * Creo un arco tra tutte le coppie (tweet, user) nel caso in cui tweet.userId sia uguale a user.id
		 */
		String cql = "match (t:Tweet),(u:User) WHERE t.userId=u.value create unique (t)<-[:AUTHOR]-(u)";
		st.executeUpdate(cql);
		
		
//		JavaPairRDD<String, Integer> pHT = pLines.flatMapToPair(new HTExtractor());
		
//	 	JavaPairRDD<String, Integer> pFrequencies = pHT.reduceByKey(new SommaFrequenze());

//		pFrequencies.saveAsTextFile("freq2");

		
		
		/*
		 * Vogliamo creare un grafo Neo4J i cui nodi sono gli hashtag estrapolati nel passo precedente,
		 * successivamente collegheremo questi nodi con altri nodi rappresentanti gli utenti che hanno usati quegli HT
		 */

		

		 // Passo 1. Raccogliamo tutte le frequenze degli hashtag mediante una collect()

//		List<Tuple2<String, Integer>> frequencies = pFrequencies.collect();
		
		

		 // Passo 3. Per ogni hashtag, creiamo un nuovo nodo Neo4J
		 
/*		for (Tuple2<String, Integer> t : frequencies){
			String cql = "create (n:Hashtag {freq:" + t._2 + ",text:'" + t._1+"'})";
			st.executeUpdate(cql);
		}
	*/	
		c.close();
		jsc.close();
		
		
		
	}

}
