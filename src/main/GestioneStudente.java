package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class GestioneStudente {
	
	ArrayList<Studente> studenti;
	HashMap<String, Studente> matricolaToStudente;
	ArrayList<Esame> esami;
	
	public GestioneStudente(){
		
		studenti = new ArrayList<Studente>();
		matricolaToStudente = new HashMap<String, Studente>();
		esami = new ArrayList<Esame>();
	}

	public static void main(String[] args) throws FileNotFoundException, MatricolaInesistenteException, VotoNonValidoException {
	
		GestioneStudente gs = new GestioneStudente();
		gs.leggiStudenti("studenti.csv", gs);
		System.out.println("Caricati "+gs.getNumeroStudenti()+ " profili studenti.");
		gs.leggiEsame("esami.csv");
		System.out.println("Caricati "+gs.getNumeroEsami()+ " esami.");
		System.out.println();
		
		for(Studente s : gs.studenti){
			try{
				if(s.esami.size()>0){
					System.out.println("La media di "+s.getNome()+" "+s.getCognome()+" è "+gs.mediaStudente(s.getMatricola()));
					System.out.println("Inoltre, ha sostenuto i seguenti esami:");
					for(Esame e : s.esami)
						System.out.println(e.getInsegnamento()+"\t"+e.getVoto());
					
				}
					
				else
					System.out.println("Lo studente "+s.getNome()+" "+s.getCognome()+" non ha sostenuto alcun esame.");
			}
			catch(MatricolaInesistenteException e1){
				
			}
			
		}

	
	
	}
	
	public void leggiStudenti(String file, GestioneStudente gs) throws FileNotFoundException{
		
		File f = new File(file);
		Scanner sc = new Scanner(f);
		sc.nextLine();
		Random r = new java.util.Random();
		while(sc.hasNextLine()){
			String row = sc.nextLine();
			String [] attr = row.split(",");
			String matricola="";
			for(int i = 1;i<attr[0].length()-1;i++)
				matricola+=attr[0].charAt(i);
	
			String cognome="";
			for(int i = 1;i<attr[1].length()-1;i++)
				cognome+=attr[1].charAt(i);
			String nome="";
			for(int i = 1;i<attr[2].length()-1;i++)
				nome+=attr[2].charAt(i);

			if(r.nextInt(2)==0)
				gs.aggiungiStudenteSAF(matricola, nome, cognome);
			else
				gs.aggiungiStudenteSSD(matricola, nome, cognome);

		}
		sc.close();
		
	}
	
	
	public void leggiEsame(String file) throws FileNotFoundException, MatricolaInesistenteException, VotoNonValidoException{
		
		File f = new File(file);
		Scanner sc = new Scanner(f);
		
		while(sc.hasNextLine()){
			
			String row = sc.nextLine();
			String [] attr = row.split(",");
			if(attr.length!=6)
				continue;
			Esame e = new Esame(attr[1], Integer.parseInt(attr[4]), Integer.parseInt(attr[5]), attr[3]);
			esami.add(e);
			
			try{
				
				Studente s = cercaStudente2(attr[1]);
				s.aggiungiEsame(e);
			}
			
			catch(MatricolaInesistenteException e1){
				
			}
			
		}
		sc.close();
		
		
	}
	
public double mediaStudente(String matricola) throws MatricolaInesistenteException{
	
	Studente s = cercaStudente2(matricola);
	return s.calcolaMedia();
}
	
public int getNumeroStudenti(){
	return studenti.size();
}

public int getNumeroEsami(){
	return esami.size();
}
	
public void aggiungiStudenteSSD(String matricola, String nome, String cognome){
	
	Studente s = new StudenteSSD(matricola,nome,cognome);
	studenti.add(s);
	matricolaToStudente.put(matricola, s);
}
public void aggiungiStudenteSAF(String matricola, String nome, String cognome){
	Studente s = new StudenteSAF(matricola,nome,cognome);
	studenti.add(s);
	matricolaToStudente.put(matricola, s);
}
public void eliminaStudente(String matricola) throws MatricolaInesistenteException{


	studenti.remove(cercaStudente2(matricola));
	
}
public void rettificaNomeStudente(String matricola, String nuovoNome) throws MatricolaInesistenteException{
	
	cercaStudente2(matricola).setNome(nuovoNome);
	
}

@SuppressWarnings("unused")
private Studente cercaStudente(String matricola) throws MatricolaInesistenteException{
	for(Studente s : studenti)
		if(s.getMatricola()==matricola){
			return s;
		}
	throw new MatricolaInesistenteException("La matricola inserita non corrisponde a nessuno studente");
	
}

private Studente cercaStudente2(String matricola) throws MatricolaInesistenteException{
	Studente s = matricolaToStudente.get(matricola);
	if(s==null)
		throw new MatricolaInesistenteException("La matricola inserita non corrisponde a nessuno studente");
	
	return s;
}
	

}
