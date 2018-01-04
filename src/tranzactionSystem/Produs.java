package tranzactionSystem;

public class Produs {
	private String denumire;
	private String categorie;
	private String taraOrigine;
	private double pret;
	
	public Produs(String denumire, String categorie, String taraOrigine, double pret){
		this.denumire = denumire;
		this.categorie = categorie;
		this.taraOrigine = taraOrigine;
		this.pret = pret;
	}
	
	void setDenumire(String denumire){
		this.denumire = denumire;
	}
	
	String getDenumire(){
		return denumire;
	}
	
	void setCategorie(String categorie){
		this.categorie = categorie;
	}
	
	String getCategorie(){
		return categorie;
	}
	
	void setTaraOrigine(String taraOrigine){
		this.taraOrigine = taraOrigine;
	}
	
	String getTaraOrigine(){
		return taraOrigine;
	}
	
	void setPret(double pret){
		this.pret = pret;
	}
	
	double getPret(){
		return pret;
	}
	
	public Produs(){
		
	}
	
	public String toString(){
		return denumire + " " + categorie + " " + taraOrigine + " " + pret;
	}
}
