package ui;

//Import libraries
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**Buttons for the navbar section of gui*/
public class UIButton extends JButton {
	public String path;
	/**Customize properties when creating button*/
	private void initialize() {
		//Set the background color to a dark gray
		setBackground(new Color(0, 0, 0));
		
		//Set the foreground color to white
		setForeground(new Color(219, 226, 239));
		
		setOpaque(true);

		setFocusPainted(false);  // Remove the default focus border
		setBorderPainted(false); // Remove the default button border

	}

	/**Constructor for panel switching buttons
	 * @param text what the UIButton displays
	 * @param path the JPanel that the UIButton leads to
	 * @param displayPanel the displayPanel for actionListeners
	*/
	public UIButton(String text, String path, DisplayPanel displayPanel) {
		super(text);
		this.path = path;
		addActionListener(new ActionListener() {
            
			@Override
			// Change panel based on path
			public void actionPerformed(ActionEvent e) {
			  displayPanel.changePanel(path); 
			}
		});

		initialize();
	}

	/**Constructor for regular JButtons
	 * @param text the text the UIButton displays
	*/
	public UIButton(String text) {
		super(text);
		initialize();
	}
}

