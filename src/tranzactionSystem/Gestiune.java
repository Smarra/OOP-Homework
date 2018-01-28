package tranzactionSystem;

import java.io.File;
import java.util.*;

public class Gestiune {
	private static Gestiune gestiune = new Gestiune();
	public ArrayList<Produs> produse = new ArrayList<>();
	public ArrayList<Magazin> magazine = new ArrayList<>();
	public ArrayList<String> tari = new ArrayList<>();
	public TreeMap<String, TreeMap<String, Double>> taxe = new TreeMap<>();

	public static boolean filesLoaded = false;
	public static String backgroundFilePath = "rsz_9_4_2500.jpg";
	public static File fProduse = null;
	public static File fFacturi = null;
	public static File fTaxe = null;
	
	
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
	
	public Produs getProdusByNameAndCountry( String name, String tara ){
		for( Produs prod : produse )
		{
			if( prod.getDenumire().equals( name ) && prod.getTaraOrigine().equals( tara ))
			{
				System.out.println(prod);
				return prod;
			}
		}
		return null;
	}
}
