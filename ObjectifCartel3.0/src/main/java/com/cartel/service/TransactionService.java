package com.cartel.service;

import java.util.List;

import com.cartel.model.Transaction;

public interface TransactionService extends GenericService<Transaction,Integer>{
	
	public void reverseTransaction(Transaction t);
	
	public void addTransactionLocal(Transaction t);
	
	public void clearTransactionLocal();
	
	public List<Transaction> getTransactionLocal();
	
}
