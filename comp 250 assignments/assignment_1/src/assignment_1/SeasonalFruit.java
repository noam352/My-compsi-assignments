package assignment_1;

public class SeasonalFruit extends Fruit {
	public SeasonalFruit(String prName, double weight, int pPkg) {
		super(prName, weight, pPkg);
	}
	public int getCost() {
		int SFprice=(int)(super.getCost()*0.85);
		return SFprice;
	}
}
