package assignment_1;

public class Egg extends MarketProduct {
	private int numEgg;
	private int priceEgg;

	public Egg(String prName, int numEgg,int priceEgg) {
		super(prName);
		this.numEgg = numEgg;
		this.priceEgg = priceEgg;
	}
	public int getCost() {
		int price = (int)((this.priceEgg*this.numEgg)/12);
		return price;
	}
	public boolean equals(Object obj) {

	if (obj instanceof Egg && this.getName().equals(((Egg)obj).getName()) && this.numEgg==((Egg)obj).numEgg && this.priceEgg==((Egg)obj).priceEgg) {
		return true;
		}else {
		return false;
		}
	}

}
