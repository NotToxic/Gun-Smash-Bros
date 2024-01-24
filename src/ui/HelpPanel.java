package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class HelpPanel extends JPanel {

  DisplayPanel displayPanel;
  BufferedImage imgHelpScreen = null;
  UIButton backButton;

  public HelpPanel(DisplayPanel displayPanel) {
    this.displayPanel = displayPanel;
    backButton = new UIButton("BACK", "menu", displayPanel);
    backButton.setSize(40, 40);
    backButton.setLocation(680, 0);

    add(backButton);

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
