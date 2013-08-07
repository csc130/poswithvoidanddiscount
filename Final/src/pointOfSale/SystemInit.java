package pointOfSale;
import javax.swing.*;

import java.awt.*;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: Defines the JFrame used as a top-level container by this program and instantiates that JFrame.
 * in a main method, initiating the application.  A single JPanel, with a GridLayout(1,1), is added to this
 * JFrame to provide a top-level border for the application.  SystemInit is interacted with by the LogInGUI,
 * TransactionGUI and AdministratorGUI classes.  These classes invoke static methods in SystemInit to change
 * screens by removing all components from the JPanel and instantiating a new object of the class which
 * the application needs to switch to, directed by user input.
 * 
 * The application is organized in a modular fashion with this class functioning as the top-level container, and
 * the LoginGUI, TransactionGUI and AdministratorGUI classes functioning as the main user interfaces which are
 * swapped out as necessary.  Those classes are extensions of JPanel and are themselves made up of other classes
 * which are extensions of JPanels, allowing each class to be inserted into another class further up the 
 * hierarchy to form the complete the screen.
 *
 */
public class SystemInit extends JFrame
{
	private static final long serialVersionUID = 1L;  //Added to satisfy the compiler
	private static final Color DARK_GREEN = new Color(0,100,0);
	private static JPanel systemPanel = new JPanel(new GridLayout(1,1));
	
	/**
	 * Main method which initiates the application by instantiating this JFrame class
	 */
	public static void main(String[] args)
	{
		SystemInit gui = new SystemInit();  
		gui.setVisible(true);
	}
	/**
	 * Defines the initial settings of the JFrame window, adds the top-level JPanel and invokes
	 * the setLogInScreen() method to default the application into the log in screen on start up
	 */
	SystemInit()
	{
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		//setSize(800,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,1));
		
		systemPanel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, DARK_GREEN));
		
		getContentPane().add(systemPanel);
		
		setLogInScreen();
	}
	/**
	 * Static method called by this class and the TransactionGUI class to switch to the log in screen
	 */
	public static void setLogInScreen()
	{
		systemPanel.removeAll();
		systemPanel.add(new LogInGUI());
		Tools.update(systemPanel);
	}
	/**
	 * Static method called by the LogInGUI class to switch to the transaction screen, where restaurant
	 * orders are actually processed by the server.
	 */
	public static void setTransactionScreen()
	{
		systemPanel.removeAll();
		systemPanel.add(new TransactionGUI());
		Tools.update(systemPanel);
	}
	/**
	 * Static method called by the TransactionGUI class to switch to the administrator screen, where
	 * a user with administrator privileges is able to edit the restaurant menu, security keys or view
	 * saved receipts
	 */
	public static void setAdminScreen()
	{
		systemPanel.removeAll();
		systemPanel.add(new AdministratorGUI());
		Tools.update(systemPanel);
	}
}
