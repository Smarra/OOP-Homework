package tranzactionSystem;

public interface IMagazin {
	double getTotalFaraTaxe();
	double getTotalCuTaxe();
	double getTotalCuTaxeScutite();
	double getTotalTaraFaraTaxe(String tara);
	double getTotalTaraCuTaxe(String tara);
	double getTotalTaraCuTaxeScutite(String tara);
	double calculScutiriTaxe();
}
