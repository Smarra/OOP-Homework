package tranzactionSystem;

import java.util.ArrayList;

public class MediumMarket extends Magazin{

	public MediumMarket(String tip) {
		super(tip);
	}

	public double calculScutiriTaxe(){
		double total = this.getTotalCuTaxe();
		
		//Gasim lista categoriilor
		ArrayList<String> categorii = new ArrayList<>();
		for( Factura fact : lista )
			for( ProdusComandat prod : fact.lista )
			{
				String categorie = prod.getProdus().getTaraOrigine();
				if( !categorii.contains(categorie) )
				{
					categorii.add(categorie);
				}
			}
		for( String categorie : categorii )
		{
			double totalCategorie = 0;
			for( Factura fact : lista )
				for( ProdusComandat prod : fact.lista )
					if( prod.getProdus().getCategorie() == categorie )
						totalCategorie += prod.getProdus().getPret() * prod.getCantitate() * ( 100 + prod.getTaxa() )/100;
					
			if( totalCategorie > total/2 )
				return 5;
		}
		
		return 0;
	}

	public String toString(){
		return "MediumMarket " + nume + " " + lista;
	}
	
    public int compareTo(Object mag) {
        double pret = ((Magazin)mag).getTotalFaraTaxe();
        if( this.getTotalFaraTaxe() > pret )
        	return 1;
        return -1;
    }
	
}