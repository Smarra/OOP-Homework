package tranzactionSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MainActivity {
	public static void main(String args[]){
		
		File file;
		Scanner input;
		Gestiune gestiune = Gestiune.getInstance();
		
		try{		
			//Folosim utilitarul Scanner pentru parcurgerea fisierului
			file = new File("produse");
			input = new Scanner(file);
			
			//Obtin lista tarilor din prima linie din fisier
			ArrayList<String> tari = new ArrayList<String>();
			if( input.hasNextLine() )
			{
				StringTokenizer line = new StringTokenizer(input.nextLine());
				line.nextToken();
				line.nextToken();
				while( line.hasMoreElements() )
				{
					String tara = new String( line.nextToken() );
					tari.add(tara);
				}
			}
			
			//Obtin pe rand fiecare produs ( vor fi create n*m produs, unde n = nr. produselor,
			//iar m = nr. tarilor de origine )
			while( input.hasNextLine() )
			{
				StringTokenizer line = new StringTokenizer(input.nextLine());
				String denumire = new String( line.nextToken() );
				String categorie = new String( line.nextToken() );
				
				int i = 0;
				while( line.hasMoreTokens() )
				{
					Double pret = Double.parseDouble( line.nextToken() );
					Produs prod = new Produs(denumire, categorie, tari.get(i++), pret);
					gestiune.produse.add( prod );
				}
			}
			input.close();
			
			//parcurgem lista de taxe si actualizam TreeMap-ul de taxe
			file = new File("taxe");
			input = new Scanner(file);
			input.nextLine(); //sarim peste antet deoarece avem deja lista tarilor
			while( input.hasNextLine() )
			{
				String line = input.nextLine();
				StringTokenizer items = new StringTokenizer(line);
			}
			
			
			//parcurgem lista de facturi
			ProductFactory productFactory = new ProductFactory();
			file = new File("facturi");
			input = new Scanner(file);
			
			while( input.hasNextLine() ){
				String line = input.nextLine();
				
				//Daca este magazin, il cream folosind ProductFactory si il adaugam in lista
				//de magazine
				if( line.startsWith("Magazin:") )
				{
					StringTokenizer mag = new StringTokenizer(line, ":");
					mag.nextToken();
					Magazin newMagazin = productFactory.createMagazin(mag.nextToken());
					newMagazin.nume = mag.nextToken();
					
					gestiune.magazine.add(newMagazin);
				}
				
				if( line.startsWith("Factura") ){
					Factura factura = new Factura();
					factura.denumire = line;
				}
			}
			
			System.out.println(gestiune.magazine);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
