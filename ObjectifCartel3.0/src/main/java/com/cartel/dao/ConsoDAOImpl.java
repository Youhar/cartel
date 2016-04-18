package com.cartel.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.cartel.model.Conso;

@Repository
public class ConsoDAOImpl extends GenericDAOImpl<Conso,Integer> implements ConsoDAO {

	@Override
	public Conso getWithoutPhoto(Integer id) {
		List al = currentSession().createCriteria(Conso.class)
				.add(Restrictions.eq("id",id))
				.setProjection(Projections.projectionList()
				.add(Projections.property("id"),"id")
				.add(Projections.property("nom"),"nom")
				.add(Projections.property("prix"),"prix")
				.add(Projections.property("type"),"type"))
				.setResultTransformer(Transformers.aliasToBean(Conso.class))
				.list();
		 if(al.isEmpty()){
			 return null;
		 }else{
			 return (Conso)al.get(0);
		 }
	}

	@Override
	public List<Conso> getAllWithoutPhoto() {
		List al = currentSession().createCriteria(Conso.class)
				.setProjection(Projections.projectionList()
				.add(Projections.property("id"),"id")
				.add(Projections.property("nom"),"nom")
				.add(Projections.property("prix"),"prix")
				.add(Projections.property("type"),"type"))
				.setResultTransformer(Transformers.aliasToBean(Conso.class))
				.list();
		
		List<Conso> result = new ArrayList<Conso>();
		for(Object conso:al){
			result.add((Conso)conso);
		}
		return result;
	}

	@Override
	public void setPhoto(Integer id, byte[] photo) {
		currentSession().createQuery("update Conso set photo = :photo where id=:id")
		.setParameter("photo", photo)
		.setParameter("id", id)
		.executeUpdate();
	}

}
