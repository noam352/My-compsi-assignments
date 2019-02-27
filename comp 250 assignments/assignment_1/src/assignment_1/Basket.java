package assignment_1;

public class Basket {
	
	private MarketProduct[] Basket;
	

	public Basket() {
		this.Basket = new MarketProduct[0];
	}
	public MarketProduct[] getProducts() {
		MarketProduct[] temp;
		temp = new MarketProduct[this.Basket.length];
		for (int i=0; i < temp.length; i++) {
			temp[i]=this.Basket[i];
		}
		return temp;
	}
	public void add(MarketProduct e) {
		MarketProduct[] temp = new MarketProduct[this.Basket.length+1];
		for (int i=0; i < this.Basket.length; i++) {
			temp[i]=this.Basket[i];
			}
		this.Basket=temp;
		this.Basket[this.Basket.length-1]=e;
	}
	public boolean remove(MarketProduct b) {
		MarketProduct[] temp;
		for (int i=0; i<=this.Basket.length-1;i++) {
			if (this.Basket[i].equals(b) && !(i==this.Basket.length-1)) {
				for (int p=i; p<this.Basket.length-1;p++) {
					this.Basket[p]=this.Basket[p+1];
				}
				temp = new MarketProduct[this.Basket.length-1];
				for (int g=0; g < this.Basket.length-1; g++) {
					temp[g]=this.Basket[g];
					}
				this.Basket=temp;
				return true;
				
			}else if (this.Basket[i].equals(b) && (i==this.Basket.length-1)) {
				temp = new MarketProduct[this.Basket.length-1];
				for (int t=0; t < this.Basket.length-1; t++) {
					temp[t]=this.Basket[t];
				}
				this.Basket=temp;
				return true;
				
			}else if (i==this.Basket.length) {
				return false;
				
			}else {
				continue;
				
			}
		}return false;//will never run
	}
	public void clear() {
		this.Basket = new MarketProduct[0];
	}
	public int getNumOfProducts() {
		return (this.Basket.length);
	}
	public int getSubTotal() {
		int sum=0;
		for (int q=0; q<this.Basket.length;q++) {
			sum=sum+this.Basket[q].getCost();
		}
		return sum;
	}
	public int getTotalTax() {
		int sumTax=0;
		for (int z=0; z<this.Basket.length;z++) {
			if (this.Basket[z] instanceof Jam) {
				sumTax=sumTax + (int)(this.Basket[z].getCost()*0.15);
			}
		}
		return sumTax;
	}
	public int getTotalCost() {
		return (this.getSubTotal() + this.getTotalTax());
	}
	public String toString() {
		String rec="";
		for (int i=0;i<this.Basket.length;i++) {
			if (this.Basket[i].getCost()<=0) {
				rec=rec+this.Basket[i].getName()+"\t"+"_"+"\n";
			}else if(this.Basket[i].getCost()>0) {
			rec=rec+this.Basket[i].getName()+"\t"+this.Basket[i].getCost()+"\n";
			}
		}
		if (this.getTotalTax()>0) {
			rec=rec+"\nSubtotal\t"+this.getSubTotal()+"\nTotal tax\t"+this.getTotalTax()+"\n\nTotalCost\t"+this.getTotalCost()+"\n";
		}else {
			rec=rec+"\nSubtotal\t"+this.getSubTotal()+"\nTotal tax\t"+"_"+"\n\nTotalCost\t"+this.getTotalCost()+"\n";
		}
		return rec;
	}
}
