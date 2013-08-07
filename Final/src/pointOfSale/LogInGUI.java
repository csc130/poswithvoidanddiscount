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
 * Purpose: Defines a JPanel which functions as the main user interface for the application's log in screen.
 * It is composed of the JComponents defined in this class and the KeyPad class, which allows the user to enter
 * a password and evaluates that password.
 *
 */
public class LogInGUI extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final Color DARK_GREEN = new Color(0,100,0);
	
	private JPanel logoPanel = new JPanel(new GridLayout(1,1));  //Left half of the screen
	private JPanel halfPanel = new JPanel(new GridLayout(1,2));  //Right half of the screen
	private JPanel quarterPanel = new JPanel(new GridLayout(2,1));  //Right quarter of the screen
	private JPanel exitPanel = new JPanel(new GridLayout(4,2));
	private KeyPad numberPad = new KeyPad();
	private MenuButton exitButton = new MenuButton("Exit","Exit",this);
	private ImageIcon logo = new ImageIcon("Files/Icons/logoBig.jpg");
	private JLabel logoLabel = new JLabel(logo,SwingConstants.CENTER);
	private Border margin = BorderFactory.createMatteBorder(125,25,125,25,DARK_CHAMPAGNE);
	private Border edge = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
										DARK_CHAMPAGNE,DARK_CHAMPAGNE);
	
	/**
	 * Arranges the various components which populate the log in screen.  Loads the corporate logo from file
	 * and displays it in the left half of the screen.
	 */
	LogInGUI()
	{	
		setLayout(new GridLayout(1,2));
		setBackground(DARK_CHAMPAGNE);
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		logoLabel.setVerticalAlignment(SwingConstants.CENTER);
		logoPanel.setBorder(BorderFactory.createCompoundBorder(margin,edge));
		logoPanel.setBackground(DARK_GREEN);
		logoPanel.add(logoLabel); 
		
		Tools.addBlankSpace(exitPanel,7);
		exitPanel.add(exitButton);
		exitPanel.setBackground(DARK_CHAMPAGNE);
		exitPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, DARK_CHAMPAGNE));
		exitButton.setFont(new Font(Font.SERIF,Font.PLAIN,36));
		
		quarterPanel.add(numberPad);
		quarterPanel.add(exitPanel);
		
		Tools.addBlankSpace(halfPanel,1);
		halfPanel.add(quarterPanel);
		halfPanel.setBackground(DARK_CHAMPAGNE);
		
		add(logoPanel);
		add(halfPanel);
	}
	/**
	 * Ends the program if the user clicks the "Exit" button
	 */
	public void actionPerformed(ActionEvent event)
	{
		if (event.getActionCommand().equals("Exit"))
			System.exit(0);
	}
}
