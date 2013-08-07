package pointOfSale;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: Allows a user with admin privileges to edit the restaurant menu by adding/changing/deleting categories
 * or adding/changing/deleting items with a limit of 32 categories and 32 items per category.  This data is
 * serialized and saved to the system.  This class is a component of the AdminGUI class.  
 *
 */
public class MenuEditor extends JPanel implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 1L;  //Added to satisfy compiler
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final String CATEGORY_LIST = "Files/Menu/CategoryList";
	private CategoryNode categorys = new CategoryNode();
	private DefaultListModel<String> categoryModel = new DefaultListModel<String>();
	private DefaultListModel<String> itemModel = new DefaultListModel<String>();
	private JList<String> categoryList = new JList<String>(categoryModel);
	private JList<String> itemList = new JList<String>(itemModel);
	private JTextField categoryNameField = new JTextField("Enter Category Name",31);
	private JTextField itemNameField = new JTextField("Enter Item Name",31);
	private JTextField itemPriceField = new JTextField("Enter Item Price",31);
	private JLabel titleLabel = new JLabel("Edit Menu",SwingConstants.CENTER);
	private JLabel categoryLabel = null;
	private JLabel itemLabel = new JLabel("Select an Item (" + itemModel.getSize() +"/"+ itemModel.getSize()+")");
	private JPanel categoryPanel = new JPanel(new GridLayout(3,1));
	private JPanel categoryHeader = new JPanel(new GridLayout(2,1));
	private JPanel categoryLower = new JPanel(new GridLayout(3,1));
	private JPanel categoryButtons = new JPanel(new GridLayout(1,3));
	private JPanel categoryFieldPanel = new JPanel(new GridLayout(1,2));
	private JPanel itemPanel = new JPanel(new GridLayout(3,1));
	private JPanel itemLower = new JPanel(new GridLayout(3,1));
	private JPanel itemButtons = new JPanel(new GridLayout(1,3));
	private JPanel itemNameFieldPanel = new JPanel(new GridLayout(1,2));
	private JPanel itemPriceFieldPanel = new JPanel(new GridLayout(1,2));
	private int catIndex;
	private int itemIndex;
	private int activeCats;
	private int activeItems;
	/**
	 * Arranges all components onto a JPanel.  Categories are added to a JList in the upper half of the panel,
	 * while items are displayed in a JList on the lower half of the panel.  The item JList is refreshed whenever
	 * the user selects a new category to display the items in that category.  
	 */
	MenuEditor()
	{
		setBackground(DARK_CHAMPAGNE);
		setBorder(BorderFactory.createMatteBorder(10,10,10,10,DARK_CHAMPAGNE));
		setLayout(new GridLayout(2,1));
		readArrays();
		CategoryNode newCategory = categorys;
		Category1  node = categorys.getfirst();
		while (node != null) {
			categoryModel.addElement(node.getCategoryName());
			node = node.getCategoryLink();
		}
		categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		categoryList.addListSelectionListener(this);
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		titleLabel.setVerticalAlignment(SwingConstants.TOP);
		titleLabel.setFont(new Font(Font.SERIF,Font.BOLD,24));
		categoryLabel = new JLabel("Select a Category (" + categoryModel.getSize() + ")"+ categoryModel.getSize());
		categoryLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		categoryLabel.setFont(new Font(Font.SERIF,Font.ITALIC,18));
		itemLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		itemLabel.setFont(new Font(Font.SERIF,Font.ITALIC,18));
		
		categoryNameField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(10,0,10,0,DARK_CHAMPAGNE),BorderFactory.createLoweredBevelBorder()));
		categoryNameField.addMouseListener(new TextFieldEraser());
		itemNameField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(10,0,10,0,DARK_CHAMPAGNE),BorderFactory.createLoweredBevelBorder()));
		itemNameField.addMouseListener(new TextFieldEraser());
		itemPriceField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(10,0,10,0,DARK_CHAMPAGNE),BorderFactory.createLoweredBevelBorder()));
		itemPriceField.addMouseListener(new TextFieldEraser());
		
		categoryHeader.setBackground(DARK_CHAMPAGNE);
		categoryHeader.add(titleLabel);
		categoryHeader.add(categoryLabel);
		
		categoryButtons.add(new MenuButton("Add","CatAdd",this));
		categoryButtons.add(new MenuButton("Edit","CatEdit",this));
		categoryButtons.add(new MenuButton("Delete","CatDelete",this));
		
		categoryFieldPanel.setBackground(DARK_CHAMPAGNE);
		categoryFieldPanel.add(new JLabel("New Category Name:",SwingConstants.RIGHT));
		categoryFieldPanel.add(categoryNameField);
		
		categoryLower.setBackground(DARK_CHAMPAGNE);
		categoryLower.add(categoryButtons);
		categoryLower.add(categoryFieldPanel);
		Tools.addBlankSpace(categoryLower,1);
		
		categoryPanel.add(categoryHeader);
		categoryPanel.add(new JScrollPane(categoryList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		categoryPanel.add(categoryLower);
		
		itemButtons.add(new MenuButton("Add","ItemAdd",this));
		itemButtons.add(new MenuButton("Edit","ItemEdit",this));
		itemButtons.add(new MenuButton("Delete","ItemDelete",this));
		
		itemNameFieldPanel.setBackground(DARK_CHAMPAGNE);
		itemNameFieldPanel.add(new JLabel("New Item Name:",SwingConstants.RIGHT));
		itemNameFieldPanel.add(itemNameField);
		
		itemPriceFieldPanel.setBackground(DARK_CHAMPAGNE);
		itemPriceFieldPanel.add(new JLabel("New Item Price:",SwingConstants.RIGHT));
		itemPriceFieldPanel.add(itemPriceField);
		
		itemLower.add(itemButtons);
		itemLower.add(itemNameFieldPanel);
		itemLower.add(itemPriceFieldPanel);
		
		itemPanel.setBackground(DARK_CHAMPAGNE);
		itemPanel.add(itemLabel);
		itemPanel.add(new JScrollPane(itemList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		itemPanel.add(itemLower);
		
		add(categoryPanel);
		add(itemPanel);
	}
	/**
	 * Listener associated with the category JList which refreshes the item JList to represent the food items
	 * associated with the newly selected category.
	 */
	public void valueChanged(ListSelectionEvent event)
	{
		try
		{

			String cat = categoryList.getSelectedValue();
			itemModel.removeAllElements();
			if (cat == null) {
			
			} else {
			ItemNode firstItem = categorys.findCategory(cat).getFirstItem();
			while (firstItem != null) {	
				itemModel.addElement(Tools.toMoney(firstItem.getPrice())+ "     "
													+firstItem.getName());
				
				firstItem =firstItem.getlink();
			}
			itemLabel.setText("Select an Item (" + itemModel.getSize() +"/"+itemModel.getSize());
		}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			itemLabel.setText("Select an Item (" + 0 +"/0)");
		}
	}
	/**
	 * Responds to user selections by add/changing/deleting categories and items.  Contains error checking
	 * to ensure that the user enters a valid price amount for the item price, and does not enter a blank space
	 * for item or category names.
	 */
	public void actionPerformed(ActionEvent event)
	{
		CategoryNode newCategory = categorys;
		String cat	= categoryList.getSelectedValue();
		String choice = itemList.getSelectedValue();
		
		if (choice ==null) {
			
		}else {
		String[] ItemName = choice.split("     ");
		choice =ItemName[1]; 
		}
		catIndex = categoryList.getSelectedIndex();
		activeCats = categoryModel.getSize();
		activeItems = itemModel.getSize();

		if(event.getActionCommand().equals("CatAdd"))
		{
			String newName = categoryNameField.getText().trim();
			
			if(activeCats == 40)
				JOptionPane.showMessageDialog(null,"ERROR: Category Limit Reached");
			else if(newName.equals("") || newName.equals("Enter Category Name"))
				JOptionPane.showMessageDialog(null,"ERROR: Invalid Category Name");
			else
			{
				if (cat !=null) {
				categorys.addCategory(newName);
				categoryNameField.setText("");
				resetLists();
				}
			}
		}
		else if(event.getActionCommand().equals("CatDelete"))
		{
			
			newCategory.deleteCategory(cat);
			
			resetLists();
		}
		else if(event.getActionCommand().equals("CatEdit"))
		{
			String newName = categoryNameField.getText().trim();
			
			if(newName.equals("") || newName.equals("Enter Category Name"))
				JOptionPane.showMessageDialog(null,"ERROR: Invalid Category Name");
			else
			{	if (cat != null) {
				
				newCategory.findCategory(cat).setCategoryName(newName);
				categorys  = newCategory;
				categoryNameField.setText("");
				resetLists();
			}
			}
		}
		else if(event.getActionCommand().equals("ItemAdd") && catIndex > -1)
		{
			String newName = itemNameField.getText().trim();
			String newPrice = itemPriceField.getText().trim();
			
			if(activeItems == 40)
				JOptionPane.showMessageDialog(null,"ERROR: Item Limit Reached");
			else if(newName.equals("") || newPrice.equals("") || newName.equals("Enter New Item"))
				JOptionPane.showMessageDialog(null,"ERROR: Invalid Input");
			else if(!Tools.isMoney(newPrice))
				JOptionPane.showMessageDialog(null,"ERROR: Invalid Price");
			else
			{
				if (cat != null) {
				newCategory.findCategory(cat).addItem(String.valueOf(Tools.toAmount(newPrice)), newName);
				itemModel.addElement(Tools.toMoney(newCategory.findCategory(cat).findItem(newName).getPrice()) + "     "
				                                  + newCategory.findCategory(cat).findItem(newName).getName());
				itemNameField.setText("");
				itemPriceField.setText("");
				resetItemList(cat);
				}
			}
		}
		else if(event.getActionCommand().equals("ItemDelete") && catIndex > -1 )
		{
			if (choice != null) {
			newCategory.findCategory(cat).deleteItem(choice);
				resetItemList(cat);
			}
		}
		else if(event.getActionCommand().equals("ItemEdit") && catIndex > -1)
		{
			String newName = itemNameField.getText().trim();
			String newPrice = itemPriceField.getText().trim();
			
			if(newName.equals("Enter Item Name"))
				newName = "";
			if(newPrice.equals("Enter Item Price"))
				newPrice ="";
			
			if(newName.equals("") && newPrice.equals(""))
				JOptionPane.showMessageDialog(null,"ERROR: Invalid Input");
			else if(newName.equals(""))
			{
				if(!Tools.isMoney(newPrice))
					JOptionPane.showMessageDialog(null,"ERROR: Invalid Price");
				else 
				{	
					if (cat != null)
					newCategory.findCategory(cat).findItem(choice).setPrice(String.valueOf(Tools.toAmount(newPrice)));
					itemPriceField.setText("");
					resetItemList(cat);
				}
			}
			else if(newPrice.equals(""))
			{
				
				if (cat !=null && choice != null){
				newCategory.findCategory(cat).findItem(choice).setName(newName);
				itemNameField.setText("");
				resetItemList(cat);
				}
			}
			else
			{
				if(!Tools.isMoney(newPrice))
					JOptionPane.showMessageDialog(null,"ERROR: Invalid Price");
				else 
				{
					newCategory.findCategory(cat).findItem(choice).setPrice(String.valueOf(Tools.toAmount(newPrice)));
					newCategory.findCategory(cat).findItem(choice).setName(newName);
					itemNameField.setText("");
					itemPriceField.setText("");
					resetItemList(cat);
				}
			}
		}
		Tools.update(this);
		saveArrays();
	}
	/**
	 * Resets both lists to represent modified item and category information.  Called after any user changes are
	 * made.
	 */
	private void resetLists()
	{
		categoryModel.removeAllElements();
		itemModel.removeAllElements();
		CategoryNode newCategory = categorys;
		Category1  node = categorys.getfirst();
		while (node != null) {
			categoryModel.addElement(node.getCategoryName());
			node = node.getCategoryLink();
		}
		
		categoryLabel.setText("Select a Category (" + categoryModel.getSize() + "/)" +categoryModel.getSize());
	}
	/**
	 * Resets only the item list to represent newly modified item information
	 * @param catIndex item category which needs to refreshed
	 */
	private void resetItemList(String catName)
	{
		CategoryNode newCategory = categorys;
		Category1  cat= newCategory.findCategory(catName);
		ItemNode item= cat.getFirstItem();
		itemModel.removeAllElements();
		while (item != null) {
			itemModel.addElement(Tools.toMoney(item.getPrice())+ "     "
															+item.getName());
			item =item.getlink();
		}
		
		itemLabel.setText("Select an Item (" + itemModel.getSize() +"/"+itemModel.getSize());
	}
	/**
	 * Reads the saved item and category information into the program from the system.  Item and category
	 * information is saved in serialized arrays.
	 */
	private void readArrays()
	{
		ObjectInputStream readCategory = null;
		
		try
		{
		
			readCategory = new ObjectInputStream(new FileInputStream (CATEGORY_LIST));
			categorys = (CategoryNode) readCategory.readObject();

			readCategory.close();
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
	/**
	 * Saves modified item and category data to the system in the form of serialized arrays.
	 */
	private void saveArrays()
	{
		
		ObjectOutputStream saveObject = null;
		try
		{
			saveObject = new ObjectOutputStream (new FileOutputStream (CATEGORY_LIST));
						
			saveObject.writeObject(categorys);
			saveObject.close();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"ERROR: Arrays Not Saved Correctly");
		}
	}
}
