package com.cartel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cartel.dao.ConsoDAO;
import com.cartel.dao.GenericDAO;
import com.cartel.model.Conso;

@Service
public class ConsoServiceImpl extends GenericServiceImpl<Conso, Integer> implements ConsoService {

	private ConsoDAO consoDAO;
	
	public ConsoServiceImpl(){
		
	}
	
	@Autowired
	public ConsoServiceImpl(@Qualifier("consoDAOImpl") GenericDAO<Conso, Integer> genericDAO){
		super(genericDAO);
		this.consoDAO=(ConsoDAO) genericDAO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Conso find(Integer id){
		if (Conso.photoExists(id)){
			return consoDAO.getWithoutPhoto(id);
		}
		System.out.println("Pas deja PHOTO ?");
		
		Conso conso = consoDAO.find(id);
		
		if(conso==null){
			System.out.println("return null");
			return null;
		}
        if(conso.getPhoto()!=null){
    		try{
                FileOutputStream fos = new FileOutputStream(Conso.getPhotoPath(id)); 
                fos.write(conso.getPhoto());
                fos.close();
                System.out.println("setting photo...");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
		return conso;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Conso> getAll(){
		List<Conso> temp = getAllWithoutPhoto();
		for (Conso c:temp){
			if(!Conso.photoExists(c.getId())){
				Conso conso = consoDAO.find(c.getId());
				if(conso.getPhoto()!=null){
		    		try{
		                FileOutputStream fos = new FileOutputStream(Conso.getPhotoPath(c.getId())); 
		                fos.write(conso.getPhoto());
		                fos.close();
		            }catch(Exception e){
		                e.printStackTrace();
		            }
		        }
			}
		}
		return temp;
	}
	
	@Override
	public Conso getWithoutPhoto(Integer id) {
		return consoDAO.getWithoutPhoto(id);
	}

	@Override
	public void setPhoto(Integer id, byte[] photo) {
		consoDAO.setPhoto(id, photo);
	}

	@Override
	public void setPhotoFromFile(Integer id, File f) {
    	File file = f;
        byte[] bFile = new byte[(int) file.length()];
        try {
	     FileInputStream fileInputStream = new FileInputStream(file);
	     //convert file into array of bytes
	     fileInputStream.read(bFile);
	     fileInputStream.close();
        } catch (Exception e) {
	     e.printStackTrace();
        }
		consoDAO.setPhoto(id, bFile);
	}

	@Override
	public List<Conso> getAllWithoutPhoto() {
		return consoDAO.getAllWithoutPhoto();
	}
}
