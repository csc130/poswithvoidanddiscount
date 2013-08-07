package pointOfSale;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: Reads text files generated from the transaction screen which contain saved transactions (receipts).
 * These saved receipts are displayed on the ReceiptPanel object which is a component of the AdminGUI class.
 * This class is a component of the AdminGUI class.
 *
 */
public class ReceiptLoader extends JPanel implements ActionListener, Printable
{
	private static final long serialVersionUID = 1L;  //Added to satisfy the compiler
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final String RECEIPT_PATH = "Files/Receipts";
	private static final String RECEIPT_LIST = RECEIPT_PATH + "/ReceiptList";
	private static final String DISCOUNT_FILE = "Files/Discount/DiscountAmount";
	
	private JPanel upperPanel = new JPanel(new GridLayout(3,2));
	private JPanel buttonPanel = new JPanel(new GridLayout(2,2));
	private static DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> receiptList = new JList<String>(getListModel());
	private JLabel titleLabel = new JLabel("Load Saved Receipts", SwingConstants.CENTER);
	private JLabel listLabel = new JLabel("Select Receipt from list below", SwingConstants.LEFT);
	public static boolean isEmpty = true;
	private MenuButton printButton;
	
	/**
	 * Arranges all components in this class onto a JPanel and reads all saved receipt text files, displaying
	 * the text files names in a JList
	 */
	ReceiptLoader()
	{
		setLayout(new GridLayout(2,1));
		setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, DARK_CHAMPAGNE));
		
		readReceipts();
		receiptList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		titleLabel.setVerticalAlignment(SwingConstants.TOP);
		titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		
		listLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		listLabel.setFont(new Font(Font.SERIF, Font.ITALIC, 18));
		
		buttonPanel.setBackground(DARK_CHAMPAGNE);
		buttonPanel.add(new MenuButton("Load","Load",this));
		//Tools.addBlankSpace(buttonPanel, 1);
		
		buttonPanel.add(new MenuButton("Print", "Print", this));
		buttonPanel.add(new MenuButton("Delete","Delete",this));
		buttonPanel.add(new MenuButton("Delete All","Delete All",this));
		buttonPanel.add(new MenuButton("Discount Item","Discount Item",this));
		buttonPanel.add(new MenuButton("Discount All","Discount All",this));
		buttonPanel.add(new MenuButton("VOID Item","VOID Item",this));
		buttonPanel.add(new MenuButton("VOID All","VOID All",this));
	
		
	
		upperPanel.setBackground(DARK_CHAMPAGNE);
		upperPanel.add(titleLabel);
		upperPanel.add(buttonPanel);
		upperPanel.add(listLabel);
		
		add(upperPanel);
		add(new JScrollPane(receiptList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
	
	
	}
	/**
	 * Responds to user input by loading or deleting saved receipt text files
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("Load") && receiptList.getSelectedIndex() > -1)
		{
			ReceiptPanel.loadReceipt(receiptList.getSelectedValue());
			isEmpty = false;
		}
		if(event.getActionCommand().equals("Delete") && receiptList.getSelectedIndex() > -1)
		{
			deleteReceipt();
			isEmpty = true;
		}
		if(event.getActionCommand().equals("Delete All"))
		{
			deleteAll();
			isEmpty = true;
		}
		if(event.getActionCommand().equals("Discount Item"))
		{
			ReceiptPanel.discountItem();
			ReceiptPanel.saveCurrentReceipt(receiptList.getSelectedValue());
		}
		if(event.getActionCommand().equals("Discount All"))
		{
			ReceiptPanel.discountAll();
			ReceiptPanel.saveCurrentReceipt(receiptList.getSelectedValue());
		}
		if(event.getActionCommand().equals("VOID Item"))
		{
			ReceiptPanel.voidItem();
			ReceiptPanel.saveCurrentReceipt(receiptList.getSelectedValue());
		}
		if(event.getActionCommand().equals("VOID All"))
		{
			ReceiptPanel.voidAll();
			ReceiptPanel.saveCurrentReceipt(receiptList.getSelectedValue());
		}
		if(event.getActionCommand().equals("Print"))
		{
			if(!ReceiptLoader.isEmpty)
			{
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintable(this);
				boolean ok = job.printDialog();
				if (ok) {
					try {
	                     job.print();
					} catch (PrinterException ex) {
	              /* The job did not successfully complete */
	             }
				 }
	         }
			 else
				 JOptionPane.showMessageDialog(null,"No receipt loaded");
		}
	}


	private String manualTab(String entry) {
		// TODO Auto-generated method stub
		String tab = "";
		for(int count=0; count < 15 - entry.length(); count++)
			tab += " ";
		return tab;
	}

	/**
	 * Private helper method which is called to read the text files stored in the system
	 */
	private void readReceipts()
	{
		listModel.removeAllElements();
		Scanner inputStream = null;
		try
		{
			inputStream = new Scanner(new File(RECEIPT_LIST));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		while(inputStream.hasNextLine())
		{
			String line = inputStream.nextLine().trim();
			
			if(line.equals(""))
				;
			else
				getListModel().addElement(line);
		}
		inputStream.close();
	}
	/**
	 * Private helper method which deletes a receipt, removing it from the JList and saving the change to the
	 * system.
	 */
	private void deleteReceipt()
	{
		File file = new File(RECEIPT_PATH + "/" + receiptList.getSelectedValue());
		file.delete();
		
		getListModel().removeElementAt(receiptList.getSelectedIndex());
		
		saveReceiptList();
		ReceiptPanel.clearReceipt();
	}
	/**
	 * Private helper method which removes all receipt files from the JList and from the System
	 */
	private void deleteAll()
	{
		File receiptDirectory = new File(RECEIPT_PATH);
		File[] file = receiptDirectory.listFiles();
		
		for(int count=0; count < file.length; count++)
			file[count].delete();
		getListModel().removeAllElements();
		saveReceiptList();
		ReceiptPanel.clearReceipt();
	}
	
	public static void loadTicketedReceipt(String ticketNum)
	{
		int currentReceiptCount, currentInt;
		String currentNum;
		currentReceiptCount = Integer.parseInt(ticketNum);
		String fileName = "";
		
		for(int i = 0; i < listModel.getSize(); i++)
		{
			currentInt = Integer.parseInt(listModel.get(i).substring(listModel.get(i).indexOf("#")+1));
			if(currentReceiptCount == currentInt)
				fileName = listModel.get(i);
		}
		
		ReceiptPanel.loadReceipt(fileName);
	}
	/**
	 * Method called to save any changes made to the receipt text file data
	 */
	private void saveReceiptList()
	{
		PrintWriter listWriter = null;
		try
		{
			listWriter = new PrintWriter(RECEIPT_LIST);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		for(int count = 0; count < getListModel().getSize(); count++)
			listWriter.println(getListModel().getElementAt(count));
		listWriter.close();
	}
	
	public static DefaultListModel<String> getListModel() {
		return listModel;
	}
	
	public void setListModel(DefaultListModel<String> listModel) {
		this.listModel = listModel;
	}
	
	public static void setIsEmpty(boolean b)
	{
		isEmpty = b;
	}
	
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		// TODO Auto-generated method stub
		if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
 
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
 
        /* Now we perform our rendering */
        int space = 50;
        for(int ii = 0; ii < ReceiptPanel.getListSize(); ii++)
        {
        	g.drawString(ReceiptPanel.getListModel(ii), 50, space);
        	space += 50;
        }
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
	}
}
