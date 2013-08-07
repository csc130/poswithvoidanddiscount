package pointOfSale;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: Detects whether the user clicks on the text field and clears the text within the field
 * when it is initially clicked.
 *
 */
public class TextFieldEraser implements MouseListener
{
	private boolean fieldClicked = false;
	
	/**
	 * Method to retrieve the text field component if clicked and clear the field only once
	 */
	public void mousePressed(MouseEvent event)
	{
		if(!fieldClicked)
		{
			((JTextField) event.getComponent()).setText("");
			fieldClicked = true;
		}
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
}
