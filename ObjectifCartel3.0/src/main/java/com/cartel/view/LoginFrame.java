package com.cartel.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.cartel.model.Operateur;
import com.cartel.service.OperateurService;
import com.cartel.service.ResourceService;

public class LoginFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 626267951067644113L;
	private JTextField textField;
	private JPasswordField passwordField;
	
	private ApplicationContext applicationContext;
    private ResourceService resourceServiceImpl;
	private OperateurService operateurServiceImpl;
	private BackgroundPanel loginFramePanel;
	MainFrame mainFrame;
	private Font font;
	
	@Autowired
	public LoginFrame(ApplicationContext applicationContext) {
		super();
		this.applicationContext=applicationContext;
		initServices();
		setTitle("Projet Cartel 2016");
		setBounds(100, 100, 400, 530);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.white);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		setLocation(x, y);
		setResizable(false);
		try {
			font=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Regular.ttf").getInputStream()).deriveFont(24f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initialize();
		
		//ICONE
		try {
			Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/icon.png").getFile());
			this.setIconImage(img);
		} catch (IOException ex) {
		}
		
		setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
			loginFramePanel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/LoginFrame.png").getInputStream());
			loginFramePanel.setBounds(0, 0, 400, 500);
			loginFramePanel.setLayout(null);
			getContentPane().add(loginFramePanel);
			
			textField = new JTextField();
			textField.setBounds(120, 230, 190, 25);
			textField.setForeground(Colors.green4);
			textField.setUI(new HintTextFieldUI("Pseudo",false));
			textField.setFont(font);
			textField.setColumns(10);
			textField.grabFocus();
			textField.setBorder(null);
			loginFramePanel.add(textField);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(120, 290, 190, 25);
			passwordField.setFont(font);
			passwordField.setForeground(Colors.green4);
			passwordField.setBorder(null);
			loginFramePanel.add(passwordField);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		FlatToggleButton adminButton;
		FlatToggleButton bar1Button;
		FlatToggleButton bar2Button;
		FlatToggleButton crediteurButton;
		FlatButton loginButton;
		
		ButtonGroup btngroup=new ButtonGroup();
		
		adminButton = new FlatToggleButton("Admin");
		btngroup.add(adminButton);
		adminButton.setBackground(Colors.green5);
		adminButton.setPressedBackgroundColor(Color.white);
		adminButton.setFont(font.deriveFont(20f));
		adminButton.setForeground(Colors.green4);
		adminButton.setBounds(72, 343, 70, 30);
		loginFramePanel.add(adminButton);
		
		bar1Button = new FlatToggleButton("Bar 1");
		btngroup.add(bar1Button);
		bar1Button.setBackground(Colors.green5);
		bar1Button.setPressedBackgroundColor(Color.white);
		bar1Button.setFont(font.deriveFont(20f));
		bar1Button.setForeground(Colors.green4);
		bar1Button.setBounds(162, 343, 70, 30);
		bar1Button.setSelected(true);
		loginFramePanel.add(bar1Button);
		
		bar2Button = new FlatToggleButton("Bar 2");
		btngroup.add(bar2Button);
		bar2Button.setBackground(Colors.green5);
		bar2Button.setPressedBackgroundColor(Color.white);
		bar2Button.setFont(font.deriveFont(20f));
		bar2Button.setForeground(Colors.green4);
		bar2Button.setBounds(162, 383, 70, 30);
		loginFramePanel.add(bar2Button);
		
		crediteurButton = new FlatToggleButton("Crediteur");
		btngroup.add(crediteurButton);
		crediteurButton.setBackground(Colors.green5);
		crediteurButton.setPressedBackgroundColor(Color.white);
		crediteurButton.setFont(font.deriveFont(20f));
		crediteurButton.setForeground(Colors.green4);
		crediteurButton.setBounds(252, 343, 70, 30);
		loginFramePanel.add(crediteurButton);
		
		loginButton = new FlatButton("Login");
		loginButton.setBackground(Colors.green5);
		loginButton.setHoverBackgroundColor(Colors.green5);
		loginButton.setPressedBackgroundColor(Colors.green1);
		loginButton.setFont(font.deriveFont(30f));
		loginButton.setForeground(Colors.green4);
		loginButton.setBounds(122,433,150,40);
		loginButton.setBorder(new LineBorder(Colors.green4,3));
		loginFramePanel.add(loginButton);
		
		loginButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int role=1;
				if(adminButton.isSelected()){
					role=1;
				}else if(bar1Button.isSelected()){
					role=3;
				}else if(bar2Button.isSelected()){
					role=4;
				}else if(crediteurButton.isSelected()){
					role=2;
				}
				Operateur connect;
				connect = operateurServiceImpl.connect(textField.getText(), new String(passwordField.getPassword()), role);
				if (connect!=null) { 
					switch (role) {
					case 1 : 
						mainFrame=(MainFrame)applicationContext.getBean("mainFrame");
						mainFrame.prepare(connect);
						setVisible(false);
						mainFrame.revalidate();
						mainFrame.repaint();
						mainFrame.setVisible(true);
						break;
					case 3 : case 4: 
						mainFrame = (MainFrame)applicationContext.getBean("mainFrame");
						mainFrame.prepare(connect);
						setVisible(false);
						mainFrame.revalidate();
						mainFrame.repaint();
						mainFrame.setVisible(true);
					break;
					case 2 :
						mainFrame = (MainFrame)applicationContext.getBean("mainFrame");
						mainFrame.prepare(connect);
						setVisible(false);
						mainFrame.revalidate();
						mainFrame.repaint();
						mainFrame.setVisible(true);
					break;
					}
				
					switch (role) {
					case 1 : 
						System.out.println("Connected as Admin !");
					break;
					case 2 : 
						System.out.println("Connected as Crediteur !");
					break;
					case 3 : case 4 :
						System.out.println("Connected as Barman !");		
					break;
					}
				}				
			}
		});
		
		revalidate();
		repaint();
	}
	
	public void initServices(){
		operateurServiceImpl=(OperateurService)applicationContext.getBean("operateurServiceImpl");
		resourceServiceImpl=(ResourceService)applicationContext.getBean("resourceServiceImpl");
	}	
}
