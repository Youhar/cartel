package com.cartel.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Euros {

	private int entier;
	private int decimale;
	private int centime;
	
	public Euros(int entier, int decimale, int centime) {
		this.entier = entier;
		this.decimale = decimale;
		this.centime = centime;
	}

	public Euros(double d){
		int temp = (int)(d*100);
		entier = (int) Math.floor(temp/100);
		temp-=entier*100;
		decimale = (int)(temp/10);
		temp-=decimale*10;
		centime=(int)temp;
	}
	
	public Euros(BigDecimal b){
		BigInteger temp = b.multiply(BigDecimal.TEN.pow(2)).toBigInteger();
		entier=temp.divide(BigInteger.TEN.pow(2)).intValue();
		decimale=temp.divideAndRemainder(BigInteger.TEN)[0].remainder(BigInteger.TEN).intValue();
		centime=temp.remainder(BigInteger.TEN).intValue();
	}
	
	@Override
	public String toString(){
		return   this.entier +"," + this.decimale + this.centime+" \u20AC";
	}
	
	public double toDouble(){
		return (this.entier*100+this.decimale*10+this.centime)/100;
	}
	
	public BigDecimal toBigDecimal(){
		return new BigDecimal(entier+"."+decimale+centime);
	}
	
	public static Euros multiply(Euros e, int i){
		return new Euros(e.toBigDecimal().multiply(new BigDecimal(i)));
	}
	
	public static Euros add(Euros e1, Euros e2){
		return new Euros(e1.toBigDecimal().add(e2.toBigDecimal()));
	}
	
	public static Euros substract(Euros e1,Euros e2){
		return new Euros(e1.toBigDecimal().subtract(e2.toBigDecimal()));
	}
	
	public boolean isSup(Euros e){
		if(this.toBigDecimal().compareTo(e.toBigDecimal())==1) return true;
		return false;
	}
	
	
	public int getEntier() {
		return entier;
	}

	public void setEntier(int entier) {
		this.entier = entier;
	}

	public int getDecimale() {
		return decimale;
	}

	public void setDecimale(int decimale) {
		this.decimale = decimale;
	}

	public int getCentime() {
		return centime;
	}

	public void setCentime(int centime) {
		this.centime = centime;
	}

	public static void main (String[] args){
    	Euros eu=new Euros(4583.6);
    	eu.toString();
    	System.out.println(eu);
    	System.out.println("Additionner 5,78");
    	
    	System.out.println((new BigDecimal((double)5544.50)).toString());

    /*	eu.additionner(eu);
    	eu.toString();
    	System.out.println(eu);	
    	System.out.println("Multiplier par 4");

    	eu.multiplier(4);
    	System.out.println(eu);	
    	Euros e=new Euros(3.1);
    	eu.soustraire(e);
    	System.out.println("Soustraire 3.1");
    	System.out.println(eu);	

    	System.out.println("Autre test");

    	Euros eur=new Euros(48.96);
    	System.out.println(eur);	
    	System.out.println(eur.centime);
    	Euros euro=new Euros(48.919);
    	System.out.println(euro);
*/
	}
	
}
