package assignment_1;

public class Fruit extends MarketProduct {
	private double weight;
	private int pPkg;
	
	public Fruit(String prName, double weight, int pPkg) {
		super(prName);
		this.weight=weight;
		this.pPkg=pPkg;
	}
	public int getCost() {
		int Fprice=(int)(this.pPkg*this.weight);
		return Fprice;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Fruit && this.getName().equals(((Fruit)obj).getName()) && this.weight==((Fruit)obj).weight && this.pPkg==((Fruit)obj).pPkg) {
		return true;
		}else {
		return false;
		}
	}
}