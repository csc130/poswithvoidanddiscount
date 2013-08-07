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
public class SearchPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;  //Added to satisfy compiler
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final String RECEIPT_PATH = "Files/Receipts/";
	
	private JPanel buttonPanel = new JPanel(new GridLayout(1,2));
	private JLabel panelLabel = new JLabel("Search for Receipt",SwingConstants.CENTER);
	private JLabel displayLabel = new JLabel("Current Receipt Loaded:");
	private JLabel entryLabel = new JLabel("Search by Ticket Number:");
	private JTextField displayField = new JTextField("",41);
	private JTextField entryField = new JTextField("Enter Ticket Number",41);
	
	/**
	 * Arranges all necessary components onto a JPanel which is added to the administrator screen.  The current
	 * tax is display on an uneditable text field.  The user user enters the new tax rate in a separate text field.
	 */
	SearchPanel()
	{
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createMatteBorder(10,10,10,10,DARK_CHAMPAGNE));
		setBackground(DARK_CHAMPAGNE);
		
		panelLabel.setVerticalAlignment(SwingConstants.TOP);
		panelLabel.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		
		displayLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		displayLabel.setFont(new Font(Font.SERIF,Font.ITALIC,18));
		
		entryLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		entryLabel.setFont(new Font(Font.SERIF,Font.ITALIC,18));
		
		displayField.setBorder(BorderFactory.createLoweredBevelBorder());
		displayField.setText("Test");
		displayField.setEditable(false);
		
		entryField.setBorder(BorderFactory.createLoweredBevelBorder());
		entryField.addMouseListener(new TextFieldEraser());
		
		buttonPanel.setBackground(DARK_CHAMPAGNE);
		Tools.addBlankSpace(buttonPanel,1);
		buttonPanel.add(new MenuButton("Search","Search",this));
		
		add(panelLabel);
		Tools.addBlankSpace(this,1);
		add(displayLabel);
		add(displayField);
		Tools.addBlankSpace(this,1);
		add(entryLabel);
		add(entryField);
		add(buttonPanel);
		Tools.addBlankSpace(this,4);
	}
	/**
	 * Responds to the user clicking the "Update" buttons by modifying the current tax rate to reflect the user
	 * entered tax rate, saving the change.  Contains error checking to ensure the user entry is not empty and
	 * represents a non-negative number.
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("Search"))
		{
			String entry = entryField.getText().trim();
			ReceiptLoader.loadTicketedReceipt(entry);
			displayField.setText(entry);
			entryField.setText("");
			
			if(entry.equals(""))
				JOptionPane.showMessageDialog(null,"ERROR: Invalid Entry");
			else
				ReceiptLoader.setIsEmpty(false);
		}
	}
	
}
