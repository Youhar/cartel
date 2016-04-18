package com.cartel.dao;

import org.springframework.stereotype.Repository;

import com.cartel.model.Transaction;

@Repository
public class TransactionDAOImpl extends GenericDAOImpl<Transaction,Integer> implements TransactionDAO {

}
