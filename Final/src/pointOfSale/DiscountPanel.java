package pointOfSale;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/29/2013) 
 * Purpose: Allows a user with administrator privileges to modify the sales tax rate to reflect their local
 * sales tax rate.  The sales tax is saved in a text file, and accessed by the ReceiptPanel class when needed.
 * This class is a component of the AdminstratorGUI class.
 *
 */
public class DiscountPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;  //Added to satisfy compiler
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final String DISCOUNT_FILE = "Files/Discount/DiscountAmount";
	
	private JPanel buttonPanel = new JPanel(new GridLayout(2,2));
	private JLabel panelLabel = new JLabel("Edit Discount Amount",SwingConstants.CENTER);
	private JLabel displayLabel = new JLabel("Current Discount Amount:");
	private JLabel entryLabel = new JLabel("New Discount Amount:");
	private JTextField displayField = new JTextField("",41);
	private JTextField entryField = new JTextField("Enter Discount Amount as a Percent",41);
	private static double discountAmount = 0;
	
	/**
	 * Arranges all necessary components onto a JPanel which is added to the administrator screen.  The current
	 * tax is display on an uneditable text field.  The user user enters the new tax rate in a separate text field.
	 */
	DiscountPanel()
	{
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createMatteBorder(10,10,10,10,DARK_CHAMPAGNE));
		setBackground(DARK_CHAMPAGNE);
		
		readDiscount();
		
		panelLabel.setVerticalAlignment(SwingConstants.TOP);
		panelLabel.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		
		displayLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		displayLabel.setFont(new Font(Font.SERIF,Font.ITALIC,18));
		
		entryLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		entryLabel.setFont(new Font(Font.SERIF,Font.ITALIC,18));
		
		displayField.setBorder(BorderFactory.createLoweredBevelBorder());
		displayField.setText(String.valueOf(discountAmount) + "%");
		displayField.setEditable(false);
		
		entryField.setBorder(BorderFactory.createLoweredBevelBorder());
		entryField.addMouseListener(new TextFieldEraser());
		
		buttonPanel.setBackground(DARK_CHAMPAGNE);
		Tools.addBlankSpace(buttonPanel,1);
		buttonPanel.add(new MenuButton("Update","Update",this));
		buttonPanel.add(new MenuButton("5% Discount","5% Discount",this));
		buttonPanel.add(new MenuButton("10% Discount","10% Discount",this));
		buttonPanel.add(new MenuButton("50% Discount","50% Discount",this));
		
		
		add(panelLabel);
		Tools.addBlankSpace(this,1);
		add(displayLabel);
		add(displayField);
		Tools.addBlankSpace(this,1);
		add(entryLabel);
		add(entryField);
		add(buttonPanel);
		Tools.addBlankSpace(this,1);
		
		
		
	}
	/**
	 * Responds to the user clicking the "Update" buttons by modifying the current tax rate to reflect the user
	 * entered tax rate, saving the change.  Contains error checking to ensure the user entry is not empty and
	 * represents a non-negative number.
	 */
	public void actionPerformed(ActionEvent event)
	{
		String entry = entryField.getText().trim();
		
		if(entry.length() > 1 && entry.charAt(entry.length()-1) == '%')
			entry = entry.substring(0,entry.length()-1);
		
		if(event.getActionCommand().equals("Update"))
		{
			if(entry.equals(""))
				JOptionPane.showMessageDialog(null,"ERROR: Invalid Entry");
			else if(Tools.isNonNegativeNumber(entry))
			{
				discountAmount = Double.parseDouble(entry);
				saveDiscount();
				displayField.setText(String.valueOf(discountAmount) + "%");
				entryField.setText("");
			}
			else
				JOptionPane.showMessageDialog(null,"ERROR: Discount Amount Must Be a Non-negative Number");
		}
		
		if(event.getActionCommand().equals("5% Discount"))
		{
			discountAmount = 5.0;
			saveDiscount();
			displayField.setText(String.valueOf(discountAmount) + "%");
		}
		
		if(event.getActionCommand().equals("10% Discount"))
		{
			discountAmount = 10.0;
			saveDiscount();
			displayField.setText(String.valueOf(discountAmount) + "%");
		}
		
		if(event.getActionCommand().equals("50% Discount"))
		{
			discountAmount = 50.0;
			saveDiscount();
			displayField.setText(String.valueOf(discountAmount) + "%");
		}
		
	}
	
	/**
	 * Private helper method which reads the sales tax amount 
	 * from a text file and sets a double value equal to it.
	 */
	private void readDiscount()
	{

		Scanner inputStream = null;
		try
		{
			inputStream = new Scanner(new File(DISCOUNT_FILE));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"ERROR: Discount File Not Found");
		}
		
		discountAmount = Double.parseDouble(inputStream.next());
		inputStream.close();
	}
	/**
	 * Private helper method which saves the sales tax amount to a text
	 * file for future use by the program
	 */
	private void saveDiscount()
	{
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(new File(DISCOUNT_FILE));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "ERROR: Discount File Not Found");
		}
		outputStream.print(discountAmount);
		outputStream.close();
	}
	public static double getDiscountAmount() {
		return discountAmount;
	}
	
}
