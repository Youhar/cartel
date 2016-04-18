package com.cartel.service;

import java.io.File;

import com.cartel.model.Client;
import com.cartel.model.Euros;

public interface ClientService extends GenericService<Client, Integer>{
	public Client getWithoutPhoto(Integer id);
	public void saveSolde(Integer id, Euros solde);
	public void setPhoto(Integer id, byte[] photo);
	public void setPhotoFromFile(Integer id,File f);
	public Client getFromWristband(Integer wnb);
	public void saveWb(Integer clId, Integer wbId, boolean wbErase, boolean clientErase);
	public Integer getWristbandNb(Integer clId);
	public boolean wristbandExists(Integer wbId);
	public boolean hasWristband(Integer clId);
}
