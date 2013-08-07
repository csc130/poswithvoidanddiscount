package pointOfSale;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: Tracks restaurant menu categories.  Each category object acts as a header for a number
 * of Item objects.  Class was created to help present the user with an organized menu.  Intended to be used
 * in an array which interacts with a multi-dimensional array of Item objects.
 *
 */

//////////////////modified 7/26/2013	By Ting Zheng
public class Category1 implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;  //Added to satisfy compiler
	private Category1 categorylink;
	private ItemNode head, current, previous;
	private String categoryName;
	private int size;
	/**
	 * Initializes a category object with a system provided name and boolean value representing 
	 * whether the category is currently active.
	 * @param newName Category name
	 * @param activeState True if category is active, false if it is inactive
	 */
	Category1(String newName)
	{
		categoryName = newName;
		head = null;
		current = null;
		previous = null;
		categorylink = null;
		size =0;
	}
	
	/**
	 * Returns the category name.
	 * @return Category name
	 */
	public void setCategoryLink (Category1 categorylink) {
		this.categorylink = categorylink;
	}
	public Category1 getCategoryLink () {
		return categorylink;
	}
	public String getCategoryName()
	{
		return categoryName;
	}
	/**
	 * Sets the category name to a new String value
	 * @param newName String representing the new category name
	 */
	public void setCategoryName(String newName)
	{
		categoryName = newName;
	}
	public void addItem(String newPrice, String newName)
	{
		ItemNode newNode = new ItemNode(newPrice, newName);
		if (isEmpty()) {
			head = newNode;
			current = newNode;
			previous = newNode;
			size++;
		}else if (previous.getlink() !=null) {
			current.setLink(newNode);
			previous = current;
			current = newNode;
			size++;
		}else {
			previous.setLink(newNode);
			current = newNode;
		}
	}
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (head == null) {
			return true;
		}
		return false;
	}

	/**
	 * Changes the active state of the item object to false, Changes price and name values to "Empty"
	 */
	public void deleteItem(String name)
	{
		ItemNode curr  = head;
		ItemNode previous = head;
		boolean found = false;
		
		while (!found && curr !=null) {
			if (curr.getName().equalsIgnoreCase(name)){
				if (curr == head) {
					head = head.getlink();
					found = true;
					size--;
				}else {
					if (curr == this.previous) {
						this.previous =previous;
					}else if (this.current ==curr) {
						this.current =previous;
					}
					previous.setLink(curr.getlink());
					found = true;
					size--;
				}
			}else {
				previous = curr;
				curr = curr.getlink();
			}
		
		}
	}
	public ItemNode findItem(String itemName) {
		ItemNode curr  = head;
		boolean found = false;		
		
		while (curr != null && !found){
			if (curr.getName().equalsIgnoreCase(itemName)) {
				found = true;
			}else {
				curr = curr.getlink();
			}
			
		}
		
		return curr;
	}
	public ItemNode getFirstItem() {
		return head;
	}
	public int getItemSize() {
		return size;
	}
	public String toString() {
		//display items
		ItemNode curr = head;
		String list = "";
		while (curr != null) {
			list=list + curr.getName()+" "+ curr.getPrice();
			curr =curr.getlink();
		}
		return list;
	}

}
