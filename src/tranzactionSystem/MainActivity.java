package tranzactionSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MainActivity {
	public static void main(String args[]){
		
		ArrayList<Produs> produse = new ArrayList<Produs>();
		
		try{		
			File file = new File("produse.txt");
			Scanner input = new Scanner(file);
			String antet = input.nextLine();
			
			while( input.hasNextLine() )
			{
				StringTokenizer line = new StringTokenizer(input.nextLine(), " ");
				Produs prod = new Produs();
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
