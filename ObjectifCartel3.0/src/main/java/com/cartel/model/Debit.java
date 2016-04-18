package com.cartel.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.TimeZone;

public class Debit extends Transaction{
	
	private int bar;
	private String panier;
	private int[] stats_qtes;

	public Debit(){
		this.setType("debit");
	}
	
	public Debit(final String date, final Operateur operateur, final int bar, final Client client, final BigDecimal montant,final String panier, final boolean canceled){
		this.setDateTransaction(date);
		this.setOperateur(operateur);
		this.setClient(client);
		this.setMontant(montant);
		this.setCanceled(canceled);
		this.setPanier(panier);
		this.setBar(bar);
		this.setType("debit");
	}
	
	public Debit(final Operateur operateur, final int bar,final Client client, final Euros montant,final String panier, final boolean canceled){
		this(Calendar.getInstance(TimeZone.getDefault()).get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MINUTE)+"  "+Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH)+"/"+(Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH)+1), operateur,bar, client, montant.toBigDecimal(),panier, canceled);	 
	}

	public int getBar() {
		return bar;
	}

	public void setBar(int bar) {
		this.bar = bar;
	}

	public String getPanier() {
		return panier;
	}

	public void setPanier(String panier) {
		this.panier = panier;
	}

	public int[] getStats_qtes() {
		return stats_qtes;
	}

	public void setStats_qtes(int[] stats_qtes) {
		this.stats_qtes = stats_qtes;
	}
	
	
	
}
