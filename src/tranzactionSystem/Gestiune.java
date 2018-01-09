package tranzactionSystem;

import java.io.File;
import java.util.*;

public class Gestiune {
	private static Gestiune gestiune = new Gestiune();
	public ArrayList<Produs> produse = new ArrayList<>();
	public ArrayList<Magazin> magazine = new ArrayList<>();
	public ArrayList<String> tari = new ArrayList<>();
	public TreeMap<String, TreeMap<String, Double>> taxe = new TreeMap<>();
	public static File fProduse = new File("C:\\Users\\Smara\\Desktop\\produse.txt");
	public static File fFacturi = new File("C:\\Users\\Smara\\Desktop\\facturi.txt");
	public static File fTaxe = new File("C:\\Users\\Smara\\Desktop\\taxe.txt");
	
	private Gestiune(){}
	
	public static Gestiune getInstance(){
		return gestiune;
	}
	
	public boolean checkProdus( Produs p ){
		for( Produs prod : produse )
		{
			if( prod.getDenumire().equals(p.getDenumire()))
				if( prod.getCategorie().equals(p.getCategorie()))
					return true;
		}
		return false;
	}
}
