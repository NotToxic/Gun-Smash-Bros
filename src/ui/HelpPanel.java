package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
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

    InputStream imageclass1 = null;
    imageclass1 = this.getClass().getResourceAsStream("/assets/images/Help.png");
    if (imageclass1 == null){
    }else{
      try{
          imgHelpScreen = ImageIO.read(imageclass1);
      }catch(IOException e){
          System.out.println("Unable to load image from jar");
      }
    }
    if (imgHelpScreen == null){
      try{
          imgHelpScreen = ImageIO.read(new File("/assets/images/Help.png"));
      } catch(IOException e){
          System.out.println("Unable to load image");
      }
    }
  }

  public void paintComponent(Graphics g){
		super.paintComponent(g);
    g.drawImage(imgHelpScreen, 0, 0, null);
  }
}
