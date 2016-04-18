package com.cartel.service;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cartel.dao.GenericDAO;
import com.cartel.dao.OperateurDAO;
import com.cartel.model.Operateur;

@Service
public class OperateurServiceImpl extends GenericServiceImpl<Operateur,Integer> implements OperateurService {

	private OperateurDAO operateurDAO;
	
	public OperateurServiceImpl(){
		
	}
	
	@Autowired
	public OperateurServiceImpl(@Qualifier("operateurDAOImpl") GenericDAO<Operateur,Integer> genericDAO){
		super(genericDAO);
		this.operateurDAO=(OperateurDAO)genericDAO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Operateur connect(String pseudo, String pswd, int role) {
		//Check le password puis le role
		List<Object[]> list = operateurDAO.getPasswordAndAuths(pseudo);
		if(!list.isEmpty()){
			Object[] infos=(Object[])list.get(0);
			if(((String)infos[0]).equals(pswd)){
				if(((String)infos[1]).charAt(role-1)=='Y'){
					operateurDAO.setConnected(pseudo,role);
					return operateurDAO.find(pseudo);
				}else{
					JOptionPane dial = new JOptionPane();
					JOptionPane.showMessageDialog(dial,"Rôle non autorisé pour ce compte.\n Si cette erreur persiste, veuillez contacter un administrateur.","Erreur",JOptionPane.ERROR_MESSAGE);
				}
			}else{
				JOptionPane dial = new JOptionPane();
				JOptionPane.showMessageDialog(dial,"Mot de passe incorrect.\n Si cette erreur persiste, veuillez contacter un administrateur.","Erreur",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane dial = new JOptionPane();
			JOptionPane.showMessageDialog(dial,"Utilisateur inconnu.\n Si cette erreur persiste, veuillez contacter un administrateur.","Erreur",JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void disconnect(String pseudo) {
		operateurDAO.setConnected(pseudo, 0);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Operateur> getConnectedOp() {
		return operateurDAO.getConnectedOp();
	}
}
