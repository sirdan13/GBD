package gbd.spark;
import java.io.Serializable;

public class Soggetto implements Serializable{
	
	int id;
	String nome;
	String cognome;

	
	public Soggetto(int id, String nome, String cognome) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;

	}
	
	public Soggetto(String nome, String cognome) {
		super();
		this.nome = nome;
		this.cognome = cognome;
	}


	
	

}
