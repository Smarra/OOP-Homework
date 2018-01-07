package tranzactionSystem;

import java.util.ArrayList;

public class MiniMarket extends Magazin{

	public MiniMarket(String tip) {
		super(tip);
	}

	public double calculScutiriTaxe(){
		double total = getTotalCuTaxe();
		
		//Gasim lista tarilor 
		ArrayList<String> tari = new ArrayList<>();
		for( Factura fact : lista )
			for( ProdusComandat prod : fact.lista )
			{
				String tara = prod.getProdus().getTaraOrigine();
				if( !tari.contains(tara) )
				{
					tari.add(tara);
				}
			}
		//Verificam totalul fiecarei 
		for( String tara : tari )
		{
			if( this.getTotalTaraCuTaxe(tara) > total/2 )
				return 0;
		}
		return 0;
	}
	
	public String toString(){
		return "MiniMarket " + nume + " " + lista;
	}
	
    public int compareTo(Object mag) {
        double pret = ((Magazin)mag).getTotalFaraTaxe();
        if( this.getTotalFaraTaxe() > pret )
        	return 1;
        return -1;
    }
}
