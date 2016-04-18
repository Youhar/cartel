package com.cartel.service;

import java.util.List;

import com.cartel.model.Operateur;


public interface OperateurService extends GenericService<Operateur,Integer>{
	public Operateur connect(String pseudo,String pswd,int role);
	public void disconnect(String pseudo);
	public List<Operateur> getConnectedOp();
}
