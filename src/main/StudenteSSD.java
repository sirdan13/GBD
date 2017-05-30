package main;

public class StudenteSSD extends Studente{
	
	public StudenteSSD(String matricola, String nome, String cognome){
		super(matricola, nome, cognome);		
		setCdL("SSD");
	}
	
public StudenteSSD(String matricola, String nome, String cognome, String comuneResidenza) {
	super(matricola, nome, cognome, comuneResidenza);		
	setCdL("SSD");
	}

public String toString(){
		
		return "Nome: "+getNome()+"\tCognome: "+getCognome()+"\tCdL: SSD";
	}
	
public double calcolaMedia(){
		
		double somma=0.0, sommaCFU=0;
		int mag = 0; int min=32, CFUmag=0, CFUmin=0;
		
		if(esami.size()>2){
			
			for(int i =0;i<esami.size();i++){
				somma+= esami.get(i).getVoto()*esami.get(i).getCFU();
				sommaCFU+=esami.get(i).getCFU();
				if(esami.get(i).getVoto()>mag){
					mag=esami.get(i).getVoto();
					CFUmag=esami.get(i).getCFU();
				}
					
				if(esami.get(i).getVoto()<min){
					min=esami.get(i).getVoto();
					CFUmin=esami.get(i).getCFU();
				}
					
			}
			
			somma=somma-(mag*CFUmag)-(min*CFUmin);
			sommaCFU=sommaCFU-CFUmag-CFUmin;
			return somma/sommaCFU;
		}
		
		else{
			
			
			for(int i =0;i<esami.size();i++){
				somma+= esami.get(i).getVoto()*esami.get(i).getCFU();
				sommaCFU+=esami.get(i).getCFU();
			}
				
			return somma/sommaCFU;
		}
		
		
		
		
		
		
	}



}
