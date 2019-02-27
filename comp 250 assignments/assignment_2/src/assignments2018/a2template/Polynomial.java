package assignments2018.a2template;

import java.math.BigInteger;

//import assignments2018.a2template.SLinkedList.SNode;
//import java.lang.Iterable;
//import java.util.Iterator;

public class Polynomial 
{
	private SLinkedList<Term> polynomial;
	//private BigInteger zero=new BigInteger("0");
	public int size()
	{	
		return polynomial.size();
	}
	private Polynomial(SLinkedList<Term> p)
	{
		polynomial = p;
	}
	
	public Polynomial()
	{
		polynomial = new SLinkedList<Term>();
	}
	
	// Returns a deep copy of the object.
	public Polynomial deepClone()
	{	
		return new Polynomial(polynomial.deepClone());
	}
	
	/* 
	 * TODO: Add new term to the polynomial. Also ensure the polynomial is
	 * in decreasing order of exponent. (DONEEE)
	 */
	public void addTerm(Term t)
	{	
		int pos=0;
		SLinkedList<Term> temp = new SLinkedList<Term>();
		boolean check=false;
		//temp.show();
		if (t.getCoefficient().intValue()==0 || t.getExponent()< 0) {
			System.out.println("please use a valid input");
			return;  //code stops if input is not valid. used big int methods here!!!
		}
		//System.out.println("hi");
		if (this.polynomial.size() == 0){
			this.polynomial.addFirst(t);
			
		}else {
			for (Term term: this.polynomial) {
				pos++;
				if (t.getExponent()>term.getExponent() && check==false) {
					temp.addLast(t);
					check = true;
					//System.out.println("1 happened");
					//temp.show();
				}
				if (t.getExponent()==term.getExponent() && check==false) {
					if (t.getCoefficient().add(term.getCoefficient()).equals(new BigInteger("0"))) {
						check = true;
						//System.out.println("2 happened");
						continue;
						
					}else {
						Term temTerm=term;
						temTerm.setCoefficient(t.getCoefficient().add(term.getCoefficient()));
						temp.addLast(temTerm);
						//temp.show();
						check = true;
						continue;
					}
					
				}else if(pos==this.polynomial.size() && check == false){
					temp.addLast(term);
					temp.addLast(t);
					}
				else{
					temp.addLast(term);
				}
					//temp.show();
				
				
			}
			//temp.show();
			this.polynomial=temp;
		}
		//this.polynomial.show();
	}
	
	public Term getTerm(int index)
	{
		return polynomial.get(index);
	}
	
	//TODO: Add two polynomial without modifying either (DONEEEEE)
	public static Polynomial add(Polynomial p1, Polynomial p2)
	{
		SLinkedList<Term> temp2= new SLinkedList<Term>();
		Polynomial temP =  new Polynomial();
		Polynomial temP1= p1.deepClone();
		Polynomial temP2= p2.deepClone();
		while(temP1.size()>0 && temP2.size()>0) {
			
			if (temP1.polynomial.get(0).getExponent()==temP2.polynomial.get(0).getExponent()) {
				if (temP1.polynomial.get(0).getCoefficient().add(temP2.polynomial.get(0).getCoefficient()).equals(new BigInteger("0"))){
					temP1.polynomial.removeFirst();
					temP2.polynomial.removeFirst();
					continue;
				}else {
					Term temTerm2= temP1.polynomial.get(0);
					temTerm2.setCoefficient(temP1.polynomial.get(0).getCoefficient().add(temP2.polynomial.get(0).getCoefficient()));
					temp2.addLast(temTerm2);
					temP1.polynomial.removeFirst();
					temP2.polynomial.removeFirst();
					continue;
				}
			}else if (temP1.polynomial.get(0).getExponent() > temP2.polynomial.get(0).getExponent()) {
				temp2.addLast(temP1.polynomial.get(0));
				temP1.polynomial.removeFirst();
				continue;
			}else {
				temp2.addLast(temP2.polynomial.get(0));
				temP2.polynomial.removeFirst();
				continue;
			}
		}
		while (temP1.size()>0 && !(temP2.size()>0)) {
			
			temp2.addLast(temP1.polynomial.get(0));
			temP1.polynomial.removeFirst();
		}
		while (!(temP1.size()>0) && temP2.size()>0) {
			
			temp2.addLast(temP2.polynomial.get(0));
			temP2.polynomial.removeFirst();
		}
		
		temP.polynomial=temp2;
		return temP;
	}
	
	//TODO: multiply this polynomial by a given term.
	private void multiplyTerm(Term t)
	{	
		if (t.getCoefficient().compareTo(new BigInteger("0"))==0) {
			this.polynomial.clear();
			return;
		}
		for (Term term: this.polynomial) {
			term.setCoefficient(term.getCoefficient().multiply(t.getCoefficient()));
			term.setExponent(term.getExponent()+t.getExponent());
			
		}//this.showing();
		
	}
	
	//TODO: multiply two polynomials
	public static Polynomial multiply(Polynomial p1, Polynomial p2)
	{
		Polynomial poly1= new Polynomial();
		if (p1.size()==0 || p2.size()==0) {
			return poly1;
		}
		for (Term t:p1.polynomial) {
			Polynomial poly2 = p2.deepClone();
			poly2.multiplyTerm(t);
			poly1=add(poly1, poly2);
		}
		
		return poly1;
	}
	
	//TODO: evaluate this polynomial.
	// Hint:  The time complexity of eval() must be order O(m), 
	// where m is the largest degree of the polynomial. Notice 
	// that the function SLinkedList.get(index) method is O(m), 
	// so if your eval() method were to call the get(index) 
	// method m times then your eval method would be O(m^2).
	// Instead, use a Java enhanced for loop to iterate through 
	// the terms of an SLinkedList.

	public BigInteger eval(BigInteger x)
	{
		SLinkedList<Term> tempoP=this.polynomial.deepClone();
		
		
		if (tempoP.size()==0) {
			return new BigInteger("0");
		}
		BigInteger result = tempoP.get(0).getCoefficient();
		//tempoP.show();
		
		if (tempoP.size()==1) {
			for (int i=0;i<tempoP.get(0).getExponent();i++) {
				result =result.multiply(x);
			}
			return result;
		}
		while(tempoP.size()-1 > 0) {
			//System.out.println("inside");
			int prev = tempoP.get(0).getExponent();
			tempoP.removeFirst();
			if (prev-tempoP.get(0).getExponent()==1) {
				result = result.multiply(x).add(tempoP.get(0).getCoefficient());
				//System.out.println(result.toString()+"me");
				if (tempoP.size()==1 && tempoP.get(0).getExponent()>0) {
					//System.out.println("sup");
					for (int i=0; i<tempoP.get(0).getExponent();i++){
						result = result.multiply(x);
					}//System.out.println(result.toString()+"me3");
					return result;
				}
				continue;
				
				
			}
			else {
				for (int i=0; i<prev-tempoP.get(0).getExponent();i++){
					result = result.multiply(x);
				}result = result.add(tempoP.get(0).getCoefficient());
				//System.out.println(result.toString()+"me2");
				if (tempoP.size()==1 && tempoP.get(0).getExponent()>0) {
					for (int i=0; i<tempoP.get(0).getExponent();i++){
						result = result.multiply(x);
					}
					
					return result;
				}
				continue;
				
			}
			
		}
		//System.out.println(result.toString());
		return result;
	}
	
	// Checks if this polynomial is same as the polynomial in the argument
	public boolean checkEqual(Polynomial p)
	{	
		if (polynomial == null || p.polynomial == null || size() != p.size())
			return false;
		
		int index = 0;
		for (Term term0 : polynomial)
		{
			Term term1 = p.getTerm(index);
			
			if (term0.getExponent() != term1.getExponent() ||
				term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)
					return false;
			
			index++;
		}
		return true;
	}
	
	// This method blindly adds a term to the end of LinkedList polynomial. 
	// Avoid using this method in your implementation as it is only used for testing.
	public void addTermLast(Term t)
	{	
		polynomial.addLast(t);
		//this.polynomial.show();
	}
	
	// This is used for testing multiplyTerm
	public void multiplyTermTest(Term t)
	{
		multiplyTerm(t);
	}
	
	public void showing() {
		this.polynomial.show();
	}
	@Override
	
	
	public String toString()
	{	
		if (polynomial.size() == 0) return "0";
		return polynomial.toString();
	}
}
