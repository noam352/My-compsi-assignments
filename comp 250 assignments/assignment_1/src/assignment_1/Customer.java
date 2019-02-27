package assignment_1;

import java.lang.IllegalArgumentException;
public class Customer {
	private String name;
	private int balance;
	private Basket basket;
	 
	public Customer(String name, int balance) {
		this.basket=new Basket();
		this.name=name;
		this.balance=balance;
		
	}
	public int getBalance() {
		return this.balance;
	}
	public String getName() {
		return this.name;
	}
	public Basket getBasket() {
		return this.basket;
	}
	public void addFunds(int a) {
		try{
			if(a<0){
				
				throw new IllegalArgumentException();
			}else{
				this.balance=this.balance+a;
			}
		}catch(IllegalArgumentException e){
			System.out.print("you must input a positive number\n");
		}	
	}
	public void addToBasket(MarketProduct obj) {
		this.basket.add(obj);
	}
	public boolean removeFromBasket(MarketProduct obj2) {
		return this.basket.remove(obj2);
	}
	public String checkOut() {
		try{
			if (this.getBalance()<this.basket.getTotalCost()) {
				throw new IllegalStateException();
			}else {
				String temp = this.basket.toString();
			this.balance=this.balance-this.basket.getTotalCost();
			this.basket.clear();
			//System.out.print(temp);
			return temp;
			}
		}catch (IllegalStateException e) {
			return "-dasdsa";
		}
	
	}
}
