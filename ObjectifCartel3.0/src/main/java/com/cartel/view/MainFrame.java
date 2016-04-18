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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.cartel.model.Client;
import com.cartel.model.Conso;
import com.cartel.model.Credit;
import com.cartel.model.Debit;
import com.cartel.model.Euros;
import com.cartel.model.Operateur;
import com.cartel.model.Panier;
import com.cartel.model.TransactAdmin;
import com.cartel.model.Transaction;
import com.cartel.nfc.ReaderException;
import com.cartel.service.ClientService;
import com.cartel.service.ConsoService;
import com.cartel.service.OperateurService;
import com.cartel.service.ResourceService;
import com.cartel.service.TransactionService;

public class MainFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6937563055893171650L;
	

	ApplicationContext applicationContext;
	OperateurService operateurServiceImpl;
	ResourceService resourceServiceImpl;
	TransactionService transactionServiceImpl;
	ClientService clientServiceImpl;
	ConsoService consoServiceImpl;
	
	private Operateur operateur;
	private Client client;
	private Panier monPanier;
	private List<Conso> consoList;
	private List<ConsoBtn> listConsoBtn;

	private JPanel operateurPanel;
	private JLabel connectedLabel;
	private JPanel ligneTransactionPanel;
	BackgroundPanel mainPanel;
	FlatButton disconnectButton;
	LigneTransaction premiereLigneTransaction;
	List<LigneTransaction> listeLigneTransaction;
	
	private JPanel readerPanel;
	private JTextField numberTF;
	private FlatButton okButton;
	private JLabel stateLabel;
	private JLabel lecteurLabel;
	
	private boolean termPresent;
	private CardTerminal terminal;
	private CardChannel channel;
	private Card card;
	private SwingWorker<Integer,Integer> readWorker;
	
	private JLabel nomLabel;
	private JLabel prenomLabel;
	private JLabel nbLabel;
	private JLabel numLabel;
	private JLabel dateLabel;
	private JLabel montantLabel;
	private JLabel curSoldeLabel;
	private JLabel barreCanceledLabel;
	private JLabel textCanceledLabel;
	private PanierPanel detailsPanierPanel;
	
	private BackgroundPanel barmanOrderingPanel;
	private JLabel clientPictureLabel;
	private JLabel clientPrenomLabel;
	private JLabel clientNomLabel;
	private JLabel addTotalLabel;
	private JLabel addCurSoldeLabel;
	private JLabel barValRestantLabel;
	private FlatButton barValValButton;
	private FlatButton barValCanButton;
	private JPanel ligneItemPanel;
	
	private BackgroundPanel crediteurOrderingPanel;
	private ModePaiementPanel creModePaiementPanel;
	FlatButton creValValButton;
	private FlatButton creValCanButton;
	private FlatButton creAddResetButton;
	private JLabel creAddCurSoldeLabel;
	private JLabel creValFinalSoldeLabel;
	private JTextField creAddUTF;
	private JTextField creAddDTF;
	
	private BackgroundPanel adminFundingPanel;
	private JPanel adminTabsPanel;
	private JTextField selIdTF;
	private JTextField selNomTF;
	private JTextField selPrenomTF;
	private JTextField selDelegationTF;
	private JLabel selReaderInfo;
	private FlatButton selResetButton;
	private List<Integer> selClientsId;
	private List<Integer> displayedClientsId;
	private List<Client> allClients;
	private JLabel clientDelegationLabel;
	private JLabel adminOpSignLabel;
	private FlatButton adminFundingValButton;
	private boolean adminFundingJustRemoved=false;
	List<LigneClientAdminFunding> adminFundingLigneList;
	private JPanel adminLignesPanel;
	private TabButton tab1Btn;
	private TabButton tab2Btn;
	private TabButton tab3Btn;
	
	private FlatButton adminLoadImageButton;
	private JLabel clientIdLabel;
	private BackgroundPanel adminAddBotPanel;
	private BackgroundPanel adminBot1Panel;
	private BackgroundPanel adminBot2Panel;
	private FlatButton adminBot3Button;
	private BackgroundPanel adminBot3Panel;
	private Integer adminAddStage;
	private File adminAddImageFile;
	private FlatButton adminAddResetButton;
	private JLabel selIdLbl;
	
	private FlatToggleButton adminTransTypeButton;
	private JRadioButton adminTransType1RB;
	private JRadioButton adminTransType2RB;
	private JRadioButton adminTransType3RB;
	private FlatToggleButton adminTransOpeButton;
	private JComboBox<Operateur> adminTransOpeCB;
	private FlatToggleButton adminTransClButton;
	private JTextField adminTransClTF;
	private JLabel adminTransClLbl;
	private FlatButton adminTransClBtn;
	private FlatToggleButton adminTransBarButton;
	private JRadioButton adminTransBar1RB;
	private JRadioButton adminTransBar2RB;
	private FlatButton adminTransOKBtn;
	private FlatButton adminTrans1PageButton;
	private FlatButton adminTransPrevPageButton;
	private FlatButton adminTransNextPageButton;
	private FlatButton adminTrans10PageButton;
	private JTextField adminTransPageTF;
	private FlatButton adminTransGoPageButton;
	private JPanel adminTransPanel;
	private List<Transaction> allTransactions;
	private List<Transaction> currentTransactions;
	private Integer adminCurPage;
	
	Font normal;
	Font light;
	Font thin;
	Font medium;
	Font mediumIt;
	
	@Autowired
	public MainFrame(ApplicationContext applicationContext){
		this.applicationContext=applicationContext;

		this.setTitle("CartelPay");
		this.getContentPane().setBackground(Color.white);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) ((dimension.getWidth() - 1300) / 2),(int) ((dimension.getHeight() - 725) / 2),1300,725);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	if(operateur!=null){
		        	operateurServiceImpl.disconnect(operateur.getPseudo());
					transactionServiceImpl.clearTransactionLocal();
		            System.exit(0);	
	        	}
	        }
	    });
		initServices();
		initFonts();
		initOperateurPanel();
		
		//ICONE
		try {
			Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/icon.png").getInputStream());
			this.setIconImage(img);
		} catch (IOException ex) {
		}
	}
	
	public void prepare(Operateur operateur){
		
		this.operateur=operateur;
		String poste="";
		switch(operateur.getRole()){
		case 1:
			poste="administrateur";
			initAdminTabsPanel();
			initAdminFundingPanel();
			this.revalidate();
			this.repaint();
			break;
		case 2:
			initReaderPanel();
			poste="cr\u00e9diteur";
			initMainPanel();
			this.revalidate();
			this.repaint();
			break;
		case 3: case 4:
			initReaderPanel();
			poste="barman"+(operateur.getRole()-2);
			initMainPanel();
			this.revalidate();
			this.repaint();
			break;			
		}
		connectedLabel.setText("Connect\u00e9 en tant que "+operateur.getPseudo()+" au poste de "+poste+".");
	}
	
	private void initOperateurPanel(){
		operateurPanel=new JPanel();
		operateurPanel.setBounds(0, 0, 1300, 40);
		operateurPanel.setBackground(Colors.green1);
		operateurPanel.setLayout(null);
		this.add(operateurPanel);
		
		connectedLabel=new JLabel("Connect\u00e9 en tant que 14xxxxxx au poste de XXXXXXX");
		connectedLabel.setForeground(Color.white);
		connectedLabel.setBounds(20,10,600,20);
		connectedLabel.setFont(light);
		connectedLabel.setFont(connectedLabel.getFont().deriveFont((float) 24.0));
		operateurPanel.add(connectedLabel);
		
		disconnectButton = new FlatButton("Se d\u00e9connecter");

		disconnectButton.setFont(thin);

		disconnectButton.setFont(disconnectButton.getFont().deriveFont((float) 22.0));
		disconnectButton.setBackground(Colors.green1);
		disconnectButton.setHoverBackgroundColor(Colors.green1);
		disconnectButton.setPressedBackgroundColor(Colors.green2);
		disconnectButton.setForeground(Color.white);
		disconnectButton.setBounds(1139, 5, 150, 30);
		disconnectButton.setFocusable(false);
		disconnectButton.addActionListener(mainButtonListener);
		operateurPanel.add(disconnectButton);
		
	}
	
	private void initReaderPanel(){		
		readerPanel = new JPanel();
		readerPanel.setBounds(0, 40, 1300, 100);
		readerPanel.setBackground(Colors.blue1);
		readerPanel.setLayout(null);
		this.add(readerPanel);
		
		JLabel cartelLabel = new JLabel("CARTEL");
		cartelLabel.setForeground(Color.white);
		cartelLabel.setBounds(30, 30, 170, 40);
		cartelLabel.setFont(thin);
		cartelLabel.setFont(cartelLabel.getFont().deriveFont((float) 46.0));
		cartelLabel.setFont(cartelLabel.getFont().deriveFont(Font.BOLD));
		readerPanel.add(cartelLabel);
		
		JLabel codeLabel = new JLabel("code client");
		codeLabel.setForeground(Color.white);
		codeLabel.setBounds(130, 30, 600, 40);
		codeLabel.setFont(light);
		codeLabel.setFont(codeLabel.getFont().deriveFont((float) 30.0));
		codeLabel.setHorizontalAlignment(JLabel.RIGHT);
		readerPanel.add(codeLabel);
		
		numberTF=new JTextField();
		numberTF.setColumns(8);
		numberTF.setFont(normal);
		numberTF.setFont(numberTF.getFont().deriveFont((float)30.0));
		numberTF.setBackground(Colors.blue1);
		numberTF.setForeground(Color.white);
		numberTF.setBounds(750, 30, 130, 40);
		readerPanel.add(numberTF);
		
		okButton = new FlatButton("OK");
		okButton.setBackground(Colors.blue1);
		okButton.setAlignmentX(CENTER_ALIGNMENT);
		okButton.setFont(normal.deriveFont((float)25.0).deriveFont(Font.BOLD));
		okButton.setForeground(Color.white);
		okButton.setPressedBackgroundColor(Colors.blue2);
		okButton.setHoverBackgroundColor(Colors.blue1);
		okButton.setBorder(new LineBorder(Color.white,1));
		okButton.addActionListener(mainButtonListener);
		okButton.setBounds(900, 30, 70, 40);
		readerPanel.add(okButton);
		
		JPanel statePanel = new JPanel();
		statePanel.setBounds(1130, 0, 170, 100);
		statePanel.setBackground(Colors.blue2);
		statePanel.setLayout(null);
		readerPanel.add(statePanel);
		
		lecteurLabel= new JLabel("LECTEUR");
		lecteurLabel.setFont(normal.deriveFont((float)25.0).deriveFont(Font.BOLD));
		lecteurLabel.setBounds(20,30,130,20);
		lecteurLabel.setForeground(Colors.red);
		lecteurLabel.setHorizontalAlignment(JLabel.CENTER);
		statePanel.add(lecteurLabel);
		
		stateLabel=new JLabel("D\u00e9connect\u00e9");
		stateLabel.setFont(normal.deriveFont((float)25.0).deriveFont(Font.BOLD));
		stateLabel.setBounds(20, 50, 130, 30);
		stateLabel.setForeground(Colors.red);
		stateLabel.setHorizontalAlignment(JLabel.CENTER);
		statePanel.add(stateLabel);
		
		// Thread lecteur
		initReader();
		transfert();

	}
	
	protected void initMainPanel(){
		try {
			mainPanel = new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/MainFramePanel.png").getInputStream());
			mainPanel.setBounds(0,140,1300,560);
			mainPanel.setLayout(null);
			this.add(mainPanel);
			
			//Details : right Panel
			Font detFont = medium.deriveFont(30f);
			
			nomLabel=new JLabel();
			nomLabel.setBounds(1050,63,200,40);
			nomLabel.setForeground(Colors.green3);
			nomLabel.setFont(detFont);
			mainPanel.add(nomLabel);
			
			prenomLabel=new JLabel();
			prenomLabel.setBounds(1050,93,200,40);
			prenomLabel.setForeground(Colors.green3);
			prenomLabel.setFont(detFont);
			mainPanel.add(prenomLabel);
			
			nbLabel=new JLabel();
			nbLabel.setBounds(1050,132,200,40);
			nbLabel.setForeground(Colors.green3);
			nbLabel.setFont(detFont);
			mainPanel.add(nbLabel);
			
			numLabel=new JLabel();
			numLabel.setBounds(1050,185,200,40);
			numLabel.setForeground(Colors.green3);
			numLabel.setFont(detFont);
			mainPanel.add(numLabel);
			
			dateLabel=new JLabel();
			dateLabel.setBounds(1050,213,200,40);
			dateLabel.setForeground(Colors.green3);
			dateLabel.setFont(detFont);
			mainPanel.add(dateLabel);
			
			montantLabel=new JLabel();
			montantLabel.setBounds(1050, 370, 200, 40);
			montantLabel.setForeground(Colors.green3);
			montantLabel.setFont(detFont);
			mainPanel.add(montantLabel);
			
			curSoldeLabel=new JLabel();
			curSoldeLabel.setBounds(1050,410,200,40);
			curSoldeLabel.setFont(detFont);
			curSoldeLabel.setForeground(Colors.green3);
			mainPanel.add(curSoldeLabel);
			
			barreCanceledLabel=new JLabel();
			barreCanceledLabel.setBackground(Colors.red);
			barreCanceledLabel.setBounds(1050,388,100,4);
			barreCanceledLabel.setVisible(false);
			barreCanceledLabel.setBorder(new LineBorder(Colors.red,2));
			mainPanel.add(barreCanceledLabel);
			
			textCanceledLabel = new JLabel("ANNUL\u00C9");
			textCanceledLabel.setForeground(Colors.red);
			textCanceledLabel.setFont(detFont.deriveFont(Font.BOLD));
			textCanceledLabel.setBounds(1160, 395,150, 40);
			textCanceledLabel.setVisible(false);
			mainPanel.add(textCanceledLabel);
			
			detailsPanierPanel = new PanierPanel(consoServiceImpl,medium);
			mainPanel.add(detailsPanierPanel);
			
			
			//Transactions
			ligneTransactionPanel = new JPanel();
			listeLigneTransaction=new ArrayList<LigneTransaction>();
			ligneTransactionPanel.setLayout(null);
			ligneTransactionPanel.setBackground(Color.white);
			ligneTransactionPanel.setBounds(340, 60, 620, 480);
			mainPanel.add(ligneTransactionPanel);
			LigneTransaction ligneTransaction;
			if(transactionServiceImpl.getTransactionLocal().isEmpty()){
				JPanel blankPanel=new JPanel();
				blankPanel.setBackground(Color.white);
				blankPanel.setBorder(null);
				blankPanel.setBounds(980, 60, 300, 480);
				mainPanel.add(blankPanel);
			}
			for (int i=0; i<transactionServiceImpl.getTransactionLocal().size(); i++) {
				if(i==0){
					premiereLigneTransaction = new LigneTransaction(i,transactionServiceImpl.getTransactionLocal().get(i),this,resourceServiceImpl, transactionServiceImpl);
					ligneTransactionPanel.add(premiereLigneTransaction);
					premiereLigneTransaction.forceBorder(true);
					listeLigneTransaction.add(premiereLigneTransaction);
					updateDetails(transactionServiceImpl.getTransactionLocal().get(i));
				}else{
					ligneTransaction = new LigneTransaction(i,transactionServiceImpl.getTransactionLocal().get(i),this,resourceServiceImpl, transactionServiceImpl);
					ligneTransactionPanel.add(ligneTransaction);
					listeLigneTransaction.add(ligneTransaction);
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	protected void initBarmanOrderingPanel(Client c){
		monPanier = new Panier();
		this.client=c;
		consoList = consoServiceImpl.getAll();
		try{
			barmanOrderingPanel = new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/BarmanOrderingFrame.png").getInputStream());
			barmanOrderingPanel.setBounds(0,40,1300,660);
			barmanOrderingPanel.setLayout(null);
			this.add(barmanOrderingPanel);
			
			initClientOrderingPanel(client);
			
			// Boutons Conso
			ConsoBtn consoBtn;
			listConsoBtn=new ArrayList<ConsoBtn>();
			for(int i=0;i<consoList.size();i++){
				consoBtn=new ConsoBtn(consoList.get(i),client.getEurosSolde(),i, medium);
				barmanOrderingPanel.add(consoBtn);
				consoBtn.addActionListener(consoBtnListener);
				listConsoBtn.add(consoBtn);
			}
			// Addition
			addTotalLabel=new JLabel(new Euros(0).toString());
			addTotalLabel.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			addTotalLabel.setForeground(Colors.blue2);
			addTotalLabel.setBounds(825, 425, 150, 30);
			barmanOrderingPanel.add(addTotalLabel);
			
			addCurSoldeLabel=new JLabel(client.getEurosSolde().toString());
			addCurSoldeLabel.setFont(medium.deriveFont(36f));
			addCurSoldeLabel.setForeground(Colors.blue2);
			addCurSoldeLabel.setHorizontalAlignment(JLabel.CENTER);
			addCurSoldeLabel.setBounds(740, 555, 150, 30);
			barmanOrderingPanel.add(addCurSoldeLabel);
			
			// Validation
			barValRestantLabel = new JLabel(client.getEurosSolde().toString());
			barValRestantLabel.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			barValRestantLabel.setForeground(Colors.blue2);
			barValRestantLabel.setBounds(1160, 425, 150, 30);
			barmanOrderingPanel.add(barValRestantLabel);
			
			barValValButton = new FlatButton("Valider");
			barValValButton.setHoverBackgroundColor(Colors.green1);
			barValValButton.setBackground(Colors.green1);
			barValValButton.setPressedBackgroundColor(Colors.green2);
			barValValButton.setBorder(null);
			barValValButton.setForeground(Color.white);
			barValValButton.setFont(medium.deriveFont(30f));
			barValValButton.setBounds(1030,490,200,50);
			barValValButton.addActionListener(mainButtonListener);
			barmanOrderingPanel.add(barValValButton);
			
			barValCanButton = new FlatButton("Annuler");
			barValCanButton.setHoverBackgroundColor(Colors.red);
			barValCanButton.setBackground(Colors.red);
			barValCanButton.setPressedBackgroundColor(Colors.red2);
			barValCanButton.setBorder(null);
			barValCanButton.setForeground(Color.white);
			barValCanButton.setFont(medium.deriveFont(30f));
			barValCanButton.setBounds(1030,570,200,50);
			barValCanButton.addActionListener(mainButtonListener);
			barmanOrderingPanel.add(barValCanButton);
			
			//Panier
			ligneItemPanel = new JPanel();
			ligneItemPanel.setBackground(Color.white);
			ligneItemPanel.setLayout(null);
			ligneItemPanel.setBounds(20, 380, 620, 260);
			barmanOrderingPanel.add(ligneItemPanel);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void initCrediteurOrderingPanel(Client c){
		this.client=c;

		//BACKGROUND
		try {
			crediteurOrderingPanel = new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/CrediteurOrderingPanel.png").getInputStream());
			crediteurOrderingPanel.setBounds(0,40,1300,660);
			crediteurOrderingPanel.setLayout(null);
			this.add(crediteurOrderingPanel);

			initClientOrderingPanel(client);
			
			//VALEURS CLASSIQUES
			BigDecimal bd=new BigDecimal(10f);
			for(int i=0;i<3;i++){
				crediteurOrderingPanel.add(new CashButton(new BigDecimal(5).multiply(bd),medium, cashButtonListener));
				crediteurOrderingPanel.add(new CashButton(new BigDecimal(2).multiply(bd),medium, cashButtonListener));
				crediteurOrderingPanel.add(new CashButton(new BigDecimal(1).multiply(bd),medium, cashButtonListener));
				bd=bd.divide(BigDecimal.TEN);
			}
			
			//ADDITION
			creAddResetButton=new FlatButton("reset");
			creAddResetButton.setFont(medium.deriveFont(30f).deriveFont(Font.BOLD));
			creAddResetButton.setBackground(Colors.blue2);
			creAddResetButton.setHoverBackgroundColor(Colors.blue2);
			creAddResetButton.setForeground(Color.white);
			creAddResetButton.setPressedBackgroundColor(Color.white);
			creAddResetButton.setBorder(new LineBorder(Colors.blue2,2));
			creAddResetButton.setBounds(700,445,70,40);
			creAddResetButton.addActionListener(mainButtonListener);
			crediteurOrderingPanel.add(creAddResetButton);
			
			creAddUTF=new JFormattedTextField(createFormatter("##"));
			creAddUTF.setText("00");
			creAddUTF.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			creAddUTF.setBackground(Color.white);
			creAddUTF.setForeground(Colors.blue2);
			creAddUTF.setBorder(new LineBorder(Colors.blue2,1));
			creAddUTF.setBounds(790, 445, 45, 40);
			crediteurOrderingPanel.add(creAddUTF);
			
			creAddUTF.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					BigDecimal ajout;
					try {
						ajout=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						if(creAddUTF.getCaretPosition()==2){
							creAddUTF.transferFocus();
							creAddDTF.setCaretPosition(0);
						}
					} catch(NumberFormatException exc) {
							creAddUTF.setText(creAddUTF.getText().trim()+"0");
							creAddUTF.setCaretPosition(1);
							ajout=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						}
					creValFinalSoldeLabel.setText(new Euros(client.getSolde().add(ajout)).toString());
					creValFinalSoldeLabel.repaint();
				}
			});
			
			creAddDTF=new JFormattedTextField(createFormatter("##"));
			creAddDTF.setText("00");
			creAddDTF.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			creAddDTF.setBackground(Color.white);
			creAddDTF.setForeground(Colors.blue2);
			creAddDTF.setBorder(new LineBorder(Colors.blue2,1));
			creAddDTF.setBounds(850, 445, 45, 40);
			crediteurOrderingPanel.add(creAddDTF);
			
			creAddDTF.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					BigDecimal ajout;
					try {
						ajout=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						} catch(NumberFormatException exc) {
							creAddDTF.setText(creAddDTF.getText().trim()+"0");
							creAddDTF.setCaretPosition(1);
							ajout=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						}
					creValFinalSoldeLabel.setText(new Euros(client.getSolde().add(ajout)).toString());
					creValFinalSoldeLabel.repaint();
				}
			});
			
			JLabel virgLbl = new JLabel(",");
			virgLbl.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			virgLbl.setBackground(Color.white);
			virgLbl.setForeground(Colors.blue2);
			virgLbl.setBorder(null);
			virgLbl.setHorizontalTextPosition(SwingConstants.CENTER);
			virgLbl.setBounds(835,445,15,40);
			crediteurOrderingPanel.add(virgLbl);
			
			JLabel euroLbl = new JLabel("\u20AC");
			euroLbl.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			euroLbl.setBackground(Color.white);
			euroLbl.setForeground(Colors.blue2);
			euroLbl.setBorder(null);
			euroLbl.setBounds(900,445,40,40);
			crediteurOrderingPanel.add(euroLbl);
			
			creAddCurSoldeLabel=new JLabel(client.getEurosSolde().toString());
			creAddCurSoldeLabel.setFont(medium.deriveFont(36f));
			creAddCurSoldeLabel.setBackground(Color.white);
			creAddCurSoldeLabel.setForeground(Colors.blue2);
			creAddCurSoldeLabel.setBorder(null);
			creAddCurSoldeLabel.setHorizontalAlignment(JLabel.CENTER);
			creAddCurSoldeLabel.setBounds(710, 550, 200, 40);
			crediteurOrderingPanel.add(creAddCurSoldeLabel);
			
			// VALIDATION
			
			creValFinalSoldeLabel=new JLabel(client.getEurosSolde().toString());
			creValFinalSoldeLabel.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			creValFinalSoldeLabel.setBackground(Color.white);
			creValFinalSoldeLabel.setForeground(Colors.blue2);
			creValFinalSoldeLabel.setBorder(null);
			creValFinalSoldeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			creValFinalSoldeLabel.setBounds(1030, 445, 200, 40);
			crediteurOrderingPanel.add(creValFinalSoldeLabel);
			
			creValValButton=new FlatButton("Valider");
			creValValButton.setFont(medium.deriveFont(30f));
			creValValButton.setForeground(Color.white);
			creValValButton.setBackground(Colors.green1);
			creValValButton.setHoverBackgroundColor(Colors.green1);
			creValValButton.setPressedBackgroundColor(Colors.green2);
			creValValButton.setBorder(null);
			creValValButton.setEnabled(false);
			creValValButton.setBounds(1030,490,200,50);
			creValValButton.addActionListener(mainButtonListener);
			crediteurOrderingPanel.add(creValValButton);
			
			creValCanButton = new FlatButton("Annuler");
			creValCanButton.setHoverBackgroundColor(Colors.red);
			creValCanButton.setBackground(Colors.red);
			creValCanButton.setPressedBackgroundColor(Colors.red2);
			creValCanButton.setBorder(null);
			creValCanButton.setForeground(Color.white);
			creValCanButton.setFont(medium.deriveFont(30f));
			creValCanButton.setBounds(1030,570,200,50);
			creValCanButton.addActionListener(mainButtonListener);
			crediteurOrderingPanel.add(creValCanButton);
			
			//MODE DE PAIEMENT
			creModePaiementPanel = new ModePaiementPanel(creValValButton,resourceServiceImpl);
			crediteurOrderingPanel.add(creModePaiementPanel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initClientOrderingPanel(Client c){
		// PHOTO
		clientPictureLabel=new JLabel(new ImageIcon(new ImageIcon(Client.getPhotoPath(client.getId())).getImage().getScaledInstance(105, 135, java.awt.Image.SCALE_SMOOTH)));
		clientPictureLabel.setBounds(1078, 80, 105,  135);
		
		//Infos
		clientPrenomLabel=new JLabel(client.getPrenom());
		clientPrenomLabel.setFont(medium.deriveFont(35f));
		clientPrenomLabel.setForeground(Colors.blue2);
		clientPrenomLabel.setHorizontalAlignment(JLabel.CENTER);
		clientPrenomLabel.setBounds(1000, 225, 260, 40);
		
		clientNomLabel=new JLabel(client.getNom());
		clientNomLabel.setFont(medium.deriveFont(35f));
		clientNomLabel.setForeground(Colors.blue2);
		clientNomLabel.setHorizontalAlignment(JLabel.CENTER);
		clientNomLabel.setBounds(1000, 260, 260, 40);

		switch(operateur.getRole()){
		case 3: case 4:
			barmanOrderingPanel.add(clientPictureLabel);
			barmanOrderingPanel.add(clientPrenomLabel);
			barmanOrderingPanel.add(clientNomLabel);
			break;
		case 2:
			crediteurOrderingPanel.add(clientPictureLabel);
			crediteurOrderingPanel.add(clientPrenomLabel);
			crediteurOrderingPanel.add(clientNomLabel);
			break;
		}
	}
	
	private void initAdminTabsPanel(){
		adminTabsPanel = new JPanel();
		adminTabsPanel.setLayout(null);
		adminTabsPanel.setBackground(Colors.green1);
		adminTabsPanel.setBounds(0, 40, 1300, 100);
		this.add(adminTabsPanel);
		
		tab1Btn = new TabButton("MODIFICATION SOLDE",normal,true);
		tab1Btn.setBounds(20,0,407,100);
		adminTabsPanel.add(tab1Btn);
		tab1Btn.addActionListener(tabsListener);
		tab2Btn = new TabButton("AJOUT CLIENT",normal,false);
		tab2Btn.setBounds(447,0,407,100);
		adminTabsPanel.add(tab2Btn);
		tab2Btn.addActionListener(tabsListener);
		tab3Btn = new TabButton("HISTORIQUE TRANSACTIONS",normal,false);
		tab3Btn.setBounds(874,0,407,100);
		adminTabsPanel.add(tab3Btn);
		tab3Btn.addActionListener(tabsListener);
	}
	
	private void initAdminFundingPanel(){
		try{
			allClients = clientServiceImpl.getAll();
			selClientsId=new ArrayList<Integer>();
			for (int i=0;i<allClients.size();i++){
				selClientsId.add(i);
			}
			
			//CLIENT
			client=allClients.get(1);
			
			//FOND
			adminFundingPanel = new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AdminFundingPanel.png").getInputStream());
			adminFundingPanel.setBounds(0,140,1300,560);
			adminFundingPanel.setLayout(null);
			this.add(adminFundingPanel);
			
			//LEFT PANEL
			//INFO LECTEUR
			selReaderInfo = new JLabel("");
			selReaderInfo.setForeground(Color.white);
			selReaderInfo.setFont(normal.deriveFont(24f));
			selReaderInfo.setHorizontalAlignment(SwingConstants.CENTER);
			selReaderInfo.setBounds(100, 123, 250, 30);
			adminFundingPanel.add(selReaderInfo);
			
			
			//SELECTION CLIENTS
			selIdTF=new JTextField();
			selIdTF.setUI(new HintTextFieldUI("Identifiant",false));
			selIdTF.setCaretPosition(0);
			selIdTF.setBackground(Color.white);
			selIdTF.setForeground(Colors.green4);
			selIdTF.setFont(normal.deriveFont(24f));
			selIdTF.setBorder(null);
			selIdTF.setBounds(150, 190, 195, 25);
			adminFundingPanel.add(selIdTF);
			
			selNomTF=new JTextField();
			selNomTF.setUI(new HintTextFieldUI("Nom",false));
			selNomTF.setCaretPosition(0);
			selNomTF.setBackground(Color.white);
			selNomTF.setForeground(Colors.green4);
			selNomTF.setFont(normal.deriveFont(24f));
			selNomTF.setBorder(null);
			selNomTF.setBounds(150, 250, 195, 25);
			adminFundingPanel.add(selNomTF);
			
			selPrenomTF=new JTextField();
			selPrenomTF.setUI(new HintTextFieldUI("Pr\u00e9nom",false));
			selPrenomTF.setCaretPosition(0);
			selPrenomTF.setBackground(Color.white);
			selPrenomTF.setForeground(Colors.green4);
			selPrenomTF.setFont(normal.deriveFont(24f));
			selPrenomTF.setBorder(null);
			selPrenomTF.setBounds(150, 310, 195, 25);
			adminFundingPanel.add(selPrenomTF);
			
			selDelegationTF=new JTextField();
			selDelegationTF.setUI(new HintTextFieldUI("D\u00e9l\u00e9gation",false));
			selDelegationTF.setCaretPosition(0);
			selDelegationTF.setBackground(Color.white);
			selDelegationTF.setForeground(Colors.green4);
			selDelegationTF.setFont(normal.deriveFont(24f));
			selDelegationTF.setBorder(null);
			selDelegationTF.setBounds(150, 370, 195, 25);
			adminFundingPanel.add(selDelegationTF);
			
			selResetButton=new FlatButton("reset");
			selResetButton.setForeground(Color.white);
			selResetButton.setFont(normal.deriveFont(24f));
			selResetButton.setBackground(Colors.green5);
			selResetButton.setHoverBackgroundColor(Colors.green5);
			selResetButton.setPressedBackgroundColor(Color.white);
			selResetButton.setBorder(null);
			selResetButton.setBounds(184,443,80,30);
			selResetButton.addActionListener(mainButtonListener);
			adminFundingPanel.add(selResetButton);

			KeyAdapter ecoutEntries = new KeyAdapter(){
				@Override
				public void keyReleased(KeyEvent e){
					selClientsId=new ArrayList<Integer>();
					for (int i=0;i<allClients.size();i++){
						selClientsId.add(i);
					}
					if(selNomTF.getText().equals("")&&selPrenomTF.getText().equals("")&&selDelegationTF.getText().equals("")&&selIdTF.getText().equals("")){
						updateAdminFundingClientPanel();
						return;
					}
					if(!selIdTF.getText().equals("")){
						selClientsId=selClientsId.stream().filter(c->allClients.get(c).getId().toString().equalsIgnoreCase(selIdTF.getText())).collect(Collectors.toList());
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
					updateAdminFundingClientPanel();
				}
			};
			
			selNomTF.addKeyListener(ecoutEntries);
			selPrenomTF.addKeyListener(ecoutEntries);
			selDelegationTF.addKeyListener(ecoutEntries);
			selIdTF.addKeyListener(ecoutEntries);
			
			//MIDDLE PANEL
			adminLignesPanel=new JPanel();
			adminLignesPanel.setLayout(null);
			adminLignesPanel.setBackground(Color.white);
			adminLignesPanel.setBounds(447, 120, 407, 390);
			adminFundingPanel.add(adminLignesPanel);
			
			//LEFT PANEL
			// INFOS CLIENT
			// PHOTO
			clientPictureLabel=new JLabel(new ImageIcon(new ImageIcon(Client.getPhotoPath(client.getId())).getImage().getScaledInstance(85, 110, java.awt.Image.SCALE_SMOOTH)));
			clientPictureLabel.setBounds(942, 120, 85,  110);
			adminFundingPanel.add(clientPictureLabel);
			
			//Infos
			clientPrenomLabel=new JLabel(client.getPrenom());
			clientPrenomLabel.setFont(normal.deriveFont(24f));
			clientPrenomLabel.setForeground(Colors.blue2);
			clientPrenomLabel.setHorizontalAlignment(JLabel.LEFT);
			clientPrenomLabel.setBounds(1030, 160, 260, 20);
			adminFundingPanel.add(clientPrenomLabel);
			
			
			clientNomLabel=new JLabel(client.getNom().toUpperCase());
			clientNomLabel.setFont(normal.deriveFont(24f));
			clientNomLabel.setForeground(Colors.blue2);
			clientNomLabel.setHorizontalAlignment(JLabel.LEFT);
			clientNomLabel.setBounds(1030, 135, 260, 20);
			adminFundingPanel.add(clientNomLabel);
			
			clientDelegationLabel=new JLabel(client.getDelegation());
			clientDelegationLabel.setFont(normal.deriveFont(24f));
			clientDelegationLabel.setForeground(Colors.blue2);
			clientDelegationLabel.setHorizontalAlignment(SwingConstants.LEFT);
			clientDelegationLabel.setBounds(1030, 190, 260, 20);
			adminFundingPanel.add(clientDelegationLabel);
			
			//SOLDE
			curSoldeLabel=new JLabel(client.getEurosSolde().toString());
			curSoldeLabel.setFont(normal.deriveFont(30f));
			curSoldeLabel.setForeground(Color.white);
			curSoldeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			curSoldeLabel.setBounds(1100, 246, 120, 25);
			adminFundingPanel.add(curSoldeLabel);
			
			addCurSoldeLabel=new JLabel(new Euros(0).toString());
			addCurSoldeLabel.setFont(normal.deriveFont(30f));
			addCurSoldeLabel.setForeground(Color.white);
			addCurSoldeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			addCurSoldeLabel.setBounds(1100, 292, 120, 25);
			adminFundingPanel.add(addCurSoldeLabel);
			
			adminOpSignLabel=new JLabel("+");
			adminOpSignLabel.setFont(normal.deriveFont(30f));
			adminOpSignLabel.setForeground(Color.white);
			adminOpSignLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			adminOpSignLabel.setBounds(935, 292, 135, 25);
			adminFundingPanel.add(adminOpSignLabel);
			
			JLabel virgLbl = new JLabel(",");
			virgLbl.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			virgLbl.setForeground(Color.white);
			virgLbl.setBorder(null);
			virgLbl.setHorizontalTextPosition(SwingConstants.CENTER);
			virgLbl.setBounds(1140,344,15,40);
			adminFundingPanel.add(virgLbl);
			
			JLabel euroLbl = new JLabel("\u20AC");
			euroLbl.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			euroLbl.setForeground(Color.white);
			euroLbl.setBorder(null);
			euroLbl.setBounds(1204,344,40,40);
			adminFundingPanel.add(euroLbl);
			
			creAddUTF=new JFormattedTextField(createFormatter("##"));
			creAddUTF.setText("00");
			creAddUTF.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			creAddUTF.setBackground(Color.white);
			creAddUTF.setForeground(Colors.blue3);
			creAddUTF.setBorder(null);
			creAddUTF.setBounds(1095, 344, 45, 40);
			adminFundingPanel.add(creAddUTF);
			
			creAddUTF.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					BigDecimal soldeF;
					try {
						soldeF=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						if(creAddUTF.getCaretPosition()==2){
							creAddUTF.transferFocus();
							creAddDTF.setCaretPosition(0);
						}
					} catch(NumberFormatException exc) {
							creAddUTF.setText(creAddUTF.getText().trim()+"0");
							creAddUTF.setCaretPosition(1);
							soldeF=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						}
					addCurSoldeLabel.setText((new Euros(soldeF.subtract(client.getSolde()).abs())).toString());
					if(soldeF.compareTo(client.getSolde())==-1){
						adminOpSignLabel.setText("-");
					}else{
						adminOpSignLabel.setText("+");
					}
					addCurSoldeLabel.repaint();
					adminOpSignLabel.repaint();
				}
			});
			
			creAddDTF=new JFormattedTextField(createFormatter("##"));
			creAddDTF.setText("00");
			creAddDTF.setFont(medium.deriveFont(36f).deriveFont(Font.BOLD));
			creAddDTF.setBackground(Color.white);
			creAddDTF.setForeground(Colors.blue3);
			creAddDTF.setBorder(null);
			creAddDTF.setBounds(1154, 344, 45, 40);
			adminFundingPanel.add(creAddDTF);
			
			creAddDTF.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					BigDecimal soldeF;
					try {
						soldeF=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						} catch(NumberFormatException exc) {
							creAddDTF.setText(creAddDTF.getText().trim()+"0");
							creAddDTF.setCaretPosition(1);
							soldeF=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						}
					addCurSoldeLabel.setText((new Euros(soldeF.subtract(client.getSolde()).abs())).toString());
					if(soldeF.compareTo(client.getSolde())==-1){
						adminOpSignLabel.setText("-");
					}else{
						adminOpSignLabel.setText("+");
					}
					addCurSoldeLabel.repaint();
					adminOpSignLabel.repaint();
				}
			});
			
			adminFundingValButton=new FlatButton("Valider");
			adminFundingValButton.setFont(medium.deriveFont(30f));
			adminFundingValButton.setForeground(Color.white);
			adminFundingValButton.setBackground(Colors.green1);
			adminFundingValButton.setHoverBackgroundColor(Colors.green1);
			adminFundingValButton.setPressedBackgroundColor(Colors.green2);
			adminFundingValButton.setBorder(null);
			adminFundingValButton.setBounds(978,435,200,50);
			adminFundingValButton.addActionListener(mainButtonListener);
			adminFundingPanel.add(adminFundingValButton);
			
			updateAdminFundingClientPanel();
			// Thread lecteur
			initReader();
			transfert();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initAdminCreatePanel(){
		try{
			client=new Client("","","",BigDecimal.ZERO,"");
			
			//FOND
			adminFundingPanel = new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AdminAddClientPanel.png").getInputStream());
			adminFundingPanel.setBounds(0,140,1300,560);
			adminFundingPanel.setLayout(null);
			this.add(adminFundingPanel);
			
			//LEFT PANEL			
			
			//INFOS CLIENT
			selIdLbl=new JLabel("Identifiant auto");
			selIdLbl.setBackground(Colors.gray);
			selIdLbl.setForeground(Colors.green4.brighter().brighter().brighter());
			selIdLbl.setFont(normal.deriveFont(24f));
			selIdLbl.setBorder(null);
			selIdLbl.setBounds(150, 123, 195, 25);
			adminFundingPanel.add(selIdLbl);
			
			selNomTF=new JTextField();
			selNomTF.setUI(new HintTextFieldUI("Nom",false));
			selNomTF.setCaretPosition(0);
			selNomTF.setBackground(Color.white);
			selNomTF.setForeground(Colors.green4);
			selNomTF.setFont(normal.deriveFont(24f));
			selNomTF.setBorder(null);
			selNomTF.setBounds(150, 182, 195, 25);
			adminFundingPanel.add(selNomTF);
			
			selPrenomTF=new JTextField();
			selPrenomTF.setUI(new HintTextFieldUI("Pr\u00e9nom",false));
			selPrenomTF.setCaretPosition(0);
			selPrenomTF.setBackground(Color.white);
			selPrenomTF.setForeground(Colors.green4);
			selPrenomTF.setFont(normal.deriveFont(24f));
			selPrenomTF.setBorder(null);
			selPrenomTF.setBounds(150, 242, 195, 25);
			adminFundingPanel.add(selPrenomTF);
			
			selDelegationTF=new JTextField();
			selDelegationTF.setUI(new HintTextFieldUI("D\u00e9l\u00e9gation",false));
			selDelegationTF.setCaretPosition(0);
			selDelegationTF.setBackground(Color.white);
			selDelegationTF.setForeground(Colors.green4);
			selDelegationTF.setFont(normal.deriveFont(24f));
			selDelegationTF.setBorder(null);
			selDelegationTF.setBounds(150, 302, 195, 25);
			adminFundingPanel.add(selDelegationTF);
			
			adminAddResetButton=new FlatButton("reset");
			adminAddResetButton.setForeground(Color.white);
			adminAddResetButton.setFont(normal.deriveFont(24f));
			adminAddResetButton.setBackground(Colors.green5);
			adminAddResetButton.setHoverBackgroundColor(Colors.green5);
			adminAddResetButton.setPressedBackgroundColor(Color.white);
			adminAddResetButton.setBorder(null);
			adminAddResetButton.setBounds(184,352,80,30);
			adminAddResetButton.addActionListener(mainButtonListener);
			adminFundingPanel.add(adminAddResetButton);

			KeyAdapter ecoutEntries = new KeyAdapter(){
				@Override
				public void keyReleased(KeyEvent e){
					try{
						if(!selNomTF.getText().equals("")&&!selPrenomTF.getText().equals("")&&!selDelegationTF.getText().equals("")){
							if(adminAddStage==0){
								client=new Client(selNomTF.getText(),selPrenomTF.getText(),selDelegationTF.getText(),new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2)),"");
								if(adminAddImageFile!=null){
									adminAddStage(1);
									adminAddStage(2);
								}else{
									adminAddStage(1);
								}
							}else{
								if(e.getSource().equals(selNomTF)){
									client.setNom(selNomTF.getText());
								}else if(e.getSource().equals(selPrenomTF)){
									client.setPrenom(selPrenomTF.getText());
								}else if(e.getSource().equals(selDelegationTF)){
									client.setDelegation(selDelegationTF.getText());
								}
								adminAddStage(adminAddStage);
								}
							}
					}catch(NumberFormatException exc){
						return;
					}
				}
			};
			
			selNomTF.addKeyListener(ecoutEntries);
			selPrenomTF.addKeyListener(ecoutEntries);
			selDelegationTF.addKeyListener(ecoutEntries);
			
			
			//MIDDLE PANEL
			//CHARGER L IMAGE
			adminLoadImageButton=new FlatButton("charger une image");
			adminLoadImageButton.setBackground(Colors.green1);
			adminLoadImageButton.setPressedBackgroundColor(Colors.green2);
			adminLoadImageButton.setHoverBackgroundColor(Colors.green1);
			adminLoadImageButton.setBorder(null);
			adminLoadImageButton.setForeground(Color.white);
			adminLoadImageButton.setFont(light.deriveFont(24f).deriveFont(Font.BOLD));
			adminLoadImageButton.setBounds(584, 162, 200, 33);
			adminLoadImageButton.setEnabled(false);
			adminLoadImageButton.addActionListener(mainButtonListener);
			adminFundingPanel.add(adminLoadImageButton);
			
			//FIXER LE SOLDE
			creAddUTF=new JFormattedTextField(createFormatter("##"));
			creAddUTF.setText("00");
			creAddUTF.setFont(light.deriveFont(30f).deriveFont(Font.BOLD));
			creAddUTF.setBackground(Color.white);
			creAddUTF.setForeground(Colors.green1);
			creAddUTF.setBorder(new LineBorder(Colors.green1,2));
			creAddUTF.setBounds(673, 253, 43, 37);
			creAddUTF.setEnabled(false);
			adminFundingPanel.add(creAddUTF);
			
			creAddUTF.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					BigDecimal soldeF;
					try {
						soldeF=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						if(creAddUTF.getCaretPosition()==2){
							creAddUTF.transferFocus();
							creAddDTF.setCaretPosition(0);
						}
					} catch(NumberFormatException exc) {
							creAddUTF.setText(creAddUTF.getText().trim()+"0");
							creAddUTF.setCaretPosition(1);
							soldeF=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						}
					client.setSolde(soldeF);
					curSoldeLabel.setText(client.getEurosSolde().toString());
				}
			});
			
			creAddDTF=new JFormattedTextField(createFormatter("##"));
			creAddDTF.setText("00");
			creAddDTF.setFont(light.deriveFont(30f).deriveFont(Font.BOLD));
			creAddDTF.setBackground(Color.white);
			creAddDTF.setForeground(Colors.green1);
			creAddDTF.setBorder(new LineBorder(Colors.green1,2));
			creAddDTF.setBounds(723, 253, 43, 37);
			creAddDTF.setEnabled(false);
			adminFundingPanel.add(creAddDTF);
			
			creAddDTF.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					BigDecimal soldeF;
					try {
						soldeF=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						} catch(NumberFormatException exc) {
							creAddDTF.setText(creAddDTF.getText().trim()+"0");
							creAddDTF.setCaretPosition(1);
							soldeF=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
						}
					client.setSolde(soldeF);
					curSoldeLabel.setText(client.getEurosSolde().toString());
				}
			});
			
			JLabel virgLbl = new JLabel(",");
			virgLbl.setFont(light.deriveFont(30f).deriveFont(Font.BOLD));
			virgLbl.setForeground(Colors.green1);
			virgLbl.setBorder(null);
			virgLbl.setHorizontalTextPosition(SwingConstants.CENTER);
			virgLbl.setBounds(715,253,15,37);
			adminFundingPanel.add(virgLbl);
			
			JLabel euroLbl = new JLabel("\u20AC");
			euroLbl.setFont(light.deriveFont(30f).deriveFont(Font.BOLD));
			euroLbl.setForeground(Colors.green1);
			euroLbl.setBorder(null);
			euroLbl.setBounds(770,253,40,37);
			adminFundingPanel.add(euroLbl);
			
			
			//LEFT PANEL
			// INFOS CLIENT
			//PHOTO
			clientPictureLabel=new JLabel();
			adminFundingPanel.add(clientPictureLabel);
			
			//Infos
			clientIdLabel=new JLabel("\u0023");
			clientIdLabel.setFont(medium.deriveFont(30f)); 
			clientIdLabel.setForeground(Color.white);
			clientIdLabel.setHorizontalAlignment(SwingConstants.LEFT);
			clientIdLabel.setBounds(1020,135,200,25);
			adminFundingPanel.add(clientIdLabel);
			
			
			clientNomLabel=new JLabel(client.getNom().toUpperCase());
			clientNomLabel.setFont(medium.deriveFont(30f));
			clientNomLabel.setForeground(Color.white);
			clientNomLabel.setHorizontalAlignment(JLabel.LEFT);
			clientNomLabel.setBounds(1020, 175, 240, 25);
			adminFundingPanel.add(clientNomLabel);
			
			clientPrenomLabel=new JLabel(client.getPrenom());
			clientPrenomLabel.setFont(medium.deriveFont(30f));
			clientPrenomLabel.setForeground(Color.white);
			clientPrenomLabel.setHorizontalAlignment(JLabel.LEFT);
			clientPrenomLabel.setBounds(1020, 215, 240, 25);
			adminFundingPanel.add(clientPrenomLabel);
			
		
			clientDelegationLabel=new JLabel(client.getDelegation());
			clientDelegationLabel.setFont(medium.deriveFont(30f));
			clientDelegationLabel.setForeground(Color.white);
			clientDelegationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			clientDelegationLabel.setBounds(880, 275, 395, 25);
			adminFundingPanel.add(clientDelegationLabel);
			
			//SOLDE
			curSoldeLabel=new JLabel(client.getEurosSolde().toString());
			curSoldeLabel.setFont(medium.deriveFont(30f));
			curSoldeLabel.setForeground(Color.white);
			curSoldeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			curSoldeLabel.setBounds(905, 321, 344, 25);
			adminFundingPanel.add(curSoldeLabel);
			
			//BOTTOMPANEL
			adminAddStage=0;
			
			adminAddBotPanel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AdminAddBg.png").getInputStream());
			adminAddBotPanel.setLayout(null);
			adminAddBotPanel.setBounds(173, 432,954, 100);
			adminFundingPanel.add(adminAddBotPanel);

			adminBot1Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix2.png").getInputStream());
			adminBot1Panel.setBounds(0, 0, 100, 100);
			adminAddBotPanel.add(adminBot1Panel);
			
			adminBot2Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix1.png").getInputStream());
			adminBot2Panel.setBounds(427,0,100,100);
			adminAddBotPanel.add(adminBot2Panel);
			
			adminBot3Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix1.png").getInputStream());
			adminBot3Panel.setBounds(854, 0, 100, 100);
			adminAddBotPanel.add(adminBot3Panel);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initAdminTransactPanel(){
		try {
			
			//FOND
			adminFundingPanel = new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AdminTransactPanel.png").getInputStream());
			adminFundingPanel.setBounds(0,140,1300,560);
			adminFundingPanel.setLayout(null);
			this.add(adminFundingPanel);
			
			//CHOIX PANEL
			//CHOIX TYPE
			adminTransTypeButton=new FlatToggleButton("type");
			adminTransTypeButton.setSelected(false);
			adminTransTypeButton.setFont(mediumIt.deriveFont(24f));
			adminTransTypeButton.setBounds(110,36,112,25);
			adminFundingPanel.add(adminTransTypeButton);
			
			ButtonGroup btnGrp = new ButtonGroup();
			
			adminTransType1RB = new JRadioButton("d\u00e9bits");
			adminTransType1RB.setBounds(132, 69, 100, 20);
			adminTransType1RB.setFont(mediumIt.deriveFont(18f));
			adminTransType1RB.setForeground(Color.white);
			adminTransType1RB.setBackground(Colors.blue3);
			adminTransType1RB.setFocusable(false);
			btnGrp.add(adminTransType1RB);
			adminFundingPanel.add(adminTransType1RB);
			adminTransType1RB.setEnabled(false);
			adminTransType1RB.setSelected(true);
			
			adminTransType2RB = new JRadioButton("cr\u00e9dits");
			adminTransType2RB.setBounds(132, 90, 100, 20);
			adminTransType2RB.setFont(mediumIt.deriveFont(18f));
			adminTransType2RB.setForeground(Color.white);
			adminTransType2RB.setBackground(Colors.blue3);
			adminTransType2RB.setFocusable(false);
			btnGrp.add(adminTransType2RB);
			adminFundingPanel.add(adminTransType2RB);
			adminTransType2RB.setEnabled(false);
			adminTransType2RB.setSelected(false);
			
			adminTransType3RB = new JRadioButton("admin");
			adminTransType3RB.setBounds(132, 111, 100, 20);
			adminTransType3RB.setFont(mediumIt.deriveFont(18f));
			adminTransType3RB.setForeground(Color.white);
			adminTransType3RB.setBackground(Colors.blue3);
			adminTransType3RB.setFocusable(false);
			btnGrp.add(adminTransType3RB);
			adminFundingPanel.add(adminTransType3RB);
			adminTransType3RB.setEnabled(false);
			adminTransType3RB.setSelected(false);
			
			adminTransTypeButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(((JToggleButton)e.getSource()).isSelected()){
						adminTransType1RB.setEnabled(true);
						adminTransType2RB.setEnabled(true);
						adminTransType3RB.setEnabled(true);
						if(adminTransBarButton.isSelected()){
							adminTransBarButton.doClick();
						}
					}else{
						adminTransType1RB.setEnabled(false);
						adminTransType2RB.setEnabled(false);
						adminTransType3RB.setEnabled(false);
					}
				}
			});
			
			//CHOIX OPERATEUR
			adminTransOpeButton=new FlatToggleButton("op\u00e9rateur");
			adminTransOpeButton.setSelected(false);
			adminTransOpeButton.setFont(mediumIt.deriveFont(24f));
			adminTransOpeButton.setBounds(352,36,112,25);
			adminFundingPanel.add(adminTransOpeButton);
			
			adminTransOpeCB = new JComboBox<Operateur>();
			List<Operateur> listOperateurs=operateurServiceImpl.getAll();
			for (Operateur o : listOperateurs){
				adminTransOpeCB.addItem(o);
			}
			adminTransOpeCB.setBackground(Colors.blue4);
			adminTransOpeCB.setForeground(Color.black);
			adminTransOpeCB.setFont(mediumIt.deriveFont(18f));
			adminTransOpeCB.setBounds(332,78,152,25);
			adminFundingPanel.add(adminTransOpeCB);
			adminTransOpeCB.setEnabled(false);
			
			adminTransOpeButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(((JToggleButton)e.getSource()).isSelected()){
						adminTransOpeCB.setEnabled(true);
					}else{
						adminTransOpeCB.setEnabled(false);
					}
				}
			});
			
			//CHOIX CLIENT
			client=null;
			adminTransClButton=new FlatToggleButton("client");
			adminTransClButton.setSelected(false);
			adminTransClButton.setFont(mediumIt.deriveFont(24f));
			adminTransClButton.setBounds(594,36,112,25);
			adminFundingPanel.add(adminTransClButton);
			
			adminTransClTF=new JTextField();
			adminTransClTF.setUI(new HintTextFieldUI("Identifiant",false));
			adminTransClTF.setCaretPosition(0);
			adminTransClTF.setFont(mediumIt.deriveFont(18f));
			adminTransClTF.setForeground(Color.BLACK);
			adminTransClTF.setBackground(Colors.blue4);
			adminTransClTF.setBounds(575, 78, 117, 25);
			adminTransClTF.setEnabled(false);
			adminFundingPanel.add(adminTransClTF);
			
			adminTransClLbl=new JLabel();
			adminTransClLbl.setFont(mediumIt.deriveFont(18f));
			adminTransClLbl.setForeground(Color.white);
			adminTransClLbl.setBounds(575, 110, 151, 25);
			adminFundingPanel.add(adminTransClLbl);
			
			adminTransClBtn=new FlatButton("go");
			adminTransClBtn.setFont(mediumIt.deriveFont(18f));
			adminTransClBtn.setBackground(Colors.blue4);
			adminTransClBtn.setHoverBackgroundColor(Colors.blue4);
			adminTransClBtn.setPressedBackgroundColor(Colors.blue2);
			adminTransClBtn.setForeground(Color.black);
			adminTransClBtn.setBorder(null);
			adminTransClBtn.setBounds(701, 78, 25, 25);
			adminTransClBtn.setEnabled(false);
			adminFundingPanel.add(adminTransClBtn);
			adminTransClBtn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						client=null;
						client=clientServiceImpl.find(Integer.parseInt(adminTransClTF.getText()));
						if(client!=null){
							adminTransClLbl.setText(client.getNom()+" "+client.getPrenom());
							adminTransClLbl.setForeground(Colors.green1);
						}else{
							adminTransClLbl.setText("Mauvais identifiant");
							adminTransClLbl.setForeground(Colors.red);
						}
					}catch(NumberFormatException ec){
						adminTransClTF.setText("");
						adminTransClLbl.setText("Mauvais identifiant");
						adminTransClLbl.setForeground(Colors.red);
					}
				}
			});
			
			adminTransClButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(((JToggleButton)e.getSource()).isSelected()){
						adminTransClTF.setEnabled(true);
						adminTransClBtn.setEnabled(true);
					}else{
						adminTransClTF.setEnabled(false);
						adminTransClBtn.setEnabled(false);
					}
				}
			});
			
			//CHOIX BAR
			adminTransBarButton=new FlatToggleButton("bar");
			adminTransBarButton.setSelected(false);
			adminTransBarButton.setFont(mediumIt.deriveFont(24f));
			adminTransBarButton.setBounds(834,36,112,25);
			adminFundingPanel.add(adminTransBarButton);
			
			ButtonGroup btnBarGrp = new ButtonGroup();
			
			adminTransBar1RB = new JRadioButton("bar 1");
			adminTransBar1RB.setBounds(856, 77, 100, 20);
			adminTransBar1RB.setFont(mediumIt.deriveFont(18f));
			adminTransBar1RB.setForeground(Color.white);
			adminTransBar1RB.setBackground(Colors.blue3);
			btnBarGrp.add(adminTransBar1RB);
			adminTransBar1RB.setFocusable(false);
			adminFundingPanel.add(adminTransBar1RB);
			adminTransBar1RB.setEnabled(false);
			adminTransBar1RB.setSelected(true);
			
			adminTransBar2RB = new JRadioButton("bar 2");
			adminTransBar2RB.setBounds(856, 98, 100, 20);
			adminTransBar2RB.setFont(mediumIt.deriveFont(18f));
			adminTransBar2RB.setForeground(Color.white);
			adminTransBar2RB.setFocusable(false);
			adminTransBar2RB.setBackground(Colors.blue3);
			btnBarGrp.add(adminTransBar2RB);
			adminFundingPanel.add(adminTransBar2RB);
			adminTransBar2RB.setEnabled(false);
			adminTransBar2RB.setSelected(false);
			
			adminTransBarButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(((JToggleButton)e.getSource()).isSelected()){
						adminTransBar1RB.setEnabled(true);
						adminTransBar2RB.setEnabled(true);
						if(adminTransTypeButton.isSelected()){
							adminTransTypeButton.doClick();
						}
					}else{
						adminTransBar1RB.setEnabled(false);
						adminTransBar2RB.setEnabled(false);
					}
				}
			});
			
			//VALIDATION
			adminTransOKBtn=new FlatButton();
			adminTransOKBtn.setBackground(Colors.orange);
			adminTransOKBtn.setHoverBackgroundColor(Colors.orange);
			adminTransOKBtn.setPressedBackgroundColor(Colors.red);
			Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/TransLoupe.png").getInputStream());
			adminTransOKBtn.setIcon(new ImageIcon(img));
			adminTransOKBtn.setForeground(Color.white);
			adminTransOKBtn.setFont(mediumIt.deriveFont(36f));
			adminTransOKBtn.setBounds(1129, 47, 80, 60);
			adminTransOKBtn.setBorder(new LineBorder(Color.white,3));
			adminTransOKBtn.addActionListener(mainButtonListener);
			adminFundingPanel.add(adminTransOKBtn);
			
			//RIGHT PANEL
			adminCurPage=1;

			adminTrans1PageButton=new FlatButton();
			adminTrans1PageButton.setBackground(Colors.blue3);
			adminTrans1PageButton.setHoverBackgroundColor(Colors.blue3);
			adminTrans1PageButton.setPressedBackgroundColor(Colors.blue4);
			adminTrans1PageButton.setBounds(1139,238,131,30);
			img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Trans1Page.png").getInputStream());
			adminTrans1PageButton.setIcon(new ImageIcon(img));
			adminTrans1PageButton.setBorder(null);
			adminTrans1PageButton.addActionListener(adminPageListener);
			adminFundingPanel.add(adminTrans1PageButton);
			
			adminTransPrevPageButton=new FlatButton();
			adminTransPrevPageButton.setBackground(Colors.blue3);
			adminTransPrevPageButton.setHoverBackgroundColor(Colors.blue3);
			adminTransPrevPageButton.setPressedBackgroundColor(Colors.blue4);
			adminTransPrevPageButton.setBounds(1139,271,131,30);
			img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/TransPrevPage.png").getInputStream());
			adminTransPrevPageButton.setIcon(new ImageIcon(img));
			adminTransPrevPageButton.setBorder(null);
			adminTransPrevPageButton.addActionListener(adminPageListener);
			adminFundingPanel.add(adminTransPrevPageButton);
			
			adminTransNextPageButton=new FlatButton();
			adminTransNextPageButton.setBackground(Colors.blue3);
			adminTransNextPageButton.setHoverBackgroundColor(Colors.blue3);
			adminTransNextPageButton.setPressedBackgroundColor(Colors.blue4);
			adminTransNextPageButton.setBounds(1139,378,131,30);
			img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/TransNextPage.png").getInputStream());
			adminTransNextPageButton.setIcon(new ImageIcon(img));
			adminTransNextPageButton.setBorder(null);
			adminTransNextPageButton.addActionListener(adminPageListener);
			adminFundingPanel.add(adminTransNextPageButton);
			
			adminTrans10PageButton=new FlatButton();
			adminTrans10PageButton.setBackground(Colors.blue3);
			adminTrans10PageButton.setHoverBackgroundColor(Colors.blue3);
			adminTrans10PageButton.setPressedBackgroundColor(Colors.blue4);
			adminTrans10PageButton.setBounds(1139,411,131,30);
			img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/Trans10Page.png").getInputStream());
			adminTrans10PageButton.setIcon(new ImageIcon(img));
			adminTrans10PageButton.setBorder(null);
			adminTrans10PageButton.addActionListener(adminPageListener);
			adminFundingPanel.add(adminTrans10PageButton);
			
			adminTransPageTF=new JTextField("1");
			adminTransPageTF.setBounds(1169, 325, 50, 30);
			adminTransPageTF.setBackground(Color.white);
			adminTransPageTF.setForeground(Color.black);
			adminTransPageTF.setFont(medium.deriveFont(30f));
			adminTransPageTF.setHorizontalAlignment(SwingConstants.CENTER);
			adminFundingPanel.add(adminTransPageTF);
			
			adminTransGoPageButton=new FlatButton("GO");
			adminTransGoPageButton.setBackground(Colors.blue4);
			adminTransGoPageButton.setHoverBackgroundColor(Colors.blue4);
			adminTransGoPageButton.setPressedBackgroundColor(Colors.blue2);
			adminTransGoPageButton.setFont(medium.deriveFont(18f));
			adminTransGoPageButton.setBorder(null);
			adminTransGoPageButton.addActionListener(adminPageListener);
			adminTransGoPageButton.setBounds(1227, 325, 30, 30);
			adminFundingPanel.add(adminTransGoPageButton);
			
			//TRANSACTIONS PANEL
			adminTransPanel = new JPanel();
			adminTransPanel.setBounds(20, 180, 1110, 360);
			adminTransPanel.setBackground(Color.white);
			adminTransPanel.setLayout(null);
			adminFundingPanel.add(adminTransPanel);
			
			allTransactions=transactionServiceImpl.getAll();
			int num=allTransactions.size()-1;
			allTransactions=IntStream.rangeClosed(0,num).mapToObj(i->allTransactions.get(num-i)).collect(Collectors.toList());
			
			adminFilterTransactions();
			adminShowTransactionPage(0);
			
			adminFundingPanel.repaint();
		} catch (IOException e) {
			
		}
	}
	
	private void adminFilterTransactions(){
		currentTransactions=allTransactions.subList(0, allTransactions.size());
		
		if(adminTransTypeButton.isSelected()){
			if(adminTransType1RB.isSelected()){
				currentTransactions=currentTransactions.stream().filter(c->c.getType().equals("debit")).collect(Collectors.toList());
			}else if(adminTransType2RB.isSelected()){
				currentTransactions=currentTransactions.stream().filter(c->c.getType().equals("credit")).collect(Collectors.toList());
			}else if(adminTransType3RB.isSelected()){
				currentTransactions=currentTransactions.stream().filter(c->c.getType().equals("admin")).collect(Collectors.toList());
			}	
		}
		
		if(adminTransOpeButton.isSelected()){
			currentTransactions=currentTransactions.stream().filter(c->c.getOperateur().getId().equals(((Operateur)adminTransOpeCB.getSelectedItem()).getId())).collect(Collectors.toList());
		}
		
		if(adminTransClButton.isSelected()){
			if(client!=null){
				currentTransactions=currentTransactions.stream().filter(c->c.getClient().getId().equals(client.getId())).collect(Collectors.toList());
			}
		}
		
		if(adminTransBarButton.isSelected()){
			currentTransactions=currentTransactions.stream().filter(c->c.getType().equals("debit")).collect(Collectors.toList());
			if(adminTransBar1RB.isSelected()){
				currentTransactions=currentTransactions.stream().filter(c->((Debit)c).getBar()==1).collect(Collectors.toList());
			}else{
				currentTransactions=currentTransactions.stream().filter(c->((Debit)c).getBar()==2).collect(Collectors.toList());
			}
		}
	}
	
	private void adminShowTransactionPage(Integer page){
		adminTransPanel.removeAll();
		adminTransPanel.revalidate();
		LigneAdminTransaction lign;
		for(int i=0;i<Math.min(Math.max(0, currentTransactions.size()-12*page), 12);i++){
			lign=new LigneAdminTransaction(i,currentTransactions.get(i+page*12),this);
			adminTransPanel.add(lign);
		}
		adminTransPanel.repaint();
	}
	
	private void adminAddStage(Integer i){
		switch (i){
		case 0:
			adminAddStage=0;
			client=new Client("","","",new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2)),"");
			creAddUTF.setEnabled(false);
			creAddDTF.setEnabled(false);
			adminLoadImageButton.setEnabled(false);
			adminAddUpdateClient(false);
			adminAddBotPanel.removeAll();
			adminAddBotPanel.revalidate();
			try {
				adminBot1Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix2.png").getInputStream());
				adminBot1Panel.setBounds(0, 0, 100, 100);
				adminAddBotPanel.add(adminBot1Panel);
				
				adminBot2Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix1.png").getInputStream());
				adminBot2Panel.setBounds(427,0,100,100);
				adminAddBotPanel.add(adminBot2Panel);
				
				adminBot3Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix1.png").getInputStream());
				adminBot3Panel.setBounds(854, 0, 100, 100);
				adminAddBotPanel.add(adminBot3Panel);
			} catch (IOException e) {
				System.err.println("Ca merde dans l'affichage des images");
			}
			adminAddBotPanel.repaint();
			break;
		case 1:
			adminAddStage=1;
			adminAddUpdateClient(false);
			adminLoadImageButton.setEnabled(true);
			creAddUTF.setEnabled(true);
			creAddDTF.setEnabled(true);
			adminAddBotPanel.removeAll();
			adminAddBotPanel.revalidate();
			try {
				adminBot1Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCheck.png").getInputStream());
				adminBot1Panel.setBounds(0, 0, 100, 100);
				adminAddBotPanel.add(adminBot1Panel);
				
				adminBot2Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix2.png").getInputStream());
				adminBot2Panel.setBounds(427,0,100,100);
				adminAddBotPanel.add(adminBot2Panel);
				
				adminBot3Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix1.png").getInputStream());
				adminBot3Panel.setBounds(854, 0, 100, 100);
				adminAddBotPanel.add(adminBot3Panel);
			} catch (IOException e) {
				System.err.println("Ca merde dans l'affichage des images");
			}
			adminAddBotPanel.repaint();
			break;
		case 2:
			adminAddStage=2;
			adminAddUpdateClient(false);
			adminAddBotPanel.removeAll();
			adminAddBotPanel.revalidate();
			try {
				adminBot1Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCheck.png").getInputStream());
				adminBot1Panel.setBounds(0, 0, 100, 100);
				adminAddBotPanel.add(adminBot1Panel);
				
				adminBot2Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCheck.png").getInputStream());
				adminBot2Panel.setBounds(427,0,100,100);
				adminAddBotPanel.add(adminBot2Panel);
				
				adminBot3Button=new FlatButton();
				adminBot3Button.setBackground(Colors.blue3);
				adminBot3Button.setHoverBackgroundColor(Colors.blue3);
				adminBot3Button.setPressedBackgroundColor(Colors.blue2);
				adminBot3Button.setBounds(854,0,100,100);
				Image img = ImageIO.read(resourceServiceImpl.getResource("classpath:img/AddCheck2.png").getInputStream());
				adminBot3Button.setIcon(new ImageIcon(img));
				adminBot3Button.setBorder(new LineBorder(Colors.blue2,2));
				adminBot3Button.addActionListener(mainButtonListener);
				adminAddBotPanel.add(adminBot3Button);
				
			} catch (IOException e) {
				System.err.println("Ca merde dans l'affichage des images");
			}
			adminAddBotPanel.repaint();
			break;
		case 3:
			adminAddStage=3;
			adminAddUpdateClient(false);
			adminLoadImageButton.setEnabled(true);
			creAddUTF.setEnabled(true);
			creAddDTF.setEnabled(true);
			adminAddBotPanel.removeAll();
			adminAddBotPanel.revalidate();
			try {
				adminBot1Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCheck.png").getInputStream());
				adminBot1Panel.setBounds(0, 0, 100, 100);
				adminAddBotPanel.add(adminBot1Panel);
				
				adminBot2Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCheck.png").getInputStream());
				adminBot2Panel.setBounds(427,0,100,100);
				adminAddBotPanel.add(adminBot2Panel);
				
				adminBot3Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCheck.png").getInputStream());
				adminBot3Panel.setBounds(854, 0, 100, 100);
				adminAddBotPanel.add(adminBot3Panel);
			} catch (IOException e) {
				System.err.println("Ca merde dans l'affichage des images");
			}
			adminAddBotPanel.repaint();
			break;
		case 5:
			adminAddStage=0;
			client=new Client("","","",BigDecimal.ZERO,"");
			client.setId(0);
			selNomTF.setText("");
			selPrenomTF.setText("");
			selDelegationTF.setText("");
			creAddUTF.setText("00");
			creAddUTF.setEnabled(false);
			creAddDTF.setText("00");
			creAddDTF.setEnabled(false);
			selIdLbl.setText("Identifiant auto");
			adminLoadImageButton.setEnabled(false);
			adminAddUpdateClient(false);
			adminAddBotPanel.removeAll();
			adminAddBotPanel.revalidate();
			adminFundingPanel.remove(clientPictureLabel);
			adminAddImageFile=null;
			try {
				adminBot1Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix2.png").getInputStream());
				adminBot1Panel.setBounds(0, 0, 100, 100);
				adminAddBotPanel.add(adminBot1Panel);
				
				adminBot2Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix1.png").getInputStream());
				adminBot2Panel.setBounds(427,0,100,100);
				adminAddBotPanel.add(adminBot2Panel);
				
				adminBot3Panel=new BackgroundPanel(resourceServiceImpl.getResource("classpath:img/AddCroix1.png").getInputStream());
				adminBot3Panel.setBounds(854, 0, 100, 100);
				adminAddBotPanel.add(adminBot3Panel);
			} catch (IOException e) {
				System.err.println("Ca merde dans l'affichage des images");
			}
			adminAddBotPanel.repaint();
			adminFundingPanel.repaint();
			break;
		}
	}
	
	private void adminAddUpdateClient(boolean picture){
		if(picture){
			// PHOTO
			try {
				adminFundingPanel.remove(clientPictureLabel);
				clientPictureLabel=new JLabel(new ImageIcon(new ImageIcon( adminAddImageFile.toURI().toURL()).getImage().getScaledInstance(105, 135, java.awt.Image.SCALE_SMOOTH)));
				clientPictureLabel.setBounds(904, 118, 105,  135);
				adminFundingPanel.add(clientPictureLabel);
				curSoldeLabel.setText(client.getEurosSolde().toString());
				adminFundingPanel.repaint();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			clientIdLabel.setText("\u0023");
			clientNomLabel.setText(client.getNom().toUpperCase());
			clientPrenomLabel.setText(client.getPrenom());
			clientDelegationLabel.setText(client.getDelegation());
			curSoldeLabel.setText(client.getEurosSolde().toString());
		}
	}
	
	private void updateAdminFundingClientPanel(){
		if(selClientsId.size()!=0){
			client=allClients.get(selClientsId.get(0));
			displayedClientsId=selClientsId.subList(0, selClientsId.size());
			adminUpdateFundingClient(client);
		}else{
			if(adminFundingJustRemoved){
				displayedClientsId=selClientsId.subList(0, selClientsId.size());
				adminUpdateFundingClient(new Client("","","",BigDecimal.ZERO,""));
				adminFundingJustRemoved=false;
			}else{
				return;
			}
			}
			
		adminLignesPanel.removeAll();
		adminLignesPanel.revalidate();
		adminFundingLigneList=new ArrayList<LigneClientAdminFunding>();
		LigneClientAdminFunding tempLigne;
		for(int i=0;i<Math.min(displayedClientsId.size(),13);i++){
			tempLigne=new LigneClientAdminFunding(i,allClients.get(displayedClientsId.get(i)),this,resourceServiceImpl);
			adminLignesPanel.add(tempLigne);
			adminFundingLigneList.add(tempLigne);
		}
		adminLignesPanel.repaint();
	}
	
	protected void adminUpdateFundingClient(Client c){
		client=c;
		clientPictureLabel.setIcon(new ImageIcon(new ImageIcon(Client.getPhotoPath(client.getId())).getImage().getScaledInstance(85, 110, java.awt.Image.SCALE_SMOOTH)));
		clientPrenomLabel.setText(client.getPrenom());
		clientNomLabel.setText(client.getNom().toUpperCase());
		clientDelegationLabel.setText(client.getDelegation());
		curSoldeLabel.setText(client.getEurosSolde().toString());
		adminOpSignLabel.setText("+");
		addCurSoldeLabel.setText(new Euros(0).toString());
		adminUpdateTF(client.getSolde());
	}
	
	protected void adminFundingRemoveClient(Client c){
		selClientsId.remove(new Integer(allClients.indexOf(c)));
		adminFundingJustRemoved=true;
		updateAdminFundingClientPanel();
	}
	
	protected void updateConsoBtn(){
		Euros curSolde = Euros.substract(client.getEurosSolde(),monPanier.getPrix());	
		addTotalLabel.setText(monPanier.getPrix().toString());
		for(ConsoBtn c : listConsoBtn){
			c.update(curSolde);
		}
		barValRestantLabel.setText(curSolde+"");
		ligneItemPanel.removeAll();
		ligneItemPanel.revalidate();
		ligneItemPanel.repaint();
		for(int i=0;i<monPanier.size();i++){
			ligneItemPanel.add(new LigneItem(i,monPanier.get(i),monPanier,this, medium, resourceServiceImpl));
		}
		ligneItemPanel.revalidate();
		ligneItemPanel.repaint();
	}
	
	protected void updateDetails(Transaction t){
		Transaction trans;
		if(t!=null){
			trans=t;
		}else{
			trans=transactionServiceImpl.getTransactionLocal().get(0);
			premiereLigneTransaction.forceBorder(true);
		}
		nomLabel.setText(trans.getClient().getNom());
		prenomLabel.setText(trans.getClient().getPrenom());
		nbLabel.setText(trans.getClient().getId()+"");
		numLabel.setText(trans.getId()+"");
		dateLabel.setText(trans.getDateTransaction());
		switch(trans.getType()){
		case "debit":
			montantLabel.setText("-"+trans.getEurosMontant().toString());
			detailsPanierPanel.init(((Debit)trans).getPanier());
			break;
		case "credit":
			montantLabel.setText("+"+trans.getEurosMontant().toString());
			break;
		case "admin":
			TransactAdmin ta=(TransactAdmin)t;
			if(ta.isAddition()){
				montantLabel.setText("+"+trans.getEurosMontant().toString());
			}else{
				montantLabel.setText("-"+trans.getEurosMontant().toString());
			}
		}
		curSoldeLabel.setText(trans.getClient().getEurosSolde().toString());
		detailsSetCanceled(trans.isCanceled());
		
	}
	
	protected void detailsSetCanceled(boolean b){
		barreCanceledLabel.setVisible(b);
		textCanceledLabel.setVisible(b);
		mainPanel.repaint();
	}
	
	public void initServices(){
		operateurServiceImpl=(OperateurService)applicationContext.getBean("operateurServiceImpl");
		resourceServiceImpl=(ResourceService)applicationContext.getBean("resourceServiceImpl");
		transactionServiceImpl=(TransactionService)applicationContext.getBean("transactionServiceImpl");
		clientServiceImpl=(ClientService)applicationContext.getBean("clientServiceImpl");
		consoServiceImpl=(ConsoService)applicationContext.getBean("consoServiceImpl");
	}
	
	private void initFonts(){
		try {
			light=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Light.ttf").getInputStream());
			thin=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Thin.ttf").getInputStream());
			normal=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Regular.ttf").getInputStream());
			medium=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrowAlt-Medium.ttf").getInputStream());
			mediumIt=Font.createFont(Font.TRUETYPE_FONT, resourceServiceImpl.getResource("classpath:fonts/TextaNarrow-MediumIt.ttf").getInputStream());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void terminalConnected(boolean b) {
		termPresent=b;
		switch(operateur.getRole()){
		case 2: case 3: case 4:
			if (b){
				stateLabel.setText("connect\u00e9");
				stateLabel.setForeground(Colors.green1);
				lecteurLabel.setForeground(Colors.green1);
				
			}else{
				stateLabel.setText("d\u00e9connect\u00e9");
				stateLabel.setForeground(Colors.red);
				lecteurLabel.setForeground(Colors.red);
			}
			break;
		case 1:
			selReaderInfo.setText("En attente de bracelet...");
			break;
			
		}
		
	}
	
	protected MaskFormatter createFormatter(String s){
		MaskFormatter formatter = null;
		try{
			formatter = new MaskFormatter(s);
		} catch(java.text.ParseException exc) {
			System.err.println("Mauvais format");
		}
		return formatter;
	}
	
	private void creUpdateTF(BigDecimal ajout){
		if(ajout.intValue()==0){
			creAddUTF.setText("00");
		}else if(ajout.intValue()<10){
			creAddUTF.setText("0"+ajout.intValue());
		}else{
			creAddUTF.setText(ajout.intValue()+"");
		}
		
		if(ajout.movePointRight(2).remainder(new BigDecimal(100)).intValue()==0){
			creAddDTF.setText("00");
		}else if(ajout.movePointRight(2).remainder(new BigDecimal(100)).intValue()<10){
			creAddDTF.setText("0"+ajout.movePointRight(2).remainder(new BigDecimal(100)).intValue());
		}else{
			creAddDTF.setText(ajout.movePointRight(2).remainder(new BigDecimal(100)).intValue()+"");
		}
		
		creValFinalSoldeLabel.setText(new Euros(client.getSolde().add(ajout)).toString());
		creValFinalSoldeLabel.repaint();
	}
	
	private void adminUpdateTF(BigDecimal ajout){
		if(ajout.intValue()==0){
			creAddUTF.setText("00");
		}else if(ajout.intValue()<10){
			creAddUTF.setText("0"+ajout.intValue());
		}else{
			creAddUTF.setText(ajout.intValue()+"");
		}
		
		if(ajout.movePointRight(2).remainder(new BigDecimal(100)).intValue()==0){
			creAddDTF.setText("00");
		}else if(ajout.movePointRight(2).remainder(new BigDecimal(100)).intValue()<10){
			creAddDTF.setText("0"+ajout.movePointRight(2).remainder(new BigDecimal(100)).intValue());
		}else{
			creAddDTF.setText(ajout.movePointRight(2).remainder(new BigDecimal(100)).intValue()+"");
		}
	}
	
	ActionListener adminPageListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(adminTrans1PageButton)){
				if(adminCurPage!=1){
					adminCurPage=1;
					adminShowTransactionPage(0);
					adminTransPageTF.setText("1");
				}
			}else if(e.getSource().equals(adminTrans10PageButton)){
				adminCurPage+=10;
				adminTransPageTF.setText(adminCurPage+"");
				adminShowTransactionPage(adminCurPage-1);
			}else if(e.getSource().equals(adminTransNextPageButton)){
				adminCurPage+=1;
				adminTransPageTF.setText(adminCurPage+"");
				adminShowTransactionPage(adminCurPage-1);
			}else if(e.getSource().equals(adminTransPrevPageButton)){
				if(adminCurPage!=1){
					adminCurPage-=1;
					adminTransPageTF.setText(adminCurPage+"");
					adminShowTransactionPage(adminCurPage-1);
				}
			}else if(e.getSource().equals(adminTransGoPageButton)){
				try{
					if(Integer.parseInt(adminTransPageTF.getText())!=adminCurPage){
						adminCurPage=Integer.parseInt(adminTransPageTF.getText());
						adminTransPageTF.setText(adminCurPage+"");
						adminShowTransactionPage(adminCurPage-1);	
					}
				}catch (NumberFormatException ex){
					adminTransPageTF.setText(adminCurPage+"");
				}
			}
		}
	};
	
	ActionListener consoBtnListener = new ActionListener(){
    	public void actionPerformed(ActionEvent evt){
    		monPanier.addConso(((ConsoBtn)evt.getSource()).getConso());
    		updateConsoBtn();
    	}
	};
	
	ActionListener tabsListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(tab1Btn)&&!tab1Btn.isSel()){
				remove(adminFundingPanel);
				initAdminFundingPanel();
				revalidate();
				repaint();
				
				tab1Btn.setSel(true);
				tab2Btn.setSel(false);
				tab3Btn.setSel(false);
			}else if(e.getSource().equals(tab2Btn)&&!tab2Btn.isSel()){
				remove(adminFundingPanel);
				initAdminCreatePanel();
				revalidate();
				repaint();
				
				tab1Btn.setSel(false);
				tab2Btn.setSel(true);
				tab3Btn.setSel(false);
				readWorker.cancel(true);
			}else if(e.getSource().equals(tab3Btn)&&!tab3Btn.isSel()){
				remove(adminFundingPanel);
				initAdminTransactPanel();
				revalidate();
				repaint();
				tab1Btn.setSel(false);
				tab2Btn.setSel(false);
				tab3Btn.setSel(true);
				readWorker.cancel(true);
			}
		}
	};
	
	ActionListener cashButtonListener = new ActionListener(){
    	public void actionPerformed(ActionEvent evt){
    		CashButton cbt = (CashButton)evt.getSource();
    		BigDecimal ajoutDeja=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
    		if(ajoutDeja.add(cbt.getVal()).compareTo(new BigDecimal(100))==-1){
    			ajoutDeja=ajoutDeja.add(cbt.getVal());
    			creUpdateTF(ajoutDeja);
    		}
    	}
	};
	
	ActionListener mainButtonListener = new ActionListener(){
    	public void actionPerformed(ActionEvent evt){
    		if(evt.getSource()==okButton){
    			try{
    				Client monClient = clientServiceImpl.getFromWristband(Integer.parseInt(numberTF.getText()));
    				if (monClient!=null){
    					remove(mainPanel);
    					remove(readerPanel);
    					revalidate();
    					repaint();
    					switch(operateur.getRole()){
    					case 3: case 4:
    						initBarmanOrderingPanel(monClient);
    						break;
    					case 2:
    						initCrediteurOrderingPanel(monClient);
    						break;
    					}
    					readWorker.cancel(true);
    				}else{
    					JOptionPane dial = new JOptionPane();
    					JOptionPane.showMessageDialog(dial,"Client inconnu. Veuillez r\u00e9essayer.","Erreur",JOptionPane.ERROR_MESSAGE);

    				}
    			}catch(java.lang.NumberFormatException e){
    				JOptionPane dial = new JOptionPane();
    				JOptionPane.showMessageDialog(dial,"Merci de rentrer un nombre valide.","Erreur",JOptionPane.ERROR_MESSAGE);
    			} catch(CancellationException e){
				}
    		}else if(evt.getSource()==barValValButton){
    			if(!monPanier.isEmpty()){
    				client.setEurosSolde(Euros.substract(client.getEurosSolde(), monPanier.getPrix()));
    				Debit d = new Debit(operateur,operateur.getRole()-2,client,monPanier.getPrix(),Panier.pToString(monPanier),false);
    				d.setStats_qtes(monPanier.getQtes());
    				transactionServiceImpl.add(d);
    				clientServiceImpl.saveSolde(client.getId(), client.getEurosSolde());
    				transactionServiceImpl.addTransactionLocal(d);
    				remove(barmanOrderingPanel);
    				initMainPanel();
    				initReaderPanel();
    				revalidate();
    				repaint();
    			}			
    		}else if(evt.getSource()==barValCanButton){
				remove(barmanOrderingPanel);
				initReaderPanel();
				initMainPanel();
				revalidate();
				repaint();
    		}else if(evt.getSource()==disconnectButton){
    			operateurServiceImpl.disconnect(operateur.getPseudo());
    			transactionServiceImpl.clearTransactionLocal();
    			setVisible(false);
    			((LoginFrame)applicationContext.getBean("loginFrame")).setVisible(true);
    			getContentPane().removeAll();
    			initOperateurPanel();
    			readWorker.cancel(true);
    			revalidate();
    			repaint();
    		}else if(evt.getSource()==creAddResetButton){
    			creAddUTF.setText("00");
    			creAddDTF.setText("00");
    			creValFinalSoldeLabel.setText(client.getEurosSolde().toString());
    		}else if(evt.getSource()==creValValButton){
    			if(!(creAddDTF.getText().equals("00")&&creAddUTF.getText().equals("00"))){
    				BigDecimal ajout=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
    				client.setSolde(client.getSolde().add(ajout));
    				Credit c = new Credit(operateur,client,new Euros(ajout),false,creModePaiementPanel.getModePaiement());
    				transactionServiceImpl.add(c);
    				clientServiceImpl.saveSolde(client.getId(), client.getEurosSolde());
    				transactionServiceImpl.addTransactionLocal(c);
    				remove(crediteurOrderingPanel);
    				initMainPanel();
    				initReaderPanel();
    				revalidate();
    				repaint();
    			}
    		}else if(evt.getSource()==creValCanButton){
				remove(crediteurOrderingPanel);
				initReaderPanel();
				initMainPanel();
				revalidate();
				repaint();
    		}else if(evt.getSource()==selResetButton){
    			selClientsId=new ArrayList<Integer>();
				for (int i=0;i<allClients.size();i++){
					selClientsId.add(i);
				}
				selIdTF.setText("");
				selNomTF.setText("");
				selPrenomTF.setText("");
				selDelegationTF.setText("");
				updateAdminFundingClientPanel();
    		}else if(evt.getSource()==adminFundingValButton){
    			BigDecimal nS=new BigDecimal(Integer.parseInt(creAddUTF.getText())).add(new BigDecimal(Integer.parseInt(creAddDTF.getText())).movePointLeft(2));
				BigDecimal ajout = nS.subtract(client.getSolde());
				boolean isAdd=true;
				if(ajout.signum()==-1){
					isAdd=false;
				}
    			if(!nS.equals(client.getSolde())){
    				TransactAdmin ta = new TransactAdmin(operateur,client,new Euros(ajout.abs()),isAdd,false);
    				client.setSolde(nS);
    				clientServiceImpl.saveSolde(client.getId(), client.getEurosSolde());
    				transactionServiceImpl.add(ta);
    				transactionServiceImpl.addTransactionLocal(ta);
        			selClientsId=new ArrayList<Integer>();
    				for (int i=0;i<allClients.size();i++){
    					selClientsId.add(i);
    				}
    				selIdTF.setText("");
    				selNomTF.setText("");
    				selPrenomTF.setText("");
    				selDelegationTF.setText("");
    				updateAdminFundingClientPanel();
    			}
    			
    		}else if(evt.getSource().equals(adminLoadImageButton)){
    			JFileChooser choice = new JFileChooser();
    			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG","JPG","JPEG");
    			choice.setFileFilter(filter);
    			filter = new FileNameExtensionFilter("PNG","PNG");
    			choice.addChoosableFileFilter(filter);
    			filter = new FileNameExtensionFilter("BMP","BMP");
    			choice.addChoosableFileFilter(filter);
    			choice.setMultiSelectionEnabled(false);
    			choice.showOpenDialog(choice);
    			adminAddImageFile = choice.getSelectedFile();
    			if (choice!=null){
    				adminAddStage(2);
    			}
    			adminAddUpdateClient(true);
    		}else if(evt.getSource().equals(adminAddResetButton)){
    			adminAddStage(5);
    		}else if(evt.getSource().equals(adminBot3Button)){
    			Integer id=clientServiceImpl.add(client);
    			clientServiceImpl.setPhotoFromFile(id, adminAddImageFile);
    			adminAddStage(3);
    			selIdLbl.setText(""+id);
    			clientIdLabel.setText("\u0023"+id);
    		}else if(evt.getSource().equals(adminTransOKBtn)){
    			adminFilterTransactions();
    			adminShowTransactionPage(0);
    			adminTransPageTF.setText("1");
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
	        	terminalConnected(false);
	        	System.out.println("Pas de lecteur");
	        	return;
	        }
	        
	        // LE LECTEUR EST PRESENT, ON LE SIGNIFIE A L IHM
	        terminalConnected(true); 
	        System.out.println("Ca marche négro");    
		}catch(CardException e){
			System.err.println(e);
		}
	}
	
	private void transfert(){
		readWorker = new SwingWorker<Integer, Integer>(){
			@Override
			protected Integer doInBackground() throws Exception {
				try {
					if(terminal==null){
						return null;
					}
					terminal.waitForCardAbsent(0);
					if(!terminal.waitForCardPresent(10000000)){
						return null;
					}
					
					// CONNEXION A LA CARTE
					card = terminal.connect("T=1");
					channel = card.getBasicChannel();
					
					//AUTHENTIFICATION
					auth(8);
					
					//LECTURE DU ID CODE PAR LE NOMBRE PREMIER 27691
					Integer id=Integer.parseInt(read(8))/27691;

					return id;
					
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

				} catch (ReaderException e2) {
					System.err.println(e2);
					//Notify the GUI we need to get going again
					publish(4);
					return null;
				} catch (NumberFormatException ex){
					System.out.println("Juste un petit souci, np");
					//Notify the GUI we need to get going again
					publish(4);
					return null;
				}
			}
			
			protected void done(){
				try {
					if(get()!=null){
						Client monClient = clientServiceImpl.getFromWristband(get());
						if(monClient!=null){
							switch(operateur.getRole()){
							case 3: case4:
								remove(mainPanel);
								remove(readerPanel);
								revalidate();
								repaint();
								initBarmanOrderingPanel(monClient);
								break;
							case 2:
								remove(mainPanel);
								remove(readerPanel);
								revalidate();
								repaint();
								initCrediteurOrderingPanel(monClient);
								break;
							case 1:
								client=monClient;
								selClientsId=new ArrayList<Integer>();
								selClientsId.add(client.getId()-1);
								updateAdminFundingClientPanel();
								transfert();
								break;
							}
							
						}
					}
				} catch (InterruptedException e) {
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			protected void process(List<Integer> param){
				for(Integer p:param){
					switch (p){
					case 4 :
						transfert();
						break;
					case 5 :
						initReader();
						transfert();
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
}
