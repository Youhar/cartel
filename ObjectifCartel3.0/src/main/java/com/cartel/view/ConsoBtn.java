package com.cartel.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import org.springframework.core.io.Resource;

import com.cartel.model.Conso;
import com.cartel.model.Euros;
import com.cartel.service.ResourceService;

public class ConsoBtn extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4038819131141391169L;
	private Conso maConso;
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
	
	public ConsoBtn(Conso c, Euros solde , int nb, Font font){
		super();
		maConso=c;
		update(solde);
		this.hoverBackgroundColor=Color.white;
		this.pressedBackgroundColor=Colors.blue1;
		this.setBackground(Color.white);
		this.setFont(font.deriveFont(24f));
		this.setForeground(Colors.blue2);
		if(nb<10){
			this.setBounds(45+90*nb,70,80,115);
		}else if(nb>10 && nb <20){
			this.setBounds(45+90*(nb-10), 195, 80, 115);
		}
		this.setBorder(new LineBorder(pressedBackgroundColor,2));
		this.setText("<html><p style='margin-top:-20'>"+c.getNom()+"<br><p style='margin-top:-15'>"+c.getEurosPrix()+"</html>");
		this.setHorizontalTextPosition(CENTER);
		this.setVerticalTextPosition(BOTTOM);
		this.setFocusable(false);
		super.setContentAreaFilled(false);
		this.setIcon(new ImageIcon(new ImageIcon(Conso.getPhotoPath(c.getId())).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH)));
	}
	
	public void update(Euros solde){
		if (maConso.getEurosPrix().isSup(solde)){
			this.setEnabled(false);
		}else{
			this.setEnabled(true);
		}
	}
	
	public Conso getConso(){
		return maConso;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
       
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
        this.setBorder(new LineBorder(pressedBackgroundColor,2));
    }
	
}
