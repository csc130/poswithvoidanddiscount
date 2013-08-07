package pointOfSale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.BorderFactory;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: JPanel containing only the "Back" button in the administrator screen used to return to the
 * transaction screen.
 *
 */
public class AdminButtonPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;  //Added to satisfy compiler
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	//private MenuButton printButton = new MenuButton("Print", "Print", this);
	private MenuButton exitButton = new MenuButton("Back","Back",this);
	
	/**
	 * Arranges the "Back" button on its own JPanel
	 */
	AdminButtonPanel()
	{
		setLayout(new GridLayout(4,2));
		setBackground(DARK_CHAMPAGNE);
		setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, DARK_CHAMPAGNE));
		
		Tools.addBlankSpace(this, 7);
		//add(printButton);
		add(exitButton);
		//printButton.setFont(new Font(Font.SERIF,Font.PLAIN,36));
		exitButton.setFont(new Font(Font.SERIF,Font.PLAIN,36));
	}
	/**
	 * Listens for the "Back" button to be clicked and returns the user to the transaction screen if it is
	 */
	public void actionPerformed(ActionEvent event)
	{
		/*if(event.getActionCommand().equals("Print"))
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
	               The job did not successfully complete 
	             }
				 }
	         }
			 else
				 JOptionPane.showMessageDialog(null,"No receipt loaded");
		}*/
		if(event.getActionCommand().equals("Back"))
		{
			SystemInit.setTransactionScreen();
			ReceiptLoader.setIsEmpty(true);
		}
	}
	//@Override
	/*public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		// TODO Auto-generated method stub
		if (page > 0) {  We have only one page, and 'page' is zero-based 
            return NO_SUCH_PAGE;
        }
 
         User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
 
         Now we perform our rendering 
        int space = 50;
        for(int ii = 0; ii < ReceiptPanel.getListSize(); ii++)
        {
        	g.drawString(ReceiptPanel.getListModel(ii), 50, space);
        	space += 50;
        }
         tell the caller that this page is part of the printed document 
        return PAGE_EXISTS;
	}*/
}
