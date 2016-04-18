package com.cartel.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cartel.model.Conso;
import com.cartel.model.Panier;
import com.cartel.service.ConsoService;
import com.cartel.service.ResourceService;

public class PanierPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5304508237874502052L;
	Panier monPanier;
	ConsoService consoServiceImpl;
	Font font;

	public PanierPanel(ConsoService consoServiceImpl, Font font){
		this.consoServiceImpl=consoServiceImpl;
		this.font=font;
		this.setBounds(1050,270,229,89);
		this.setLayout(null);
		this.setBackground(Color.white);
	}
	
	protected void init(String panier){
		monPanier = Panier.sToPanier(panier, consoServiceImpl.getAll());
		this.removeAll();
		this.revalidate();
		JLabel qteLbl;
		JLabel imgLbl;
		for(int i=0;i<Math.min(monPanier.size(),6);i++){
			imgLbl=new JLabel(getImg(monPanier.get(i).getConso().getId()));
			qteLbl=new JLabel(""+monPanier.get(i).getQte());
			qteLbl.setFont(font.deriveFont(30f).deriveFont(Font.BOLD));
			qteLbl.setForeground(Colors.green3);
			if(i%2==0){
				imgLbl.setBounds(2+75*i/2,2,35,35);
				qteLbl.setBounds(37+75*i/2,12,32,20);
			}else{
				imgLbl.setBounds(2+75*(i-1)/2,50,35,35);
				qteLbl.setBounds(37+75*(i-1)/2,60,32,20);
			}
		
		/*	imgLbl.setBounds(2+35*i, 2,30, 30);
			qteLbl.setBounds(2+35*i,35,30,30);
			qteLbl.setHorizontalAlignment(JLabel.CENTER);
		*/	this.add(imgLbl);
			this.add(qteLbl);
		
		}
		this.repaint();
	}
	
	public ImageIcon getImg(Integer id){
		return new ImageIcon(new ImageIcon(Conso.getPhotoPath(id)).getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
	}
	
	
}
