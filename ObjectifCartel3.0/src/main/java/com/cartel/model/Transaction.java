package com.cartel.model;

import java.math.BigDecimal;

public abstract class Transaction {
	private Integer id;
	private String type;
	private Euros montant;
	private String dateTransaction;
	private boolean canceled;
	private Operateur operateur;
	private Client client;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Euros getEurosMontant() {
		return montant;
	}
	public BigDecimal getMontant(){
		return montant.toBigDecimal();
	}
	public void setEurosMontant(Euros montant) {
		this.montant = montant;
	}
	public void setMontant(BigDecimal montant){
		this.montant=new Euros(montant);
	}
	public String getDateTransaction() {
		return dateTransaction;
	}
	public void setDateTransaction(String dateTransaction) {
		this.dateTransaction = dateTransaction;
	}
	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	public Operateur getOperateur() {
		return operateur;
	}
	public void setOperateur(Operateur operateur) {
		this.operateur = operateur;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
}
