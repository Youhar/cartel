package com.cartel.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.cartel.model.Transaction;
import com.cartel.service.ResourceService;
import com.cartel.service.TransactionService;

public class LigneTransaction extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4140685924481781792L;
	private Image image;
	private Transaction t;
	private Font f;
	private Color c=new Color(59,132,83);
	private FlatButton cancelButton;
	private int nb;
	private MainFrame mFrame;
	private ResourceService resourceServiceImpl;
	private TransactionService transactionServiceImpl;
	private JLabel currentSoldeLabel;
	private boolean selected=false;
	
	public LigneTransaction(int nb, Transaction t,MainFrame mFrame, ResourceService resourceServiceImpl, TransactionService transactionServiceImpl){
		this.t=t;
		this.nb=nb;
		this.mFrame=mFrame;
		this.resourceServiceImpl=resourceServiceImpl;
		this.transactionServiceImpl=transactionServiceImpl;
		this.addMouseListener(this);
	    try
	    {
	      if(nb%2==0){
	    	  image = javax.imageio.ImageIO.read(resourceServiceImpl.getResource("classpath:img/LigneTransactionDebit1.png").getInputStream());
	      }else{
	    	  image = javax.imageio.ImageIO.read(resourceServiceImpl.getResource("classpath:img/LigneTransactionDebit2.png").getInputStream());
	      }
	    }
	    catch (Exception e) { /*handled in paintComponent()*/ }
	    this.setLayout(null);
	    this.setBounds(0,40*nb,620, 40);
		f=mFrame.light.deriveFont(24f);
		if(nb==0){
			selected=true;
		}
	    init();
	    
	}
	
	  @Override
	  protected void paintComponent(Graphics g)
	  {
	    super.paintComponent(g); 
	    if (image != null)
	      g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
	  }
	  
	  private void init(){
		  JLabel nbLabel= new JLabel(t.getClient().getId()+"");
		  nbLabel.setBounds(22,5,90,30);
		  nbLabel.setFont(f);
		  nbLabel.setForeground(c);
		  this.add(nbLabel);
		  
		  JLabel nameLabel = new JLabel(t.getClient().getPrenom()+" "+t.getClient().getNom());
		  nameLabel.setBounds(135,5,245,30);
		  nameLabel.setFont(f);
		  nameLabel.setForeground(c);
		  this.add(nameLabel);
		  
		  JLabel costLabel = new JLabel(t.getEurosMontant().toString());
		  costLabel.setBounds(415,5,75,30);
		  costLabel.setFont(f);
		  costLabel.setForeground(c);
		  this.add(costLabel);
		  
		  currentSoldeLabel=new JLabel(t.getClient().getEurosSolde().toString());
		  currentSoldeLabel.setBounds(510,5,75,30);
		  currentSoldeLabel.setFont(f);
		  currentSoldeLabel.setForeground(c);
		  this.add(currentSoldeLabel);
		  
		  cancelButton = new FlatButton();
		  cancelButton.setBounds(580, 2,38,36);
		  if(!t.isCanceled()){
			  if(nb%2==0){
				  cancelButton.setBackground(Color.white);
			  }else{
				  cancelButton.setBackground(new Color(242,241,239));
			  }
			  cancelButton.setHoverBackgroundColor(new Color(100,217,138));
			  cancelButton.setPressedBackgroundColor(new Color(94,202,127));
			  cancelButton.addActionListener(cancelListener);
		  }else{
			  canceledLook();
			  cancelButton.setBackground(Colors.red);
			  cancelButton.setHoverBackgroundColor(Colors.red);
			  cancelButton.setPressedBackgroundColor(Colors.red);;
		  }
		  try {
			  Image img;
			  	if(nb%2==0){
			  		img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Croix1.png").getInputStream());
			  	}else{
			  		img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Croix2.png").getInputStream());
			  	}
			    cancelButton.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {
			  }
		  cancelButton.setBorder(null);
		  cancelButton.addMouseListener(this);
		  this.add(cancelButton);
		  }
	  
	  private void canceledLook(){
		 try {
			 JLabel cancelLineLabel;
			 Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/CancelLine.png").getInputStream());
			 cancelLineLabel=new JLabel(new ImageIcon(img));
			 cancelLineLabel.setBounds(2, 18, 578, 4);
			 this.add(cancelLineLabel);
		 } catch (IOException e) {
			e.printStackTrace();
		}
	  }
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!selected){
			mFrame.updateDetails(t);
			this.setBorder(new LineBorder(Colors.green1,2));
			for(int i=0;i<mFrame.listeLigneTransaction.size();i++){
				if(i!=nb){
					mFrame.listeLigneTransaction.get(i).forceBorder(false);
				}
			}
			selected=true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void forceBorder(boolean plain){
		if(plain){
			this.setBorder(new LineBorder(Colors.green1,2));
		}else{
			this.setBorder(null);
			selected=false;
		}
	}
	
	private ActionListener cancelListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			canceledLook();
			cancelButton.setBackground(Colors.red);
			cancelButton.setHoverBackgroundColor(Colors.red);
			cancelButton.setPressedBackgroundColor(Colors.red);
			// Please reverse the fucking transaction
			transactionServiceImpl.reverseTransaction(t);
			currentSoldeLabel.setText(t.getClient().getEurosSolde().toString());
			mFrame.updateDetails(t);
			repaint();
		}
		
	};
}
