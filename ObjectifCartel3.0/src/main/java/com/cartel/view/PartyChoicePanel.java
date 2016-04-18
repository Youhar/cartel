package com.cartel.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class PartyChoicePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2333101850768086048L;

	private int chosenParty=0;
	private FlatButton p1Btn;
	private FlatButton p2Btn;
	private FlatButton p3Btn;
	
	private Font font;
	private FlatButton okBtn;
	
	public PartyChoicePanel(Font f,FlatButton okBtn){
		super();
		this.font=f;
		this.okBtn=okBtn;
		this.setBackground(Color.white);
		this.setLayout(null);
		this.setBounds(40,140,260,160);
		this.initComponents();
	}
	
	private void initComponents(){
		p1Btn=new FlatButton("1 - Jeudi 21 avril");
		p1Btn.setForeground(Color.white);
		p1Btn.setBackground(Colors.gray);
		p1Btn.setHoverBackgroundColor(Colors.gray);
		p1Btn.setPressedBackgroundColor(Colors.green1);
		p1Btn.setBorder(null);
		p1Btn.setFont(font.deriveFont(24f));
		p1Btn.setBounds(0,0,260,40);
		p1Btn.addActionListener(ecout);
		this.add(p1Btn);
		
		p2Btn=new FlatButton("2 - Vendredi 22 avril");
		p2Btn.setForeground(Color.white);
		p2Btn.setBackground(Colors.gray);
		p2Btn.setHoverBackgroundColor(Colors.gray);
		p2Btn.setPressedBackgroundColor(Colors.green1);
		p2Btn.setBorder(null);
		p2Btn.setFont(font.deriveFont(24f));
		p2Btn.setBounds(0,60,260,40);
		p2Btn.addActionListener(ecout);
		this.add(p2Btn);
		
		p3Btn=new FlatButton("3 - Samedi 23 avril");
		p3Btn.setForeground(Color.white);
		p3Btn.setBackground(Colors.gray);
		p3Btn.setHoverBackgroundColor(Colors.gray);
		p3Btn.setPressedBackgroundColor(Colors.green1);
		p3Btn.setBorder(null);
		p3Btn.setFont(font.deriveFont(24f));
		p3Btn.setBounds(0,120,260,40);
		p3Btn.addActionListener(ecout);
		this.add(p3Btn);
	}
	
	private void setSelected(FlatButton f,boolean b){
		if(b){
			f.setBackground(Colors.green1);
			f.setHoverBackgroundColor(Colors.green1);
			f.setPressedBackgroundColor(Colors.green1);
			f.setBorder(null);
		}else{
			f.setBackground(Colors.gray);
			f.setHoverBackgroundColor(Colors.gray);
			f.setPressedBackgroundColor(Colors.green1);
			f.setBorder(null);
		}
	}
	
	private ActionListener ecout=new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent evt) {
			if(!okBtn.isEnabled()){
				okBtn.setEnabled(true);
			}
			
			if(evt.getSource().equals(p1Btn)){
				if(chosenParty!=1){
					setSelected(p1Btn,true);
					setSelected(p2Btn,false);
					setSelected(p3Btn,false);
					chosenParty=1;
				}
			}else if(evt.getSource().equals(p2Btn)){
				if(chosenParty!=2){
					setSelected(p2Btn,true);
					setSelected(p3Btn,false);
					setSelected(p1Btn,false);
					chosenParty=2;
				}
			}else if(evt.getSource().equals(p3Btn)){
				if(chosenParty!=3){
					setSelected(p3Btn,true);
					setSelected(p1Btn,false);
					setSelected(p2Btn,false);
					chosenParty=3;
				}
			}
			
		}
	};
	
	protected int getChoice(){
		return chosenParty;
	}
	
	public void disableAll(){
		p1Btn.setEnabled(false);
		p2Btn.setEnabled(false);
		p3Btn.setEnabled(false);
	}
}
