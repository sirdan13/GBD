package gbd.spark;

import java.io.Serializable;

public class Transazione implements Serializable{
	
	int id;
	int importo;
	int soggetto;
	int beneficiario;
	String tipo;
	
	
	public Transazione(int id, int importo, int soggetto, int beneficiario, String tipo) {
		super();
		this.id = id;
		this.importo = importo;
		this.soggetto = soggetto;
		this.beneficiario = beneficiario;
		this.tipo = tipo;

	}
	
	

}
