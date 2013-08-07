package pointOfSale;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: A grid of MenuButtons which represent either the restaurants food categories or the items in any
 * particular category.  These buttons are used to interact with the Item and Category arrays which contain
 * the restaurant's menu data.  Those arrays are read from a serialized binary file save in the system.
 * This class is a component of the TransactionGUI class.
 *
 */

//////////////////modified 7/26/2013	By Ting Zheng
public class ItemPanel extends JPanel implements ActionListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final String CATEGORY_LIST = "Files/Menu/CategoryList";
	private int categoryButtonS = 0;			//initial
	private int LongSize=0;						//initial
	private MenuButton[] mButton;
//	private MenuButton[] button = new MenuButton[buttonSize];
	private CategoryNode categorys = new CategoryNode();
	/**
	 * Initializes the screen with an array of 32 MenuButton objects, organized into 8 rows and
	 * 4 columns.  Defaults the objects to represent categories initially.
	 */
	ItemPanel()
	{
		setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, DARK_CHAMPAGNE));
		setBackground(DARK_CHAMPAGNE);
		setLayout(new GridLayout(8,4));
	
		addMouseListener(this);
		initializeArrays();
		mButton = new MenuButton[LongSize];
		initializeButtons();
		displayCategories();
	}
	/**
	 * MenuButton ActionListener. If a button is clicked when categories are being displayed, changes the
	 * buttons to display all items in that category.  If an item button is clicked, that item's date is added
	 * to the receipt panel.
	 */
	public void actionPerformed(ActionEvent event)
	{
		
		String command = event.getActionCommand()+"";
		String index = command.substring(0,4);
		
		if(index.equalsIgnoreCase("llll"))		// "llll" is added to the front of the name to a reference category
			displayItems(command.substring(4,command.length()));
		else
		{						
			String[] item = command.split("llll");	
			
			CategoryNode newCategory = categorys;
			Category1 newCat= newCategory.findCategory(item[0]);
			ItemNode target = newCat.findItem(item[1]);
			ReceiptPanel.addItem(target.getPrice(), target.getName());
			
		}
	}
	/**
	 * This MouseListener is assigned to the ItemPanel as a whole and to each button.  If the user clicks the
	 * right mouse button, then the buttons change to display categories, allowing the user to quickly
	 * browse the restaurant menu.
	 */
	public void mousePressed(MouseEvent event)
	{
		if(event.getButton() == MouseEvent.BUTTON3)
			displayCategories();
	}
	public void mouseClicked(MouseEvent event)
	{
	}
	public void mouseReleased(MouseEvent event)
	{
	}
	public void mouseEntered(MouseEvent event)
	{
	}
	public void mouseExited(MouseEvent event)
	{
	}
	/**
	 * Changes the buttons to display food categories.  Called by this class and the TransactionGUI class.
	 */
	public void displayCategories()
	{
		int count = 0;
		CategoryNode newCategory = categorys;
		Category1  node = categorys.getfirst();
		while (node != null) {
			
			mButton[count].setText(node.getCategoryName());
			
			// "llll" is added to the front of the name to a reference category
			mButton[count].setActionCommand("llll"+ node.getCategoryName());
			
			node = node.getCategoryLink();
			mButton[count].setVisible(true);
			count++;
		} for (; count <LongSize; count ++) {
			mButton[count].setVisible(false);
		}

				
				
		Tools.update(this);
	}
	/**
	 * Private helper method which changes the buttons to display individual menu items associated with the
	 * same category
	 * @param category Category selected by the user
	 */
	private void displayItems(String categoryName)
	{
		int count = 0;
		CategoryNode newCategory = categorys;
		Category1 newCat= newCategory.findCategory(categoryName);
		ItemNode firstItem = newCat.getFirstItem();
			while (firstItem != null) {				
				mButton[count].setText(firstItem.getName());
				// "llll" is added to middle of the category and item names to reference item
				mButton[count].setActionCommand(categoryName +"llll"+ firstItem.getName());
				mButton[count].setVisible(true);
				count++;
				firstItem = firstItem.getlink();
			}
			for(; count < LongSize; count++)
				mButton[count].setVisible(false);
			
		
					
		Tools.update(this);
	}
	/**
	 * Private helper method which initializes the MenuButton array, and adds a MouseListener to each button
	 */
	private void initializeButtons()
	{

	
		for(int count=0; count<LongSize; count++)
		{
			mButton[count] = new MenuButton("Empty","null",this);
			mButton[count].addMouseListener(this);
			mButton[count].setVisible(false);
			add(mButton[count]);
			
		}
	}
	/**
	 * Private helper method which reads the Item and Category arrays from the system in their respective binary
	 * files.
	 */
	private void initializeArrays()
	{
		ObjectInputStream readObject = null;
		
		try
		{
		
			
			readObject = new ObjectInputStream(new FileInputStream(CATEGORY_LIST));
			categorys = (CategoryNode)readObject.readObject();
			categoryButtonS = categorys.getCatSize();
			CategoryNode newCategorys = categorys;
			Category1 singleCategory = categorys.getfirst();
			while (singleCategory != null) {
				int size =categorys.getfirst().getItemSize();
			if (size > LongSize) {
				LongSize=size;
			}if (categorys.getCatSize()>LongSize) {
				LongSize = categorys.getCatSize();
			}
			singleCategory = singleCategory.getCategoryLink();
			}
			readObject.close();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"ERROR: Arrays Not Loaded Correctly");
		}
		catch(ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"ERROR: Local Array Class Not Found");
		}
	}
}
