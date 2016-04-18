package com.cartel.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JToggleButton;

public class FlatToggleButton extends JToggleButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2491011807588106644L;
    private Color pressedBackgroundColor;

    public FlatToggleButton() {
        this(null);
    }

    public FlatToggleButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        this.setFocusable(false);
        this.setMargin(new Insets(0,0,0,0));
        this.setBorder(null);
        this.setPressedBackgroundColor(Colors.orange);
        this.setBackground(Colors.blue4);
        this.setForeground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(getModel().isSelected()){
        	if (getModel().isPressed()) {
                g.setColor(getBackground());
            } else {
                g.setColor(pressedBackgroundColor);
            }
        }else{
        	if (getModel().isPressed()) {
                g.setColor(pressedBackgroundColor);
            } else {
                g.setColor(getBackground());
            }
        }
    	
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }


    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}
