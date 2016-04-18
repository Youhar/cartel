package com.cartel.dao;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.cartel.model.Client;
import com.cartel.model.Euros;

@Repository
public class ClientDAOImpl extends GenericDAOImpl<Client,Integer> implements ClientDAO {

	@Override
	public Client getWithoutPhoto(Integer id) {
		 List al = currentSession().createCriteria(Client.class)
				.add(Restrictions.eq("id",id))
				.setProjection(Projections.projectionList()
				.add(Projections.property("id"),"id")
				.add(Projections.property("nom"),"nom")
				.add(Projections.property("prenom"),"prenom")
				.add(Projections.property("delegation"),"delegation")
				.add(Projections.property("role"),"role")
				.add(Projections.property("solde"),"solde"))
				.setResultTransformer(Transformers.aliasToBean(Client.class))
				.list();
		 if(al.isEmpty()){
			 return null;
		 }else{
			 return (Client)al.get(0);
		 }
	}

	@Override
	public void saveSolde(Integer id, Euros solde) {
		currentSession().createQuery("update Client set solde = :solde where id=:id")
		.setParameter("solde", solde.toBigDecimal())
		.setParameter("id", id)
		.executeUpdate();
		
	}

	@Override
	public void setPhoto(Integer id, byte[] photo) {
		currentSession().createQuery("update Client set photo = :photo where id=:id")
		.setParameter("photo", photo)
		.setParameter("id", id)
		.executeUpdate();
		
	}

	@Override
	public Integer getClientFromWristband(Integer wnb) {
		List ret = currentSession().createSQLQuery("SELECT client_id FROM wristbands WHERE wrist_id="+wnb).list();
		if(ret.size()==0){
			return -1;
		}else{
			return (Integer)ret.get(0);
		}
	}

	@Override
	public void saveWb(Integer clId, Integer wbId) {
		currentSession().createSQLQuery("INSERT INTO wristbands (client_id, wrist_id) VALUES ("+clId+", "+wbId+")").executeUpdate();
	}

	@Override
	public Integer getWristbandNb(Integer clId) {
		List ret = currentSession().createSQLQuery("SELECT wrist_id FROM wristbands WHERE client_id="+clId).list();
		if(ret.size()==0){
			return -1;
		}else{
			return (Integer)ret.get(0);
		}
	}

	@Override
	public void wb_eraseClient(Integer clId) {
		currentSession().createSQLQuery("DELETE FROM wristbands WHERE client_id="+clId).executeUpdate();
		
	}

	@Override
	public void wb_eraseWristband(Integer wbId) {
		currentSession().createSQLQuery("DELETE FROM wristbands WHERE wrist_id="+wbId).executeUpdate();	
	}
	

}
