package com.cartel.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cartel.model.Item;
import com.cartel.model.Panier;
import com.cartel.service.ResourceService;

public class LigneItem extends JPanel{
	private static final long serialVersionUID = 8476311789356582492L;
	private JLabel nbLbl;
	private JLabel nomLbl;
	private JLabel qteLbl;
	private JLabel prixLbl;
	private Item it;
	private Panier monPanier;
	private int index;
	private MainFrame frame;
	private FlatButton cancelButton;
	
	public LigneItem(int ind, Item m, Panier p, MainFrame f, Font font,ResourceService resourceServiceImpl){
		it=m;
		monPanier = p ;
		index=ind;
		frame=f;
		
		if(ind%2==0){
			this.setBackground(Color.white);
		}else{
			this.setBackground(Colors.fond);
		}
		
		this.setBounds(0, 40*ind, 620, 40);
		
		this.setLayout(null);
		
		nbLbl= new JLabel(ind+1+".");
		nbLbl.setForeground(Colors.blue2);
		nbLbl.setFont(font.deriveFont(24f));
		nbLbl.setBounds(15,10,30,20);
		this.add(nbLbl);
		
		nomLbl = new JLabel(it.getConso().getNom());
		nomLbl.setForeground(Colors.blue2);
		nomLbl.setFont(font.deriveFont(24f));
		nomLbl.setBounds(85,10,140,20);
		this.add(nomLbl);
		
		qteLbl = new JLabel(it.getQte()+"");
		qteLbl.setForeground(Colors.blue2);
		qteLbl.setFont(font.deriveFont(24f));
		qteLbl.setBounds(235,10,30,20);
		this.add(qteLbl);
		
		prixLbl = new JLabel(it.getPrix()+"");
		prixLbl.setForeground(Colors.blue2);
		prixLbl.setFont(font.deriveFont(24f));
		prixLbl.setBounds(405,10,100,20);
		this.add(prixLbl);
		
		cancelButton = new FlatButton();
		cancelButton.setBounds(580, 0,40,40);
		if(ind%2==0){
			cancelButton.setBackground(Color.white);
		}else{
			cancelButton.setBackground(Colors.fond);
		}
		cancelButton.setHoverBackgroundColor(Colors.blue1);
		cancelButton.setPressedBackgroundColor(Colors.blue2);
		try {
			Image img;
			 if(ind%2==0){
				 img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Croix1.png").getFile());
			 }else{
				 img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Croix2.png").getFile());
			 }
			 cancelButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		cancelButton.setBorder(null);
		cancelButton.addActionListener(ecout);
		this.add(cancelButton);
		
	}
	
	ActionListener ecout = new ActionListener(){
    	public void actionPerformed(ActionEvent evt){
    		monPanier.deleteItem(index);
    		frame.updateConsoBtn();
    	}
 };
	


}
