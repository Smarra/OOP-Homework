package tranzactionSystem;

public class ProdusComandat {
	private Produs produs;
	private double taxa;
	private int cantitate;
	
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
}
