package tranzactionSystem;

import java.util.Vector;

public class Factura {
	String denumire;
	Vector<ProdusComandat> lista;
	
	double getTotalFaraTaxe(){
		return 0.0;
	}
	
	double getTotalCuTaxe(){
		return 0;
	}
	
	double getTaxe(){
		return 0;
	}
	
	double getTotalTaraFaraTaxe(String tara){
		return 0;
	}
	
	double getTaxeTara(String tara){
		return 0;
	}
}
