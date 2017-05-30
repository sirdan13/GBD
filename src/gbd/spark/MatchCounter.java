package gbd.spark;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatchCounter {

	public static void main(String[] args) throws FileNotFoundException {
    
  Scanner sc=new Scanner(new File("nasodargento.txt"));
  Scanner sc1=new Scanner(System.in);
  System.out.println("Inserisci la stringa da ricercare");
  String stringa=sc1.nextLine();
  int count=0;
  while(sc.hasNext()){
	 String line=sc.nextLine();
	 if(line.contains(stringa)){
	 count++;
	 
	 		}

		}

System.out.println("Numero di righe contenenti la stringa cercata: "+count);

	}
}
