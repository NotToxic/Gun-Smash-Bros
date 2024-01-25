package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class HelpPanel extends JPanel {

  /**To connect to the network of other JPanels */
  DisplayPanel displayPanel;
  /**BufferedImage to display the helpImage */
  BufferedImage imgHelpScreen = null;
  /**A UIButton to go back to the main menu */
  UIButton backButton;

  /**Constructor of the helpPanel
   * @param displayPanel to connect to the network of other Jpanels
   */
  public HelpPanel(DisplayPanel displayPanel) {
    this.displayPanel = displayPanel;

    //Construct the backbutton
    backButton = new UIButton("BACK", "menu", displayPanel);
    backButton.setSize(40, 40);
    backButton.setLocation(680, 0);

    add(backButton);

    //Add image
    InputStream imgClass = null;
    imgClass = this.getClass().getResourceAsStream("/assets/images/Help.png");
    if(imgClass == null){
			System.out.println("Cannot find help screen image");
		} else {
			try {
				imgHelpScreen = ImageIO.read(imgClass);
			} catch(IOException e) {
				System.out.println("Cannot read help screen image file"); //Handle exception
			}
		}
  }

  public void paintComponent(Graphics g){
		super.paintComponent(g);
    g.drawImage(imgHelpScreen, 0, 0, null);
  }
}
