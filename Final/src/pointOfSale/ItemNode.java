package pointOfSale;



////////////////////	added By Ting Zheng 7/26/2013
public class ItemNode implements java.io.Serializable{

	private ItemNode link;
	private static final long serialVersionUID = 1L;  //Added to satisfy compiler
	private String itemName;
	private String itemPrice;
	
	
	ItemNode (String newPrice, String newName)
	{
		itemPrice = newPrice;
		itemName = newName;
		link = null;
	}
	/**
	 * Returns the item price
	 * @return String value representing the item price
	 */
	public String getPrice()
	{
		return itemPrice;
	}
	/**
	 * Returns the item name
	 * @return String value representing the item's name
	 */
	public String getName()
	{
		return itemName;
	}
	/**
	 * Sets the item price to a new value
	 * @param newPrice User entered String, new item price
	 */
	public void setPrice(String newPrice)
	{
		itemPrice = newPrice;
	}
	/**
	 * Sets the item name to a new value
	 * @param newName User entered String, new item name
	 */
	public void setName(String newName)
	{
		itemName = newName;
	}
	/**
	 * Changes the active state of the item object to true, adds a new name and price
	 * @param newPrice User entered item price
	 * @param newName User entered item name
	 */
	public ItemNode getlink () {
		return link;
	}
	public void setLink (ItemNode link) {
		this.link = link;
	}

}
