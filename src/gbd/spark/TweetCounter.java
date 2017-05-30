package gbd.spark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class TweetCounter {

	public static void main(String[] args) throws SQLException {
		
		
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);

		SparkConf conf = new SparkConf();
		
		conf.setAppName("Esempio d'uso di Spark");
		conf.setMaster("local[*]");
		
		//CREIAMO UNA CONNESSIONE A NEO4J
		Connection c = DriverManager.getConnection("jdbc:neo4j://localhost:7474/", "neo4j", "tweet");
		Statement st = c.createStatement();
		
		
		JavaSparkContext jsc = new JavaSparkContext(conf);
	//	JavaRDD<String> pLines = jsc.parallelize(lines);
		
		JavaRDD<String> pLines = jsc.textFile("tweets2.csv");
		System.out.println("Count pLines"+pLines.count());
		
		
		
		
		
		
		
		
		
		
		EstraiTweet et = new EstraiTweet();
		JavaPairRDD<String, Integer> pUsers = pLines.flatMapToPair(et);
		
		
		List<String> tweets = pLines.collect();
		for(String t : tweets){
			String cql = " CREATE (n:Tweet {value:'" + t.replace("'", "\"") +"'})";
			st.executeUpdate(cql);
		}
		
		
/*
		SommaFrequenze sf = new SommaFrequenze();
		JavaPairRDD<String, Integer> pFrequencies = pUsers.reduceByKey(sf);
		if(pFrequencies==null)
			System.out.println("NULL");
		List<Tuple2<String, Integer>> freq = pFrequencies.collect();
		for(Tuple2<String, Integer> p : freq)
			System.out.println(p._1()+" "+p._2());
	*/
	//	System.out.println();
	//	pFrequencies.saveAsTextFile("tweet_frequencies");
	//	pFrequencies.saveAsTextFile("freq1");
		
//		pFrequencies = pLines.flatMapToPair(new EstraiHashtag());
//		pFrequencies()

/*		pFrequencies=
		
		freq = pFrequencies.collect();
		for(Tuple2<String, Integer> p : freq)
			System.out.println(p._1()+" "+p._2());
		
		pFrequencies.saveAsTextFile("freq2");
		*/
		
		JavaPairRDD<String, Integer> pHT = pLines.flatMapToPair(new EstraiHashtag());
		System.out.println("Count pHT"+pHT.count());
		SommaFrequenze sf = new SommaFrequenze();
		JavaPairRDD<String, Integer> pFrequencies = pUsers.reduceByKey(sf);
		System.out.println("Count pFrequencies"+pFrequencies.count());
		pFrequencies = pHT.reduceByKey(new SommaFrequenze());

		pFrequencies.saveAsTextFile("freq2");

		//RACCOGLIAMO LE FREQUENZE DEGLI HASHTAG MEDIANTE UNA COLLECT
		List<Tuple2<String, Integer>> frequencies = pFrequencies.collect();
	//	System.out.println("Count frequencies"+frequencies.count());
	

		
		for(Tuple2<String, Integer> t : frequencies){
			System.out.println(t._2()+"\t"+t._1());
			String cql = "create (n:Hashtag{freq:"+t._2()+",text:'"+t._1()+"'})";
			st.executeUpdate(cql);
		}
		
		
		
		c.close();
		jsc.close();

	}

}
