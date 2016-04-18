package com.cartel.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cartel.dao.StatsDAO;

@Service
public class StatsServiceImpl implements StatsService {

	private StatsDAO statsDAO;
	
	public StatsServiceImpl(){
		
	}
	
	@Autowired
	public StatsServiceImpl(@Qualifier("statsDAOImpl") StatsDAO statsDAO){
		this.statsDAO=statsDAO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void creditAddCash(BigDecimal sum) {
		statsDAO.creditAddCash(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void creditAddCB(BigDecimal sum) {
		statsDAO.creditAddCB(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void creditAddEcocup(BigDecimal sum) {
		statsDAO.creditAddEcocup(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void creditRemoveCash(BigDecimal sum) {
		statsDAO.creditRemoveCash(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void creditRemoveCB(BigDecimal sum) {
		statsDAO.creditRemoveCB(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void creditRemoveEcocup(BigDecimal sum) {
		statsDAO.creditRemoveEcocup(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void debitAddAlcool(Integer sum) {
		statsDAO.debitAddAlcool(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void debitAddSoft(Integer sum) {
		statsDAO.debitAddSoft(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void debitAddFood(Integer sum) {
		statsDAO.debitAddFood(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void debitRemoveAlcool(Integer sum) {
		statsDAO.debitRemoveAlcool(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void debitRemoveSoft(Integer sum) {
		statsDAO.debitRemoveSoft(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void debitRemoveFood(Integer sum) {
		statsDAO.debitRemoveFood(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void mealAddPerson(Integer sum) {
		statsDAO.mealAddPerson(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void entryAddPerson(Integer sum) {
		statsDAO.entryAddPerson(sum);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void debitAddOther(Integer sum) {
		statsDAO.debitAddOther(sum);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void debitRemoveOther(Integer sum) {
		statsDAO.debitRemoveOther(sum);
		
	}
}
