package com.cartel.dao;

import java.math.BigDecimal;

public interface StatsDAO{
	public void checkCredit(String time);
	public void creditAddCash(BigDecimal sum);
	public void creditAddCB(BigDecimal sum);
	public void creditAddEcocup(BigDecimal sum);
	public void creditRemoveCash(BigDecimal sum);
	public void creditRemoveCB(BigDecimal sum);
	public void creditRemoveEcocup(BigDecimal sum);
	public void checkDebit(String time);
	public void debitAddAlcool(Integer sum);
	public void debitAddSoft(Integer sum);
	public void debitAddFood(Integer sum);
	public void debitAddOther(Integer sum);
	public void debitRemoveAlcool(Integer sum);
	public void debitRemoveSoft(Integer sum);
	public void debitRemoveFood(Integer sum);
	public void debitRemoveOther(Integer sum);
	public void checkPeople(String time);
	public void mealAddPerson(Integer sum);
	public void entryAddPerson(Integer sum);
	public String getCurrentTimeString();	
}
