package pointOfSale;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: To load the GUI for processing and saving transactions. Upon successful login, 
 * the user is able to select items from each category into a receipt panel and save the receipt
 * by pressing the "Checkout" button. Administrators are given supplemented privileges to access and change
 * system info through a "System" button. 
 *
 */
public class TransactionGUI extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final Color DARK_GREEN = new Color(0,100,0);
	private static boolean adminPrivilege;
	
	private JPanel halfPanel = new JPanel(new GridLayout(1,2));
	private JPanel transactionPanel = new JPanel(new GridLayout(3,1));
	private JPanel receiptButtonPanel = new JPanel(new GridLayout(4,3));
	private JPanel logoPanel = new JPanel(new GridLayout(1,1));
	private ItemPanel menuPanel = new ItemPanel();
	private JPanel checkoutButtonPanel = new JPanel(new GridLayout(3,1));
	private MenuButton systemButton = new MenuButton("System","System",this);
	private MenuButton checkoutButton = new MenuButton("Checkout","Checkout",this);
	private JLabel adminLabel = new JLabel("Admin: ", SwingConstants.RIGHT);
	private ImageIcon logo = new ImageIcon("Files/Icons/logoSmall.jpg");
	private JLabel logoLabel = new JLabel(logo,SwingConstants.CENTER);
	private Border margin = BorderFactory.createMatteBorder(25,25,25,25,DARK_CHAMPAGNE);
	private Border edge = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
										DARK_CHAMPAGNE,DARK_CHAMPAGNE);
	/**
	 * Main constructor which positions components for completing transactions in grid layout.
	 * Allows admin "System" button and label visibility dependent on the 
	 * determined user privilege status
	 */
	TransactionGUI()
	{
		setLayout(new GridLayout(1,2));
		setBackground(DARK_CHAMPAGNE);
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		ReceiptPanel.clearReceipt();
		
		adminLabel.setFont(new Font(Font.SERIF,Font.BOLD,24));
		adminLabel.setForeground(Color.RED);
		checkoutButton.setFont(new Font(Font.SERIF,Font.PLAIN,24));
		
		receiptButtonPanel.setBackground(DARK_CHAMPAGNE);
		receiptButtonPanel.add(new MenuButton("Delete Line","Delete Line",this));
		receiptButtonPanel.add(new MenuButton("Delete All","Delete All",this));
		receiptButtonPanel.add(new MenuButton("Log Off","Log Off",this));;
		receiptButtonPanel.add(adminLabel);
		receiptButtonPanel.add(systemButton);
		receiptButtonPanel.add(new MenuButton("Categories","Categories",this));
		Tools.addBlankSpace(receiptButtonPanel,6);
		
		logoPanel.setBackground(DARK_GREEN);
		logoLabel.setVerticalAlignment(SwingConstants.CENTER);
		logoLabel.setBorder(BorderFactory.createCompoundBorder(margin,edge));
		logoPanel.add(logoLabel);
	
		
		checkoutButtonPanel.setBackground(DARK_CHAMPAGNE);
		Tools.addBlankSpace(checkoutButtonPanel,2);
		checkoutButtonPanel.add(checkoutButton);
		
		transactionPanel.setBorder(BorderFactory.createMatteBorder(10,0,10,0,DARK_CHAMPAGNE));
		transactionPanel.setBackground(DARK_CHAMPAGNE);
		transactionPanel.add(receiptButtonPanel);
		transactionPanel.add(logoPanel);
		transactionPanel.add(checkoutButtonPanel);
		
		halfPanel.add(new ReceiptPanel());
		halfPanel.add(transactionPanel);
		
		if(adminPrivilege)
		{
			adminLabel.setVisible(true);
			systemButton.setVisible(true);
		}
		else
		{
			adminLabel.setVisible(false);
			systemButton.setVisible(false);
		}
		
		add(halfPanel);
		add(menuPanel);
		
	}
	/**
	 * ActionListener for the transaction screens system oriented or receipt panel oriented buttons.
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("Log Off"))
			SystemInit.setLogInScreen();
		else if(event.getActionCommand().equals("Delete All"))
			ReceiptPanel.clearReceipt();
		else if(event.getActionCommand().equals("Delete Line"))
			ReceiptPanel.deleteItem();
		else if(event.getActionCommand().equals("Checkout"))
			ReceiptPanel.saveReceipt();
		else if(event.getActionCommand().equals("System"))
			SystemInit.setAdminScreen();
		else if(event.getActionCommand().equals("Categories"))
			menuPanel.displayCategories();
	}
	/**
	 * Setter for the adminPrivilege variable 
	 * @param newAdminPrivilege desired new boolean value for adminPrivelege
	 */
	public static void setAdminPrivilege(boolean newAdminPrivilege)
	{
		adminPrivilege = newAdminPrivilege;
	}
}
