package tranzactionSystem;

import java.util.*;

public class Gestiune {
	public static Gestiune gestiune = new Gestiune();
	public ArrayList<Produs> produse = new ArrayList<>();
	public ArrayList<Magazin> magazine = new ArrayList<>();
	public TreeMap<String, TreeMap<String, Double>> taxe = new TreeMap<>();
	
	private Gestiune(){}
	
	public static Gestiune getInstance(){
		return gestiune;
	}
}
