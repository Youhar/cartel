package com.cartel.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class StatsDAOImpl implements StatsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session currentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void checkCredit(String time) {
		if(currentSession().createSQLQuery("SELECT * FROM stats_credit WHERE time='"+time+"'").list().size()>0){
			return;
		}else{
			currentSession().createSQLQuery("INSERT INTO stats_credit (time, cash, bank, ecocups) VALUES ('"+time+"', 0, 0, 0)").executeUpdate();
		}
	}

	@Override
	public void creditAddCash(BigDecimal sum) {
		String time = getCurrentTimeString();
		checkCredit(time);
		currentSession().createSQLQuery("UPDATE stats_credit SET cash = cash + "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void creditAddCB(BigDecimal sum) {
		String time = getCurrentTimeString();
		checkCredit(time);
		currentSession().createSQLQuery("UPDATE stats_credit SET bank = bank + "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void creditAddEcocup(BigDecimal sum) {
		String time = getCurrentTimeString();
		checkCredit(time);
		currentSession().createSQLQuery("UPDATE stats_credit SET ecocups = ecocups + "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void creditRemoveCash(BigDecimal sum) {
		String time = getCurrentTimeString();
		checkCredit(time);
		currentSession().createSQLQuery("UPDATE stats_credit SET cash = cash - "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void creditRemoveCB(BigDecimal sum) {
		String time = getCurrentTimeString();
		checkCredit(time);
		currentSession().createSQLQuery("UPDATE stats_credit SET bank = bank - "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void creditRemoveEcocup(BigDecimal sum) {
		String time = getCurrentTimeString();
		checkCredit(time);
		currentSession().createSQLQuery("UPDATE stats_credit SET ecocups = ecocups - "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}
	
	@Override
	public void checkDebit(String time) {
		if(currentSession().createSQLQuery("SELECT * FROM stats_debit WHERE time='"+time+"'").list().size()>0){
			return;
		}else{
			currentSession().createSQLQuery("INSERT INTO stats_debit (time, alcool, soft, food) VALUES ('"+time+"', 0, 0, 0)").executeUpdate();
		}
	}

	@Override
	public void debitAddAlcool(Integer sum) {
		String time = getCurrentTimeString();
		checkDebit(time);
		currentSession().createSQLQuery("UPDATE stats_debit SET alcool = alcool + "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void debitAddSoft(Integer sum) {
		String time = getCurrentTimeString();
		checkDebit(time);
		currentSession().createSQLQuery("UPDATE stats_debit SET soft = soft + "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void debitAddFood(Integer sum) {
		String time = getCurrentTimeString();
		checkDebit(time);
		currentSession().createSQLQuery("UPDATE stats_debit SET food = food + "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void debitRemoveAlcool(Integer sum) {
		String time = getCurrentTimeString();
		checkDebit(time);
		currentSession().createSQLQuery("UPDATE stats_debit SET alcool = alcool - "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void debitRemoveSoft(Integer sum) {
		String time = getCurrentTimeString();
		checkDebit(time);
		currentSession().createSQLQuery("UPDATE stats_debit SET soft = soft - "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void debitRemoveFood(Integer sum) {
		String time = getCurrentTimeString();
		checkDebit(time);
		currentSession().createSQLQuery("UPDATE stats_debit SET food = food - "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}
	
	@Override
	public void checkPeople(String time) {
		if(currentSession().createSQLQuery("SELECT * FROM stats_people WHERE time='"+time+"'").list().size()>0){
			return;
		}else{
			currentSession().createSQLQuery("INSERT INTO stats_people (time, entry, meal) VALUES ('"+time+"', 0, 0)").executeUpdate();
		}
	}

	@Override
	public void mealAddPerson(Integer sum) {
		String time = getCurrentTimeString();
		checkPeople(time);
		currentSession().createSQLQuery("UPDATE stats_people SET meal = meal + "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void entryAddPerson(Integer sum) {
		String time = getCurrentTimeString();
		checkPeople(time);
		currentSession().createSQLQuery("UPDATE stats_people SET entry = entry + "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public String getCurrentTimeString() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		return (cal.get(Calendar.MONTH)+1)+""+cal.get(Calendar.DAY_OF_MONTH)+"."+cal.get(Calendar.HOUR_OF_DAY);
	}

	@Override
	public void debitAddOther(Integer sum) {
		String time = getCurrentTimeString();
		checkDebit(time);
		currentSession().createSQLQuery("UPDATE stats_debit SET other = other + "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}

	@Override
	public void debitRemoveOther(Integer sum) {
		String time = getCurrentTimeString();
		checkDebit(time);
		currentSession().createSQLQuery("UPDATE stats_debit SET other = other - "+ sum + " WHERE time = '"+time+"'").executeUpdate();
	}
	
}
