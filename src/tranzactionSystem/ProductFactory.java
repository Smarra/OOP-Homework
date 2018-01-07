package tranzactionSystem;

public class ProductFactory {
	public Magazin createMagazin( String idMarket ){
		switch (idMarket){
			case "MiniMarket":
				return new MiniMarket("MiniMarket");
			
			case "MediumMarket":
				return new MediumMarket("MediumMarket");
				
			case "HyperMarket":
				return new HyperMarket("HyperMarket");
		}
		return null;
	}
}
