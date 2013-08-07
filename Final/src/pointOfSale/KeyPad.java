package pointOfSale;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: A component of the LogInGUI class.  Allows the user to enter a password up to 6 characters long
 * while masking the password entered.  Evaluates the password and if it is valid, transfers the user to the
 * transaction screen.  If the password is valid, evaluates whether it is an administrator password and, if it is,
 * passes a boolean value to the TransactionGUI class which allows access to administrator privileges.  If the
 * password is not valid, the user is informed through a JOptionPane.
 *
 */
public class KeyPad extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final String SECURITY_FILE = "Files/System/SecurityCodes";
	
	private JPanel buttonRow1 = new JPanel(new GridLayout(1,4));
	private JPanel buttonRow2 = new JPanel(new GridLayout(1,4));
	private JPanel buttonRow3 = new JPanel(new GridLayout(1,4));
	private JTextField numberField = new JTextField("",11);
	private String numberCode = "";
	private boolean validCode = false;
	private boolean adminPrivilege = false;
	
	/**
	 * Constructs a panel containing a grid of buttons used to enter a password
	 */
	KeyPad()
	{
		setBorder(BorderFactory.createMatteBorder(10,10,10,10,DARK_CHAMPAGNE));
		setLayout(new GridLayout(5,1));
		setBackground(DARK_CHAMPAGNE);
		
		numberField.setEditable(false);
		numberField.setBackground(Color.WHITE);
		numberField.setFont(new Font(Font.SERIF,Font.PLAIN,18));
		
		buttonRow1.add(new MenuButton("0","0",this));
		buttonRow1.add(new MenuButton("1","1",this));
		buttonRow1.add(new MenuButton("2","2",this));
		buttonRow1.add(new MenuButton("3","3",this));
		
		buttonRow2.add(new MenuButton("4","4",this));
		buttonRow2.add(new MenuButton("5","5",this));
		buttonRow2.add(new MenuButton("6","6",this));
		buttonRow2.add(new MenuButton("7","7",this));
		
		buttonRow3.add(new MenuButton("8","8",this));
		buttonRow3.add(new MenuButton("9","9",this));
		buttonRow3.add(new MenuButton("Clear","10",this));
		buttonRow3.add(new MenuButton("Enter","11",this));
		
		add(numberField);
		Tools.addBlankSpace(this,1);
		add(buttonRow1);
		add(buttonRow2);
		add(buttonRow3);
	}
	/**
	 * Action listener assigned to the keypad.  Creates a password reflecting the user's entries, clears
	 * the password or calls checkCode() to evaluate the password.
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(Integer.parseInt(event.getActionCommand()) < 10)
		{
			if(numberCode.length() < 6)
			{
				numberCode = numberCode + event.getActionCommand();
				numberField.setText(numberField.getText() + "*");
			}
		}
		else if (event.getActionCommand().equals("10"))
		{
			numberCode = "";
			numberField.setText("");
		}
		else if (event.getActionCommand().equals("11"))
			checkCode();
	}
	/**
	 * Private helper method used to check the user entered password against a text file list of
	 * valid passwords.  If the password checks out, the user is transfered to the transaction screen
	 * along with the appropriate access privilege (server or admin)
	 */
	private void checkCode()
	{
		Scanner inputStream = null;
		try
		{
			inputStream = new Scanner(new File(SECURITY_FILE));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"ERROR: File Not Found");
		}
			
		while(inputStream.hasNextLine() && !validCode)
		{
			String line = inputStream.nextLine();
				
			if(line.equals(""))
				;
			else
			{
				validCode = numberCode.equals(line.substring(0,6));
				adminPrivilege = line.substring(7,8).equals("A");
			}
		}
		
		if(validCode)
		{
			TransactionGUI.setAdminPrivilege(adminPrivilege);
			SystemInit.setTransactionScreen();
		}
		else
		{
			JOptionPane.showMessageDialog(null,"ERROR: Invalid Security Code");
			numberCode = "";
			numberField.setText("");
		}
	}
}
