package tranzactionSystem;

import java.util.Vector;

public abstract class Magazin implements IMagazin, Comparable<Object> {
	public String nume;
	public String tip;
	public Vector<Factura> lista = new Vector<Factura>();
	
	public Magazin(String tip){
		this.tip = tip;
	}
	
	public double getTotalFaraTaxe(){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalFaraTaxe();
		return total;
	}
	
	public double getTotalCuTaxe(){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalCuTaxe();
		return total;
	}
	
	public double getTotalCuTaxeScutite(){
		return getTotalCuTaxe() * (100 - calculScutiriTaxe())/100 ;
	}
	
	public double getTotalTaraFaraTaxe(String tara){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalTaraFaraTaxe(tara);
		return total;
	}
	
	public double getTotalTaraCuTaxe(String tara){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalTaraCuTaxe(tara);
		return total;
	}
	
	public double getTotalTaraCuTaxeScutite(String tara){
		return getTotalTaraCuTaxe(tara) * (100 - calculScutiriTaxe())/100 ;
	}
	
	public String toString(){
		return nume + " " + lista;
	}
}
