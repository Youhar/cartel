package com.cartel.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.cartel.model.Client;

public class WbLoadingLigneClient extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5315243727615066184L;
	private int nb;
	private Client client;
	private WbLoadingFrame frame;
	private boolean selected;
	
	
	public WbLoadingLigneClient(Client c,int nb, WbLoadingFrame frame){
		this.nb=nb;
		this.client=c;
		this.frame=frame;
		this.addMouseListener(this);
		this.setLayout(null);
		this.setBorder(null);
		if(nb<16){
			if(nb%2==0){
				this.setBackground(Color.white);
			}else{
				this.setBackground(Colors.blue4);
			}
			this.setBounds(0, nb*30, 310, 30);
		}else{
			if(nb%2==0){
				this.setBackground(Colors.blue4);
			}else{
				this.setBackground(Color.white);
			}
			this.setBounds(310, (nb-16)*30, 310, 30);
		}
		if(nb==0){
			selected=true;
			forceBorder(true);
		}
		initLabels();
		
		
	}
	
	private void initLabels(){
		JLabel nomLbl=new JLabel(client.getNom());
		nomLbl.setFont(frame.regular.deriveFont(18f));
		nomLbl.setBorder(null);
		nomLbl.setForeground(Colors.blue3);
		nomLbl.setBounds(5,0,90,30);
		nomLbl.setVerticalAlignment(SwingConstants.CENTER);
		this.add(nomLbl);
		
		JLabel prenomLbl=new JLabel(client.getPrenom());
		prenomLbl.setFont(frame.regular.deriveFont(18f));
		prenomLbl.setBorder(null);
		prenomLbl.setForeground(Colors.blue3);
		prenomLbl.setBounds(100,0,90,30);
		prenomLbl.setVerticalAlignment(SwingConstants.CENTER);
		this.add(prenomLbl);
		
		JLabel delegationLbl=new JLabel(client.getDelegation());
		delegationLbl.setFont(frame.regular.deriveFont(18f));
		delegationLbl.setBorder(null);
		delegationLbl.setForeground(Colors.blue3);
		delegationLbl.setBounds(200,0,100,30);
		delegationLbl.setVerticalAlignment(SwingConstants.CENTER);
		this.add(delegationLbl);
	}
	
	public void forceBorder(boolean plain){
		if(plain){
			this.setBorder(new LineBorder(Colors.blue3,3));
		}else{
			this.setBorder(null);
			selected=false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(!selected){
			frame.updateClientInfo(client);
			for(int i=0;i<frame.ligneList.size();i++){
					frame.ligneList.get(i).forceBorder(i==nb);
			}
			selected=true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		
	}
}
