package pointOfSale;
import javax.swing.JLabel;
import java.awt.Container;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: General methods used as tools throughout the program 
 *
 */
public class Tools 
{
	private static int index;
	
	/**
	 * Adds a specified number of blank spaces to a container in the form of invisible labels.  Intended
	 * to be used with the GridLayout layout manager when customizing the interface.
	 * @param contentPane Container which blank spaces will be added to
	 * @param spaces Number of blank spaces to add.
	 */
	public static void addBlankSpace(Container contentPane, int spaces)
	{
		for(;spaces > 0; spaces--)
		{
			blankSpace(contentPane);
		}
	}
	private static void blankSpace(Container contentPane)
	{
		JLabel blankSpace = new JLabel();
		contentPane.add(blankSpace);
		blankSpace.setVisible(false);
	}
	/**
	 * Method to validate and refresh the selected container
	 * @param contentPane the container to be updated
	 */
	public static void update(Container contentPane)
	{
		contentPane.validate();
		contentPane.setVisible(false);
		contentPane.setVisible(true);
	}
	/**
	 * Method to convert a String representation of a number into dollar format
	 * @param number the number to be converted 
	 * @return the dollar representation of given number
	 */
	public static String toMoney(String number)
	{
		if(number.length() == 1)
			return "$.0" + number;
		else if(number.length() == 2)
			return "$." + number;
		else
			return "$" + number.substring(0,number.length()-2) + "." + number.substring(number.length()-2);
	}
	/**
	 * Method to convert a long value into String with dollar format
	 * @param number number to be converted String
	 * @return String representation of given number
	 */
	public static String toMoney(long number)
	{
		return toMoney(String.valueOf(number));
	}
	/**
	 * Converts the String representation of a dollar amount into a long value representing that dollar
	 * amount as a quantity of pennies by checking for the index of the decimal place.
	 * @param price String to be converted to long value 
	 * @return price parsed into long value
	 */
	public static long toAmount(String price)
	{
		if(price.charAt(0) == '$')
			price = price.substring(1);
		
		index = price.indexOf(".");
		
		if(index == -1)
			price = price + "00";
		else if(index == price.length()-1)
			price = price.substring(0,price.length()-1) + "00";
		else if(index == 0 && price.length() == 2)
			price = price.substring(1) + "0";
		else if(index == 0 && price.length() == 3)
			price = price.substring(1);
		else if(price.substring(index).length() == 2)
			price = price.substring(0,price.indexOf(".")) + price.substring(price.indexOf(".")+1) + "0";
		else
			price = price.substring(0,price.indexOf(".")) + price.substring(price.indexOf(".")+1);
		
		return Long.parseLong(price);
	}
	/**
	 * Checks to see whether the given string is in dollar format
	 * @param amount
	 * @return true if String input can be given a dollar value, else false
	 */
	public static boolean isMoney(String amount)
	{
		if(amount.charAt(0) == '$' && amount.length() > 1)
			amount = amount.substring(1);
		
		index = amount.indexOf(".");
		
		if(index == -1 || amount.substring(index).length() < 4)
			return isNonNegativeNumber(amount);
		else
			return false;
	}
	/**
	 * Checks to see whether given String represents a non-negative number
	 * @param value String to be evaluated
	 * @return true if given String is non-negative, else false
	 */
	public static boolean isNonNegativeNumber(String value)
	{
		try
		{
			return Double.parseDouble(value) >= 0;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
}
