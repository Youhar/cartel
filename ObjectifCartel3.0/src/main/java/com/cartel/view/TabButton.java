package com.cartel.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.MatteBorder;

public class TabButton extends FlatButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2585792414709950626L;
	private boolean selected;
	
	public TabButton(String text,Font font, boolean sel){
		super(text);
		this.setBorder(null);
		this.setBackground(Colors.green1);
		this.setHoverBackgroundColor(Colors.green1);
		this.setPressedBackgroundColor(Colors.green1);
		this.setFont(font.deriveFont(30f).deriveFont(Font.BOLD));
		setSel(sel);
		selected=sel;
	}
	
	protected void setSel(boolean b){
		if(b){
			this.setBorder(new MatteBorder(0, 0, 5, 0, Colors.orange2));
			this.setForeground(Color.white);
			selected=true;
		}else{
			this.setBorder(null);
			this.setForeground(Colors.tabOff);
			selected=false;
		}
	}
	
	protected boolean isSel(){
		return selected;
	}

}
