package tranzactionSystem;

import java.util.Vector;

public abstract class Magazin implements IMagazin {
	public String nume;
	public Vector<Factura> lista = new Vector<Factura>();
	
	public double getTotalFaraTaxe(){
		return 0;
	}
	
	public double getTotalCuTaxe(){
		return 0;
	}
	
	public double getTotalCuTaxeScutite(){
		return 0;
	}
	
	public double getTotalTaraFaraTaxe(String tara){
		return 0;
	}
	
	public double getTotalTaraCuTaxe(String tara){
		return 0;
	}
	
	public double getTotalTaraCuTaxeScutite(String tara){
		return 0;
	}
	
	public double calculScutiriTaxe(){
		return 0;
	}
}
