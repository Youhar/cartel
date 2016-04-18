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

import com.cartel.model.Credit;
import com.cartel.model.Debit;
import com.cartel.model.Operateur;
import com.cartel.model.Transaction;
import com.cartel.service.ResourceService;
import com.cartel.service.TransactionService;

public class LigneAdminTransaction extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4000591767864446479L;
	private int nb;
	private Transaction transaction;
	private Font font;
	private TransactionService transactionServiceImpl;
	private ResourceService resourceServiceImpl;
	private MainFrame maFrame;
	private JPanel panierPanel;
	private FlatButton cancelButton;
	
	public LigneAdminTransaction(int nb, Transaction t, MainFrame frame) {
		super();
		this.nb=nb;
		this.transaction=t;
		this.maFrame=frame;
		this.font=frame.medium.deriveFont(20f);
		this.transactionServiceImpl=frame.transactionServiceImpl;
		this.resourceServiceImpl=frame.resourceServiceImpl;
		this.setLayout(null);
		this.setBounds(0,30*nb,1110, 30);
		
		init();
	}
	
	private void init(){
		try{
			JLabel idLabel=new JLabel("\u0023"+transaction.getId());
			idLabel.setBounds(10, 5, 70, 20);
			idLabel.setFont(font);
			this.add(idLabel);
			
			JLabel dateLabel = new JLabel(transaction.getDateTransaction());
			dateLabel.setBounds(110, 5, 105, 20);
			dateLabel.setFont(font);
			this.add(dateLabel);
			
			JLabel typeLabel = new JLabel(transaction.getType());
			typeLabel.setBounds(245, 5, 50, 20);
			typeLabel.setFont(font);
			this.add(typeLabel);

			JLabel opeLabel = new JLabel(transaction.getOperateur().getPseudo());
			opeLabel.setBounds(325, 5, 75, 20);
			opeLabel.setFont(font);
			this.add(opeLabel);
			if(transaction.getType().equals("debit")){
				JLabel barLabel = new JLabel("\u0040bar "+((Debit)transaction).getBar());
				barLabel.setBounds(405, 5, 60, 20);
				barLabel.setFont(font);
				this.add(barLabel);
			}
			
			JLabel clientIdLabel = new JLabel(transaction.getClient().getId()+"");
			clientIdLabel.setBounds(495, 5, 55, 20);
			clientIdLabel.setFont(font);
			this.add(clientIdLabel);
			
			JLabel clientLabel = new JLabel(transaction.getClient().getNom().toUpperCase()+" "+transaction.getClient().getPrenom());
			clientLabel.setBounds(570, 5, 260, 20);
			clientLabel.setFont(font);
			this.add(clientLabel);
			
			if(transaction.getType().equals("debit")){
				panierPanel = new AdminTransPanierPanel(((Debit)transaction).getPanier(),maFrame.consoServiceImpl,maFrame.medium);
			}else if(transaction.getType().equals("credit")){
				switch(((Credit)transaction).getMode()){
				case "C":
					panierPanel= new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AdminTransCash.png").getInputStream());
					panierPanel.setBounds(900, 5, 33, 20);
					break;
				case "B":
					panierPanel= new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AdminTransCB.png").getInputStream());
					panierPanel.setBounds(900, 5, 30, 20);
					break;
				case "E":
					panierPanel= new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AdminTransEco.png").getInputStream());
					panierPanel.setBounds(900, 5, 33, 20);
					break;
				}
			}else{
				panierPanel= new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AdminTransAdmin.png").getInputStream());
				panierPanel.setBounds(900, 5, 22, 20);
			}
			this.add(panierPanel);
			
			cancelButton=new FlatButton();
			cancelButton.setBounds(1080,0,30,30);
			Image img;
			if(nb%2==0){
				panierPanel.setBackground(Colors.gray);
				cancelButton.setBackground(Colors.gray);
				img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Croix2.png").getInputStream());
			}else{
				panierPanel.setBackground(Color.white);
				cancelButton.setBackground(Color.white);
				cancelButton.setHoverBackgroundColor(Color.white);
				img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Croix1.png").getInputStream());
			}
			cancelButton.setHoverBackgroundColor(Colors.blue3);
			cancelButton.setPressedBackgroundColor(Colors.red);
			if(transaction.isCanceled()){
				panierPanel.setBackground(Colors.red);
				setBackground(Colors.red);
				cancelButton.setBackground(Colors.red);
				cancelButton.setHoverBackgroundColor(Colors.red);
				cancelButton.setPressedBackgroundColor(Colors.red);
			}else{
				if(nb%2==0){
					this.setBackground(Colors.gray);
				}else{
					this.setBackground(Color.white);
				}
			}
			cancelButton.setIcon(new ImageIcon(img));
			cancelButton.setBorder(null);
			cancelButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!transaction.isCanceled()){
						setBackground(Colors.red);
						panierPanel.setBackground(Colors.red);
						cancelButton.setBackground(Colors.red);
						cancelButton.setHoverBackgroundColor(Colors.red);
						cancelButton.setPressedBackgroundColor(Colors.red);
						// Please reverse the fucking transaction
						transactionServiceImpl.reverseTransaction(transaction);
						transaction.setCanceled(true);
					}
				}
			});
			this.add(cancelButton);
			
			this.revalidate();
			this.repaint();
		}catch(IOException e){
			System.err.println("Ca merde avec les images");
		}
		
		
	}

	

	

}
