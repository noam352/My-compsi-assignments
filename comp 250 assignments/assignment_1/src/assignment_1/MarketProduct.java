package assignment_1;

public abstract class MarketProduct {
	private String prName;
	
	public MarketProduct (String prName) {
		this.prName = prName;
		}
	public final String getName() {
		return this.prName;
		}
	public abstract int getCost();
	public abstract boolean equals(Object obj);
		
}
