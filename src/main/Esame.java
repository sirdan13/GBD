package main;

/*
 * Mantiene i dati relativi ad ogni singolo esame
 */
public class Esame {


	/*
	 * Variabile static usata per attribuire a ciascuna copia di Esame un id univoco
	 */
	private static int contatore = 0;
	/*
	 * Variabile istanza per conservare il codice identificativo dell'oggetto corrente
	 */
	private int id;
	private int voto;
	private int CFU;
	private String insegnamento;
	private String matricolaStudente;
	
	
	public int getVoto() {
		return voto;
	}
	public void setVoto(int voto) {
		this.voto = voto;
	}
	public int getCFU() {
		return CFU;
	}
	public void setCFU(int cFU) {
		CFU = cFU;
	}
	public String getInsegnamento() {
		return insegnamento;
	}
	public void setInsegnamento(String insegnamento) {
		this.insegnamento = insegnamento;
	}
	
	public Esame(String matricolaStudente, int voto, int cFU, String insegnamento) {
		super();
		this.matricolaStudente=matricolaStudente;
		this.id = contatore;
		contatore++;
		this.voto = voto;
		CFU = cFU;
		this.insegnamento = insegnamento;
	}
	public int getId() {
		return id;
	}
	public String getMatricolaStudente() {
		return matricolaStudente;
	}
	public void setMatricolaStudente(String matricolaStudente) {
		this.matricolaStudente = matricolaStudente;
	}
	
	
	
		
}
