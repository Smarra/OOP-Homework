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
	
	public Produs(Produs prod){
		this.denumire = prod.denumire;
		this.categorie = prod.categorie;
		this.taraOrigine = prod.taraOrigine;
		this.pret = prod.pret;
	}
	
	public void setDenumire(String denumire){
		this.denumire = denumire;
	}
	
	public String getDenumire(){
		return denumire;
	}
	
	public void setCategorie(String categorie){
		this.categorie = categorie;
	}
	
	public String getCategorie(){
		return categorie;
	}
	
	public void setTaraOrigine(String taraOrigine){
		this.taraOrigine = taraOrigine;
	}
	
	public String getTaraOrigine(){
		return taraOrigine;
	}
	
	public void setPret(double pret){
		this.pret = pret;
	}
	
	public double getPret(){
		return pret;
	}
	
	public String toString(){
		return denumire + " " + categorie + " " + taraOrigine + " " + pret;
	}
}
