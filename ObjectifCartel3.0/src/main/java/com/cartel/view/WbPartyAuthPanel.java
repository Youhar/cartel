package com.cartel.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WbPartyAuthPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8945977495896467617L;
	private Font font;
	private JLabel legendeLbl;
	private FlatButton btn1;
	private FlatButton btn2;
	private FlatButton btn3;
	private boolean sel1;
	private boolean sel2;
	private boolean sel3;
	
	public WbPartyAuthPanel(Font font){
		super();
		this.font=font.deriveFont(24f);
		this.setBackground(Colors.green5);
		this.setLayout(null);
		this.setBounds(1015,240,230,40);
		initComponents();
	}
	
	private void initComponents(){
		legendeLbl=new JLabel("Aut. soir\u00e9es");
		legendeLbl.setForeground(Color.white);
		legendeLbl.setBorder(null);
		legendeLbl.setFont(font);
		legendeLbl.setBackground(Colors.green5);
		legendeLbl.setBounds(5,15,110,20);
		this.add(legendeLbl);
		
		btn1=new FlatButton("1");
		btn1.setForeground(Color.white);
		btn1.setBackground(Colors.green4);
		btn1.setHoverBackgroundColor(Colors.green4);
		btn1.setPressedBackgroundColor(Colors.red);
		btn1.setBorder(null);
		btn1.setFont(font);
		btn1.setBounds(120,10,30,30);
		btn1.addActionListener(ecout);
		this.add(btn1);
		sel1=true;
		
		btn2=new FlatButton("2");
		btn2.setForeground(Color.white);
		btn2.setBackground(Colors.green4);
		btn2.setHoverBackgroundColor(Colors.green4);
		btn2.setPressedBackgroundColor(Colors.red);
		btn2.setBorder(null);
		btn2.setFont(font);
		btn2.setBounds(160,10,30,30);
		btn2.addActionListener(ecout);
		this.add(btn2);
		sel2=true;
		
		btn3=new FlatButton("3");
		btn3.setForeground(Color.white);
		btn3.setBackground(Colors.green4);
		btn3.setHoverBackgroundColor(Colors.green4);
		btn3.setPressedBackgroundColor(Colors.red);
		btn3.setBorder(null);
		btn3.setFont(font);
		btn3.setBounds(200,10,30,30);
		btn3.addActionListener(ecout);
		this.add(btn3);
		sel3=true;
	}
	
	private void setSelected(FlatButton f,boolean b){
		if(b){
			f.setBackground(Colors.green4);
			f.setHoverBackgroundColor(Colors.green4);
			f.setPressedBackgroundColor(Colors.red);
			f.setBorder(null);
		}else{
			f.setBackground(Colors.red);
			f.setHoverBackgroundColor(Colors.red);
			f.setPressedBackgroundColor(Colors.green4);
			f.setBorder(null);
		}
	}
	
	private ActionListener ecout=new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource().equals(btn1)){
				sel1=!sel1;
				setSelected(btn1,sel1);
			}else if(evt.getSource().equals(btn2)){
				sel2=!sel2;
				setSelected(btn2,sel2);
			}else if(evt.getSource().equals(btn3)){
				sel3=!sel3;
				setSelected(btn3,sel3);
			}
			
		}
	};
	
	public String getAuthString(){
		StringBuffer result=new StringBuffer("");
		if(sel1){
			result.append("1");
		}else{
			result.append("2");
		}
		if(sel2){
			result.append("1");
		}else{
			result.append("2");
		}
		if(sel3){
			result.append("1");
		}else{
			result.append("2");
		}
		return result.toString();
	}
	
	public void setAuths(boolean s1, boolean s2, boolean s3){
		sel1=s1;
		sel2=s2;
		sel3=s3;
		setSelected(btn1,s1);
		setSelected(btn2, s2);
		setSelected(btn3, s3);
	}
}
