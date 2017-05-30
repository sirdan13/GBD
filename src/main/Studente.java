package main;
import java.util.ArrayList;

public abstract class Studente {
	
	private String nome;
	private String cognome;
	private String matricola;
	private String comuneResidenza;
	protected ArrayList<Esame> esami;
	protected int nEsami;
	private String CdL;
	
	
	public Studente(String matricola, String nome, String cognome){
		this.setMatricola(matricola);
		this.nome=nome;
		this.cognome=cognome;
		init();
	}
	
	public Studente(String matricola, String nome, String cognome,String comuneResidenza){
		this.setMatricola(matricola);
		this.nome=nome;
		this.cognome=cognome;
		this.setComuneResidenza(comuneResidenza);
		init();
	}
	
	public void aggiungiEsame(Esame e) throws VotoNonValidoException{
		try{
			if(e.getVoto()<18 || e.getVoto()>31)
				throw new VotoNonValidoException("Il voto inserito non è nell'intervallo 18-31");
			else
				esami.add(e);
		}
		catch(VotoNonValidoException e1){
			
		}
		
	}
	
	
	
	public String getCdL() {
		return CdL;
	}

	public void setCdL(String cdL) {
		CdL = cdL;
	}

	//Inizializza le strutture dati della classe Studente
	protected void init(){
		esami = new ArrayList<Esame>();
	}
	
	//Metodo costruttore. Non restituisce nulla e viene eseguito all'atto della creazione di un oggetto Studente
	//Un costruttore ha lo stesso nome della classe cui appartiene e non restituisce valori di ritorno
	public Studente(){
		init();
		
	}
	

	public String getNome(){
		return nome;
	}
	
	public void setNome(String nome){
		this.nome=nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	
	public void rettificaVotoEsame(int idEsame, int nuovoVoto) throws EsameNonValidoException, VotoNonValidoException {
			
			if ((nuovoVoto < 18) || (nuovoVoto > 31)){
				
				throw new VotoNonValidoException("Il voto inserito è fuori dall'intervallo 18-31.");
				
			}
			else{
				
				for(int i = 0;i<esami.size();i++){
					if(esami.get(i)!=null)
						if(idEsame==esami.get(i).getId()){
							esami.get(i).setVoto(nuovoVoto);
							return;
					}
				}
				
			}
			
			
		}
	
	
	public void eliminaEsame(int idEsame) throws EsameNonValidoException {

		
		for(int i = 0;i<esami.size();i++){
			if(esami.get(i)!=null)
				if(idEsame==esami.get(i).getId()){
					esami.remove(i);
					
					return;
			}
				
		}
			
		
		
	}
	
	/*
	public double calcolaMedia(){
		
		double somma=0.0;
		for(int i =0;i<nEsami;i++)
			somma+= esami[i].getVoto();
		return somma/nEsami;
	}
	*/
	
	public abstract double calcolaMedia();
	
	
	public String toString(){
		
		return "Nome: "+nome+" Cognome: "+cognome;
	}
	
	
	@SuppressWarnings("unused")
	private void azzeraEsami(){
		esami=null;
	}
	/*
	public void aggiungiEsame(int voto, int CFU, String insegnamento) throws VotoNonValidoException{
		
		
		Esame e = new Esame(voto,CFU,insegnamento);
		esami[nEsami]=e;
		nEsami++;
	}*/
	
	/*
public int verbalizzaEsame(int voto, int CFU, String insegnamento) throws VotoNonValidoException{
		
		
		if ((voto < 18) || (voto > 31)){


			throw new VotoNonValidoException("Il voto inserito è fuori dall'intervallo 18-31.");
		}
			


		
		Esame e = new Esame(voto, CFU, insegnamento);
		esami[nEsami] = e;
		
		
//		  Valorizziamo le prime caselle disponibili
//		  degli array voti e CFU con i dati forniti in input
		 
//		this.voti[nrEsami] = voto;
//		this.CFU[nrEsami] = CFU;
//		this.insegnamento[nrEsami] = insegnamento;
		

		 //Incrementiamo il numero di esami
		 
		nEsami++;
		
		return e.getId();
		
	}*/

public String getMatricola() {
	return matricola;
}

public void setMatricola(String matricola) {
	this.matricola = matricola;
}



public String getComuneResidenza() {
	return comuneResidenza;
}



public void setComuneResidenza(String comuneResidenza) {
	this.comuneResidenza = comuneResidenza;
}
	
	
	
}
