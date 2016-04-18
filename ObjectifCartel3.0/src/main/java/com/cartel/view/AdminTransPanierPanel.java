package com.cartel.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.core.io.Resource;

import com.cartel.model.Conso;
import com.cartel.model.Panier;
import com.cartel.service.ConsoService;
import com.cartel.service.ResourceService;

public class AdminTransPanierPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8262827291762553561L;
	private ConsoService consoServiceImpl;
	private Font font;
	private String panier;
	private Panier monPanier;
	
	public AdminTransPanierPanel(String panier, ConsoService consoServiceImpl, Font font){
		this.consoServiceImpl=consoServiceImpl;
		this.font=font;
		this.panier=panier;
		this.setFont(font.deriveFont(20f));
		this.setForeground(Color.white);
		this.setBounds(860,5,200,20);
		this.setLayout(null);
		
		init();
	}
	
	public void init(){
		monPanier = Panier.sToPanier(panier, consoServiceImpl.getAll());
		JLabel qteLbl;
		JLabel imgLbl;
		for(int i=0;i<Math.min(monPanier.size(),4);i++){
			imgLbl=new JLabel(getImg(monPanier.get(i).getConso().getId()));
			qteLbl=new JLabel(""+monPanier.get(i).getQte());
			qteLbl.setFont(font.deriveFont(17f));
			qteLbl.setForeground(Color.black);
			imgLbl.setBounds(50*i,0,20,20);
			qteLbl.setBounds(20+50*i,0,25,20);
			this.add(imgLbl);
			this.add(qteLbl);
		}
		this.repaint();
	}
	
	public ImageIcon getImg(Integer id){
		return new ImageIcon(new ImageIcon(Conso.getPhotoPath(id)).getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
	}
}
