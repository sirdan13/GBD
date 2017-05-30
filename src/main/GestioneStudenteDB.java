package main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class GestioneStudenteDB {

	/*
	 * Classe utilizzata per testare le funzioni di java per il collegamento con una base dati SQL
	 * Per poter interagire con un DBMS da Java, è necessario utilizare un modulo connector
	 */
	
	/*
	 * Scriviamo un'applicazione che si collega al nostro db e mostra a schermo l'elenco degli studenti
	 */
	
	
	private Connection c;
	
	private ArrayList<Studente> studenti;
	private HashMap<Integer, Studente> idToStudente;
	
	/*
	 * Elenco delle queries da eseguire durante l'esecuzione dell'applicazione
	 * organizzate come PreparedStatement
	 */
	
	PreparedStatement psSelezionaStudenti;
	PreparedStatement psEliminaStudenti;
	PreparedStatement psModificaStudenti;
	
	/*
	 * IMPLEMENTARE QUESTA QUERY SOSTITUENDOLA A QUELLA GIA' ESISTENTE
	 */
	PreparedStatement psInserisciStudente;
	
	
	
	public GestioneStudenteDB() throws SQLException{
		
		apriConnessione();
		studenti= new ArrayList<Studente>();
		idToStudente = new HashMap<Integer, Studente>();
		
		/*
		 * Carichiamo in memoria l'elenco degli studenti
		 */
		
		caricaStudenti();
		
		
		
	}
	
private void apriConnessione() throws SQLException{
		
		MysqlDataSource ds = new MysqlDataSource();
		ds.setServerName("luna.sta.uniroma1.it");
		ds.setPort(3306);
		ds.setDatabaseName("gbd2017_lombardi");
		c = ds.getConnection("gbd2017_lombardi", "scienzestatistiche");
		System.out.println("Connessione stabilita");
		psSelezionaStudenti=c.prepareStatement("SELECT * FROM studente");
		psModificaStudenti=c.prepareStatement("UPDATE studente set ComuneResidenza=? where id=?");
		psEliminaStudenti=c.prepareStatement("DELETE studente FROM studente where id=?");
		psInserisciStudente=c.prepareStatement("insert into studente (matricola,Nome,Cognome,ComuneResidenza,ProvinciaResidenza,gNascita,mNascita,aNascita,Sesso) VALUES (?,?,?,?,?,?,?,?,?)");
				
		
	}
	
	private void caricaStudenti() throws SQLException{
		
	//	String sql = "SELECT * FROM studente";
	//	Statement st = c.createStatement();
	//	ResultSet rs = st.executeQuery(sql);
		ResultSet rs = psSelezionaStudenti.executeQuery();
		int count = 0;
		while(rs.next()){
			String matricola = rs.getString("matricola");
			String nome = rs.getString("Nome");
			String cognome = rs.getString("Cognome");
			String comune = rs.getString("ComuneResidenza");
/*			String provincia = rs.getString("ProvinciaResidenza");
			int gNascita = rs.getInt("gNascita");
			int mNascita = rs.getInt("mNascita");
			int aNascita = rs.getInt("aNascita");
			String sesso = rs.getString("Sesso");
*/			int id = rs.getInt("id");
			
			Random r = new Random();
			Studente s;
			if(r.nextInt(2)==0)
				s = new StudenteSAF(matricola,nome,cognome,comune);
			else
				s = new StudenteSSD(matricola,nome,cognome,comune);
			studenti.add(s);
			idToStudente.put(id,s);
//			System.out.println("Nome: "+s.getNome()+" Cognome: "+s.getCognome()+" Città: "+s.getComuneResidenza());
			count++;
				
		}
		
		System.out.println("Numero schede caricate: "+count);
	}
	
	
	public void mostraStudente(int id){
		
		Studente s = idToStudente.get(id);
		if(s==null){
			System.out.println("Non esiste uno studente con identificativo "+id);
			return;
		}
		
		System.out.println("Nome: "+s.getNome()+" Cognome: "+s.getCognome()+" Comune: "+s.getComuneResidenza());
	}
	
	
	public void eliminaStudente(int id){
		
	
		try {
				psEliminaStudenti.setInt(1, id);
				if(psEliminaStudenti.executeUpdate()==0){
					System.out.println("Identificativo inesistente");
					return;
			}
				
			Studente s = idToStudente.get(id);
			idToStudente.remove(id);
			studenti.remove(s);
		} catch (SQLException e) {
			
			System.out.println("Impossibile eseguire l'operazone: "+e.getMessage());
		}
			
	}
	
	public void modificaResidenzaStudente(int id, String nuovaResidenza){
		

	//	String sql = "update studente set ComuneResidenza= '"+nuovaResidenza+"' where matricola = '"+matricola+"'";
	//	Statement st;
		try {
			psModificaStudenti.setInt(2, id);
			psModificaStudenti.setString(1, nuovaResidenza);
	//		st = c.createStatement();
			if(psModificaStudenti.executeUpdate()==0){
				System.out.println("Identificativo inesistente: "+id);
				return;
			}
				
			else{
				
				idToStudente.get(id).setComuneResidenza(nuovaResidenza);
				
			}
				
		} catch (SQLException e) {
			
			System.out.println("Impossibile eseguire l'operazone: "+e.getMessage());
		}
	}
	
	
	
	
	public void chiudiConnessione() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("Errore nella chiusura della connessione: "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	public void modificaStudente(int id, String attributo, String valore){
		
		if(!(attributo.compareTo("Nome")==0) && !(attributo.compareTo("Cognome")==0))
			return;
		String sql = "update studente set "+attributo+"= '"+valore+"' where id = '"+id+"'";
		Statement st;
		try {
				st = c.createStatement();
				if(st.executeUpdate(sql)==0){
				System.out.println("Identificativo inesistente: "+id);
				return;
			}
			Studente s = idToStudente.get(id);
			if(attributo.compareTo("Cognome")==0)
				s.setCognome(valore);
			if(attributo.compareTo("Nome")==0)
				s.setNome(valore);
	
		} catch (SQLException e) {
			
			System.out.println("Impossibile eseguire l'operazone: "+e.getMessage());
		}
		
	}
	
	public void aggiungiStudente(String matricola,String nome,String cognome, String comuneResidenza,String provinciaResidenza,int gNascita,int mNascita,int aNascita,String sesso ){
		
		
		int id = 0;
		try {

			psInserisciStudente.setString(1, matricola);
			psInserisciStudente.setString(2, nome);
			psInserisciStudente.setString(3, cognome);
			psInserisciStudente.setString(4, comuneResidenza);
			psInserisciStudente.setString(5, provinciaResidenza);
			psInserisciStudente.setInt(6, gNascita);
			psInserisciStudente.setInt(7, mNascita);
			psInserisciStudente.setInt(8, aNascita);
			psInserisciStudente.setString(9, sesso);
	//		psInserisciStudente.executeUpdate();
	//		Statement st = c.createStatement();
	//		String sql = "insert into studente (matricola,Nome,Cognome,ComuneResidenza,ProvinciaResidenza,gNascita,mNascita,aNascita,Sesso) VALUES ("+matricola+",'"+nome+"','"+cognome+"','"+comuneResidenza+"','"+provinciaResidenza+"',"+gNascita+","+mNascita+","+aNascita+",'"+sesso+"')";
			/*
			 * Quando aggiungo un nuovo studente al db, questi viene contrassegnato con un id sconosciuto all'applicazione
			 * L'applicazione può chiedere al db di conoscere questo id nel seguente modo
			 */
			String separatore = "\\:";
			String[] temp = psInserisciStudente.toString().split(separatore);
			
			psInserisciStudente.executeUpdate(temp[1], Statement.RETURN_GENERATED_KEYS);
			
			/*
			 * Otteniamo questo valore nella forma di un ResultSet richiamando il metodo getGeneratedKeys sotto forma di intero
			 */
	//		System.out.println(psInserisciStudente);
	//		psInserisciStudente.executeUpdate();
			ResultSet rs = psInserisciStudente.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
			System.out.println("Studente creato con identificativo: "+id);
			
		} catch (SQLException e) {
			System.out.println("Impossibile eseguire l'operazone: "+e.getMessage());
		}
		
		Studente s = new StudenteSAF(matricola,nome,cognome,comuneResidenza);
		studenti.add(s);
		idToStudente.put(id, s);
		
	}
	
	public static Studente cercaStudente(int id, GestioneStudenteDB tdb){
		
		Studente s = tdb.idToStudente.get(id);
		if(s==null){
			System.out.println("Non esiste alcuno studente con identificativo "+id);
			return s;
		}
			
		else
			return s;
	}
	
	public static void main(String []args) throws SQLException{
		
		GestioneStudenteDB tdb = new GestioneStudenteDB();

//		tdb.aggiungiStudente("112233", "Ciro", "Immobile", "Roma", "RM", 15, 7, 1990, "Maschio");
//		tdb.eliminaStudente(895);
	
		tdb.chiudiConnessione();
		
		
	}

	
	
}
