package com.cartel.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.TimeZone;

public class Credit extends Transaction {
	
	/**
	 * Values : "C" for Cash, "B" for CreditCard, "E" for EcoCup return
	 */
	private String mode;
	
	
	public Credit(){
		this.setType("credit");
	}
	
	public Credit(final String date, final Operateur operateur, final Client client, final BigDecimal montant, final boolean canceled, final String mode){
		this.setDateTransaction(date);
		this.setOperateur(operateur);
		this.setClient(client);
		this.setMontant(montant);
		this.setCanceled(canceled);
		this.setMode(mode);
		this.setType("credit");
	}
	
	public Credit(final Operateur operateur, final Client client, final Euros montant, final boolean canceled, final String mode){
		this(Calendar.getInstance(TimeZone.getDefault()).get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MINUTE)+"  "+Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH)+"/"+(Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH)+1), operateur, client, montant.toBigDecimal(), canceled,mode);	 
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
