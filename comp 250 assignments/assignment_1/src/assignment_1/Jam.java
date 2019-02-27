package assignment_1;

public class Jam extends MarketProduct {
	private int nOj;
	private int pPj;
	
	public Jam(String prName, int nOj, int pPj) {
		super(prName);
		this.nOj=nOj;
		this.pPj=pPj;
	}
	@Override
	public int getCost() {
		int jPrice= this.nOj*this.pPj;
		return jPrice;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Jam && this.getName().equals(((Jam)obj).getName()) && this.nOj==((Jam)obj).nOj && this.pPj==((Jam)obj).pPj) {
			return true;
			}else {
			return false;
			}
	}
}
