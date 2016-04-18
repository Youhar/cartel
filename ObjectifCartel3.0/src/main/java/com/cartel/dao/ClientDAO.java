package com.cartel.dao;

import com.cartel.model.Client;
import com.cartel.model.Euros;

public interface ClientDAO extends GenericDAO<Client,Integer>{
	public Integer getClientFromWristband(Integer wnb);
	public Client getWithoutPhoto(Integer id);
	public void saveSolde(Integer id, Euros solde);
	public void setPhoto(Integer id,byte[] photo);
	public void saveWb(Integer clId, Integer wbId);
	public Integer getWristbandNb(Integer clId);
	public void wb_eraseClient(Integer clId);
	public void wb_eraseWristband(Integer wbId);
}
