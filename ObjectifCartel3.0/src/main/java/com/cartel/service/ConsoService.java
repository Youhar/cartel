package com.cartel.service;

import java.io.File;
import java.util.List;

import com.cartel.model.Conso;

public interface ConsoService extends GenericService<Conso,Integer>{
	public Conso getWithoutPhoto(Integer id);
	public void setPhoto(Integer id, byte[] photo);
	public void setPhotoFromFile(Integer id,File f);
	public List<Conso>getAllWithoutPhoto();
	
}
