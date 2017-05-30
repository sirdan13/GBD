package gbd.neo4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestNeo4j {

	public static void main(String[] args) throws SQLException {
		
		
		Connection c = DriverManager.getConnection("jdbc:neo4j://localhost:7474/", "neo4j", "SSlazio1900");
		Statement st = c.createStatement();
		String allergene = "nuts";
		String cql = "match (m)-[:IS_ALLERGIC_TO]->(n:Product) where n.name = '"+allergene+"' return m.name";
		ResultSet rs = st.executeQuery(cql);
		while(rs.next())
			System.out.println(rs.getString(1));
		
		c.close();
	
	
	}

}
