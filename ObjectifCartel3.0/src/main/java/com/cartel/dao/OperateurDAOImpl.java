package com.cartel.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.cartel.model.Operateur;


@Repository
@SuppressWarnings("unchecked")
public class OperateurDAOImpl extends GenericDAOImpl<Operateur,Integer> implements OperateurDAO{

	@Override
	public List<Object[]> getPasswordAndAuths(String pseudo) {
		return currentSession().createSQLQuery("SELECT * FROM operateurs where pseudo= :pseudo")
			.addScalar("password",StringType.INSTANCE)
			.addScalar("auths",StringType.INSTANCE)
			.setParameter("pseudo", pseudo)
			.list();
	}

	@Override
	public void setConnected(String pseudo, int role) {
		currentSession().createQuery("update Operateur set connected= :co where pseudo = :pseudo")
			.setParameter("co", role)
			.setParameter("pseudo", pseudo)
			.executeUpdate();
	}

	@Override
	public Operateur find(String pseudo) {
		return (Operateur)currentSession().createCriteria(Operateur.class)
					.add(Restrictions.eq("pseudo", pseudo))
					.uniqueResult();
	}

	@Override
	public List<Operateur> getConnectedOp() {
		return currentSession().createQuery("From Operateur where connected != '0'")
				.list();
	}
	

}
