package com.cartel.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.TimeZone;

public class TransactAdmin extends Transaction {
	
	private boolean addition;
	
	public TransactAdmin(){
		this.setType("admin");
	}
	
	public TransactAdmin(final String date, final Operateur operateur, final Client client, final BigDecimal montant, final boolean addition,final boolean canceled){
		this.setDateTransaction(date);
		this.setOperateur(operateur);
		this.setClient(client);
		this.setMontant(montant);
		this.setCanceled(canceled);
		this.setAddition(addition);
		this.setType("admin");
	}
	
	public TransactAdmin(final Operateur operateur,final Client client, final Euros montant,final boolean addition, final boolean canceled){
		this(Calendar.getInstance(TimeZone.getDefault()).get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MINUTE)+"  "+Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH)+"/"+(Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH)+1), operateur, client, montant.toBigDecimal(),addition, canceled);	 
	}

	public boolean isAddition() {
		return addition;
	}

	public void setAddition(boolean addition) {
		this.addition = addition;
	}
	

}
