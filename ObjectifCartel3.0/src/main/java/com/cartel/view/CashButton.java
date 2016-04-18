package com.cartel.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;

public class CashButton extends FlatButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1690327587348386630L;
	private BigDecimal val;

	
	public CashButton(BigDecimal val, Font font, ActionListener cashButtonListener){
		super(val.toString()+" \u20AC");
		this.val=val;
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.CENTER);
		this.setForeground(Color.white);
		this.setHoverBackgroundColor(Colors.green1);
		this.setBackground(Colors.green1);
		this.setPressedBackgroundColor(Colors.green2);
		this.setBorder(null);
		this.setFont(font.deriveFont(60f).deriveFont(Font.BOLD));
		this.addActionListener(cashButtonListener);
		
		int x=0;
		int y=0;
		switch(val.movePointRight(1).intValue()){
		case 500:
			x=40;
			y=80;
			break;
		case 200:
			x=240;
			y=80;
			break;
		case 100:
			x=440;
			y=80;
			break;
		case 50:
			x=40;
			y=266;
			break;
		case 20:
			x=240;
			y=266;
			break;
		case 10:
			x=440;
			y=266;
			break;
		case 5:
			x=40;
			y=453;
			break;
		case 2:
			x=240;
			y=453;
			break;
		case 1:
			x=440;
			y=453;
			break;
		}
		this.setBounds(x,y,180,167);
	}
	
	protected BigDecimal getVal(){
		return val;
	}
}
