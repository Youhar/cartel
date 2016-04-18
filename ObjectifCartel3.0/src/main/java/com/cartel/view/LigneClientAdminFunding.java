package com.cartel.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.cartel.model.Client;
import com.cartel.service.ResourceService;

public class LigneClientAdminFunding extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1768676358229765322L;
	private JLabel nbLbl;
	private JLabel nomLbl;
	private JLabel prenomLbl;
	private JLabel delegationLbl;
	private int index;
	private Client client;
	private MainFrame frame;
	private FlatButton cancelButton;
	private boolean selected=false;
	
	public LigneClientAdminFunding(int ind, Client c, MainFrame f,ResourceService resourceServiceImpl){
		client=c;
		index=ind;
		frame=f;
		this.addMouseListener(this);
		if(ind==0){
			selected=true;
			forceBorder(true);
		}		
		if(ind%2==0){
			this.setBackground(Colors.fond);
		}else{
			this.setBackground(Color.white);
		}
		
		this.setBounds(0, 30*ind, 407, 30);
		
		this.setLayout(null);
		
		nbLbl= new JLabel(client.getId().toString());
		nbLbl.setForeground(Colors.green4);
		nbLbl.setFont(f.normal.deriveFont(20f));
		nbLbl.setBounds(5,5,80,20);
		this.add(nbLbl);
		
		nomLbl = new JLabel(client.getNom());
		nomLbl.setForeground(Colors.green4);
		nomLbl.setFont(f.normal.deriveFont(20f));
		nomLbl.setBounds(95,5,85,20);
		this.add(nomLbl);
		
		prenomLbl = new JLabel(client.getPrenom());
		prenomLbl.setForeground(Colors.green4);
		prenomLbl.setFont(f.normal.deriveFont(20f));
		prenomLbl.setBounds(185,5,90,20);
		this.add(prenomLbl);
		
		delegationLbl = new JLabel(client.getDelegation());
		delegationLbl.setForeground(Colors.green4);
		delegationLbl.setFont(f.normal.deriveFont(20f));
		delegationLbl.setBounds(280,5,97,20);
		this.add(delegationLbl);
		
		cancelButton = new FlatButton();
		cancelButton.setBounds(377,2,28,26);
		if(ind%2==0){
			cancelButton.setBackground(Colors.fond);
		}else{
			cancelButton.setBackground(Color.white);
		}
		cancelButton.setHoverBackgroundColor(Colors.green1);
		cancelButton.setPressedBackgroundColor(Colors.green2);
		try {
			Image img;
			 if(ind%2==0){
				 img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Croix2.png").getFile());
			 }else{
				 img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Croix1.png").getFile());
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
    		frame.adminFundingRemoveClient(client);
    	}
 };
 
	public void forceBorder(boolean plain){
		if(plain){
			this.setBorder(new LineBorder(Colors.green4,2));
		}else{
			this.setBorder(null);
			selected=false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!selected){
			frame.adminUpdateFundingClient(client);
			for(int i=0;i<frame.adminFundingLigneList.size();i++){
					frame.adminFundingLigneList.get(i).forceBorder(i==index);
			}
			selected=true;
		}		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	


}
