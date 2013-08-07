package pointOfSale;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.event.ActionListener;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: An extension of the JButton class containing customized color and border settings.  
 * Creates a button object an automatically assigns an action listener designated in the constructor.  
 *
 */
public class MenuButton extends JButton
{
	private static final long serialVersionUID = 1L;
	private static final  Color PALE_GOLDENROD = new Color(238,232,170);
	
	/**
	 * Sets the initial conditions of the button.  The color and border of the MenuButton objects has
	 * been standardized for this program.
	 * @param label Text which should appear on the button
	 * @param command ActionCommand associated with this button
	 * @param listener ActionListener to be assigned to the button
	 */
	MenuButton(String label, String command, ActionListener listener)
	{
		setText(label);
		setActionCommand(command);
		setBackground(PALE_GOLDENROD);
		setBorder(BorderFactory.createRaisedBevelBorder());
		addActionListener(listener);
	}
}
