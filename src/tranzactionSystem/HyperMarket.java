package tranzactionSystem;

public class HyperMarket extends Magazin{

	public HyperMarket(String tip) {
		super(tip);
	}

	public double calculScutiriTaxe(){
		double total = this.getTotalCuTaxe();
		for( Factura fact : lista )
			if( fact.getTotalCuTaxe() > total/10 )
				return 1;
		return 0;
	}
	
	public String toString(){
		return "HyperMarket " + nume + " " + lista;
	}
	
    public int compareTo(Object mag) {
        double pret = ((Magazin)mag).getTotalFaraTaxe();
        if( this.getTotalFaraTaxe() > pret )
        	return 1;
        return -1;
    }
	
}