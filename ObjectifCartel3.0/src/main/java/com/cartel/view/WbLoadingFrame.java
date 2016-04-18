package com.cartel.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.cartel.model.Client;
import com.cartel.nfc.ReaderException;
import com.cartel.service.ClientService;
import com.cartel.service.ClientServiceImpl;
import com.cartel.service.ResourceService;

public class WbLoadingFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6187046983321483684L;
	ApplicationContext applicationContext;
	ResourceService resourceServiceImpl;
	ClientService clientServiceImpl;
	
	Font light;
	Font thin;
	Font regular;
	Font medium;
	
	private BackgroundPanel mainPanel;
	private JTextField selNomTF;
	private JTextField selPrenomTF;
	private JTextField selDelegationTF;
	private FlatButton selResetButton;
	private JPanel clientsPanel;
	private JLabel traNomLabel;
	private JLabel traPrenomLabel;
	private JLabel traDelegationLabel;
	private FlatButton traTraButton;
	private FlatButton traSkipButton;
	private JLabel traWaitLabel;
	private JLabel traStateLabel;
	private JLabel traPrecClientLabel;
	private JLabel traRoleLabel;
	private JLabel terminalLabel;
	private JTextField wbNbTF;
	private WbPartyAuthPanel authPanel;
	
	private final List<Client> allClients;
	private List<Integer> selClientsId;
	private List<Integer> displayedClientsId;
	private Client client;
	private boolean termPresent;
	private boolean justRemoved=false;
	
	private CardTerminal terminal;
	private CardChannel channel;
	private Card card;
	protected List<WbLoadingLigneClient> ligneList;
	
	
	
	@Autowired
	public WbLoadingFrame(ApplicationContext applicationContext){
		super();
		this.applicationContext=applicationContext;
		this.setTitle("Cartel-WBLoader");
		this.getContentPane().setBackground(Color.white);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) ((dimension.getWidth() - 1300) / 2),(int) ((dimension.getHeight() - 725) / 2),1300,725);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initServices();
		initFonts();
		initMainPanel();
		initReader();
		
		//ICONE
		try {
			Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/icon.png").getFile());
			this.setIconImage(img);
		} catch (IOException ex) {
		}
		
		allClients=clientServiceImpl.getAll();
		selClientsId=new ArrayList<Integer>();
		displayedClientsId=new ArrayList<Integer>();
		for (int i=0;i<allClients.size();i++){
			selClientsId.add(i);
			displayedClientsId.add(i);
		}
		
		updateClientsPanel();
		
		revalidate();
		repaint();
		this.setVisible(true);
	}
	
	
	public void initMainPanel(){
		try {
			
			//FOND
			mainPanel = new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/WbLoadingPanel.png").getInputStream());
			mainPanel.setBounds(0,0,1300,700);
			mainPanel.setLayout(null);
			this.add(mainPanel);
			
			//INFO TERMINAL
			terminalLabel=new JLabel("");
			terminalLabel.setFont(light.deriveFont(24f).deriveFont(Font.BOLD));
			terminalLabel.setBackground(Colors.blue1);
			terminalLabel.setBorder(null);
			terminalLabel.setBounds(980,5,220,30);
			mainPanel.add(terminalLabel);
			
			//SELECTION CLIENTS
			selNomTF=new JTextField();
			selNomTF.setUI(new HintTextFieldUI("Nom",false));
			selNomTF.setCaretPosition(0);
			selNomTF.setBackground(Color.white);
			selNomTF.setForeground(Colors.green4);
			selNomTF.setFont(regular.deriveFont(24f));
			selNomTF.setBorder(null);
			selNomTF.setBounds(98, 150, 195, 25);
			mainPanel.add(selNomTF);
			
			selPrenomTF=new JTextField();
			selPrenomTF.setUI(new HintTextFieldUI("Pr\u00e9nom",false));
			selPrenomTF.setCaretPosition(0);
			selPrenomTF.setBackground(Color.white);
			selPrenomTF.setForeground(Colors.green4);
			selPrenomTF.setFont(regular.deriveFont(24f));
			selPrenomTF.setBorder(null);
			selPrenomTF.setBounds(98, 210, 195, 25);
			mainPanel.add(selPrenomTF);
			
			selDelegationTF=new JTextField();
			selDelegationTF.setUI(new HintTextFieldUI("D\u00e9l\u00e9gation",false));
			selDelegationTF.setCaretPosition(0);
			selDelegationTF.setBackground(Color.white);
			selDelegationTF.setForeground(Colors.green4);
			selDelegationTF.setFont(regular.deriveFont(24f));
			selDelegationTF.setBorder(null);
			selDelegationTF.setBounds(98, 270, 195, 25);
			mainPanel.add(selDelegationTF);
			
			selResetButton=new FlatButton("reset");
			selResetButton.setForeground(Color.white);
			selResetButton.setFont(regular.deriveFont(24f));
			selResetButton.setBackground(Colors.green5);
			selResetButton.setHoverBackgroundColor(Colors.green5);
			selResetButton.setPressedBackgroundColor(Color.white);
			selResetButton.setBorder(null);
			selResetButton.setBounds(130,320,80,30);
			selResetButton.addActionListener(ecout);
			mainPanel.add(selResetButton);
			
			KeyAdapter ecoutEntries = new KeyAdapter(){
				@Override
				public void keyReleased(KeyEvent e){
					selClientsId=new ArrayList<Integer>();
					for (int i=0;i<allClients.size();i++){
						selClientsId.add(i);
					}
					if(selNomTF.getText().equals("")&&selPrenomTF.getText().equals("")&&selDelegationTF.getText().equals("")){
						updateClientsPanel();
						return;
					}
					if(!selNomTF.getText().equals("")){
						selClientsId=selClientsId.stream().filter(c->allClients.get(c).getNom().equalsIgnoreCase(selNomTF.getText())).collect(Collectors.toList());
					}
					if(!selPrenomTF.getText().equals("")){
						selClientsId=selClientsId.stream().filter(c->allClients.get(c).getPrenom().equalsIgnoreCase(selPrenomTF.getText())).collect(Collectors.toList());
					}
					if(!selDelegationTF.getText().equals("")){
						selClientsId=selClientsId.stream().filter(c->allClients.get(c).getDelegation().equalsIgnoreCase(selDelegationTF.getText())).collect(Collectors.toList());
					}
					updateClientsPanel();
				}
			};
			
			selNomTF.addKeyListener(ecoutEntries);
			selPrenomTF.addKeyListener(ecoutEntries);
			selDelegationTF.addKeyListener(ecoutEntries);
			
			//RESULTATS
			clientsPanel=new JPanel();
			clientsPanel.setLayout(null);
			clientsPanel.setBackground(Colors.blue3);
			clientsPanel.setBounds(340,140,620,480);
			mainPanel.add(clientsPanel);
			
			//TRANSFERT
			traNomLabel= new JLabel("");
			traNomLabel.setHorizontalAlignment(SwingConstants.CENTER);
			traNomLabel.setFont(regular.deriveFont(24f));
			traNomLabel.setBackground(Color.white);
			traNomLabel.setForeground(Colors.green4);
			traNomLabel.setBorder(null);
			traNomLabel.setBounds(1015, 145, 230, 30);
			mainPanel.add(traNomLabel);
			
			traPrenomLabel= new JLabel("");
			traPrenomLabel.setHorizontalAlignment(SwingConstants.CENTER);
			traPrenomLabel.setFont(regular.deriveFont(24f));
			traPrenomLabel.setBackground(Color.white);
			traPrenomLabel.setForeground(Colors.green4);
			traPrenomLabel.setBorder(null);
			traPrenomLabel.setBounds(1015, 172, 230, 30);
			mainPanel.add(traPrenomLabel);
			
			traDelegationLabel= new JLabel("");
			traDelegationLabel.setHorizontalAlignment(SwingConstants.LEFT);
			traDelegationLabel.setFont(regular.deriveFont(24f));
			traDelegationLabel.setBackground(Color.white);
			traDelegationLabel.setForeground(Colors.green4);
			traDelegationLabel.setBorder(null);
			traDelegationLabel.setBounds(1005, 205, 125, 30);
			mainPanel.add(traDelegationLabel);
			
			traRoleLabel=new JLabel("");
			traRoleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			traRoleLabel.setFont(regular.deriveFont(24f));
			traRoleLabel.setBackground(Color.white);
			traRoleLabel.setForeground(Colors.green4);
			traRoleLabel.setBorder(null);
			traRoleLabel.setBounds(1130, 205, 125, 30);
			mainPanel.add(traRoleLabel);
			
			authPanel=new WbPartyAuthPanel(regular);
			mainPanel.add(authPanel);
			
			wbNbTF=new JTextField();
			wbNbTF.setUI(new HintTextFieldUI("Numéro Bracelet",false));
			wbNbTF.setCaretPosition(0);
			wbNbTF.setBackground(Color.white);
			wbNbTF.setForeground(Colors.green4);
			wbNbTF.setFont(regular.deriveFont(24f));
			wbNbTF.setBorder(null);
			wbNbTF.setBounds(1058, 306, 195, 25);
			mainPanel.add(wbNbTF);
			
			traTraButton=new FlatButton("Transfert");
			traTraButton.setForeground(Color.white);
			traTraButton.setBackground(Colors.green4);
			traTraButton.setHoverBackgroundColor(Colors.green4);
			traTraButton.setPressedBackgroundColor(Colors.green3);
			traTraButton.setBorder(null);
			traTraButton.setFont(regular.deriveFont(24f));
			traTraButton.setBounds(1005, 347, 100, 40);
			traTraButton.addActionListener(ecout);
			mainPanel.add(traTraButton);
			
			traSkipButton=new FlatButton("Passer");
			traSkipButton.setForeground(Color.white);
			traSkipButton.setBackground(Colors.green4);
			traSkipButton.setHoverBackgroundColor(Colors.green4);
			traSkipButton.setPressedBackgroundColor(Colors.green3);
			traSkipButton.setBorder(null);
			traSkipButton.setFont(regular.deriveFont(24f));
			traSkipButton.setBounds(1155, 347, 100, 40);
			traSkipButton.addActionListener(ecout);
			mainPanel.add(traSkipButton);
			
			traWaitLabel=new JLabel("En attente de bracelet");
			traWaitLabel.setBackground(Colors.green1);
			traWaitLabel.setForeground(new Color(252,255,0));
			traWaitLabel.setFont(regular.deriveFont(24f));
			traWaitLabel.setBorder(null);
			traWaitLabel.setHorizontalAlignment(SwingConstants.CENTER);
			traWaitLabel.setBounds(980, 420, 300, 30);
			traWaitLabel.setVisible(false);
			mainPanel.add(traWaitLabel);
			
			traStateLabel=new JLabel("");
			traStateLabel.setBackground(Colors.green1);
			traStateLabel.setForeground(Color.white);
			traStateLabel.setHorizontalAlignment(SwingConstants.CENTER);
			traStateLabel.setFont(regular.deriveFont(24f));
			traStateLabel.setBorder(null);
			traStateLabel.setBounds(980, 475, 300, 30);
			mainPanel.add(traStateLabel);
			
			traPrecClientLabel = new JLabel("");
			traPrecClientLabel.setBackground(Colors.green1);
			traPrecClientLabel.setForeground(Color.white);
			traPrecClientLabel.setHorizontalAlignment(SwingConstants.CENTER);
			traPrecClientLabel.setFont(regular.deriveFont(24f));
			traPrecClientLabel.setBorder(null);
			traPrecClientLabel.setBounds(980, 450, 300, 30);
			mainPanel.add(traPrecClientLabel);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void initReaderInfo(boolean present){
		termPresent=present;
		if(present){
			terminalLabel.setText("Lecteur connect\u00e9");
			terminalLabel.setForeground(Colors.green1);
		}else{
			terminalLabel.setText("Lecteur d\u00e9connect\u00e9");
			terminalLabel.setForeground(Colors.red);
		}
	}
	
	private void updateClientsPanel(){
		if(selClientsId.size()!=0){
			client=allClients.get(selClientsId.get(0));
			displayedClientsId=selClientsId.subList(0, selClientsId.size());
			updateClientInfo(client);
		}else{
			if(justRemoved){
				displayedClientsId=selClientsId.subList(0, selClientsId.size());
				updateClientInfo(new Client("","","",BigDecimal.ZERO,""));
				justRemoved=false;
			}else{
				return;
			}
			}
			
		clientsPanel.removeAll();
		clientsPanel.revalidate();
		ligneList=new ArrayList<WbLoadingLigneClient>();
		WbLoadingLigneClient tempLigne;
		for(int i=0;i<Math.min(displayedClientsId.size(),32);i++){
			tempLigne=new WbLoadingLigneClient(allClients.get(displayedClientsId.get(i)),i,this);
			clientsPanel.add(tempLigne);
			ligneList.add(tempLigne);
		}
		clientsPanel.repaint();
	}
	
	protected void updateClientInfo(Client client){
		this.client=client;
		traNomLabel.setText(client.getNom().toUpperCase());
		traPrenomLabel.setText(client.getPrenom());
		traDelegationLabel.setText(client.getDelegation());
		traRoleLabel.setText(client.getRole());
		switch (client.getRole()){
		case "sport":
			authPanel.setAuths(true, true, true);
			break;
		case "supporter":
			authPanel.setAuths(true, true, true);
			break;
		case "fanfare":
			authPanel.setAuths(true, true, true);
			break;
		case "pompom":
			authPanel.setAuths(true, true, true);
			break;	
		case "soiree":
			authPanel.setAuths(false, false, false);
			break;
		case "accompagnateur":
			authPanel.setAuths(true, true, true);
			break;
		case "alumni":
			authPanel.setAuths(false, false, true);
			break;
		default:
			authPanel.setAuths(true, true, true);
			break;
		}
	}
	
	private void initServices(){
		resourceServiceImpl=(ResourceService)applicationContext.getBean("resourceServiceImpl");
		clientServiceImpl=(ClientService)applicationContext.getBean("clientServiceImpl");
	}
	
	private void initFonts(){
		try {
			light=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrow-Light.ttf").getFile());
			thin=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Thin.ttf").getFile());
			regular=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Regular.ttf").getFile());
			medium=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Medium.ttf").getFile());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private ActionListener ecout = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(traTraButton)){
				if(displayedClientsId.isEmpty()){
					return;
				}
				if(authPanel.getAuthString()=="222"){
					JOptionPane dial = new JOptionPane();
    				JOptionPane.showMessageDialog(dial,"Merci de rentrer des autorisations pour la soir\u00e9e","Erreur",JOptionPane.ERROR_MESSAGE);
    				return;
				}
				try{
					traTraButton.setEnabled(false);
					traSkipButton.setEnabled(false);
					boolean wbReplace=false;
					boolean clientReplace=false;
					Integer wbNb = Integer.parseUnsignedInt(wbNbTF.getText().trim());
					if(clientServiceImpl.wristbandExists(wbNb)){
						JOptionPane dial = new JOptionPane();
	    				if(JOptionPane.showConfirmDialog(dial,"Ce bracelet est d\u00e9j\u00e0 attribu\u00e9 \u00e0 une autre personne : \u00e9craser ?","Attention",JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION){
	    					traTraButton.setEnabled(true);
	    					traSkipButton.setEnabled(true);
	    					return;
	    				}
	    				wbReplace=true;
					}
					if(clientServiceImpl.hasWristband(client.getId())){
						JOptionPane dial = new JOptionPane();
	    				if(JOptionPane.showConfirmDialog(dial,"Cette personne a d\u00e9j\u00e0 un bracelet : remplacer ?","Attention",JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION){
	    					traTraButton.setEnabled(true);
	    					traSkipButton.setEnabled(true);
	    					return;
	    				}
	    				clientReplace=true;
					}
					transfert(client, wbNb, wbReplace, clientReplace);
				}catch(Exception ex){
					System.err.println(ex);
					JOptionPane dial = new JOptionPane();
    				JOptionPane.showMessageDialog(dial,"Merci de rentrer un nombre valide.","Erreur",JOptionPane.ERROR_MESSAGE);
    				traTraButton.setEnabled(true);
					traSkipButton.setEnabled(true);
    				return;
				}
				
			}else if(e.getSource().equals(traSkipButton)){
				if(displayedClientsId.isEmpty()){
					return;
				}
				if(selClientsId.isEmpty()){
					selClientsId=displayedClientsId.subList(0, displayedClientsId.size());
				}
				selClientsId.remove(new Integer(allClients.indexOf(client)));
				justRemoved=true;
				updateClientsPanel();
			}else if(e.getSource().equals(selResetButton)){
				selClientsId=new ArrayList<Integer>();
				for (int i=0;i<allClients.size();i++){
					selClientsId.add(i);
				}
				selNomTF.setText("");
				selPrenomTF.setText("");
				selDelegationTF.setText("");
				updateClientsPanel();
			}
			
		}
	
	};
	
	//METHODES POUR LE LECTEUR NFC
	
	private void initReader(){
		terminal = null;
		try{
			
			//SELECTION DU LECTEUR
	        TerminalFactory factory = TerminalFactory.getDefault();
	        List<CardTerminal> terminals = factory.terminals().list();
	        for (int i=0;i<terminals.size();i++){
	        	if (terminals.get(i).getName().substring(0, 3).equals("ACS")){
	        		terminal=terminals.get(i);
	        	}
	        }
	        
	        //SI PAS DE LECTEUR, ON LE SIGNIFIE A L IHM
	        if(terminal==null){
	        	initReaderInfo(false);
	        	System.out.println("Pas de lecteur");
	        	return;
	        }
	        
	        // LE LECTEUR EST PRESENT, ON LE SIGNIFIE A L IHM
	        initReaderInfo(true); 
	        System.out.println("Ca marche négro");    
		}catch(CardException e){
			System.err.println(e);
		}
	}
	
	private void transfert(Client client, Integer wbNb, boolean wbReplace, boolean clientReplace){
		SwingWorker<Boolean,Integer> readWorker = new SwingWorker<Boolean, Integer>(){

			@Override
			protected Boolean doInBackground() throws Exception {
				try {
					if(terminal==null){
						return false;
					}
					terminal.waitForCardAbsent(0);
					//NOTIFY THE GUI WE'RE WAITING FOR A CARD
					publish(1);
					if(!terminal.waitForCardPresent(10000)){
						// Notify the GUI no card was detected
						publish(2);
						return false;
					}
					
					//Notify the GUI we're working with the detected card
					publish(3);
					
					// CONNEXION A LA CARTE
					card = terminal.connect("T=1");
					channel = card.getBasicChannel();
					
					//AUTHENTIFICATION
					auth(4);
					
					//INSCRIPTION DU NOM EN BLOC 4
					write(client.getNom(),4);
					//INSCRIPTION PRENOM BLOC 5
					write(client.getPrenom(),5);
					//INSCRIPTION DELEGATION BLOC6
					write(client.getDelegation(),6);
					
					//VERIFICATION DES 3
					if(!read(4).equals(client.getNom().substring(0, Math.min(client.getNom().length(), 16)))){
						throw new ReaderException(3);
					}
					if(!read(5).equals(client.getPrenom().substring(0, Math.min(client.getPrenom().length(), 16)))){
						throw new ReaderException(3);
					}
					if(!read(6).equals(client.getDelegation().substring(0, Math.min(client.getDelegation().length(), 16)))){
						throw new ReaderException(3);
					}
					
					//RESTE DES INFOS DANS LES BLOCS SUIVANTS
					auth(8);
					
					//INSCRIPTION DU ID CODE PAR LE NOMBRE PREMIER 27691
					write(wbNb*27691+"",8);
					//INSCRIPTION AUTORISATIONS SOIREES
					write(authPanel.getAuthString(),9);
					//INSCRIPTION AUTORISATIONS REPAS
					write(authPanel.getAuthString(),10);
					//VERIFICATIONS
					if(!read(8).equals(wbNb*27691+"")){
						throw new ReaderException(3);
					}
					if(!read(9).equals(authPanel.getAuthString())){
						throw new ReaderException(3);
					}
					return true;
				} catch (CardException e) {
					if (e.getMessage().equals("connect() failed")){
						System.out.println("Juste un petit souci, np");
						//Notify the GUI we need to get going again
						publish(4);
					}else{
						System.err.println(e);
						JOptionPane dial = new JOptionPane();
						JOptionPane.showMessageDialog(dial,"Erreur sur le lecteur.\nVerifiez que le lecteur est bien branché et relancez le logiciel. \nSi le problème persiste, contactez le support technique.","Erreur",JOptionPane.ERROR_MESSAGE);
						//Notify the GUI we need to get going again with initReader
						publish(5);
					}
					
					return false;

				} catch (ReaderException e) {
					System.err.println(e);
					//Notify the GUI we need to get going again
					publish(4);
					return false;
				}
			}
			
			protected void done(){
				try {
					if(get()){
						clientServiceImpl.saveWb(client.getId(), wbNb, wbReplace, clientReplace);
						traStateLabel.setText("Transfert r\u00e9ussi !");
						traPrecClientLabel.setText(client.getNom().toUpperCase()+" "+client.getPrenom()+" : "+wbNb);
						if(selClientsId.isEmpty()){
							selClientsId=displayedClientsId.subList(0, displayedClientsId.size());
						}
						selClientsId.remove(new Integer(allClients.indexOf(client)));
						justRemoved=true;
						updateClientsPanel();
						wbNbTF.setText(""+(wbNb+1));
						traTraButton.setEnabled(true);
    					traSkipButton.setEnabled(true);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			protected void process(List<Integer> param){
				for(Integer p:param){
					switch (p){
					case 1 :
						traWaitLabel.setVisible(true);
						break;
					case 2 :
						traPrecClientLabel.setText("Aucun bracelet d\u00e9tect\u00e9.");
						traStateLabel.setText("Veuillez r\u00e9essayer");
						traWaitLabel.setVisible(false);
						traTraButton.setEnabled(true);
    					traSkipButton.setEnabled(true);
						break;
					case 3 :
						traWaitLabel.setVisible(false);
						break;
					case 4 :
						transfert(client,wbNb,wbReplace, clientReplace);
						break;
					case 5 :
						initReader();
						transfert(client,wbNb,wbReplace, clientReplace);
						break;
					}
				}
			}
		};
		readWorker.execute();
	}
	
	private String send(byte[] cmd, CardChannel channel) {

	    String res = "";

	    byte[] baResp = new byte[258];
	    ByteBuffer bufCmd = ByteBuffer.wrap(cmd);
	    ByteBuffer bufResp = ByteBuffer.wrap(baResp);

	    // output = The length of the received response APDU
	    int output = 0;

	    try {


	output = channel.transmit(bufCmd, bufResp);
	    } catch (CardException ex) {
	        ex.printStackTrace();
	    }

	    for (int i = 0; i < output; i++) {
	        res += String.format("%02X", baResp[i]);
	        // The result is formatted as a hexadecimal integer
	    }

	    return res;
	}
	
	private void auth(int bloc)throws ReaderException{
		byte blocByte = (byte)bloc;
		// ON S AUTHENTIFIE SUR LES 4 DU COUP
		//FF 86 0000 05 01 0004 60 00 Authenticate to Block 4
        byte[] read_four_to_seven = new byte[]{(byte) 0xFF, (byte) 0x86, (byte) 0x00,
                (byte) 0x00, (byte) 0x05, (byte) 0x01, (byte) 0x00, blocByte,
                (byte) 0x60, (byte) 0x00};
        String authResult = send(read_four_to_seven, channel);
        if(!authResult.substring(authResult.length()-4, authResult.length()).equals("9000")){
        	throw new ReaderException(ReaderException.AUTH_ERROR);
        }
        System.out.println("Authen : " + authResult );
	}
	
	private void write(String s,int bloc) throws ReaderException{
		byte blocByte = (byte)bloc;
		//byte bloc=(byte)String.format("0x%2s", Integer.toHexString(bloc).toUpperCase()).replace(' ', '0')
		// Write into blockXX FF D6 00 XX 10 + ajout de 16bit : 00 00 00 00 00 00 00 00 3A 77 88 99 FF AA BB CC
        byte[] write_base = new byte[] { (byte) 0xFF, (byte) 0xD6, (byte) 0x00,
        		blocByte, (byte) 0x10};
        byte[] message = new byte[21];
        System.arraycopy(write_base, 0, message, 0, 5);
        System.arraycopy(stringToByte(s), 0, message, 5, 16);
        
        String writeResult=send(message, channel);
        if(!writeResult.substring(writeResult.length()-4, writeResult.length()).equals("9000")){
        	throw new ReaderException(ReaderException.AUTH_ERROR);
        }else{
        	System.out.println("Writing successful : "+s+" on bloc "+bloc);
        }
	}
	
	private String read(int bloc) throws ReaderException{
		byte blocByte = (byte)bloc;
        // Read Block4 FF B0 00 04 10
        byte[] readFromPage = new byte[] { (byte) 0xFF, (byte) 0xB0, (byte) 0x00,
        		 blocByte, (byte) 0x10 };
        final String readResult = send(readFromPage, channel);
        if(!readResult.substring((readResult.length()-4), readResult.length()).equals("9000")){
        	throw new ReaderException(ReaderException.READ_ERROR);
        }
        return hexaToString(readResult.substring(0,32));
	}
	
	private static String hexaToString(String hexa){
		StringBuffer message = new StringBuffer("");
	    for(int i=0;i<hexa.length();i+=2){
	        int number = Integer.parseInt(hexa.substring(i, i+2),16);
	        message.append(java.lang.Character.toChars(number));
	    }
	    return message.toString().trim();
	}
	
	private static byte[] stringToByte(String s){
		byte hexa[]=new byte[16];
		for(int i=0;i<Math.min(s.length(),16);i++){
			hexa[i]=(byte)Character.codePointAt(s.toCharArray(), i);
		}
		if(s.length()<16){
			for(int i=s.length();i<16;i++){
				hexa[i]=(byte)0;
			}
		}
		return hexa;
	}
}
