package com.cartel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cartel.dao.ClientDAO;
import com.cartel.dao.GenericDAO;
import com.cartel.model.Client;
import com.cartel.model.Euros;

@Service
public class ClientServiceImpl extends GenericServiceImpl<Client, Integer> implements ClientService{
	
	private ClientDAO clientDAO;
	
	@Autowired
	private ResourceService resourceServiceImpl;
	
	public ClientServiceImpl(){
		
	}
	
	@Autowired
	public ClientServiceImpl(@Qualifier("clientDAOImpl") GenericDAO<Client, Integer> genericDAO){
		super(genericDAO);
		this.clientDAO =(ClientDAO) genericDAO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Client find(Integer id){
		if (Client.photoExists(id)){
			return clientDAO.getWithoutPhoto(id);
		}
		System.out.println("Pas deja PHOTO ?");
		
		Client client = clientDAO.find(id);
		
		if(client==null){
			System.out.println("return null");
			return null;
		}
        if(client.getPhoto()!=null){
    		try{
                FileOutputStream fos = new FileOutputStream(Client.getPhotoPath(id)); 
                fos.write(client.getPhoto());
                fos.close();
                System.out.println("setting photo...");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
		return client;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Client getWithoutPhoto(Integer id) {
		return clientDAO.getWithoutPhoto(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveSolde(Integer id, Euros solde) {
		clientDAO.saveSolde(id, solde);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setPhoto(Integer id, byte[] photo) {
		clientDAO.setPhoto(id, photo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setPhotoFromFile(Integer id,File f) {
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
		clientDAO.setPhoto(id, bFile);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Client getFromWristband(Integer wnb) {
		return find(clientDAO.getClientFromWristband(wnb));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveWb(Integer clId, Integer wbId, boolean wbErase, boolean clientErase) {
		if(wbErase){
			clientDAO.wb_eraseWristband(wbId);
		}
		if(clientErase){
			clientDAO.wb_eraseClient(clId);
		}
		clientDAO.saveWb(clId,wbId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer getWristbandNb(Integer clId) {
		return clientDAO.getWristbandNb(clId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean wristbandExists(Integer wbId) {
		return (clientDAO.getClientFromWristband(wbId)!=-1);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean hasWristband(Integer clId) {
		return (clientDAO.getWristbandNb(clId)!=-1);
	}
	
	
}
