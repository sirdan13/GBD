import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Questionario_RSI {
	
	private static List<String[]> data;
	private static List<String[]> encoding;
	private static String[][] dati;
	private static int[][] codifica;
	private static String[][] modalità;

	public static void main(String[] args) throws FileNotFoundException {
		
		
		loadData("risposte.csv");
		loadEncoding("codifica.txt");
		
		int [][] risultati = new int[data.size()][20];
		int riga = 0, colonna = 0;
		//Righe della matrice dei dati
		for(int i = 0;i<dati.length;i++){
			colonna=0;
			//Colonne della matrice dei dati
			for(int j = 0;j<dati[i].length;j++){
				
					//Colonne della matrice della codifica
					for(int p = 0;p<modalità[j].length;p++){
						//Effettuare controllo
						if(dati[i][j].equals(modalità[j][p])){
							risultati[riga][colonna]=codifica[j][p];
							
						}
							
					}
					colonna++;
				}
			riga++;
			}
		
		for(int i = 0;i<risultati.length;i++){
			for(int j = 0;j<risultati[i].length;j++){
				if(j<risultati[i].length-1)
					System.out.print(risultati[i][j]+";");
				else
					System.out.print(risultati[i][j]);
			}
			System.out.println();
		}
			
				
		}
	
	
	private static void loadData(String location) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(location));
		data = new ArrayList<String[]>();
		while(sc.hasNext()){
			String [] temp = sc.next().split("#");
			String [] row = new String[20];
			for(int i = 1;i<temp.length;i++)
				row[i-1]=temp[i].replaceAll("_", " ");
			data.add(row);
		}
		dati = new String[data.size()][20];
		for(int i = 0;i<data.size();i++)
			dati[i]=data.get(i);
		for(int i = 0;i<dati.length;i++)
			for(int j = 0;j<dati[i].length;j++)
				if(dati[i][j].length()==0)
					dati[i][j]="-1";
			
	//	System.out.println("Caricate "+data.size()+" risposte.");
		sc.close();
	}
	
	private static void loadEncoding(String location) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(location));
		encoding = new ArrayList<String[]>();
		while(sc.hasNext()){
			String [] row = sc.next().split(";");
			for(int i = 0;i<row.length;i++)
				row[i]=row[i].replaceAll("_", " ");
				encoding.add(row);
			
		}
		modalità = new String[encoding.size()][];
		codifica = new int[encoding.size()][];
		for(int i = 0;i<encoding.size();i++){
			modalità[i] = new String[encoding.get(i).length];
			codifica[i] = new int[encoding.get(i).length];
			for(int k = 0;k<encoding.get(i).length;k++){
				modalità[i][k]=encoding.get(i)[k].split("=")[0];
				codifica[i][k]=Integer.parseInt(encoding.get(i)[k].split("=")[1]);
			}
			
		}
		/*
		for(int i = 0;i<modalità.length;i++){
			for(int j = 0;j<modalità[i].length;j++){
				System.out.print(modalità[i][j]+"\t"); System.out.println(codifica[i][j]+"\t");
			}
			System.out.println();
		}
			*/
	//	System.out.println("Caricato file di codifica risposte.");
		sc.close();
	}

}
