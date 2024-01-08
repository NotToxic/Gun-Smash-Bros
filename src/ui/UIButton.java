package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

//Import libraries


//Buttons for the navbar section of gui
public class UIButton extends JButton {
	public String path;
	private DisplayPanel displayPanel;

	// Customize properties when creating button
	private void initialize() {
		// Set the background color to a dark gray
		setBackground(new Color(0, 0, 0));
		
		// Set the foreground color to white
		setForeground(new Color(219, 226, 239));
		
		setOpaque(true);

		setFocusPainted(false);  // Remove the default focus border
		setBorderPainted(false); // Remove the default button border

		// Add action listeners to switch between panels
		addActionListener(new ActionListener() {
            
			@Override
			// Change panel based on path
			public void actionPerformed(ActionEvent e) {
			  displayPanel.changePanel(path); 
			}
		});
	}

	//Constructor
	public UIButton(String text, String path, DisplayPanel displayPanel) {
		super(text);
		this.path = path;
		this.displayPanel = displayPanel;
        
		initialize();
	}
}

