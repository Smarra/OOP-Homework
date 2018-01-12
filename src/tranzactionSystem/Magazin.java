package tranzactionSystem;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Vector;

public abstract class Magazin implements IMagazin, Comparable<Object> {
	public String nume;
	public String tip;
	public Vector<Factura> lista = new Vector<Factura>();
	
	DecimalFormat df = new DecimalFormat("#.####");
	{
		df.setRoundingMode(RoundingMode.CEILING);
	}
	
	public Magazin(String tip){
		this.tip = tip;
	}
	
	public double getTotalFaraTaxe(){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalFaraTaxe();
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalCuTaxe(){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalCuTaxe();
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalCuTaxeScutite(){
		double total = getTotalCuTaxe() * (100 - calculScutiriTaxe())/100;
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalTaraFaraTaxe(String tara){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalTaraFaraTaxe(tara);
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalTaraCuTaxe(String tara){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalTaraCuTaxe(tara);
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalCategorieFaraTaxe(String categorie){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalCategorieFaraTaxe(categorie);
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalCategorieCuTaxe(String categorie){
		double total = 0;
		for( Factura fact : lista )
			total += fact.getTotalCategorieCuTaxe(categorie);
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalTaraCuTaxeScutite(String tara){
		double total = getTotalTaraCuTaxe(tara) * (100 - calculScutiriTaxe())/100 ;
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalCategorieCuTaxeScutite(String categorie){
		double total = getTotalCategorieCuTaxe(categorie) * (100 - calculScutiriTaxe())/100 ;
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public String toString(){
		return nume + " " + lista;
	}
}
