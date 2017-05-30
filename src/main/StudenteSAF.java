package main;

public class StudenteSAF extends Studente {

	public double calcolaMedia(){
		
		double somma=0.0, sommaCFU=0;
		for(int i =0;i<esami.size();i++){
			somma+= esami.get(i).getVoto()*esami.get(i).getCFU();
			sommaCFU+=esami.get(i).getCFU();
		}
			
		return somma/sommaCFU;
	}
	
public String toString(){
		
	return "Nome: "+getNome()+"\tCognome: "+getCognome()+"\tCdL: SAF";
	}
	

	public StudenteSAF(String matricola, String nome, String cognome){
		super(matricola, nome, cognome);		
		setCdL("SAF");
	}
	
	public StudenteSAF(String matricola, String nome, String cognome, String comuneResidenza){
		super(matricola, nome, cognome,comuneResidenza);		
		setCdL("SAF");
	}



	

	

}
