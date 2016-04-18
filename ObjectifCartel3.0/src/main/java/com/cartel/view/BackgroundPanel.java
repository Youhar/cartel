package com.cartel.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.InputStream;

import javax.swing.JPanel;

class BackgroundPanel extends JPanel
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -8265089457860432013L;
Image image;
  public BackgroundPanel(InputStream f)
  {
    try
    {
      image = javax.imageio.ImageIO.read(f);
    }
    catch (Exception e) { /*handled in paintComponent()*/ }
  }
  
  public BackgroundPanel(File f)
  {
    try
    {
      image = javax.imageio.ImageIO.read(f);
    }
    catch (Exception e) { /*handled in paintComponent()*/ }
  }
 
  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g); 
    if (image != null)
      g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
  }
}