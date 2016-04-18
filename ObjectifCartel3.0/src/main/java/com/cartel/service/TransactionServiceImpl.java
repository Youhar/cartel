package com.cartel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cartel.dao.GenericDAO;
import com.cartel.dao.StatsDAO;
import com.cartel.dao.TransactionDAO;
import com.cartel.model.Client;
import com.cartel.model.Credit;
import com.cartel.model.Debit;
import com.cartel.model.Euros;
import com.cartel.model.TransactAdmin;
import com.cartel.model.Transaction;

@Service
public class TransactionServiceImpl extends GenericServiceImpl<Transaction, Integer> implements TransactionService {

	private TransactionDAO transactionDAO;
	
	@Autowired
	private ClientService clientServiceImpl;
	
	@Autowired
	private StatsDAO statsDAOImpl;
	
	private List<Transaction> transactionsLocales;
	
	public TransactionServiceImpl(){
		transactionsLocales=new ArrayList();
	}
	
	@Autowired
	public TransactionServiceImpl(@Qualifier("transactionDAOImpl") GenericDAO<Transaction, Integer> genericDAO){
		super(genericDAO);
		this.transactionDAO=(TransactionDAO) genericDAO;
		transactionsLocales=new ArrayList();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer add(Transaction t){
		switch (t.getType()){
		case "credit":
			switch(((Credit)t).getMode()){
			case "C":
				statsDAOImpl.creditAddCash(t.getMontant());
				break;
			case "B":
				statsDAOImpl.creditAddCB(t.getMontant());
				break;
			case "E":
				statsDAOImpl.creditAddEcocup(t.getMontant());
				break;
			}
			break;
		case "debit":
			if(((Debit)t).getStats_qtes()[0]>0){
				statsDAOImpl.debitAddAlcool(((Debit)t).getStats_qtes()[0]);
			}
			if(((Debit)t).getStats_qtes()[1]>0){
				statsDAOImpl.debitAddSoft(((Debit)t).getStats_qtes()[1]);
			}
			if(((Debit)t).getStats_qtes()[2]>0){
				statsDAOImpl.debitAddFood(((Debit)t).getStats_qtes()[2]);
			}
			if(((Debit)t).getStats_qtes()[3]>0){
				statsDAOImpl.debitAddOther(((Debit)t).getStats_qtes()[3]);
			}
		}
		return transactionDAO.add(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void reverseTransaction(Transaction t) {
		if(t.isCanceled()){
			return;
		};
		t.setCanceled(true);
		Client client=t.getClient();
		switch(t.getType()){
		case "debit":
			client.setEurosSolde(Euros.add(client.getEurosSolde(), t.getEurosMontant()));
			break;
		case "credit":
			client.setEurosSolde(Euros.substract(client.getEurosSolde(), t.getEurosMontant()));
			switch(((Credit)t).getMode()){
			case "C":
				statsDAOImpl.creditRemoveCash(t.getMontant());
				break;
			case "B":
				statsDAOImpl.creditRemoveCB(t.getMontant());
				break;
			case "E":
				statsDAOImpl.creditRemoveEcocup(t.getMontant());
				break;
			}
			break;
		case "admin":
			TransactAdmin ta=(TransactAdmin)t;
			if(ta.isAddition()){
				client.setEurosSolde(Euros.substract(client.getEurosSolde(), ta.getEurosMontant()));
			}else{
				client.setEurosSolde(Euros.add(client.getEurosSolde(), ta.getEurosMontant()));
			}
		}
		clientServiceImpl.saveSolde(client.getId(), client.getEurosSolde());
		transactionDAO.saveOrUpdate(t);
	}

	@Override
	public void addTransactionLocal(Transaction t) {
		transactionsLocales.add(t);		
	}

	@Override
	public void clearTransactionLocal() {
		transactionsLocales=new ArrayList();
		
	}

	@Override
	public List<Transaction> getTransactionLocal() {
		return transactionsLocales;
	}
}
