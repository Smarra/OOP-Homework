package tranzactionSystem;

import java.io.File;
import java.util.*;

public class Gestiune {
	public static Gestiune gestiune = new Gestiune();
	public ArrayList<Produs> produse = new ArrayList<>();
	public ArrayList<Magazin> magazine = new ArrayList<>();
	public ArrayList<String> tari = new ArrayList<>();
	public TreeMap<String, TreeMap<String, Double>> taxe = new TreeMap<>();
	public static File fProduse = null;
	public static File fFacturi = null;
	public static File fTaxe = null;
	
	private Gestiune(){}
	
	public static Gestiune getInstance(){
		return gestiune;
	}
}
