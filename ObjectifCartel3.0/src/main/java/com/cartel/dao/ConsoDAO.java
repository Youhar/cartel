package com.cartel.dao;

import java.util.List;

import com.cartel.model.Conso;
import com.cartel.model.Euros;

public interface ConsoDAO extends GenericDAO<Conso,Integer>{
	public Conso getWithoutPhoto(Integer id);
	public List<Conso> getAllWithoutPhoto();
	public void setPhoto(Integer id,byte[] photo);
}
