package tranzactionSystem;

public class ProductFactory {
	public Magazin createMagazin( String idMarket ){
		switch (idMarket){
			case "MiniMarket":
				return new MiniMarket();
			
			case "MediumMarket":
				return new MediumMarket();
				
			case "HyperMarket":
				return new HyperMarket();
		}
		return null;
	}
}
