package com.cartel.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class FlatButton extends JButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2491011807588106644L;
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;

    public FlatButton() {
        this(null);
    }

    public FlatButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        this.setFocusable(false);
        this.setMargin(new Insets(0,0,0,0));
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