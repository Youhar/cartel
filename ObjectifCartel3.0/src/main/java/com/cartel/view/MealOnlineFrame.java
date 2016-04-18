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
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.cartel.model.Client;
import com.cartel.nfc.ReaderException;
import com.cartel.service.ClientService;
import com.cartel.service.ResourceService;
import com.cartel.service.StatsService;

public class MealOnlineFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2119930057210952058L;

	private BackgroundPanel mainPanel;
	private JLabel terminalLabel;
	private PartyChoicePanel choicePanel;
	private JPanel cliPanel;
	private JLabel cliNomLabel;
	private JLabel cliPrenomLabel;
	private JLabel cliDelegationLabel;
	private JLabel cliAuthLabel;
	private FlatButton okBtn;
	private JLabel cardStateLabel;
	private boolean running=false;
	private JLabel clientPictureLabel;
	
	private Font medium;
	private Font light;
	
	private boolean termPresent;
	private CardTerminal terminal;
	private CardChannel channel;
	private Card card;
	
	private ResourceService resourceServiceImpl;
	private ClientService clientServiceImpl;
	private StatsService statsServiceImpl;
	private ApplicationContext applicationContext;
	
	private Client client;
	private int authorized=0;
	private int tonight=0;
	
	@Autowired
	public MealOnlineFrame(ApplicationContext applicationContext){
		super();
		this.applicationContext=applicationContext;
		initServices();
		initFonts();
		this.setTitle("Cartel-ControleRepas");
		this.getContentPane().setBackground(Color.white);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) ((dimension.getWidth() - 1300) / 2),(int) ((dimension.getHeight() - 725) / 2),1300,725);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initMainPanel();
		initReader();
		
		//ICONE
		try {
			Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/icon.png").getInputStream());
			this.setIconImage(img);
		} catch (IOException ex) {
		}
			
		revalidate();
		repaint();
		this.setVisible(true);
	}
	
	public void initMainPanel(){
		//FOND
		try {
			mainPanel=new BackgroundPanel(resourceServiceImpl.getResource("img/CartelEntranceMeal.png").getInputStream());
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
			
			//CHOIX SOIREE
			okBtn = new FlatButton("LANCER");
			okBtn.setForeground(Color.white);
			okBtn.setBackground(Colors.blue1);
			okBtn.setHoverBackgroundColor(Colors.blue1);
			okBtn.setPressedBackgroundColor(Colors.blue3);
			okBtn.setBorder(null);
			okBtn.setFont(medium.deriveFont(48f));
			okBtn.setBounds(80,360,180,100);
			okBtn.addActionListener(mainListener);
			okBtn.setEnabled(false);
			mainPanel.add(okBtn);
			
			choicePanel = new PartyChoicePanel(medium,okBtn);
			mainPanel.add(choicePanel);
			
			cardStateLabel=new JLabel("");
			cardStateLabel.setForeground(Colors.blue1);
			cardStateLabel.setFont(medium.deriveFont(30f));
			cardStateLabel.setHorizontalAlignment(SwingConstants.CENTER);
			cardStateLabel.setBounds(20, 595, 300, 30);
			mainPanel.add(cardStateLabel);
			
			//AFFICHAGE CLIENT
			cliPanel=new JPanel();
			cliPanel.setBackground(Color.white);
			cliPanel.setLayout(null);
			cliPanel.setBounds(340, 60, 940, 620);
			mainPanel.add(cliPanel);
			
			cliNomLabel = new JLabel("");
			cliNomLabel.setForeground(Color.white);
			cliNomLabel.setFont(medium.deriveFont(72f));
			cliNomLabel.setHorizontalAlignment(SwingConstants.CENTER);
			cliNomLabel.setBounds(320, 146,620, 50);
			cliPanel.add(cliNomLabel);
			
			cliPrenomLabel = new JLabel("");
			cliPrenomLabel.setForeground(Color.white);
			cliPrenomLabel.setFont(medium.deriveFont(72f));
			cliPrenomLabel.setHorizontalAlignment(SwingConstants.CENTER);
			cliPrenomLabel.setBounds(320, 208, 620, 50);
			cliPanel.add(cliPrenomLabel);
			
			cliDelegationLabel = new JLabel("");
			cliDelegationLabel.setForeground(Color.white);
			cliDelegationLabel.setFont(medium.deriveFont(72f));
			cliDelegationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			cliDelegationLabel.setBounds(320, 320, 620, 50);
			cliPanel.add(cliDelegationLabel);
			
			clientPictureLabel=new JLabel();
			clientPictureLabel.setBounds(20, 106, 315,  405);
			cliPanel.add(clientPictureLabel);
			
			cliAuthLabel = new JLabel("");
			cliAuthLabel.setForeground(Color.white);
			cliAuthLabel.setFont(medium.deriveFont(72f));
			cliAuthLabel.setHorizontalAlignment(SwingConstants.CENTER);
			cliAuthLabel.setBounds(320, 460, 620, 50);
			cliPanel.add(cliAuthLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void manageAuths(int auth){
		int choice=choicePanel.getChoice();
		if((auth+"").substring(choice-1, choice).equals("1")){
			updateAuthPanel(1);
			statsServiceImpl.mealAddPerson(1);
		}else if((auth+"").substring(choice-1, choice).equals("2")){
			updateAuthPanel(2);
		}else{
			updateAuthPanel(3);
		}
	}
	
	private void updateAuthPanel(int i){
		authorized=i;
		if(authorized==1){
			cliPanel.setBackground(Colors.green1);
			cliAuthLabel.setText("AUTORIS\u00c9(E)");
		}else if(authorized==2){
			cliPanel.setBackground(Colors.red);
			cliAuthLabel.setText("NON AUTORIS\u00c9(E)");
		}else{
			cliPanel.setBackground(Colors.orange);
			cliAuthLabel.setText("D\u00c9J\u00c0 MANG\u00c9");
		}
	}
	
	public void initServices(){
		resourceServiceImpl=(ResourceService)applicationContext.getBean("resourceServiceImpl");
		clientServiceImpl=(ClientService)applicationContext.getBean("clientServiceImpl");
		statsServiceImpl=(StatsService)applicationContext.getBean("statsServiceImpl");
	}
	
	private void initFonts(){
		try {
			light=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Light.ttf").getInputStream());
			medium=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Medium.ttf").getInputStream());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void initReaderInfo(boolean present){
		termPresent=present;
		if(termPresent){
			terminalLabel.setText("Lecteur connect\u00e9");
			terminalLabel.setForeground(Colors.green1);
		}else{
			terminalLabel.setText("Lecteur d\u00e9connect\u00e9");
			terminalLabel.setForeground(Colors.red);
		}
	}
	
	private ActionListener mainListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource().equals(okBtn)){
				if(running){
					transfert();
				}else{
					okBtn.setText("EN COURS");
					okBtn.setFont(medium.deriveFont(38f));
					running=true;
					transfert();
					choicePanel.disableAll();
				}
				
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
	
	private void transfert(){
		tonight=choicePanel.getChoice();
		SwingWorker<Client,Integer> readWorker = new SwingWorker<Client, Integer>(){

			@Override
			protected Client doInBackground() throws Exception {
				try {
					if(terminal==null){
						return null;
					}
					publish(10);
					terminal.waitForCardAbsent(0);
					//NOTIFY THE GUI WE'RE WAITING FOR A CARD
					publish(1);
					if(!terminal.waitForCardPresent(10000000)){
						// Notify the GUI no card was detected
						publish(2);
						return null;
					}
					
					//Notify the GUI we're working with the detected card
					publish(3);
					
					// CONNEXION A LA CARTE
					card = terminal.connect("T=1");
					channel = card.getBasicChannel();
					
					//AUTHENTIFICATION
					auth(4);
					
					//LECTURE
					String nom=read(4);
					String prenom=read(5);
					String delegation=read(6);
					
					//RESTE DES INFOS DANS LES BLOCS SUIVANTS
					auth(8);
					
					//LECTURE DU ID CODE PAR LE NOMBRE PREMIER 27691
					Integer id=Integer.parseInt(read(8))/27691;
					//LECTURE AUTORISATIONS REPAS
					String auths=read(10);
					
					//ECRITURE DU FAIT QUE LA PERSONNE AIT MANGE
					if(auths.substring(tonight-1, tonight).equals("1")){
						String wri = auths.substring(0,tonight-1)+"3"+auths.substring(tonight,3);
						write(wri,10);
					}
					
					//Notify the GUI of authorizations
					publish(Integer.parseInt(auths));
					
					Client monClient = new Client(nom,prenom,delegation,BigDecimal.ZERO,"");
					monClient.setId(id);
					return monClient;
					
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
					
					return null;

				} catch (ReaderException e3) {
					System.err.println(e3);
					//Notify the GUI we need to get going again
					publish(4);
					return null;
				} catch (NumberFormatException e2){
					System.err.println(e2);
					//Notify the GUI we need to get going again
					publish(4);
					return null;
				} catch (java.lang.StringIndexOutOfBoundsException e4){
					//Notify the GUI we need to get going again
					publish(4);
					return null;
				}
			}
			
			protected void done(){
				try {
					if(get()==null){
						cardStateLabel.setText("Erreur lecteur");
					}else{
						client=get();
						Integer wbId = client.getId();
						client.setId(clientServiceImpl.getFromWristband(wbId).getId());
						cliNomLabel.setText(client.getNom().toUpperCase());
						cliPrenomLabel.setText(client.getPrenom());
						cliDelegationLabel.setText(client.getDelegation());
						clientPictureLabel.setIcon(new ImageIcon(new ImageIcon(Client.getPhotoPath(client.getId())).getImage().getScaledInstance(315, 405, java.awt.Image.SCALE_SMOOTH)));
					}
				} catch (InterruptedException e) {

					e.printStackTrace();
				} catch (ExecutionException e) {

					e.printStackTrace();
				}
				cardStateLabel.setText("");
				transfert();
			}
			
			@Override
			protected void process(List<Integer> param){
				for(Integer p:param){
					if(p>100){
						manageAuths(p);
					}else{
						switch (p){
						case 1 :
							cardStateLabel.setText("Pr\u00e9sentez un bracelet...");
							break;
						case 2 :
							cardStateLabel.setText("Pas de carte détect\u00e9e");
							break;
						case 3 :
							cardStateLabel.setText("Lecture du bracelet...");
							break;
						case 4 :
							transfert();
							break;
						case 5 :
							initReader();
							transfert();
							break;
						case 10 :
							cardStateLabel.setText("Patientez...");
							break;
						}
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
