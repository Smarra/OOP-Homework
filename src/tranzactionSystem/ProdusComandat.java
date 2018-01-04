package tranzactionSystem;

public class ProdusComandat {
	private Produs produs;
	private double taxa;
	private int cantitate;
	
	public ProdusComandat(Produs produs, double taxa, int cantitate){
		this.produs = new Produs(produs);
		this.taxa = taxa;
		this.cantitate = cantitate;
	}
	
	void setProdus(Produs produs){
		this.produs = produs;
	}
	
	Produs getProdus(){
		return produs;
	}
	
	void getTaxa(double taxa){
		this.taxa = taxa;
	}
	
	double getTaxa(){
		return taxa;
	}
	
	void setCantitate(int cantitate){
		this.cantitate = cantitate;
	}
	
	int getCantitate(){
		return cantitate;
	}
	
	public String toString(){
		return " Taxa:" + taxa + " Cantitate:" + cantitate;
	}
}
