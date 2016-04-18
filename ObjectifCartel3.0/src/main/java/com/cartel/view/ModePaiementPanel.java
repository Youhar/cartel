package com.cartel.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.cartel.service.ResourceService;

public class ModePaiementPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2674998186256251877L;
	private ResourceService resourceServiceImpl;
	private String modePaiement="";
	private FlatButton cbButton;
	private FlatButton cashButton;
	private FlatButton ecoButton;
	FlatButton valButton;
	
	public ModePaiementPanel(FlatButton valButton,ResourceService resourceServiceImpl){
		super();
		this.valButton=valButton;
		this.resourceServiceImpl=resourceServiceImpl;
		this.setBackground(Color.white);
		this.setLayout(null);
		this.setBounds(660,60,300,260);
		initComponents();
	}
	
	private void initComponents(){
		cbButton=new FlatButton();
		cbButton.setBackground(Colors.gray);
		cbButton.setHoverBackgroundColor(Colors.gray);
		cbButton.setPressedBackgroundColor(Colors.green1);
		cbButton.setBounds(40,40,140,80);
		try {
			Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/ModeCB.png").getInputStream());
			cbButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		cbButton.setBorder(null);
		cbButton.addActionListener(ecout);
		this.add(cbButton);
		
		cashButton=new FlatButton();
		cashButton.setBackground(Colors.gray);
		cashButton.setHoverBackgroundColor(Colors.gray);
		cashButton.setPressedBackgroundColor(Colors.green1);
		cashButton.setBounds(40,140,140,80);
		try {
			Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/ModeCash.png").getInputStream());
			cashButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		cashButton.setBorder(null);
		cashButton.addActionListener(ecout);
		this.add(cashButton);
		
		ecoButton=new FlatButton();
		ecoButton.setBackground(Colors.gray);
		ecoButton.setHoverBackgroundColor(Colors.gray);
		ecoButton.setPressedBackgroundColor(Colors.green1);
		ecoButton.setBounds(190,60,80,140);
		try {
			Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/ModeEcoCup.png").getInputStream());
			ecoButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		ecoButton.setBorder(null);
		ecoButton.addActionListener(ecout);
		this.add(ecoButton);
	}
	
	protected String getModePaiement(){
		return modePaiement;
	}
	
	private void setSelected(FlatButton f, boolean b){
		if(b){
			f.setBackground(Colors.green1);
			f.setHoverBackgroundColor(Colors.green1);
			f.setPressedBackgroundColor(Colors.green1);
			f.setBorder(new LineBorder(Colors.green3,2));
		}else{
			f.setBackground(Colors.gray);
			f.setHoverBackgroundColor(Colors.gray);
			f.setPressedBackgroundColor(Colors.green1);
			f.setBorder(null);
		}
	}
	
	private ActionListener ecout=new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!valButton.isEnabled()){
				setSelected((FlatButton)e.getSource(),true);
				valButton.setEnabled(true);
				repaint();
			}else{
				if(e.getSource().equals(cashButton)&&modePaiement!="C"){
					setSelected(cashButton,true);
					setSelected(cbButton,false);
					setSelected(ecoButton,false);
					modePaiement="C";
					repaint();
				}else if(e.getSource().equals(cbButton)&&modePaiement!="B"){
					setSelected(cashButton,false);
					setSelected(cbButton,true);
					setSelected(ecoButton,false);
					modePaiement="B";
					repaint();
				}else if(e.getSource().equals(ecoButton)&&modePaiement!="E"){
					setSelected(cashButton,false);
					setSelected(cbButton,false);
					setSelected(ecoButton,true);
					modePaiement="E";
					repaint();
				}
			}
		}
	};
}
