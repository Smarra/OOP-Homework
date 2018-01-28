package tranzactionSystem;

import java.util.ArrayList;

public class MiniMarket extends Magazin{

	public MiniMarket(String tip) {
		super(tip);
	}

	public double calculScutiriTaxe(){
		double total = getTotalCuTaxe();
		for( String tara : Gestiune.getInstance().tari )
		{
			if( this.getTotalTaraCuTaxe(tara) > (total/2) )
				return 10;
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
