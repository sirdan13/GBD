package gbd.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class TestCassandra {
	
	public static void main(String[] args){
		/*
		 * Stabilire connessione con Cassandra
		 * possiamo indicare più indirizzi contemporaneamente
		 */
		
		Cluster c = Cluster.builder().addContactPoint("marvel.sta.uniroma1.it").addContactPoint("gpu.sta.uniroma1.it").withCredentials("gbd2017_lombardi", "scienzestatistiche").build();
		Session s = c.connect("gbd2017_twitteranalysis");
		System.out.println("Connessione effettuata al cluster: "+s.getCluster().getMetadata().getClusterName());
//		String cql = "select * from traccia where id=?";
		String cql = "INSERT INTO hashtags (id, text) VALUES (?, ?)";
		
		PreparedStatement psSelezionaTraccia = s.prepare(cql);
		BoundStatement bsSelezionaTraccia = new BoundStatement(psSelezionaTraccia);
		s.execute(bsSelezionaTraccia.bind(3, "#ht"));
		
		
		
		s.close();
	}

}
