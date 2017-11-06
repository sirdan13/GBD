package gbd.neo4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GestioneAllergie {
	
	Connection c;
	
	public GestioneAllergie() throws SQLException{
		apriConnessione();
		init();
	}

	PreparedStatement psEliminaPersona;
	PreparedStatement psEliminaAllergene;
	PreparedStatement psAttribuisciAllergia;
	PreparedStatement psInserisciPersona;
	PreparedStatement psInserisciAllergene;
	
	String cqlEliminaPersona = "match (n:Person) where n.id = {1} detach delete n";
	String cqlEliminaAllergene = "match (n:Product) where id(n) = {1} delete n";
	String cqlAttribuisciAllergia = "match (n),(m) where id(n)={1} and id(m)={2} create (n)-[:IS_ALLERGIC_TO]->(m)";
	String cqlInserisciPersona = "create (n:Person{name: {1} })";
	String cqlInserisciAllergene = "create (n:Product{name: {1} })";
	
	public static void main(String[] args) throws SQLException {
		GestioneAllergie ga = new GestioneAllergie();
	//	ga.aggiungiAllergia(3, 15);
		ga.aggiungiAllergene("shrimps");
	//	ga.eliminaPersona(27);
		ga.chiudiConnessione();
	}
	
	
	public void aggiungiPersona(String nome) throws SQLException{
		psInserisciPersona.setString(1, nome);
		psInserisciPersona.executeUpdate();
	}
	
	public void eliminaPersona(int id) throws SQLException{
		psEliminaPersona.setInt(1, id);
		psEliminaPersona.executeUpdate();
	}
	
	public void aggiungiAllergene(String nome) throws SQLException{
		psInserisciAllergene.setString(1, nome);
		psInserisciAllergene.executeUpdate();
	}
	
	public void eliminaAllergene(int id){
		
	}
	
	public void aggiungiAllergia(int idPersona, int idAllergene) throws SQLException{
		psAttribuisciAllergia.setInt(1, idPersona);
		psAttribuisciAllergia.setInt(2, idAllergene);
		psAttribuisciAllergia.executeUpdate();
	}
	
	public void eliminaAllergia(int idPersona, int idAllergene){
		
	}
	
	public List<Object> elencoAllergici(int idAllergene){
		return null;
	}
	
	public List<Object> estraiReteSociale(int idPersona, int profondita){
		return null;
	}
	
	private void apriConnessione() throws SQLException{
		c = DriverManager.getConnection("jdbc:neo4j://localhost:7474/", "neo4j", "twitter");
	}
	
	public void chiudiConnessione() throws SQLException{
		c.close();
	}
	
	private void init() throws SQLException{
		psEliminaPersona = c.prepareStatement(cqlEliminaPersona);
		psEliminaAllergene = c.prepareStatement(cqlEliminaAllergene);
		psAttribuisciAllergia = c.prepareStatement(cqlAttribuisciAllergia);
		psInserisciPersona = c.prepareStatement(cqlInserisciPersona);
		psInserisciAllergene = c.prepareStatement(cqlInserisciAllergene);
	}

}
