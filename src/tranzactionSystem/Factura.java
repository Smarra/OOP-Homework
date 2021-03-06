package tranzactionSystem;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Vector;

public class Factura implements Comparable<Object>{
	public String denumire;
	public Vector<ProdusComandat> lista = new Vector<ProdusComandat>();
	
	DecimalFormat df = new DecimalFormat("#.####");
	{
		df.setRoundingMode(RoundingMode.CEILING);
	}
	
	public double getTotalFaraTaxe(){
		Double total = 0.0;
		for( ProdusComandat prod : lista )
			total += prod.getProdus().getPret() * prod.getCantitate();
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalCuTaxe(){
		double total = 0;
		for( ProdusComandat prod : lista )
			total += prod.getProdus().getPret() * prod.getCantitate() * ( 100 + prod.getTaxa() )/100;
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTaxe(){
		double total = 0;
		for( ProdusComandat prod : lista )
			total += prod.getTaxa();
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalTaraFaraTaxe(String tara){
		double total = 0;
		for( ProdusComandat prod : lista )
			if( prod.getProdus().getTaraOrigine().compareTo(tara) == 0 )
				total += prod.getProdus().getPret() * prod.getCantitate();
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalTaraCuTaxe(String tara){
		double total = 0;
		for( ProdusComandat prod : lista )
			if( prod.getProdus().getTaraOrigine().compareTo(tara) == 0 )
				total += prod.getProdus().getPret() * prod.getCantitate() * ( 100 + prod.getTaxa() )/100;
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalCategorieFaraTaxe(String categorie){
		double total = 0;
		for( ProdusComandat prod : lista )
			if( prod.getProdus().getCategorie().compareTo(categorie) == 0 )
				total += prod.getProdus().getPret() * prod.getCantitate();
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTotalCategorieCuTaxe(String categorie){
		double total = 0;
		for( ProdusComandat prod : lista )
			if( prod.getProdus().getCategorie().compareTo(categorie) == 0 )
				total += prod.getProdus().getPret() * prod.getCantitate() * ( 100 + prod.getTaxa() )/100;
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public double getTaxeTara(String tara){
		double total = 0;
		for( ProdusComandat prod : lista )
			if( prod.getProdus().getTaraOrigine().compareTo(tara) == 0 )
				total += prod.getTaxa();
		return Double.valueOf(df.format(total).replace(',', '.'));
	}
	
	public String toString(){
		return denumire + " " + lista;
	}

	@Override
	public int compareTo(Object o) {
		double total = ((Factura) o).getTotalCuTaxe();
		if( this.getTotalCuTaxe() > total )
			return 1;
		return -1;
	}
}
