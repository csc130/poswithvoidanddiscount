package pointOfSale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.GridLayout;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: This is the administrator window when the Administrator selects the "System" button.  Accessed
 * through the TransactionGUI class if the user has administrator privileges, as determined by the KeyPad clas.
 * Instantiates objects of the PinEditor, MenuEditor and ReceiptLoader class which can be used to view and
 * edit saved password information, view and edit saved menu information or view saved receipts generated from
 * the transaction screen.
 *
 */
public class AdministratorGUI extends JPanel
{
	private static final long serialVersionUID = 1L; //Added to satisfy compiler
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	
	private JPanel quarterPanel1 = new JPanel(new GridLayout(2,1));
	private JPanel quarterPanel2 = new JPanel(new GridLayout(2,1));
	
	/**
	 * Constructs the layout and initial properties of the administrator screen.  Instantiates objects of
	 * all sub-classes.
	 */
	AdministratorGUI()
	{
		setLayout(new GridLayout(1,4));
		setBackground(DARK_CHAMPAGNE);
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		ReceiptPanel.clearReceipt();
		
		quarterPanel1.add(new ReceiptLoader());
		quarterPanel1.add(new PinEditor());
		
		quarterPanel2.setBackground(DARK_CHAMPAGNE);
		quarterPanel2.add(new TaxPanel());
		quarterPanel2.add(new AdminButtonPanel());
		
		add(new ReceiptPanel());
		add(quarterPanel1);
		add(new SearchPanel());
		add(new MenuEditor());
		add(quarterPanel2);
		add(new DiscountPanel());
		add(quarterPanel2);
		
	}
}
