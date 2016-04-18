package com.cartel.dao;

import java.util.List;

import com.cartel.model.Operateur;

public interface OperateurDAO extends GenericDAO<Operateur,Integer> {
	public List<Object[]> getPasswordAndAuths(String pseudo);
	public void setConnected(String pseudo,int role);
	public Operateur find(String pseudo);
	public List<Operateur> getConnectedOp();
}
